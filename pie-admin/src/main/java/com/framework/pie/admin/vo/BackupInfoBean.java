package com.framework.pie.admin.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * @author ：longlong
 * @date ：Created in 2021/9/6 14:34
 * @modified By：
 * @version: ：V1.0
 */
@Data
public class BackupInfoBean {

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 数据源id
     */
    private Long datasourceId;
    /**
     * 附件id
     */
    private Long attachmentId;

    /**
     * 是否上传云端 0 没有上传，1上传成功
     */
    private Integer isUpload = 0;

    /**
     * 备份路径
     */
    private String path;

    /**
     * 备注
     */
    private String note;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createTime = new Date();

    /**
     * 更新人
     */
    private String lastUpdateBy;

    /**
     * 更新时间
     */
    private Date lastUpdateTime;

    /**
     * 是否删除 -1：已删除 0：正常
     */
    private Integer delFlag;
}
