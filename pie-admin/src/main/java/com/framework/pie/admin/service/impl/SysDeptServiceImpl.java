package com.framework.pie.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.framework.pie.admin.dao.SysDeptMapper;
import com.framework.pie.admin.dao.SysDeptRoleMapper;
import com.framework.pie.admin.dao.SysOrgDeptMapper;
import com.framework.pie.admin.dao.SysUserMapper;
import com.framework.pie.admin.model.SysDept;
import com.framework.pie.admin.model.SysDeptRole;
import com.framework.pie.admin.model.SysOrgDept;
import com.framework.pie.admin.model.SysUser;
import com.framework.pie.admin.service.SysDeptService;
import com.framework.pie.constant.GlobalConstants;
import com.framework.pie.http.HttpResult;
import com.framework.pie.web.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper,SysDept> implements SysDeptService {

    @Autowired
    private SysDeptMapper sysDeptMapper;
    @Autowired
    private SysOrgDeptMapper sysOrgDeptMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysDeptRoleMapper sysDeptRoleMapper;

    @Override
    public HttpResult addOrUpdate(SysDept record) {
        if(StrUtil.isEmpty(record.getId())) {
            // 保存部门信息
            record.setCreateBy(JwtUtils.getUserId());
            sysDeptMapper.insert(record);

            //更新pathInfo信息
            record.setPathInfo(getPathInfo(record));
            sysDeptMapper.updateById(record);

            // 保存部门机构对应关系
            String deptId = record.getId();
            SysOrgDept sysOrgDept= new SysOrgDept();
            sysOrgDept.setDeptId(deptId);
            sysOrgDept.setOrgId(JwtUtils.getOrgId());
            sysOrgDept.setCreateBy(JwtUtils.getUserId());
            sysOrgDeptMapper.insert(sysOrgDept);
        } else {
            //设置pathInfo
            record.setPathInfo(getPathInfo(record));
            sysDeptMapper.updateById(record);
        }

        //更新部门角色信息
        //1、先删除角色信息
        UpdateWrapper<SysDeptRole> deptRoleQueryWrapper = new UpdateWrapper<>();
        deptRoleQueryWrapper.lambda().eq(SysDeptRole::getDeptId,record.getId())
                .set(SysDeptRole::getDelFlag, GlobalConstants.DEL_FLAG_DELETE)
                .set(SysDeptRole::getLastUpdateBy,JwtUtils.getUserId());
        sysDeptRoleMapper.update(new SysDeptRole(),deptRoleQueryWrapper);

        //2、保存角色信息
        if(record.getDeptRoles() != null && record.getDeptRoles().size() > 0){
            record.getDeptRoles().forEach(sysDeptRole -> {
                sysDeptRole.setCreateBy(JwtUtils.getUserId());
                sysDeptRoleMapper.insert(sysDeptRole);
            });
        }

        return HttpResult.ok("操作成功");
    }

    private String getPathInfo(SysDept record){
        String pathInfo = record.getId();
        if(StrUtil.isNotEmpty(record.getParentId())){
            SysDept parentSysDept = sysDeptMapper.selectById(record.getParentId());
            pathInfo = parentSysDept.getPathInfo() + "_" + pathInfo;
        }
        return pathInfo;
    }

    @Override
    public HttpResult batchDelete(List<SysDept> records) {
        List<String> idList = new ArrayList<>();
        for(SysDept record:records) {
            List<SysUser> userList = sysUserMapper.findByDept(record.getId());
            if (userList.size() > 0){
                return HttpResult.error("所选部门存在合法用户，不允许删除！");
            }
            idList.add(record.getId());
        }
        //配置成修改，以便设置更新和修改用户信息
        if(idList != null && idList.size() > 0){
            idList.forEach(id->{
                UpdateWrapper<SysDept> updateWrapper = new UpdateWrapper<>();
                updateWrapper.lambda().eq(SysDept::getId,id)
                        .set(SysDept::getDelFlag,GlobalConstants.DEL_FLAG_DELETE)
                        .set(SysDept::getLastUpdateBy,JwtUtils.getUserId());
                sysDeptMapper.update(new SysDept(),updateWrapper);
            });
        }

        return HttpResult.ok("删除成功！");
    }

    @Override
    public SysDept selectByName(String name) {
        return sysDeptMapper.selectByName(name);
    }

    @Override
    public HttpResult getDeptRoles(String deptId) {
        QueryWrapper<SysDeptRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysDeptRole::getDeptId,deptId);
        List<SysDeptRole> sysDeptRoles = sysDeptRoleMapper.selectList(queryWrapper);
        return HttpResult.ok(sysDeptRoles);
    }


    @Override
    public List<SysDept> findTree() {
        List<SysDept> sysDepts = new ArrayList<>();
        List<SysDept> depts = sysDeptMapper.findByDeptAll(JwtUtils.getOrgId());
        for (SysDept dept : depts) {
            if (dept.getParentId() == null || "0".equals(dept.getParentId()) || "".equals(dept.getParentId()) ) {
                dept.setLevel(0);
                sysDepts.add(dept);
            }
        }
        findChildren(sysDepts, depts);
        return sysDepts;
    }

    @Override
    public List<SysDept> findTree(String parentId) {
        if (parentId.equals("0")) parentId ="";
        List<SysDept> sysDepts = sysDeptMapper.findByParentId(JwtUtils.getOrgId(),parentId);
        sysDepts.stream().forEach(item ->{
            if(sysDeptMapper.findByParentId(JwtUtils.getOrgId(),item.getId()).stream().count() > 0){
                item.setHasChildren(false);
            }
        });
        return sysDepts;
    }

    private void findChildren(List<SysDept> sysDepts, List<SysDept> depts) {
        for (SysDept sysDept : sysDepts) {
            List<SysDept> children = new ArrayList<>();
            for (SysDept dept : depts) {
                if (sysDept.getId() != null && sysDept.getId().equals(dept.getParentId())) {
                    dept.setParentName(dept.getName());
                    dept.setLevel(sysDept.getLevel() + 1);
                    children.add(dept);
                }
            }
            sysDept.setChildren(children);
            findChildren(children, depts);
        }
    }

}