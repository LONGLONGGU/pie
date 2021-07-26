package com.framework.pie.admin.service.impl;

import com.framework.pie.admin.config.UploadFileConfig;
import com.framework.pie.admin.model.SysAttachments;
import com.framework.pie.admin.service.UploadService;
import com.framework.pie.admin.service.SysAttachmentsService;
import com.framework.pie.admin.util.SecurityUtils;
import com.framework.pie.http.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class UploadServiceImpl implements UploadService {
    @Autowired
    private SysAttachmentsService sysAttachmentsService;
    @Autowired
    UploadFileConfig uploadFileConfig;


    @Override
    public HttpResult upload(MultipartFile file) {
        if (file.isEmpty()) {
            return HttpResult.error("上传失败，请选择文件");
        }
        //获取文件名
        String fileName = file.getOriginalFilename();
        //获取文件大小（字节）
        long fileSize = file.getSize();
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        String folder = new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
        String filePath = uploadFileConfig.getUploadFolder() + folder + "/";
        String uuid = UUID.randomUUID().toString().replace("-", "") + suffixName;
        File dest = new File(filePath);
        if (!dest.exists() && !dest.isDirectory()) //判断文件夹是否存在
        {
            dest.mkdir();
        }
        try {
            filePath = filePath + uuid;
            dest = new File(filePath);
            file.transferTo(dest);
            SysAttachments sysAttachments = new SysAttachments();
            sysAttachments.setFileName(fileName);
            sysAttachments.setFilePath(filePath);
            sysAttachments.setFileSize(fileSize);
            sysAttachments.setCreateBy(SecurityUtils.getUsername());
            sysAttachments.setFileType(suffixName);
            sysAttachments.setUuid(uuid);
            sysAttachmentsService.save(sysAttachments);
            return HttpResult.ok(sysAttachments);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return HttpResult.error("上传失败！");
    }

    @Override
    public HttpResult download(HttpServletResponse response, Long fileId) {
//        SysAttachments sysAttachments = sysAttachmentsService.findById(fileId);
//        String filename = sysAttachments.getUuid();
//        String filePath = sysAttachments.getFilePath();
//        File file = new File(filePath + "/" + filename);
//        if (file.exists()) { //判断文件父目录是否存在
//            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
//            response.setCharacterEncoding("UTF-8");
//            // response.setContentType("application/force-download");
//            try {
//                response.setHeader("Content-Disposition", "attachment;fileName=" + java.net.URLEncoder.encode(filename, "UTF-8"));
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//            byte[] buffer = new byte[1024];
//            FileInputStream fis = null; //文件输入流
//            BufferedInputStream bis = null;
//
//            OutputStream os = null; //输出流
//            try {
//                os = response.getOutputStream();
//                fis = new FileInputStream(file);
//                bis = new BufferedInputStream(fis);
//                int i = bis.read(buffer);
//                while (i != -1) {
//                    os.write(buffer);
//                    i = bis.read(buffer);
//                }
//
//            } catch (Exception e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//
//        }
//        return null;
        return null;
    }
}
