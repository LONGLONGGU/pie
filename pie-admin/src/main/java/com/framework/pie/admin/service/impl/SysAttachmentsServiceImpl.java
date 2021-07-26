package com.framework.pie.admin.service.impl;

import com.framework.pie.admin.dao.SysAttachmentsMapper;
import com.framework.pie.admin.model.SysAttachments;
import com.framework.pie.admin.service.SysAttachmentsService;
import com.framework.pie.mybatis.page.MybatisPageHelper;
import com.framework.pie.mybatis.page.PageRequest;
import com.framework.pie.mybatis.page.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
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
        return this.sysAttachmentsMapper.deleteByPrimaryKey(record.getId());
    }

    @Override
    public int delete(List<SysAttachments> records) {

        for (SysAttachments record : records){
            record = this.findById(record.getId());
            File file = new File(record.getFilePath());
            if(file.exists()) {
                file.delete();
            }
        this.delete(record);
        }
        return 1;
    }

    @Override
    public SysAttachments findById(Long id) {
        return sysAttachmentsMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        return MybatisPageHelper.findPage(pageRequest,sysAttachmentsMapper);
    }
}
