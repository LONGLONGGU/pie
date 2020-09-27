package com.framework.pie.business.wechat.dao;

import com.framework.pie.business.wechat.model.BusWechatinfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
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
@Repository
public interface BusWechatinfoMapper extends BaseMapper<BusWechatinfo> {

    int deleteByPrimaryKey(Long id);

    int insert(BusWechatinfo record);

    int insertSelective(BusWechatinfo record);

    BusWechatinfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BusWechatinfo record);

    int updateByPrimaryKey(BusWechatinfo record);

    List<BusWechatinfo> findPage();

    List<BusWechatinfo> findAll();

}
