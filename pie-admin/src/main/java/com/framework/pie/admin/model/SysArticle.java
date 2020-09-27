package com.framework.pie.admin.model;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
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
public class SysArticle implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 文章类型
     */
    private Integer type;

    /**
     * 封面图 900*383
     */
    private String coverImage;

    /**
     * 作者
     */
    private String author;

    /**
     * 重要性
     */
    private Integer importance;

    /**
     * 摘要
     */
    private String contentShort;

    /**
     * 内容
     */
    private String content;

    /**
     * 点击次数
     */
    private Integer hitCount = 0;

    /**
     * 发布时间
     */
    private Date releaseTime;

    /**
     * 状态 状态 0：删除  1：草稿2：发表
     */
    private Integer status;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    private Date createdTime = new Date();

    /**
     * 更新人
     */
    private String updatedBy;

    /**
     * 更新时间
     */
    private Date updatedTime;


}
