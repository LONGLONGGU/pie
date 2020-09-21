package com.framework.pie.admin.dao;

import com.framework.pie.admin.model.SysAttachments;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysAttachmentsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysAttachments record);

    int insertSelective(SysAttachments record);

    SysAttachments selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysAttachments record);

    int updateByPrimaryKey(SysAttachments record);

    List<SysAttachments> findPage();
}