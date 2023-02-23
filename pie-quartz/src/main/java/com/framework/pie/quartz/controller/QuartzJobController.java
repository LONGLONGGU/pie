package com.framework.pie.quartz.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.framework.pie.quartz.service.QuartzJobService;
import com.framework.pie.quartz.model.QuartzJob;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import com.framework.pie.http.HttpResult;
import com.framework.pie.mybatis.page.PageRequest;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * job任务表 前端控制器
 * </p>
 *
 * @author longlong
 * @since 2021-08-16
 * @version v1.0
 */
@Api(tags = {"job任务表"})
@RestController
@RequestMapping("/quartz")
public class QuartzJobController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private QuartzJobService quartzJobService;

    /**
     * 查询分页数据
     */
    @ApiOperation(value = "查询分页数据")
    @PostMapping(value = "/findPage")
    public HttpResult findPage(@RequestBody PageRequest pageRequest){
      return HttpResult.ok(quartzJobService.findPage(pageRequest));
    }

    /**
     * 新增
     */
    @ApiOperation(value = "新增修改数据")
    @PostMapping(value = "/save")
    public HttpResult save(@RequestBody QuartzJob record){
       return quartzJobService.addOrUpdate(record);
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除数据")
    @PostMapping(value = "/delete")
    public HttpResult delete(@RequestBody List<QuartzJob> records){
        return HttpResult.ok(quartzJobService.delete(records));
    }

    /**
     * 启动停止Job
     * @param record
     * @return
     */
    @ApiOperation(value = "启动停止Job")
    @PostMapping(value = "/starOrStopJob")
    public HttpResult starOrStopJob(@RequestBody QuartzJob record){

        return HttpResult.ok(quartzJobService.starOrStopJob(record));
    }

}
