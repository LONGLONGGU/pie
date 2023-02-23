package com.framework.pie.quartz.utils;


import java.text.SimpleDateFormat;
import java.util.Date;

public class DFUtils {
    public static  final SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static  String fomat(Date date){
        return sdf.format(date);
    }
}
