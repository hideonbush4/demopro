package com.example.demo.service.impl;

import com.example.demo.BaseTest;
import com.example.demo.service.interfaces.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author dengzhewen
 * @create 2022-03-03 17:32
 * @Version v1.0.0
 */
@Rollback
@Transactional
public class UserServiceImplTest extends BaseTest {
    @Autowired
    UserService userService;

    @Test
    void testUpdate(){
        userService.testOwnUpdate();
    }

}
