package com.example.demo.domain.dto.initcomment;

import lombok.Data;

/**
 * @author dengzhewen
 * @create 2022-02-19 11:12
 * @Version v1.0.0
 */
@Data
public class ColumnEntity {
    /**
     * 列表
     */
    private String columnName;
    /**
     * 数据类型
     */
    private String dataType;
    /**
     * 备注
     */
    private String comments;

    /**
     * 驼峰属性
     */
    private String caseAttrName;
    /**
     * 普通属性
     */
    private String lowerAttrName;
    /**
     * 属性类型
     */
    private String attrType;
    /**
     * 其他信息
     */
    private String extra;
    /**
     * 字段类型
     */
    private String columnType;
    /**
     * 是否可以为空
     */
    private Boolean nullable;
}
