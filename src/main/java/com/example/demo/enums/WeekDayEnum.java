package com.example.demo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum WeekDayEnum {
    MONDAY("1", "一"),
    TUESDAY("2", "二"),
    WEDNESDAY("3", "三"),
    THURSDAY("4", "四"),
    FRIDAY("5", "五");

    String num;
    String desc;


    public static WeekDayEnum getEnum(String num) {

        for (WeekDayEnum value : values()) {
            if (value.getNum().equals(num)) {
                return value;
            }
        }
        return null;
    }

    public static WeekDayEnum getEnmu2(String num) {
        return Stream.of(values()).filter(e -> e.getNum().equals(num)).findAny().orElse(null);
    }
}
