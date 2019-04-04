package com.luckypeng.mock.core.template;

import com.alibaba.fastjson.JSON;
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

/**
 * @author coalchan
 * @date 2019/4/3
 */
public class TemplateHandler {
    /**
     * 模板计算
     * @param jsonTemplate
     * @return
     */
    public static JSONObject handle(String jsonTemplate) {
        LinkedHashMap<String, Object> map = JSON.parseObject(jsonTemplate, new TypeReference<LinkedHashMap<String, Object>>(){});
        JSONObject jsonObject = new JSONObject(map);
        return handle(jsonObject);
    }

    /**
     * 模板计算
     * @param template
     * @return
     */
    public static JSONObject handle(JSONObject template) {
        return handle(Rule.fromTemplate("json"), template);
    }

    /**
     * 单一模板计算
     * @param key
     * @param value
     * @return
     */
    public static Object handle(String key, Object value) {
        Rule rule = Rule.fromTemplate(key);
        return handle(rule, value);
    }

    /**
     * 计算单一模板结果
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
        } else {
            throw new RuntimeException("暂时不支持该类型数据");
        }
    }

    /**
     * 属性值为布尔值
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
                : value;
    }

    /**
     * 属性值为数字
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
            return rule.isRange() ? rule.getCount() : value;
        }
    }

    /**
     * 属性值为字符串（可能为占位符）
     * @param rule
     * @param value
     * @return
     */
    public static String handle(Rule rule, String value) {
        String result = "";

        if (Rule.isPlaceholder(value)) {
            value = FunctionHelper.execFunction(value).toString();
        }

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
     * 属性值为JSON对象
     * @param rule
     * @param value
     * @return
     */
    public static JSONObject handle(Rule rule, JSONObject value) {
        JSONObject result;
        if (rule.isRange()) {
            int size = Math.min(rule.getCount().intValue(), value.size());
            result = new JSONObject(size, true);
            List<String> shuffleKeys = new ArrayList<>(value.keySet());
            Collections.shuffle(shuffleKeys);
            for (int i = 0; i < size; i++) {
                String subKey = shuffleKeys.get(i);
                Rule subRule = Rule.fromTemplate(subKey);
                result.put(subRule.getKey(), handle(subRule, value.get(subKey)));
            }
        } else {
            result = new JSONObject(value.size(), true);
            value.entrySet().stream()
                    .forEach(kv -> {
                        Rule subRule = Rule.fromTemplate(kv.getKey());
                        result.put(subRule.getKey(), handle(subRule, kv.getValue()));
                    });
        }
        return result;
    }
}
