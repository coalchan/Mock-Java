package com.luckypeng.mock.core.schema;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Method;

/**
 * @author coalchan
 * @date 2019/4/2
 */
@Data
@AllArgsConstructor
public class MockFunction {
    /**
     * 函数名称
     */
    private String name;

    /**
     * 函数描述
     */
    private String desc;

    /**
     * 对应的方法
     */
    private Method method;
}
