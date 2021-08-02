package com.jptangchina.jxcel;


import com.jptangchina.jxcel.exception.JxcelParseException;

/**
 * 添加数据库验证
 * @author OU
 */
public interface JxcelDatabaseVerification<T> {

      Boolean matchData(T date) throws JxcelParseException;

}
