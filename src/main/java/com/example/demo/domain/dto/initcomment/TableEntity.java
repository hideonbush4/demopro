package com.example.demo.domain.dto.initcomment;

import lombok.Data;

import java.util.List;

/**
 * @author dengzhewen
 * @create 2022-02-19 11:11
 * @Version v1.0.0  https://blog.csdn.net/lkforce/article/details/79557482
 */
@Data
public class TableEntity {
    /**
     * 名称
     */
    private String tableName;
    /**
     * 备注
     */
    private String comments;
    /**
     * 主键
     */
    private ColumnEntity pk;
    /**
     * 列名
     */
    private List<ColumnEntity> columns;
    /**
     * 驼峰类型
     */
    private String caseClassName;
    /**
     * 普通类型
     */
    private String lowerClassName;
}
