package com.framework.pie.admin.dictionary.controller;

import com.framework.pie.admin.dictionary.dto.DictValidateDTO;
import com.framework.pie.admin.dictionary.model.SysDictionary;
import com.framework.pie.admin.dictionary.service.SysDictionaryService;
import com.framework.pie.admin.util.syslog.Log;
import com.framework.pie.http.HttpResult;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author houjh
 * @since 2021-09-06
 * @version v1.0
 */
@RestController
@RequestMapping("/sysDictionary")
public class SysDictionaryController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysDictionaryService sysDictionaryService;

    /**
     * 新增
     */
    @Log(value = "新增修改数据字典")
    @ApiOperation(value = "新增修改数据")
    @PostMapping(value = "/saveAdd")
    public HttpResult save(@RequestBody SysDictionary record){
        return sysDictionaryService.addOrUpdate(record);
    }

    /**
     * 删除
     */
    @Log(value = "删除数据字典")
    @ApiOperation(value = "删除数据")
    @GetMapping(value = "/delete/{id}")
    public HttpResult delete(@PathVariable String id){
        return sysDictionaryService.delete(id);
    }

    /**
     * 异步加载数据字典tree信息
     * @param parentId
     * @return
     */
    @ApiOperation("异步加载数据字典tree")
    @GetMapping(value="/asyncFindTree/{parentId}")
    public HttpResult asyncFindTree(@PathVariable("parentId") String parentId) {
        return HttpResult.ok(sysDictionaryService.asyncFindTree(parentId));
    }

    /**
     * 验证数据字典code是否重复
     * @param
     *      dictValidateDto
     *         id id信息
     *         code 字典code信息
     * @return
     */
    @ApiOperation("验证数据字典code是否重复")
    @PostMapping(value="/validateCode")
    public HttpResult validateCode(@RequestBody DictValidateDTO dictValidateDto){
        return sysDictionaryService.validateCode(dictValidateDto.getId(),dictValidateDto.getCode());
    }
}
