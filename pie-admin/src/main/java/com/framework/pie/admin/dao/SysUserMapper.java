package com.framework.pie.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.framework.pie.admin.dto.UserExportDTO;
import com.framework.pie.admin.model.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
    /**
     * 查询全部
     * @return
     */
    List<SysUser> findAll();
    /**
     * 分页查询
     * @return
     */
    List<SysUser> findPage();

    /**
     * 根据用户名查询
     * @param name
     * @return
     */
    SysUser getByName(@Param(value="name") String name);

    /**
     * 通过部门查询用户信息
     * @param deptId
     * @return
     */
    List<SysUser> findByDept(@Param(value = "deptId") String deptId);

    /**
     * 根据用户名分页查询
     * @param name
     * @return
     */
    List<SysUser> findPageByName(@Param(value="name") String name);
    /**
     * 根据name和email查询
     * @param name
     * @return
     */
    List<SysUser> findPageByNameAndEmail(@Param(value="name") String name, @Param(value="email") String email);

    /**
     * 根据org查询
     * @param params
     * @return
     */
    List<SysUser> findPageByOrgAndDeptAndName(@Param("params") Map<String,Object> params);

    /**
     * 通过用户id查询用户角色信息
     * @param map
     * @return
     */
    List<String> listRoleIdsByUserId(@Param("param") Map map);

    /**
     * 查询用户导出信息
     * @param params
     * @return
     */
    List<UserExportDTO> listUserExportData(@Param("params") Map<String,Object> params);

    /**
     * 查询用户信息
     * @param params
     * @return
     */
    List<SysUser> getByOrgIdAndDeptId(@Param("params") Map<String,Object> params);


    String checkPhone(String phone);
}