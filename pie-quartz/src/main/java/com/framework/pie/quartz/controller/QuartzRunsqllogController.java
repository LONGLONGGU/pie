package com.framework.pie.quartz.controller;

import com.baomidou.mybatisplus.extension.api.R;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.framework.pie.quartz.service.QuartzRunsqllogService;
import com.framework.pie.quartz.model.QuartzRunsqllog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import com.framework.pie.http.HttpResult;
import com.framework.pie.mybatis.page.PageRequest;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author houjh
 * @since 2021-09-09
 * @version v1.0
 */
@Api(tags = {""})
@RestController
@RequestMapping("/quartzRunsqllog")
public class QuartzRunsqllogController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private QuartzRunsqllogService quartzRunsqllogService;

    /**
     * 查询分页数据
     */
    @ApiOperation(value = "查询分页数据")
    @GetMapping(value = "/findPage")
    public HttpResult findPage(@RequestBody PageRequest pageRequest){
      return HttpResult.ok(quartzRunsqllogService.findPage(pageRequest));
    }

    /**
     * 新增
     */
    @ApiOperation(value = "新增修改数据")
    @PostMapping(value = "/save")
    public HttpResult save(@RequestBody QuartzRunsqllog record){//
       return HttpResult.ok(quartzRunsqllogService.saveByNativeSql(record));
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除数据")
    @DeleteMapping(value = "/delete")
    public HttpResult delete(@RequestBody List<QuartzRunsqllog> records){
        return HttpResult.ok(quartzRunsqllogService.delete(records));
    }

}
