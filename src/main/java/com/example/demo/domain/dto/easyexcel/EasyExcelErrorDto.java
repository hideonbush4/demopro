package com.example.demo.domain.dto.easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.*;
import lombok.Data;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

/**
 * @author dengzhewen
 * @create 2022-03-04 14:50
 * @Version v1.0.0
 */
@Data
@HeadStyle(horizontalAlignment = HorizontalAlignment.CENTER)
@ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
@ContentRowHeight(20)
@HeadRowHeight(25)
@ColumnWidth(25)
public class EasyExcelErrorDto extends EasyExcelDto{

    @ExcelProperty(value = "错误原因", index = 3)
    private String reason;

}
