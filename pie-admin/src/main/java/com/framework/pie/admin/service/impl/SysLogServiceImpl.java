package com.framework.pie.admin.service.impl;

import com.framework.pie.admin.dao.SysLogMapper;
import com.framework.pie.admin.model.SysLog;
import com.framework.pie.admin.service.SysLogService;
import com.framework.pie.core.page.MybatisPageHelper;
import com.framework.pie.core.page.PageRequest;
import com.framework.pie.core.page.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysLogServiceImpl implements SysLogService {
    @Autowired
    private SysLogMapper sysLogMapper;
    @Override
    public int save(SysLog record) {
        return sysLogMapper.insert(record);
    }

    @Override
    public int delete(SysLog record) {
        return 0;
    }

    @Override
    public int delete(List<SysLog> records) {
        return 0;
    }

    @Override
    public SysLog findById(Long id) {
        return null;
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        Object name = pageRequest.getParam("name");
        return  MybatisPageHelper.findPage(pageRequest,sysLogMapper,"findPageByName",name);
    }
}
