package com.example.demo.study;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class OtherTest {

    private byte[] bytes = new byte[64 * 1024];

    public static void main(String[] args) throws Exception {
        System.in.read();
        List<OtherTest> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            Thread.sleep(100);
            list.add(new OtherTest());
        }
        System.gc();
    }

    public static final String ABC = "222";
    public static String ab = "ff";
    public final String cc = "44";
    @Test
    public void test1(){
        //        OtherTest.ABC = "ff";// 常量不可再次赋值
//        OtherTest.ab = "tt";// 类名.变量获取静态变量
//        OtherTest otherTest = new OtherTest();
//        otherTest.cc = "55";// 常量不可再次赋值，并且不能通过类名.变量获取常量
        
    }
}
