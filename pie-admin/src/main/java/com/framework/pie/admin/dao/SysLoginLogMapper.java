package com.framework.pie.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.framework.pie.admin.model.SysLoginLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysLoginLogMapper extends BaseMapper<SysLoginLog> {
    List<SysLoginLog> findPage();

    List<SysLoginLog> findPageByUserName(@Param(value="userName") String userName);

    List<SysLoginLog> findPageByStatus(@Param(value="status") String status);

    List<SysLoginLog> findPageByUserNameAndStatus(@Param(value="userName") String userName, @Param(value="status") String status);

    List<SysLoginLog> findByUserNameAndStatus(@Param(value="userName") String userName, @Param(value="status") String status, @Param("loginType") String loginType);
}