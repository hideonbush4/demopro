package com.example.demo.study.fourp.a;

public class A {
    private int a; // 同类可以访问
    int b; // 同类、同包
    protected int c; // 同类、同包、不同包子类
    public int d; // 所有

    public A() {
        this.a = 1;
        this.b = 2;
        this.c = 3;
        this.d = 4;
    }

    // protected常用于父类定义模板方法，供子类实现自己的逻辑
    protected int cal() {
        return c++;
    }

    public void test1() {
        System.out.println(a + " " + b + " " + c + " " + d);
    }

    public static void main(String[] args) {
        B b = new B();
        b.test2();
    }
}

class B extends A {
    public B() {
        super();
//        a = 1; // 报错  同类才能访问
        b = 1;
        c = 1;
        d = 1;

        c = cal();
    }

    public void test2() {
        test1();
    }


}
