package com.example.demo.controller;

import com.example.demo.domain.pojo.Admin;
import com.example.demo.dao.AdminMapper;
import com.example.demo.dao.UserMapper;
import com.example.demo.domain.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/home")
public class UserController {
    @Autowired
    UserMapper userMapper;
    @Autowired
    AdminMapper adminMapper;
    @RequestMapping("/test1")
    public String test1(){
        return "@RestController注解相当于@ResponseBody ＋ @Controller";
    }

    @RequestMapping("/test2")
    public User test2(){
        User user = new User();
        user.setId(1).setName("张三").setAge(18).setPhone("123456789");
        return user;
    }

    @RequestMapping("/test3")
    public List<User> test3(){
        List<User> users = userMapper.selectList(null);
        return users;
    }

    @RequestMapping("/test4")
    public List<Admin> test4(){
        List<Admin> users = adminMapper.selectList(null);
        return users;
    }
}
