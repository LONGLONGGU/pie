package com.framework.pie.admin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "用户导出临时数据对象",description = "用户导出临时数据对象")
public class UserExportDTO {

    @ApiModelProperty("用户帐号")
    private String name;

    @ApiModelProperty("用户姓名")
    private String nickName;

    @ApiModelProperty("用户手机号")
    private String mobile;

    @ApiModelProperty("路径层级信息")
    private String pathInfo;

    @ApiModelProperty("部门信息")
    private String deptNameInfo;
}
