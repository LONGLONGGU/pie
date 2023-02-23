package com.framework.pie.admin.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysTableMapper {

    @Select("select * from information_schema.TABLES where TABLE_SCHEMA=(select database())")
    List<Map> findPage();

    @Select("select * from information_schema.TABLES where TABLE_SCHEMA=(select database()) and TABLE_NAME like CONCAT('%',#{0},'%')")
    List<Map> findPageByTableColumn(String tableName);
}
