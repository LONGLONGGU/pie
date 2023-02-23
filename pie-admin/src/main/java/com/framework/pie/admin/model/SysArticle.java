package com.framework.pie.admin.model;

import java.util.Date;
import com.framework.pie.mybatis.model.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 文章 
 * </p>
 *
 * @author longlong
 * @since 2020-09-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SysArticle对象",description = "文章信息表")
public class SysArticle extends BaseEntity {
    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    private String title;

    /**
     * 文章类型
     */
    @ApiModelProperty(value = "文章类型")
    private Integer type;

    /**
     * 封面图 900*383
     */
    @ApiModelProperty(value = "封面图")
    private String coverImage;

    /**
     * 作者
     */
    @ApiModelProperty(value = "作者")
    private String author;

    /**
     * 重要性
     */
    @ApiModelProperty(value = "重要性")
    private Integer importance;

    /**
     * 摘要
     */
    @ApiModelProperty(value = "摘要")
    private String contentShort;

    /**
     * 内容
     */
    @ApiModelProperty(value = "内容")
    private String content;

    /**
     * 点击次数
     */
    @ApiModelProperty(value = "点击次数")
    private Integer hitCount = 0;

    /**
     * 发布时间
     */
    @ApiModelProperty(value = "发布时间")
    private Date releaseTime;

    /**
     * 状态 状态 0：删除  1：草稿2：发表
     */
    @ApiModelProperty(value = "状态 0：删除  1：草稿2：发表")
    private Integer status;



}
