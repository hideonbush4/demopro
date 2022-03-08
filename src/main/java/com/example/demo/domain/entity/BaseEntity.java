package com.example.demo.domain.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author dengzhewen
 * @create 2022-02-14 10:16
 * @Version v1.0.0
 */
@Data
public class BaseEntity implements Serializable {

    // 标识主键id，input表示insert 前自行 set 主键值
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    /**
     * fill-insert:插入时自动填充字段
     *      insert_update:插入、修改时自动填充字段
     *      update: 修改时自动填充字段
     *      default:默认不处理
     */
    @TableField(value = "createTime", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @TableField(value = "modifiedTime", fill = FieldFill.INSERT_UPDATE)
    // @JSONField是fastjson的注解，JSON.toJSONString方法获取的属性格式
    // @JsonFormat是jackson的注解，数据库的返回数据使用改格式
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date modifiedTime;

    @TableField(value = "createId", fill = FieldFill.INSERT)
    private String createId;

    @TableField(value = "updateId", fill = FieldFill.INSERT_UPDATE)
    private String updateId;

}
