package com.framework.pie.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.framework.pie.admin.model.SysOrg;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysOrgMapper extends BaseMapper<SysOrg> {
    int deleteByPrimaryKey(Long id);

    int insert(SysOrg record);

    int insertSelective(SysOrg record);

    SysOrg selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysOrg record);

    int updateByPrimaryKey(SysOrg record);

    List<SysOrg> findPage();

    List<SysOrg> findAll();

    List<SysOrg> findPageByName(@Param(value="name") String name);

    List<SysOrg> findByName(@Param(value="name") String name);

    SysOrg findByOrg(@Param(value="username") String username);
}