package com.example.demo.study;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

public class JsonTest {
    @Test
    public void test1() {
        String str1 = "{\"a\":\"b\",\"t\":[{\"tt\":\"t\"},{\"rr\":\"r\"}]}";
        String str2 = "{\"a\":\"b\",\"t\":[{\"rr\":\"r\"},{\"tt\":\"t\"}]}";
        String str3 = "{\"t\":[{\"rr\":\"r\"},{\"tt\":\"t\"}],\"a\":\"b\"}";
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = (JsonObject) parser.parse(str1);
        JsonParser parser2 = new JsonParser();
        JsonObject jsonObject2 = (JsonObject) parser2.parse(str2);
        System.out.println(jsonObject2.equals(jsonObject));

        System.out.println(JSONObject.parse(str2).equals(JSONObject.parse(str1)));

        System.out.println(JsonSameUtil.same(str1, str2));
    }
}
