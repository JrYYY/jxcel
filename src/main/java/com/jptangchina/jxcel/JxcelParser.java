package com.jptangchina.jxcel;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.jptangchina.jxcel.annotation.JxcelCell;
import com.jptangchina.jxcel.exception.JxcelException;
import com.jptangchina.jxcel.exception.JxcelParseException;
import com.jptangchina.jxcel.exception.JxcelVerificationException;
import com.jptangchina.jxcel.model.ErrorInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 从文件或workbook中解析java对象
 * 注意：只会解析第一个sheet中的内容
 *
 * @author jptangchina
 */
@Slf4j
public class JxcelParser {



    public <T> List<T> parseFromWorkbook(Class<T> type, Workbook workbook, JxcelDatabaseVerification<T> verification) throws JxcelException {
        Preconditions.checkNotNull(workbook, "工作簿不能为空.");
        Sheet sheet = workbook.getSheetAt(0);
        Preconditions.checkNotNull(sheet, "工作表不能为空.");
        Row headRow = sheet.getRow(0);
        List<String> headers = new ArrayList<>();

        // 获取每列标题 存储到 headers
        headRow.cellIterator().forEachRemaining(c -> headers.add(getCellValueAsString(c)));

        // 将带有 JxcelCell 注解的 参数根据 JxcelCell的value
        Map<String, Field> fields = Arrays.stream(type.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(JxcelCell.class))
                .collect(Collectors.toMap(f -> f.getAnnotation(JxcelCell.class).value(), f -> f));

        List<ErrorInfo> errorInfos = new ArrayList<>();
        List<T> parsedData = new ArrayList<>();
        for (int rowIndex = 1; rowIndex < sheet.getLastRowNum() + 1; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            T data = null;
            try {
                data = type.newInstance();
                for (int colIndex = 0; colIndex < headers.size(); colIndex++) {
                    String cellValue = getCellValueAsString(row.getCell(colIndex));
                    invokeDataSet(data, fields.get(headers.get(colIndex)), cellValue);
                }
                if (verification.matchData(data)) {
                    JxcelDataVerification.now.dataVerification(type,data);
                    parsedData.add(data);
                }else {
                    throw new JxcelException("数据有误！");
                }
            } catch (JxcelException e) {
                errorInfos.add(ErrorInfo.builder().index(rowIndex).data(data).message(e.getMessage()).build());
            } catch (InstantiationException | IllegalAccessException e) {
                throw new JxcelParseException(e.getMessage());
            }
        }
        if(errorInfos.size() != 0){
            throw new JxcelVerificationException("数据上传有误",errorInfos);
        }
        return parsedData;
    }

    public <T> List<T> parseFromFile(Class<T> type, File file, JxcelDatabaseVerification<T> verification) {
        try (Workbook workbook = WorkbookFactory.create(file)) {
            return parseFromWorkbook(type, workbook, verification);
        } catch (IOException e) {
            throw new JxcelParseException("无法从目标文件创建工作簿.");
        }
    }

    /**
     * excel 导入数据为 数据模型 验证数据库
     *
     * @param type         模型类
     * @param file         加载文件流
     * @param verification 验证 回调函数
     * @param <T>          解析类型
     * @return 解析列表
     * @throws JxcelException
     */
    public <T> List<T> parseFileToData(Class<T> type, File file, JxcelDatabaseVerification<T> verification) throws JxcelException {
        return parseFromFile(type, file, verification);
    }

    /**
     * excel 导入数据为 数据模型
     *
     * @param type 模型类
     * @param file 加载文件流
     * @param <T>  解析类型
     * @return 解析列表
     * @throws JxcelException
     */
    public <T> List<T> parseFileToData(Class<T> type, File file) throws JxcelException {
        return parseFromFile(type, file, (data) -> true);
    }

    private <T> void invokeDataSet(T obj, Field field, String value) throws IllegalAccessException {
        field.setAccessible(true);
        field.set(obj, parseStringWithMatchedType(value, field));
    }

    private Object parseStringWithMatchedType(String value, Field field) {
        JxcelCell jxcelCell = field.getAnnotation(JxcelCell.class);
        value = removeSuffix(value, jxcelCell);
        Class<?> fieldType = field.getType();
        if (jxcelCell.parse().length > 0) {
            List<String> parseData = Arrays.asList(jxcelCell.parse());
            int index = parseData.indexOf(value);
            if (index < 0) {
                return parseStringToBasicDataType(value, fieldType);
            } else {
                return index;
            }
        }
        if (Date.class.equals(fieldType) && !Strings.isNullOrEmpty(value)) {
            if (isValidLong(value)) {
                return new DateTime(Long.valueOf(value)).toDate();
            }
            return DateTime.parse(value, DateTimeFormat.forPattern(jxcelCell.format())).toDate();
        }
//        if()
        return parseStringToBasicDataType(value, fieldType);
    }

    private String removeSuffix(String value, JxcelCell jxcelCell) {
        if (!Strings.isNullOrEmpty(jxcelCell.suffix())
                && value.contains(jxcelCell.suffix())
                && value.indexOf(jxcelCell.suffix()) == (value.length() - jxcelCell.suffix().length())) {
            value = value.substring(0, value.lastIndexOf(jxcelCell.suffix()));
        }
        return value;
    }

    private Object parseStringToBasicDataType(String value, Class<?> fieldType) {
        if (Strings.isNullOrEmpty(value)) {
            return null;
        }


        if (Byte.class.equals(fieldType) || Byte.TYPE.equals(fieldType)) {
            return Byte.valueOf(value);
        } else if (Boolean.class.equals(fieldType) || Boolean.TYPE.equals(fieldType)) {
            return Boolean.parseBoolean(value) || "1".equals(value);
        } else if (String.class.equals(fieldType)) {
            return value;
        } else if (Short.class.equals(fieldType) || Short.TYPE.equals(fieldType)) {
            return Short.valueOf(value);
        } else if (Integer.class.equals(fieldType) || Integer.TYPE.equals(fieldType)) {
            return Integer.valueOf(value);
        } else if (Long.class.equals(fieldType) || Long.TYPE.equals(fieldType)) {
            return Long.valueOf(value);
        } else if (Float.class.equals(fieldType) || Float.TYPE.equals(fieldType)) {
            return Float.valueOf(value);
        } else if (Double.class.equals(fieldType) || Double.TYPE.equals(fieldType)) {
            return Double.valueOf(value);
        } else {
            throw new JxcelParseException("Illegal data type: " + fieldType);
        }
    }

    private String getCellValueAsString(Cell cell) {
        if (null == cell) {
            return "";
        }
        if (cell.getCellType() == CellType.NUMERIC) {
            if (DateUtil.isCellDateFormatted(cell)) {
                return String.valueOf(cell.getDateCellValue().getTime());
            }
            DecimalFormat df = new DecimalFormat();
            return df.format(cell.getNumericCellValue());
        }
        return cell.toString();
    }

    private boolean isValidLong(String longValue) {
        for (int i = longValue.length(); --i >= 0; ) {
            int c = longValue.charAt(i);
            if (c < 48 || c > 57) {
                return false;
            }
        }
        return true;
    }

    public static JxcelParser parser() {
        return new JxcelParser();
    }
}
