package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.dao.UserMapper;
import com.example.demo.domain.entity.User;
import com.example.demo.service.interfaces.UserService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public void testOwnUpdate() {
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        List<String> nameList = Arrays.asList("张三", "李四");
        updateWrapper.in(User::getName, nameList);
        updateWrapper.set(User::getAge, 123);
        update(updateWrapper);
    }
}
