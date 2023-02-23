package com.framework.pie.quartz.service.impl;

import com.framework.pie.http.HttpResult;
import com.framework.pie.quartz.config.quartz.SchedulerConfig;
import com.framework.pie.quartz.feign.BusDistrictFeignClient;
import com.framework.pie.quartz.model.QuartzJob;
import com.framework.pie.quartz.dao.QuartzJobMapper;
import com.framework.pie.quartz.service.QuartzJobService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.framework.pie.web.utils.JwtUtils;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.framework.pie.mybatis.page.MybatisPageHelper;
import com.framework.pie.mybatis.page.PageRequest;
import com.framework.pie.mybatis.page.PageResult;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


/**
 * <p>
 * job任务表 服务实现类
 * </p>
 *
 * @author longlong
 * @since 2021-08-16
 */
@Service("QuartzJobService")
@Transactional(rollbackFor = Exception.class)
public class QuartzJobServiceImpl extends ServiceImpl<QuartzJobMapper, QuartzJob> implements QuartzJobService {

    @Autowired
    private QuartzJobMapper quartzJobMapper;
    @Autowired
    SchedulerConfig schedulerConfig;
    @Autowired
    BusDistrictFeignClient busDistrictFeignClient;

    @Override
    public HttpResult addOrUpdate(QuartzJob record) {
      if (StringUtils.isEmpty(record.getId())){
          record.setCreateBy(JwtUtils.getUserId());
          quartzJobMapper.insert(record);
       }
        record.setLastUpdateBy(JwtUtils.getUserId());
        quartzJobMapper.updateById(record);
      return HttpResult.ok("操作成功");
    }

    @Override
    public int delete(QuartzJob record) {
        this.deleteJob(this.findById(record.getId()));
       return quartzJobMapper.deleteById(record.getId());
    }

    @Override
    public int delete(List<QuartzJob> records) {
        for (QuartzJob record : records){
            delete(record);
        }
        return 1;
    }
    @Override
    public QuartzJob findById(String id) {
        return quartzJobMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        Object label = pageRequest.getParam("label");
        return MybatisPageHelper.findPage(pageRequest,quartzJobMapper);
    }

    @Override
    public HttpResult findTree() {
        return busDistrictFeignClient.findTree();
    }

    @Override
    public int starOrStopJob(QuartzJob quartzJob) {
        int status = quartzJob.getStatus();
        if (status == 1){
            if (quartzJob.getStartTime()!=null){
              this.resumeJob(quartzJob);
            }else {
                quartzJob.setStartTime(new Date());
                this.startJob(quartzJob);
            }
            this.quartzJobMapper.updateById(quartzJob);
        }else {
            quartzJob.setStopTime(new Date());
            System.out.println(this.stopJob(quartzJob));
            this.quartzJobMapper.updateById(quartzJob);
        }
        return 0;
    }

    @Override
    public List<QuartzJob> findRunQuartzJobs() {

        return this.quartzJobMapper.findRunQuartzJobs();
    }

    //启动Job
    public String startJob(QuartzJob quartzJob){
        Scheduler scheduler = null;
        try {
            scheduler = schedulerConfig.scheduler();
            TriggerKey triggerKey = TriggerKey.triggerKey(quartzJob.getTriggerName(), quartzJob.getTriggerGroupName());
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            if (null == trigger) {
                Class clazz = Class.forName(quartzJob.getJobClass());
                JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(quartzJob.getJobname(),quartzJob.getJobGroup()).build();
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(quartzJob.getCronexpression());
                trigger = TriggerBuilder.newTrigger().withIdentity(quartzJob.getTriggerName(), quartzJob.getTriggerGroupName())
                        .withSchedule(scheduleBuilder).build();
            scheduler.scheduleJob(jobDetail, trigger);
            System.out.println("Quartz 创建了job:...:{}"+ jobDetail.getKey());
            }else {
                return "该任务已经存在！";
            }
        }catch (Exception e){
            e.printStackTrace();
            return "启动异常！";
        }
        return "启动成功";
    }

    /**
     *  停止Job
     */
    public String stopJob(QuartzJob quartzJob){
        Scheduler scheduler = null;
        try {
            scheduler = schedulerConfig.scheduler();
            JobKey jobKey = JobKey.jobKey(quartzJob.getJobname(),quartzJob.getJobGroup());
            scheduler.pauseJob(jobKey);
            this.quartzJobMapper.updateById(quartzJob);
        }catch (Exception e){
            e.printStackTrace();
            return "停止job失败";
        }
        return "停止job成功";
    }
    /**
     * 恢复job
     */
    public String resumeJob(QuartzJob quartzJob){
        Scheduler scheduler = null;
        try {
            scheduler = schedulerConfig.scheduler();
            JobKey jobKey = JobKey.jobKey(quartzJob.getJobname(), quartzJob.getJobGroup());
            scheduler.resumeJob(jobKey);
        }catch (Exception e){
            e.printStackTrace();
            return "恢复job失败";
        }
        return "恢复job成功";
    }


    /**
     * 删除job
     */
    public String deleteJob(QuartzJob quartzJob){
        Scheduler scheduler = null;
        try {
            scheduler = schedulerConfig.scheduler();
            JobKey jobKey = JobKey.jobKey(quartzJob.getJobname(),quartzJob.getJobGroup());
            scheduler.deleteJob(jobKey);
        }catch (Exception e){
            e.printStackTrace();
            return "删除job失败";
        }
        return "删除job成功";
    }

}
