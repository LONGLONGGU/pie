package com.framework.pie.admin.model;

import com.framework.pie.mybatis.model.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SysUserRole对象",description = "用户角色")
public class SysUserRole extends BaseEntity {

    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "角色ID")
    private String roleId;

}