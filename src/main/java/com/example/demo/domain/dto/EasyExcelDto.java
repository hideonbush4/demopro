package com.example.demo.domain.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author dengzhewen
 * @create 2022-02-18 9:44
 * @Version v1.0.0
 */
@Data
@Accessors(chain = true)
public class EasyExcelDto {
    @ExcelIgnore
    private Integer id;

    @ColumnWidth(20)
    @ExcelProperty(value = "姓名", index = 0)
    private String name;

    @ColumnWidth(5)
    @ExcelProperty(value = "年龄", index = 1)
    private Integer age;

    @ColumnWidth(30)
    @ExcelProperty(value = "修改时间", index = 2)
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private Date modifiedTime;
}
