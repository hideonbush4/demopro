package com.example.demo.controller.initcomment;

import cn.hutool.core.io.IoUtil;
import com.example.demo.domain.dto.initcomment.GenConfig;
import com.example.demo.service.interfaces.InitCommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author dengzhewen
 * @create 2022-02-19 11:31
 * @Version v1.0.0
 */
@RestController
@Api
@RequestMapping(value = "/init")
@Slf4j
public class InitDbCommentController {

    @Resource
    InitCommentService iinitCommentService;

    @ApiOperation(value = "生成带swagger注释的实体类", notes = "")
    @GetMapping(value = "/generalEntity")
    public void generalEntity(GenConfig genConfig, HttpServletResponse response) {
//        Throws.ifBlank(genConfig.getTableName(), "tableName不能为空");
//        Throws.ifBlank(genConfig.getTableComment(), "tableComment不能为空");
        byte[] data = iinitCommentService.generalCode(genConfig);
        response.reset();
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                String.format("attachment; filename=%s.zip", genConfig.getTableName()));
        response.addHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(data.length));
        response.setContentType("application/octet-stream; charset=UTF-8");

        try {
            IoUtil.write(response.getOutputStream(), Boolean.TRUE, data);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

    }

}
