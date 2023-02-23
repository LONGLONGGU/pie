package com.framework.pie.admin.controller;

import com.framework.pie.admin.model.SysMenu;
import com.framework.pie.admin.service.SysMenuService;
import com.framework.pie.admin.util.syslog.Log;
import com.framework.pie.http.HttpResult;
import com.framework.pie.web.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("menu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;


    @Log(value = "新增修改菜单")
    @PostMapping(value="/save")
    public HttpResult save(@RequestBody SysMenu record) {
        return HttpResult.ok(sysMenuService.addOrUpdate(record));
    }

    @Log(value = "删除菜单")
    @GetMapping(value = "/delete/{id}")
    public HttpResult delete(@PathVariable("id") String id){
        return HttpResult.ok(sysMenuService.removeById(id));
    }

    @GetMapping(value="/findNavTree")
    public HttpResult findNavTree(@RequestParam String userName) {
        return HttpResult.ok(sysMenuService.findTree(userName, 1));
    }

    @GetMapping(value="/findMenuTree")
    public HttpResult findMenuTree() {
        List<SysMenu> tree = sysMenuService.findTree(JwtUtils.getUsername(), 0);
        return HttpResult.ok(tree);
    }

}
