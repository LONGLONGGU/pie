package com.framework.pie.admin.service.impl;

import com.framework.pie.admin.model.SysUser;
import com.framework.pie.admin.dao.SysUserMapper;
import com.framework.pie.admin.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public List<SysUser> findAll() {
        return sysUserMapper.findAll();
    }
}
