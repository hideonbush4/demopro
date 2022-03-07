package com.example.demo.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author dengzhewen
 * @create 2022-03-07 14:58
 * @Version v1.0.0
 */
@Mapper
public interface DataBaseDdlMapper {

    /**
     * 创建表
     *
     * @param createSql
     *            建表语句
     */
    void createTable(String createSql);

    /**
     * 修改字段类型
     *
     * @param tableName
     *            表名
     * @param columnCode
     *            字段名
     * @param dataType
     *            类型及长度
     */
    void modify(String tableName, String columnCode, String dataType);

    /**
     * 添加列
     *
     * @param tableName
     *            表名
     * @param columnName
     *            字段名
     * @param dataType
     *            类型及长度
     * @param comment
     *            备注
     */
    void addColumn(String tableName, String columnName, String dataType, String comment);

    /**
     * 删除列
     *
     * @param tableName
     *            表名
     * @param columnName
     *            列名
     */
    void dropColumn(String tableName, String columnName);

    /**
     * 重命名
     *
     * @param tableName
     *            表名
     * @param sourceColumn
     *            修改钱名称
     * @param dataType
     *            类型及长度
     * @param targetColumn
     *            修改后名称
     * @param comment
     *            备注
     */
    void changeColumn(String tableName, String sourceColumn, String targetColumn, String dataType, String comment);

    /**
     * 根据分组列名进行分组查询
     * @param tableName：表名
     * @param groupColumn：分组列名
     * @return
     */
    List<String> groupByColumn(String tableName, String groupColumn);

}
