package com.framework.pie.admin.dao;

import com.framework.pie.admin.model.SysDict;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysDictMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysDict record);

    int insertSelective(SysDict record);

    SysDict selectByPrimaryKey(Long id);

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

}