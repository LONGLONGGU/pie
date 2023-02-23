package com.framework.pie.admin.controller;

import com.alibaba.fastjson.JSONArray;
import com.framework.pie.admin.constant.SysConstants;
import com.framework.pie.admin.model.SysRole;
import com.framework.pie.admin.model.SysRoleMenu;
import com.framework.pie.admin.service.SysRoleService;
import com.framework.pie.admin.util.syslog.Log;
import com.framework.pie.http.HttpResult;
import com.framework.pie.mybatis.page.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 角色控制器
 */
@RestController
@RequestMapping("role")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @Log(value = "新增修改角色")
    @PostMapping(value="/save")
    public HttpResult save(@RequestBody SysRole record) {
        return sysRoleService.addOrUpdate(record);
    }

    @Log(value = "删除角色")
    @GetMapping(value="/delete/{id}")
    public HttpResult delete(@PathVariable("id") String id) {
       return sysRoleService.delete(id);
    }

    @PostMapping(value="/findPage")
    public HttpResult findPage(@RequestBody PageRequest pageRequest) {
        return HttpResult.ok(sysRoleService.findPage(pageRequest));
    }

    @GetMapping(value="/findAll")
    public HttpResult findAll() {
        return HttpResult.ok(sysRoleService.findByOrgId());
    }

    @GetMapping(value="/findRoleMenus")
    public HttpResult findRoleMenus(@RequestParam String roleId) {
        return HttpResult.ok(sysRoleService.findRoleMenus(roleId));
    }

    @PostMapping(value="/saveRoleMenus")
    public HttpResult saveRoleMenus(@RequestBody Map<String, Object> models) {
        String roleId = models.get("roleId").toString();
        List<SysRoleMenu> records = JSONArray.parseArray(JSONArray.toJSONString(models.get("roleMenus")),SysRoleMenu.class);
        for(SysRoleMenu record:records) {
            SysRole sysRole = sysRoleService.getById(record.getRoleId());
            if(SysConstants.SUPERADMIN.equalsIgnoreCase(sysRole.getName())) {
                // 如果是超级管理员，不允许修改
                return HttpResult.error("超级管理员拥有所有菜单权限，不允许修改！");
            }
            if(SysConstants.ADMIN.equalsIgnoreCase(sysRole.getName())) {
                // 如果是超级管理员，不允许修改
                return HttpResult.error("系统管理员拥有所有菜单权限，不允许修改！");
            }
        }
        return sysRoleService.saveRoleMenus(roleId,records);
    }
}
