package com.example.demo.domain.dto.initcomment;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author dengzhewen
 * @create 2022-02-19 11:05
 * @Version v1.0.0
 */
@Data
public class GenConfig {
    /**
     * 模块名
     */
    @ApiModelProperty(value = "模块名")
    String moduleName;
    @ApiModelProperty(value = "作者")
    String author;
    @NotBlank(message = "tableName不能为空")
    @ApiModelProperty(value = "表名")
    String tableName;
    @NotBlank(message = "tableComment不能为空")
    @ApiModelProperty(value = "表名描述")
    String tableComment;
}
