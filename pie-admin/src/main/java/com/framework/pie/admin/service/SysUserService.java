package com.framework.pie.admin.service;

import com.framework.pie.admin.model.SysOrg;
import com.framework.pie.admin.model.SysUser;
import com.framework.pie.admin.model.SysUserRole;
import com.framework.pie.core.http.HttpResult;
import com.framework.pie.core.page.PageRequest;
import com.framework.pie.core.service.CurdService;

import java.io.File;
import java.util.List;
import java.util.Set;

/**
 * 用户管理service
 * @author longlong
 */
public interface SysUserService extends CurdService<SysUser> {

    /**
     * 通过用户名查询用户
     * @return
     */

     HttpResult saveUser(SysUser record);
    /**
     * 通过用户名查询用户
     * @return
     */

    SysUser findByName(String username);
    /**
     * 查询所有用户
     * @return
     */
    List<SysUser> findAll();
    /**
     * 查找用户的菜单权限标识集合
     * @param userName
     * @return
     */
    Set<String> findPermissions(String userName);

    /**
     * 查找用户的角色集合
     * @param userId
     * @return
     */
    List<SysUserRole> findUserRoles(Long userId);

    /**
     * 生成用户信息Excel文件
     * @param pageRequest 要导出的分页查询参数
     * @return
     */
    File createUserExcelFile(PageRequest pageRequest);
}
