package com.example.demo.controller.gencodetest;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.domain.entity.gencodetest.GenCodeTest;
import com.example.demo.domain.form.gencodetest.GenCodeTestForm;
import com.example.demo.service.interfaces.gencodetest.GenCodeTestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 代码生成测试表
 *
 * @author dengzhewen
 * @date 2022-03-02 20:07:04
 */
@RestController
@AllArgsConstructor
@RequestMapping("/gencodetest")
@Api(value = "gencodetest", tags = "代码生成测试表管理")
public class GenCodeTestController {

    private final GenCodeTestService genCodeTestService;

    /**
     * 分页查询
     *
     * @param page            分页对象
     * @param genCodeTestForm 代码生成测试表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page")
    public Page<GenCodeTest> getGenCodeTestPage(Page page, GenCodeTestForm genCodeTestForm) {
        GenCodeTest genCodeTest = new GenCodeTest();
        BeanUtils.copyProperties(genCodeTestForm, genCodeTest);
        return genCodeTestService.page(page, Wrappers.query(genCodeTest));
    }

    /**
     * 条件查询
     *
     * @param genCodeTestForm 代码生成测试表
     * @return
     */
    @ApiOperation(value = "条件查询", notes = "条件查询")
    @GetMapping("/list")
    public List<GenCodeTest> getGenCodeTestList(GenCodeTestForm genCodeTestForm) {
        GenCodeTest genCodeTest = new GenCodeTest();
        BeanUtils.copyProperties(genCodeTestForm, genCodeTest);
        return genCodeTestService.list(Wrappers.query(genCodeTest));
    }

    /**
     * 通过id查询代码生成测试表
     *
     * @param id id
     * @return Response
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}")
    public GenCodeTest getById(@PathVariable("id") Long id) {
        return genCodeTestService.getById(id);
    }

    /**
     * 新增代码生成测试表
     *
     * @param genCodeTestForm 代码生成测试表
     * @return Response
     */
    @ApiOperation(value = "新增代码生成测试表", notes = "新增代码生成测试表")
    @PostMapping
    public GenCodeTest save(@RequestBody GenCodeTestForm genCodeTestForm) {
        GenCodeTest genCodeTest = new GenCodeTest();
        BeanUtils.copyProperties(genCodeTestForm, genCodeTest);
        genCodeTestService.save(genCodeTest);
        return genCodeTest;
    }

    /**
     * 修改代码生成测试表
     *
     * @param genCodeTestForm 代码生成测试表
     * @return Response
     */
    @ApiOperation(value = "修改代码生成测试表", notes = "修改代码生成测试表")
    @PutMapping
    public Boolean updateById(@RequestBody @Validated(GenCodeTestForm.UpdateGenCodeTestValidate.class) GenCodeTestForm genCodeTestForm) {
        GenCodeTest genCodeTest = new GenCodeTest();
        BeanUtils.copyProperties(genCodeTestForm, genCodeTest);
        return genCodeTestService.updateById(genCodeTest);
    }

    /**
     * 通过id删除代码生成测试表
     *
     * @param idList
     * @return Response
     */
    @ApiOperation(value = "通过ids删除代码生成测试表", notes = "通过ids删除代码生成测试表")
    @DeleteMapping()
    public Boolean removeByIds(@RequestBody List<Long> idList) {

        return genCodeTestService.removeByIds(idList);
    }

}
