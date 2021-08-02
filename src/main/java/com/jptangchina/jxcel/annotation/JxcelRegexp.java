package com.jptangchina.jxcel.annotation;

/**
 * 正则集合
 */
public class JxcelRegexp {

    /**
     * 邮箱验证
     */
    public static final String email = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";

    /**
     * 身份证
     */
    public static final String idCard = "^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)|(^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$";

    /**
     * 电话号码
     */
    public static final String phoneNumber = "^1[0-9]{10}$";

}
