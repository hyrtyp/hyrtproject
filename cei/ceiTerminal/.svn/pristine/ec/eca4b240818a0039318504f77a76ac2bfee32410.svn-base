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
	 * 接受终端传入的xml,查询详细信息
	 * @param xml 终端传入的参数
	 * @param queryKey queryKey 查询key,相当于WEB-INF/hqldict文件中<query code="key">
	 * @param sortNodes 对查询的map按照sortNodes排序
	 * @param mapNodes 对返回的map数据的特殊处理
	 * @param flag 是否使用native sql，true为使用，false 不使用,而使用hql
	 * @return
	 */
	@SuppressWarnings( { "unused", "unchecked" })
	protected String getQueryResultInfoXMl(String xml, String queryKey,
			String[] sortNodes, String[] mapNodes,boolean flag ) {
				Map array = new HashMap();
				String errorMessage = "";
				String returnMessage = "";
				try {
					// 解析传入的XML格式的文件
					errorMessage = ReadOperationXML.parseInputDataXml(xml, array);
					
					if (errorMessage.length() == 0) {// 当没有异常时
						Map parseHqlXMLHashMap = new HashMap();
						// 解析HQL的XML文件
						errorMessage = ReadOperationXML.parseHqlXML(parseHqlXMLHashMap);
						// 从内存中取得解析HQL的XML文件的MAP
						// parseHqlXMLHashMap=GridStatic.HQL_DICT_DATA;
			
						if (errorMessage.length() == 0 && parseHqlXMLHashMap != null) {
							errorMessage = parseHqlXMLHashMap.get("error") == null ? ""
									: parseHqlXMLHashMap.get("error").toString();
						}
						// 当解析的HQL.xml文件有错误时
						if (errorMessage.length() > 0) {
							errorMessage = ReadOperationXML.addErrorXml(errorMessage);
							return errorMessage;
						} else {
							// 查询的编号，条件的MAP，传入解析好的hqldict.xml整个数据的MAP
							// 根据HQL的查询编号，和解析传入的XML和HQL的文件，进行组合HQL语句。
							String hql = ReadOperationXML.makeUpHql(queryKey, array,
									parseHqlXMLHashMap, "0");
			
							if (hql.length() > 0) {
								
								List allData=new ArrayList();
								if(!flag){
									 allData = ientinfoDao.getInfo(hql);
								}else{
									 allData = ientinfoDao.getInfo(hql);
								}
								//测试list的值
								System.out.println("sql开始------------------------"+hql);
								
								if (mapNodes!=null) {
									allData = this.sortNodesbyAllDatas(allData,
											sortNodes, mapNodes);
								} else {
									allData = this.sortNodesbyAllDatas(allData,
											sortNodes);
								}
								if (allData != null && allData.size() > 0) {// 当查询有数据时，则开始计算总页数和拼凑返回给客户端的XML格式的文件
									// 生成返回的XML格式的文件
			
									returnMessage = ReadOperationXML
											.OperationPartDataXml(allData,
													parseHqlXMLHashMap, queryKey);
								} else {// 当没有数据时，则返回没有找到查询结果的编码
									errorMessage = ReadOperationXML
											.addErrorXml(GridStatic.V_SUCCESS_NO_QUERYDATA_B);
									return errorMessage;
								}
							} else {
								// 提供的主编号有问题
								errorMessage = ReadOperationXML
										.addErrorXml(GridStatic.E_DB_HQL_CODE_ERROR_B);
								return errorMessage;
							}
						}
					} else {
						// 传入的XML格式的文件有问题
						errorMessage = ReadOperationXML
								.addErrorXml(GridStatic.E_RETURN_FAILURE_OUTPUT_XML_B);
						return errorMessage;
					}
				} catch (HibernateQueryException e) {
					// 查询错误
					errorMessage = ReadOperationXML
							.addErrorXml(GridStatic.E_DB_QUERY_ERROR_B);
					e.printStackTrace();
					return errorMessage;
				} catch (QuerySyntaxException e) {
					// 查询错误
					errorMessage = ReadOperationXML
							.addErrorXml(GridStatic.E_DB_QUERY_ERROR_B);
					e.printStackTrace();
					return errorMessage;
			
				} catch (GenericJDBCException e) {
					// 连接错误
					errorMessage = ReadOperationXML
							.addErrorXml(GridStatic.E_DB_CONNECTION_ERROR_B);
					e.printStackTrace();
					return errorMessage;
				} catch (Exception e) {// 这里还要捕执行HQL语句的异常和数据库连接异常
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
			// 解析传入的XML格式的文件
			errorMessage = ReadOperationXML.parseInputDataXml(xml, array);
			log.debug("企业概要查询--传入的xml---" + xml);
			String pageNo = "";
			String querytype = "";// 查询类型：精确/模糊
			if (errorMessage.length() == 0) {// 当没有异常时
				// 取得当前页
				pageNo = array.get("pageno") == null ? "1" : array
						.get("pageno").toString();
				querytype = array.get("querytype") == null ? "1" : array.get(
						"querytype").toString();
				// 终端传入一页显示多少行，
				String pageSize = array.get("pagesize") == null ? "" : array
						.get("pagesize").toString();
				Map parseHqlXMLHashMap = new HashMap();
				// 解析HQL的XML文件
				errorMessage = ReadOperationXML.parseHqlXML(parseHqlXMLHashMap);
				// 从内存中取得解析HQL的XML文件的MAP
				// parseHqlXMLHashMap=GridStatic.HQL_DICT_DATA;

				if (errorMessage.length() == 0 && parseHqlXMLHashMap != null) {
					errorMessage = parseHqlXMLHashMap.get("error") == null ? ""
							: parseHqlXMLHashMap.get("error").toString();
				}
				// 当解析的HQL.xml文件有错误时
				if (errorMessage.length() > 0) {
					errorMessage = ReadOperationXML.addErrorXml(errorMessage);
					return errorMessage;
				} else {
					// 查询的编号，条件的MAP，传入解析好的hqldict.xml整个数据的MAP
					// 根据HQL的查询编号，和解析传入的XML和HQL的文件，进行组合HQL语句。
					String hql = ReadOperationXML.makeUpHql(searchHql, array,
							parseHqlXMLHashMap, querytype);
					// 一页显示多少行，如果终端传入为空的话，按后页配置为准
					if (pageSize.length() == 0) {
						pageSize = parseHqlXMLHashMap.get("pagesize") == null ? "10"
								: parseHqlXMLHashMap.get("pagesize").toString();
					}
					if (hql.length() > 0) {
						// 取得总记录数的HQL语句
						String hqlcount = ReadOperationXML.getCountHql(
								parseHqlXMLHashMap, searchHql);
						PageBean pageBean = new PageBean();
						pageBean.setPageNo(new Integer(pageNo));// 当前多少页
						pageBean.setPageSize(new Integer(pageSize));// 一页显示多少行

						List allData = ientinfoDao.getResultData(
								hql, pageBean, hqlcount);
						if (allData != null && allData.size() > 0) {// 当查询有数据时，则开始计算总页数和拼凑返回给客户端的XML格式的文件
							// 计算总页数
							int pageSum = ReadOperationXML.getSumPage(pageBean);
							// 生成返回的XML格式的文件
							returnMessage = ReadOperationXML
									.OperationQueryListDataXml(allData, pageNo,
											pageSum);
						} else {// 当没有数据时，则返回没有找到查询结果的编码
							errorMessage = ReadOperationXML
									.addErrorXml(GridStatic.V_SUCCESS_NO_QUERYDATA_B);
							return errorMessage;
						}
					} else {
						// 提供的主编号有问题
						errorMessage = ReadOperationXML
								.addErrorXml(GridStatic.E_DB_HQL_CODE_ERROR_B);
						return errorMessage;
					}
				}
			} else {
				// 传入的XML格式的文件有问题
				errorMessage = ReadOperationXML
						.addErrorXml(GridStatic.E_RETURN_FAILURE_OUTPUT_XML_B);
				return errorMessage;
			}
		} catch (HibernateQueryException e) {
			// 查询错误
			log.error("QuerySyntaxException", e);
			errorMessage = ReadOperationXML
					.addErrorXml(GridStatic.E_DB_QUERY_ERROR_B);
			e.printStackTrace();
			return errorMessage;
		} catch (QuerySyntaxException e) {
			// 查询错误
			log.error("QuerySyntaxException", e);
			errorMessage = ReadOperationXML
					.addErrorXml(GridStatic.E_DB_QUERY_ERROR_B);
			e.printStackTrace();
			return errorMessage;

		} catch (GenericJDBCException e) {
			// 连接错误
			log.error("GenericJDBCException", e);
			errorMessage = ReadOperationXML
					.addErrorXml(GridStatic.E_DB_CONNECTION_ERROR_B);
			e.printStackTrace();
			return errorMessage;
		} catch (Exception e) {// 这里还要捕执行HQL语句的异常和数据库连接异常
			log.error("GenericJDBCException", e);
			errorMessage = ReadOperationXML
					.addErrorXml(GridStatic.E_RETURN_ERROR_B);
			e.printStackTrace();
			return errorMessage;
		}
		log.debug("处理结果xml ---------------" + returnMessage);
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
					//相对路径和绝对路径转换
					//String path = String.valueOf(map.get(fidl));
					//之所以要强制转换成SerializableClob 是跟数据库的驱动有关，c3po连接不用转换，jtds方式连接sqlserver要转换
					//数据表中的path类型是text要转换成string
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
					//相对路径和绝对路径转换
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
		// 解析传入的XML格式的文件
		log.debug("保存信息 传入  xml --------------------" + xml);
		String errorMessage = ReadOperationXML.parseInputDataXml(xml, array);
		if (errorMessage.length() == 0) {
			OaReleaseReply orl = new OaReleaseReply();
			setXfgwValues(orl,array);
			//保存处理公文回复表
			ientinfoDao.saveObject(orl);
			errorMessage = ReadOperationXML
					.addErrorXml(GridStatic.E_RETURN_SUCCESS_A);
			return errorMessage;
		} else {
			// 传入的XML格式的文件有问题
			errorMessage = ReadOperationXML
					.addErrorXml(GridStatic.E_RETURN_FAILURE_OUTPUT_XML_B);
			return errorMessage;
		}
	}
	
	// //保存处理公文回复对象信息
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
