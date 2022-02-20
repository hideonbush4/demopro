package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.dao.AdminMapper;
import com.example.demo.dao.UserMapper;
import com.example.demo.domain.ResultPage;
import com.example.demo.domain.entity.Admin;
import com.example.demo.domain.entity.User;
import com.example.demo.domain.form.UserForm;
import com.example.demo.domain.form.UserFormTest;
import com.example.demo.service.interfaces.UserService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.ibatis.annotations.Param;
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
     * 分页查询-2
     * @param page
     * @param userForm
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "/pagelist2")
    public Page<User> getUserPage2(Page<User> page, UserForm userForm){
        User user = new User();
        BeanUtils.copyProperties(userForm, user);
//        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
//        userQueryWrapper.gt("id",user.getId());
//        Page page1 = userService.page(page, userQueryWrapper);

        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        String name = user.getName();
        userLambdaQueryWrapper.likeRight(ObjectUtils.isNotEmpty(name), User::getName, name);
        Page<User> page1 = userService.page(page, userLambdaQueryWrapper);

        return page1;
    }

    /**
     * 分页查询-使用ResultPage
     * @param page
     * @param userForm
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "/ResultPagelist")
    public ResultPage<User> getUserResultPage(Page<User> page, UserForm userForm){
        boolean searchCount = page.isSearchCount();
        User user = new User();
        BeanUtils.copyProperties(userForm, user);

        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        String name = user.getName();
        userLambdaQueryWrapper.likeRight(ObjectUtils.isNotEmpty(name), User::getName, name);
        Page<User> page1 = userService.page(page, userLambdaQueryWrapper);

        return new ResultPage<>((int) page.getCurrent(), (int) page.getSize(), page1.getTotal(), page1.getRecords());
    }

    /**
     * 条件查询
     * @param userForm
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<User> getUserList(@Param("userForm") UserForm userForm, @Param("userFormTest") UserFormTest userFormTest){
        User user = new User();
        BeanUtils.copyProperties(userForm, user);
        return userService.list(Wrappers.query(user));
    }

    /**
     * 通过id查询
     * @param id
     * @return
     */
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

    @RequestMapping(method = RequestMethod.PUT)
    public boolean updateById(@RequestBody UserForm userForm){
        User user = new User();
        BeanUtils.copyProperties(userForm, user);
        boolean b = userService.updateById(user);
        return b;
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public boolean deleteByIds(@RequestBody List<Integer> idList){
        return userService.removeByIds(idList);
    }

    @RequestMapping("/test4")
    public List<Admin> test4(){
        List<Admin> users = adminMapper.selectList(null);
        return users;
    }
}
