package com.example.demo.service.impl;

import com.example.demo.BaseTest;
import com.example.demo.service.interfaces.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author dengzhewen
 * @create 2022-03-03 17:32
 * @Version v1.0.0
 */
// @Rollback注解value默认为true，表明对数据库的操作会回滚；不加@Rollback也会回滚数据
// 加上value = false才会保留更改数据
// @Transactional会试数据回滚
//@Transactional注解应该直接加在类上,@Rollback注解则可以加在类上也可以加在需要回滚的方法上.这样测试的事务就由spring控制,
// 而回滚则由@Rollback来指定,对于想要落库的数据,则添加@Commit注解就可以
//@Rollback(value = false)
//@Transactional
 class UserServiceImplTest extends BaseTest {
    @Autowired
    UserService userService;

    @Test
    public void testUpdate(){
        userService.testOwnUpdate();
    }

}
