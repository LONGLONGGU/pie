package com.framework.pie.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.framework.pie.admin.model.SysOrg;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysOrgMapper extends BaseMapper<SysOrg> {

    List<SysOrg> findPage();

    List<SysOrg> findAll();

    List<SysOrg> findPageByName(@Param(value="name") String name);

    List<SysOrg> findByName(@Param(value="name") String name);

    SysOrg findByOrg(@Param(value="username") String username);
}