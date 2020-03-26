package com.framework.pie.admin.service;

import com.framework.pie.core.http.HttpResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public interface UploadService {
     HttpResult upload( MultipartFile file);

     HttpResult download(HttpServletResponse response, Long fileId);
}
