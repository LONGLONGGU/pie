package com.framework.pie.quartz.job;

import cn.hutool.extra.ssh.GanymedUtil;
import com.framework.pie.mybatis.utils.RunSqlUtils;
import com.framework.pie.quartz.dao.QuartzRunsqllogMapper;
import com.framework.pie.quartz.model.QuartzRunsqllog;
import com.framework.pie.quartz.service.QuartzRunsqllogService;
import com.framework.pie.quartz.utils.DFUtils;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;
import org.apache.ibatis.session.SqlSession;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@DisallowConcurrentExecution //是否并发
//@PersistJobDataAfterExecution //持久化
@Component
public class SynchronousData  extends QuartzJobBean {
    private RunSqlUtils rs;
    @Resource
    private QuartzRunsqllogService quartzRunsqllogService;

    private static SynchronousData  synchronousData ;

    @PostConstruct //通过@PostConstruct实现初始化bean之前进行的操作
    public void init() {
        synchronousData= this;
        synchronousData.quartzRunsqllogService = this.quartzRunsqllogService;
        // 初使化时将已静态化的quartzRunsqllogService实例化
    }
    @SneakyThrows
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
            rs=new RunSqlUtils("","","","");
            if(rs.getConnection()){
                String sql="SELECT\n" +
                        "\ta.scriptSql,c.ip,c.username,c.databasePassword,c.driver\n" +
                        "FROM\n" +
                        "\td_scriptmysql a\n" +
                        "INNER JOIN d_treesqlscript b ON a.treeScriptId = b.id,\n" +
                        "data_source_manager c\n" +
                        "WHERE \n" +
                        "c.id=b.scriptSqlId and\n" +
                        "a.id=7";
                List<Map<String,Object>> list=rs.getObjectLists(sql);
                if(!list.isEmpty()){
                    for (Map<String, Object> stringObjectMap : list) {
                        sql=stringObjectMap.get("scriptSql").toString();
                        RunSqlUtils rsu=new RunSqlUtils(stringObjectMap.get("driver").toString(),stringObjectMap.get("ip").toString(),stringObjectMap.get("username").toString(),stringObjectMap.get("databasePassword").toString());
                        if(rsu.getConnection()){
                            List<Map<String,Object>> li=rsu.getUpdateObjectList(sql);
                            for (Map<String, Object> objectMap : li) {
                                QuartzRunsqllog qrlog=new QuartzRunsqllog();//存日志
                                qrlog.setDatabasesNmae(jobExecutionContext.getJobDetail().getKey().getName());
                                qrlog.setSqls(sql);
                                qrlog.setCreateTime(new Date());
                                if(objectMap.get("success").toString()==null){
                                    qrlog.setMessage(objectMap.get("error").toString());
                                    qrlog.setRunTime("");
                                }else{
                                    qrlog.setMessage(objectMap.get("success").toString());
                                    qrlog.setRunTime(objectMap.get("runTime").toString());
                                }
                                System.out.println(qrlog.toString());
                               synchronousData.quartzRunsqllogService.saveByNativeSql(qrlog);
                            }
                        }
                    }
                }
            }
        String jobs=jobExecutionContext.getJobDetail().getJobDataMap().getString("name");
        System.out.println(jobs+"时间:"+DFUtils.fomat(new Date()) );
    }
}
