package com.framework.pie.admin.dao;

import com.framework.pie.admin.model.SysConfig;

public interface SysConfigMapper {
    int insert(SysConfig record);

    int insertSelective(SysConfig record);
}