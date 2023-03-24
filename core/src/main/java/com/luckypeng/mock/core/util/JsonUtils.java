package com.luckypeng.mock.core.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import java.util.LinkedHashMap;

/**
 * @author coalchan
 */
public class JsonUtils {
    /**
     * JSON 字符串转为 JSONObject
     * @param jsonTemplate
     * @return
     */
    public static JSONObject toJson(String jsonTemplate) {
        LinkedHashMap<String, Object> map =
                JSON.parseObject(jsonTemplate, new TypeReference<LinkedHashMap<String, Object>>(){});
        return new JSONObject(map);
    }
}
