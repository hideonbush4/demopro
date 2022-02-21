package com.example.demo.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

//@Component
public class DataMetaObjectHandler implements MetaObjectHandler {

    /**
     * 执行insert语句时设置以下内容
     *
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        Object id = this.getFieldValByName("id", metaObject);
        if (Objects.isNull(id)) {
            this.setFieldValByName("id", UUID.randomUUID().toString(), metaObject);
        }
        this.setFieldValByName("createId", getCurrentUserId(), metaObject);
        this.setFieldValByName("updateId", getCurrentUserId(), metaObject);
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("modifiedTime", new Date(), metaObject);
    }

    /**
     * 执行update语句时设置以下内容
     *
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateId", getCurrentUserId(), metaObject);
        this.setFieldValByName("modifiedTime", new Date(), metaObject);
    }

    /**
     * 获取当前登录用户的id
     *
     * @return
     */
    private String getCurrentUserId() {
        String currentUserId = "";
//        Authorizable currentUser = Context.getCurrentUser();
//        String currentUserId = null;
//        if (currentUser != null) {
//            MacrowingUser user = (MacrowingUser)currentUser;
//            currentUserId = user.getId();
//        }
        return currentUserId;
    }
}
