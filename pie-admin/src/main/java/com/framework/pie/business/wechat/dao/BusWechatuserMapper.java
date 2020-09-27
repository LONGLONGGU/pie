package com.framework.pie.business.wechat.dao;

import com.framework.pie.business.wechat.model.BusWechatuser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
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
@Repository
public interface BusWechatuserMapper extends BaseMapper<BusWechatuser> {

    int deleteByPrimaryKey(Long id);

    int insert(BusWechatuser record);

    int insertSelective(BusWechatuser record);

    BusWechatuser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BusWechatuser record);

    int updateByPrimaryKey(BusWechatuser record);

    List<BusWechatuser> findPage();

    List<BusWechatuser> findAll();

}
