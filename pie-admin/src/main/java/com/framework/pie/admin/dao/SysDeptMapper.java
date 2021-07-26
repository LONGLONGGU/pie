package com.framework.pie.admin.dao;

import com.framework.pie.admin.model.SysDept;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysDeptMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysDept record);

    int insertSelective(SysDept record);

    SysDept selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysDept record);

    int updateByPrimaryKey(SysDept record);


    List<SysDept> findPage();

    List<SysDept> findAll();

    List<SysDept> findByDeptAll(Long orgId);

    List<SysDept> findByParentId(@Param(value="orgId") Long orgId,@Param(value="parentId")  Long parentId);
}