package com.framework.pie.admin.dao;

import com.framework.pie.admin.model.SysTokenApple;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* <p>
* token申请表 Mapper 接口
* </p>
*
* @author longlong
* @since 2022-08-18
*/
@Mapper
public interface SysTokenAppleMapper extends BaseMapper<SysTokenApple> {

    List<SysTokenApple> findPage(@Param("params") Map<String,Object> params);
}
