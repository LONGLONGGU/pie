package com.framework.pie.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.framework.pie.admin.model.SysDict;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysDictMapper extends BaseMapper<SysDict> {
    int deleteByPrimaryKey(String id);

    int insert(SysDict record);

    int insertSelective(SysDict record);

    SysDict selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysDict record);

    int updateByPrimaryKey(SysDict record);
    /**
     * 分页查询
     * @return
     */
    List<SysDict> findPage();

    /**
     * 根据标签名称分页查询
     * @param labe
     * @return
     */
    List<SysDict> findByLable(@Param(value = "label") String labe);

    /**
     * 根据标签名称分页查询
     * @param label
     * @return
     */
    List<SysDict> findPageByLabel(@Param(value = "label") String label);

    /**
     * 根据标签名称和类型分页查询
     * @param label
     * @return
     */
    List<SysDict> findPageByTypeAndLabel(@Param(value = "type") String type ,@Param(value = "label") String label);

    /**
     * 根据类型查询
     * @param type
     * @return
     */
    List<SysDict> findByType(@Param(value = "type") String type);

    /**
     * 获取数据字典全部类型
     * @return
     */
     List<String> findTypes();

}