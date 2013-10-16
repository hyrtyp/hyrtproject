package com.hyrt.cei.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtil{

	public StringUtil() {
	}

	/**
	 * Ｓｔｒｉｎｇ转化日期格式
	 */
	public static Date getDateType(String str) {
		Date nowDate = new Date();
		SimpleDateFormat temp = new SimpleDateFormat("yyyy-MM-dd");
		try {
			nowDate = temp.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nowDate;
	}
	/**
	 *	获得系统时间 
	 * 	@return	
	 */
	public static String getTime() {

		String sysTime = "";
		Date now = new Date();
		DateFormat mediumDateFormat = DateFormat.getDateTimeInstance(
				DateFormat.MEDIUM, DateFormat.MEDIUM);
		sysTime = mediumDateFormat.format(now);
		sysTime = "to_date('" + sysTime + "','yyyy-mm-dd hh24:mi:ss')";
		return sysTime;
	}
	
	/**
	 *	获得系统时间 
	 * 	@return	
	 */
	public static Date getSysTime() {
		Date now = new Date();
		return now;
	}
	public  static String DateToStr(Date date) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(date);
	}
}
