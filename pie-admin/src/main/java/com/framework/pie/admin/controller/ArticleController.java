package com.framework.pie.admin.controller;

import com.framework.pie.admin.model.SysAttachments;
import com.framework.pie.admin.service.SysAttachmentsService;
import com.framework.pie.admin.service.UploadService;
import com.framework.pie.core.http.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
@RequestMapping("article")
public class ArticleController {
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
