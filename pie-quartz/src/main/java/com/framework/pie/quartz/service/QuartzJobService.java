package com.framework.pie.quartz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.framework.pie.http.HttpResult;
import com.framework.pie.mybatis.page.PageRequest;
import com.framework.pie.mybatis.page.PageResult;
import com.framework.pie.quartz.model.QuartzJob;
import com.framework.pie.mybatis.service.CurdService;

import java.util.List;

/**
 * <p>
 * job任务表 服务类
 * </p>
 *
 * @author longlong
 * @since 2021-08-16
 */
public interface QuartzJobService extends IService<QuartzJob> {
    /**
     * 启动停止Job
     * @param quartzJob
     * @return
     */
    int starOrStopJob(QuartzJob quartzJob);

    /**
     * 查询运行中的Job
     * @return
     */
    List<QuartzJob> findRunQuartzJobs();

    HttpResult addOrUpdate(QuartzJob quartzJob);

    int delete(QuartzJob record);

    int delete(List<QuartzJob> records);

    QuartzJob findById(String id);

    PageResult findPage(PageRequest pageRequest);

    HttpResult findTree();

}

