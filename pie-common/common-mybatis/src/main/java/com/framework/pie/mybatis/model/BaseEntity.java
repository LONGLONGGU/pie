package com.framework.pie.mybatis.model;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ：longlong
 * @date ：Created in 2021/9/7 22:35
 * @modified By：
 * @version: ：V1.0
 */
@Data
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id自动增加
     */
    @TableId(type = IdType.ASSIGN_UUID)
    @ApiModelProperty("唯一标识")
    private String id;

    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime = new Date();

    /**
     * 修改人
     */
    @ApiModelProperty("修改人")
    @TableField(fill = FieldFill.UPDATE)
    private String lastUpdateBy;

    /**
     * 修改时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("修改时间")
    @TableField(fill = FieldFill.UPDATE)
    private Date lastUpdateTime;

    /**
     * 删除标识 0 删除 1未删除
     */
    @ApiModelProperty("删除时间 0 删除 1未删除")
    @TableField(fill = FieldFill.INSERT)
    @TableLogic
    private Byte delFlag = 1;
}
