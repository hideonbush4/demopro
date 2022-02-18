package com.example.demo.enums;

import org.apache.commons.codec.binary.StringUtils;

/**
 * @author dengzhewen
 * @create 2022-02-18 14:25
 * @Version v1.0.0
 */
public enum Test {
    /**
     * 待协查
     */
    TO_ASSIST_IN("0", "待协查"),
    /**
     * 已完成
     */
    FINISH("1", "已完成"),
    /**
     * 已拒绝
     */
    REJECT_ASSIST("2", "已拒绝"),

    ;

    public String code;

    public String zhCode;

    Test(String code, String zhCode) {
        this.code = code;
        this.zhCode = zhCode;
    }

    /*public static String getZHCode(String code) {
        String zhCode = null;
        Test[] values = Test.values();
        for (Test value : values) {
            if (StringUtils.equals(value.code, code)) {
                zhCode = value.zhCode;
            }
        }
        return zhCode;
    }*/

    public static String getCode(String zhCode) {
        String code = null;
        Test[] values = Test.values();
        for (Test value : values) {
            if (StringUtils.equals(value.zhCode, zhCode)) {
                code = value.code;
            }
        }
        return code;
    }

    /**
     * 根据枚举类的code值，获取枚举类型
     *
     * @param zhCode
     * @return
     */
    public static Test getEnumByZhCode(String zhCode) {
        for (Test Test : Test.values()) {
            if (StringUtils.equals(Test.zhCode, zhCode)) {
                return Test;
            }
        }
        return null;
    }
}
