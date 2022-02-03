package com.example.demo.redis;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redisTest")
public class RedisTestController {
    @Autowired
    RedisTemplate redisTemplate;

    @GetMapping("/test1")
    public String testRedis(){
        // 设置值到redis中
        redisTemplate.opsForValue().set("name","jack");

        // 获取redis中的值
        Object name = redisTemplate.opsForValue().get("name");
        return (String) name;
    }
}
