package com.example.demo.arch.dynamic;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 这里定义所有系统字段
 * 
 * @author wangchao
 */
@Data
public class EdrmsFileDynamic extends AbstractArchDynamic<EdrmsFileDynamic> {
    private static final long serialVersionUID = 824833913406029148L;
    @NotBlank
    @JSONField(serialize = false)
    private String formId;
    @NotNull
    private Long archCategoryId;
    @NotBlank
    @JSONField(serialize = false)
    private String subType;
    private Long id;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifiedTime;
    private String createId;
    private String updateId;
    /**
     * 题名
     */
    private String name;
    /**
     * 档号
     */
    private String number;
    /**
     * 成文日期
     */
    private String writtendate;
    /**
     * 载体形式
     */
    private String carrier;
    /**
     * 实体类型
     */
    private String objtype;
    /**
     * 实体数量
     */
    private String entitynum;
    /**
     * 库房信息
     */
    private String storageroom;

}
