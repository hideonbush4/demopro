package com.example.demo.domain.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
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
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class User extends Model<User> {
    /**
     * 默认分配 UUID,主键类型为 String
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    // 忽略某些字段的序列化，controller层返回对象时不返回该字段 使用Jackson是使用
    @JsonIgnore
    // 使用fastJson时使用
    @JSONField(serialize = false)
    private Integer age;
    @TableField(exist = false)
    private String phone;

    public User(Long id, String name) {
        this.id = Math.toIntExact(id);
        this.name = name;
    }
}
