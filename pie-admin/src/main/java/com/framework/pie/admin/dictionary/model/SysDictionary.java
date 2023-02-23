package com.framework.pie.admin.dictionary.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.framework.pie.mybatis.model.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "数据字典对象",description = "数据字典")
public class SysDictionary extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "字典名称")
    private String name;

    @ApiModelProperty(value = "机构标识")
    private String code;

    @ApiModelProperty(value = "父级ID信息")
    private String parentId;
    
    @ApiModelProperty(value = "路径长度信息")
    private String pathInfo;

    @ApiModelProperty(value = "排序值信息")
    private Integer orderNum;

    @ApiModelProperty(value = "是否存在子节点")
    @TableField(exist = false)
    private boolean hasChildren = true;
}
