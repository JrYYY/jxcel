package com.jptangchina.jxcel;

import com.jptangchina.jxcel.bean.Student;
import com.jptangchina.jxcel.exception.JxcelException;
import com.jptangchina.jxcel.exception.JxcelParseException;
import com.jptangchina.jxcel.exception.JxcelVerificationException;
import com.jptangchina.jxcel.model.ErrorInfo;
import lombok.ToString;
import org.junit.Assert;
import org.junit.Test;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JxcelParserTest {


    @Test
    public void testParseFromFile() {
        String desktop = FileSystemView.getFileSystemView().getHomeDirectory().getPath();
        JxcelParser parser = JxcelParser.parser();
        List<Student> result = new ArrayList<>();
        try {
            result = parser.parseFileToData(Student.class, new File(desktop + "/jxcel.xlsx"),
                    (data) -> {
                        if (data.getAge() == 18) { throw new JxcelParseException("年龄不能等于18"); }
                        return true;
                    });
        } catch (JxcelVerificationException e) {
            List<ErrorInfo> a = e.getErrorInfo();
            a.forEach(System.out::println);
        }
        result.forEach(System.out::println);
    }

}
