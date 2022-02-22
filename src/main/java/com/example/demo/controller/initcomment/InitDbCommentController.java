package com.example.demo.controller.initcomment;

import cn.hutool.core.io.IoUtil;
import com.example.demo.domain.dto.initcomment.GenConfig;
import com.example.demo.service.interfaces.InitCommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContextException;
import org.springframework.http.HttpHeaders;
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

/**

 CREATE TABLE `gen_code_test` (
 `id` bigint(19) NOT NULL COMMENT 'id主键',
 `create_id` varchar(64) NOT NULL COMMENT '创建人id',
 `update_id` varchar(64) NOT NULL COMMENT '修改人id',
 `create_time` datetime NOT NULL COMMENT '创建时间',
 `modified_time` datetime NOT NULL COMMENT '修改时间',
 `sort` int(8) DEFAULT '1' COMMENT '序号',
 `type` tinyint(4) DEFAULT NULL COMMENT '类型',
 `state` char(1) DEFAULT NULL COMMENT '0系统级，1用户级',
 PRIMARY KEY (`id`),
 KEY `sort` (`sort`),
 KEY `type` (`type`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='代码生成测试表';

 一个位就代表一个0或1，位用小写b来表示bit
 8个位组成一个字节; 一般字节用大写B来表示Byte ，1B=8b 1KB=1024B 1MB=1024KB

 括号中的数字不代表占用空间容量。而代表最小显示位数（而且只有设置了zerofill用0来填充，最小显示位数才生效）

 类型	字节	范围（有符号）	范围（无符号）
 TINYINT	1	(-128,127)	(0,255)
 SMALLINT	2	（-32768,32767）	(0,65535)
 MEDIUMINT	3	(-8388608,8388607)	(0,16777215)
 INT	4	(-2147483648,2147483647)	(0,4294967295)
 BIGINT	8	(-9223372036854775808,9223372036854775807)	(0,18446744073709551615)

 */

@RestController
@Api
@RequestMapping(value = "/init")
@Slf4j
public class InitDbCommentController {

    @Resource
    InitCommentService iinitCommentService;

    @ApiOperation(value = "生成表对应的实体类", notes = "")
    @GetMapping(value = "/generalEntity")
    public void generalEntity(GenConfig genConfig, HttpServletResponse response) {
//        Throws.ifBlank(genConfig.getTableName(), "tableName不能为空");
//        Throws.ifBlank(genConfig.getTableComment(), "tableComment不能为空");
        /**
         * isEmpty和isBlank不同的地方, isEmpty认为空格(无论单空格还是多空格)都是不是空. 而isBlank 认为空格,换行符号(\n),tab(\t)都是空.
         * isNoneBlank 和isNotBlank 在同一个参数的情况下, 含义是一样的, 但是isNoneBlank 的参数, 可以是一个字符数组, 当isNoneBlank 的参数是一个数组的时候, 就是说有一个参数为null, 那么返回都会返回false.
         * 一般情况下 isNoneBlank 用于校验表单的参数是否为空. 如果有一个为空, 都不满足条件.
         * 和isNoneBlank 相反的isAnyBlank , 一般情况下, 如果认为请求的每个参数都不能为null的话, 可以
         */
        if (StringUtils.isBlank(genConfig.getTableName())){
            throw new ApplicationContextException("tableName不能为空");
        }
        if (StringUtils.isBlank(genConfig.getTableComment())){
            throw new ApplicationContextException("tableComment不能为空");
        }
        byte[] data = iinitCommentService.generalCodeSelf(genConfig);
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
