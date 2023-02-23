package com.framework.pie.quartz.model;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.framework.pie.mybatis.model.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
* <p>
*
* </p>
*
* @author houjh
* @since 2021-09-09
*/
@Data
@EqualsAndHashCode(callSuper = false)
public class QuartzRunsqllog extends BaseEntity {

    @TableField("sqls")
    private String sqls;

    @TableField("runTime")
    private String runTime;

    @TableField("message")
    private String message;

    @TableField("createTime")
    private Date createTime;

    @TableField("databasesNmae")
    private String databasesNmae;


   /* @Override
    protected Serializable pkVal() {
        return this.id;
    }*/

}
