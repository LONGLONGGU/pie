package com.framework.pie.file.upload.controller.app;

import com.framework.pie.file.upload.service.UploadService;
import com.framework.pie.http.HttpResult;
import com.framework.pie.web.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author ：longlong
 * @date ：Created in 2022/1/7 14:58
 * @modified By：
 * @version: ：V1.0
 */
@RestController
public class UploadAppController {
    @Autowired
    private UploadService uploadService;


    @PostMapping("/upload/app")
    @ResponseBody
    public HttpResult upload(@RequestParam("file") MultipartFile file){
        return uploadService.upload(JwtUtils.getUserId(),"APP","commonFile",file);
    }
}
