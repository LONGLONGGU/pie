package com.framework.pie.admin.model;


import com.framework.pie.mybatis.model.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SysConfig对象",description = "系统配置表")
public class SysConfig extends BaseEntity {

    @ApiModelProperty(value = "数据值")
    private String value;

    @ApiModelProperty(value = "标签名")
    private String label;

    @ApiModelProperty(value = "类型")
    private String type;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "排序")
    private Long sort;

    @ApiModelProperty(value = "备注信息")
    private String remarks;

}