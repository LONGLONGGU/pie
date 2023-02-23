package com.framework.pie.admin.controller;

import com.framework.pie.admin.feign.BusDistrictFeignClient;
import com.framework.pie.http.HttpResult;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 行政区划控制器
 */

@Api(tags = "行政区划表")
@RestController
@RequestMapping("/district")
public class BusDistrictController {

    @Autowired
    public BusDistrictFeignClient busDistrictFeignClient;

    @GetMapping(value="/findTree")
    public HttpResult findTree() {
        return busDistrictFeignClient.findTree();
    }

    /**
     * 查询行政区划信息  组织机构维护的时候需要关联行政区划信息
     * @param
     *      parentId 父级ID信息
     * @return
     */
    @GetMapping(value="/findTree/{parentId}")
    public HttpResult asyncFindTree(@PathVariable("parentId") String parentId) {
        return busDistrictFeignClient.asyncFindTree(parentId);
    }
}
