package com.jptangchina.jxcel.annotation;

import java.lang.annotation.*;

/**
 * 数字 最大值
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JxcelMax {
    long value() default Long.MAX_VALUE;
    String message() default "数值过大";
}
