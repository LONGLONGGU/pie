package com.framework.pie.file.upload.service.impl;

import cn.hutool.core.util.StrUtil;
import com.framework.pie.constant.ServerTypeCode;
import com.framework.pie.file.upload.config.UploadFileConfig;
import com.framework.pie.file.upload.model.SysAttachments;
import com.framework.pie.file.upload.service.SysAttachmentsService;
import com.framework.pie.file.upload.service.UploadService;
import com.framework.pie.http.HttpResult;
import com.framework.pie.web.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
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
    // 注入配置上下文信息
    @Autowired
    private ConfigurableApplicationContext applicationContext;

    @Override
    public HttpResult upload(String userId,String serverCode,String category,MultipartFile file) {
        if (StrUtil.isBlank(userId)) {
            return HttpResult.error("用户ID不能为空");
        }
        if (StrUtil.isBlank(serverCode)) {
            return HttpResult.error("文件所属服务code不能为空");
        }
        if (file.isEmpty()) {
            return HttpResult.error("上传失败，请选择文件");
        }
        //获取文件名
        String fileName = file.getOriginalFilename();
        //获取文件大小（字节）
        long fileSize = file.getSize();
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        String folder = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String filePath = uploadFileConfig.getUploadFolder() + folder + "/" + serverCode + "/";
        if(StrUtil.isNotBlank(category)){
            filePath += category + "/";
        }
        String uuid = UUID.randomUUID().toString().replace("-", "") + suffixName;
        File dest = new File(filePath);
        if (!dest.exists() && !dest.isDirectory()) { //判断文件夹是否存在
            dest.mkdirs();
        }
        try {
            filePath = filePath + uuid;
            dest = new File(filePath);
            file.transferTo(dest);
            SysAttachments sysAttachments = new SysAttachments();
            sysAttachments.setFileName(fileName);
            sysAttachments.setFilePath(filePath);
            sysAttachments.setFileSize(fileSize);
            sysAttachments.setCreateBy(userId);
            sysAttachments.setFileType(suffixName);
            sysAttachments.setServerCode(serverCode);
            sysAttachments.setServerName(ServerTypeCode.getNameByType(serverCode));
            sysAttachments.setUuid(uuid);
            sysAttachmentsService.addOrUpdate(sysAttachments);
            sysAttachments.setDownloadUrl(applicationContext.getEnvironment().getProperty("file.downloadUrl")+sysAttachments.getId());
            // 更新下载地址
            sysAttachmentsService.updateById(sysAttachments);
            return HttpResult.ok(sysAttachments);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return HttpResult.error("上传失败！");
    }

    @Override
    public HttpResult upload(String serverCode,String category,MultipartFile file) {
       return this.upload(JwtUtils.getUserId(), serverCode, category,file);
    }

    @Override
    public void download(HttpServletResponse response, String fileId) {
        SysAttachments sysAttachments = sysAttachmentsService.getById(fileId);
        if(sysAttachments == null){
            return;
        }
        String filename = sysAttachments.getUuid();
        String filePath = sysAttachments.getFilePath();
        File file = new File(filePath);
        if (file.exists()) { //判断文件父目录是否存在
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-type", "application/octet-stream;charset=UTF-8");
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.addHeader("Content-Length", String.valueOf(file.length()));
            try {
                response.setHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(filename.trim(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            byte[] buffer = new byte[1024];
            FileInputStream fis = null; //文件输入流
            BufferedInputStream bis = null;
            OutputStream os = null; //输出流
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer);
                    i = bis.read(buffer);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if(os != null){
                    try {
                        os.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(bis != null){
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(fis != null){
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public HttpResult delete(String userId, String fileId) {
        return sysAttachmentsService.delete(fileId,userId);
    }

    @Override
    public HttpResult delete(String fileId) {
        return this.delete(JwtUtils.getUserId(),fileId);
    }

    @Override
    public SysAttachments filesInfo(String fileId) {
        return sysAttachmentsService.getById(fileId);
    }
}
