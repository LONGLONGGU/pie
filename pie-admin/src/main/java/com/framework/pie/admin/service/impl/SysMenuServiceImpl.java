package com.framework.pie.admin.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.framework.pie.admin.constant.SysConstants;
import com.framework.pie.admin.dao.SysMenuMapper;
import com.framework.pie.admin.dao.SysOrgMapper;
import com.framework.pie.admin.dto.MenuRolesDTO;
import com.framework.pie.admin.model.SysMenu;
import com.framework.pie.admin.model.SysOrg;
import com.framework.pie.admin.service.SysMenuService;
import com.framework.pie.admin.service.SysRoleService;
import com.framework.pie.constant.GlobalConstants;
import com.framework.pie.redis.client.RedisClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper,SysMenu> implements SysMenuService {
    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysOrgMapper sysOrgMapper;
    @Autowired
    private RedisClient redisClient;

    @Override
    public int addOrUpdate(SysMenu record) {
        if(StringUtils.isEmpty(record.getId())) {
            return sysMenuMapper.insert(record);
        }
        if(StringUtils.isEmpty(record.getParentId())) {
            record.setParentId(null);
        }
        return sysMenuMapper.updateById(record);
    }

    @Override
    public List<SysMenu> findTree(String userName, int menuType) {
        List<SysMenu> sysMenus = new ArrayList<>();
        List<SysMenu> menus = findByUser(userName);
        for (SysMenu menu : menus) {
            if (menu.getParentId() == null || "".equals(menu.getParentId()) || "0".equals(menu.getParentId())) {
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
    public List<MenuRolesDTO> listMenuRoles() {
        return sysMenuMapper.listMenuRoles();
    }

    @Override
    public boolean refreshMenuRoles() {
        //先删除缓存信息
        redisClient.del(GlobalConstants.URL_PERM_ROLES_KEY);
        Map<String,List<String>> menuRoleMap = new HashMap<>();
        List<MenuRolesDTO> menuRoleList = sysMenuMapper.listMenuRoles();
        if(CollectionUtil.isNotEmpty(menuRoleList)){
            //排除URL为空的菜单角色信息
            List<MenuRolesDTO> menuRole = menuRoleList.stream().filter(item -> StrUtil.isNotBlank(item.getModuleInfo())
                    && StrUtil.isNotBlank(item.getPathInfo())).collect(Collectors.toList());
            if(CollectionUtil.isNotEmpty(menuRole)){
                menuRole.forEach(item -> {
                    String menuKey = item.getModuleInfo()+item.getPathInfo();
                    List<String> roles = Arrays.asList(item.getRoleIds().split(","));
                    menuRoleMap.put(menuKey,roles);
                });
            }
        }
        redisClient.set(GlobalConstants.URL_PERM_ROLES_KEY, JSONObject.toJSONString(menuRoleMap));
        return true;
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
