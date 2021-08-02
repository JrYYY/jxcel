package com.jptangchina.jxcel.model;

import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ErrorInfo<T> {
    private int index;
    private T data;
    private String message;
}
