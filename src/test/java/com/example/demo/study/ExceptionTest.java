package com.example.demo.study;

import com.example.demo.BaseTest;
import lombok.extern.slf4j.Slf4j;

/*
异常只能被捕获一次，当异常捕获出现嵌套时，只会在最内层被捕获
 */

@Slf4j
public class ExceptionTest extends BaseTest {

    public static void main(String[] args) {
        try {
            test();
//        } catch (Exception e) {
        } catch (Exception e) {
//            e.printStackTrace();
            // 只会打印ffff，test1方法不会执行
            log.info("捕获NullPointerException异常" + e.getMessage());
        }
    }

    public static void test() throws Exception {
        test2(0, 0);
        test1(0, 0);
    }

    public static void test1(int a, int b) throws Exception {
        if (b == 0) throw new Exception("0不能为除数11");
    }

    public static void test2(int a, int b) throws Exception {
        if (a == 0) throw new Exception("ffff");
    }

}
