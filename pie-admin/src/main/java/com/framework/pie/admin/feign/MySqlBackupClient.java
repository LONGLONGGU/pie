package com.framework.pie.admin.feign;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.framework.pie.admin.feign.fallback.MySqlBackupFallbackClient;
import com.framework.pie.admin.vo.BackupInfoBean;
import com.framework.pie.admin.vo.DataSourceBean;
import com.framework.pie.mybatis.page.PageRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "pie-data-back-service",fallback = MySqlBackupFallbackClient.class)
@SentinelResource(value = "test",
        blockHandler = "handleException",
        blockHandlerClass = {MySqlBackupFallbackClient.class},
        fallback = "helloFallback",
        fallbackClass = {MySqlBackupFallbackClient.class})
public interface MySqlBackupClient {

    @GetMapping("/backup/findRecords")
    Object findRecords();

    @PostMapping("/backupInfo/findPage")
    Object findPage(PageRequest pageRequest);

    @PostMapping("/backup/backup")
    Object backup(DataSourceBean dataSourceBean);

    @PostMapping("/backup/restore")
    Object restore(BackupInfoBean backupInfoBean);

    @GetMapping("/backup/delete")
    Object delete(@RequestParam("name") String name);

    @GetMapping("/datasource/findTree/{parentId}")
    Object asyncFindTree(@PathVariable("parentId") Long parentId);

    @PostMapping("/datasource/save")
    Object save(DataSourceBean dataSourceBean);

    @PostMapping("/datasource/delete")
    Object delete(DataSourceBean dataSourceBean);

    @PostMapping("/backupInfo/delete")
    Object deleteBackupInfo(BackupInfoBean backupInfoBean);

}
