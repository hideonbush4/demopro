package com.example.demo.service.impl.gencodetest;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.domain.entity.gencodetest.GenCodeTest;
import com.example.demo.dao.mysql.gencodetest.GenCodeTestMapper;
import com.example.demo.service.interfaces.gencodetest.GenCodeTestService;
import org.springframework.stereotype.Service;

/**
 * 代码生成测试表
 *
 * @author dengzhewen
 * @date 2022-03-02 20:07:04
 */
@Service
public class GenCodeTestServiceImpl extends ServiceImpl<GenCodeTestMapper, GenCodeTest> implements GenCodeTestService {

}
