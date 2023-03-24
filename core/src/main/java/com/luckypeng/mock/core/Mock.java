package com.luckypeng.mock.core;

import com.alibaba.fastjson.JSONObject;
import com.luckypeng.mock.core.template.TemplateHandler;
import com.luckypeng.mock.core.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * TODO 设计一个初始化规则的工具类，后续的每次生成模拟数据避免再次多余的规则解析，正则匹配等，从而应对大数据量生成时的效率，预计大改
 * @author coalchan
 * @since 1.0
 */
@Slf4j
public class Mock {
    /**
     * 处理属性值（可能为占位符）
     * @param value value of property
     * @return mock value
     */
    public static Object mockValue(Object value) {
        return TemplateHandler.handle(value);
    }

    /**
     * KV模板计算
     * @param key name of property
     * @param value value of property
     * @return mock value
     */
    public static Object mock(String key, Object value) {
        return TemplateHandler.handle(key, value);
    }

    /**
     * 模板计算
     * @param template template with json object
     * @return mock value
     */
    public static JSONObject mock(JSONObject template) {
        return TemplateHandler.handle(template);
    }

    /**
     * 模板计算
     * @param jsonTemplate template with json string
     * @return mock value
     */
    public static JSONObject mock(String jsonTemplate) {
        JSONObject jsonObject = JsonUtils.toJson(jsonTemplate);
        return mock(jsonObject);
    }

    /**
     * 模板计算
     * @param jsonTemplate template with json string
     * @param sortedKeys sorted keys, may be less than keys in template
     * @return
     */
    public static JSONObject mock(String jsonTemplate, List<String> sortedKeys) {
        JSONObject sortedTemplate = TemplateHandler.sortedTemplate(JsonUtils.toJson(jsonTemplate), sortedKeys);
        return mock(sortedTemplate);
    }
}
