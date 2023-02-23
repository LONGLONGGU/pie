package com.framework.pie.admin.model;

import com.framework.pie.mybatis.model.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SysLoginLog对象",description = "系统登录日志")
public class SysLoginLog extends BaseEntity {
    public static final String STATUS_LOGIN = "login";
    public static final String STATUS_LOGOUT = "logout";
    public static final String STATUS_ONLINE = "online";

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "登录状态（online：在线，登录初始状态，方便统计在线人数：login：退出登录之后将online置为login；logout：退出登录）")
    private String status;

    @ApiModelProperty(value = "IP地址")
    private String ip;

    @ApiModelProperty(value = "IP解析出地址信息")
    private String ipAddr;

    @ApiModelProperty(value = "登录类型,用于区分用户是从PC，还是移动端")
    private String loginType;
}