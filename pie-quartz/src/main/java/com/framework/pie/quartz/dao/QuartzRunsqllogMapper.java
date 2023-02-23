package com.framework.pie.quartz.dao;

import com.framework.pie.quartz.model.QuartzRunsqllog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author houjh
 * @since 2021-09-09
 */
@Mapper
public interface QuartzRunsqllogMapper extends BaseMapper<QuartzRunsqllog> {

    int deleteByPrimaryKey(String id);

    int insert(QuartzRunsqllog record);

    int insertSelective(QuartzRunsqllog record);

    QuartzRunsqllog selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(QuartzRunsqllog record);

    int updateByPrimaryKey(QuartzRunsqllog record);

    List<QuartzRunsqllog> findPage();

    List<QuartzRunsqllog> findAll();

}
