package com.jptangchina.jxcel.type;

import java.time.LocalDateTime;

/**
 * 数据类型处理器
 * @param <T>
 */
public interface TypeHandler<T> {

    /**
     * 获取类型
     * @return
     */
    Class getType();

    /**
     * 将字符串转换为 T
     * @param data 转换数据
     * @return
     */
     T stringToData(String text) throws TypeDataException;



}
