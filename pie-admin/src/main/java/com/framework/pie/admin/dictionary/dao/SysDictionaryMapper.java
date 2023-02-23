package com.framework.pie.admin.dictionary.dao;

import com.framework.pie.admin.dictionary.model.SysDictionary;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author houjh
 * @since 2021-09-06
 */
@Mapper
public interface SysDictionaryMapper extends BaseMapper<SysDictionary> {

    List<SysDictionary> findPage();

    List<SysDictionary> findAll();
}
