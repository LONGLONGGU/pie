package com.framework.pie.admin.controller;

import com.framework.pie.admin.service.SysCodeService;
import com.framework.pie.core.http.HttpResult;
import com.framework.pie.core.page.PageRequest;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/code")
public class SysCodeController {
    @Autowired
    private SysCodeService sysCodeService;

    @ApiOperation("查询系统表")
    @PostMapping(value="/findPage")
    public HttpResult findPage(@RequestBody PageRequest pageRequest) {
        return HttpResult.ok(sysCodeService.findPage(pageRequest));
    }

    @ApiOperation("生成代码")
    @PostMapping(value = "/generator")
    public HttpResult generator(@RequestBody Map<String,Object> record){
        return  HttpResult.ok(sysCodeService.generator(record));
    }


}
