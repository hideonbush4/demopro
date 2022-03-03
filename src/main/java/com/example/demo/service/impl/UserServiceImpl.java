package com.example.demo.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.dao.UserMapper;
import com.example.demo.domain.entity.User;
import com.example.demo.service.interfaces.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public boolean testOwnUpdate() {
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        List<String> nameList = Arrays.asList("张三", "李四");
        updateWrapper.in(User::getName, nameList);
        String phone = null;
        // phone非空才会加上=phone的条件
        updateWrapper.eq(StrUtil.isNotEmpty(phone), User::getPhone, phone);
        updateWrapper.set(User::getAge, 123);
//        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
//        List<String> nameList = Arrays.asList("张三", "李四");
//        updateWrapper.in("name", nameList);
//        updateWrapper.set("age", 123);
        return update(updateWrapper);
    }
}
