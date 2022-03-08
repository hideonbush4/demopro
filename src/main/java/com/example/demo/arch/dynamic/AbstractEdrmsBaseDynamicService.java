package com.example.demo.arch.dynamic;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlRunner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wangchao
 */
@SuppressWarnings("all")
@Slf4j
public abstract class AbstractEdrmsBaseDynamicService<T extends AbstractArchDynamic> {

    private static final String[] NOT_EXIST_COMMENT_CLOUMNS =
        {"id", "create_id", "update_id", "create_time", "modified_time"};
//    @Autowired
//    EdrmsArchCategorySubFieldService edrmsArchCategorySubFieldService;

    /**
     * 获取当前类对应的subType
     *
     * @return
     */
    public abstract String getEdrmsCategorySubType();

    /**
     * 获取当前表所有字段
     *
     * @param categoryId
     * @return
     */
    private List<String> getAllColumn(Long categoryId) {
//        // 获取当前表的所有列 TODO 这里要改成从缓存读取
//        List<EdrmsArchCategorySubField> subFieldList = edrmsArchCategorySubFieldService.list(Wrappers
//            .lambdaQuery(EdrmsArchCategorySubField.class).eq(EdrmsArchCategorySubField::getArchCategoryId, categoryId)
//            .eq(EdrmsArchCategorySubField::getSubType, getEdrmsCategorySubType()));
//        List<String> allColumn =
//            subFieldList.stream().map(v -> v.getColumnCode().toLowerCase(Locale.ROOT)).collect(Collectors.toList());
//        allColumn.addAll(Arrays.asList(NOT_EXIST_COMMENT_CLOUMNS));
//        return allColumn;
        return null;
    }

    private String getDynamicTableName(Long categoryId) {
        return "edrms_arch_" + getEdrmsCategorySubType().toLowerCase() + "_" + categoryId;
    }

    /**
     * 自己拼接where条件
     *
     * @param categoryId
     * @param where
     * @return
     */
    public List<T> select(Long categoryId, String where) {
        List<String> allColumn = getAllColumn(categoryId);
        allColumn = allColumn.stream().map(v -> v + " " + StrUtil.toCamelCase(v)).collect(Collectors.toList());
        String sql = "select " + StrUtil.join(",", allColumn) + " from " + getDynamicTableName(categoryId) + where;
        SqlRunner sqlRunner = new SqlRunner();
        List<Map<String, Object>> dbMapList = sqlRunner.selectList(sql, null);
        return table2Dynamic(allColumn, dbMapList);
    }

    /**
     * 按照params参数查询
     *
     * @param categoryId
     * @param params
     * @return
     */
    public List<T> select(Long categoryId, Map<String, Object> params) {
        return select(categoryId, whereString(categoryId, params));
    }

    public Page<T> selectPage(Long categoryId, Page page, Map<String, Object> params) {
        List<String> allColumn = getAllColumn(categoryId);
        allColumn = allColumn.stream().map(v -> v + " " + StrUtil.toCamelCase(v)).collect(Collectors.toList());
        String sql = "select " + StrUtil.join(",", allColumn) + " from " + getDynamicTableName(categoryId)
            + whereString(categoryId, params);
        SqlRunner sqlRunner = new SqlRunner();
        Page page1 = sqlRunner.selectPage(page, sql, null);
        List records = page1.getRecords();
        List list = table2Dynamic(allColumn, records);
        page1.setRecords(list);
        return page1;
    }

    /**
     * 按照实体查询
     *
     * @param categoryId
     * @param entity
     * @return
     */
    public List<T> select(Long categoryId, T entity) {
        Map map = null;
        try {
            map = BeanMap.create(entity.generate());
        } catch (Exception e) {
            log.error("类型转换异常", e);
        }
        return select(categoryId, map);
    }

    /**
     * 按照id查询
     *
     * @param categoryId
     * @param id
     * @return
     */
    public T selectById(Long categoryId, Long id) {
        StringBuilder where = new StringBuilder();
        List<T> list = select(categoryId, " where id=" + id);
        if (CollUtil.isNotEmpty(list)) {
            return list.get(0);
        } else {
            return null;
        }
    }

    public boolean insert(Long categoryId, T entity) {
        List<String> allColumn = getAllColumn(categoryId);

        // 驼峰转换下划线并获取value值
        SerializeConfig config = new SerializeConfig();
        config.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
        String jsonString = JSON.toJSONString(entity, config);
        JSONObject entityJson = JSONObject.parseObject(jsonString);
        List columnValue = allColumn.stream().map(v -> {
            if (ObjectUtil.isEmpty(entityJson.get(v))) {
                return null;
            } else if (entityJson.get(v) instanceof String) {
                return "'" + entityJson.get(v) + "'";
            } else {
                return entityJson.get(v);
            }
        }).collect(Collectors.toList());
        String sql = "insert into " + getDynamicTableName(categoryId) + " (" + StrUtil.join(",", allColumn)
            + ") values (" + StrUtil.join(",", columnValue) + ")";
        SqlRunner sqlRunner = new SqlRunner();
        return sqlRunner.insert(sql);
    }

    public boolean update(@NotNull Long archCategoryId, Long id, T generate) {
        List<String> allColumn = getAllColumn(archCategoryId);
        SerializeConfig config = new SerializeConfig();
        config.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
        String jsonString = JSON.toJSONString(generate, config);
        JSONObject entityJson = JSONObject.parseObject(jsonString);
        StringBuilder builder = new StringBuilder();
        for (String column : allColumn) {
            if (ObjectUtil.isEmpty(entityJson.get(column))) {
                builder.append(column).append("=null,");
            } else {
                if (entityJson.get(column) instanceof String) {
                    builder.append(column).append("='").append(entityJson.get(column)).append("',");
                } else {
                    builder.append(column).append("=").append(entityJson.get(column)).append(",");
                }
            }
        }
        String colums = builder.toString().substring(0, builder.lastIndexOf(","));
        SqlRunner sqlRunner = new SqlRunner();
        String sql = "update " + getDynamicTableName(archCategoryId) + " set " + colums + " where id = " + id;
        return sqlRunner.update(sql);
    }

    public boolean updateWithNull(Long categroyId, T entity) {
        return false;
    }

    public Boolean delete(Long categoryId, List<Long> idList) {
        String sql = "delete from edrms_arch_" + getEdrmsCategorySubType().toLowerCase() + "_" + categoryId
            + " where id in (" + StrUtil.join(",", idList) + ")";
        SqlRunner sqlRunner = new SqlRunner();
        return sqlRunner.delete(sql);
    }

    private String whereString(Long categoryId, Map<String, Object> params) {
        StringBuilder where = new StringBuilder();
        List<String> allColumn = getAllColumn(categoryId);
        Map newParams = new HashMap(humpToUnderline(params));
        newParams.forEach((k, v) -> {
            if (!allColumn.contains(k)) {
                newParams.remove(k);
            }
        });
        if (newParams.size() > 0) {
            where.append(" where ");
            Set<String> keyList = newParams.keySet();
            keyList = keyList.stream().map(v -> v + "='" + newParams.get(v) + "'").collect(Collectors.toSet());
            where.append(StrUtil.join(" and ", keyList));
        }
        return where.toString();
    }

    private Map<String, Object> humpToUnderline(Map<String, Object> params) {
        HashMap<String, Object> transitionMap = new HashMap<>(16);
        params.forEach((k, v) -> transitionMap.put(StrUtil.toUnderlineCase(k), v));
        return transitionMap;
    }

    private List<T> table2Dynamic(List<String> allColumn, List<Map<String, Object>> dbMapList) {
        Class<T> dynamicClass = (Class<T>)ReflectionKit.getSuperClassGenericType(getClass(), 0);
        Field[] fieldList = ReflectUtil.getFields(dynamicClass);
        // 转换成驼峰
        List<String> camelCaseList = allColumn.stream().map(v -> StrUtil.toCamelCase(v)).collect(Collectors.toList());
        List<String> fieldNameList = Arrays.stream(fieldList).map(v -> v.getName()).collect(Collectors.toList());
        camelCaseList.removeIf(v -> fieldNameList.contains(v));
        List<T> resultList = new ArrayList<>();
        for (Map<String, Object> stringObjectMap : dbMapList) {
            T result =
                JSONObject.toJavaObject(JSONObject.parseObject(JSONObject.toJSONString(stringObjectMap)), dynamicClass);
            fieldNameList.forEach(v -> stringObjectMap.remove(v));

            if (CollUtil.isNotEmpty(camelCaseList)) {
                result.setFieldDef(stringObjectMap);
            }
            resultList.add(result);
        }
        return resultList;
    }
}