package com.framework.pie.admin.controller;

import com.framework.pie.admin.feign.FileUploadFeignClient;
import com.framework.pie.admin.util.syslog.Log;
import com.framework.pie.constant.ServerTypeCode;
import com.framework.pie.http.HttpResult;
import com.framework.pie.web.utils.JwtUtils;
import feign.Response;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@RestController
@RequestMapping("/fileUpload")
public class  FileUploadController {

    @Autowired
    private FileUploadFeignClient fileUploadFeignClient;

    /**
     * 文件上传
     * @param file
     * @return
     */
    @Log(value = "文件上传")
    @ApiOperation(value = "文件上传接口")
    @PostMapping(value = "/upload")
    public HttpResult upload(@RequestParam("file") MultipartFile file){
        return fileUploadFeignClient.upload(JwtUtils.getUserId(),ServerTypeCode.ADMIN_FILE.getType(), file);
    }

    /**
     * 文件下载
     * @param response
     * @param fileId
     */
    @ApiOperation(value = "文件下载")
    @GetMapping(value = "/download")
    public void download(HttpServletResponse response, @RequestParam String fileId){
        InputStream inputStream = null;
        OutputStream outputStream=null;
        try {
            // feign文件下载
            Response responseFromRemote = fileUploadFeignClient.download(fileId);
            Response.Body body = responseFromRemote.body();
            inputStream = body.asInputStream();
            outputStream = response.getOutputStream();
            byte[] buffer = new byte[1024 * 8];
            int count = 0;
            while ((count = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, count);
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 文件删除
     * @param fileId 文件ID信息
     * @return
     */
    @Log(value = "文件删除")
    @ApiOperation(value = "文件上传接口")
    @GetMapping(value = "/delete/{fileId}")
    public HttpResult delete(@PathVariable("fileId") String fileId){
        return fileUploadFeignClient.delete(JwtUtils.getUserId(), fileId);
    }

}
