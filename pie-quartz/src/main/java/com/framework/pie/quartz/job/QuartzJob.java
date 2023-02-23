package com.framework.pie.quartz.job;
import com.framework.pie.http.HttpResult;
import com.framework.pie.quartz.config.quartz.ApplicationContextHelper;
import com.framework.pie.quartz.feign.BusDistrictFeignClient;
import com.framework.pie.quartz.service.QuartzJobService;
import com.framework.pie.quartz.utils.DFUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
//禁止并发得执行同一个job
@DisallowConcurrentExecution
//持久化jobDetal 如果没有持久化 那么qutaze会删除
@PersistJobDataAfterExecution
public class QuartzJob extends QuartzJobBean {
    QuartzJobService quartzJobService = (QuartzJobService) ApplicationContextHelper.getBean("QuartzJobService");
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        String taskName = context.getJobDetail().getJobDataMap().getString("name");
        System.out.println("taskName="+context.getJobDetail().getKey().getName());
        System.out.println("---> 任务调度Quartz job {}, {} <----"+ DFUtils.fomat(new Date()) + taskName);
        HttpResult httpResult = quartzJobService.findTree();
        System.out.println(httpResult.getMsg());
    }

}
