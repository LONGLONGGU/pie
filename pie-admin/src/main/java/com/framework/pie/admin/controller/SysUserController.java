package com.framework.pie.admin.controller;

import com.framework.pie.admin.constant.SysConstants;
import com.framework.pie.admin.dao.SysUserMapper;
import com.framework.pie.admin.model.SysUser;
import com.framework.pie.admin.service.SysRoleService;
import com.framework.pie.admin.service.SysUserService;
import com.framework.pie.admin.service.UploadService;
import com.framework.pie.admin.util.JwtTokenUtils;
import com.framework.pie.admin.util.PasswordUtils;
import com.framework.pie.admin.util.SecurityUtils;
import com.framework.pie.admin.util.syslog.Log;
import com.framework.pie.http.HttpResult;
import com.framework.pie.mybatis.page.PageRequest;
import com.framework.pie.web.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;

@RestController
@RequestMapping("user")
public class SysUserController {
    @Autowired
    private UploadService fileService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysUserMapper sysUserMapper;

    @GetMapping("/info")
    public HttpResult info(String token){
        String userNmae= JwtTokenUtils.getUsernameFromToken(token);
        return HttpResult.ok(this.sysUserService.findByName(userNmae));
    }
    @Log(value = "新增修改用户")
    @PostMapping(value="/save")
    public HttpResult save(@RequestBody SysUser record) {
        return sysUserService.saveUser(record);
    }

    @Log(value = "修改个人信息")
    @PostMapping(value="/updatePersonal")
    public HttpResult updatePersonal(@RequestBody SysUser record) {
        return HttpResult.ok(sysUserService.updatePersonal(record));
    }
    @Log(value = "切换用户状态")
    @PostMapping(value="/userStatusSwitching")
    public HttpResult userStatusSwitching(@RequestBody SysUser record) {
        return sysUserService.userStatusSwitching(record);
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

    @PreAuthorize("hasAuthority('sys:user:view')")
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
        if(!PasswordUtils.matches(user.getSalt(), password, user.getPassword())) {
            return HttpResult.error("原密码不正确!");
        }
        user.setPassword(PasswordUtils.encode(newPassword, user.getSalt()));
        return HttpResult.ok(sysUserMapper.updateByPrimaryKeySelective(user));
    }
    @PostMapping("/fileUpload")
    @ResponseBody
    public HttpResult upload(@RequestParam("file") MultipartFile file){
        return fileService.upload(file);

    }
}
