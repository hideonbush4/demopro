package com.example.demo.jedisdemo;

import org.testng.annotations.Test;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;

public class JedisDemo1 {
    public static void main(String[] args) {
        // 创建Jedis对象
        Jedis jedis = new Jedis("120.55.98.1",6379);
        String ping = jedis.ping();
        System.out.println(ping);
        jedis.close();
    }
    @Test
    public void demo1(){
        // 创建Jedis对象
        Jedis jedis = new Jedis("120.55.98.1",6379);
        // 设置密码
        jedis.auth("dzw431121");
        // 设置key-value
        jedis.set("name","lucky");
        // 获取key-value
        String name = jedis.get("name");
        System.out.println(name);

        // 设置多个key-value
        jedis.mset("key1", "value1", "key2", "value2", "key3", "value3", "key3", "value4");
        List<String> mget = jedis.mget("key1", "key2", "key3");
        System.out.println(mget);

        Set<String> keys = jedis.keys("*");
        for (String key : keys) {
            System.out.println(key);
        }
        jedis.close();

    }

    // 操作list
    @Test
    public void demo2() {
        // 创建Jedis对象
        Jedis jedis = new Jedis("120.55.98.1",6379);
        // 设置密码
        jedis.auth("dzw431121");
        jedis.lpush("key22", "lucy", "mary", "jack");
        // 0,-1表示取所有值
        List<String> value = jedis.lrange("key22", 0, -1);
        System.out.println(value);
        jedis.close();

    }

    // 操作set
    @Test
    public void demo3() {
        // 创建Jedis对象
        Jedis jedis = new Jedis("120.55.98.1",6379);
        // 设置密码
        jedis.auth("dzw431121");
        jedis.sadd("name33", "lucy", "jack");
        Set<String> name = jedis.smembers("name33");
        System.out.println(name);
        jedis.close();

    }

    // 操作hash
    @Test
    public void demo4() {
        // 创建Jedis对象
        Jedis jedis = new Jedis("120.55.98.1",6379);
        // 设置密码
        jedis.auth("dzw431121");

        jedis.hset("users","age","20");
        String hget = jedis.hget("users", "age");
        System.out.println(hget);
        jedis.close();

    }

    // 操作zset
    @Test
    public void demo5() {
        // 创建Jedis对象
        Jedis jedis = new Jedis("120.55.98.1",6379);
        // 设置密码
        jedis.auth("dzw431121");

        jedis.zadd("china",100d,"beijing");
        jedis.zadd("china",200,"usa");
        jedis.zadd("china",300,"uk");
//        Set<String> china = jedis.zrange("china", 0, -1);
        Set<String> china = jedis.zrevrange("china", 0, -1);
        System.out.println(china);
        jedis.close();



    }

}
