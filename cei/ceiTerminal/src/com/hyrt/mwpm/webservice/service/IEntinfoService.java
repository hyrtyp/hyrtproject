package com.hyrt.mwpm.webservice.service;

public interface IEntinfoService {
	
	//relative path�Ķ���
	public  final static String RELATIVEPATH = "http://10.80.2.10:8090/";
	/**
	 * ���Թ���
	 * @param 
	 */
	public String testFunction();

	/**
	 * ����������Ϣ�б�
	 */
	public  final static String oa_dclgw_list ="OA_DCLGW_LIST";
	/**
	 * ����������ϸ��Ϣ
	 */
	public  final static String oa_dclgw_info ="OA_DCLGW_INFO";
	
	/**
	 * ��ѯ���������б�
	 * @param 
	 * @param 
	 * @return
	 */
	public String queryDclgwLists(String xml);
	/***
	 * ��ѯ����������ϸ��Ϣ
	 * @param xml �ն˴����xml
	 * @return
	 */
	public String queryDclgwInfo(String xml);
	
	/**
	 * �·��Ĺ�����Ϣ�б�
	 */
	public  final static String oa_xfgw_list ="OA_XFGW_LIST";
	/**
	 * �·��Ĺ�����ϸ��Ϣ
	 */
	public  final static String oa_xfgw_info ="OA_XFGW_INFO";
	
	/**
	 * ��ѯ�·��Ĺ����б�
	 * @param 
	 * @param 
	 * @return
	 */
	public String queryXfgwLists(String xml);
	/***
	 * ��ѯ�·��Ĺ�����ϸ��Ϣ
	 * @param xml �ն˴����xml
	 * @return
	 */
	public String queryXfgwInfo(String xml);
	
	/**
	 * �յ��Ĺ�����Ϣ�б�
	 */
	public  final static String oa_sdgw_list ="OA_SDGW_LIST";
	/**
	 * �յ��Ĺ�����ϸ��Ϣ
	 */
	public  final static String oa_sdgw_info ="OA_SDGW_INFO";
	
	/**
	 * ��ѯ�յ��Ĺ����б�
	 * @param 
	 * @param 
	 * @return
	 */
	public String querySdgwLists(String xml);
	/***
	 * ��ѯ�յ��Ĺ�����ϸ��Ϣ
	 * @param xml �ն˴����xml
	 * @return
	 */
	public String querySdgwInfo(String xml);
	
	/**
	 * �յ����ʼ���Ϣ�б�
	 */
	public  final static String oa_email_list ="OA_EMIAL_LIST";
	/**
	 * �յ����ʼ���ϸ��Ϣ
	 */
	public  final static String oa_email_info ="OA_EMAIL_INFO";
	
	/**
	 * ��ѯ�յ����ʼ��б�
	 * @param 
	 * @param 
	 * @return
	 */
	public String queryEmailLists(String xml);
	/***
	 * ��ѯ�յ����ʼ���ϸ��Ϣ
	 * @param xml �ն˴����xml
	 * @return
	 */
	public String queryEmailInfo(String xml);
	
	/***
	 * ���Ĵ���
	 * @param xml �ն˴����xml
	 * @return
	 */
	public String saveXfgwInfo(String xml);
	
	
	
	
	
	
	
	
	

}
