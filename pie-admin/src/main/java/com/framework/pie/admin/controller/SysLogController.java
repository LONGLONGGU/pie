package com.framework.pie.admin.controller;

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
}
