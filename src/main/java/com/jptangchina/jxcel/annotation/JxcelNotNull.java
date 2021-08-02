package com.jptangchina.jxcel.annotation;

import java.lang.annotation.*;

/**
 * 不能为空
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JxcelNotNull {

    boolean value() default true;

    String message() default "不能为空";
}
