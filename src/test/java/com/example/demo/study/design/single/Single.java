package com.example.demo.study.design.single;

// 饿汉式单例（缺点：如果类中有很多数据，一上来就初始化对象，会很占内存）
public class Single {
    private byte[] b1 = new byte[1024 * 1024];
    private byte[] b2 = new byte[1024 * 1024];
    private byte[] b3 = new byte[1024 * 1024];

    // 1. private static final修饰
    private static final Single single = new Single();
    // 2. 私有化构造方法
    private Single() {

    }
    // 3. 返回单例对象
    public static Single getInstance() {
        return single;
    }
}
// 懒汉式单例
class Single2 {
    // 1. volatile关键字禁止语义重排序
    private static volatile Single2 single2;
    // 2. 私有化构造方法
    private Single2() {

    }
    // 3.双重检测所机制
    public static Single2 getInstance() {
        if (single2 == null) {
            synchronized (Single2.class) {
                if (single2 == null) {
                    single2 = new Single2();
                }
            }
        }
        return single2;
    }
}

// 静态内部类
class Holder {
    private Holder() {

    }
    public static Holder getInstance() {
        return InnerClass.holder;
    }
    public static class InnerClass {
        private static final Holder holder = new Holder();
    }
}

enum Single3 {
    INSTANCE;

    public Single3 getInstance() {
        return INSTANCE;
    }
}
