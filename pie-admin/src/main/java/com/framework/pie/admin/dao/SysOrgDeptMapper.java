package com.framework.pie.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.framework.pie.admin.model.SysOrgDept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysOrgDeptMapper extends BaseMapper<SysOrgDept> {

    int  deleteByDept(@Param("dept_id") String dept_id);
}