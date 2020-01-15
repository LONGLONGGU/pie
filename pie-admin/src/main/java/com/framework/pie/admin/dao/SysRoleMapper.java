package com.framework.pie.admin.dao;

import com.framework.pie.admin.model.SysRole;
import org.springframework.stereotype.Repository;

@Repository
public interface SysRoleMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);
}