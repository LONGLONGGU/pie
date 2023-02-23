package com.framework.pie.business.wechat.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.framework.pie.business.wechat.model.BusWechatinfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
    * 公众号信息表  Mapper 接口
    * </p>
 *
 * @author longlong
 * @since 2020-09-25
 */
@Mapper
public interface BusWechatinfoMapper extends BaseMapper<BusWechatinfo> {

    int deleteByPrimaryKey(Long id);

    int insert(BusWechatinfo record);

    int insertSelective(BusWechatinfo record);

    BusWechatinfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BusWechatinfo record);

    int updateByPrimaryKey(BusWechatinfo record);

    List<BusWechatinfo> findPage();

    List<BusWechatinfo> findAll();

}
