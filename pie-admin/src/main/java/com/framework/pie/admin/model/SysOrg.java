package com.framework.pie.admin.model;


import com.baomidou.mybatisplus.annotation.TableField;
import com.framework.pie.mybatis.model.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SysOrg对象",description = "机构表")
public class SysOrg extends BaseEntity {

    @ApiModelProperty("机构名称")
    private String name;

    @ApiModelProperty("状态 0：禁用 1：正常")
    private Byte status = 1;

    @ApiModelProperty("行政区划Id信息，关联zst-crawler库中bus_district表")
    private String districtId;

    @ApiModelProperty("行政区划名称")
    private String districtName;

    @ApiModelProperty("排序")
    private Integer orderNum;

    @ApiModelProperty("备注信息")
    private String remark;

    // 非数据库字段
    @ApiModelProperty("机构管理员密码信息")
    @TableField(exist = false)
    private String orgAdminPwd;

    // 非数据库字段
    @ApiModelProperty("创建用户名称")
    @TableField(exist = false)
    private String createUserName;
}