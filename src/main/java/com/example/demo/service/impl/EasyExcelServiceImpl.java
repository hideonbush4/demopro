package com.example.demo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.event.SyncReadListener;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.util.DateUtils;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.dao.EasyExcelMapper;
import com.example.demo.domain.dto.easyexcel.EasyExcelDto;
import com.example.demo.domain.dto.easyexcel.EasyExcelErrorDto;
import com.example.demo.domain.dto.easyexcel.ImportResultDto;
import com.example.demo.domain.entity.easyexcel.EasyExcelEntity;
import com.example.demo.service.interfaces.EasyExcelService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author dengzhewen
 * @create 2022-02-18 10:01
 * @Version v1.0.0
 */
@Service
@Slf4j
public class EasyExcelServiceImpl extends ServiceImpl<EasyExcelMapper, EasyExcelEntity> implements EasyExcelService {
    @Override
    public void exportEasyExcel(List<EasyExcelDto> list, HttpServletResponse response) throws UnsupportedEncodingException {

        // 导出excel
        String fileName = URLEncoder.encode("导出的excel名称" + DateUtils.format(new Date(), "yyyyMMdd"), "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");

        // 头的样式
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short) 10);
        headWriteCellStyle.setWriteFont(headWriteFont);
        headWriteCellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());

        // 内容的样式
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        WriteFont contentWriteFont = new WriteFont();
        contentWriteFont.setFontHeightInPoints((short) 10);
        contentWriteCellStyle.setWriteFont(contentWriteFont);
        HorizontalCellStyleStrategy horizontalCellStyleStrategy =
                new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
        ExcelWriter excelWriter = null;
        try {
            excelWriter = EasyExcel.write(response.getOutputStream()).excelType(ExcelTypeEnum.XLS).inMemory(Boolean.TRUE)
                    .registerWriteHandler(horizontalCellStyleStrategy).build();
            // CommentWriteHandler通用写excel 添加批注 CustomSheetWriteHandler excel单元格添加下拉选择
//            excelWriter = EasyExcel.write(response.getOutputStream()).excelType(ExcelTypeEnum.XLS).inMemory(Boolean.TRUE)
//                    .registerWriteHandler(horizontalCellStyleStrategy)
//                    .registerWriteHandler(new CommentWriteHandler())
//                    .registerWriteHandler(new CustomSheetWriteHandler())
//                    .build();
            WriteSheet writeSheet0 = EasyExcel.writerSheet("sheet名称").head(EasyExcelDto.class).build();
            excelWriter.write(list, writeSheet0);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
    }

    @Override
    public List<EasyExcelDto> exportData(String ids) {
        if (StringUtils.isEmpty(ids)) {
            // ids为空，下载模板
            EasyExcelDto easyExcelDto = new EasyExcelDto();
            easyExcelDto.setName("示例名称");
            easyExcelDto.setAge(18);
            easyExcelDto.setModifiedTime(new Date());
            List<EasyExcelDto> list = Arrays.asList(easyExcelDto);
            return list;
        }

        String[] split = ids.split(",");
        List<String> list = Arrays.asList(split);
        LambdaQueryWrapper<EasyExcelEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(EasyExcelEntity::getId, list);
        List<EasyExcelEntity> list1 = this.list(lambdaQueryWrapper);
        List<EasyExcelDto> listDto = new ArrayList<>();
        list1.forEach(easyExcelEntity -> {
            EasyExcelDto easyExcelDto = new EasyExcelDto();
            BeanUtils.copyProperties(easyExcelEntity, easyExcelDto);
            listDto.add(easyExcelDto);
        });

        return listDto;
    }

    @Override
    @Transactional
    public ImportResultDto importData(MultipartFile file) {

        // 读取Excel文件
        List<Object> objects = new ArrayList<>();
        try {
            InputStream inputStream = file.getInputStream();
            objects = EasyExcel.read(inputStream, EasyExcelDto.class, new SyncReadListener()).headRowNumber(1).sheet()
                    .doReadSync();
        } catch (Exception e) {
            throw new RuntimeException("读取Excel文件异常");
        }

        for (Object e : objects) {
            EasyExcelDto easyExcelDto = (EasyExcelDto) e;
            EasyExcelEntity easyExcelEntity = BeanUtil.copyProperties(easyExcelDto, EasyExcelEntity.class);
            save(easyExcelEntity);
        }

        ImportResultDto importResultDto = new ImportResultDto();
        importResultDto.setTotalCount(objects.size());

        return importResultDto;
    }

    @Override
    @Transactional
    public ImportResultDto importDataError(MultipartFile file, Byte rollbackType) {
        // 读取Excel文件
        List<Object> objects = new ArrayList<>();
        try {
            InputStream inputStream = file.getInputStream();
            objects = EasyExcel.read(inputStream, EasyExcelDto.class, new SyncReadListener()).headRowNumber(1).sheet()
                    .doReadSync();
        } catch (Exception e) {
            throw new RuntimeException("读取Excel文件异常");
        }

        List<EasyExcelErrorDto> errorList = new ArrayList<>();
        for (Object e : objects) {
            EasyExcelDto easyExcelDto = (EasyExcelDto) e;
            StringBuffer sb = new StringBuffer();
            if (easyExcelDto.getAge() == null) {
                sb.append("年龄不能为空");
            }
            if (StrUtil.isEmpty(easyExcelDto.getName())) {
                sb.append("姓名不能为空");
            }
            if (easyExcelDto.getModifiedTime() == null) {
                sb.append("修改时间不能为空");
            }
            if (StrUtil.isNotEmpty(sb.toString())){
                // 判空
                errorList.add(getErrorDto(easyExcelDto, sb.toString()));
            } else {
                try {
                    EasyExcelEntity easyExcelEntity = BeanUtil.copyProperties(easyExcelDto, EasyExcelEntity.class);
                    save(easyExcelEntity);
                } catch (Exception exception) {
                    sb.append(exception.getMessage());
                    errorList.add(getErrorDto(easyExcelDto, exception.getMessage()));
                }
            }

        }
        String errorFilePath = saveErrorInfo2TempFile(errorList);

        ImportResultDto importResultDto = new ImportResultDto();
        importResultDto.setTotalCount(objects.size()).setFailCount(errorList.size()).setSuccessCount(objects.size() - errorList.size())
                .setErrorFilePath(errorFilePath);

        return importResultDto;
    }

    private EasyExcelErrorDto getErrorDto(EasyExcelDto easyExcelDto, String reason) {

        EasyExcelErrorDto easyExcelErrorDto = BeanUtil.copyProperties(easyExcelDto, EasyExcelErrorDto.class);
        easyExcelErrorDto.setReason(reason);
        return easyExcelErrorDto;

    }

    /**
     * 保存导入失败信息至临时文件
     *
     * @param errorExcelDtoList
     * @return
     */
    private String saveErrorInfo2TempFile(List<EasyExcelErrorDto> errorExcelDtoList) {
        String taskId = IdUtil.simpleUUID();
        String errorFilePath = null;
        if (errorExcelDtoList.size() > 0) {
            try {
                File errorFile = File.createTempFile(taskId, ".xlsx");
                EasyExcel.write(errorFile, EasyExcelErrorDto.class).sheet("导入失败信息").doWrite(errorExcelDtoList);
                errorFilePath = errorFile.getName();
                // JVM退出时删除该文件
                errorFile.deleteOnExit();
            } catch (IOException e) {
                log.error("保存错误文件信息失败");
                log.error(e.getMessage(), e);
            }
        }
        return errorFilePath;
    }

}
