package com.framework.pie.quartz.dao;

import com.framework.pie.quartz.model.QuartzJob;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * job任务表 Mapper 接口
 * </p>
 *
 * @author longlong
 * @since 2021-08-16
 */
@Mapper
public interface QuartzJobMapper extends BaseMapper<QuartzJob> {

    QuartzJob selectByPrimaryKey(String id);

    List<QuartzJob> findPage();

    List<QuartzJob> findAll();

    List<QuartzJob> findRunQuartzJobs();

}
