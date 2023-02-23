package com.framework.pie.admin.controller;


import com.framework.pie.admin.feign.FileUploadFeignClient;
import com.framework.pie.admin.model.SysArticle;
import com.framework.pie.admin.service.SysArticleService;
import com.framework.pie.constant.ServerTypeCode;
import com.framework.pie.http.HttpResult;
import com.framework.pie.mybatis.page.PageRequest;
import com.framework.pie.web.utils.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 文章  前端控制器
 * </p>
 *
 * @author longlong
 * @since 2020-09-25
 * @version v1.0
 */
@Api(tags = {"文章"})
@RestController
@RequestMapping("/article")
public class SysArticleController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private SysArticleService sysArticleService;
    @Autowired
    private FileUploadFeignClient fileUploadFeignClient;

    @PostMapping("/uploadFiles")
    public HttpResult uploadImage(@RequestParam("file") MultipartFile file){
        return fileUploadFeignClient.upload(JwtUtils.getUserId(),ServerTypeCode.ADMIN_FILE.getType(),file);
    }

    /**
     * 查询分页数据
     */
    @ApiOperation(value = "查询分页数据")
    @PostMapping(value = "/findPage")
    public HttpResult findPage(@RequestBody PageRequest pageRequest){
      return HttpResult.ok(sysArticleService.findPage(pageRequest));
    }

    @GetMapping("/{articleId}")
    public HttpResult findByArticle(@PathVariable("articleId") String articleId){
        return HttpResult.ok(sysArticleService.findById(articleId));
    }
    /**
     * 新增
     */
    @ApiOperation(value = "新增修改数据")
    @PostMapping(value = "/add")
    public HttpResult add(@RequestBody SysArticle record){
       return HttpResult.ok(sysArticleService.saveArticle(record));
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除数据")
    @PostMapping(value = "/delete")
    public HttpResult delete(@RequestBody List<SysArticle> records){

        return HttpResult.ok(sysArticleService.delete(records));
    }
}
