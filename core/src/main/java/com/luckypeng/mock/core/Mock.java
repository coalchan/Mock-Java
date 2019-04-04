package com.luckypeng.mock.core;

import com.alibaba.fastjson.JSONObject;
import com.luckypeng.mock.core.function.util.FunctionHelper;
import com.luckypeng.mock.core.template.TemplateHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author coalchan
 * @date 2019/4/2
 */
@Slf4j
public class Mock {
    /**
     * 计算函数值
     * @param funcExpression
     * @return
     */
    public static Object mockFunc(String funcExpression) {
        return FunctionHelper.execFunction(funcExpression);
    }

    /**
     * 单一模板计算
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
        return TemplateHandler.handle(jsonTemplate);
    }
}
