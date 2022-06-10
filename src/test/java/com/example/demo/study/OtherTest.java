package com.example.demo.study;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OtherTest {

    @Test
    public void test3() {
//        BlockingQueue<String> abq = new ArrayBlockingQueue<>(3);
//        abq.add("a");
//        abq.remove();

        ExecutorService executorService = Executors.newFixedThreadPool(5); // 固定线程
//        ExecutorService executorService = Executors.newSingleThreadExecutor(); // 单个线程
//        ExecutorService executorService = Executors.newCachedThreadPool(); // 伸缩线程
        try {
            for (int i = 0; i < 100; i++) {
                executorService.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + " ---> ");
                });
            }
        } finally {
            executorService.shutdown();
        }
    }

    private byte[] bytes = new byte[64 * 1024];

    public static void main(String[] args) throws Exception {
        System.in.read();
        List<OtherTest> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            Thread.sleep(100);
            list.add(new OtherTest());
        }
        System.gc();
        LinkedList linkedList = new LinkedList();
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
        System.getProperties().forEach((k, v) -> {
            System.out.println(k + " : " + v);
        });
    }

    @Test
    public void test2() {
        System.out.println(returnTryExec());
        System.out.println(returnCatchExec());
    }

    public static int returnTryExec() {
        try {
            return 0;
        } catch (Exception e) {
        } finally {
            System.out.println("finally returnTryExec");
            return -1;
        }
    }

    public static int returnCatchExec() {
        try { } catch (Exception e) {
            return 0;
        } finally {
            System.out.println("finally returnCatchExec");
            return -1;
        }
    }

    public static void exitTryExec() {
        try {
            System.exit(0);
        } catch (Exception e) {
        } finally {
            System.out.println("finally exitTryExec");
        }
    }

    public static void exitCatchExec() {
        try { } catch (Exception e) {
            System.exit(0);
        } finally {
            System.out.println("finally exitCatchExec");
        }
    }

}
