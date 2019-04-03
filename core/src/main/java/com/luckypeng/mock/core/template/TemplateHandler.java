package com.luckypeng.mock.core.template;

import com.luckypeng.mock.core.function.BasicFunction;
import com.luckypeng.mock.core.function.util.FunctionHelper;
import com.luckypeng.mock.core.util.ObjectUtils;

import static com.luckypeng.mock.core.function.BasicFunction.*;

public class TemplateHandler {
    /**
     * 计算模板结果
     * @param template
     * @param value
     * @return
     */
    public static Object handle(String template, Object value) {
        Rule rule = Rule.fromTemplate(template);
        if (value instanceof Boolean) {
            return handle(rule, (boolean) value);
        } else if (value instanceof Number) {
            return handle(rule, (Number) value);
        } else if (value instanceof String) {
            return handle(rule, (String) value);
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
}
