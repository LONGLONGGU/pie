package com.framework.pie.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.framework.pie.admin.model.SysConfig;

public interface SysConfigMapper extends BaseMapper<SysConfig> {
    int insert(SysConfig record);

    int insertSelective(SysConfig record);
}