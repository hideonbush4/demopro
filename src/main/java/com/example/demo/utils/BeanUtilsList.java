package com.example.demo.utils;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dengzhewen
 * @create 2022-03-04 9:26
 * @Version v1.0.0
 */
public class BeanUtilsList<T> {

    /**
     * 将某个A泛型的list转化为B泛型的list
     * @param sourceObjs
     * @param clazz
     * @param <T>
     * @param <D>
     * @return
     */
    public static <T, D> List<T> copyBeanList(List<D> sourceObjs, Class<T> clazz) {
        if (sourceObjs == null) {
            return null;
        }
        int len = sourceObjs.size();
        List ts = new ArrayList(len);
        Object t = null;
        for (int i = 0; i < len; i++) {
            Object d = sourceObjs.get(i);
            t = populateTbyDbySpring(d, clazz);
            ts.add(t);
        }
        return ts;
    }

    public static <T, D> T populateTbyDbySpring(D sourceObj, Class<T> clazz) {
        if (sourceObj == null) {
            return null;
        }
        Object t = null;
        try {
            t = clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        BeanUtils.copyProperties(sourceObj, t);
        return (T) t;
    }

}
