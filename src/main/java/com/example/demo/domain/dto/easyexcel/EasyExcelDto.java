package com.example.demo.domain.dto.easyexcel;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.*;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.util.Date;

/**
 * @author dengzhewen
 * @create 2022-02-18 9:44
 * @Version v1.0.0
 */

/*
@ExcelProperty
@ColumnWith 列宽
@ContentFontStyle 文本字体样式
@ContentLoopMerge 文本合并
@ContentRowHeight 文本行高度
@ContentStyle 文本样式
@HeadFontStyle 标题字体样式
@HeadRowHeight 标题高度
@HeadStyle 标题样式
@ExcelIgnore 忽略项
@ExcelIgnoreUnannotated 忽略未注解
 */

@Data
// easyexcel的相关实体不能使用@Accessors
//@Accessors(chain = true)
// 标题样式
@HeadStyle(horizontalAlignment = HorizontalAlignment.CENTER)
// @ContentStyle 文本样式
@ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
@ContentRowHeight(20)
@HeadRowHeight(25)
public class EasyExcelDto {

    @ColumnWidth(20)
    @ExcelProperty(value = "姓名", index = 0)
    private String name;

    @ColumnWidth(10)
    @ExcelProperty(value = "年龄", index = 1)
    private Integer age;

    @ColumnWidth(30)
    @ExcelProperty(value = "修改时间", index = 2)
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private Date modifiedTime;
}
