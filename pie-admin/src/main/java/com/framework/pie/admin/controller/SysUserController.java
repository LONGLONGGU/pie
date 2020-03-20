package com.framework.pie.admin.controller;

import com.framework.pie.admin.constant.SysConstants;
import com.framework.pie.admin.model.SysUser;
import com.framework.pie.admin.service.SysRoleService;
import com.framework.pie.admin.service.SysUserService;
import com.framework.pie.admin.util.PasswordUtils;
import com.framework.pie.admin.util.SecurityUtils;
import com.framework.pie.admin.util.syslog.Log;
import com.framework.pie.common.utils.FileUtils;
import com.framework.pie.core.http.HttpResult;
import com.framework.pie.core.page.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;

@RestController
@RequestMapping("user")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleService sysRoleService;

    @Log(value = "新增修改用户")
    @PostMapping(value="/save")
    public HttpResult save(@RequestBody SysUser record) {
        return sysUserService.saveUser(record);
    }

    @Log(value = "删除用户")
    @PreAuthorize("hasAuthority('sys:user:delete')")
    @PostMapping(value="/delete")
    public HttpResult delete(@RequestBody List<SysUser> records) {
        for(SysUser record:records) {
            SysUser sysUser = sysUserService.findById(record.getId());
            if(sysUser != null && sysRoleService.checkedRole(sysUser.getName(),SysConstants.SUPERADMIN)) {
                return HttpResult.error("超级管理员不允许删除!");
            }
            if(sysUser != null && sysRoleService.checkedRole(sysUser.getName(),SysConstants.ADMIN)) {
                return HttpResult.error("系统管理员不允许删除!");
            }
        }
        return HttpResult.ok(sysUserService.delete(records));
    }

    @GetMapping(value="/findByName")
    public HttpResult findByName(@RequestParam String name) {
        return HttpResult.ok(sysUserService.findByName(name));
    }

    //查询用户权限
    @GetMapping(value="/findPermissions")
    public HttpResult findPermissions(@RequestParam String name) {
        return HttpResult.ok(sysUserService.findPermissions(name));
    }

    @GetMapping(value="/findUserRoles")
    public HttpResult findUserRoles(@RequestParam Long userId) {
        return HttpResult.ok(sysUserService.findUserRoles(userId));
    }

    @PostMapping(value="/findPage")
    public HttpResult findPage(@RequestBody PageRequest pageRequest) {
        return HttpResult.ok(sysUserService.findPage(pageRequest));
    }

    @PostMapping(value="/exportExcelUser")
    public void exportExcelUser(@RequestBody PageRequest pageRequest, HttpServletResponse res) {
        File file = sysUserService.createUserExcelFile(pageRequest);
        FileUtils.downloadFile(res, file, file.getName());
    }
    @GetMapping("/findALl")
    public Object findAll(){
        return sysUserService.findAll();
    }

    @PreAuthorize("hasAuthority('sys:user:edit')")
    @GetMapping(value="/updatePassword")
    public HttpResult updatePassword(@RequestParam String password, @RequestParam String newPassword) {
        SysUser user = sysUserService.findByName(SecurityUtils.getUsername());
        if(user == null) {
            HttpResult.error("用户不存在!");
        }
//        if(SysConstants.ADMIN.equalsIgnoreCase(user.getName())) {
//            return HttpResult.error("超级管理员不允许修改!");
//        }
        if(!PasswordUtils.matches(user.getSalt(), password, user.getPassword())) {
            return HttpResult.error("原密码不正确!");
        }
        user.setPassword(PasswordUtils.encode(newPassword, user.getSalt()));
        return HttpResult.ok(sysUserService.save(user));
    }
}
