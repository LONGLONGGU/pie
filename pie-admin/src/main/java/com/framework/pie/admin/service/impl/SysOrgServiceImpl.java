package com.framework.pie.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.cloud.commons.lang.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.framework.pie.admin.constant.SysConstants;
import com.framework.pie.admin.dao.*;
import com.framework.pie.admin.model.*;
import com.framework.pie.admin.service.SysMenuService;
import com.framework.pie.admin.service.SysOrgService;
import com.framework.pie.constant.GlobalConstants;
import com.framework.pie.http.HttpResult;
import com.framework.pie.mybatis.page.MybatisPageHelper;
import com.framework.pie.mybatis.page.PageRequest;
import com.framework.pie.mybatis.page.PageResult;
import com.framework.pie.utils.MD5Utils;
import com.framework.pie.utils.PinyinUtils;
import com.framework.pie.web.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysMenuService sysMenuService;

    @Override
    @Transactional
    public HttpResult addOrUpdate(SysOrg sysOrg) {
        //先验证机构名称是否重新，如果重复则返回
        boolean validateOrgName = validateOrgName(sysOrg);
        if(!validateOrgName){
            return HttpResult.error("机构名称已存在，请更换后重新添加!");
        }

        if(sysOrg.getId() == null || "".equals(sysOrg.getId())) {
            //新建默认部门信息
            SysDept sysDept = new SysDept();
            sysDept.setName(GlobalConstants.DEFAULT_ADMIN_DEPT_NAME);
            sysDept.setOrderNum(1);
            sysDept.setCreateBy(JwtUtils.getUserId());
            sysDeptMapper.insert(sysDept);
            //更新部门pathInfo信息
            sysDept.setPathInfo(sysDept.getId());
            sysDeptMapper.updateById(sysDept);

            //保存机构信息
            sysOrg.setCreateBy(JwtUtils.getUserId());
            sysOrgMapper.insert(sysOrg);

            //保存机构部门对应关系
            String deptId = sysDept.getId();
            String orgId = sysOrg.getId();
            SysOrgDept sysOrgDept = new SysOrgDept();
            sysOrgDept.setOrgId(orgId);
            sysOrgDept.setDeptId(deptId);
            sysOrgDept.setCreateBy(JwtUtils.getUserId());
            sysOrgDeptMapper.insert(sysOrgDept);

            //新建默认角色信息
            SysRole sysRole = new SysRole();
            sysRole.setName(SysConstants.ADMIN);
            sysRole.setRemark("超级管理员");
            sysRole.setCreateBy(JwtUtils.getUserId());
            sysRole.setCreateTime(new Date());
            sysRole.setOrgId(orgId);
            sysRole.setCreateBy(JwtUtils.getUserId());
            sysRoleMapper.insert(sysRole);
            String roleId = sysRole.getId();

            //新建默认机构管理员
            SysUser sysUser = new SysUser();
            String name = PinyinUtils.getPingYin(sysOrg.getName());
            sysUser.setName(name);
            sysUser.setNickName("机构管理员");
            String salt = MD5Utils.getSalt();
            String password = MD5Utils.encrypt(salt, sysOrg.getOrgAdminPwd());
            sysUser.setSalt(salt);
            sysUser.setPassword(password);
            sysUser.setStatus((byte) 1);
            sysUser.setDeptId(deptId);
            sysUser.setCreateBy(JwtUtils.getUserId());
            sysUserMapper.insert(sysUser);
            String userId = sysUser.getId();

            //保存管理员角色关系
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(userId);
            sysUserRole.setRoleId(roleId);
            sysUserRole.setCreateBy(JwtUtils.getUserId());
            sysUserRoleMapper.insert(sysUserRole);
            return HttpResult.ok("机构添加成功!");
        }
        sysOrg.setLastUpdateBy(JwtUtils.getUserId());
        sysOrgMapper.updateById(sysOrg);
        return HttpResult.ok("机构修改成功!");
    }

    @Override
    @Transactional
    public HttpResult removeOrg(String orgId) {
        //机构先不提供删除功能
        return HttpResult.ok("删除成功");
    }

    /**
     * 验证机构名称是否重复
     * @param sysOrg
     * @return
     */
    private boolean validateOrgName(SysOrg sysOrg){
        QueryWrapper<SysOrg> queryWrapper = new QueryWrapper<>();
        if(StrUtil.isNotEmpty(sysOrg.getId())){
            queryWrapper.lambda().select(SysOrg::getName).eq(SysOrg::getName,sysOrg.getName()).ne(SysOrg::getId,sysOrg.getId());
        } else {
            queryWrapper.lambda().select(SysOrg::getName).eq(SysOrg::getName,sysOrg.getName());
        }
        SysOrg org = sysOrgMapper.selectOne(queryWrapper);
        if(org != null){
            return false;
        }
        return true;
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
    public List<SysMenu> findOrgMenus(String orgId) {
        SysOrg sysOrg = sysOrgMapper.selectById(orgId);
        //超级管理员返回全部权限
        if (SysConstants.SUPERADMIN.equals(sysOrg.getName())){
            return sysMenuMapper.findAll();
        }
        return sysMenuMapper.findOrgMenus(orgId);
    }

    @Override
    public int saveRoleMenus(String orgId, List<SysOrgMenu> records) {
        if(records == null || records.isEmpty()) {
            sysOrgMenuMapper.deleteByOrgId(orgId);
            return 1;
        }
        sysOrgMenuMapper.deleteByOrgId(orgId);
        for(SysOrgMenu record:records) {
            sysOrgMenuMapper.insert(record);
        }
        // 保存机构菜单授权时，处理缓存信息
        sysMenuService.refreshMenuRoles();
        return 1;
    }

    @Override
    public SysOrg findByOrg() {
        String username = JwtUtils.getUsername();
        return sysOrgMapper.findByOrg(username);
    }
}
