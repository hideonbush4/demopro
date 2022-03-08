package com.example.demo.arch.dynamic;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.domain.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Copyright (C), 2018-2021, ShangHai Macrowing. Co., Ltd.
 *
 * @Package: sol.edrms.v2.controller.dynamicarch
 * @Description:
 * @author: junjie.li
 * @Date: 2022/3/5
 * @version: 1.00
 */
@RestController
@Api(tags = "档案数据管理")
@RequestMapping(value = "/dynamicarch")
@Validated
@AllArgsConstructor
public class EdrmsDynamicArchController {

    private final EdrmsArchFileServiceImpl archFileService;
    private final EdrmsArchDossierServiceImpl archDossierService;

    @ApiOperation(value = "保存档案数据", tags = "保存档案数据")
    @PostMapping(value = "/arch")
    public Response<Boolean> saveArch(@RequestBody @Validated EdrmsFileDynamic fileDynamic) {
        return Response.success(archFileService.addArchArch(fileDynamic));
    }

    @ApiOperation(value = "修改档案数据", tags = "修改档案数据")
    @PutMapping(value = "/arch")
    public Response<Boolean> updateArch(@RequestBody @Validated EdrmsFileDynamic fileDynamic) {
        return Response.success(archFileService.updateArch(fileDynamic));
    }

//    @ApiOperation(value = "档案数据列表", tags = "档案数据列表")
//    @GetMapping(value = "/arch")
//    public Response<List<EdrmsFileDynamic>> list(@RequestBody ArchForm archForm) {
//        return Response.success(archFileService.select(archForm.getCategoryId(), archForm.getParam()));
//    }

//    @ApiOperation(value = "档案数据分页", tags = "档案数据分页")
//    @GetMapping(value = "/page")
//    public Response<Page<EdrmsFileDynamic>> listPage(@RequestBody ArchForm archForm) {
//        return Response
//            .success(archFileService.selectPage(archForm.getCategoryId(), archForm.getPage(), archForm.getParam()));
//    }

    @ApiOperation(value = "档案详情", tags = "档案详情")
    @GetMapping(value = "/arch/{id}")
    public Response<EdrmsFileDynamic> oneById(@PathVariable(value = "id") Long id,
        @RequestParam(value = "categoryId") Long categoryId) {
        return Response.success(archFileService.selectById(categoryId, id));
    }

    @ApiOperation(value = "保存案卷数据", tags = "保存案卷数据")
    @PostMapping(value = "/dossier")
    public Response<Boolean> saveDossier(@RequestBody @Validated EdrmsDossierDynamic dossierDynamic) {
        return Response.success(archDossierService.addArchDossier(dossierDynamic));
    }
}
