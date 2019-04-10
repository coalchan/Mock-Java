package com.luckypeng.mock.core.function.schema;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author coalchan
 * @since 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Function {
    /**
     * @return 函数别名列表
     */
    String[] alias() default {};

    /**
     * @return 函数描述
     */
    String desc() default "";
}
