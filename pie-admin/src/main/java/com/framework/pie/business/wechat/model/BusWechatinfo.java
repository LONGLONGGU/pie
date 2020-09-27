package com.framework.pie.business.wechat.model;

import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 公众号信息表 
 * </p>
 *
 * @author longlong
 * @since 2020-09-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BusWechatinfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * 企业名称 企业名称
     */
    private String name;

    /**
     * 公众号账号 公众号账号
     */
    private String account;

    /**
     * APP_ID 公众号APP_ID
     */
    private String appId;

    /**
     * APP_SECRET 公众号APP_SECRET
     */
    private String appSecret;

    /**
     * TOKEN 公众号自定义token
     */
    private String token;

    /**
     * 状态 状态 0：禁用 1：正常
     */
    private Integer status = 1;

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
