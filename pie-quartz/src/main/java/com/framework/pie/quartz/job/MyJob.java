package com.framework.pie.quartz.job;

import org.quartz.*;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

/**
 * @author ：longlong
 * @date ：Created in 2021/8/15 20:54
 * @modified By：
 * @version: ：V1.0
 */

//禁止并发得执行同一个job
@DisallowConcurrentExecution
//持久化jobDetal 如果没有持久化 那么qutaze会删除
@PersistJobDataAfterExecution
public class MyJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        String taskName = context.getJobDetail().getJobDataMap().getString("name");
        System.out.println("taskName="+context.getJobDetail().getKey().getName());
        System.out.println("---> 任务调度myjob {}, {} <----"+ new Date()+ taskName);

    }
}
