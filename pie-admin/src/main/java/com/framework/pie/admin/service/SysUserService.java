package com.framework.pie.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.framework.pie.admin.dto.UserExportDTO;
import com.framework.pie.admin.model.SysUser;
import com.framework.pie.admin.model.SysUserRole;
import com.framework.pie.admin.vo.OAuthSysUser;
import com.framework.pie.http.HttpResult;
import com.framework.pie.mybatis.page.PageRequest;
import com.framework.pie.mybatis.page.PageResult;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 用户管理service
 * @author longlong
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 通过用户名查询用户
     * @return
     */

     HttpResult saveUser(SysUser record);

    /**
     * 修改个人信息
     * @param record
     * @return
     */
     int updatePersonal(SysUser record);

    /**
     * 用户状态切换
     */
    HttpResult switchingUserStatus(SysUser record);
    /**
     * 通过用户名查询用户
     * @return
     */

    SysUser getByName(String username);
    /**
     * 查询所有用户
     * @return
     */
    List<SysUser> findAll();
    /**
     * 查找用户的菜单权限标识集合
     * @param userName
     * @return
     */
    Set<String> listPermissions(String userName);

    /**
     * 查找用户的角色集合
     * @param userId
     * @return
     */
    List<SysUserRole> listUserRoles(String userId);

    /**
     * 通过用户名称查询用户信息
     * @param userName
     * @return
     */
    HttpResult getSysUserByName(String userName, HttpServletRequest request);

    /**
     * 生成用户信息Excel文件
     * @param pageRequest 要导出的分页查询参数
     * @return
     */
    File createUserExcelFile(PageRequest pageRequest);


    File exportAllExcelUser();

    /**
     * 用户信息导入
     * @param sysAttachments
     * @return
     */
    //HttpResult importUserExcel(SysAttachments sysAttachments);

    /**
     * 分页查询用户信息
     * @param pageRequest
     * @return
     */
    PageResult findPage(PageRequest pageRequest);

    /**
     * 重置组织机构管理员密码信息
     * @param orgId
     * @return
     */
    HttpResult resetPwdByOrgId(String orgId);

    /**
     * 通过Id重置用户密码信息
     * @param id
     * @return
     */
    HttpResult resetPwd(String id);

    /**
     * 通讯录查询
     * @param parentId
     * @return
     */
    HttpResult findAddressBook(String parentId);

    /**
     * 重置密码
     * @return
     */
    HttpResult updatePassWord(Map map);

    /**
     * 查询用户导出信息
     * @param params
     * @return
     */
    List<UserExportDTO> listUserExportData(@Param("params") Map<String,Object> params);


    HttpResult getUserByDeptId(Map<String,Object> params);

    Map<String,String> sendCode(String phone);

    boolean checkCode(String phone, String code);

    HttpResult updatePassByCheck(Map map);
}
