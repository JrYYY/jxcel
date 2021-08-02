package com.jptangchina.jxcel.annotation;

import java.lang.annotation.*;

/**
 * 字符串长度
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JxcelSize {

    /**
     * @return  最小长度
     */
    int min() default 0;

    /**
     * @return  最大长度
     */
    int max() default 2147483647;

    /**
     * @return 不满足信息
     */
    String message() default "长度不满足";
}
