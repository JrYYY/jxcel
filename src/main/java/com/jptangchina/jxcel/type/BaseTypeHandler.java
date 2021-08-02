package com.jptangchina.jxcel.type;

public abstract class BaseTypeHandler<T> implements TypeHandler{

    @Override
    public T stringToData(String text) throws TypeDataException {
        return (T) text;
    }
}
