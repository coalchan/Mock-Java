package com.luckypeng.mock.core.function.schema;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author coalchan
 * @date 2019/4/2
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FunctionInfo {
    /**
     * 函数别名列表
     */
    String[] alias() default {};

    /**
     * 函数描述
     */
    String desc() default "";
}
