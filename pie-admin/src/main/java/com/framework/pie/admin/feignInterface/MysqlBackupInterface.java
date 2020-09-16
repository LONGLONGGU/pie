package com.framework.pie.admin.feignInterface;

import com.framework.pie.admin.Hystrix.MySqlBackupHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "pie-backup",fallback = MySqlBackupHystrix.class)
public interface MysqlBackupInterface {

    @GetMapping("/backup/findRecords")
    Object findRecords();

    @GetMapping("/backup/backup")
    Object backup();

    @GetMapping("/backup/restore")
    Object restore(@RequestParam("name") String name);

    @GetMapping("/backup/delete")
    Object delete(@RequestParam("name") String name);



}
