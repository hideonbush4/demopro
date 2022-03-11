package com.example.demo.config;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * @author dengzhewen
 * @create 2022-03-11 11:27
 * @Version v1.0.0
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("com.example.demo")).paths(PathSelectors.any())
                // 过滤展示对外接口时打开 .paths(PathSelectors.regex("/external.*")) ,同时注释掉 .paths(PathSelectors.any())
                // .paths(PathSelectors.regex("/external.*"))
                .build().pathMapping("/")
                // 以字符串代替日期格式显示
                .directModelSubstitute(LocalDate.class, String.class)
                .directModelSubstitute(LocalDateTime.class, String.class).genericModelSubstitutes(ResponseEntity.class)
                .groupName("DemoTest")
                // 页面显示信息
                .apiInfo(apiInfo()).enable(true);
                // 全局token设置
//                .securityContexts(Lists.newArrayList(securityContext())).securitySchemes(Lists.newArrayList(apiKey()));
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("demo接口文档")
                .description("本文档描述了demo的接口定义")
                .version("1.0")
                .contact(new Contact("dengzhewen", "http://www.baidu.com", "1917314396@qq.com"))
                .build();
    }

//    private ApiInfo apiInfo() {
//        StringBuilder sb = new StringBuilder("<html><table border=1><tr><th>code</th><th>message</th></tr>");
//        for (CommonErrCodeMsg value : CommonErrCodeMsg.values()) {
//            sb.append("<tr><td>");
//            sb.append(value.getCode());
//            sb.append("</td><td>");
//            sb.append(value.getMsg());
//            sb.append("</td></tr>");
//        }
//        sb.append("</table></html>");
//        return new ApiInfo("EDRMS 517 API",
//                "EDRMS 517 API接口文档<br/>接口返回值code（非http的code码）<br/>" + "异常码规定 长度为7位数字<br/>"
//                        + " 0    - 00 -      0 -     000<br/>" + "终端-功能模块-错误类型-自定义code<br/>"
//                        + "第一位数字代表终端类型 1:不区分终端;  2:web端;  3:客户终端（手持端）<br/>"
//                        + "第二三为数字表示功能模块 10:档案收集; 20:档案管理; 30:档案统计;  40:档案保管;  50:档案利用;  99:默认不选模块<br/>"
//                        + "第四位表示错误类型 1:远程调用异常;  2:内部业务异常;  3:参数异常   默认为2内部业务异常<br/>" + "第五六七位 自定义code<br/>" + "例如：<br/>"
//                        + "1993000 入参校验异常<br/>" + "1202001 档案管理的业务异常<br/>" + "http状态码200成功；401认证错误；500系统错误" + sb,
//                "517.0.2", "/edrmscore/api", new Contact("档案事业部", "/doc.html", ""), "", "", new ArrayList<>());
//    }

}
