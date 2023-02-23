package com.framework.pie.admin.feign;

import com.framework.pie.admin.feign.fallback.FileUploadFeignFallbackClient;
import com.framework.pie.http.HttpResult;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(value = "pie-file-upload-service", fallback = FileUploadFeignFallbackClient.class)
public interface FileUploadFeignClient {

    /**
     * 文件上传服务 注意些处调用时附件的注解使用RequestPart，而不能使用RequestParam
     * @param serverCode
     * @param file
     * @return
     */
    @PostMapping(value = "/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    HttpResult upload(@RequestParam("userId") String userId,@RequestParam("serverCode") String serverCode,
                      @RequestPart("file") MultipartFile file);

    /**
     * 附件下载 注意此处返回的是feign的Response，到控制器时需要进行转换操作
     * @param fileId
     * @return
     */
    @GetMapping("/download")
    Response download(@RequestParam("fileId") String fileId);

    /**
     * 删除附件信息
     * @param userId 当前登录用户ID
     * @param fileId 附件ID信息
     * @return
     */
    @GetMapping("/delete")
    HttpResult delete(@RequestParam("userId") String userId,@RequestParam("fileId") String fileId);
}
