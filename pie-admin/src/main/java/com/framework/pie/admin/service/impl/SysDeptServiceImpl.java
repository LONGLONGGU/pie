package com.framework.pie.admin.service.impl;

import com.framework.pie.admin.dao.SysDeptMapper;
import com.framework.pie.admin.dao.SysOrgDeptMapper;
import com.framework.pie.admin.dao.SysUserMapper;
import com.framework.pie.admin.model.SysDept;
import com.framework.pie.admin.model.SysOrgDept;
import com.framework.pie.admin.model.SysUser;
import com.framework.pie.admin.service.SysDeptService;
import com.framework.pie.admin.service.SysOrgService;
import com.framework.pie.core.http.HttpResult;
import com.framework.pie.core.page.MybatisPageHelper;
import com.framework.pie.core.page.PageRequest;
import com.framework.pie.core.page.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysDeptServiceImpl implements SysDeptService {

    @Autowired
    private SysDeptMapper sysDeptMapper;
    @Autowired
    private SysOrgService sysOrgService;
    @Autowired
    private SysOrgDeptMapper sysOrgDeptMapper;
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public int save(SysDept record) {
        if(record.getId() == null || record.getId() == 0) {
            sysDeptMapper.insertSelective(record);
            Long deptId = record.getId();
            SysOrgDept sysOrgDept= new SysOrgDept();
            sysOrgDept.setDeptId(deptId);
            sysOrgDept.setOrgId(sysOrgService.findByOrg().getId());
            return sysOrgDeptMapper.insertSelective(sysOrgDept);
        }
        return sysDeptMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int delete(SysDept record) {
        sysOrgDeptMapper.deleteByDept(record.getId());
        return sysDeptMapper.deleteByPrimaryKey(record.getId());
    }

    @Override
    public int delete(List<SysDept> records) {
        return 0;
    }

    @Override
    public HttpResult remove(List<SysDept> records) {
        for(SysDept record:records) {
            List<SysUser> userList = sysUserMapper.findByDept(record.getId());
            if (userList.size() > 0){
                return HttpResult.error("所选部门存在合法用户，不允许删除！");
            }
            delete(record);
        }
        return HttpResult.ok("删除成功！");
    }

    @Override
    public SysDept findById(Long id) {
        return sysDeptMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        return MybatisPageHelper.findPage(pageRequest, sysDeptMapper);
    }

    @Override
    public List<SysDept> findTree() {
        List<SysDept> sysDepts = new ArrayList<>();
        List<SysDept> depts = sysDeptMapper.findByDeptAll(sysOrgService.findByOrg().getId());
        for (SysDept dept : depts) {
            if (dept.getParentId() == null || dept.getParentId() == 0) {
                dept.setLevel(0);
                sysDepts.add(dept);
            }
        }
        findChildren(sysDepts, depts);
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