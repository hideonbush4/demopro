package com.example.demo.controller.initcomment;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.CharsetUtil;

import com.example.demo.domain.dto.initcomment.ColumnEntity;
import com.example.demo.domain.dto.initcomment.GenConfig;
import com.example.demo.domain.dto.initcomment.TableEntity;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.commons.configuration.Configuration;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;


import java.io.File;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成器 工具类
 *
 * @author cloud
 * @date 2018-07-30
 */
@Slf4j
@UtilityClass
public class GenUtils {
    private final String ENTITY_JAVA_VM = "Entity.java.vm";
    private final String MAPPER_JAVA_VM = "Mapper.java.vm";
    private final String SERVICE_JAVA_VM = "Service.java.vm";
    private final String SERVICE_IMPL_JAVA_VM = "ServiceImpl.java.vm";
    private final String CONTROLLER_JAVA_VM = "Controller.java.vm";
    private final String FORM_JAVA_VM = "Form.java.vm";
    private final String MAPPER_XML_VM = "Mapper.xml.vm";

    /**
     * 配置
     *
     * @param config
     * @return
     */
    private List<String> getTemplates(GenConfig config) {
        List<String> templates = new ArrayList<>();
        templates.add("template/Entity.java.vm");
        templates.add("template/Mapper.java.vm");
        templates.add("template/Mapper.xml.vm");
        templates.add("template/Service.java.vm");
        templates.add("template/ServiceImpl.java.vm");
        templates.add("template/Controller.java.vm");
        templates.add("template/Form.java.vm");
        return templates;
    }

    /**
     * 生成代码
     */
    @SneakyThrows
    public void generatorCode(GenConfig genConfig, List<Map<String, String>> columns, ZipOutputStream zip) {
        // 配置信息
        Configuration config = getConfig();
        boolean hasBigDecimal = false;
        // 表信息
        TableEntity tableEntity = new TableEntity();
        tableEntity.setTableName(genConfig.getTableName());

        tableEntity.setComments(genConfig.getTableComment());

        // 表名转换成Java类名
        String className = columnToJava(tableEntity.getTableName());
        tableEntity.setCaseClassName(className);
        tableEntity.setLowerClassName(StringUtils.uncapitalize(className));
        // 列信息
        List<ColumnEntity> columnList = new ArrayList<>();
        for (Map<String, String> column : columns) {
            ColumnEntity columnEntity = new ColumnEntity();
            columnEntity.setColumnName(column.get("columnName"));
            columnEntity.setDataType(column.get("dataType"));
            columnEntity.setComments(column.get("columnComment") == null ? "" : column.get("columnComment"));
            columnEntity.setExtra(column.get("extra"));
            columnEntity.setNullable("NO".equals(column.get("isNullable")));
            columnEntity.setColumnType(column.get("columnType"));
            // 列名转换成Java属性名
            columnEntity.setCaseAttrName(StrUtil.toCamelCase(columnEntity.getColumnName()));
            columnEntity.setLowerAttrName(columnEntity.getColumnName());

            // 列的数据类型，转换成Java类型
            String attrType = config.getString(columnEntity.getDataType(), "unknowType");
            columnEntity.setAttrType(attrType);
            if (!hasBigDecimal && "BigDecimal".equals(attrType)) {
                hasBigDecimal = true;
            }
            // 是否主键
            if ("PRI".equalsIgnoreCase(column.get("columnKey")) && tableEntity.getPk() == null) {
                tableEntity.setPk(columnEntity);
            }

            columnList.add(columnEntity);
        }
        tableEntity.setColumns(columnList);

        // 没主键，则第一个字段为主键
        if (tableEntity.getPk() == null) {
            tableEntity.setPk(tableEntity.getColumns().get(0));
        }

        // 设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop);
        // 封装模板数据
        Map<String, Object> map = new HashMap<>(16);
        map.put("tableName", tableEntity.getTableName());
        map.put("pk", tableEntity.getPk());
        map.put("className", tableEntity.getCaseClassName());
        map.put("classname", tableEntity.getLowerClassName());
        map.put("pathName", tableEntity.getLowerClassName().toLowerCase());
        map.put("columns", tableEntity.getColumns());
        map.put("hasBigDecimal", hasBigDecimal);
        map.put("datetime", DateUtil.now());

        if (StrUtil.isNotBlank(genConfig.getTableComment())) {
            map.put("comments", genConfig.getTableComment());
        } else {
            map.put("comments", "");
        }

        if (StrUtil.isNotBlank(genConfig.getAuthor())) {
            map.put("author", genConfig.getAuthor());
        } else {
            String author = System.getProperty("user.name");
            map.put("author", author == null ? "author" : author);
        }

        if (StrUtil.isNotBlank(genConfig.getModuleName())) {
            map.put("moduleName", genConfig.getModuleName());
        } else {
            map.put("moduleName", "default");
        }
        map.put("package", config.getString("package"));
        map.put("mainPath", config.getString("mainPath"));
        VelocityContext context = new VelocityContext(map);

        // 获取模板列表
        List<String> templates = getTemplates(genConfig);
        for (String template : templates) {
            // 渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, CharsetUtil.UTF_8);
            tpl.merge(context, sw);

            // 添加到zip
            zip.putNextEntry(new ZipEntry(Objects.requireNonNull(getFileName(template, tableEntity.getCaseClassName(),
                    map.get("package").toString(), map.get("moduleName").toString()))));
            IoUtil.write(zip, StandardCharsets.UTF_8, false, sw.toString());
            IoUtil.close(sw);
            zip.closeEntry();
        }
    }
    /**
     * 列名转换成Java属性名
     */
    public String columnToJava(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[] {'_'}).replace("_", "");
    }

    /**
     * 表名转换成Java类名
     */
    private String tableToJava(String tableName, String tablePrefix) {
        if (StringUtils.isNotBlank(tablePrefix)) {
            tableName = tableName.replaceFirst(tablePrefix, "");
        }
        return columnToJava(tableName);
    }

    /**
     * 获取配置信息
     */
    @SneakyThrows
    private Configuration getConfig() {
        return new PropertiesConfiguration("generator.properties");
    }

    /**
     * 获取文件名
     */
    private String getFileName(String template, String className, String packageName, String moduleName) {
        String packagePath =
                "edrms-core" + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator;

        if (StringUtils.isNotBlank(packageName)) {
            packagePath += packageName.replace(".", File.separator) + File.separator;
        }

        if (template.contains(ENTITY_JAVA_VM)) {
            return packagePath + "domain" + File.separator + "entity" + File.separator + moduleName + File.separator
                    + className + ".java";
        }
        if (template.contains(FORM_JAVA_VM)) {
            return packagePath + "domain" + File.separator + "form" + File.separator + moduleName + File.separator
                    + className + "Form.java";
        }
        if (template.contains(MAPPER_JAVA_VM)) {
            return packagePath + "dao" + File.separator + "mysql" + File.separator + moduleName + File.separator
                    + className + "Mapper.java";
        }

        if (template.contains(SERVICE_JAVA_VM)) {
            return packagePath + "service" + File.separator + "interfaces" + File.separator + moduleName
                    + File.separator + className + "Service.java";
        }

        if (template.contains(SERVICE_IMPL_JAVA_VM)) {
            return packagePath + "service" + File.separator + "impl" + File.separator + moduleName + File.separator
                    + className + "ServiceImpl.java";
        }

        if (template.contains(CONTROLLER_JAVA_VM)) {
            return packagePath + "controller" + File.separator + moduleName + File.separator + className
                    + "Controller.java";
        }

        if (template.contains(MAPPER_XML_VM)) {
            return "edrms-core" + File.separator + "src" + File.separator + "main" + File.separator + "resources"
                    + File.separator + "mybatis" + File.separator + "mysql" + File.separator + moduleName + File.separator
                    + className + "Mapper.xml";
        }

        return null;
    }
}
