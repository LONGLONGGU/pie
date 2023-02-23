package com.framework.pie.quartz.job;


import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution

public class QuartzJob2 extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        String taskName = context.getJobDetail().getJobDataMap().getString("name");
        System.out.println("taskName="+context.getJobDetail().getKey().getName());
        System.out.println("---> 任务调度Quartz job 2 {}, {} <----"+ new Date()+ taskName);
    }
}
