package com.jptangchina.jxcel.bean;

import com.jptangchina.jxcel.annotation.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JxcelSheet("学生名单")
public class Student {
    @JxcelMin(value = 10,message = "年龄最小为10岁")
    @JxcelCell("年龄")
    private int age;

    @JxcelCell(value = "性别", parse = {"男", "女"})
    private int sex;

    @JxcelCell(value = "姓名", order = 1)
    private String name;

    @JxcelCell(value = "出生日期", format = "yyyy-MM-dd")
    private Date birthDay;

    @JxcelPattern(JxcelRegexp.phoneNumber)
    @JxcelCell(value = "手机号", suffix = "\t")
    private String mobile;

}
