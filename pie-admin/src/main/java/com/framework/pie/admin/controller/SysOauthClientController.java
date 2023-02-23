package com.framework.pie.admin.controller;

import com.framework.pie.admin.service.SysOauthClientService;
import com.framework.pie.http.HttpResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 客户端信息表 前端控制器
 * </p>
 *
 * @author houjh
 * @since 2021-08-04
 * @version v1.0
 */
@Api(tags = {"客户端信息表"})
@RestController
@RequestMapping("/sysOauthClient")
public class SysOauthClientController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysOauthClientService sysOauthClientService;

    /**
     * 通过clientId查询客户端信息
     */
    @ApiOperation(value = "通过clientId查询客户端信息")
    @GetMapping(value = "/getByClientId")
    public HttpResult getByClientId(@RequestParam String clientId){
        return HttpResult.ok(sysOauthClientService.getByClientId(clientId));
    }
}
