package com.example.demo.service.interfaces;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.domain.dto.initcomment.GenConfig;

/**
 * @author dengzhewen
 * @create 2022-02-19 11:33
 * @Version v1.0.0
 */
public interface InitCommentService {

    /**
     * 根据表明查询字段备注
     *
     * @param tableName
     * @return
     */
    JSONObject queryTableComment(String tableName);

    /**
     * 生成代码
     *
     * @param genConfig
     * @return
     */
    byte[] generalCode(GenConfig genConfig);

    byte[] generalCodeSelf(GenConfig genConfig);

}
