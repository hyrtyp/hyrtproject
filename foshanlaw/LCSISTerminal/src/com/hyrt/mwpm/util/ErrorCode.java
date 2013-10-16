package com.hyrt.mwpm.util;

public class ErrorCode {
	// 服务端异常标志位
	public final static String EXPSERVER_FLAG = "2";
	// 客户端异常标志位
	public final static String EXPCLIENT_FLAG = "3";
	// 没有数据标志位
	public final static String NODATA_FLAG = "4";
	// 数据加载完毕标志位
	public final static String ALDATA_FLAG = "5";
	//用户名密码错误标志位
	public final static String PASSERROR_FLAG = "6";
	//用户名密码不能为空
	public final static String PASSNULL_FLAG = "7";
	//无权做此操作
	public final static String NOROLE_FLAG = "10";
	//设备号已存在操作
	public final static String DEVICEEXIST_FLAG = "12";
}
