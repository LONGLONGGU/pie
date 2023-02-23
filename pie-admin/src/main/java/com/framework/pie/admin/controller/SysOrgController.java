package com.framework.pie.admin.controller;

import com.alibaba.fastjson.JSONArray;
import com.framework.pie.admin.constant.SysConstants;
import com.framework.pie.admin.model.*;
import com.framework.pie.admin.service.SysOrgService;
import com.framework.pie.admin.util.syslog.Log;
import com.framework.pie.http.HttpResult;
import com.framework.pie.mybatis.page.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 机构控制器
 */
@RestController
@RequestMapping("org")
public class SysOrgController {
    @Autowired
    private SysOrgService sysOrgService;

    @Log(value = "新增修改机构")
    @PostMapping(value="/save")
    public HttpResult save(@RequestBody SysOrg record){
        SysOrg org = sysOrgService.getById(record.getId());
        if(org != null) {
            if(SysConstants.SUPERADMIN.equalsIgnoreCase(org.getName())) {
                return HttpResult.error("机构超级管理员不允许修改!");
            }
            if(SysConstants.ADMIN.equalsIgnoreCase(org.getName())) {
                return HttpResult.error("机构系统管理员不允许修改!");
            }
        }
        return sysOrgService.addOrUpdate(record);
    }

    @PostMapping(value="/findPage")
    public HttpResult findPage(@RequestBody PageRequest pageRequest) {
        return HttpResult.ok(sysOrgService.findPage(pageRequest));
    }

    @GetMapping("/findOrgMenus")
    public HttpResult findOrgMenus(@RequestParam String orgId){
        return HttpResult.ok(sysOrgService.findOrgMenus(orgId));
    }

    @Log(value = "操作机构权限")
    @PostMapping(value = "/saveOrgMenus")
    public HttpResult saveOrgMenus(@RequestBody Map<String,Object> models){
        String orgId = models.get("orgId").toString();
        List<SysOrgMenu> records = JSONArray.parseArray(JSONArray.toJSONString(models.get("orgMenus")),SysOrgMenu.class);
        for(SysOrgMenu record:records) {
            SysOrg sysOrg = sysOrgService.getById(record.getOrgId());
            if(SysConstants.ADMIN.equalsIgnoreCase(sysOrg.getName())) {
                // 如果是超级管理员，不允许修改
                return HttpResult.error("超级管理员拥有所有菜单权限，不允许修改！");
            }
        }
        return HttpResult.ok(sysOrgService.saveRoleMenus(orgId,records));
    }

    /**
     * 删除机构信息
     * @param id
     * @return
     */
    @Log(value = "删除机构信息")
    @GetMapping(value = "/delete/{id}")
    public HttpResult delete(@PathVariable("id") String id){
        return HttpResult.ok(sysOrgService.removeOrg(id));
    }
}
