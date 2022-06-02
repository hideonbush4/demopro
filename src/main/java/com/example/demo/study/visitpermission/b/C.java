package com.example.demo.study.visitpermission.b;


import com.example.demo.study.visitpermission.a.A;

public class C extends A {
    public C() {
        super();
//        a = 1; // 报错 同类才能访问
//        b = 1; // 报错 同类或者同包才能访问
        c = 1;
        d = 1;
    }
}
