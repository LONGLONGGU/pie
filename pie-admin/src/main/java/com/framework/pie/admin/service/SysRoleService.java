package com.framework.pie.admin.service;

import com.framework.pie.admin.model.SysMenu;
import com.framework.pie.admin.model.SysRole;
import com.framework.pie.admin.model.SysRoleMenu;
import com.framework.pie.mybatis.service.CurdService;

import java.util.List;

public interface SysRoleService extends CurdService<SysRole> {

    /**
     * 查询全部
     * @return
     */
    List<SysRole> findAll();

    /**
     * 通过机构查询查询全部
     * @return
     */
    List<SysRole> findByOrgId();

    /**
     * 查询角色菜单集合
     * @return
     */
    List<SysMenu> findRoleMenus(Long roleId);

    /**
     * 保存角色菜单
     * @param records
     * @return
     */
    int saveRoleMenus(Long roleId,List<SysRoleMenu> records);

    /**
     * 根据名称查询
     * @param name
     * @return
     */
    List<SysRole> findByName(String name);

    /**
     * 校验是否拥有该角色
     * @param roleName
     * @return
     */
    boolean checkedRole(String roleName);

    /**
     * 校验该用户否拥有该角色
     * @param roleName
     * @return
     */
     boolean checkedRole(String username,String roleName);
}
