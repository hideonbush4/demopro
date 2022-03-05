package com.example.demo.study;

import org.junit.jupiter.api.Test;

import java.util.Optional;

 public class OptionalTest {

     @Test
     public void test2(){
         // 支持过滤操作和映射操作
         String str = "A";
         Optional<String> optional = Optional.ofNullable(str);   //转换为Optional（可空）
         System.out.println(optional.filter(s -> s.equals("B")).get());   //被过滤了，此时元素为null，获取时报错
     }

    @Test
    public void test1(){
        String str = null;
        Optional<String> optional = Optional.ofNullable(str);   //转换为Optional
        optional.ifPresent(System.out::println);  //当存在时再执行方法
        System.out.println(optional.orElse("abc"));
    }

}
