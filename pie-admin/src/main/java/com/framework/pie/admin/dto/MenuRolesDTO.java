package com.framework.pie.admin.dto;

import lombok.Data;

/**
 * 菜单对就角色规则URL信息
 */
@Data
public class MenuRolesDTO {
    /**
     * 菜单ID信息
     */
    private String menuId;
    /**
     * 菜单名称
     */
    private String menuName;
    /**
     * 模块路径
     */
    private String moduleInfo;
    /**
     * 后台控制器请求路径信息
     */
    private String pathInfo;
    /**
     * 能够请求当前菜单的角色集合
     */
    private String roleIds;
}
