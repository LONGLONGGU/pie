package com.framework.pie.admin.controller;

import com.alibaba.fastjson.JSONArray;
import com.framework.pie.admin.constant.SysConstants;
import com.framework.pie.admin.dao.SysRoleMapper;
import com.framework.pie.admin.model.SysOrg;
import com.framework.pie.admin.model.SysRole;
import com.framework.pie.admin.model.SysRoleMenu;
import com.framework.pie.admin.service.SysOrgService;
import com.framework.pie.admin.service.SysRoleService;
import com.framework.pie.admin.util.syslog.Log;
import com.framework.pie.http.HttpResult;
import com.framework.pie.mybatis.page.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysOrgService sysOrgService;

    @Log(value = "新增修改角色")
    @PreAuthorize("hasAuthority('sys:role:add') AND hasAuthority('sys:role:edit')")
    @PostMapping(value="/save")
    public HttpResult save(@RequestBody SysRole record) {
        SysRole role = sysRoleService.findById(record.getId());
        if(role != null) {
            if(SysConstants.SUPERADMIN.equalsIgnoreCase(role.getName())) {
                return HttpResult.error("超级管理员不允许修改!");
            }
            if(SysConstants.ADMIN.equalsIgnoreCase(role.getName())) {
                return HttpResult.error("系统管理员不允许修改!");
            }
        }
        // 新增角色
        if((record.getId() == null || record.getId() == 0) && !sysRoleService.findByName(record.getName()).isEmpty()) {
            return HttpResult.error("角色名已存在!");
        }
        SysOrg sysOrg = sysOrgService.findByOrg();
        record.setOrgId(sysOrg.getId());
        return HttpResult.ok(sysRoleService.save(record));
    }

    @Log(value = "删除角色")
    @PreAuthorize("hasAuthority('sys:role:delete')")
    @PostMapping(value="/delete")
    public HttpResult delete(@RequestBody List<SysRole> records) {
        int result = sysRoleService.delete(records);
        if (result ==1){
          return  HttpResult.error("角色被已经被使用，无法删除！");
        }
        return HttpResult.ok(result);
    }

    @PreAuthorize("hasAuthority('sys:role:view')")
    @PostMapping(value="/findPage")
    public HttpResult findPage(@RequestBody PageRequest pageRequest) {
        return HttpResult.ok(sysRoleService.findPage(pageRequest));
    }

    @PreAuthorize("hasAuthority('sys:role:view')")
    @GetMapping(value="/findAll")
    public HttpResult findAll() {
        return HttpResult.ok(sysRoleService.findByOrgId());
    }

    @PreAuthorize("hasAuthority('sys:role:view')")
    @GetMapping(value="/findRoleMenus")
    public HttpResult findRoleMenus(@RequestParam Long roleId) {
        return HttpResult.ok(sysRoleService.findRoleMenus(roleId));
    }

    @PreAuthorize("hasAuthority('sys:role:view')")
    @PostMapping(value="/saveRoleMenus")
    public HttpResult saveRoleMenus(@RequestBody Map<String, Object> models) {
        Long roleId = Long.parseLong(models.get("roleId").toString());
        List<SysRoleMenu> records = JSONArray.parseArray(JSONArray.toJSONString(models.get("roleMenus")),SysRoleMenu.class);
        for(SysRoleMenu record:records) {
            SysRole sysRole = sysRoleMapper.selectByPrimaryKey(record.getRoleId());
            if(SysConstants.SUPERADMIN.equalsIgnoreCase(sysRole.getName())) {
                // 如果是超级管理员，不允许修改
                return HttpResult.error("超级管理员拥有所有菜单权限，不允许修改！");
            }
            if(SysConstants.ADMIN.equalsIgnoreCase(sysRole.getName())) {
                // 如果是超级管理员，不允许修改
                return HttpResult.error("系统管理员拥有所有菜单权限，不允许修改！");
            }
        }
        return HttpResult.ok(sysRoleService.saveRoleMenus(roleId,records));
    }
}
