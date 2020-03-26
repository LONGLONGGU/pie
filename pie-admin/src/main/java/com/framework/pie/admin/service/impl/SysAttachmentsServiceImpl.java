package com.framework.pie.admin.service.impl;

import com.framework.pie.admin.dao.SysAttachmentsMapper;
import com.framework.pie.admin.model.SysAttachments;
import com.framework.pie.admin.service.SysAttachmentsService;
import com.framework.pie.core.page.PageRequest;
import com.framework.pie.core.page.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysAttachmentsServiceImpl implements SysAttachmentsService {
    @Autowired
    private SysAttachmentsMapper sysAttachmentsMapper;
    @Override
    public int save(SysAttachments record) {
        if(record.getId() == null || record.getId() == 0) {
            return sysAttachmentsMapper.insertSelective(record);
        }
        return sysAttachmentsMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int delete(SysAttachments record) {
        return 0;
    }

    @Override
    public int delete(List<SysAttachments> records) {
        return 0;
    }

    @Override
    public SysAttachments findById(Long id) {
        return sysAttachmentsMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        return null;
    }
}
