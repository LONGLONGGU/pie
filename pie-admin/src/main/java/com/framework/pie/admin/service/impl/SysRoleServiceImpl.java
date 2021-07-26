package com.framework.pie.admin.service.impl;

import com.framework.pie.admin.constant.SysConstants;
import com.framework.pie.admin.dao.SysMenuMapper;
import com.framework.pie.admin.dao.SysRoleMapper;
import com.framework.pie.admin.dao.SysRoleMenuMapper;
import com.framework.pie.admin.dao.SysUserRoleMapper;
import com.framework.pie.admin.model.*;
import com.framework.pie.admin.service.SysOrgService;
import com.framework.pie.admin.service.SysRoleService;
import com.framework.pie.admin.util.SecurityUtils;
import com.framework.pie.mybatis.page.MybatisPageHelper;
import com.framework.pie.mybatis.page.PageRequest;
import com.framework.pie.mybatis.page.PageResult;
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
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;


    @Override
    public List<SysRole> findAll() {

        return sysRoleMapper.findAll();
    }

    @Override
    public List<SysRole> findByOrgId() {
        return sysRoleMapper.findByOrgId(sysOrgService.findByOrg().getId());
    }

    @Override
    public List<SysMenu> findRoleMenus(Long roleId) {
        SysRole sysRole = sysRoleMapper.selectByPrimaryKey(roleId);
        if(SysConstants.SUPERADMIN.equalsIgnoreCase(sysRole.getName())) {
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
        if(record.getId() == null || record.getId() == 0) {
            return sysRoleMapper.insertSelective(record);
        }
        return sysRoleMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int delete(SysRole record) {
        return sysRoleMapper.deleteByPrimaryKey(record.getId());
    }

    @Override
    public int delete(List<SysRole> records) {
        for (SysRole sysRole : records){
          List<SysUserRole> sysUserRoles = sysUserRoleMapper.findRoles(sysRole.getId());
          if (sysUserRoles.size() > 0){
              return 1;
          }
          delete(sysRole);
        }
        return 0;
    }

    @Override
    public SysRole findById(Long id) {
        return sysRoleMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        Object name = pageRequest.getParam("name");
        return MybatisPageHelper.findPage(pageRequest, sysRoleMapper,"findPageByOrgAndName",sysOrgService.findByOrg().getId(),name);
    }

    //验证是否拥有该角色
    public boolean checkedRole(String roleName){
        List<SysRole> roles = sysRoleMapper.findRoles(SecurityUtils.getUsername());
        for (SysRole role: roles){
            if (roleName.equals(role.getName())){
                return true;
            }
        }
        return false;
    }

    //验证该用户是否拥有该角色
    public boolean checkedRole(String username,String roleName){
        List<SysRole> roles = sysRoleMapper.findRoles(username);
        for (SysRole role: roles){
            if (roleName.equals(role.getName())){
                return true;
            }
        }
        return false;
    }
}
