package com.example.demo.dao.initcomment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author dengzhewen
 * @create 2022-02-19 11:38
 * @Version v1.0.0
 */
@Mapper
public interface InitCommentDao extends BaseMapper {

    List<Map<String, String>> queryColumns(@Param("tableName") String tableName);

}
