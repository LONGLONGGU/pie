package com.framework.pie.admin.service.impl;

import com.framework.pie.admin.dao.SysMenuMapper;
import com.framework.pie.admin.dao.SysRoleMapper;
import com.framework.pie.admin.dao.SysRoleMenuMapper;
import com.framework.pie.admin.model.SysMenu;
import com.framework.pie.admin.model.SysRole;
import com.framework.pie.admin.model.SysRoleMenu;
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
    @Override
    public List<SysRole> findAll() {
        return null;
    }

    @Override
    public List<SysMenu> findRoleMenus(Long roleId) {
        return null;
    }

    @Override
    public int saveRoleMenus(List<SysRoleMenu> records) {
        return 0;
    }

    @Override
    public List<SysRole> findByName(String name) {
        return null;
    }

    @Override
    public int save(SysRole record) {
        return 0;
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
        return null;
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
