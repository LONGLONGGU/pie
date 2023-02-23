package com.framework.pie.file.upload.model;

import com.framework.pie.mybatis.model.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SysAttachments对象",description = "系统附件表")
public class SysAttachments extends BaseEntity {

    @ApiModelProperty(value = "文件名称")
    private String fileName;

    @ApiModelProperty(value = "uuid")
    private String uuid;

    @ApiModelProperty(value = "文件路径")
    private String filePath;

    @ApiModelProperty(value = "文件大小")
    private Long fileSize;

    @ApiModelProperty(value = "文件类型")
    private String fileType;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "文件所属子系统CODE")
    private String serverCode;

    @ApiModelProperty(value = "文件所属子系统名称")
    private String serverName;

    @ApiModelProperty(value = "文件下载地址")
    private String downloadUrl;
}