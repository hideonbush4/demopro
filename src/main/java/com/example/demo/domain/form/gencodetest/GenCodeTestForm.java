
package com.example.demo.domain.form.gencodetest;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 代码生成测试表
 *
 * @author dengzhewen
 * @date 2022-03-02 20:07:04
 */
@Data
@ApiModel(value = "代码生成测试表Form")
public class GenCodeTestForm {
    private static final long serialVersionUID = 1L;

    public interface UpdateGenCodeTestValidate {

    }

    public interface AddGenCodeTestValidate {

    }


    /**
     * id主键
     */
    @NotNull(groups = UpdateGenCodeTestValidate.class, message = "id不能为空")
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "id主键")
    private Long id;


    /**
     * 测试varchar
     */
    @ApiModelProperty(value = "测试varchar")
    private String testVarchar;


    /**
     * 序号
     */
    @ApiModelProperty(value = "序号")
    private Integer sort;


    /**
     * 类型
     */
    @ApiModelProperty(value = "类型")
    private Integer type;


    /**
     * 0系统级，1用户级
     */
    @ApiModelProperty(value = "0系统级，1用户级")
    private String state;
}
