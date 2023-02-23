package com.framework.pie.admin.component.security;

import com.framework.pie.admin.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 容器启动完成时加载角色权限规则至Redis缓存
 */
@Component
public class InitPermissionRoles implements CommandLineRunner {

    @Autowired
    private SysMenuService sysMenuService;

    @Override
    public void run(String... args) throws Exception {
        sysMenuService.refreshMenuRoles();
    }
}
