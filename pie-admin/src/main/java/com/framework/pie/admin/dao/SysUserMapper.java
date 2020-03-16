package com.framework.pie.admin.dao;

import com.framework.pie.admin.model.SysUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);
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
    SysUser findByName(@Param(value="name") String name);

    List<SysUser> findByDept(@Param(value = "deptId") Long deptId);

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
     * @param orgId
     * @return
     */
    List<SysUser> findPageByOrgAndName(@Param(value="orgId") Long orgId, @Param(value="name") String name);
}