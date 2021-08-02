package com.jptangchina.jxcel.type;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LocalDateTimeTypeHandler extends BaseTypeHandler<LocalDateTime> {



    @Override
    public Class getType() {
        return LocalDateTime.class;
    }

    @Override
    public LocalDateTime stringToData(String text) throws TypeDataException {
        Pattern pattern = Pattern.compile(TypeConstant.DATA_TIME_REGULAR);
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return LocalDateTime.parse(text, DateTimeFormatter.ofPattern(TypeConstant.DATA_TIME));
        } else {
            throw new TypeDataException("文本不满足 " + TypeConstant.DATA_TIME + " 格式");
        }
    }

    public static LocalDateTime stringToData(String text, String pattern) {
        return LocalDateTime.parse(text, DateTimeFormatter.ofPattern(pattern));
    }


    public static boolean typeEquals(Object data) {
        return LocalDateTime.class.equals(data.getClass());
    }

    public static boolean typeEquals(Class type) {
        return type.equals(LocalDateTime.class);
    }
}


