package com.framework.pie.admin.feign.fallback;

import com.framework.pie.admin.feign.FileUploadFeignClient;
import com.framework.pie.http.HttpResult;
import feign.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Slf4j
public class FileUploadFeignFallbackClient implements FileUploadFeignClient {

    @Override
    public HttpResult upload(String userId, String serverCode, MultipartFile file) {
        log.error("Feign远程调用pie-file-upload-service服务[文件上传服务方法-upload]异常");
        return HttpResult.error("Feign远程调用pie-file-upload-service服务[文件上传服务方法-upload]异常,请刷新重试!");
    }

    @Override
    public Response download(String fileId) {
        log.error("Feign远程调用pie-file-upload-service服务[文件上传服务方法-download]异常");
        return null;
    }

    @Override
    public HttpResult delete(String userId,String fileId) {
        log.error("Feign远程调用pie-file-upload-service服务[文件上传服务方法-remove]异常");
        return HttpResult.error("Feign远程调用pie-file-upload-service服务[文件上传服务方法-remove]异常,请刷新重试!");
    }

}
