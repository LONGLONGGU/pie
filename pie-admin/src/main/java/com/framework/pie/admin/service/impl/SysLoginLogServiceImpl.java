package com.framework.pie.admin.service.impl;

import com.framework.pie.admin.dao.SysLoginLogMapper;
import com.framework.pie.admin.model.SysLoginLog;
import com.framework.pie.admin.service.SysLoginLogService;
import com.framework.pie.core.page.MybatisPageHelper;
import com.framework.pie.core.page.PageRequest;
import com.framework.pie.core.page.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysLoginLogServiceImpl implements SysLoginLogService {
    @Autowired
    private SysLoginLogMapper sysLoginLogMapper;
    @Override
    public int save(SysLoginLog record) {
        return sysLoginLogMapper.insert(record);
    }

    @Override
    public int delete(SysLoginLog record) {
        return 0;
    }

    @Override
    public int delete(List<SysLoginLog> records) {
        return 0;
    }

    @Override
    public SysLoginLog findById(Long id) {
        return null;
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        Object name = pageRequest.getParam("name");
        return MybatisPageHelper.findPage(pageRequest,sysLoginLogMapper,"findPageByName",name);
    }
}
