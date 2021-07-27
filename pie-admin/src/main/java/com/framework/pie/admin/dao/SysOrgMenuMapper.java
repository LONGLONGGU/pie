package com.framework.pie.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.framework.pie.admin.model.SysOrgMenu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SysOrgMenuMapper extends BaseMapper<SysOrgMenu> {
    int deleteByPrimaryKey(Long id);

    int insert(SysOrgMenu record);

    int insertSelective(SysOrgMenu record);

    SysOrgMenu selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysOrgMenu record);

    int updateByPrimaryKey(SysOrgMenu record);

    int deleteByOrgId(@Param(value="orgId") Long orgId);


}