package com.luckypeng.mock.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.luckypeng.mock.core.template.TemplateHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashMap;

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
    public static Object mockValue(Object value) {
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
        LinkedHashMap<String, Object> map =
                JSON.parseObject(jsonTemplate, new TypeReference<LinkedHashMap<String, Object>>(){});
        JSONObject jsonObject = new JSONObject(map);
        return mock(jsonObject);
    }
}
