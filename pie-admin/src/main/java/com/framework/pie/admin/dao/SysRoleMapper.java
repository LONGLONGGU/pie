package com.framework.pie.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.framework.pie.admin.model.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    List<SysRole> findPage();

    List<SysRole> findAll();

    List<SysRole> findPageByOrgAndName(@Param(value = "orgId") String orgId,@Param(value="name") String name);

    List<SysRole> findByName(@Param(value="name") String name);

    List<SysRole> findRoles(@Param(value="username") String username);

    List<SysRole> findByOrgId(@Param(value = "orgId") String orgId);

    /**
     * 根据用户ID和部门ID查询用户角色信息
     * @param param
     *          userId 用户ID
     *          deptId 部门ID
     * @return
     */
    List<Map<String,Object>> listRoleByUserId(@Param("param") Map param);
}