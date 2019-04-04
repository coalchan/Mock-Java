package com.luckypeng.mock.core.function.util;

import com.luckypeng.mock.core.io.ClassScanner;
import com.luckypeng.mock.core.function.schema.Function;
import com.luckypeng.mock.core.function.schema.FunctionInfo;
import com.luckypeng.mock.core.function.schema.MockFunction;
import com.luckypeng.mock.core.function.schema.ParamType;
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
 * @date 2019/4/2
 */
@Slf4j
public class FunctionHelper {
    private FunctionHelper() {}

    public static Map<String, List<MockFunction>> MOCK_FUNCTIONS;

    static {
        List<Class> mockClassList = ClassScanner.scan("com.luckypeng.mock.core.function", Function.class);
        MOCK_FUNCTIONS = mockClassList
                .stream()
                .flatMap(clazz -> Arrays.stream(clazz.getMethods()))
                .filter(method -> method.isAnnotationPresent(FunctionInfo.class))
                .flatMap(method -> {
                    FunctionInfo info = method.getAnnotation(FunctionInfo.class);
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
     * @return
     */
    public static Object execFunction(String funcExpression) {
        int bracketIndex = funcExpression.indexOf('(');
        String funcName = funcExpression.substring(1, bracketIndex);
        Object[] params = resolveParams(funcExpression.substring(bracketIndex+1, funcExpression.length()-1));

        List<MockFunction> functions = MOCK_FUNCTIONS.get(funcName);
        AssertionUtils.notEmpty(functions, "不支持该函数: " + funcName);
        for (MockFunction function: functions) {
            boolean isFit = true;
            Class[] classes = function.getMethod().getParameterTypes();
            // 参数个数检查
            if ((ObjectUtils.isEmpty(params) && ObjectUtils.isEmpty(classes)) ||
                    (params != null && params.length == classes.length)) {
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
                    throw new RuntimeException("函数执行出错: " + funcName);
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
        Object[] params = null;
        if (StringUtils.isNotEmpty(paramStr)) {
            String[] paramStrArray = paramStr.split(" *, *");
            params = new Object[paramStrArray.length];
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
        }
        return params;
    }
}
