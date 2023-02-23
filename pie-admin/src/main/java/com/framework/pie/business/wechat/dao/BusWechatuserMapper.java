package com.framework.pie.business.wechat.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.framework.pie.business.wechat.model.BusWechatuser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
    * 微信用户  Mapper 接口
    * </p>
 *
 * @author longlong
 * @since 2020-09-25
 */
@Mapper
public interface BusWechatuserMapper extends BaseMapper<BusWechatuser> {

    int deleteByPrimaryKey(Long id);

    int insert(BusWechatuser record);

    int insertSelective(BusWechatuser record);

    BusWechatuser selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BusWechatuser record);

    int updateByPrimaryKey(BusWechatuser record);

    List<BusWechatuser> findPage();

    List<BusWechatuser> findAll();

}
