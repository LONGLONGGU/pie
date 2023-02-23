package com.framework.pie.admin.controller.app;

import com.framework.pie.admin.model.SysUser;
import com.framework.pie.admin.service.SysRoleService;
import com.framework.pie.admin.service.SysUserService;
import com.framework.pie.http.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Map;

/**
 * @author ：longlong
 * @date ：Created in 2022/1/5 11:29
 * @modified By：
 * @version: ：V1.0
 */

@RestController
@RequestMapping("user/app")
public class SysUserAppController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleService sysRoleService;

    @GetMapping("/findAddressBook/{parentId}")
    public HttpResult findAddressBook(@PathVariable("parentId") String parentId){
        return sysUserService.findAddressBook(parentId);
    }

    @GetMapping("/findUserById/{id}")
    public HttpResult findUserById(@PathVariable("id") String id){
        return HttpResult.ok(sysUserService.getById(id));
    }

    @PostMapping("/updateUser")
    public HttpResult updateUser(@RequestBody SysUser record){
        SysUser sysUser = new SysUser();
        sysUser.setId(record.getId());
        sysUser.setAvatarId(record.getAvatarId());
       return  HttpResult.ok(sysUserService.updateById(sysUser));

    }

    @PostMapping("/updatePassWord")
    public HttpResult updatePassWord(@RequestBody Map map){
        return sysUserService.updatePassWord(map);
    }

    @GetMapping("/sendCode/{phone}")
    public HttpResult sendCode(@PathVariable("phone") String phone){
        return HttpResult.ok(sysUserService.sendCode(phone));
    }

    @GetMapping("/checkCode/{phone}/{code}")
    public HttpResult checkCode(@PathVariable("phone") String phone,@PathVariable("code") String code){
        return HttpResult.ok(sysUserService.checkCode(phone,code));
    }

    @PostMapping("/updatePassByCheck")
    public HttpResult updatePassByCheck(@RequestBody Map map){
        return sysUserService.updatePassByCheck(map);
    }

}
