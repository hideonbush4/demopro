package com.example.demo.service.interfaces;

import com.example.demo.domain.dto.EasyExcelDto;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author dengzhewen
 * @create 2022-02-18 9:43
 * @Version v1.0.0
 */
public interface EasyExcelService {
    void exportEasyExcel(List<EasyExcelDto> list, HttpServletResponse response) throws UnsupportedEncodingException;

    List<EasyExcelDto> exportData(String ids);
}
