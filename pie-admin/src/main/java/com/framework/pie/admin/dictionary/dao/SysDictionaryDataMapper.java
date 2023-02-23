package com.framework.pie.admin.dictionary.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.framework.pie.admin.dictionary.model.SysDictionaryData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author houjh
 * @since 2021-09-06
 */
@Mapper
public interface SysDictionaryDataMapper extends BaseMapper<SysDictionaryData> {

    /**
     * 分页查询数据字典信息
     * @param params
     * @return
     */
    List<SysDictionaryData> findPage(@Param("params") Map<String,Object> params);


    /**
     * 根据code查询字典详情
     * @param params
     * @return
     */
    List<SysDictionaryData> findByCode(@Param("params") Map<String, Object> params);

}
