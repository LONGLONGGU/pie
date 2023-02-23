package com.framework.pie.business.wechat.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.framework.pie.business.wechat.model.BusWechatmenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
    * 公众号菜单  Mapper 接口
    * </p>
 *
 * @author longlong
 * @since 2020-09-25
 */
@Mapper
public interface BusWechatmenuMapper extends BaseMapper<BusWechatmenu> {

    int deleteByPrimaryKey(Long id);

    int insert(BusWechatmenu record);

    int insertSelective(BusWechatmenu record);

    BusWechatmenu selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BusWechatmenu record);

    int updateByPrimaryKey(BusWechatmenu record);

    List<BusWechatmenu> findPage();

    List<BusWechatmenu> findAll();

    BusWechatmenu findMneu(@Param(value="wechatinfoId") String wechatinfoId);

}
