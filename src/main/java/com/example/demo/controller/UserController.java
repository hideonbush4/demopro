package com.example.demo.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.dao.AdminMapper;
import com.example.demo.dao.UserMapper;
import com.example.demo.domain.entity.Admin;
import com.example.demo.domain.entity.User;
import com.example.demo.domain.form.UserForm;
import com.example.demo.service.interfaces.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserMapper userMapper;
    @Autowired
    AdminMapper adminMapper;
    @Resource
    UserService userService;
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

//    @RequestMapping("/list")
    public List<User> test3(){
        List<User> users = userMapper.selectList(null);
        return users;
    }

    /**
     * 分页查询
     * @param page
     * @param userForm
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "/pagelist")
    public Page<User> getUserPage(Page page, UserForm userForm){
        User user = new User();
        BeanUtils.copyProperties(userForm, user);
        return userService.page(page, Wrappers.query(user));
    }

    /**
     * 条件查询
     * @param userForm
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<User> getUserList(@RequestBody UserForm userForm){
        User user = new User();
        BeanUtils.copyProperties(userForm, user);
        return userService.list(Wrappers.query(user));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User getUserById(@PathVariable("id") Integer id){
        return userService.getById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public User save(@RequestBody UserForm userForm){
        User user = new User();
        BeanUtils.copyProperties(userForm, user);
        userService.save(user);
        return user;
    }

    @RequestMapping("/test4")
    public List<Admin> test4(){
        List<Admin> users = adminMapper.selectList(null);
        return users;
    }
}
