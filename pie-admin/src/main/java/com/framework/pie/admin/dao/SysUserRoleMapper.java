package com.framework.pie.admin.dao;

import com.framework.pie.admin.model.SysUserRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysUserRoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysUserRole record);

    int insertSelective(SysUserRole record);

    SysUserRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUserRole record);

    int updateByPrimaryKey(SysUserRole record);

    /**
     * 查询用户角色
     * @param userId
     * @return
     */
    List<SysUserRole> findUserRoles(@Param(value="userId") Long userId);

    /**
     * 查询用户角色
     * @param roleId
     * @return
     */
    List<SysUserRole> findRoles(@Param(value="roleId") Long roleId);
    /**
     * 删除用户角色信息
     * @param userId
     * @return
     */
    int deleteByUserId(@Param(value="userId") Long userId);
}