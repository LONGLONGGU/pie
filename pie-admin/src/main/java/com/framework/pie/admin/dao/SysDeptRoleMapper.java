package com.framework.pie.admin.dao;

import com.framework.pie.admin.model.SysDeptRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
* <p>
* 角色机构 Mapper 接口
* </p>
*
* @author houjh
* @since 2021-11-15
*/
@Mapper
public interface SysDeptRoleMapper extends BaseMapper<SysDeptRole> {

    List<SysDeptRole> findPage();
}
