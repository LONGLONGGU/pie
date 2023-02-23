package com.framework.pie.admin.controller;

import com.framework.pie.admin.model.SysLog;
import com.framework.pie.admin.service.SysLogService;
import com.framework.pie.http.HttpResult;
import com.framework.pie.mybatis.page.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 系统日志控制器
 */
@RestController
@RequestMapping(value = "log")
public class SysLogController {
    @Autowired
    private SysLogService sysLogService;

    @PostMapping(value = "/findPage")
    public HttpResult findPage(@RequestBody PageRequest pageRequest){
        return HttpResult.ok(sysLogService.findPage(pageRequest));
    }

    /**
     * 保存日志信息
     * @param sysLog
     * @return
     */
    @PostMapping(value = "/addOrUpdate")
    public HttpResult addOrUpdate(@RequestBody SysLog sysLog){
        boolean save = sysLogService.save(sysLog);
        return HttpResult.ok("日志保存成功");
    }
}
