package com.jptangchina.jxcel;


import com.jptangchina.jxcel.annotation.*;
import com.jptangchina.jxcel.exception.JxcelParseException;

import java.lang.reflect.Field;
import java.util.regex.Pattern;

/**
 * 数据基础验证
 *
 * @author OU
 */
public class JxcelDataVerification {

    public static JxcelDataVerification now = JxcelDataVerification.now();

    /**
     * 验证数据不能为空
     *
     * @param field 列对象
     * @param data  数据
     * @param <T>   数据类型
     * @throws JxcelParseException
     * @throws IllegalAccessException
     */
    public <T> void notNull(Field field, T data) throws JxcelParseException, IllegalAccessException {
        if (field.isAnnotationPresent(JxcelNotNull.class) && field.isAnnotationPresent(JxcelCell.class)) {
            JxcelNotNull jxcelNotNull = field.getAnnotation(JxcelNotNull.class);
            JxcelCell jxcelCell = field.getAnnotation(JxcelCell.class);
            field.setAccessible(true);
            if (jxcelNotNull.value()) {  // 不能为空
                if (field.get(data) instanceof String) {
                    String strData = (String) field.get(data);
                    if (strData == null || strData.isEmpty()) {
                        throw new JxcelParseException(jxcelCell.value() + jxcelNotNull.message());
                    }
                } else {
                    Object obj = field.get(data);
                    if (obj == null) {
                        throw new JxcelParseException(jxcelCell.value() + jxcelNotNull.message());
                    }
                }
            } else {    // 必须为空
                Object obj = field.get(data);
                if (obj == null) {
                    throw new JxcelParseException(jxcelCell.value() + jxcelNotNull.message());
                }
            }
        }
    }

    /**
     * 验证字符串长度
     *
     * @param field 列对象
     * @param data  数据
     * @param <T>   数据类型
     * @throws JxcelParseException
     * @throws IllegalAccessException
     */
    public <T> void stringSize(Field field, T data) throws JxcelParseException, IllegalAccessException {
        if (field.isAnnotationPresent(JxcelSize.class) && field.isAnnotationPresent(JxcelCell.class)) {
            JxcelSize jxcelSize = field.getAnnotation(JxcelSize.class);
            JxcelCell jxcelCell = field.getAnnotation(JxcelCell.class);
            field.setAccessible(true);
            if (field.get(data) instanceof String) {
                String strData = (String) field.get(data);
                int min = jxcelSize.min();
                int max = jxcelSize.max();
                if (min < max) {
                    if (strData.length() < min || strData.length() > max) {
                        throw new JxcelParseException(jxcelCell.value() + jxcelSize.message());
                    }
                } else {
                    throw new IllegalAccessException("错误原因：字符串最小值大于最大值");
                }
            } else {
                throw new IllegalAccessException("错误原因：JxcelSize 无法使用在非字符串类型上");
            }
        }
    }

    /**
     * 数字最小值
     *
     * @param field 列对象
     * @param data  数据
     * @param <T>   数据类型
     * @throws JxcelParseException
     * @throws IllegalAccessException
     */
    public <T> void numberMin(Field field, T data) throws JxcelParseException, IllegalAccessException {
        if (field.isAnnotationPresent(JxcelMin.class) && field.isAnnotationPresent(JxcelCell.class)) {
            JxcelMin jxcelMin = field.getAnnotation(JxcelMin.class);
            JxcelCell jxcelCell = field.getAnnotation(JxcelCell.class);
            field.setAccessible(true);
            if (field.getType().equals(Integer.TYPE) || field.getType().equals(Integer.class)) {
                Integer number = (Integer) field.get(data);
                if (jxcelMin.value() > number) {
                    throw new JxcelParseException(jxcelCell.value() + jxcelMin.message());
                }
            } else {
                throw new IllegalAccessException("错误原因：JxcelMin 无法使用在非数字类型上");
            }
        }
    }

    /**
     * 数字最大值
     *
     * @param field 列对象
     * @param data  数据
     * @param <T>   数据类型
     * @throws JxcelParseException
     * @throws IllegalAccessException
     */
    public <T> void numberMax(Field field, T data) throws JxcelParseException, IllegalAccessException {
        if (field.isAnnotationPresent(JxcelMax.class) && field.isAnnotationPresent(JxcelCell.class)) {
            JxcelMax jxcelMax = field.getAnnotation(JxcelMax.class);
            JxcelCell jxcelCell = field.getAnnotation(JxcelCell.class);
            field.setAccessible(true);
            if (field.getType().equals(Integer.TYPE) || field.getType().equals(Integer.class)) {
                Integer number = (Integer) field.get(data);
                if (jxcelMax.value() < number) {
                    throw new JxcelParseException(jxcelCell.value() + jxcelMax.message());
                }
            } else {
                throw new IllegalAccessException("错误原因：JxcelMax 无法使用在非数字类型上");
            }
        }
    }

    /**
     * 正则验证
     *
     * @param field 列对象
     * @param data  数据
     * @param <T>   数据类型
     * @throws JxcelParseException
     * @throws IllegalAccessException
     */
    public <T> void pattern(Field field, T data) throws JxcelParseException, IllegalAccessException {
        if (field.isAnnotationPresent(JxcelPattern.class) && field.isAnnotationPresent(JxcelCell.class)) {
            JxcelPattern jxcelPattern = field.getAnnotation(JxcelPattern.class);
            JxcelCell jxcelCell = field.getAnnotation(JxcelCell.class);
            field.setAccessible(true);
            if (field.get(data) instanceof String) {
                String strData = (String) field.get(data);
                Pattern pattern = Pattern.compile(jxcelPattern.regexp().isEmpty() ? jxcelPattern.value() : jxcelPattern.regexp());
                if (!pattern.matcher(strData).find()) {
                    throw new JxcelParseException(jxcelCell.value() + jxcelPattern.message());
                }
            } else {
                throw new IllegalAccessException("错误原因：JxcelPattern 无法使用在非字符串类型上");
            }
        }
    }

    public <T> void dataVerification(Class<T> modal, T data) throws JxcelParseException, IllegalAccessException {
        Field[] fields = modal.getDeclaredFields();
        StringBuilder message = new StringBuilder();
        for (Field field : fields) {
            try {
                notNull(field, data);
                stringSize(field, data);
                numberMin(field, data);
                numberMax(field, data);
                pattern(field, data);
            } catch (JxcelParseException e) {
                message.append(e.getMessage()).append(" ");
            } catch (IllegalAccessException e) {
                throw e;
            }
        }
        if (message.length() != 0){
            throw new JxcelParseException(message.toString());
        }
    }

    private static JxcelDataVerification now() {
        return new JxcelDataVerification();
    }

    private JxcelDataVerification() {
    }
}
