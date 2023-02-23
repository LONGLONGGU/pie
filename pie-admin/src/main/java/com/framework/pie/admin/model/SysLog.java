package com.framework.pie.admin.model;

import com.framework.pie.mybatis.model.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SysLog对象",description = "系统操作日志")
public class SysLog extends BaseEntity {

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "用户操作")
    private String operation;

    @ApiModelProperty(value = "请求方法")
    private String method;

    @ApiModelProperty(value = "请求参数")
    private String params;

    @ApiModelProperty(value = "持续时长（毫秒）")
    private Long time;

    @ApiModelProperty(value = "ip")
    private String ip;

}