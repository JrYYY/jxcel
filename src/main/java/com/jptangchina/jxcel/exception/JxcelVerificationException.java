package com.jptangchina.jxcel.exception;

import com.jptangchina.jxcel.model.ErrorInfo;
import lombok.Getter;

import java.util.List;

@Getter
public class JxcelVerificationException extends JxcelException {

    private List<ErrorInfo> errorInfo;

    public JxcelVerificationException(String message,List<ErrorInfo> errorInfo) {
        super(message);
        this.errorInfo = errorInfo;
    }
}
