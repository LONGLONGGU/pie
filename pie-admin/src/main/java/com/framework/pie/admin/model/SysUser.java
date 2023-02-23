package com.framework.pie.admin.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.framework.pie.mybatis.model.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.ArrayList;
import java.util.List;
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SysUser对象",description = "系统用户表")
public class SysUser extends BaseEntity {

    @ApiModelProperty(value = "用户名")
    private String name;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "头像Id")
    private String avatarId;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "加密盐")
    private String salt;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "状态 0：禁用 1：正常")
    private Byte status = 1;

    @ApiModelProperty(value = "部门id")
    private String deptId;

    @ApiModelProperty(value = "排序值信息")
    private String orderNum;
    // 非数据库字段
    @TableField(exist = false)
    private String deptName;
    // 非数据库字段
    @TableField(exist = false)
    private String roleNames;
    // 非数据库字段
    @TableField(exist = false)
    private List<SysUserRole> userRoles = new ArrayList<>();

    // 非数据库字段
    @TableField(exist = false)
    private String deptPath;
}