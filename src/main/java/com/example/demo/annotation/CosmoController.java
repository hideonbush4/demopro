package com.example.demo.annotation;

import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@RestController
// 限制运行时有效
@Retention(RetentionPolicy.RUNTIME)
// 使用限制，使用在class,interface,enum
@Target({ElementType.TYPE})
public @interface CosmoController {

}
