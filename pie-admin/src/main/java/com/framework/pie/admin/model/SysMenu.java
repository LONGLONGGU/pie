package com.framework.pie.admin.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.framework.pie.mybatis.model.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SysMenu对象",description = "系统菜单")
public class SysMenu extends BaseEntity {

    @ApiModelProperty(value = "菜单名称")
    private String name;

    @ApiModelProperty(value = "父菜单ID，一级机构为0")
    private String parentId;

    @ApiModelProperty(value = "菜单URL")
    private String url;

    @ApiModelProperty(value = "外部站点嵌套url")
    private String nestedUrl;

    @ApiModelProperty(value = "授权")
    private String perms;

    @ApiModelProperty(value = "所属系统模块")
    private String moduleInfo;

    @ApiModelProperty(value = "功能请求路径")
    private String pathInfo;

    @ApiModelProperty(value = "类型：0：目录 1：菜单 2：按钮")
    private Integer type;

    @ApiModelProperty(value = "是否显示")
    private Boolean hidden;

    @ApiModelProperty(value = "菜单图标")
    private String icon;

    @ApiModelProperty(value = "排序")
    private Integer orderNum;

    // 非数据库字段
    @TableField(exist = false)
    private String parentName;
    // 非数据库字段
    @TableField(exist = false)
    private Integer level;
    // 非数据库字段
    @TableField(exist = false)
    private List<SysMenu> children;

}