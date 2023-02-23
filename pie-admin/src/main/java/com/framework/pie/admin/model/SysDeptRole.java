package com.framework.pie.admin.model;
import com.framework.pie.mybatis.model.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* <p>
* 角色机构
* </p>
*
* @author houjh
* @since 2021-11-15
*/
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_dept_role")
@ApiModel(value = "SysDeptRole对象", description = "部门角色信息表")
public class SysDeptRole extends BaseEntity {
    private static final long serialVersionUID = 1L;

    //角色ID
    @ApiModelProperty(value = "角色ID")
    @TableField("role_id")
    private String roleId;

    //部门ID
    @ApiModelProperty(value = "部门ID")
    @TableField("dept_id")
    private String deptId;


}