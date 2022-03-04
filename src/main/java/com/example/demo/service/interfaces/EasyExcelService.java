package com.example.demo.service.interfaces;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.domain.dto.easyexcel.EasyExcelDto;
import com.example.demo.domain.dto.easyexcel.ImportResultDto;
import com.example.demo.domain.entity.easyexcel.EasyExcelEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author dengzhewen
 * @create 2022-02-18 9:43
 * @Version v1.0.0
 */
public interface EasyExcelService extends IService<EasyExcelEntity> {
    /**
     * service层导出，入参是list<T>
     * @param list
     * @param response
     * @throws UnsupportedEncodingException
     */
    void exportEasyExcel(List<EasyExcelDto> list, HttpServletResponse response) throws UnsupportedEncodingException;

    /**
     * controller册导出，入参是ids，service层查数据
     * @param ids
     * @return
     */
    List<EasyExcelDto> exportData(String ids);

    ImportResultDto importData(MultipartFile file);

    ImportResultDto importDataError(MultipartFile file, Byte rollbackType);

}
