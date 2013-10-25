package com.hyrt.mwpm.webservice.service;

public interface IEntinfoService {
	
	//relative path的定义
	public  final static String RELATIVEPATH = "http://10.80.2.10:8090/";
	/**
	 * 测试功能
	 * @param 
	 */
	public String testFunction();

	/**
	 * 待处理公文信息列表
	 */
	public  final static String oa_dclgw_list ="OA_DCLGW_LIST";
	/**
	 * 待处理公文详细信息
	 */
	public  final static String oa_dclgw_info ="OA_DCLGW_INFO";
	
	/**
	 * 查询待处理公文列表
	 * @param 
	 * @param 
	 * @return
	 */
	public String queryDclgwLists(String xml);
	/***
	 * 查询待处理公文详细信息
	 * @param xml 终端传入的xml
	 * @return
	 */
	public String queryDclgwInfo(String xml);
	
	/**
	 * 下发的公文信息列表
	 */
	public  final static String oa_xfgw_list ="OA_XFGW_LIST";
	/**
	 * 下发的公文详细信息
	 */
	public  final static String oa_xfgw_info ="OA_XFGW_INFO";
	
	/**
	 * 查询下发的公文列表
	 * @param 
	 * @param 
	 * @return
	 */
	public String queryXfgwLists(String xml);
	/***
	 * 查询下发的公文详细信息
	 * @param xml 终端传入的xml
	 * @return
	 */
	public String queryXfgwInfo(String xml);
	
	/**
	 * 收到的公文信息列表
	 */
	public  final static String oa_sdgw_list ="OA_SDGW_LIST";
	/**
	 * 收到的公文详细信息
	 */
	public  final static String oa_sdgw_info ="OA_SDGW_INFO";
	
	/**
	 * 查询收到的公文列表
	 * @param 
	 * @param 
	 * @return
	 */
	public String querySdgwLists(String xml);
	/***
	 * 查询收到的公文详细信息
	 * @param xml 终端传入的xml
	 * @return
	 */
	public String querySdgwInfo(String xml);
	
	/**
	 * 收到的邮件信息列表
	 */
	public  final static String oa_email_list ="OA_EMIAL_LIST";
	/**
	 * 收到的邮件详细信息
	 */
	public  final static String oa_email_info ="OA_EMAIL_INFO";
	
	/**
	 * 查询收到的邮件列表
	 * @param 
	 * @param 
	 * @return
	 */
	public String queryEmailLists(String xml);
	/***
	 * 查询收到的邮件详细信息
	 * @param xml 终端传入的xml
	 * @return
	 */
	public String queryEmailInfo(String xml);
	
	/***
	 * 公文处理
	 * @param xml 终端传入的xml
	 * @return
	 */
	public String saveXfgwInfo(String xml);
	
	
	
	
	
	
	
	
	

}
