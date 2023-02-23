package com.framework.pie.admin.model;

import com.framework.pie.mybatis.model.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SysOrgMenu对象",description = "机构权限")
public class SysOrgMenu extends BaseEntity {

    @ApiModelProperty("机构ID")
    private String orgId;

    @ApiModelProperty("菜单ID")
    private String menuId;
}