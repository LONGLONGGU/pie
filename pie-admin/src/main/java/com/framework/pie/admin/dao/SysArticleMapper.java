package com.framework.pie.admin.dao;

import com.framework.pie.admin.model.SysArticle;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * <p>
    * 文章  Mapper 接口
    * </p>
 *
 * @author longlong
 * @since 2020-09-25
 */
@Repository
public interface SysArticleMapper extends BaseMapper<SysArticle> {

    int deleteByPrimaryKey(Long id);

    int insert(SysArticle record);

    int insertSelective(SysArticle record);

    SysArticle selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysArticle record);

    int updateByPrimaryKey(SysArticle record);

    List<SysArticle> findPage();

    List<SysArticle> findAll();

}
