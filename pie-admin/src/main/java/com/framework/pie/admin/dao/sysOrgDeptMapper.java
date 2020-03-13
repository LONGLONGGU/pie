package com.framework.pie.admin.dao;

import com.framework.pie.admin.model.sysOrgDept;

public interface sysOrgDeptMapper {
    int deleteByPrimaryKey(Long id);

    int insert(sysOrgDept record);

    int insertSelective(sysOrgDept record);

    sysOrgDept selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(sysOrgDept record);

    int updateByPrimaryKey(sysOrgDept record);
}