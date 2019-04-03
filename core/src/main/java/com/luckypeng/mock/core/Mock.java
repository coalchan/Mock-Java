package com.luckypeng.mock.core;

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
    public static Object mock(String funcExpression) {
        return FunctionHelper.execFunction(funcExpression);
    }

    /**
     * 模板计算
     * @param template
     * @param value
     * @return
     */
    public static Object mock(String template, Object value) {
        return TemplateHandler.handle(template, value);
    }
}
