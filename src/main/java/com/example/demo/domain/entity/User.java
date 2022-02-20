package com.example.demo.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * entity和数据库表对应
 * vo和前端页面对应
 * dto转换entity和vo，后端中使用
 */

@Data
//链式编程
@Accessors(chain = true)
@TableName("user1")
public class User {
    @TableId()
    private Integer id;
    private String name;
    private Integer age;
    @TableField(exist = false)
    private String phone;
}
