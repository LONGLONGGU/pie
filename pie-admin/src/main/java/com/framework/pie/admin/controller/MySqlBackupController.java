package com.framework.pie.admin.controller;

import com.framework.pie.admin.feign.MySqlBackupClient;
import com.framework.pie.admin.vo.BackupInfoBean;
import com.framework.pie.admin.vo.DataSourceBean;
import com.framework.pie.http.HttpResult;
import com.framework.pie.mybatis.page.PageRequest;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("backup")
public class MySqlBackupController {
   @Resource
   MySqlBackupClient mysqlBackupInterface;

    @ApiOperation("备份")
    @PostMapping("/findPage")
    public Object findPage(@RequestBody PageRequest pageRequest ) throws Exception{
        Object object =  mysqlBackupInterface.findPage(pageRequest);
        return  object;
    }

   @ApiOperation("查询备份文件")
   @GetMapping("/findRecords")
   public HttpResult findRecords() throws Exception{
       Object backupRecords = mysqlBackupInterface.findRecords();
       return HttpResult.ok(backupRecords);
   }
    @ApiOperation("备份")
    @PostMapping("/backup")
   public HttpResult backup(@RequestBody DataSourceBean dataSourceBean) throws Exception{
        Object object =  mysqlBackupInterface.backup(dataSourceBean);
      return  HttpResult.ok(object);
   }
    @ApiOperation("恢复数据")
    @PostMapping("/restore")
   public HttpResult restore(@RequestBody BackupInfoBean backupInfoBean) throws Exception{
        Object object = mysqlBackupInterface.restore(backupInfoBean);
        return  HttpResult.ok(object);
    }
//    @ApiOperation("删除")
//    @GetMapping("/delete")
//    public Object delete(@RequestParam String name) throws Exception{
//        Object object = mysqlBackupInterface.delete(name);
//        return  HttpResult.ok(object);
//    }

    @ApiOperation("异步加载tree")
    @GetMapping(value="/findTree/{parentId}")
    public Object asyncFindTree(@PathVariable("parentId") Long parentId) {
        return mysqlBackupInterface.asyncFindTree(parentId);
    }
    @ApiOperation("保存数据源信息")
    @PostMapping(value = "/save")
    public Object save(@RequestBody() DataSourceBean dataSourceBean) {
        return mysqlBackupInterface.save(dataSourceBean);
    }

    @ApiOperation("删除数据源信息")
    @PostMapping(value = "/delete")
    public Object delete(@RequestBody() DataSourceBean dataSourceBean) {
        return mysqlBackupInterface.delete(dataSourceBean);
    }

    @ApiOperation("删除数据备份信息")
    @PostMapping("/deleteBackupInfo")
    public Object deleteBackupInfo(@RequestBody() BackupInfoBean backupInfoBean) throws Exception{
        Object object = mysqlBackupInterface.deleteBackupInfo(backupInfoBean);
        return  HttpResult.ok(object);
    }


}
