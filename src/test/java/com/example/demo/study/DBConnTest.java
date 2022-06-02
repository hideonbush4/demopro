package com.example.demo.study;

import com.example.demo.study.jdbc.DBUtils;
import org.junit.Test;

public class DBConnTest {
    @Test
    public void test1() {
        System.out.println(DBUtils.getConnection());
    }
}