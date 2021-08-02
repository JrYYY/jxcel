package com.jptangchina.jxcel.type;

public class TypeConstant {
    public static String DATA = "yyyy-MM-dd";
    public static String DATA_TIME = "yyyy-MM-dd HH:mm:ss";
    public static String TIME = "HH:mm:ss";

    public static String DATA_REGULAR = "^[\\d]{4}-[0-1][0-9]-[0-3][0-9]$";
    public static String DATA_TIME_REGULAR = "^[\\d]{4}-[\\d]{2}-[\\d]{2} [\\d]{2}:[\\d]{2}:[\\d]{2}$";

    public static String INTEGER_REGULAR = "^((-?[1-9][0-9]*)|0)$";
    public static String FLOAT_REGULAR = "^((-?[1-9][0-9]*)|0).([0-9]*[1-9])$";


}
