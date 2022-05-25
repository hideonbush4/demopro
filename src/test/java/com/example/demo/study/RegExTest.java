package com.example.demo.study;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExTest {

    /**
     * 模板引擎是指，定义一个字符串作为模板：
     *
     * Hello, ${name}! You are learning ${lang}!
     * 其中，以${key}表示的是变量，也就是将要被替换的内容
     *
     * 当传入一个Map<String, String>给模板后，需要把对应的key替换为Map的value。
     *
     * 例如，传入Map为：
     *
     * {
     *     "name": "Bob",
     *     "lang": "Java"
     * }
     * 然后，${name}被替换为Map对应的值"Bob”，${lang}被替换为Map对应的值"Java"，最终输出的结果为：
     *
     * Hello, Bob! You are learning Java!
     * 请编写一个简单的模板引擎，利用正则表达式实现这个功能。
     */
    @org.junit.Test
    public void test9() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "Bob");
        map.put("lang", "Java");
        Pattern p = Pattern.compile("\\$\\{(\\w+)\\}");
        Matcher matcher = p.matcher("Hello, ${name}! You are learning ${lang}!");
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, map.get(matcher.group(1)));
        }
        System.out.println(sb);
    }

    @org.junit.Test
    public void test8() {
        // 反向引用
        String s = "the quick brown fox jumps over the lazy dog.";
        String r = s.replaceAll("\\s([a-z]{4})\\s", " <b>$1</b> ");
        System.out.println(r); // the quick brown fox jumps <b>over</b> the <b>lazy</b> dog.
    }

    @org.junit.Test
    public void test7() {
        // replaceAll
        String s = "The     quick\t\t brown   fox  jumps   over the  lazy dog.";
        String r = s.replaceAll("\\s+", " ");
        System.out.println(r); // "The quick brown fox jumps over the lazy dog."
    }

    @org.junit.Test
    public void test6() {
        String[] s = "a b c".split(" ");
        String[] s1 = "a b  c".split(" ");
        for (String s2 : s) {
            System.out.println(s2);
        }
        System.out.println("=============");
        for (String r : s1) {
            System.out.println(r);
        }
        System.out.println("=============");
        String ss = "the quick brown fox jumps over the lazy dog.";
        Pattern p = Pattern.compile("\\wo\\w");
        Matcher m = p.matcher(ss);
        while (m.find()) {
            String sub = ss.substring(m.start(), m.end());
            System.out.println(sub);
        }
    }

    @org.junit.Test
    public void test5() {
        // 利用分组匹配，从字符串"23:01:59"提取时、分、秒
        Pattern compile = Pattern.compile("([0-2][0-9]):([0-5][0-9]):([0-5][0-9])");
        Matcher matcher = compile.matcher("23:01:59");
        if (matcher.matches()) {
            System.out.println(matcher.group(1));
            System.out.println(matcher.group(2));
            System.out.println(matcher.group(3));
        }
    }

    @org.junit.Test
    public void test4() {
        // 使用Pattern
        Pattern pattern = Pattern.compile("(\\d{3,4})\\-(\\d{7,8})");
        pattern.matcher("010-12345678").matches(); // true
        pattern.matcher("021-123456").matches(); // false
        pattern.matcher("022#1234567").matches(); // false
        // 获得Matcher对象:
        Matcher matcher = pattern.matcher("010-12345678");
        if (matcher.matches()) {
            String whole = matcher.group(0); // "010-12345678", 0表示匹配的整个字符串
            String area = matcher.group(1); // "010", 1表示匹配的第1个子串
            String tel = matcher.group(2); // "12345678", 2表示匹配的第2个子串
            System.out.println(area);
            System.out.println(tel);
        }
    }

    @org.junit.Test
    public void test3() {
        Pattern p = Pattern.compile("(\\d{3,4})\\-(\\d{7,8})");
        Matcher m = p.matcher("010-12345678");
        if (m.matches()) {
            String g1 = m.group(1);
            String g2 = m.group(2);
            System.out.println(g1);
            System.out.println(g2);
        } else {
            System.out.println("匹配失败!");
        }
    }

    @org.junit.Test
    public void test2() {
        String re = "(L|l)earn\\s((j|J)ava|(p|P)hp|(g|G)o)";
        System.out.println("learn java".matches(re));
        System.out.println("learn Java".matches(re));
        System.out.println("learn php".matches(re));
        System.out.println("learn Go".matches(re));
        System.out.println("===========");
        System.out.println("Learn java".matches(re));
        System.out.println("Learn Java".matches(re));
        System.out.println("Learn php".matches(re));
        System.out.println("Learn Go".matches(re));
    }

    @org.junit.Test
    public void test1() {
        String first = "\\d{3,4}";
        String mid = "-";
        String last = "\\d{7,8}";
        String re = first + mid + last;

        for (String s : Arrays.asList("010-12345678", "020-9999999", "0755-7654321")) {
            if (!s.matches(re)) {
                System.out.println("测试失败: " + s);
                return;
            }
        }
        for (String s : Arrays.asList("010 12345678", "A20-9999999", "0755-7654.321")) {
            if (s.matches(re)) {
                System.out.println("测试失败: " + s);
                return;
            }
        }
        System.out.println("测试成功!");
    }

    public static void main(String[] args) {

    }

}
