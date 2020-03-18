package com.framework.pie.admin.controller;

import com.framework.pie.admin.model.SysMenu;
import com.framework.pie.admin.service.SysMenuService;
import com.framework.pie.admin.util.SecurityUtils;
import com.framework.pie.admin.util.syslog.Log;
import com.framework.pie.core.http.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("menu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;


    @Log(value = "新增修改菜单")
    @PreAuthorize("hasAuthority('sys:menu:add') AND hasAuthority('sys:menu:edit')")
    @PostMapping(value="/save")
    public HttpResult save(@RequestBody SysMenu record) {
        return HttpResult.ok(sysMenuService.save(record));
    }

    @PreAuthorize("hasAuthority('sys:menu:view')")
    @GetMapping(value="/findNavTree")
    public HttpResult findNavTree(@RequestParam String userName) {
        return HttpResult.ok(sysMenuService.findTree(userName, 1));
    }

    @PreAuthorize("hasAuthority('sys:menu:view')")
    @GetMapping(value="/findMenuTree")
    public HttpResult findMenuTree() {
        List<SysMenu> tree = sysMenuService.findTree(SecurityUtils.getUsername(), 0);
        return HttpResult.ok(tree);
    }

}
