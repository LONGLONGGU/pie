package com.framework.pie.admin.dictionary.controller;

import cn.hutool.core.util.StrUtil;
import com.framework.pie.admin.dictionary.model.SysDictionaryData;
import com.framework.pie.admin.dictionary.service.SysDictionaryDataService;
import com.framework.pie.admin.util.syslog.Log;
import com.framework.pie.http.HttpResult;
import com.framework.pie.mybatis.page.PageRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author houjh
 * @since 2021-09-06
 * @version v1.0
 */
@Api(tags = {""})
@RestController
@RequestMapping("/sysDictionaryData")
public class SysDictionaryDataController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysDictionaryDataService sysDictionaryDataService;

    /**
     * 查询分页数据
     */
    @PostMapping(value="/findPage")
    public HttpResult findPage(@RequestBody PageRequest pageRequest) {
        return HttpResult.ok(sysDictionaryDataService.findPage(pageRequest));
    }

    /**
     * 新增
     */
    @Log(value = "新增修改数据字典值信息")
    @ApiOperation(value = "新增修改数据")
    @PostMapping(value = "/saveData")
    public HttpResult save(@RequestBody SysDictionaryData record){
        return sysDictionaryDataService.addOrUpdate(record);
    }

    /**
     * 逻辑批量删除
     */
    @Log(value = "批量删除数据字典值信息")
    @ApiOperation(value = "删除数据")
    @PostMapping(value = "/batchDeleteData")
    public HttpResult batchDeleteData(@RequestBody Map map){
        if(map == null || map.size() == 0){
            return HttpResult.error("参数信息不能为空!");
        }
        if(StrUtil.isEmptyIfStr(map.get("ids"))){
            return HttpResult.error("必填参数[ids]不能为空!");
        }
        return sysDictionaryDataService.batchDeleteData(map.get("ids").toString());
    }

    /**
     * 删除
     */
    @Log(value = "删除数据字典值信息")
    @ApiOperation(value = "删除数据")
    @GetMapping(value = "/delete/{id}")
    public HttpResult delete(@PathVariable("id") String id){
        sysDictionaryDataService.removeById(id);
        return HttpResult.ok("删除成功");
    }

    /**
     * 根据code查询数据字典
     */
    @ApiOperation(value = "根据code查询数据字典")
    @GetMapping(value = "/findDicByCode")
    public HttpResult findDicByCode(@RequestParam String code){
        return HttpResult.ok(sysDictionaryDataService.findDicByCode(code));
    }
}
