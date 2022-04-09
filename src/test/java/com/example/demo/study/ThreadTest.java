package com.example.demo.study;

import org.junit.jupiter.api.Test;

import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadTest {

    @Test
    public void SemaphoreTest() throws InterruptedException {
        // 线程数量，限流
        Semaphore semaphore = new Semaphore(3);
        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire(); // 得到
                    System.out.println(Thread.currentThread().getName() + " 抢到了车位");
                    // 业务逻辑
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println(Thread.currentThread().getName() + " 离开了车位");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release(); // 释放
                }
            }).start();
            
        }

        TimeUnit.SECONDS.sleep(5);
    }

    @Test
    public void CyclicBarrierTest() {
        // 加法计数器
        // 集齐7颗龙珠，召唤神龙
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7, () -> {
            System.out.println("召唤神龙成功");
        });
        for (int i = 1; i <= 7; i++) {
            final int temp = i;
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " 收集了第" + temp + " 颗龙珠");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " 召唤后后续操作");
            }, String.valueOf(i)).start();
        }

    }

    @Test
    public void CountDownLatchTest() throws InterruptedException {
        // 计数器
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                countDownLatch.countDown();
                System.out.println(Thread.currentThread().getName() + " go out");
            }, String.valueOf(i)).start();
        }
        countDownLatch.await();
        System.out.println("close door");
    }

    @Test
    public void test1() throws InterruptedException {
        System.out.println(Runtime.getRuntime().availableProcessors());
        // 睡一天
        TimeUnit.DAYS.sleep(1);
        TimeUnit.SECONDS.sleep(1);
    }

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

// Synchronized版 生产者消费者
// 等待；业务；通知
class Data {

    private int number = 0;

    // 生产
    public synchronized void add() throws InterruptedException {
        // 不能用if，防止虚假唤醒
        while (number != 0) {
            // 等待
            wait();
        }
        // 业务
        number++;
        // 通知
        notifyAll();
    }

    // 消费
    public synchronized void dec() throws InterruptedException {
        while (number == 0) {
            // 等待
            wait();
        }
        // 业务
        number--;
        // 通知
        notifyAll();
    }

}

// Lock版 生产者消费者
// 等待；业务；通知
class Data2 {

    private int number = 0;
    Lock lock = new ReentrantLock();
    // 可以精准通知和唤醒线程
    Condition condition = lock.newCondition();

    // 生产
    public void add() throws InterruptedException {
        lock.lock();
        try {
            // 不能用if，防止虚假唤醒
            while (number != 0) {
                // 等待
                condition.await();
            }
            // 业务
            number++;
            // 通知
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    // 消费
    public void dec() throws InterruptedException {
        lock.lock();
        try {
            while (number == 0) {
                // 等待
                condition.await();
            }
            // 业务
            number--;
            // 通知
            notifyAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            condition.signalAll();
        }
    }
}

// condition精准唤醒
class Data3 {

    private Lock lock = new ReentrantLock();
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();
    private int num = 1; // 1A 2B 3C

    public void printA() {
        lock.lock();
        try {
            // 业务
            while (num != 1) {
                // 等待
                condition1.await();
            }
            // 精准唤醒
            num = 2;
            condition2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printB() {
        lock.lock();
        try {
            // 业务
            while (num != 2) {
                // 等待
                condition2.await();
            }
            num = 3;
            condition3.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printC() {
        lock.lock();
        try {
            // 业务
            while (num != 3) {
                // 等待
                condition3.await();
            }
            num = 1;
            condition1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}
