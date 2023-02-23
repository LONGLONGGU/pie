package com.framework.pie.admin.dao;

import com.framework.pie.admin.model.SysOauthClient;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 客户端信息表 Mapper 接口
 * </p>
 *
 * @author houjh
 * @since 2021-08-04
 */
@Mapper
public interface SysOauthClientMapper extends BaseMapper<SysOauthClient> {

    List<SysOauthClient> findPage();

    List<SysOauthClient> findAll();

}
