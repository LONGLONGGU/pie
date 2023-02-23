package com.framework.pie.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.framework.pie.admin.dto.MenuRolesDTO;
import com.framework.pie.admin.model.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<SysMenu> findPage();

    List<SysMenu> findPageByName(@Param(value="name") String name);

    List<SysMenu> findAll();

    List<SysMenu> findByUserName(@Param(value="userName") String userName);

    List<SysMenu> findRoleMenus(@Param(value="roleId") String roleId);

    List<SysMenu> findOrgMenus(@Param(value="orgId") String orgId);

    /**
     * 查询菜单访问需要的角色信息，聚合了机构可以访问的菜单信息
     * @return
     */
    List<MenuRolesDTO> listMenuRoles();
}