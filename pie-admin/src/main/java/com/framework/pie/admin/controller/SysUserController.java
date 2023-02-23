package com.framework.pie.admin.controller;

import com.framework.pie.admin.constant.SysConstants;
import com.framework.pie.admin.feign.FileUploadFeignClient;
import com.framework.pie.admin.model.SysUser;
import com.framework.pie.admin.service.SysRoleService;
import com.framework.pie.admin.service.SysUserService;
import com.framework.pie.admin.util.syslog.Log;
import com.framework.pie.constant.GlobalConstants;
import com.framework.pie.constant.ServerTypeCode;
import com.framework.pie.http.HttpResult;
import com.framework.pie.mybatis.page.PageRequest;
import com.framework.pie.utils.MD5Utils;
import com.framework.pie.web.utils.FileUtils;
import com.framework.pie.web.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("user")
public class SysUserController {
    @Autowired
    private FileUploadFeignClient fileUploadFeignClient;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleService sysRoleService;

    @GetMapping("/info")
    public HttpResult info(){
        String userName = JwtUtils.getUsername(); 
        return HttpResult.ok(this.sysUserService.getByName(userName));
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
        return sysUserService.switchingUserStatus(record);
    }

    @Log(value = "删除用户")
    @PostMapping(value="/delete")
    public HttpResult delete(@RequestBody List<SysUser> records) {
        List<String> ids = new ArrayList<>();
        for(SysUser record:records) {
            SysUser sysUser = sysUserService.getById(record.getId());
            if(sysUser != null && sysRoleService.checkedRole(sysUser.getName(),SysConstants.SUPERADMIN)) {
                return HttpResult.error("超级管理员不允许删除!");
            }
            if(sysUser != null && sysRoleService.checkedRole(sysUser.getName(),SysConstants.ADMIN)) {
                return HttpResult.error("系统管理员不允许删除!");
            }
            ids.add(record.getId());
        }
        return HttpResult.ok(sysUserService.removeByIds(ids));
    }

    @GetMapping(value="/findByName")
    public HttpResult findByName(@RequestParam String name) {
        return HttpResult.ok(sysUserService.getByName(name));
    }

    //查询用户权限
    @GetMapping(value="/findPermissions")
    public HttpResult findPermissions(@RequestParam String name) {
        return HttpResult.ok(sysUserService.listPermissions(name));
    }

    @GetMapping(value="/findUserRoles")
    public HttpResult findUserRoles(@RequestParam String userId) {
        return HttpResult.ok(sysUserService.listUserRoles(userId));
    }

    @PostMapping(value="/findPage")
    public HttpResult findPage(@RequestBody PageRequest pageRequest) {
        return HttpResult.ok(sysUserService.findPage(pageRequest));
    }

    @Log(value = "导出用户信息")
    @PostMapping(value="/exportExcelUser")
    public void exportExcelUser(@RequestBody PageRequest pageRequest, HttpServletResponse res) {
        File file = sysUserService.createUserExcelFile(pageRequest);
        FileUtils.downloadFile(res, file, file.getName());
    }

    @Log(value = "导出用户信息")
    @PostMapping(value="/exportAllExcelUser")
    public void exportAllExcelUser( HttpServletResponse res) {
        File file = sysUserService.exportAllExcelUser();
        FileUtils.downloadFile(res, file, file.getName());
    }
    @GetMapping("/findALl")
    public Object findAll(){
        return sysUserService.findAll();
    }

    @Log(value = "修改密码")
    @GetMapping(value="/updatePassword")
    public HttpResult updatePassword(@RequestParam String password, @RequestParam String newPassword) {
        SysUser user = sysUserService.getByName(JwtUtils.getUsername());
        if(user == null) {
            HttpResult.error("用户不存在!");
        }
        //组装原始密码信息
        String encPass = user.getSalt()+ GlobalConstants.PWD_SPLIT + user.getPassword();
        if(!MD5Utils.matches(password,encPass)){
            return HttpResult.error("原密码不正确!");
        }
        String salt = MD5Utils.getSalt();
        String encryptPwd = MD5Utils.encrypt(salt, newPassword);
        user.setSalt(salt);
        user.setPassword(encryptPwd);
        return HttpResult.ok(sysUserService.updateById(user));
    }

    @PostMapping("/fileUpload")
    @ResponseBody
    public HttpResult upload(@RequestParam("file") MultipartFile file){
        return fileUploadFeignClient.upload(JwtUtils.getUserId(),ServerTypeCode.ADMIN_FILE.getType(),file);

    }

    /**
     * 通过用户名称查询用户信息，将用户角色一起查询出来返回到资源认证服务器
     * @param name
     * @return
     */
    @GetMapping(value="/getSysUserByName")
    public HttpResult getSysUserByName(@RequestParam String name, HttpServletRequest request) {
        return sysUserService.getSysUserByName(name,request);
    }

   /* @PostMapping(value="/importUserExcel")
    public HttpResult importUserExcel(@RequestBody SysAttachments sysAttachments){

        return sysUserService.importUserExcel(sysAttachments);
    }*/

    /**
     * 重置组织机构超级管理员密码
     * @param orgId
     * @return
     */
    @Log(value = "重置机构管理员密码")
    @GetMapping(value = "/resetPwdByOrgId/{orgId}")
    public HttpResult resetPwdByOrgId(@PathVariable("orgId") String orgId){
        return sysUserService.resetPwdByOrgId(orgId);
    }

    /**
     * 重置用户密码信息
     * @param id
     * @return
     */
    @Log(value = "重置用户密码信息")
    @GetMapping(value = "/resetPwd/{id}")
    public HttpResult resetPwd(@PathVariable("id") String id){
        return sysUserService.resetPwd(id);
    }

    /**
     * 通过部门id查询用户信息
     * @param params
     * @return
     */
    @Log(value = "通过部门id查询用户信息")
    @PostMapping(value = "/getUserByDeptId")
    public HttpResult getUserByDeptId(@RequestBody() Map<String,Object> params){
        return sysUserService.getUserByDeptId(params);
    }
}
