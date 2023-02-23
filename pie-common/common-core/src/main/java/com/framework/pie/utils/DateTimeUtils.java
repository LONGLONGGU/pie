package com.framework.pie.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期时间相关工具
 * @author longlong
 */
public class DateTimeUtils {

	public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * 获取当前标准格式化日期时间
	 * @param
	 * @return
	 */
	public static String getDateTime() {
		return getDateTime(new Date());
	}
	
	/**
	 * 标准格式化日期时间
	 * @param date
	 * @return
	 */
	public static String getDateTime(Date date) {
		return (new SimpleDateFormat(DATE_FORMAT)).format(date);
	}

	/**
	 * 日期加或减num天
	 *
	 * @param sDate
	 * @param num
	 * @return
	 */
	public static Date getDateByDay(Date sDate, int num) {
		Calendar cal = Calendar.getInstance();
		// 设置开始时间
		cal.setTime(sDate);
		//增加或减少num天
		cal.add(Calendar.DATE, num);
		//cal.add(Calendar.DATE, -10);//减10天
		//cal.add(Calendar.DATE, 2);//加2天
		return cal.getTime();
	}
}
