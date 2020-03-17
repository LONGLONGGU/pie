package com.framework.pie.admin.dao;

import com.framework.pie.admin.model.SysOrgDept;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SysOrgDeptMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysOrgDept record);

    int insertSelective(SysOrgDept record);

    SysOrgDept selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysOrgDept record);

    int updateByPrimaryKey(SysOrgDept record);

    int  deleteByDept(@Param("dept_id") Long dept_id);
}