package com.jptangchina.jxcel.annotation;

import java.lang.annotation.*;

/**
 * 正则匹配
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JxcelPattern {

    /**
     * value 等同于 regexp
     * @return 正则表达式
     */
    String value() default "";

    /**
     * regexp 等同于 value
     * @return 正则表达式
     */
    String regexp() default "";

    /**
     * @return 错误信息
     */
    String message() default "不满足验证要求";
}
