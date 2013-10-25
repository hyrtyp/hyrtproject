package com.hyrt.mwpm.webservice.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.CharBuffer;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.exception.GenericJDBCException;
import org.hibernate.hql.ast.QuerySyntaxException;
import org.hibernate.lob.SerializableClob;
import org.springframework.orm.hibernate3.HibernateQueryException;

import com.hyrt.mwpm.util.GridStatic;
import com.hyrt.mwpm.util.NekoHtmlUtil;
import com.hyrt.mwpm.util.PageBean;
import com.hyrt.mwpm.util.ReadOperationXML;
import com.hyrt.mwpm.vo.OaRelease;
import com.hyrt.mwpm.vo.OaReleaseReply;
import com.hyrt.mwpm.webservice.dao.IEntinfoDao;

public class EntinfoSericeImpl implements IEntinfoService{

    
	private static Logger log = Logger.getLogger(EntinfoSericeImpl.class);
	private IEntinfoDao ientinfoDao;
	
	public IEntinfoDao getIentinfoDao() {
		return ientinfoDao;
	}

	public void setIentinfoDao(IEntinfoDao ientinfoDao) {
		this.ientinfoDao = ientinfoDao;
	}

	public String testFunction() {
		return "success";
	}

	/**
	 * �����ն˴����xml,��ѯ��ϸ��Ϣ
	 * @param xml �ն˴���Ĳ���
	 * @param queryKey queryKey ��ѯkey,�൱��WEB-INF/hqldict�ļ���<query code="key">
	 * @param sortNodes �Բ�ѯ��map����sortNodes����
	 * @param mapNodes �Է��ص�map���ݵ����⴦��
	 * @param flag �Ƿ�ʹ��native sql��trueΪʹ�ã�false ��ʹ��,��ʹ��hql
	 * @return
	 */
	@SuppressWarnings( { "unused", "unchecked" })
	protected String getQueryResultInfoXMl(String xml, String queryKey,
			String[] sortNodes, String[] mapNodes,boolean flag ) {
				Map array = new HashMap();
				String errorMessage = "";
				String returnMessage = "";
				try {
					// ���������XML��ʽ���ļ�
					errorMessage = ReadOperationXML.parseInputDataXml(xml, array);
					
					if (errorMessage.length() == 0) {// ��û���쳣ʱ
						Map parseHqlXMLHashMap = new HashMap();
						// ����HQL��XML�ļ�
						errorMessage = ReadOperationXML.parseHqlXML(parseHqlXMLHashMap);
						// ���ڴ���ȡ�ý���HQL��XML�ļ���MAP
						// parseHqlXMLHashMap=GridStatic.HQL_DICT_DATA;
			
						if (errorMessage.length() == 0 && parseHqlXMLHashMap != null) {
							errorMessage = parseHqlXMLHashMap.get("error") == null ? ""
									: parseHqlXMLHashMap.get("error").toString();
						}
						// ��������HQL.xml�ļ��д���ʱ
						if (errorMessage.length() > 0) {
							errorMessage = ReadOperationXML.addErrorXml(errorMessage);
							return errorMessage;
						} else {
							// ��ѯ�ı�ţ�������MAP����������õ�hqldict.xml�������ݵ�MAP
							// ����HQL�Ĳ�ѯ��ţ��ͽ��������XML��HQL���ļ����������HQL��䡣
							String hql = ReadOperationXML.makeUpHql(queryKey, array,
									parseHqlXMLHashMap, "0");
			
							if (hql.length() > 0) {
								
								List allData=new ArrayList();
								if(!flag){
									 allData = ientinfoDao.getInfo(hql);
								}else{
									 allData = ientinfoDao.getInfo(hql);
								}
								//����list��ֵ
								System.out.println("sql��ʼ------------------------"+hql);
								
								if (mapNodes!=null) {
									allData = this.sortNodesbyAllDatas(allData,
											sortNodes, mapNodes);
								} else {
									allData = this.sortNodesbyAllDatas(allData,
											sortNodes);
								}
								if (allData != null && allData.size() > 0) {// ����ѯ������ʱ����ʼ������ҳ����ƴ�շ��ظ��ͻ��˵�XML��ʽ���ļ�
									// ���ɷ��ص�XML��ʽ���ļ�
			
									returnMessage = ReadOperationXML
											.OperationPartDataXml(allData,
													parseHqlXMLHashMap, queryKey);
								} else {// ��û������ʱ���򷵻�û���ҵ���ѯ����ı���
									errorMessage = ReadOperationXML
											.addErrorXml(GridStatic.V_SUCCESS_NO_QUERYDATA_B);
									return errorMessage;
								}
							} else {
								// �ṩ�������������
								errorMessage = ReadOperationXML
										.addErrorXml(GridStatic.E_DB_HQL_CODE_ERROR_B);
								return errorMessage;
							}
						}
					} else {
						// �����XML��ʽ���ļ�������
						errorMessage = ReadOperationXML
								.addErrorXml(GridStatic.E_RETURN_FAILURE_OUTPUT_XML_B);
						return errorMessage;
					}
				} catch (HibernateQueryException e) {
					// ��ѯ����
					errorMessage = ReadOperationXML
							.addErrorXml(GridStatic.E_DB_QUERY_ERROR_B);
					e.printStackTrace();
					return errorMessage;
				} catch (QuerySyntaxException e) {
					// ��ѯ����
					errorMessage = ReadOperationXML
							.addErrorXml(GridStatic.E_DB_QUERY_ERROR_B);
					e.printStackTrace();
					return errorMessage;
			
				} catch (GenericJDBCException e) {
					// ���Ӵ���
					errorMessage = ReadOperationXML
							.addErrorXml(GridStatic.E_DB_CONNECTION_ERROR_B);
					e.printStackTrace();
					return errorMessage;
				} catch (Exception e) {// ���ﻹҪ��ִ��HQL�����쳣�����ݿ������쳣
					errorMessage = ReadOperationXML
							.addErrorXml(GridStatic.E_RETURN_ERROR_B);
					e.printStackTrace();
					return errorMessage;
				}
				return returnMessage;
			
	}
	
	public String getResultXml(String xml, String searchHql) {

		Map array = new HashMap();
		String errorMessage = "";
		String returnMessage = "";
		try {
			// ���������XML��ʽ���ļ�
			errorMessage = ReadOperationXML.parseInputDataXml(xml, array);
			log.debug("��ҵ��Ҫ��ѯ--�����xml---" + xml);
			String pageNo = "";
			String querytype = "";// ��ѯ���ͣ���ȷ/ģ��
			if (errorMessage.length() == 0) {// ��û���쳣ʱ
				// ȡ�õ�ǰҳ
				pageNo = array.get("pageno") == null ? "1" : array
						.get("pageno").toString();
				querytype = array.get("querytype") == null ? "1" : array.get(
						"querytype").toString();
				// �ն˴���һҳ��ʾ�����У�
				String pageSize = array.get("pagesize") == null ? "" : array
						.get("pagesize").toString();
				Map parseHqlXMLHashMap = new HashMap();
				// ����HQL��XML�ļ�
				errorMessage = ReadOperationXML.parseHqlXML(parseHqlXMLHashMap);
				// ���ڴ���ȡ�ý���HQL��XML�ļ���MAP
				// parseHqlXMLHashMap=GridStatic.HQL_DICT_DATA;

				if (errorMessage.length() == 0 && parseHqlXMLHashMap != null) {
					errorMessage = parseHqlXMLHashMap.get("error") == null ? ""
							: parseHqlXMLHashMap.get("error").toString();
				}
				// ��������HQL.xml�ļ��д���ʱ
				if (errorMessage.length() > 0) {
					errorMessage = ReadOperationXML.addErrorXml(errorMessage);
					return errorMessage;
				} else {
					// ��ѯ�ı�ţ�������MAP����������õ�hqldict.xml�������ݵ�MAP
					// ����HQL�Ĳ�ѯ��ţ��ͽ��������XML��HQL���ļ����������HQL��䡣
					String hql = ReadOperationXML.makeUpHql(searchHql, array,
							parseHqlXMLHashMap, querytype);
					// һҳ��ʾ�����У�����ն˴���Ϊ�յĻ�������ҳ����Ϊ׼
					if (pageSize.length() == 0) {
						pageSize = parseHqlXMLHashMap.get("pagesize") == null ? "10"
								: parseHqlXMLHashMap.get("pagesize").toString();
					}
					if (hql.length() > 0) {
						// ȡ���ܼ�¼����HQL���
						String hqlcount = ReadOperationXML.getCountHql(
								parseHqlXMLHashMap, searchHql);
						PageBean pageBean = new PageBean();
						pageBean.setPageNo(new Integer(pageNo));// ��ǰ����ҳ
						pageBean.setPageSize(new Integer(pageSize));// һҳ��ʾ������

						List allData = ientinfoDao.getResultData(
								hql, pageBean, hqlcount);
						if (allData != null && allData.size() > 0) {// ����ѯ������ʱ����ʼ������ҳ����ƴ�շ��ظ��ͻ��˵�XML��ʽ���ļ�
							// ������ҳ��
							int pageSum = ReadOperationXML.getSumPage(pageBean);
							// ���ɷ��ص�XML��ʽ���ļ�
							returnMessage = ReadOperationXML
									.OperationQueryListDataXml(allData, pageNo,
											pageSum);
						} else {// ��û������ʱ���򷵻�û���ҵ���ѯ����ı���
							errorMessage = ReadOperationXML
									.addErrorXml(GridStatic.V_SUCCESS_NO_QUERYDATA_B);
							return errorMessage;
						}
					} else {
						// �ṩ�������������
						errorMessage = ReadOperationXML
								.addErrorXml(GridStatic.E_DB_HQL_CODE_ERROR_B);
						return errorMessage;
					}
				}
			} else {
				// �����XML��ʽ���ļ�������
				errorMessage = ReadOperationXML
						.addErrorXml(GridStatic.E_RETURN_FAILURE_OUTPUT_XML_B);
				return errorMessage;
			}
		} catch (HibernateQueryException e) {
			// ��ѯ����
			log.error("QuerySyntaxException", e);
			errorMessage = ReadOperationXML
					.addErrorXml(GridStatic.E_DB_QUERY_ERROR_B);
			e.printStackTrace();
			return errorMessage;
		} catch (QuerySyntaxException e) {
			// ��ѯ����
			log.error("QuerySyntaxException", e);
			errorMessage = ReadOperationXML
					.addErrorXml(GridStatic.E_DB_QUERY_ERROR_B);
			e.printStackTrace();
			return errorMessage;

		} catch (GenericJDBCException e) {
			// ���Ӵ���
			log.error("GenericJDBCException", e);
			errorMessage = ReadOperationXML
					.addErrorXml(GridStatic.E_DB_CONNECTION_ERROR_B);
			e.printStackTrace();
			return errorMessage;
		} catch (Exception e) {// ���ﻹҪ��ִ��HQL�����쳣�����ݿ������쳣
			log.error("GenericJDBCException", e);
			errorMessage = ReadOperationXML
					.addErrorXml(GridStatic.E_RETURN_ERROR_B);
			e.printStackTrace();
			return errorMessage;
		}
		log.debug("������xml ---------------" + returnMessage);
		return returnMessage;
	}
	
	@SuppressWarnings("unchecked")
	private List sortNodesbyAllDatas(List allData, String[] sortNodes) {
		List newList = new ArrayList();
		for (int v = 0; v < allData.size(); v++) {
			Map linkMap = new LinkedHashMap();
			Map map = (HashMap) allData.get(v);
			
			
			for (int o = 0; o < sortNodes.length; o++) {
				String fidl = sortNodes[o];
				if(fidl.equals("emailpath")){
					//D:\www\AicOA\OAFile\\Mail\gq_guox\201012\20101230105304093
					//http://10.80.2.10/OAFile/Mail/gq_guox/201012/20101230105304093
					//���·���;���·��ת��
					//String path = String.valueOf(map.get(fidl));
					//֮����Ҫǿ��ת����SerializableClob �Ǹ����ݿ�������йأ�c3po���Ӳ���ת����jtds��ʽ����sqlserverҪת��
					//���ݱ��е�path������textҪת����string
					SerializableClob s = (SerializableClob)map.get(fidl);
					System.out.println("======semail==========================="+s);
					
					String path = "";
					if(s!=null){
					try {
						path = s.getSubString(1, (int)s.length());
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					}
					if(!("").equals(path)){
						System.out.println("=========path========================"+path);
						String temp = path.substring(path.indexOf("\\"));
						temp = temp.replace('\\','/');
						temp = temp.substring(temp.indexOf("Mail"));
						temp = RELATIVEPATH+temp;
						linkMap.put(fidl, temp);
					}
				}else if(fidl.equals("oacommonpath")){
					//d:\OAFiles\shj\20110321\20110321144950375
					//http://10.80.2.10/OAFiles/shj/20110321/20110321144950375
					//���·���;���·��ת��
					//String path = String.valueOf(map.get(fidl));
					SerializableClob s = (SerializableClob)map.get(fidl);
					System.out.println("======soa==========================="+s);
					String path = "";
					if(s!=null){
					try {
						path = s.getSubString(1, (int)s.length());
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					}
					//String path = bufferPath.toString();
					if(!("").equals(path)){
						System.out.println("================================="+path);
						String temp = path.substring(path.indexOf("\\"));
						temp = temp.replace('\\','/');
						temp = temp.substring(temp.indexOf("OAFiles"));
						temp = RELATIVEPATH+temp;
						linkMap.put(fidl, temp);
					}
				}else if(fidl.equals("html")){					
					SerializableClob s = (SerializableClob)map.get(fidl);
					String path = "";
					if(s!=null){
					try {
						path = s.getSubString(1, (int)s.length());
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					}
					if(!("").equals(path)){
						try {
							NekoHtmlUtil.extractTextFromHTML(path.replaceAll("&nbsp", ""), linkMap);
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}else{
					linkMap.put(fidl, map.get(fidl));
				}
				
			}
			newList.add(linkMap);
		}
		return newList;
	}
	
	@SuppressWarnings( { "unchecked", "unused" })
	private List sortNodesbyAllDatas(List allData, String[] sortNodes,
			String[] mapNodes) {

		List list = new ArrayList();
		for (int i = 0; i < allData.size(); i++) {
			Map map = (HashMap) allData.get(i);
			for (int m = 0; m < mapNodes.length; m++) {
				map.put(mapNodes[m], "FENGEXIAN");
			}
			list.add(map);
		}
		List newList = new ArrayList();
		for (int v = 0; v < list.size(); v++) {
			Map linkMap = new LinkedHashMap();
			Map map = (HashMap) list.get(v);
			for (int o = 0; o < sortNodes.length; o++) {
				String fidl = sortNodes[o];
				linkMap.put(fidl, map.get(fidl));
			}
			newList.add(linkMap);
		}
		return newList;
	}
	
	@Override
	public String saveXfgwInfo(String xml) {
		Map array = new HashMap();
		// ���������XML��ʽ���ļ�
		log.debug("������Ϣ ����  xml --------------------" + xml);
		String errorMessage = ReadOperationXML.parseInputDataXml(xml, array);
		if (errorMessage.length() == 0) {
			OaReleaseReply orl = new OaReleaseReply();
			setXfgwValues(orl,array);
			//���洦���Ļظ���
			ientinfoDao.saveObject(orl);
			errorMessage = ReadOperationXML
					.addErrorXml(GridStatic.E_RETURN_SUCCESS_A);
			return errorMessage;
		} else {
			// �����XML��ʽ���ļ�������
			errorMessage = ReadOperationXML
					.addErrorXml(GridStatic.E_RETURN_FAILURE_OUTPUT_XML_B);
			return errorMessage;
		}
	}
	
	// //���洦���Ļظ�������Ϣ
	private void setXfgwValues(OaReleaseReply orl,Map array) {
		orl.setReply(array.get("reply") == null ? "" : array.get("reply").toString());
		orl.setReleaseId(array.get("releaseid") == null ? "" : array.get("releaseid").toString());
		orl.setAccount(array.get("account") == null ? "" : array.get("account").toString());
		orl.setUserName(array.get("username") == null ? "" : array.get("username").toString());
		orl.setTaskId(array.get("taskid") == null ? "" : array.get("taskid").toString());
		orl.setWorkId(array.get("workid") == null ? "" : array.get("workid").toString());
		orl.setGroup(array.get("group") == null ? "" : array.get("group").toString());
		orl.setDate(new Date());
	}
	
	

	@Override
	public String queryDclgwInfo(String xml) {
		String sortNodes[]={"name","valuenumber","valueorgans","valuedate","valuespeed","nodename","date","fjname","oacommonpath"};
		return this.getQueryResultInfoXMl(xml, oa_dclgw_info, sortNodes, null, false);
	}

	@Override
	public String queryDclgwLists(String xml) {
		// TODO Auto-generated method stub
		return this.getResultXml(xml, oa_dclgw_list);
	}

	@Override
	public String queryEmailInfo(String xml) {
		String sortNodes[]={"id","fileid","title","owneraccount","userlist","mydate","message","filelist","emailpath"};
		return this.getQueryResultInfoXMl(xml, oa_email_info, sortNodes, null, false);
	}

	@Override
	public String queryEmailLists(String xml) {
		// TODO Auto-generated method stub
		return this.getResultXml(xml, oa_email_list);
	}

	@Override
	public String querySdgwInfo(String xml) {
		String sortNodes[]={"valueTitle","valueNumber","valueOrgans","valueDate","date","userlist","fjname","oacommonpath"};
		return this.getQueryResultInfoXMl(xml, oa_sdgw_info, sortNodes, null, false);
	}

	@Override
	public String querySdgwLists(String xml) {
		// TODO Auto-generated method stub
		return this.getResultXml(xml, oa_sdgw_list);
	}

	@Override
	public String queryXfgwInfo(String xml) {
		String sortNodes[]={"id","workid","taskid","owner","owneraccount","html"};
		return this.getQueryResultInfoXMl(xml, oa_xfgw_info, sortNodes, null, false);
	}

	@Override
	public String queryXfgwLists(String xml) {
		// TODO Auto-generated method stub
		return this.getResultXml(xml, oa_xfgw_list);
	}

}
