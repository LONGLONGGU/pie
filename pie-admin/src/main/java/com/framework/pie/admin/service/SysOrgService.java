package com.framework.pie.admin.service;

import com.framework.pie.admin.model.SysMenu;
import com.framework.pie.admin.model.SysOrg;
import com.framework.pie.admin.model.SysOrgMenu;
import com.framework.pie.mybatis.service.CurdService;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface SysOrgService extends CurdService<SysOrg> {
    /**
     * 根据名称查询
     * @param name
     * @return
     */
    List<SysOrg> findByName(String name);

    /**
     * 查询机构菜单集合
     * @return
     */
    List<SysMenu> findOrgMenus(@RequestParam Long orgId);

    /**
     * 保存机构菜单
     * @param records
     * @return
     */
    int saveRoleMenus(Long orgId,List<SysOrgMenu> records);

    /**
     * 通过用户名查询用户所属机构
     * @return
     */

    SysOrg findByOrg();
}
