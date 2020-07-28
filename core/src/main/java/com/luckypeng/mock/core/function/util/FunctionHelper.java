package com.luckypeng.mock.core.function.util;

import com.luckypeng.mock.core.io.ClassScanner;
import com.luckypeng.mock.core.function.schema.Functions;
import com.luckypeng.mock.core.function.schema.Function;
import com.luckypeng.mock.core.function.schema.MockFunction;
import com.luckypeng.mock.core.function.schema.ParamType;
import com.luckypeng.mock.core.template.Rule;
import com.luckypeng.mock.core.util.AssertionUtils;
import com.luckypeng.mock.core.util.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author coalchan
 * @since 1.0
 */
@Slf4j
public class FunctionHelper {
    private FunctionHelper() {}

    public static Map<String, List<MockFunction>> MOCK_FUNCTIONS;

    static {
        List<Class> mockClassList = ClassScanner.scan("com.luckypeng.mock.core.function", Functions.class);
        MOCK_FUNCTIONS = mockClassList
                .stream()
                .flatMap(clazz -> Arrays.stream(clazz.getMethods()))
                .filter(method -> method.isAnnotationPresent(Function.class))
                .flatMap(method -> {
                    Function info = method.getAnnotation(Function.class);
                    List<MockFunction> functions = new ArrayList<>();
                    functions.add(new MockFunction(method.getName(), info.desc(), method));
                    for (String alias: info.alias()) {
                        functions.add(new MockFunction(alias, info.desc(), method));
                    }
                    return functions.stream();
                })
                .collect(Collectors.groupingBy(MockFunction::getName));
    }

    /**
     * 执行函数表达式
     * @param funcExpression 格式为 @func(p1 [, p2, ...])
     * @return function result
     */
    public static Object execFunction(String funcExpression) {
        String[] functionAndParams = Rule.parsePlaceholder(funcExpression);
        String funcName = functionAndParams[0];
        Object[] params = resolveParams(functionAndParams[1]);

        List<MockFunction> functions = MOCK_FUNCTIONS.get(funcName);
        AssertionUtils.notEmpty(functions, "不支持该函数: " + funcName);
        for (MockFunction function: functions) {
            boolean isFit = true;
            Class[] classes = function.getMethod().getParameterTypes();
            // 参数个数检查
            boolean paramsMatched = (ObjectUtils.isEmpty(params) && ObjectUtils.isEmpty(classes)) ||
                    (params != null && params.length == classes.length);
            if (paramsMatched) {
                for (int i = 0; params != null && i < params.length; i++) {
                    // 参数类型检查
                    if(!ParamType.isFitType(params[i], classes[i])) {
                        isFit = false;
                    }
                }
            } else {
                isFit = false;
            }
            if (isFit) {
                log.debug("已找到对应的函数: " + function.toString());
                try {
                    return function.getMethod().invoke(null, params);
                } catch (IllegalAccessException|InvocationTargetException e) {
                    throw new RuntimeException("函数执行出错: " + funcName, e);
                }
            }
        }
        throw new RuntimeException("该函数没有匹配的参数类型: " + funcName);
    }

    /**
     * @param paramStr e.g. true,1,10.1
     * @return
     */
    private static Object[] resolveParams(String paramStr) {
        if (StringUtils.isEmpty(paramStr)) {
            return null;
        } else if (paramStr.startsWith("[")) {
            // 数组类型
            AssertionUtils.isTrue(paramStr.endsWith("]") && paramStr.length() > 2,
                    "参数为数组时必须以[]进行包裹且数组不为空");
            Object[] params = splitParams(paramStr.substring(1, paramStr.length() - 1));
            return new Object[]{params};
        } else {
            Object[] params = splitParams(paramStr);
            return params;
        }
    }

    /**
     * 切分逗号分隔的参数
     * @param paramStr
     * @return
     */
    private static Object[] splitParams(String paramStr) {
        if (ObjectUtils.isEmpty(paramStr)) {
            return new Object[]{};
        }
        String[] paramStrArray = paramStr.split(" *, *");
        Object[] params = new Object[paramStrArray.length];
        for (int i = 0; i < paramStrArray.length; i++) {
            paramStrArray[i] = paramStrArray[i].trim();
            if (paramStrArray[i].charAt(0) == '\'') {
                // 函数参数为字符串
                params[i] = paramStrArray[i].substring(1, paramStrArray[i].length()-1);
            } else {
                // 其他
                params[i] = ParamType.toSpecificType(paramStrArray[i]);
            }
        }
        return params;
    }
}
