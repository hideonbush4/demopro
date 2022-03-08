package com.example.demo.arch.dynamic;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author wangchao
 */
@Service
@Slf4j
public class EdrmsArchFileServiceImpl extends AbstractEdrmsBaseDynamicService<EdrmsFileDynamic> {

    @Override
    public List<EdrmsFileDynamic> select(Long categoryId, Map<String, Object> params) {
        return super.select(categoryId, params);
    }

    @Override
    public String getEdrmsCategorySubType() {
//        return EdrmsArchCategory.CategorySubType.FILE.getCode();
        return null;
    }

    public Boolean addArchArch(EdrmsFileDynamic fileDynamic) {
        try {
            Date currentDate = new Date();
            fileDynamic.setId(IdWorker.getId());
            fileDynamic.setCreateId(getCurrentUserId());
            fileDynamic.setUpdateId(getCurrentUserId());
            fileDynamic.setCreateTime(currentDate);
            fileDynamic.setModifiedTime(currentDate);
            EdrmsFileDynamic generate = fileDynamic.generate();
            BeanMap beanMap = BeanMap.create(generate);
//            boolean check = AbstractValidArch.check(beanMap, generate.getFormId());
            boolean check = true;
            if (check) {
                super.insert(fileDynamic.getArchCategoryId(), generate);
            }
        } catch (Exception e) {
            log.error("保存档案失败", e);
//            throw new BusinessException.Builder().codeMsg(CommonErrCodeMsg.DYNAMIC_ERR.msg("保存档案失败")).build();
        }
        return true;
    }

    public boolean updateArch(EdrmsFileDynamic fileDynamic) {
        fileDynamic.setUpdateId(getCurrentUserId());
        fileDynamic.setModifiedTime(new Date());
        EdrmsFileDynamic generate = null;
        try {
            generate = fileDynamic.generate();
        } catch (InvocationTargetException | IllegalAccessException e) {
            log.info("类型转换失败", e);
//            throw new BusinessException.Builder().build();
        }
        BeanMap beanMap = BeanMap.create(generate);
        // boolean check = AbstractValidArch.check(beanMap, generate.getFormId());
        if (true) {
            return super.update(fileDynamic.getArchCategoryId(), fileDynamic.getId(), generate);
        }
        return false;
    }

    private String getCurrentUserId() {
//        Authorizable currentUser = Context.getCurrentUser();
//        String userId = "";
//        if (ObjectUtil.isNotNull(currentUser)) {
//            MacrowingUser user = (MacrowingUser)currentUser;
//            userId = user.getId();
//        }
//        return userId;
        return null;
    }
}
