package com.luckypeng.mock.core.template;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.luckypeng.mock.core.function.BasicFunction;
import com.luckypeng.mock.core.function.util.FunctionHelper;
import com.luckypeng.mock.core.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import static com.luckypeng.mock.core.function.BasicFunction.*;
import static com.luckypeng.mock.core.util.ObjectUtils.Operation.*;

/**
 * @author coalchan
 * @date 2019/4/3
 */
public class TemplateHandler {
    /**
     * JSON模板计算
     * @param jsonTemplate
     * @return
     */
    public static JSONObject handleTemplate(String jsonTemplate) {
        LinkedHashMap<String, Object> map = JSON.parseObject(jsonTemplate, new TypeReference<LinkedHashMap<String, Object>>(){});
        JSONObject jsonObject = new JSONObject(map);
        return handle(jsonObject);
    }

    /**
     * KV模板计算
     * @param key
     * @param value
     * @return
     */
    public static Object handle(String key, Object value) {
        Rule rule = Rule.fromTemplate(key);
        return handle(rule, value);
    }

    /**
     * KV模板计算
     * @param rule
     * @param value
     * @return
     */
    public static Object handle(Rule rule, Object value) {
        if (value instanceof Boolean) {
            return handle(rule, (boolean) value);
        } else if (value instanceof Number) {
            return handle(rule, (Number) value);
        } else if (value instanceof String) {
            return handle(rule, (String) value);
        } else if (value instanceof JSONObject) {
            return handle(rule, (JSONObject) value);
        } else if (value instanceof JSONArray) {
            return handle(rule, (JSONArray) value);
        } else {
            throw new RuntimeException("暂时不支持该类型数据");
        }
    }

    /**
     * 处理属性值
     * @param value
     * @return
     */
    public static Object handle(Object value) {
        if (value instanceof Boolean) {
            return handle((boolean) value);
        } else if (value instanceof Number) {
            return handle((Number) value);
        } else if (value instanceof String) {
            return handle((String) value);
        } else if (value instanceof JSONObject) {
            return handle((JSONObject) value);
        } else if (value instanceof JSONArray) {
            return handle((JSONArray) value);
        } else {
            throw new RuntimeException("暂时不支持该类型数据");
        }
    }

    /**
     * 属性值为布尔值，带有模板规则
     * @param rule
     * @param value
     * @return
     */
    public static boolean handle(Rule rule, boolean value) {
        return rule.isRange() ?
                BasicFunction.bool(
                        ObjectUtils.coalesce(rule.getMin(), 1L),
                        ObjectUtils.coalesce(rule.getMax(), 1L),
                        value
                )
                : handle(value);
    }

    /**
     * 属性值为布尔值
     * @param value
     * @return
     */
    public static boolean handle(boolean value) {
        return value;
    }

    /**
     * 属性值为数字，带有模板规则
     * @param rule
     * @param value
     * @return
     */
    public static Number handle(Rule rule, Number value) {
        if (rule.isDecimal()) {
            return BasicFunction._float(
                    ObjectUtils.coalesce(rule.getMin(), DEFAULT_FLOAT_MIN),
                    ObjectUtils.coalesce(rule.getMax(), DEFAULT_FLOAT_MAX),
                    ObjectUtils.coalesce(rule.getDMin(), DEFAULT_FLOAT_D_MIN),
                    ObjectUtils.coalesce(rule.getDMax(), DEFAULT_FLOAT_D_MAX)
            );
        } else {
            return rule.isRange() ? rule.getCount() : handle(value);
        }
    }

    /**
     * 属性值为数字
     * @param value
     * @return
     */
    public static Number handle(Number value) {
        return value;
    }

    /**
     * 属性值为字符串（可能为占位符），带有模板规则
     * @param rule
     * @param value
     * @return
     */
    public static String handle(Rule rule, String value) {
        String result = "";
        value = handle(value);

        if (!rule.isRange()) {
            result = value;
        } else {
            for (int i = 0; i < rule.getCount(); i++) {
                result += value;
            }
        }

        return result;
    }

    /**
     * 属性值为字符串（可能为占位符）
     * @param value
     * @return
     */
    public static String handle(String value) {
        if (Rule.isPlaceholder(value)) {
            value = FunctionHelper.execFunction(value).toString();
        }
        return value;
    }

    /**
     * 属性值为JSON对象，带有模板规则
     * @param rule
     * @param value
     * @return
     */
    public static JSONObject handle(Rule rule, JSONObject value) {
        if (value.isEmpty()) {
            return value;
        }
        if (rule.isRange()) {
            int size = Math.min(rule.getCount().intValue(), value.size());
            List<String> randomKeys = new ArrayList<>(value.keySet());
            Collections.shuffle(randomKeys);

            JSONObject randomValue = new JSONObject(size, true);
            for (int i = 0; i < size; i++) {
                String subKey = randomKeys.get(i);
                randomValue.put(subKey, value.get(subKey));
            }
            return handle(randomValue);
        } else {
            return handle(value);
        }
    }

    /**
     * 属性值为JSON对象
     * @param value
     * @return
     */
    public static JSONObject handle(JSONObject value) {
        if (value.isEmpty()) {
            return value;
        }
        JSONObject result = new JSONObject(value.size(), true);
        value.entrySet().stream()
                .forEach(kv -> {
                    Rule subRule = Rule.fromTemplate(kv.getKey());
                    result.put(subRule.getKey(), handle(subRule, kv.getValue()));
                    if (subRule.getStep() != null && value.get(kv.getKey()) instanceof Number) {
                        // 有步长时增加 value，以便下一次递增
                        value.put(kv.getKey(), ObjectUtils.compute((Number) value.get(kv.getKey()), subRule.getStep(), add));
                    }
                });
        return result;
    }

    /**
     * 属性值为JSON数组，带有模板规则
     * @param rule
     * @param value
     * @return
     */
    public static Object handle(Rule rule, JSONArray value) {
        if (value.isEmpty()) {
            return value;
        }
        if (Long.valueOf(1).equals(rule.getMin()) && rule.getMax() == null) {
            // 随机选取 1 个元素
            return handle(value.get((int) BasicFunction.integer(0, value.size() - 1)));
        } else if (Integer.valueOf(1).equals(rule.getStep())) {
            // 顺序选取 1 个元素
            return handle(value.get(0));
        } else if (rule.isRange()) {
            // 生成一个新数组，重复次数为 count，大于等于 min，小于等于 max
            JSONArray result = new JSONArray();
            for (int i = 0; i < rule.getCount(); i++) {
                result.addAll(handle(value));
            }
            return result;
        } else {
            return value;
        }
    }

    /**
     * 属性值为JSON数组
     * @param value
     * @return
     */
    public static JSONArray handle(JSONArray value) {
        JSONArray result = new JSONArray();
        value.stream()
                .forEach(json -> result.add(handle(json)));
        return result;
    }
}
