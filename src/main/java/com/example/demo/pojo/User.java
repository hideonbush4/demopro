package com.example.demo.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
//链式编程
@Accessors(chain = true)
@TableName("user1")
public class User {
    private Integer id;
    private String name;
    private Integer age;
    @TableField(exist = false)
    private String phone;
}
