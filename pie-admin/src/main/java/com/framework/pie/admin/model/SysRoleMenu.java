package com.framework.pie.admin.model;

import com.framework.pie.mybatis.model.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SysRoleMenu对象",description = "角色菜单")
public class SysRoleMenu extends BaseEntity {

    @ApiModelProperty("角色ID")
    private String roleId;

    @ApiModelProperty("菜单ID")
    private String menuId;
}