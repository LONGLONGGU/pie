package com.framework.pie.business.wechat.controller;


import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.framework.pie.business.wechat.service.BusWechatmenuService;
import com.framework.pie.business.wechat.model.BusWechatmenu;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;
import com.framework.pie.core.http.HttpResult;
import com.framework.pie.core.page.PageRequest;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

/**
 * <p>
    * 公众号菜单  前端控制器
    * </p>
 *
 * @author longlong
 * @since 2020-09-25
 * @version v1.0
 */
@Api(tags = {"公众号菜单"})
@RestController
@RequestMapping("wechatmenu")
public class BusWechatmenuController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private BusWechatmenuService busWechatmenuService;

    /**
     * 查询分页数据
     */
    @ApiOperation(value = "查询分页数据")
    @PostMapping(value = "/findPage")
    public HttpResult findPage(@RequestBody PageRequest pageRequest){
      return HttpResult.ok(busWechatmenuService.findPage(pageRequest));
    }
    /**
     * 新增
     */
    @ApiOperation(value = "新增修改数据")
    @PostMapping(value = "/add")
    public HttpResult add(@RequestBody BusWechatmenu record){
       BusWechatmenu busWechatmenu = busWechatmenuService.findMenu(record.getWechatinfoId()+"");
       if (null != busWechatmenu){
           record.setId(busWechatmenu.getId());
       }
       return HttpResult.ok(busWechatmenuService.save(record));
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除数据")
    @PostMapping(value = "/delete")
    public HttpResult delete(@RequestBody List<BusWechatmenu> records){

        return HttpResult.ok(busWechatmenuService.delete(records));
    }
    /**
     * 删除
     */
    @ApiOperation(value = "查询菜单信息")
    @GetMapping(value = "/findMenu/{wechatinfoId}")
    public  HttpResult findMenu(@PathVariable("wechatinfoId") String wechatinfoId){
        System.out.println(wechatinfoId);
        return HttpResult.ok(busWechatmenuService.findMenu(wechatinfoId));
    }

}
