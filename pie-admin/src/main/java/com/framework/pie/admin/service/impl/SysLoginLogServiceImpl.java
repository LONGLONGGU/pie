package com.framework.pie.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.framework.pie.admin.dao.SysLoginLogMapper;
import com.framework.pie.admin.model.SysLoginLog;
import com.framework.pie.admin.service.SysLoginLogService;
import com.framework.pie.mybatis.page.MybatisPageHelper;
import com.framework.pie.mybatis.page.PageRequest;
import com.framework.pie.mybatis.page.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SysLoginLogServiceImpl extends ServiceImpl<SysLoginLogMapper,SysLoginLog> implements SysLoginLogService {

    @Autowired
    private SysLoginLogMapper sysLoginLogMapper;



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
    public int writeLoginLog(String userName, String ip ,String ipAddr, String loginType) {
//        List<SysLoginLog> sysLoginLogs = sysLoginLogMapper.findByUserNameAndStatus(userName, SysLoginLog.STATUS_ONLINE, loginType);
//        for(SysLoginLog sysLoginLog:sysLoginLogs) {
//            sysLoginLog.setStatus(SysLoginLog.STATUS_LOGIN);
//            sysLoginLogMapper.updateById(sysLoginLog);
//        }
        SysLoginLog record = new SysLoginLog();
        record.setUserName(userName);
        record.setIp(ip);
        record.setIpAddr(ipAddr);
        record.setLoginType(loginType);
        record.setStatus(SysLoginLog.STATUS_LOGIN);
        sysLoginLogMapper.insert(record);
        return 0;
    }

    @Transactional
    @Override
    public int  writeLoginOut(String userName, String ip,String ipAddr, String loginType){
//        List<SysLoginLog> sysLoginLogs = sysLoginLogMapper.findByUserNameAndStatus(userName, SysLoginLog.STATUS_ONLINE,loginType);
//        for(SysLoginLog sysLoginLog:sysLoginLogs) {
//            sysLoginLog.setStatus(SysLoginLog.STATUS_LOGIN);
//            sysLoginLogMapper.updateById(sysLoginLog);
//        }
        SysLoginLog record = new SysLoginLog();
        record.setUserName(userName);
        record.setIp(ip);
        record.setIpAddr(ipAddr);
        record.setLoginType(loginType);
        record.setStatus(SysLoginLog.STATUS_LOGOUT);
        sysLoginLogMapper.insert(record);
        return 0;
    }
}
