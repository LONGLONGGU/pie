package com.framework.pie.file.upload.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

@Configuration
@RefreshScope
public class UploadFileConfig {

    @Value("${file.uploadFolder}")
    private String uploadFolder;

    public String getUploadFolder() {
        if (uploadFolder == null){
            File path = null;
            try {
                path = new File(ResourceUtils.getURL("classpath:").getPath());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            if(!path.exists()){
                path = new File("");
               uploadFolder = path.getAbsolutePath();
            }
        }
        return uploadFolder;
    }
}
