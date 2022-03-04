package com.example.demo.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author dengzhewen
 * @create 2022-03-04 11:36
 * @Version v1.0.0
 */
@Data
public class Response<T> implements Serializable {

    private boolean result;
    private String message;
    private T obj;
    private int code;

    public static <T> Response<T> success() {
        return success("success", (T) null);
    }

    public static <T> Response<T> success(T obj) {
        return success("success", obj);
    }

    public static <T> Response<T> success(String msg, T obj) {
        Response<T> response = new Response();
        response.setMessage(msg);
        response.setObj(obj);
        response.setResult(Boolean.TRUE);
        response.setCode(0);
        return response;
    }

    public static <T> Response<T> fail(String msg, T obj, int responseCode) {
        Response<T> response = new Response();
        response.setMessage(msg);
        response.setObj(obj);
        response.setResult(Boolean.FALSE);
        response.setCode(responseCode);
        return response;
    }

    public static <T extends Throwable> Response<T> fail(T obj) {
        return fail(obj, -1);
    }

    public static <T> Response<T> fail(String msg) {
        return fail((String)msg, (T) null, -1);
    }

    public static <T> Response<T> fail(T obj, int code) {
        return fail("fail", obj, code);
    }

    public static <T> Response<T> fail() {
        return fail((String)"fail", (T) null, -1);
    }

}
