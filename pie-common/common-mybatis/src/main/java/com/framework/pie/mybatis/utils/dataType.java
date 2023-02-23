package com.framework.pie.mybatis.utils;
/**
 * @Author: guanqiangzhang
 * @Date:11:19 2021/11/3
 *  判断数据库中字段类型
 *
 */
public class dataType {
    private static  final  String[] s=
            {"CHAR","VARCHAR","LONGVARCHAR","TEXT","MEDIUMTEXT","LONGTEXT","ENUM","DATETIME"};

    public static boolean getIsString(String type){
        boolean flag=false;
        for (String s1 : s) {
            if(type.indexOf(s1.toLowerCase())!=-1)
                flag=true;
        }
        return flag;
    }
}
