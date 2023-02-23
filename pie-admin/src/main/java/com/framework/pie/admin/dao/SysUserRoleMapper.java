package com.framework.pie.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.framework.pie.admin.model.SysUserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {
    /**
     * 查询用户角色
     * @param userId
     * @return
     */
    List<SysUserRole> findUserRoles(@Param(value="userId") String userId);

    /**
     * 查询用户角色
     * @param roleId
     * @return
     */
    List<SysUserRole> findRoles(@Param(value="roleId") String roleId);

    /**
     * 删除用户角色信息
     * @param userId
     * @return
     */
    int deleteByUserId(@Param(value="userId") String userId);
}