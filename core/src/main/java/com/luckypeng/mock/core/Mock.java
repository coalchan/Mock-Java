package com.luckypeng.mock.core;

import com.alibaba.fastjson.JSONObject;
import com.luckypeng.mock.core.template.TemplateHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author coalchan
 * @date 2019/4/2
 */
@Slf4j
public class Mock {
    /**
     * 处理属性值（可能为占位符）
     * @param value
     * @return
     */
    public static Object mock(Object value) {
        return TemplateHandler.handle(value);
    }

    /**
     * KV模板计算
     * @param key
     * @param value
     * @return
     */
    public static Object mock(String key, Object value) {
        return TemplateHandler.handle(key, value);
    }

    /**
     * 模板计算
     * @param template
     * @return
     */
    public static JSONObject mock(JSONObject template) {
        return TemplateHandler.handle(template);
    }

    /**
     * 模板计算
     * @param jsonTemplate
     * @return
     */
    public static JSONObject mock(String jsonTemplate) {
        return TemplateHandler.handleTemplate(jsonTemplate);
    }
}
