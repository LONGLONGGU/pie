package com.framework.pie.admin.controller;

import com.framework.pie.admin.model.SysDict;
import com.framework.pie.admin.service.SysDictService;
import com.framework.pie.core.http.HttpResult;
import com.framework.pie.core.page.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 字典Controller
 */
@RestController
@RequestMapping("dict")
public class SysDictController {
    @Autowired
    private SysDictService sysDictService;

    @PreAuthorize("hasAuthority('sys:dict:add') and hasAnyAuthority('sys:dict:edit')")
    @PostMapping(value="/save")
    public HttpResult save(@RequestBody SysDict record) {
        return HttpResult.ok(sysDictService.save(record));
    }

    @PreAuthorize("hasAuthority('sys:dict:delete')")
    @PostMapping(value="/delete")
    public HttpResult delete(@RequestBody List<SysDict> records) {
        return HttpResult.ok(sysDictService.delete(records));
    }

    @PreAuthorize("hasAuthority('sys:dict:view')")
    @PostMapping(value="/findPage")
    public HttpResult findPage(@RequestBody PageRequest pageRequest) {
        return HttpResult.ok(sysDictService.findPage(pageRequest));
    }
    @PreAuthorize("hasAuthority('sys:dict:view')")
    @GetMapping(value="/findByLable")
    public HttpResult findByLable(@RequestParam String lable) {
        return HttpResult.ok(sysDictService.findByLable(lable));
    }
}
