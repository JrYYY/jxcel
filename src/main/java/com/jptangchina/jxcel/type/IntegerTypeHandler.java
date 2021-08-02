package com.jptangchina.jxcel.type;

public class IntegerTypeHandler extends BaseTypeHandler<Integer> {
    @Override
    public Class getType() {
        return Integer.TYPE;
    }

    @Override
    public Integer stringToData(String text) {
        return Integer.valueOf(text);
    }






}
