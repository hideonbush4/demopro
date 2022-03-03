package com.example.demo;

import org.junit.After;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: snow_grains
 * @Date: 2021/11/2
 * @Description: 基础测试类，所有新建的测试类继承与它
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class BaseTest {

    @BeforeEach
    public void setDefaultUser() {
//        MacrowingUser defaultUser = new MacrowingUser();
//        defaultUser.setAccount("admin");
//        defaultUser.setName("Administrator");
//        defaultUser.setId("90eb0c6350ce4284828e633304decc01");
//        Context context = SpringUtil.getBean(Context.class);
//        @SuppressWarnings("unchecked")
//        UserContext<MacrowingUser> userContext = SpringUtil.getBean(UserContext.class);
//        userContext.setCurrent(defaultUser);
//        context.setUserContext(userContext);
    }

    @AfterEach
    public void test2(){

    }

    @Test
    public void name() {

        /*String formId = V5Sdk.copyForm("220223100423733_edrms", "我是复制出来的" + new Date(), "eform_edrms_test217", null, null, null);
        System.out.println("formId = " + formId);*/


    }
}
