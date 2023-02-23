package com.framework.pie.admin.feign.fallback;

import com.framework.pie.admin.feign.MySqlBackupClient;
import com.framework.pie.admin.vo.BackupInfoBean;
import com.framework.pie.admin.vo.DataSourceBean;
import com.framework.pie.http.HttpResult;
import com.framework.pie.mybatis.page.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Component
public class MySqlBackupFallbackClient implements MySqlBackupClient {
    @Override
    @GetMapping("/backup/findRecords")
    public Object findRecords() {
        return  HttpResult.error(500,"数据备份服务异常，请稍后重试");
    }

    @Override
    @PostMapping(value="/backupInfo/findPage")
    public Object findPage(PageRequest pageRequest) {
        return HttpResult.error(500,"数据备份服务异常，请稍后重试");
    }

    @Override
    @GetMapping("/backup/backup")
    public Object backup(DataSourceBean dataSourceBean) {
        return  HttpResult.error(500,"数据备份服务异常，请稍后重试");
    }

    @Override
    @PostMapping("/backup/restore")
    public Object restore(BackupInfoBean backupInfoBean) {
        return  HttpResult.error(500,"数据备份服务异常，请稍后重试");
    }

    @Override
    @GetMapping("/backup/delete")
    public Object delete(String name) {
        return  HttpResult.error(500,"数据备份服务异常，请稍后重试");
    }

    @Override
    @GetMapping(value="/datasource/findTree/{parentId}")
    public Object asyncFindTree(Long parentId) {
        return  HttpResult.error(500,"数据备份服务异常，请稍后重试");
    }

    @Override
    @PostMapping(value="/datasource/save")
    public Object save(DataSourceBean dataSourceBean) {
        return  HttpResult.error(500,"数据备份服务保存异常，请稍后重试");
    }

    @Override
    @PostMapping(value="/datasource/delete")
    public Object delete(DataSourceBean dataSourceBean) {
        return  HttpResult.error(500,"数据备份服务删除异常，请稍后重试");
    }

    @Override
    @PostMapping("/backupInfo/delete")
    public Object deleteBackupInfo(BackupInfoBean backupInfoBean) {
        return  HttpResult.error(500,"数据备份服务删除异常，请稍后重试");
    }
}
