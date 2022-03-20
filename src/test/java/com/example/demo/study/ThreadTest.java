package com.example.demo.study;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadTest {

    public static final int COUNT = 10000;
    private static int value = 0;

    public static void main(String[] args) throws Exception{
        ThreadTest threadTest1 = new ThreadTest();
        ThreadTest threadTest2 = new ThreadTest();
        Thread thread1 = new Thread(() -> {
            synchronized (threadTest1) {
                for (int i = 0; i < COUNT; i++) {
                    value++;
                }
            }
            System.out.println("线程1完成！");
        });
        Thread thread2 = new Thread(() -> {
            synchronized (threadTest1) {
                for (int i = 0; i < COUNT; i++) {
                    value++;
                }
            }
            System.out.println("线程2完成！");
        });
        thread1.start();
        thread2.start();
        Thread.sleep(1000);
        System.out.println(value);
    }

    public static void main1(String[] args) throws ExecutionException, InterruptedException {
        // 方式1：重写Thread#run()
        Thread thread = new Thread() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "========>正在执行");
            }
        };
        thread.start();

        // 方式2：构造方法传入Runnable实例
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "========>正在执行");
        }).start();

        // 方式3：线程池 + Callable/Runnable，这里以Callable为例
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<String> submit = executorService.submit(() -> {
            System.out.println(Thread.currentThread().getName() + "========>正在执行");
            Thread.sleep(3 * 1000L);
            return "success";
        });
        String result = submit.get();
        System.out.println("result=======>" + result);
        // 关闭线程池
        executorService.shutdown();
    }
}
