package com.framework.pie.admin.dictionary.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.framework.pie.mybatis.model.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "数据字典详情对象",description = "数据字典详情")
public class SysDictionaryData extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "字典详情名称")
    private String name;

    @ApiModelProperty(value = "字典详情值")
    private String value;

    @ApiModelProperty(value = "备注信息")
    private String mark;

    @ApiModelProperty(value = "字典ID")
    private String dictionaryId;

    @ApiModelProperty(value = "字典名称信息")
    @TableField(exist = false)
    private String dictionaryName;

    @ApiModelProperty(value = "排序")
    private Integer orderNum;
}
