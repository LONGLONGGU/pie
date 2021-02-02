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
 * 公众号菜单 
 * </p>
 *
 * @author longlong
 * @since 2020-09-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BusWechatmenu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 菜单信息
     */
    private String menu;

    /**
     * wechatInfo_id
     */
    @TableField("wechatInfo_id")
    private Long wechatinfoId;

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
    /**
     * 状态 状态 0：禁用 1：正常
     */
    private Integer status = 1;


}
