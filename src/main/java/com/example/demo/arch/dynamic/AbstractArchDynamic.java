package com.example.demo.arch.dynamic;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.springframework.cglib.beans.BeanGenerator;
import org.springframework.cglib.beans.BeanMap;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author wangchao
 */
@SuppressWarnings("all")
@Data
public abstract class AbstractArchDynamic<T extends AbstractArchDynamic<?>> extends Model<T> implements Serializable {
    private static final long serialVersionUID = -622018509456272841L;
    Map<String, Object> fieldDef = new HashMap<>();

    public Object getDef(String name) {
        return fieldDef.get(name);
    }

    public Object putDef(String name, Object value) {
        return fieldDef.put(name, value);
    }

    private final class DynamicBean {
        private AbstractArchDynamic target;
        private BeanMap beanMap;

        private DynamicBean(Class<? extends AbstractArchDynamic> superclass, Map<String, Class> propertyMap) {
            this.target = generateBean(superclass, propertyMap);
            this.beanMap = BeanMap.create(this.target);
        }

        private void setValue(String property, Object value) {
            beanMap.put(property, value);
        }

        private Object getValue(String property) {
            return beanMap.get(property);
        }

        private AbstractArchDynamic getTarget() {
            return this.target;
        }

        /**
         * 根据属性生成对象
         */
        private T generateBean(Class<? extends AbstractArchDynamic> superclass, Map<String, Class> propertyMap) {
            BeanGenerator generator = new BeanGenerator();
            if (null != superclass) {
                generator.setSuperclass(superclass);
            }
            BeanGenerator.addProperties(generator, propertyMap);
            return (T)generator.create();
        }
    }

    public <T extends AbstractArchDynamic> T generate() throws InvocationTargetException, IllegalAccessException {
        PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();

        // 1.获取原对象的字段数组
        PropertyDescriptor[] descriptorArr = propertyUtilsBean.getPropertyDescriptors(this);

        // 2.遍历原对象的字段数组，并将其封装到Map
        Map<String, Class> oldKeyMap = new HashMap<>(4);
        for (PropertyDescriptor it : descriptorArr) {
            if (!"class".equalsIgnoreCase(it.getName())) {
                oldKeyMap.put(it.getName(), it.getPropertyType());
                fieldDef.put(it.getName(), it.getReadMethod().invoke(this));
            }
        }

        fieldDef.remove("fieldDef");
        // 3.将扩展字段Map合并到原字段Map中
        fieldDef.forEach((k, v) -> {
            if (null != v) {
                oldKeyMap.put(k, v.getClass());
            }
        });

        // 4.根据新的字段组合生成子类对象
        DynamicBean dynamicBean = new DynamicBean(this.getClass(), oldKeyMap);

        // 5.放回合并后的属性集合
        fieldDef.forEach((k, v) -> {
            dynamicBean.setValue(k, v);
        });
        return (T)dynamicBean.getTarget();
    }

    public <T extends AbstractArchDynamic> T jsonStringToDynamicBean(String jsonData)
        throws InstantiationException, IllegalAccessException {
        JSONObject object = JSON.parseObject(jsonData);
        AbstractArchDynamic archDynamic = this.getClass().newInstance();

        Map<String, Field> fieldMap = Stream.of(this.getClass().getDeclaredFields())
            .collect(Collectors.toMap(Field::getName, Function.identity()));
        for (Map.Entry<String, Object> entry : object.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (!fieldMap.containsKey(key)) {
                archDynamic.putDef(key, value);
            } else {
                Field field = fieldMap.get(key);
                field.setAccessible(true);
                field.set(archDynamic, value);
            }
        }
        return (T)archDynamic;
    }
}
