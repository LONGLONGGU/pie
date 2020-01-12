package com.framework.pie.admin.service;

import com.framework.pie.admin.model.SysUser;

import java.util.List;

/**
 *查找所有用户
 */
public interface SysUserService {

    /**
     * 查询所有用户
     * @return
     */
    List<SysUser> findAll();
}
