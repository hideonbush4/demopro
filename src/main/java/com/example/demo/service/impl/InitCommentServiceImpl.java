package com.example.demo.service.impl;

import cn.hutool.core.io.IoUtil;
import com.example.demo.controller.initcomment.GenUtils;
import com.example.demo.dao.initcomment.InitCommentDao;
import com.example.demo.domain.dto.initcomment.GenConfig;
import com.example.demo.service.interfaces.InitCommentService;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

/**
 * @author dengzhewen
 * @create 2022-02-19 11:33
 * @Version v1.0.0
 */
@Service
public class InitCommentServiceImpl implements InitCommentService {

    @Resource
    InitCommentDao initCommentDao;

    @Override
    public JSONObject queryTableComment(String tableName) {
        com.alibaba.fastjson.JSONObject result = new com.alibaba.fastjson.JSONObject();
        List list = Lists.newArrayList(tableName);
        List<Map> formList = initCommentDao.selectFormIdByTableName(list);
        if (formList != null && formList.size() > 0) {

            List<MetaAttr> array = new ArrayList<>();
            // 存在多个表单的，取字段最多的表单
            for (int i = 0; i < formList.size(); i++) {

                MetaData metaData = V5Sdk.getMetaDataTypeByFormId(formList.get(i).get("Id").toString());
                if (metaData.getMetaAttrList() != null && metaData.getMetaAttrList().size() > array.size()) {
                    array = metaData.getMetaAttrList();
                }
            }
            // key表示column，value表示备注
            for (int i = 0; i < array.size(); i++) {
                result.put(array.get(i).getAttrId(), array.get(i).getAttrName());
            }
        } else {
            throw Throws.exception("没有对应的表单存在！", "没有对应的表单存在！");
        }
        return result;
    }

    @Override
    public byte[] generalCode(GenConfig genConfig) {
        JSONObject columnDesc = queryTableComment(genConfig.getTableName());
        columnDesc.fluentPut("Id", "主键").fluentPut("createTime", "创建时间").fluentPut("modifiedTime", "修改时间")
                .fluentPut("createId", "创建人id").fluentPut("updateId", "更新人id");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        ZipOutputStream zip = new ZipOutputStream(outputStream);
        try {
            String tableName = genConfig.getTableName();
            // 查询列信息
            List<Map<String, String>> columns = initCommentDao.queryColumns(tableName);
            columns.forEach(v -> {
                v.put("columnComment", columnDesc.getString(v.get("columnName")));
            });
            // 生成代码
            GenUtils.generatorCode(genConfig, columns, zip);
        } finally {
            IoUtil.close(zip);
        }
        return outputStream.toByteArray();
    }

    @Override
    public byte[] generalCodeSelf(GenConfig genConfig) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        ZipOutputStream zip = new ZipOutputStream(outputStream);
        try {
            String tableName = genConfig.getTableName();
            // 查询列信息
            List<Map<String, String>> columns = initCommentDao.queryColumns(tableName);
            // 生成代码
            GenUtils.generatorCode(genConfig, columns, zip);
        } finally {
            IoUtil.close(zip);
        }
        return outputStream.toByteArray();
    }
}
