package com.jptangchina.jxcel.type;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class LocalDateTypeHandler extends BaseTypeHandler<LocalDate> {



    @Override
    public Class getType() {
        return LocalDate.class;
    }

    @Override
    public LocalDate stringToData(String text) throws TypeDataException {

        Pattern dataPattern = Pattern.compile(TypeConstant.DATA_REGULAR);
        Pattern dataTimePattern = Pattern.compile(TypeConstant.DATA_TIME_REGULAR);

        if (dataPattern.matcher(text).find()) {
            return LocalDate.parse(text, DateTimeFormatter.ofPattern(TypeConstant.DATA));
        }else if(dataTimePattern.matcher(text).find()){
            return LocalDate.parse(text, DateTimeFormatter.ofPattern(TypeConstant.DATA_TIME));
        } else {
            throw new TypeDataException("文本不满足 " + TypeConstant.DATA +" 格式");
        }
    }

    public static LocalDate stringToData(String text, String pattern) {
        return LocalDate.parse(text, DateTimeFormatter.ofPattern(pattern));
    }

    public static boolean typeEquals(Object data) {
        return LocalDate.class.equals(data.getClass());
    }

    public static boolean typeEquals(Class type) {
        return type.equals(LocalDate.class);
    }


}
