package com.framework.pie.admin.service.impl;

import com.framework.pie.admin.dao.SysLoginLogMapper;
import com.framework.pie.admin.model.SysLoginLog;
import com.framework.pie.admin.service.SysLoginLogService;
import com.framework.pie.core.page.MybatisPageHelper;
import com.framework.pie.core.page.PageRequest;
import com.framework.pie.core.page.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SysLoginLogServiceImpl implements SysLoginLogService {
    @Autowired
    private SysLoginLogMapper sysLoginLogMapper;
    @Override
    public int save(SysLoginLog record){
        if(record.getId() == null || record.getId() == 0) {
        return sysLoginLogMapper.insertSelective(record);
    }
      return sysLoginLogMapper.updateByPrimaryKeySelective(record);
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
        Object userName = pageRequest.getParam("userName");
        Object status = pageRequest.getParam("status");
        if(userName != null && status != null) {
            return MybatisPageHelper.findPage(pageRequest, sysLoginLogMapper, "findPageByUserNameAndStatus", userName, status);
        } else if(status != null) {
            return MybatisPageHelper.findPage(pageRequest, sysLoginLogMapper, "findPageByStatus", status);
        } else if(userName != null) {
            return MybatisPageHelper.findPage(pageRequest, sysLoginLogMapper, "findPageByUserName", userName);
        }
        return MybatisPageHelper.findPage(pageRequest, sysLoginLogMapper);
    }
    @Transactional
    @Override
    public int writeLoginLog(String userName, String ip) {
        List<SysLoginLog> sysLoginLogs = sysLoginLogMapper.findByUserNameAndStatus(userName, SysLoginLog.STATUS_ONLINE);
        for(SysLoginLog sysLoginLog:sysLoginLogs) {
            sysLoginLog.setStatus(SysLoginLog.STATUS_LOGIN);
            sysLoginLogMapper.updateByPrimaryKey(sysLoginLog);
        }
        SysLoginLog record = new SysLoginLog();
        record.setUserName(userName);
        record.setIp(ip);
        record.setStatus(SysLoginLog.STATUS_LOGOUT);
        sysLoginLogMapper.insertSelective(record);
        record.setStatus(SysLoginLog.STATUS_ONLINE);
        sysLoginLogMapper.insertSelective(record);
        return 0;
    }
}
