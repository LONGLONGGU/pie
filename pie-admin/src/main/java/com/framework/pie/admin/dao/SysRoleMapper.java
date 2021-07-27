package com.framework.pie.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.framework.pie.admin.model.SysRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysRoleMapper extends BaseMapper<SysRole> {
    int deleteByPrimaryKey(Long id);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole  selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);

    List<SysRole> findPage();

    List<SysRole> findAll();

    List<SysRole> findPageByOrgAndName(@Param(value = "orgId") Long orgId,@Param(value="name") String name);

    List<SysRole> findByName(@Param(value="name") String name);

    List<SysRole> findRoles(@Param(value="username") String username);

    List<SysRole> findByOrgId(@Param(value = "orgId") Long orgId);
}