package com.framework.pie.admin.service;

import com.framework.pie.admin.model.SysDict;
import com.framework.pie.mybatis.service.CurdService;

import java.util.List;

/**
 * 字典管理
 * @author longlong
 */
public interface SysDictService extends CurdService<SysDict> {

    /**
     * 根据名称查询
     * @param lable
     * @return
     */
    List<SysDict> findByLable(String lable);

    /**
     * 根据名称查询
     * @param type
     * @return
     */
    List<SysDict> findByType(String type);

    /**
     * 获取数据字典全部类型
     * @return
     */
    List<String> findTypes();

}
