package com.framework.pie.admin.service.impl;

import com.framework.pie.admin.constant.SysConstants;
import com.framework.pie.admin.dao.SysMenuMapper;
import com.framework.pie.admin.dao.SysRoleMapper;
import com.framework.pie.admin.dao.SysRoleMenuMapper;
import com.framework.pie.admin.model.SysMenu;
import com.framework.pie.admin.model.SysOrg;
import com.framework.pie.admin.model.SysRole;
import com.framework.pie.admin.model.SysRoleMenu;
import com.framework.pie.admin.service.SysOrgService;
import com.framework.pie.admin.service.SysRoleService;
import com.framework.pie.core.page.MybatisPageHelper;
import com.framework.pie.core.page.PageRequest;
import com.framework.pie.core.page.PageResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;
    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Autowired
    private SysOrgService sysOrgService;


    @Override
    public List<SysRole> findAll() {
        return sysRoleMapper.findAll();
    }

    @Override
    public List<SysMenu> findRoleMenus(Long roleId) {
        SysRole sysRole = sysRoleMapper.selectByPrimaryKey(roleId);
        if(SysConstants.ADMIN.equalsIgnoreCase(sysRole.getName())) {
            // 如果是超级管理员，返回全部
            return sysMenuMapper.findAll();
        }
        return sysMenuMapper.findRoleMenus(roleId);
    }

    @Override
    public int saveRoleMenus(Long roleId,List<SysRoleMenu> records) {
        if(records == null || records.isEmpty()) {
            sysRoleMenuMapper.deleteByRoleId(roleId);
            return 1;
        }
        sysRoleMenuMapper.deleteByRoleId(roleId);
        for(SysRoleMenu record:records) {
            sysRoleMenuMapper.insertSelective(record);
        }
        return 1;
    }

    @Override
    public List<SysRole> findByName(String name) {
        return sysRoleMapper.findByName(name);
    }

    @Override
    public int save(SysRole record) {
        SysOrg sysOrg = sysOrgService.findByOrg();
        record.setOrgId(sysOrg.getId());
        if(record.getId() == null || record.getId() == 0) {
            return sysRoleMapper.insertSelective(record);
        }
        return sysRoleMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int delete(SysRole record) {
        return 0;
    }

    @Override
    public int delete(List<SysRole> records) {
        return 0;
    }

    @Override
    public SysRole findById(Long id) {
        return sysRoleMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        Object label = pageRequest.getParam("name");
        if(!StringUtils.isEmpty(label.toString())) {
            return MybatisPageHelper.findPage(pageRequest, sysRoleMapper, "findPageByName", label);
        }
        return MybatisPageHelper.findPage(pageRequest, sysRoleMapper);
    }
}
