package com.jptangchina.jxcel.annotation;

import java.lang.annotation.*;

/**
 * 数字 最小值
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JxcelMin {
    long value() default Long.MIN_VALUE;

    String message() default "数值过小";
}
