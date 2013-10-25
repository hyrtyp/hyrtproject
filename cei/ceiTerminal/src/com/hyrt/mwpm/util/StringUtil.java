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

	/**	���ӶԻ�õ��ַ���Ϊnull�Ĵ���,��Nullת����"--"		*/
	public static String StringNoNull(String str) {
		String strString = "-";
		if (("null".equals(str)) || ("".equals(str)) || (str == null)) {
			return strString;
		} else {
			return str;
		}
	}

	/**	���ӶԻ�õ��ַ���Ϊnull�Ĵ���,��Nullת����""		*/
	public static String StringIsNull(String str) {
		String strString = "";
		if ((str == null) || ("null".equals(str)) || ("".equals(str))) {
			return strString;
		} else {
			return str;
		}
	}

	/**
	 *	���ϵͳʱ�� 
	 * 	@return	
	 */
	public static Date getSysTime() {
		Date now = new Date();
		return now;
	}

	/**
	 * �ӣ������ת�����ڸ�ʽ
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
	 * �ӣ������ת�����ڸ�ʽ
	 */
	public static java.sql.Date getDateType1(String str) {
		Date nowDate = new Date();
		java.sql.Date dd2 = null;
		SimpleDateFormat temp = new SimpleDateFormat("yyyy-MM-dd");
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
	 *	���ϵͳʱ�� 
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
	 *	���ϵͳ��ǰʱ�� 
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
	
	
	/** ����ϵͳ�ĵ�ǰʱ��**/
	public static String getCurrentTime() {
		Date day = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(day);

	}
	/** ����ϵͳ�ĵ�ǰʱ��**/
	public static String getCurrentTime2() {
		Date day = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		return format.format(day);

	}

	/**�������·��жϵ�ǰ���ж�����**/
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
			System.out.println("�Ƿ��·�");
			break;
		}
		return num;
	}

	//���巽��isLeapYear()�����ж�ָ��������Ƿ�Ϊ����
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
	 * ����
	 * @param args
	 */
	public static void main(String[] args) {
		//		int day = StringUtil.getDate("20090705");
				System.out.println(getCurrentTime2());
	}

}