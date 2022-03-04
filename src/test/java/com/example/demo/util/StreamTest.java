package com.example.demo.util;

import com.example.demo.BaseTest;
import com.example.demo.domain.entity.User;
import com.example.demo.service.interfaces.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author dengzhewen
 * @create 2022-03-04 9:37
 * @Version v1.0.0
 */
class StreamTest extends BaseTest {

    @Autowired
    UserService userService;

    @Test
    public void test2(){
        List<User> list = userService.list();

        // 从小到大排序
        list.sort((o1, o2) -> o1.getAge().compareTo(o2.getAge()));

        // 同上
        list.sort(Comparator.comparingInt(user -> user.getAge()));
        list.sort(Comparator.comparingInt(User::getAge));

        // 进行相反的排序
        list.sort(Comparator.comparingInt(User::getAge).reversed());

        // 从大到小排序
        list.sort(Comparator.comparing(
                User::getAge, (s1, s2) -> {
                    return s2.compareTo(s1);
                }
        ));
    }

    @Test
    public void test1(){

        List<User> list = userService.list();
        // 使用Collectors.groupingBy返回的map的key是name，value是list<User>
        Map<String, List<User>> collect = list.stream().collect(Collectors.groupingBy(User::getName));
        System.out.println("---");

    }

}
