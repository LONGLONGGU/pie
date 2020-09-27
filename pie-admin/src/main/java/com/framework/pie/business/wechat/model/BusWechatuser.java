package com.framework.pie.business.wechat.model;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 微信用户 
 * </p>
 *
 * @author longlong
 * @since 2020-09-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BusWechatuser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * wechatInfo_id
     */
    @TableField("wechatInfo_id")
    private Long wechatinfoId;

    /**
     * 开放平台unionid
     */
    private String unionid;

    /**
     * 公众平台openid
     */
    private String openid;

    /**
     * 头像链接
     */
    private String headimgurl;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 国家
     */
    private String country;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 更新人
     */
    private String updatedBy;

    /**
     * 更新时间
     */
    private Date updatedTime;


}
