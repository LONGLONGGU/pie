package com.framework.pie.admin.model;
import com.framework.pie.mybatis.model.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* <p>
* token申请表
* </p>
*
* @author longlong
* @since 2022-08-18
*/
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_token_apple")
@ApiModel(value = "SysTokenApple对象", description = "token申请表")
public class SysTokenApple extends BaseEntity {
    private static final long serialVersionUID = 1L;

    //应用名称
    @ApiModelProperty(value = "应用名称")
    @TableField("name")
    private String name;

    //申请时间
    @ApiModelProperty(value = "申请时间")
    @TableField("apply_time")
    private Date applyTime;

    //过期时间
    @ApiModelProperty(value = "过期时间")
    @TableField("overdue_time")
    private Date overdueTime;

    //账号信息
    @ApiModelProperty(value = "账号信息")
    @TableField("user_name")
    private String userName;

    //账号id
    @ApiModelProperty(value = "账号id")
    @TableField("user_id")
    private String userId;

    //password
    @ApiModelProperty(value = "password")
    @TableField("password")
    private String password;

    //token状态 0 禁用 1 正常
    @ApiModelProperty(value = "token状态 0 禁用 1 正常")
    @TableField("token_status")
    private Integer tokenStatus;

    //请求次数
    @ApiModelProperty(value = "请求次数")
    @TableField("request_count")
    private Integer requestCount;

    //token
    @ApiModelProperty(value = "token")
    @TableField("token")
    private String token;

    //备注
    @ApiModelProperty(value = "备注")
    @TableField("note")
    private String note;


}