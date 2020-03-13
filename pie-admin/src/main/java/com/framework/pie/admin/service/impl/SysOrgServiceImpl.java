package com.framework.pie.admin.service.impl;

import com.framework.pie.admin.constant.SysConstants;
import com.framework.pie.admin.dao.SysMenuMapper;
import com.framework.pie.admin.dao.SysOrgMapper;
import com.framework.pie.admin.dao.SysOrgMenuMapper;
import com.framework.pie.admin.model.SysMenu;
import com.framework.pie.admin.model.SysOrg;
import com.framework.pie.admin.model.SysOrgMenu;
import com.framework.pie.admin.model.SysRoleMenu;
import com.framework.pie.admin.service.SysOrgService;
import com.framework.pie.core.page.MybatisPageHelper;
import com.framework.pie.core.page.PageRequest;
import com.framework.pie.core.page.PageResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysOrgServiceImpl implements SysOrgService {

    @Autowired
    private SysOrgMapper sysOrgMapper;
    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Autowired
    private SysOrgMenuMapper sysOrgMenuMapper;

    @Override
    public int save(SysOrg record) {
        if(record.getId() == null || record.getId() == 0) {
            return sysOrgMapper.insertSelective(record);
        }
        return sysOrgMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int delete(SysOrg record) {
        return 0;
    }

    @Override
    public int delete(List<SysOrg> records) {
        return 0;
    }

    @Override
    public SysOrg findById(Long id) {
        return null;
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        Object label = pageRequest.getParam("name");
        if(!StringUtils.isEmpty(label.toString())) {
            return MybatisPageHelper.findPage(pageRequest, sysOrgMapper, "findPageByName", label);
        }
        return MybatisPageHelper.findPage(pageRequest, sysOrgMapper);
    }

    @Override
    public List<SysOrg> findByName(String name) {
        return sysOrgMapper.findByName(name);
    }

    @Override
    public List<SysMenu> findOrgMenus(Long orgId) {
        SysOrg sysRole = sysOrgMapper.selectByPrimaryKey(orgId);
        //超级管理员返回全部权限
        if (SysConstants.ADMIN.equals(sysRole.getName())){
            return  sysMenuMapper.findAll();
        }
        return sysMenuMapper.findOrgMenus(orgId);
    }

    @Override
    public int saveRoleMenus(Long orgId, List<SysOrgMenu> records) {
        if(records == null || records.isEmpty()) {
            sysOrgMenuMapper.deleteByOrgId(orgId);
            return 1;
        }
        sysOrgMenuMapper.deleteByOrgId(orgId);
        for(SysOrgMenu record:records) {
            sysOrgMenuMapper.insertSelective(record);
        }
        return 1;
    }
}
