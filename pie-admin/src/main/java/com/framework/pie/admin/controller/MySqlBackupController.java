package com.framework.pie.admin.controller;

import com.framework.pie.admin.feignInterface.MysqlBackupInterface;
import com.framework.pie.http.HttpResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

@RestController
@RequestMapping("backup")
public class MySqlBackupController {
   @Resource
   MysqlBackupInterface mysqlBackupInterface;

   @ApiOperation("查询备份文件")
   @GetMapping("/findRecords")
   public HttpResult findRecords() throws Exception{
       Object backupRecords = mysqlBackupInterface.findRecords();
       return HttpResult.ok(backupRecords);
   }
    @ApiOperation("备份")
    @GetMapping("/backup")
   public HttpResult backup() throws Exception{
        Object object =  mysqlBackupInterface.backup();
      return  HttpResult.ok(object);
   }
    @ApiOperation("恢复数据")
    @GetMapping("/restore")
   public HttpResult restore(@RequestParam String name) throws Exception{
        Object object = mysqlBackupInterface.restore(name);
        return  HttpResult.ok(object);
    }
    @ApiOperation("删除")
    @GetMapping("/delete")
    public HttpResult delete(@RequestParam String name) throws Exception{
        Object object = mysqlBackupInterface.delete(name);
        return  HttpResult.ok(object);
    }
}
