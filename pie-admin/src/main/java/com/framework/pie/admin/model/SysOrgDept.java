package com.framework.pie.admin.model;


import com.framework.pie.mybatis.model.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SysOrgDept对象",description = "机构部门")
public class SysOrgDept extends BaseEntity {

    @ApiModelProperty("机构ID")
    private String orgId;

    @ApiModelProperty("部门ID")
    private String deptId;
}