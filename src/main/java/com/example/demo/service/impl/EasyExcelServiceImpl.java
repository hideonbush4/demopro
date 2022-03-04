package com.example.demo.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.util.DateUtils;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.dao.EasyExcelMapper;
import com.example.demo.domain.dto.EasyExcelDto;
import com.example.demo.domain.entity.EasyExcelEntity;
import com.example.demo.service.interfaces.EasyExcelService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
            easyExcelDto.setName("示例名称").setAge(18).setModifiedTime(new Date());
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
}
