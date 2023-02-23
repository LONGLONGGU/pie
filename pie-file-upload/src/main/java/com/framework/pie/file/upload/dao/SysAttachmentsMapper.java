package com.framework.pie.file.upload.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.framework.pie.file.upload.model.SysAttachments;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysAttachmentsMapper extends BaseMapper<SysAttachments> {

    /**
     * 分页查询附件信息
     * @return
     */
    List<SysAttachments> findPage();
}