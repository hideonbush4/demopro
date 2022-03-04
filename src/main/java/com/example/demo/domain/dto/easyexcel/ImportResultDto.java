package com.example.demo.domain.dto.easyexcel;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author dengzhewen
 * @create 2022-03-04 11:32
 * @Version v1.0.0
 */
@Data
@Accessors(chain = true)
public class ImportResultDto {

    /**
     * 导入总条数
     */
    private Integer totalCount;

    /**
     * 成功处理总条数
     */
    private Integer successCount;

    /**
     * 失败处理总条数
     */
    private Integer failCount;

    /**
     * 保存失败信息的文件路径
     */
    private String errorFilePath;

}
