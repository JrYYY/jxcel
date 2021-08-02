package com.jptangchina.jxcel.type;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalTimeTypeHandler extends BaseTypeHandler<LocalTime> {

    @Override
    public Class getType() {
        return LocalTime.class;
    }

    @Override
    public LocalTime stringToData(String text) throws TypeDataException {
        return LocalTime.parse(text, DateTimeFormatter.ofPattern("hh:mm:ss"));
    }

    public static boolean typeEquals(Object data) {
        return LocalTime.class.equals(data.getClass());
    }

    public static boolean typeEquals(Class type) {
        return type.equals(LocalTime.class);
    }
}

