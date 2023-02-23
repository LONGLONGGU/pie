package com.framework.pie.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.framework.pie.admin.model.SysDept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysDeptMapper extends BaseMapper<SysDept> {

    List<SysDept> findPage();

    List<SysDept> findAll();

    List<SysDept> findByDeptAll(String orgId);

    List<SysDept> findByParentId(@Param(value="orgId") String orgId,@Param(value="parentId")  String parentId);

    SysDept selectByName(String name);

    /**
     * 通过机构ID和部门名称来查询出唯一的管理部门信息，通过组织机构ID重置用户密码的时候用到
     * @param
     *      params
     *          orgId 组织机构ID
     *          deptName 部门名称
     * @return
     */
    SysDept getDeptByOrgId(@Param("params") Map<String,Object> params);

    /**
     * 通过ID查询部门名称
     * @param idList
     * @return
     */
    List<String> getDeptName(@Param("idList") List<String> idList);
}