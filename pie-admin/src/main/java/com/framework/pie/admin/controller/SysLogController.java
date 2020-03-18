package com.framework.pie.admin.controller;

import com.framework.pie.admin.service.SysLogService;
import com.framework.pie.core.http.HttpResult;
import com.framework.pie.core.page.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统日志控制器
 */
@RestController
@RequestMapping(value = "log")
public class SysLogController {
    @Autowired
    private SysLogService sysLogService;

    @RequestMapping(value = "/findPage")
    public HttpResult findPage(@RequestBody PageRequest pageRequest){
        return HttpResult.ok(sysLogService.findPage(pageRequest));
    }
}
