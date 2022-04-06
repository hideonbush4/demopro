package com.example.demo.study.design.single;

import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.ChronoField;
import java.util.Date;
import java.util.TimeZone;

public class DateTest {
    public static final String FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Test
    public void test1() {
        Date date = new Date();
        System.out.println(date.getYear() + 1900); // 2022
        System.out.println(date.getMonth() + 1); // 4
        System.out.println(date.getDate()); // 6

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT);
        System.out.println(simpleDateFormat.format(date));
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        System.out.println(simpleDateFormat.format(date));

        // jdk8新增日期、时间类，线程安全
        // https://www.cnblogs.com/huanshilang/p/12013386.html
        // 获取当前日期
        LocalDate now = LocalDate.now();
        // 设置日期
        LocalDate localDate = LocalDate.of(2019, 9, 10);
        // 获取年
        int year = localDate.getYear();     //结果：2019
        int year1 = localDate.get(ChronoField.YEAR); //结果：2019
        // 获取月
        Month month = localDate.getMonth();   // 结果：SEPTEMBER
        int month1 = localDate.get(ChronoField.MONTH_OF_YEAR); //结果：9
        // 获取日
        int day = localDate.getDayOfMonth();   //结果：10
        int day1 = localDate.get(ChronoField.DAY_OF_MONTH); // 结果：10
        // 获取星期
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();   //结果：TUESDAY
        int dayOfWeek1 = localDate.get(ChronoField.DAY_OF_WEEK); //结果：2
        System.out.println(year1);
        System.out.println(month1);
        System.out.println(day1);
        System.out.println(dayOfWeek1);

        // 获取当前时间
        LocalTime now2 = LocalTime.now();
        // 设置时间
        LocalTime localTime = LocalTime.of(13, 51, 10);
        //获取小时
        int hour = localTime.getHour();    // 结果：13
        int hour1 = localTime.get(ChronoField.HOUR_OF_DAY); // 结果：13
        //获取分
        int minute = localTime.getMinute();  // 结果：51
        int minute1 = localTime.get(ChronoField.MINUTE_OF_HOUR); // 结果：51
        //获取秒
        int second = localTime.getSecond();   // 结果：10
        int second1 = localTime.get(ChronoField.SECOND_OF_MINUTE); // 结果：10

        // LocalDateTime可以设置年月日时分秒，相当于LocalDate + LocalTime
        // 获取当前日期时间
        LocalDateTime localDateTime = LocalDateTime.now();
        // 设置日期
        LocalDateTime localDateTime1 = LocalDateTime.of(2019, Month.SEPTEMBER, 10, 14, 46, 56);
        LocalDateTime localDateTime2 = LocalDateTime.of(localDate, localTime);
        LocalDateTime localDateTime3 = localDate.atTime(localTime);
        LocalDateTime localDateTime4 = localTime.atDate(localDate);
        // 获取LocalDate
        LocalDate localDate2 = localDateTime.toLocalDate();
        // 获取LocalTime
        LocalTime localTime2 = localDateTime.toLocalTime();

        // 如果只是为了获取秒数或者毫秒数，使用System.currentTimeMillis()来得更为方便
        // 创建Instant对象
        Instant instant = Instant.now();
        // 获取秒
        long currentSecond = instant.getEpochSecond();
        // 获取毫秒
        long currentMilli = instant.toEpochMilli();

        // https://www.liaoxuefeng.com/wiki/1252599548343744/1303904694304801
        ZonedDateTime zbj = ZonedDateTime.now(); // 默认时区
        ZonedDateTime zny = ZonedDateTime.now(ZoneId.of("America/New_York")); // 用指定时区获取当前时间
        System.out.println(zbj);
        System.out.println(zny);

        LocalDateTime ldt = LocalDateTime.of(2019, 9, 15, 15, 16, 17);
        ZonedDateTime zbj2 = ldt.atZone(ZoneId.systemDefault());
        ZonedDateTime zny2 = ldt.atZone(ZoneId.of("America/New_York"));
        System.out.println(zbj2);
        System.out.println(zny2);

    }
}
