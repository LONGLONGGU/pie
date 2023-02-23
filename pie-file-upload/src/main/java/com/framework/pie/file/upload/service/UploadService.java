package com.framework.pie.file.upload.service;

import com.framework.pie.file.upload.model.SysAttachments;
import com.framework.pie.http.HttpResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 文件上传服务接口
 */
public interface UploadService {

     /**
      * 文件上传
      * @param serverCode 所属服务
      * @param file 文件信息
      * @return
      */
     HttpResult upload(String userId,String serverCode,String category, MultipartFile file);

     /**
      * 文件上传
      * @param file 文件信息
      * @return
      */
     HttpResult upload(String serverCode,String category, MultipartFile file);

     /**
      * 文件下载
      * @param response
      * @param fileId
      * @return
      */
     void download(HttpServletResponse response, String fileId);

     /**
      * 删除文件
      * @param userId 用户信息
      * @param fileId 文件Id
      * @return
      */
     HttpResult delete(String userId,String fileId);

     /**
      * 删除文件
      * @param fileId 文件Id
      * @return
      */
     HttpResult delete(String fileId);

     SysAttachments filesInfo(String fileId);
}
