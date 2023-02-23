package com.framework.pie.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.framework.pie.admin.model.SysArticle;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
    * 文章  Mapper 接口
    * </p>
 *
 * @author longlong
 * @since 2020-09-25
 */
@Mapper
public interface SysArticleMapper extends BaseMapper<SysArticle> {

    int deleteByPrimaryKey(String id);

    int insert(SysArticle record);

    int insertSelective(SysArticle record);

    SysArticle selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysArticle record);

    int updateByPrimaryKey(SysArticle record);

    List<SysArticle> findPage();

    List<SysArticle> findAll();

}
