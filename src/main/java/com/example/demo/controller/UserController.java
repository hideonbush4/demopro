package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.domain.ResultPage;
import com.example.demo.domain.entity.User;
import com.example.demo.domain.form.UserForm;
import com.example.demo.domain.form.UserFormTest;
import com.example.demo.service.interfaces.AdminService;
import com.example.demo.service.interfaces.UserService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    // controller册不能注入mapper
//    @Autowired(required = true)
//    UserMapper userMapper;
//    @Autowired
//    AdminMapper adminMapper;
    @Autowired
    UserService userService;
    @Autowired
    AdminService adminService;

    @RequestMapping(value = "/testupdate", method = RequestMethod.GET)
    public Object testUpdate(){
        return userService.testOwnUpdate();
    }

    public void serviceTest() {
        User user = new User();
        // 插入
        boolean save = userService.save(user);

        // 批量插入
        List<User> list = new ArrayList<>();
        boolean b1 = userService.saveBatch(list);

        // 分页条件查询 lamada
        Page<User> page = new Page<>();
        page.setCurrent(1);
        page.setSize(10);
        Page<User> resultPage = userService.page(page, Wrappers.lambdaQuery(User.class).eq(User::getId, "123"));
//        Page<User> resultPage1 = userService.page(page, Wrappers.query(user));

        // 通过id查询
        User userById = userService.getById(1);
        User user2 = new User();
        List<User> list1 = userService.list(Wrappers.query(user2));

        // 修改
        User user1 = userById.setAge(11);
        boolean b = userService.updateById(userById);

        // 通过id删除
        boolean b2 = userService.removeById(2);

    }

    public void mapperTest() {

        // 根据条件查询list
//        User user = new User();
//        List<User> list = userMapper.selectList(Wrappers.query(user));
//
//        // 插入
//        int insert = userMapper.insert(user);
//
//        // 删除
//        int i = userMapper.deleteById(1);
//
//        int delete = userMapper.delete(Wrappers.lambdaQuery(User.class).eq(User::getAge, "123"));
//
//        // 修改
//        int i1 = userMapper.updateById(user);
//        int update = userMapper.update(user, Wrappers.query());
//
//        // 查询
//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("age", 18);
//        userMapper.selectOne(queryWrapper);
//        Page<User> page = new Page<>();
//        Page<User> userPage = userMapper.selectPage(page, Wrappers.lambdaQuery(User.class).eq(User::getPhone, "123"));


    }

    /**
     * 分页查询
     *
     * @param page
     * @param userForm
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/pagelist")
    // @Validated未指定group，不会校验update和insert组
    public Page<User> getUserPage(Page page,@Validated UserForm userForm) {
        User user = new User();
        BeanUtils.copyProperties(userForm, user);
        return userService.page(page, Wrappers.query(user));
    }

    /**
     * 分页查询-2
     *
     * @param page
     * @param userForm
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/pagelist2")
    public Page<User> getUserPage2(Page<User> page, UserForm userForm, @RequestParam(defaultValue = "ASC") String sortValue,
                                   @RequestParam(defaultValue = "1") int currentPage,
                                   @RequestParam(defaultValue = "10") int pageSize) {
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
     *
     * @param page
     * @param userForm
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/ResultPagelist")
    public ResultPage<User> getUserResultPage(Page<User> page, UserForm userForm) {
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
     *
     * @param userForm
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<User> getUserList(@Param("userForm") UserForm userForm, @Param("userFormTest") UserFormTest userFormTest) {
        User user = new User();
        BeanUtils.copyProperties(userForm, user);
        return userService.list(Wrappers.query(user));
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User getUserById(@PathVariable("id") @NotNull(message = "id不能为空") Integer id) {
        return userService.getById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    // 指定校验组为insert组合默认组
    public User save(@RequestBody @Validated(value = {UserForm.insert.class, Default.class}) UserForm userForm) {
        User user = new User();
        BeanUtils.copyProperties(userForm, user);
        userService.save(user);
        return user;
    }

    @RequestMapping(method = RequestMethod.PUT)
    // 指定校验组为update组合默认组
    public boolean updateById(@RequestBody @Validated(value = {UserForm.update.class, Default.class}) UserForm userForm) {
        User user = new User();
        BeanUtils.copyProperties(userForm, user);
        boolean b = userService.updateById(user);
        return b;
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public boolean deleteByIds(@RequestBody List<Integer> idList) {
        return userService.removeByIds(idList);
    }

    @RequestMapping(value = "/test1", method = RequestMethod.GET)
    public String test1() {
        return "@RestController注解相当于@ResponseBody ＋ @Controller";
    }

    @RequestMapping(value = "/test2", method = RequestMethod.GET)
    public User test2() {
        User user = new User();
        user.setId(1).setName("张三").setAge(18).setPhone("123456789");
        return user;
    }

    //    @RequestMapping("/list")
//    public List<User> test3(){
//        List<User> users = userMapper.selectList(null);
//        return users;
//    }
//
//    @RequestMapping("/test4")
//    public List<Admin> test4(){
//        List<Admin> users = adminMapper.selectList(null);
//        return users;
//    }
//
//    @RequestMapping(value = "/test5", method = RequestMethod.GET)
//    public List<Map<String, Object>> test5(){
//        return adminMapper.selectDiy();
//    }
}
