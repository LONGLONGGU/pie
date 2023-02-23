package com.framework.pie.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.framework.pie.admin.model.SysMenu;
import com.framework.pie.admin.model.SysRole;
import com.framework.pie.admin.model.SysRoleMenu;
import com.framework.pie.http.HttpResult;
import com.framework.pie.mybatis.page.PageRequest;
import com.framework.pie.mybatis.page.PageResult;

import java.util.List;

public interface SysRoleService extends IService<SysRole> {

    /**
     * 添加或修改角色信息
     * @param sysRole
     */
    HttpResult addOrUpdate(SysRole sysRole);

    /**
     * 删除角色信息
     * @param roleId
     * @return
     */
    HttpResult delete(String roleId);

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
    List<SysMenu> findRoleMenus(String roleId);

    /**
     * 保存角色菜单
     * @param records
     * @return
     */
    HttpResult saveRoleMenus(String roleId,List<SysRoleMenu> records);

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

    /**
     * 分页查询角色列表信息
     * 分页查询角色列表信息
     * @param pageRequest
     * @return
     */
    PageResult findPage(PageRequest pageRequest);
}
