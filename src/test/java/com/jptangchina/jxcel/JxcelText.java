package com.jptangchina.jxcel;

import com.jptangchina.jxcel.type.LocalTimeTypeHandler;

import java.time.LocalTime;

public class JxcelText {

    public static void main(String[] args) {
        System.out.println( LocalTimeTypeHandler.typeEquals(LocalTime.now()));
    }
}
