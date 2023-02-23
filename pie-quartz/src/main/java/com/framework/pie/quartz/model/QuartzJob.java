package com.framework.pie.quartz.model;

import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableField;
import com.framework.pie.mybatis.model.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * job任务表
 * </p>
 *
 * @author longlong
 * @since 2021-08-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "QuartzJob对象",description = "job任务表")
public class QuartzJob extends BaseEntity {
    /**
     * 任务名称
     */
    @ApiModelProperty("任务名称")
    private String jobname;

    /**
     * 任务分组
     */
    @ApiModelProperty("任务分组")
    @TableField("jobGroup")
    private String jobGroup;

    /**
     * 触发器名称
     */
    @ApiModelProperty("触发器名称")
    @TableField("triggerName")
    private String triggerName;


    /**
     * 触发器分组
     */
    @ApiModelProperty("触发器分组")
    @TableField("triggerGroupName")
    private String triggerGroupName;


    /**
     * job任务调用类，包名+类名
     */
    @ApiModelProperty("job任务调用类，包名+类名")
    @TableField("jobClass")
    private String jobClass;

    /**
     * 任务调用的方法名
     */
    @ApiModelProperty("任务调用的方法名")
    private String methodname;

    /**
     * 任务运行时间表达式
     */
    @ApiModelProperty("任务运行时间表达式")
    private String cronexpression;

    /**
     * 任务描述
     */
    @ApiModelProperty("任务描述")
    private String description;

    /**
     * 任务初始状态 0未启动 1运行中 2已停止 3异常
     */
    @ApiModelProperty("任务初始状态 0未启动 1运行中 2已停止 3异常")
    private Integer jobstatus = 0;

    /**
     * 状态 0：禁用 1：正常
     */
    @ApiModelProperty("状态 0：禁用 1：正常")
    private Integer status = 0;

    /**
     * 启动时间
     */
    @ApiModelProperty("启动时间")
    @TableField("startTime")
    private Date startTime;

    /**
     * 上一次运行时间
     */
    @ApiModelProperty("上一次运行时间")
    @TableField("stopTime")
    private Date stopTime;

}
