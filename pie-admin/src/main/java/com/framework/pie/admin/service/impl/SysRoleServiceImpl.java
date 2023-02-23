package com.framework.pie.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.framework.pie.admin.constant.SysConstants;
import com.framework.pie.admin.dao.SysMenuMapper;
import com.framework.pie.admin.dao.SysRoleMapper;
import com.framework.pie.admin.dao.SysRoleMenuMapper;
import com.framework.pie.admin.dao.SysUserRoleMapper;
import com.framework.pie.admin.model.*;
import com.framework.pie.admin.service.SysMenuService;
import com.framework.pie.admin.service.SysOrgService;
import com.framework.pie.admin.service.SysRoleService;
import com.framework.pie.http.HttpResult;
import com.framework.pie.mybatis.page.MybatisPageHelper;
import com.framework.pie.mybatis.page.PageRequest;
import com.framework.pie.mybatis.page.PageResult;
import com.framework.pie.web.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper,SysRole> implements SysRoleService {
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
    @Autowired
    private SysMenuService sysMenuService;

    @Override
    public HttpResult addOrUpdate(SysRole sysRole) {
        //验证角色名称是否存在
        if(!validateRoleName(sysRole)){
            return HttpResult.error("角色名称已存在，请修改后重新提交");
        }

        // 如果ID不为空则为修改操作
        if(StrUtil.isNotEmpty(sysRole.getId())){
            SysRole role = sysRoleMapper.selectById(sysRole.getId());
            if(SysConstants.SUPERADMIN.equalsIgnoreCase(role.getName())) {
                return HttpResult.error("超级管理员不允许修改!");
            }
            if(SysConstants.ADMIN.equalsIgnoreCase(role.getName())) {
                return HttpResult.error("系统管理员不允许修改!");
            }
            sysRole.setOrgId(JwtUtils.getOrgId());
            sysRole.setLastUpdateBy(JwtUtils.getUserId());
            //修改角色信息
            sysRoleMapper.updateById(sysRole);
            return HttpResult.ok("角色修改成功!");
        }

        //添加角色信息
        sysRole.setOrgId(JwtUtils.getOrgId());
        sysRole.setCreateBy(JwtUtils.getUserId());
        sysRoleMapper.insert(sysRole);
        return HttpResult.ok("角色添加成功!");
    }

    @Override
    public HttpResult delete(String roleId) {
        //验证角色用户信息
        QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysUserRole::getRoleId,roleId);
        List<SysUserRole> sysUserRoleList = sysUserRoleMapper.selectList(queryWrapper);
        if(sysUserRoleList != null && sysUserRoleList.size() > 0){
            return HttpResult.error("角色下还存在部门信息，不能删除，请先解除用户角色关系后再删除!");
        }

        QueryWrapper<SysRoleMenu> menuQueryWrapper = new QueryWrapper<>();
        menuQueryWrapper.lambda().eq(SysRoleMenu::getRoleId,roleId);
        List<SysRoleMenu> sysRoleMenuList = sysRoleMenuMapper.selectList(menuQueryWrapper);
        if(sysRoleMenuList != null && sysRoleMenuList.size() > 0){
            return HttpResult.error("角色与菜单还存在绑定关系，请先解除菜单角色关系后再删除!");
        }


        //删除角色信息
        this.removeById(roleId);
        //删除角色用户对应关系
        /*UpdateWrapper<SysUserRole> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().eq(SysUserRole::getRoleId,roleId);
        sysUserRoleMapper.delete(updateWrapper);*/
        return HttpResult.ok("角色删除成功!");
    }

    //验证角色名称是否存在
    private boolean validateRoleName(SysRole sysRole){
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        if(StrUtil.isEmpty(sysRole.getId())){
            queryWrapper.lambda().select(SysRole::getName).eq(SysRole::getName,sysRole.getName());
        } else {
            queryWrapper.lambda().select(SysRole::getName).eq(SysRole::getName,sysRole.getName()).ne(SysRole::getId,sysRole.getId());
        }
        SysRole role = sysRoleMapper.selectOne(queryWrapper);
        if(role != null){
            return  false;
        }
        return true;
    }

    @Override
    public List<SysRole> findAll() {
        return sysRoleMapper.findAll();
    }

    @Override
    public List<SysRole> findByOrgId() {
        return sysRoleMapper.findByOrgId(sysOrgService.findByOrg().getId());
    }

    @Override
    public List<SysMenu> findRoleMenus(String roleId) {
        SysRole sysRole = sysRoleMapper.selectById(roleId);
        if(SysConstants.SUPERADMIN.equalsIgnoreCase(sysRole.getName())) {
            // 如果是超级管理员，返回全部
            return sysMenuMapper.findAll();
        }
        return sysMenuMapper.findRoleMenus(roleId);
    }

    @Override
    public HttpResult saveRoleMenus(String roleId,List<SysRoleMenu> records) {
        if(records == null || records.isEmpty()) {
            sysRoleMenuMapper.deleteByRoleId(roleId);
            return HttpResult.ok("角色授权成功!");
        }
        sysRoleMenuMapper.deleteByRoleId(roleId);
        for(SysRoleMenu record:records) {
            sysRoleMenuMapper.insert(record);
        }
        //保存权限时刷新缓存信息
        sysMenuService.refreshMenuRoles();
        return HttpResult.ok("角色授权成功!");
    }

    @Override
    public List<SysRole> findByName(String name) {
        return sysRoleMapper.findByName(name);
    }


    @Override
    public PageResult findPage(PageRequest pageRequest) {
        Object name = pageRequest.getParam("name");
        return MybatisPageHelper.findPage(pageRequest, sysRoleMapper,"findPageByOrgAndName",JwtUtils.getOrgId(),name);
    }

    //验证是否拥有该角色
    public boolean checkedRole(String roleName){
        List<SysRole> roles = sysRoleMapper.findRoles(JwtUtils.getUsername());
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
