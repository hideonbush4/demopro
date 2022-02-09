package com.example.demo.domain.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("admin")
public class Admin {
    private Integer id;
    private String name;
    private String password;
}
