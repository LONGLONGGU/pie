package com.framework.pie.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.framework.pie.admin.model.SysOrgMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysOrgMenuMapper extends BaseMapper<SysOrgMenu> {

    int deleteByOrgId(@Param(value="orgId") String orgId);

}