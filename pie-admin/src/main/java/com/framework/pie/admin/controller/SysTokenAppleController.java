package com.framework.pie.admin.controller;
import cn.hutool.core.util.StrUtil;
import com.framework.pie.admin.service.SysTokenAppleService;
import com.framework.pie.admin.model.SysTokenApple;
import com.framework.pie.http.HttpResult;
import com.framework.pie.mybatis.page.PageRequest;
import com.framework.pie.mybatis.page.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* token申请表 前端控制器
* </p>
*
* @author longlong
* @since 2022-08-18
*/

@Api(tags = "token申请表")
@RestController
@RequestMapping("/sysTokenApple")
public class SysTokenAppleController {

    @Autowired
    private SysTokenAppleService sysTokenAppleService;

    /**
    * 新增和修改
    */
    @ApiOperation(value = "新增修改token申请表")
    @PostMapping(value = "/addOrUpdate")
    public HttpResult addOrUpdate(@RequestBody SysTokenApple sysTokenApple){
        return sysTokenAppleService.addOrUpdate(sysTokenApple);
    }

    /**
    * 根据id逻辑删除一条数据
    */
    @ApiOperation(value = "根据单个id逻辑删除")
    @GetMapping("/delete/{id}")
    public HttpResult delete(@PathVariable("id") String id){
        return sysTokenAppleService.delete(id);
    }

    /**
    * 根据ids批量逻辑删除数据
    */
    @ApiOperation(value = "根据ids逻辑删除")
    @GetMapping("/batchDelete")
    public HttpResult batchDelete(@RequestBody Map map){
        if(map == null || map.size() == 0){
            return HttpResult.error("参数信息不能为空!");
        }
        if(StrUtil.isEmptyIfStr(map.get("ids"))){
            return HttpResult.error("必填参数[ids]不能为空!");
        }
        return sysTokenAppleService.batchDelete(map.get("ids").toString());
    }

    /**
    * 列表（分页）
    */
    @ApiOperation(value = "列表（分页）")
    @PostMapping("/findPage")
    public HttpResult findPage(@RequestBody PageRequest pageRequest){
        PageResult page = sysTokenAppleService.findPage(pageRequest);
        return HttpResult.ok(page);
    }

    /**
    * 根据ID查询详情
    */
    @ApiOperation(value = "详情")
    @GetMapping("/get/{id}")
    public HttpResult get(@PathVariable("id") String id){
        SysTokenApple sysTokenApple = sysTokenAppleService.getById(id);
        return HttpResult.ok(sysTokenApple);
    }
    /**
     * token申请
     */
    @ApiOperation(value = "token申请")
    @PostMapping("/tokenApply")
    public HttpResult tokenApply(@RequestBody Map<String, String> parms){
        return sysTokenAppleService.tokenApply(parms);
    }


}
