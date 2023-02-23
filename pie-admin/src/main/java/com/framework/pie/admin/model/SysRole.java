package com.framework.pie.admin.model;

import com.framework.pie.mybatis.model.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SysRole对象",description = "角色")
public class SysRole extends BaseEntity {

    @ApiModelProperty("角色名称")
    private String name;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("机构id")
    private String orgId;

}