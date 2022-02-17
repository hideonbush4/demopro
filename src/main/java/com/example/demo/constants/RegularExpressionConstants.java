package com.example.demo.constants;

/**
 * @author dengzhewen
 * @create 2022-02-14 13:52
 * @Version v1.0.0
 */
public class RegularExpressionConstants {
    /**
     * 验证手机号
     */
    public static final String VERIFY_PHONE =
            "^((\\+?86)|(\\(\\+86\\)))?((13[0-9])|(14[0,1,4-9])|(15[0-3,5-9])|(16[2,5,6,7])|(17[0-8])|(18[0-9])|(19[0-3,5-9]))\\d{8}$|^0\\d{2,3}-?\\d{7,8}$";

    /**
     * 验证邮箱地址
     */
    public static final String VERIFY_EMAIL =
            "^$|^([a-z0-9A-Z]+[-|\\.|_]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
}
