package com.framework.pie.business.wechat.controller;


import com.framework.pie.business.wechat.model.BusWechatuser;
import com.framework.pie.business.wechat.service.BusWechatuserService;
import com.framework.pie.http.HttpResult;
import com.framework.pie.mybatis.page.PageRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
    * 微信用户  前端控制器
    * </p>
 *
 * @author longlong
 * @since 2020-09-25
 * @version v1.0
 */
@Api(tags = {"微信用户 "})
@RestController
@RequestMapping("/bus-wechatuser")
public class BusWechatuserController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private BusWechatuserService busWechatuserService;

    /**
     * 查询分页数据
     */
    @ApiOperation(value = "查询分页数据")
    @PostMapping(value = "/findPage")
    public HttpResult findPage(@RequestBody PageRequest pageRequest){
      return HttpResult.ok(busWechatuserService.findPage(pageRequest));
    }
    /**
     * 新增
     */
    @ApiOperation(value = "新增修改数据")
    @PostMapping(value = "/add")
    public HttpResult add(@RequestBody BusWechatuser record){
       return HttpResult.ok(busWechatuserService.saveByNativeSql(record));
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除数据")
    @PostMapping(value = "/delete")
    public HttpResult delete(@RequestBody List<BusWechatuser> records){

        return HttpResult.ok(busWechatuserService.delete(records));
    }

}
