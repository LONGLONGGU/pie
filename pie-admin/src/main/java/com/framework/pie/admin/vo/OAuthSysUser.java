package com.framework.pie.admin.vo;

import lombok.Data;
import java.util.Set;

/**
 * 用户信息查询，将角色信息一起封装进去
 */
@Data
public class OAuthSysUser {

    /**
     * 用户ID
     */
    private String id;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户加密码增强字符串
     */
    private String salt;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户状态
     */
    private Integer status;

    /**
     * 用户部门
     */
    private String deptId;

    /**
     * 用户部门名称
     */
    private String deptName;

    /**
     * 用户角色信息
     */
    Set<String> roleIds;

    /**
     * 超级管理员标识，系统是通过角色进行判断的
     */
    private boolean superAdminFlag;

    /**
     * 机构ID信息
     */
    private String orgId;

    /**
     * 机构行政区划信息
     */
    private String orgDistrictId;

    /**
     * 机构状态信息
     */
    private boolean orgStatus;

    /**
     * 登录类型
     */
    private String loginType;
}
