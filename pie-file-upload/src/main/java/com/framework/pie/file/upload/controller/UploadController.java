package com.framework.pie.file.upload.controller;

import com.framework.pie.file.upload.model.SysAttachments;
import com.framework.pie.file.upload.service.UploadService;
import com.framework.pie.http.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@RestController
public class UploadController {
    @Autowired
    private UploadService uploadService;

    /**
     * feign请求调用文件上传服务
     * @param userId
     * @param serverCode
     * @param file
     * @return
     */
    @PostMapping("/upload")
    @ResponseBody
    public HttpResult upload(@RequestParam("userId") String userId,@RequestParam("serverCode") String serverCode,
                             @RequestParam("file") MultipartFile file){
        return uploadService.upload(userId,serverCode,"commonFile",file);
    }

    /**
     * 前端直接调用文件上传服务上传文件
     * @param serverCode 服务code信息,详细请参考ServerTypeCode枚举类信息
     * @param file 文件信息
     * @return
     */
    @PostMapping("/uploadServer")
    @ResponseBody
    public HttpResult uploadServer(@RequestParam("serverCode") String serverCode,@RequestParam("category") String category
            ,@RequestParam("file") MultipartFile file){
        return uploadService.upload(serverCode,category,file);
    }

    /**
     * 文件下载
     * @param response
     * @param fileId
     */
    @GetMapping("/download")
    @ResponseBody
    public void download(HttpServletResponse response, @RequestParam("fileId") String fileId){
        uploadService.download(response,fileId);
    }
    /**
     * @Author: guanqiangzhang
     * @Date:9:47 2022/2/9
     *  附件信息
     *
     */
    @GetMapping("/filesInfo")
    @ResponseBody
    public HttpResult filesInfo(@RequestParam("fileId") String fileId){
        SysAttachments sysAttachments = uploadService.filesInfo(fileId);
        HttpResult httpResult=new HttpResult();
        httpResult.setMsg(sysAttachments.getFilePath());
        httpResult.setData(sysAttachments);
        return httpResult;
    }
    /**
     * feign远程调用删除文件
     * @param userId
     * @param fileId
     * @return
     */
    @GetMapping("/delete")
    @ResponseBody
    public HttpResult delete(@RequestParam("userId") String userId,@RequestParam("fileId") String fileId){
        return uploadService.delete(userId,fileId);
    }

    /**
     * 直接调用文件上传服务删除文件
     * @param fileId
     * @return
     */
    @GetMapping("/deleteServer")
    @ResponseBody
    public HttpResult delete(@RequestParam("fileId") String fileId){
        return uploadService.delete(fileId);
    }
}
