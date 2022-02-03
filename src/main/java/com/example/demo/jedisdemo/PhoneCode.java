package com.example.demo.jedisdemo;

import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;

import java.util.Random;

public class PhoneCode {
    public static void main(String[] args) {

        if(verifyCode("98765432111",getCode())){
            System.out.println("验证码发送成功");
        }

//        if (getRedisCode("98765432111", "281403")) {
//            System.out.println("校验成功");
//        }else {
//            System.out.println("校验失败，验证码可能已过期");
//        }

    }

    /**
     * 验证码校验
     * @param phone
     * @param code
     * @return
     */
    public static boolean getRedisCode(String phone, String code){
        Jedis jedis = new Jedis("120.55.98.1",6379);
        jedis.auth("dzw431121");

        String codeKey = "Verify" + phone + ":code";
        String redisCode = jedis.get(codeKey);
        jedis.close();
        if(code.equals(redisCode)){
            return true;
        }
        return false;

    }

    /**
     * 每个手机号每天只能输入3次，验证码放到redis中，设置过期时间2分钟
     * @param phone
     * @param code
     * @return
     */
    public static boolean verifyCode(String phone, String code){
        Jedis jedis = new Jedis("120.55.98.1",6379);
        jedis.auth("dzw431121");

        // 验证码key
        String codeKey = "Verify" + phone + ":code";
        // 次数key
        String countKey = "Verify" + phone + ":count";

        String count = jedis.get(countKey);
        if(count == null){
            // 没有发送过验证码，是指发送次数是1
            jedis.setex(countKey, 24*60*60, "1");
        }else if(Integer.parseInt(count) <= 2){
            // 发送次数小于3
            jedis.incr(countKey);
        }else if(Integer.parseInt(count) >= 3) {
            // 大于等于3
            System.out.println(String.format("该手机号%s今天已经发送3次验证码了",phone));
            jedis.close();
            return false;
        }

        jedis.setex(codeKey,120,code);
        jedis.close();
        return true;
    }

    /**
     * 生成6位验证码
     * @return
     */
    public static String getCode(){
        Random random = new Random();
        int i = random.nextInt(1000000);
        return String.format("%06d",i);
    }
}
