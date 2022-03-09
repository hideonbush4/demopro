package com.example.demo.domain.form;

import com.baomidou.mybatisplus.annotation.TableField;
import com.example.demo.constants.RegularExpressionConstants;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
public class UserForm {

    public interface insert{

    }

    public interface update{

    }
    // @Validated分组校验
    @NotNull(message = "修改时id不允许为空", groups = UserForm.update.class)
    private Integer id;
    @NotBlank(message = "新增时name不允许为空", groups = UserForm.insert.class)
    private String name;
    private Integer age;
    /**
     * 全宗号;唯一值
     */
    // 未指定校验分组，为默认分组
    @NotBlank(message = "全宗号不允许为空")
    @ApiModelProperty(value = "全宗号;唯一值")
    @TableField("sect_code")
    private String sectCode;
    /**
     * 序号;唯一值
     */
    @Min(value = 1, message = "序号不能小于1")
    @ApiModelProperty(value = "序号;唯一值")
    @TableField("sort")
    private Integer sort;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间", hidden = true)
    private Date createTime;
    @Pattern(regexp = RegularExpressionConstants.VERIFY_PHONE, message = "请输入正确的联系方式：手机号【11位数字】，固定电话【区号-号码】")
    private String phone;
    /**
     * 使用Pattern注解验证某些参数
     */
    @Pattern(regexp = RegularExpressionConstants.VERIFY_EMAIL, message = "请输入正确的邮箱地址")
    private String email;
}
