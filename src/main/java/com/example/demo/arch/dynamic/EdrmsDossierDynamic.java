package com.example.demo.arch.dynamic;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Copyright (C), 2018-2021, ShangHai Macrowing. Co., Ltd.
 *
 * @Package: sol.edrms.v2.domain.entity.arch
 * @Description:
 * @author: junjie.li
 * @Date: 2022/3/5
 * @version: 1.00
 */
@Data
public class EdrmsDossierDynamic extends AbstractArchDynamic<EdrmsDossierDynamic> {

    private static final long serialVersionUID = 4402575030756901826L;

    @NotBlank
    @JSONField(serialize = false)
    private String formId;
    @NotNull
    private Long archCategoryId;
    @NotBlank
    @JSONField(serialize = false)
    private String subType;
    private Long id;
    private Date createTime;
    private Date modifiedTime;
    private String createId;
    private String updateId;

}
