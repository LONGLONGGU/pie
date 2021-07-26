package com.framework.pie.admin.controller;


import com.framework.pie.admin.model.SysArticle;
import com.framework.pie.admin.model.SysAttachments;
import com.framework.pie.admin.service.SysArticleService;
import com.framework.pie.admin.service.SysAttachmentsService;
import com.framework.pie.admin.service.UploadService;
import com.framework.pie.http.HttpResult;
import com.framework.pie.mybatis.page.PageRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
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
    private UploadService uploadService;
    @Autowired
    private SysAttachmentsService sysAttachmentsService;

    @PostMapping("/uploadFiles")
    public HttpResult uploadImage(@RequestParam("file") MultipartFile file){
        return uploadService.upload(file);
    }
    @GetMapping("/remove/{fileId}")
    public HttpResult remove(@PathVariable("fileId") long fileId){
        SysAttachments sysAttachments =  sysAttachmentsService.findById(fileId);
        return HttpResult.ok(sysAttachmentsService.delete(sysAttachments));
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
    public HttpResult findByArticle(@PathVariable("articleId") long articleId){
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
    @GetMapping("/download")
    @ResponseBody
    public HttpResult download(HttpServletResponse response, @RequestParam Long fileId){
        SysAttachments sysAttachments = sysAttachmentsService.findById(fileId);
        String filename = sysAttachments.getUuid();
        String filePath = sysAttachments.getFilePath();
        File file = new File(filePath);
        if (file.exists()) { //判断文件父目录是否存在
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + filename);
            try {
                response.setHeader("Content-Disposition", "attachment;fileName=" + java.net.URLEncoder.encode(filename, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            byte[] buffer = new byte[1024];
            FileInputStream fis = null; //文件输入流
            BufferedInputStream bis = null;

            OutputStream os = null; //输出流
            try {
                os = response.getOutputStream();
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer);
                    i = bis.read(buffer);
                }
                os.close();
                fis.close();
                return null;

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return HttpResult.error("系统异常");
    }

}
