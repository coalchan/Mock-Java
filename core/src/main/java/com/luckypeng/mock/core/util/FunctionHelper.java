package com.luckypeng.mock.core.util;

import com.luckypeng.mock.core.io.ClassScanner;
import com.luckypeng.mock.core.schema.Function;
import com.luckypeng.mock.core.schema.FunctionInfo;
import com.luckypeng.mock.core.schema.MockFunction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author coalchan
 * @date 2019/4/2
 */
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
}
