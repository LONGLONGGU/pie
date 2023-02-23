package com.framework.pie.admin.dictionary.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "数据字典验证对象",description = "数据字典验证对象")
public class DictValidateDTO {

    @ApiModelProperty("id信息")
    private String id;

    @ApiModelProperty("字典code信息")
    private String code;
}
