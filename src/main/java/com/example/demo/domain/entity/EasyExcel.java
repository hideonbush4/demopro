package com.example.demo.domain.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author dengzhewen
 * @create 2022-02-21 11:41
 * @Version v1.0.0
 */
@Data
@Accessors(chain = true)
@TableName("easyexcel")
public class EasyExcel {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;

    private Integer age;

    private Date modifiedTime;

}
