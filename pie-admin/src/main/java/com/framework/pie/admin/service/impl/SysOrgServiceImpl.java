package com.framework.pie.admin.service.impl;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.framework.pie.admin.constant.SysConstants;
import com.framework.pie.admin.dao.*;
import com.framework.pie.admin.model.*;
import com.framework.pie.admin.service.SysOrgService;
import com.framework.pie.admin.service.SysUserService;
import com.framework.pie.admin.util.SecurityUtils;
import com.framework.pie.mybatis.page.MybatisPageHelper;
import com.framework.pie.mybatis.page.PageRequest;
import com.framework.pie.mybatis.page.PageResult;
import com.framework.pie.utils.PinyinUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SysOrgServiceImpl extends ServiceImpl<SysOrgMapper,SysOrg> implements SysOrgService {

    @Autowired
    private SysOrgMapper sysOrgMapper;
    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Autowired
    private SysOrgMenuMapper sysOrgMenuMapper;
    @Autowired
    private SysDeptMapper sysDeptMapper;
    @Autowired
    private SysOrgDeptMapper sysOrgDeptMapper;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public int saveByNativeSql(SysOrg sysOrg) {
        if(sysOrg.getId() == null || sysOrg.getId() == 0) {
            //新建默认部门
            SysDept sysDept = new SysDept();
            sysDept.setName("系统管理");
            sysDept.setOrderNum(1);
            sysDeptMapper.insertSelective(sysDept);
            Long deptId = sysDept.getId();
            sysOrgMapper.insertSelective(sysOrg);
            Long orgId = sysOrg.getId();
            SysOrgDept sysOrgDept = new SysOrgDept();
            sysOrgDept.setOrgId(orgId);
            sysOrgDept.setDeptId(deptId);
            sysOrgDeptMapper.insertSelective(sysOrgDept);
            //新建默认角色
            SysRole sysRole = new SysRole();
            sysRole.setName("admin");
            sysRole.setRemark("超级管理员");
            sysRole.setCreateBy("SuperAdmin");
            sysRole.setCreateTime(new Date());
            sysRole.setOrgId(orgId);
            sysRoleMapper.insertSelective(sysRole);
            Long roleId = sysRole.getId();
            //新建默认机构管理员
            SysUser sysUser = new SysUser();
            String name = PinyinUtils.getPingYin(sysOrg.getName());
            sysUser.setName(name);
            sysUser.setPassword("123456");
            sysUser.setStatus((byte) 1);
            sysUser.setDeptId(deptId);
            sysUserService.saveUser(sysUser);
            Long userId = sysUser.getId();
            //保存管理员角色关系
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(userId);
            sysUserRole.setRoleId(roleId);
            return  sysUserRoleMapper.insertSelective(sysUserRole);
        }
        return sysOrgMapper.updateByPrimaryKeySelective(sysOrg);
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
        return sysOrgMapper.selectByPrimaryKey(id);
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
        if (SysConstants.SUPERADMIN.equals(sysRole.getName())){
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

    @Override
    public SysOrg findByOrg() {
        String username =  SecurityUtils.getUsername();
        return sysOrgMapper.findByOrg(username);
    }
}
