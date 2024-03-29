package com.framework.pie.admin.controller;

import com.framework.pie.admin.model.SysDict;
import com.framework.pie.admin.service.SysDictService;
import com.framework.pie.http.HttpResult;
import com.framework.pie.mybatis.page.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping(value="/findPaged")
    public HttpResult findPaged(@RequestBody PageRequest pageRequest) {
        return HttpResult.ok(sysDictService.findPage(pageRequest));
    }

    @PostMapping(value="/save")
    public HttpResult save(@RequestBody SysDict record) {
        return HttpResult.ok(sysDictService.saveByNativeSql(record));
    }

    @PostMapping(value="/delete")
    public HttpResult delete(@RequestBody List<SysDict> records) {
        return HttpResult.ok(sysDictService.delete(records));
    }

    @PostMapping(value="/findPage")
    public HttpResult findPage(@RequestBody PageRequest pageRequest) {
        return HttpResult.ok(sysDictService.findPage(pageRequest));
    }

    @GetMapping(value="/findByLable")
    public HttpResult findByLable(@RequestParam String lable) {
        return HttpResult.ok(sysDictService.findByLable(lable));
    }

    @GetMapping(value="/findTypes")
    public HttpResult findTypes(){
        return HttpResult.ok(sysDictService.findTypes());
    }

    @GetMapping(value="/findByType")
    public HttpResult findByType(@RequestParam String type){
        return HttpResult.ok(sysDictService.findByLable(type));
    }
}
