package com.framework.pie.mybatis.utils;


import java.util.List;
import java.util.Map;

@FunctionalInterface
public interface JudgeSql<T>{
    List<Map<String,Object>> getObjectList(T t);
}
