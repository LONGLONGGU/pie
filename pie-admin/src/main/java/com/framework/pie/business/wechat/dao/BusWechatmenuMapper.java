package com.framework.pie.business.wechat.dao;

import com.framework.pie.business.wechat.model.BusWechatmenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * <p>
    * 公众号菜单  Mapper 接口
    * </p>
 *
 * @author longlong
 * @since 2020-09-25
 */
@Repository
public interface BusWechatmenuMapper extends BaseMapper<BusWechatmenu> {

    int deleteByPrimaryKey(Long id);

    int insert(BusWechatmenu record);

    int insertSelective(BusWechatmenu record);

    BusWechatmenu selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BusWechatmenu record);

    int updateByPrimaryKey(BusWechatmenu record);

    List<BusWechatmenu> findPage();

    List<BusWechatmenu> findAll();

}
