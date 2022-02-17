package com.example.demo.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.demo.constants.RegularExpressionConstants;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@TableName("admin")
public class Admin {

    private Integer id;

    /**
     * 详见https://blog.csdn.net/qq_40600507/article/details/105407353
     * @NotNull：不能为null，但可以为empty
     *
     * @NotEmpty：不能为null，而且长度必须大于0
     *
     * @NotBlank：只能作用在String上，不能为null，而且调用trim()后，长度必须大于0
     * 在使用@NotBlank等注解时，一定要和@valid一起使用，不然@NotBlank不起作用
     */
    @NotBlank(message = "名字不允许为空")
    /**
     * @Length(min=, max=)		验证注解的元素值长度在min和max区间内
     */
    @Length(max = 500, message = "名字最大支持输入500个字")
    private String name;

    private String password;

    /**
     * 使用Pattern注解验证某些参数
     */
    @Pattern(regexp = RegularExpressionConstants.VERIFY_EMAIL, message = "请输入正确的邮箱地址")
    private String email;

    @Pattern(regexp = RegularExpressionConstants.VERIFY_PHONE, message = "请输入正确的联系方式：手机号【11位数字】，固定电话【区号-号码】")
    private String phone;

}
