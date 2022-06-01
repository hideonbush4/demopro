package com.example.demo.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.DateUtils;
import com.example.demo.constants.ExcelConstants;
import com.example.demo.domain.Response;
import com.example.demo.domain.dto.easyexcel.EasyExcelDto;
import com.example.demo.domain.dto.easyexcel.ImportResultDto;
import com.example.demo.service.interfaces.EasyExcelService;
import com.example.demo.utils.ExcelAttribute;
import com.example.demo.utils.PoiExcelUtils;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.security.action.GetPropertyAction;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.security.AccessController;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@Api(tags = "excel导入导出")
@RequestMapping(value = "/excel")
@Validated
public class ExcelController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    EasyExcelService easyExcelService;

    // 下载导入失败的错误信息
    @GetMapping("/errorFileDownload")
    @ApiOperation(value = "下载导入失败的错误信息", notes = "下载导入失败的错误信息")
    public void downloadErrorExcel(String errorFilePath, HttpServletResponse response) throws IOException {
        if (StrUtil.isEmpty(errorFilePath)) {
            throw new RuntimeException("找不到要下载的错误信息文件");
        }
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = String.format("批量导入错误信息文件_%s.xlsx", DateUtil.format(new Date(), "yyyyMMddHHmmss"));
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));

        // 获取临时文件目录
        String tempDir = AccessController.doPrivileged(new GetPropertyAction("java.io.tmpdir"));
        String fileFullPath = String.format("%s%s%s", tempDir, File.separator, errorFilePath);
        byte[] bytes = FileUtil.readBytes(fileFullPath);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(bytes);
        IoUtil.close(outputStream);
    }

    // 导入-easyexcel-带错误信息
    // rollbackType:0单个回滚（错误数据不会导入，正确数据会导入），1整体回滚（只要有错误数据就全部取消导入
    @PostMapping("/importDataError")
    @ApiOperation(value = "导入-easyexcel-带错误信息", notes = "导入-easyexcel-带错误信息")
    public Response<ImportResultDto> importDataError(MultipartFile file, @RequestParam("rollbackType") byte rollbackType){
        String fileName = file.getOriginalFilename();
        if (StrUtil.endWithAnyIgnoreCase(fileName, ExcelConstants.EXCEL_SUFFIX)) {
            return Response.success(easyExcelService.importDataError(file, rollbackType));
        }
        return Response.fail("上传excel文件");
    }

    // 导入-easyexcel
    @PostMapping("/importData")
    @ApiOperation(value = "导入-easyexcel", notes = "导入-easyexcel")
    public Response<ImportResultDto> importData(MultipartFile file){
        String fileName = file.getOriginalFilename();
        if (StrUtil.endWithAnyIgnoreCase(fileName, ExcelConstants.EXCEL_SUFFIX)) {
            return Response.success(easyExcelService.importData(file));
        }
        return Response.fail("上传excel文件");
    }

    // 导出模板-easyexcel
    @GetMapping("/exportOrTemplate")
    @ApiOperation(value = "导出模板-easyexcel", notes = "导出模板-easyexcel")
    public void exportEasyExcel2(String ids, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = String.format("导出excel名称.xlsx", DateUtils.format(new Date(), "yyyyMMddHHmmss"));
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
        EasyExcel.write(response.getOutputStream(), EasyExcelDto.class).sheet("sheet名称").doWrite(easyExcelService.exportData(ids));
    }

    // 导出-easyexcel
    @GetMapping("/exportEasyExcel")
    @ApiOperation(value = "导出示例数据-easyexcel", notes = "导出示例数据-easyexcel")
    public void exportEasyExcel(HttpServletResponse response) throws Exception {
        List<EasyExcelDto> list = new ArrayList<>();
        EasyExcelDto easyExcelDto = new EasyExcelDto();
        easyExcelDto.setAge(18);
        easyExcelDto.setName("张三");
        easyExcelDto.setModifiedTime(new Date());
        EasyExcelDto easyExcelDto1 = new EasyExcelDto();
        easyExcelDto1.setAge(19);
        easyExcelDto1.setName("李四");
        easyExcelDto1.setModifiedTime(new Date());
        list.add(easyExcelDto);
        list.add(easyExcelDto1);
        easyExcelService.exportEasyExcel(list, response);
    }

    @PostMapping(value = "/enclosureFile/{uniqueCode}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
//    @PostMapping(value = "/enclosureFile/{uniqueCode}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void importTest(@RequestParam("multipartFiles") MultipartFile[] multipartFiles, @PathVariable String uniqueCode){

    }

    // 导出-poi
    @GetMapping("/exportExcel")
    @ApiOperation(value = "导出示例数据-poi", notes = "导出示例数据-poi")
    public void exportExcel(HttpServletResponse response, HttpServletRequest request) throws Exception {
        // 模拟从数据库查询数据
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student(1L, "周深（web导出）", 28, "贵州", new SimpleDateFormat("yyyy-MM-dd").parse("1992-9-29"), 161.0, true));
        studentList.add(new Student(2L, "李健（web导出）", 46, "哈尔滨", new SimpleDateFormat("yyyy-MM-dd").parse("1974-9-23"), 174.5, true));
        studentList.add(new Student(3L, "周星驰（web导出）", 58, "香港", new SimpleDateFormat("yyyy-MM-dd").parse("1962-6-22"), 174.0, false));

        // 导出数据  按模板导出到网络
//        PoiExcelUtils<Student> poiExcelUtils = new PoiExcelUtils<>(Student.class);
//        FileInputStream excelTemplateInputStream = new FileInputStream(new File("C:////student_info3.xlsx"));
//        poiExcelUtils.exportExcelWithTemplate(studentList, "学生信息表.xlsx", excelTemplateInputStream, 2, 0, response);

        // Excel导出到网络 不按模板
        PoiExcelUtils<Student> poiExcelUtils = new PoiExcelUtils<>(Student.class);
        poiExcelUtils.exportExcel(studentList, "学生信息表-不按模板.xlsx", "sheetName", response);

        logger.info("导出成功！");
    }

    // 导入-poi
    @PostMapping("/importExcel")
    @ApiOperation(value = "导入-poi", notes = "导入-poi")
    public Map<String, Object> importExcel(MultipartFile file) throws Exception {
        PoiExcelUtils<Student> poiExcelUtils = new PoiExcelUtils<>(Student.class);
        // 兼容xls
        String[] split = Objects.requireNonNull(file.getOriginalFilename()).split("\\.");
        List<Student> studentList = poiExcelUtils.importExcel(split[split.length - 1], file.getInputStream(), 1, 0);
//        List<Student> studentList = poiExcelUtils.importExcel(file.getInputStream(), 1, 0);

        saveToDB(studentList);
        logger.info("导入{}成功！", file.getOriginalFilename());

        // 这里用Map偷懒了，实际项目中可以封装Result实体类返回
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("data", studentList);
        result.put("msg", "success");
        return result;
    }

    // 导入-poi-map
    @PostMapping("/importExcelForMap")
    public Map<String, Object> importExcelForMap(MultipartFile file) throws Exception {
        PoiExcelUtils poiExcelUtils = new PoiExcelUtils<>();
        String[] split = Objects.requireNonNull(file.getOriginalFilename()).split("\\.");
        // 兼容xls
        List<Map<Integer, String>> studentList = poiExcelUtils.importExcelForMap(split[split.length - 1], file.getInputStream(), 0, 0);
//        List<Student> studentList = poiExcelUtils.importExcel(file.getInputStream(), 1, 0);

//        saveToDB(studentList);
        logger.info("导入{}成功！", file.getOriginalFilename());

        // 这里用Map偷懒了，实际项目中可以封装Result实体类返回
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("data", studentList);
        result.put("msg", "success");
        return result;
    }

    private void saveToDB(List<Student> studentList) {
        if (CollectionUtils.isEmpty(studentList)) {
            return;
        }
        // 直接打印，模拟插入数据库
        studentList.forEach(System.out::println);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Student {
        @JsonSerialize(using = ToStringSerializer.class)
//        @ExcelAttribute(sort = 6, value = "id")
        private Long id;
        @ExcelAttribute(sort = 0, value = "姓名")
        private String name;
        @ExcelAttribute(sort = 1, value = "年龄")
        private Integer age;
        @ExcelAttribute(sort = 2, value = "住址")
        private String address;
        @ExcelAttribute(sort = 3, value = "生日", format = "date")
        private Date birthday;
        @ExcelAttribute(sort = 4, value = "身高")
        private Double height;
        @ExcelAttribute(sort = 5, value = "是否来自大陆")
        private Boolean isMainlandChina;
    }

}
