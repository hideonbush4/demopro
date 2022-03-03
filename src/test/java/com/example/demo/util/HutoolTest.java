package com.example.demo.util;

import cn.hutool.core.util.StrUtil;
import com.example.demo.BaseTest;
import com.example.demo.domain.entity.User;
import com.example.demo.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class HutoolTest extends BaseTest {

    @Autowired
    UserService userService;

    @Test
    public void test2(){

        String s = "132";
        // 判断null和length==0
        boolean notEmpty = StrUtil.isNotEmpty(s);

        boolean b = StrUtil.equals("a", "A");
        boolean b1 = StrUtil.equals("a", "A", true);
        String str = "abc";
        boolean b2 = StrUtil.equalsAny(str, "ABC", "a", "bb", "cc");
        boolean b3 = StrUtil.equalsAnyIgnoreCase(str, "Abc", "aBc");


    }

    @Test
    public void test3(){
        String s = "1,2,3,4,5";
        Set<Integer> collect = Arrays.stream(s.split(",")).map(e -> Integer.valueOf(e)).collect(Collectors.toSet());
    }

    @Test
    public void test(){

        List<Integer> list = new ArrayList<>();
        list.forEach(e -> userService.removeById(e));
        userService.removeByIds(list);

        List<User> userList = new ArrayList<>();
        userList.forEach(user -> user.setAge(19));
        userList.stream().forEach(user -> user.setName("33"));
    }
}
