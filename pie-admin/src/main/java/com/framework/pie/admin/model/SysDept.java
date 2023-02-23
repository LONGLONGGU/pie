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
@ApiModel(value = "SysDept对象",description = "部门")
public class SysDept extends BaseEntity {

    @ApiModelProperty(value = "部门名称")
    private String name;

    @ApiModelProperty(value = "上级机构ID，一级机构为0")
    private String parentId;

    @ApiModelProperty(value = "路径信息")
    private String pathInfo;

    @ApiModelProperty(value = "排序")
    private Integer orderNum;

    // 非数据库字段
    @TableField(exist = false)
    private List<SysDept> children;

    //是否有子节点
    @TableField(exist = false)
    private boolean hasChildren = true;

    // 非数据库字段
    @TableField(exist = false)
    private String parentName;

    // 非数据库字段
    @TableField(exist = false)
    private Integer level;

    // 非数据库字段
    @TableField(exist = false)
    @ApiModelProperty(value = "部门角色信息")
    private List<SysDeptRole> deptRoles;
}