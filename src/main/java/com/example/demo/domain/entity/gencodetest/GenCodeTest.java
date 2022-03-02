
package com.example.demo.domain.entity.gencodetest;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 代码生成测试表
 *
 * @author dengzhewen
 * @date 2022-03-02 20:07:04
 */
@Data
@TableName("gen_code_test")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "代码生成测试表")
public class GenCodeTest extends Model<GenCodeTest> {
    private static final long serialVersionUID = 1L;

    /**
     * id主键
     */
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "id主键")

    @TableField("id")
    private Long id;
    /**
     * 创建人id
     */
    @TableField(value = "create_id", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建人id")

    private String createId;
    /**
     * 修改人id
     */
    @TableField(value = "update_id", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "修改人id")

    private String updateId;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")

    private Date createTime;
    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "modified_time", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "修改时间")

    private Date modifiedTime;
    /**
     * 测试varchar
     */
    @ApiModelProperty(value = "测试varchar")

    @TableField("test_varchar")
    private String testVarchar;
    /**
     * 序号
     */
    @ApiModelProperty(value = "序号")

    @TableField("sort")
    private Integer sort;
    /**
     * 类型
     */
    @ApiModelProperty(value = "类型")

    @TableField("type")
    private Integer type;
    /**
     * 0系统级，1用户级
     */
    @ApiModelProperty(value = "0系统级，1用户级")

    @TableField("state")
    private String state;
}
