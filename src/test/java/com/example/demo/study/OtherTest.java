package com.example.demo.study;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author dengzhewen
 * @create 2022-03-07 10:03
 * @Version v1.0.0
 */
public class OtherTest {

    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        while (sc.hasNext()) {
//            // 会以回车、空格为分隔符
//            String next = sc.next();
//            System.out.println(next);
//        }
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String s = sc.nextLine();
            String[] s1 = s.split(" ");
            for (String s2 : s1) {
                System.out.println(s2);
            }
        }
    }

}
