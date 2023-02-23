package com.framework.pie.quartz.config.quartz;

import com.framework.pie.quartz.job.QuartzJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

//监听器
@Component  //变成主键  不然下面东西不生效
public class StartApplicationListener implements ApplicationListener<ContextClosedEvent> {

    @Autowired
    private Scheduler scheduler;

    //定义触发器
    @Override
    public void onApplicationEvent(ContextClosedEvent contextClosedEvent) {
        /*try {
            TriggerKey triggerKey=TriggerKey.triggerKey("trigger1","group1");
            Trigger trigger=scheduler.getTrigger(triggerKey);
            if(trigger==null){
                trigger= TriggerBuilder.newTrigger()
                        .withIdentity(triggerKey)
                        .withSchedule(CronScheduleBuilder.cronSchedule("0/10 * * * * ?"))
                        //.startNow()//立马启动
                        .build();
                JobDetail jobDetail =JobBuilder.newJob(QuartzJob.class)
                        .withIdentity("job1","group1")
                        .usingJobData("name","job1111")
                        .build();
                scheduler.scheduleJob(jobDetail,trigger);

            }
            TriggerKey triggerKey1=TriggerKey.triggerKey("trigger2","group2");
            Trigger trigger1=scheduler.getTrigger(triggerKey1);
            if(trigger1==null){
                trigger1= TriggerBuilder.newTrigger()
                        .withIdentity(triggerKey1)
                        .withSchedule(CronScheduleBuilder.cronSchedule("0/10 * * * * ?"))
                        //.startNow()//立马启动
                        .build();
                JobDetail jobDetail2 =JobBuilder.newJob(QuartzJob.class)
                        .withIdentity("job2","group2")
                        .usingJobData("name","job2222")
                        .build();
                scheduler.scheduleJob(jobDetail2,trigger1);

            }
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
*/
    }

}
