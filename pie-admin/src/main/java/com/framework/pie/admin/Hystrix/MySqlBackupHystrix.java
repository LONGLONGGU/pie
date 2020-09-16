package com.framework.pie.admin.Hystrix;

import com.framework.pie.admin.feignInterface.MysqlBackupInterface;
import com.framework.pie.core.http.HttpResult;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@Component
public class MySqlBackupHystrix implements MysqlBackupInterface {
    @Override
    @GetMapping("/backup/findRecords")
    public Object findRecords() {
        return  HttpResult.error(500,"数据备份服务异常，请稍后重试");
    }
    @Override
    @GetMapping("/backup/backup")
    public Object backup() {
        return  HttpResult.error(500,"数据备份服务异常，请稍后重试");
    }

    @Override
    @GetMapping("/backup/restore")
    public Object restore(String name) {
        return  HttpResult.error(500,"数据备份服务异常，请稍后重试");
    }

    @Override
    @GetMapping("/backup/delete")
    public Object delete(String name) {
        return  HttpResult.error(500,"数据备份服务异常，请稍后重试");
    }
}
