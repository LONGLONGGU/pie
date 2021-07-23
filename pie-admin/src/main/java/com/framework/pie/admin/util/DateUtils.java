package com.framework.pie.admin.util;


import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static String YYYY = "yyyy";
    public static String YYYY_MM = "yyyy-MM";
    public static String YYYY_MM_DD = "yyyy-MM-dd";
    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    /**
     * 获取当前Date型日期
     * @return
     */
    public static Date getNowDate(){
        return new Date();
    }

    /**
     * 当前日期的默认格式 yyyy-MM-dd
     * @return
     */
    public static String getDate(){
        return dateTimeNow(YYYY_MM_DD);
    }

    /**
     * 获取当前日期时间，格式：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static final String getTime(){
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 获取当前日期时间，格式：yyyyMMddHHmmss
     * @return
     */
    public static final String dateTimeNow(){
        return dateTimeNow(YYYYMMDDHHMMSS);
    }

    /**
     * 解析成固定格式 yyyy-MM-dd
     * @param date
     * @return
     */
    public static final String dateTime(final Date date){
        return parseDateToStr(YYYY_MM_DD,date);
    }

    /**
     * 将日期型格式解析为字符串型格式
     * @param format
     * @param date
     * @return
     */
    public static final String parseDateToStr(final String format,Date date){
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * 将当前日期解析成指定字符串格式
     * @param format
     * @return
     */
    public static final String dateTimeNow(final String format){
        return parseDateToStr(format,new Date());
    }

    /**
     * 将指定格式字符串日期解析为Date型
     * @param format
     * @param ts
     * @return
     */
    public static final Date dateTime(final String format,final String ts){
        try {
            return new SimpleDateFormat(format).parse(ts);
        }catch (ParseException e){
            throw new RuntimeException(e);
        }
    }

    /**
     * 将当前Date型时间解析成日期路径  ：2019/09/12
     */
    public static final String datePath(){
        Date now = new Date();
        return DateFormatUtils.format(now,"yyyy/MM/dd");
    }
    /**
     * 将当前Date型时间解析成日期路径  ：20190912
     */
    public static final String dateTime(){
        Date date = new Date();
        return DateFormatUtils.format(date,"yyyyMMdd");
    }

}
