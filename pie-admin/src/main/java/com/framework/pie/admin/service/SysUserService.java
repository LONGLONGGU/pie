package com.framework.pie.admin.service;

import com.framework.pie.admin.model.SysUser;
import com.framework.pie.admin.model.SysUserRole;
import com.framework.pie.http.HttpResult;
import com.framework.pie.mybatis.page.PageRequest;
import com.framework.pie.mybatis.service.CurdService;

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
     * 修改个人信息
     * @param record
     * @return
     */
     int updatePersonal(SysUser record);

    /**
     * 用户状态切换
     */
    HttpResult userStatusSwitching(SysUser record);
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
