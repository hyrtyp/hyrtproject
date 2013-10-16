package com.hyrt.mwpm.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.hyrt.mwpm.base.BaseDao;

public class StringUtil extends BaseDao {

	public StringUtil() {
	}

	private static String gbk = "GBK";

	private static String iso = "ISO-8859-1";

	private static String cp850 = "cp850";

	public static String inspectionlimit = "3";

	public static String iso2gbk(String str) {
		try {
			if (str != null && !str.equals("")) {
				return new String(str.getBytes(iso), gbk);
			} else {
				return "";
			}
		} catch (Exception ex) {
			return str;
		}
	}

	public static String gbk2iso(String str) {
		try {
			return new String(str.getBytes(gbk), iso);
		} catch (Exception ex) {
			return str;
		}
	}

	public static String cp850gbk(String str) {
		try {
			return new String(str.getBytes(cp850), gbk);
		} catch (Exception ex) {
			return str;
		}
	}

	public static String gbkcp850(String str) {
		try {
			return new String(str.getBytes(gbk), cp850);
		} catch (Exception ex) {
			return str;
		}
	}

	public static String gbkisocp850(String str) {
		try {
			String strStr = new String(str.getBytes(iso), gbk);
			return new String(strStr.getBytes(gbk), cp850);
		} catch (Exception e) {
			return str;
		}
	}

	public static String iso2cp850(String str) {
		try {
			return new String(str.getBytes(iso), cp850);
		} catch (Exception ex) {
			return str;
		}
	}

	/**	增加对获得的字符串为null的处理,将Null转化成"--"		*/
	public static String StringNoNull(String str) {
		String strString = "-";
		if (("null".equals(str)) || ("".equals(str)) || (str == null)) {
			return strString;
		} else {
			return str;
		}
	}

	/**	增加对获得的字符串为null的处理,将Null转化成""		*/
	public static String StringIsNull(String str) {
		String strString = "";
		if ((str == null) || ("null".equals(str)) || ("".equals(str))) {
			return strString;
		} else {
			return str;
		}
	}

	/**
	 *	获得系统时间 
	 * 	@return	
	 */
	public static Date getSysTime() {
		Date now = new Date();
		return now;
	}

	/**
	 * Ｓｔｒｉｎｇ转化日期格式
	 */
	public static Date getDateType(String str) {
		Date nowDate = new Date();
		SimpleDateFormat temp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			nowDate = temp.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nowDate;
	}
	
	public static String getCurDate(String dateStr, String fieldStr,
			String toDataOrChar) {
		    String sqlField = "";
			String timeMuster = "";
			if (dateStr != null && dateStr.length() > 0) {
				if (dateStr.equalsIgnoreCase("date")) {
					timeMuster = "%Y-%m-%d";
				} else if (dateStr.equalsIgnoreCase("datatime")) {
					timeMuster = "%Y-%m-%d %H:%i:%s";
				}  else if (dateStr.equalsIgnoreCase("yearmonth")) {
					timeMuster = "%Y-%m";
				} else {
					timeMuster = "%Y-%m-%d";
				}
			} else {
				timeMuster = "%Y-%m-%d";
			}
			
			if(fieldStr==null || fieldStr.length()==0){
				fieldStr="sysdate";
			}

			sqlField = "DATE_FORMAT" + "(" + fieldStr + ",'" + timeMuster + "')";

		return sqlField;
	}

	/**
	 * Ｓｔｒｉｎｇ转化日期格式
	 */
	public static java.sql.Date getDateType1(String str) {
		Date nowDate = new Date();
		java.sql.Date dd2 = null;
		SimpleDateFormat temp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			if (str != "" && str.length() > 0) {
				nowDate = temp.parse(str);
				dd2 = new java.sql.Date(nowDate.getTime());
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dd2;
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
	 *	获得系统当前时间 
	 * 	@return	
	 */
	public static String getCurTime() {
	String sysTime = "";
		Date now = new Date();
		DateFormat mediumDateFormat = DateFormat.getDateTimeInstance(
				DateFormat.MEDIUM, DateFormat.MEDIUM);
		sysTime = mediumDateFormat.format(now);
		return sysTime;
	}
	
	
	/** 返回系统的当前时间**/
	public static String getCurrentTime() {
		Date day = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(day);

	}
	/** 返回系统的当前时间**/
	public static String getCurrentTime2() {
		Date day = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		return format.format(day);

	}

	/**根据年月份判断当前月有多少天**/
	public static int getDayMonth(int year, int mouth) {
		int num = 0;
		switch (mouth) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			num = 31;
			break;
		case 4:
		case 6:
		case 9:
		case 11:
			num = 30;
			break;
		case 2:
			num = isLeapYear(year) ? 29 : 28;
			break;
		default:
			System.out.println("非法月份");
			break;
		}
		return num;
	}

	//定义方法isLeapYear()方法判断指定的年份是否为闰年
	public static boolean isLeapYear(int year) {
		if ((year % 4 == 0) && (year % 100 != 0) || (year % 400 == 0))
			return true;
		else
			return false;
	}

	
	public  static String DateToStr() {
		Date now = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(now);
	}
	
	/**
	 * 拼接like语句
	 * @param headHql
	 * @param columns
	 * @return
	 */
	public static String contactLikeHql(String headHql,String[][] columns){
		for(int i=0;i<columns.length;i++){
			if(columns[i][1].equals(""))
				continue;
			headHql += " and " +  (columns[i][0]) + " like '%"+columns[i][1]+"%'"; 
		}
		return headHql;
	}

	/**
	 * 测试
	 * @param args
	 */
	public static void main(String[] args) {
		String[][] likeValues = new String[4][2];
		likeValues[0][0] = "enrol";
		likeValues[0][1] = "enrol";
		likeValues[1][0] = "name";
		likeValues[1][1] ="name";
		likeValues[2][0] = "member";
		likeValues[2][1] = "member";
		likeValues[3][0] = "address";
		likeValues[3][1] = "address";
		System.out.println(contactLikeHql("from MwpmBussEntinfo",likeValues));
	}

}
