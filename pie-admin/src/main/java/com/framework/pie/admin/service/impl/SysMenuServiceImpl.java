package com.framework.pie.admin.service.impl;

import com.framework.pie.admin.constant.SysConstants;
import com.framework.pie.admin.dao.SysMenuMapper;
import com.framework.pie.admin.dao.SysOrgMapper;
import com.framework.pie.admin.dao.SysRoleMapper;
import com.framework.pie.admin.model.SysMenu;
import com.framework.pie.admin.model.SysOrg;
import com.framework.pie.admin.model.SysRole;
import com.framework.pie.admin.service.SysMenuService;
import com.framework.pie.admin.service.SysOrgService;
import com.framework.pie.admin.service.SysRoleService;
import com.framework.pie.admin.util.SecurityUtils;
import com.framework.pie.core.page.PageRequest;
import com.framework.pie.core.page.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysMenuServiceImpl implements SysMenuService {
    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Autowired
    private SysOrgService sysOrgService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysOrgMapper sysOrgMapper;

    @Override
    public List<SysMenu> findTree(String userName, int menuType) {
        List<SysMenu> sysMenus = new ArrayList<>();
        List<SysMenu> menus = findByUser(userName);
        for (SysMenu menu : menus) {
            if (menu.getParentId() == null || menu.getParentId() == 0) {
                menu.setLevel(0);
                if(!exists(sysMenus, menu)) {
                    sysMenus.add(menu);
                }
            }
        }
        sysMenus.sort((o1, o2) -> o1.getOrderNum().compareTo(o2.getOrderNum()));
        findChildren(sysMenus, menus, menuType);
        return sysMenus;
    }

    @Override
    public List<SysMenu> findByUser(String userName) {
        //超级机构管理员
        if(userName == null || "".equals(userName) || sysRoleService.checkedRole(userName,SysConstants.SUPERADMIN)) {
            return sysMenuMapper.findAll();
        }
        //普通机构管理员
        if(userName == null || "".equals(userName) ||  sysRoleService.checkedRole(userName,SysConstants.ADMIN)) {
            //查询用户所属机构
            SysOrg sysOrg = sysOrgMapper.findByOrg(userName);
            return sysMenuMapper.findOrgMenus(sysOrg.getId());
        }
        //普通用户
        return sysMenuMapper.findByUserName(userName);
    }

    @Override
    public int save(SysMenu record) {
        if(record.getId() == null || record.getId() == 0) {
            return sysMenuMapper.insertSelective(record);
        }
        if(record.getParentId() == null) {
            record.setParentId(0L);
        }
        return sysMenuMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int delete(SysMenu record) {
        sysMenuMapper.deleteByPrimaryKey(record.getId());
        return 1;
    }

    @Override
    public int delete(List<SysMenu> records) {
        return 0;
    }

    @Override
    public SysMenu findById(Long id) {
        return null;
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        return null;
    }


    private void findChildren(List<SysMenu> SysMenus, List<SysMenu> menus, int menuType) {
        for (SysMenu SysMenu : SysMenus) {
            List<SysMenu> children = new ArrayList<>();
            for (SysMenu menu : menus) {
                if(menuType == 1 && menu.getType() == 2) {
                    // 如果是获取类型不需要按钮，且菜单类型是按钮的，直接过滤掉
                    continue ;
                }
                if (SysMenu.getId() != null && SysMenu.getId().equals(menu.getParentId())) {
                    menu.setParentName(SysMenu.getName());
                    menu.setLevel(SysMenu.getLevel() + 1);
                    if(!exists(children, menu)) {
                        children.add(menu);
                    }
                }
            }
            SysMenu.setChildren(children);
            children.sort((o1, o2) -> o1.getOrderNum().compareTo(o2.getOrderNum()));
            findChildren(children, menus, menuType);
        }
    }
    private boolean exists(List<SysMenu> sysMenus, SysMenu sysMenu) {
        boolean exist = false;
        for(SysMenu menu:sysMenus) {
            if(menu.getId().equals(sysMenu.getId())) {
                exist = true;
            }
        }
        return exist;
    }
}
