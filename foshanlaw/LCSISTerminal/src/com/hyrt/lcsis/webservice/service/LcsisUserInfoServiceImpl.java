package com.hyrt.lcsis.webservice.service;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.log4j.Logger;
import org.hibernate.exception.GenericJDBCException;
import org.hibernate.hql.ast.QuerySyntaxException;
import org.hibernate.lob.SerializableClob;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.orm.hibernate3.HibernateQueryException;

import com.hyrt.lcsis.webservice.dao.LcsisDictDao;
import com.hyrt.mwpm.util.CommonParm;
import com.hyrt.mwpm.util.EmailUtil;
import com.hyrt.mwpm.util.GridStatic;
import com.hyrt.mwpm.util.JsonDateValueProcessor;
import com.hyrt.mwpm.util.NekoHtmlUtil;
import com.hyrt.mwpm.util.PageBean;
import com.hyrt.mwpm.util.ReadOperationXML;
import com.hyrt.mwpm.util.ReadXML;
import com.hyrt.mwpm.util.ErrorCode;
import com.hyrt.mwpm.util.StringUtil;
import com.hyrt.mwpm.vo.*;

public class LcsisUserInfoServiceImpl implements LcsisUserInfoService {

	private static Logger log = Logger
			.getLogger(LcsisUserInfoServiceImpl.class);

	// relative path�Ķ���
	public final static String RELATIVEPATH = "http://localhost:8080/";

	private LcsisDictDao ceiDictDao;

	private JavaMailSender sender;

	public JavaMailSender getSender() {
		return sender;
	}

	public void setSender(JavaMailSender sender) {
		this.sender = sender;
	}

	public LcsisDictDao getCeiDictDao() {
		return ceiDictDao;
	}

	public void setCeiDictDao(LcsisDictDao ceiDictDao) {
		this.ceiDictDao = ceiDictDao;
	}

	/**
	 * �����ն˴����xml,��ѯ��ϸ��Ϣ
	 * 
	 * @param xml
	 *            �ն˴���Ĳ���
	 * @param queryKey
	 *            queryKey ��ѯkey,�൱��WEB-INF/hqldict�ļ���<query code="key">
	 * @param sortNodes
	 *            �Բ�ѯ��map����sortNodes����
	 * @param mapNodes
	 *            �Է��ص�map���ݵ����⴦��
	 * @param flag
	 *            �Ƿ�ʹ��native sql��trueΪʹ�ã�false ��ʹ��,��ʹ��hql
	 * @return
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	protected String getQueryResultInfoXMl(String xml, String queryKey,
			String[] sortNodes, String[] mapNodes, boolean flag) {
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

						List allData = new ArrayList();
						if (!flag) {
							allData = ceiDictDao.getInfo(hql);
						} else {
							allData = ceiDictDao.getInfo(hql);
						}
						// ����list��ֵ
						System.out.println("allData------------------------"
								+ allData);

						if (mapNodes != null) {
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

						List allData = new ArrayList();
						// allData = ientinfoDao.getResultData(
						// hql, pageBean, hqlcount);
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
				if (fidl.equals("emailpath")) {
					// D:\www\AicOA\OAFile\\Mail\gq_guox\201012\20101230105304093
					// http://10.80.2.10/OAFile/Mail/gq_guox/201012/20101230105304093
					// ���·���;���·��ת��
					// String path = String.valueOf(map.get(fidl));
					// ֮����Ҫǿ��ת����SerializableClob
					// �Ǹ����ݿ�������йأ�c3po���Ӳ���ת����jtds��ʽ����sqlserverҪת��
					// ���ݱ��е�path������textҪת����string
					SerializableClob s = (SerializableClob) map.get(fidl);
					System.out
							.println("======semail==========================="
									+ s);

					String path = "";
					if (s != null) {
						try {
							path = s.getSubString(1, (int) s.length());
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
					if (!("").equals(path)) {
						System.out
								.println("=========path========================"
										+ path);
						String temp = path.substring(path.indexOf("\\"));
						temp = temp.replace('\\', '/');
						temp = temp.substring(temp.indexOf("Mail"));
						temp = RELATIVEPATH + temp;
						linkMap.put(fidl, temp);
					}
				} else if (fidl.equals("oacommonpath")) {
					// d:\OAFiles\shj\20110321\20110321144950375
					// http://10.80.2.10/OAFiles/shj/20110321/20110321144950375
					// ���·���;���·��ת��
					// String path = String.valueOf(map.get(fidl));
					SerializableClob s = (SerializableClob) map.get(fidl);
					System.out.println("======soa==========================="
							+ s);
					String path = "";
					if (s != null) {
						try {
							path = s.getSubString(1, (int) s.length());
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
					// String path = bufferPath.toString();
					if (!("").equals(path)) {
						System.out.println("================================="
								+ path);
						String temp = path.substring(path.indexOf("\\"));
						temp = temp.replace('\\', '/');
						temp = temp.substring(temp.indexOf("OAFiles"));
						temp = RELATIVEPATH + temp;
						linkMap.put(fidl, temp);
					}
				} else if (fidl.equals("html")) {
					SerializableClob s = (SerializableClob) map.get(fidl);
					String path = "";
					if (s != null) {
						try {
							path = s.getSubString(1, (int) s.length());
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
					if (!("").equals(path)) {
						try {
							NekoHtmlUtil.extractTextFromHTML(
									path.replaceAll("&nbsp", ""), linkMap);
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
					}
				} else {
					linkMap.put(fidl, map.get(fidl));
				}

			}
			newList.add(linkMap);
		}
		return newList;
	}

	@SuppressWarnings({ "unchecked", "unused" })
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

	/**
	 * �޸ľ�γ��
	 * 
	 * @author tanJie
	 */
	@Override
	public String updateUser(String xml) {
		String error = "";
		String xmlMessage = "<?xml version='1.0' encoding='utf-8'?>" + "<ROOT>";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String id = xmlStrMap.get("userid") == null ? "" : xmlStrMap
					.get("userid");
			String longitude = xmlStrMap.get("longitude") == null ? ""
					: xmlStrMap.get("longitude");
			String latitude = xmlStrMap.get("latitude") == null ? ""
					: xmlStrMap.get("latitude");
			if (longitude.equals("") || latitude.equals(""))
				return xmlMessage;
			String sql = "from MwpmSysUserinfo where userid='" + id + "'";
			List list = ceiDictDao.getALLData(sql);
			if (list != null && list.size() > 0) {
				MwpmSysUserinfo user = (MwpmSysUserinfo) list.get(0);
				String longitudeOld = user.getLong1() == null ? "" : user
						.getLong1();
				String latitudeOld = user.getLat() == null ? "" : user.getLat();
				if (!longitude.equals(longitudeOld)
						|| !latitude.equals(latitudeOld)) {
					try {
						String hql = "update MwpmSysUserinfo set  userid='"
								+ id + "'";
						if (!longitude.equals("")) {
							hql = hql + ",long1='" + longitude + "'";
						}
						if (!latitude.equals("")) {
							hql = hql + ",lat='" + latitude + "'";
						}
						hql = hql + " where userid ='" + id + "'";
						ceiDictDao.updateXYInfo(hql);
						MwpmBussPatrolLoca mwpmBussPatrolLoca = new MwpmBussPatrolLoca();
						mwpmBussPatrolLoca.setUserid(id);
						mwpmBussPatrolLoca.setCreatetime(new Date());
						mwpmBussPatrolLoca.setLat(latitude);
						mwpmBussPatrolLoca.setLong1(longitude);
						ceiDictDao.saveObject(mwpmBussPatrolLoca);
						xmlMessage += "<RETURNCODE>2</RETURNCODE>";
					} catch (Exception e) {
						e.printStackTrace();
						xmlMessage += "<RETURNCODE>0</RETURNCODE>";
					}
				} else {
					xmlMessage += "<RETURNCODE>2</RETURNCODE>";
				}
			} else {
				xmlMessage += "<RETURNCODE>2</RETURNCODE>";
			}
		} else {
			xmlMessage += "<RETURNCODE>-1</RETURNCODE>";
		}
		xmlMessage += "</ROOT>";
		return xmlMessage;
	}

	/**
	 * ����userid��ѯ�����
	 * 
	 * @author tanJie
	 */
	@Override
	public String queryTaskByUserid(String xml) {
		String error = "";
		String xmlMessage = "<?xml version='1.0' encoding='utf-8'?>" + "<ROOT>";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String userid = xmlStrMap.get("userid") == null ? "" : xmlStrMap
					.get("userid");
			try {
				String hql = "from Task where userid='" + userid + "'";
				List list = ceiDictDao.getALLData(hql);
				if (list != null && list.size() > 0) {
					Task t = new Task();
					xmlMessage += "<tasks>";
					for (int i = 0; i < list.size(); i++) {
						t = (Task) list.get(i);
						String id = String.valueOf(t.getId());
						String company = t.getCompany() == null ? "" : t
								.getCompany();
						String taskname = t.getTaskname() == null ? "" : t
								.getTaskname();
						String content = t.getContent() == null ? "" : t
								.getContent();
						xmlMessage += "<task>";
						xmlMessage += "<id>" + id + "</id>";
						xmlMessage += "<company>" + company + "</company>";
						xmlMessage += "<taskname>" + taskname + "</taskname>";
						xmlMessage += "<content>" + content + "</content>";
						xmlMessage += "</task>";
					}
					xmlMessage += "</tasks>";
				} else {
					xmlMessage += "<RETURNCODE>5</RETURNCODE>";
				}
			} catch (Exception e) {
				e.printStackTrace();
				xmlMessage += "<RETURNCODE>0</RETURNCODE>";
			}
		} else {
			xmlMessage += "<RETURNCODE>-1</RETURNCODE>";
		}
		xmlMessage += "</ROOT>";
		return xmlMessage;
	}

	/**
	 * ����case��
	 * 
	 * @author hyx
	 */
	@Override
	public String saveCase(String xml) {
		String error = "";
		String xmlMessage = "<?xml version='1.0' encoding='utf-8'?>" + "<ROOT>";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String eid = xmlStrMap.get("eid") == null ? "0" : xmlStrMap
					.get("eid");
			String name = xmlStrMap.get("name") == null ? "" : xmlStrMap
					.get("name");
			String submituserid = xmlStrMap.get("submituserid") == null ? ""
					: xmlStrMap.get("submituserid");
			String submitunitid = xmlStrMap.get("submitunitid") == null ? ""
					: xmlStrMap.get("submitunitid");
			String createtime = xmlStrMap.get("createtime") == null ? ""
					: xmlStrMap.get("createtime");
			String jointuserid = xmlStrMap.get("jointuserid") == null ? ""
					: xmlStrMap.get("jointuserid");
			String idea = xmlStrMap.get("idea") == null ? "" : xmlStrMap
					.get("idea");
			String status = xmlStrMap.get("status") == null ? "" : xmlStrMap
					.get("status");

			String main = xmlStrMap.get("main") == null ? "" : xmlStrMap
					.get("main");
			String property = xmlStrMap.get("property") == null ? ""
					: xmlStrMap.get("property");
			String casesource = xmlStrMap.get("casesource") == null ? ""
					: xmlStrMap.get("casesource");
			String baseinfo = xmlStrMap.get("baseinfo") == null ? ""
					: xmlStrMap.get("baseinfo");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				MwpmBussCase c = new MwpmBussCase();
				c.setEid(eid);
				c.setName(name);
				c.setSubmituserid(submituserid);
				c.setSubmitunitid(submitunitid);
				c.setJointuserid(jointuserid);
				c.setIdea(idea);
				c.setStatus(status);
				c.setMain(main);

				c.setProperty(property);
				c.setCasesource(casesource);
				c.setBaseinfo(baseinfo);
				if (!createtime.equals("")) {
					c.setCreatetime(sdf.parse(createtime));
				}
				ceiDictDao.saveObject(c);
				xmlMessage += "<RETURNCODE>2</RETURNCODE>";
			} catch (Exception e) {
				e.printStackTrace();
				xmlMessage += "<RETURNCODE>0</RETURNCODE>";
			}
		} else {
			xmlMessage += "<RETURNCODE>-1</RETURNCODE>";
		}
		xmlMessage += "</ROOT>";
		return xmlMessage;
	}

	/**
	 * ��������id�ı�������isdreceive״̬
	 * 
	 * @author tanJie
	 */
	@Override
	public String updateCaseByIsDreceive(String xml) {
		String error = "";
		String xmlMessage = "<?xml version='1.0' encoding='utf-8'?>" + "<ROOT>";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String taskid = xmlStrMap.get("taskid") == null ? "0" : xmlStrMap
					.get("taskid");
			try {
				String hql = "update Task set isdreceive='0' where id='"
						+ taskid + "'";
				ceiDictDao.updateXYInfo(hql);
				xmlMessage += "<RETURNCODE>2</RETURNCODE>";
			} catch (Exception e) {
				e.printStackTrace();
				xmlMessage += "<RETURNCODE>0</RETURNCODE>";
			}
		} else {
			xmlMessage += "<RETURNCODE>-1</RETURNCODE>";
		}
		xmlMessage += "</ROOT>";
		return xmlMessage;
	}

	/**
	 * ����Ѳ���¼
	 * 
	 * @author tanJie
	 */
	@Override
	public String savePatrol(String xml) {
		String error = "";
		String xmlMessage = "";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String registId = xmlStrMap.get("registid") == null ? ""
					: xmlStrMap.get("registid");
			String company = xmlStrMap.get("company") == null ? "" : xmlStrMap
					.get("company");
			String contact = xmlStrMap.get("contact") == null ? "" : xmlStrMap
					.get("contact");
			String phone = xmlStrMap.get("phone") == null ? "" : xmlStrMap
					.get("phone");
			String majorproject = xmlStrMap.get("majorproject") == null ? ""
					: xmlStrMap.get("majorproject");
			String monitoringarea = xmlStrMap.get("monitoringarea") == null ? ""
					: xmlStrMap.get("monitoringarea");
			String checktype = xmlStrMap.get("checktype") == null ? ""
					: xmlStrMap.get("checktype");
			String checkproject1 = xmlStrMap.get("checkproject1") == null ? ""
					: xmlStrMap.get("checkproject1");
			String checkproject2 = xmlStrMap.get("checkproject2") == null ? ""
					: xmlStrMap.get("checkproject2");
			String checkproject3 = xmlStrMap.get("checkproject3") == null ? ""
					: xmlStrMap.get("checkproject3");
			String checkproject4 = xmlStrMap.get("checkproject4") == null ? ""
					: xmlStrMap.get("checkproject4");
			String checkproject5 = xmlStrMap.get("checkproject5") == null ? ""
					: xmlStrMap.get("checkproject5");
			String checkproject6 = xmlStrMap.get("checkproject6") == null ? ""
					: xmlStrMap.get("checkproject6");
			String note = xmlStrMap.get("note") == null ? "" : xmlStrMap
					.get("note");
			try {
				Patrol p = new Patrol();
				p.setRegistId(registId);
				p.setCompany(company);
				p.setContact(contact);
				p.setPhone(phone);
				p.setMajorproject(majorproject);
				p.setMonitoringarea(monitoringarea);
				p.setChecktype(checktype);
				p.setCheckproject1(checkproject1);
				p.setCheckproject2(checkproject2);
				p.setCheckproject3(checkproject3);
				p.setCheckproject4(checkproject4);
				p.setCheckproject5(checkproject5);
				p.setCheckproject6(checkproject6);
				p.setNote(note);
				p.setCreattime(new Date());
				String pkId = ceiDictDao.saveObjectRePK(p) + "";
				xmlMessage = pkId;
			} catch (Exception e) {
				e.printStackTrace();
				xmlMessage += "<RETURNCODE>0</RETURNCODE>";
			}
		} else {
			xmlMessage += "<RETURNCODE>-1</RETURNCODE>";
		}
		xmlMessage += "</ROOT>";
		return xmlMessage;
	}

	/**
	 * ��ѯѲ���¼
	 * 
	 * @author tanJie
	 */
	@Override
	public String queryPatrol(String xml) {
		String error = "";
		String xmlMessage = "";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String userid = xmlStrMap.get("userid") == null ? "" : xmlStrMap
					.get("userid");
			String pageNo = xmlStrMap.get("pageno") == null ? "" : xmlStrMap
					.get("pageno");
			try {
				final String sql = "select MWPM_BUSS_ENTINFO.LAT,MWPM_BUSS_ENTINFO.LONG1 from "
						+ "MWPM_BUSS_ENTINFO ,MWPM_BUSS_PATROL_LOG where "
						+ "MWPM_BUSS_ENTINFO.ID = MWPM_BUSS_PATROL_LOG.ENTID "
						+ "and  MWPM_BUSS_PATROL_LOG.USERID = '"
						+ userid
						+ "' ORDER BY MWPM_BUSS_PATROL_LOG.CLOCK";
				List list = ceiDictDao.getListByNativeSql(sql);
				if (list != null && list.size() > 0)
					xmlMessage = JSONArray.fromObject(list).toString();
				else
					xmlMessage = "{'errorcode':'" + // �����ݷ��ر�־λ
							ErrorCode.NODATA_FLAG + "'}";
			} catch (Exception e) {
				xmlMessage = "{'errorcode':'" + // �ͻ��˴������󷵻ر�־λ
						ErrorCode.EXPSERVER_FLAG + "'}";
				e.printStackTrace();
			}
		} else {
			xmlMessage = "{'errorcode':'" + // �ͻ��˴������󷵻ر�־λ
					ErrorCode.EXPCLIENT_FLAG + "'}";
		}
		return xmlMessage;
	}

	@Override
	public String saveUser(String xml) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * �������ơ�����������ѯ���ɷ���
	 * 
	 * @author Jh
	 */
	@Override
	public String queryCodeFlag(String xml) {
		String error = "";
		String xmlMessage = "<?xml version='1.0' encoding='utf-8'?>" + "<ROOT>";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		MwpmBussCodeFlag codeflag = new MwpmBussCodeFlag();
		if ("".equals(error)) {
			String flagc = xmlStrMap.get("flag") == null ? "" : xmlStrMap
					.get("flag");
			String dmc = xmlStrMap.get("dm") == null ? "" : xmlStrMap.get("dm");
			String bzc = xmlStrMap.get("bz") == null ? "" : xmlStrMap.get("bz");
			String pageNo = xmlStrMap.get("pageno") == null ? "" : xmlStrMap
					.get("pageno");
			codeflag.setFlag(flagc);
			codeflag.setDm(dmc);
			codeflag.setBz(bzc);
			try {
				String hql = "from MwpmBussCodeFlag where 1=1  "
						+ this.getWhereHql(codeflag) + "";
				PageBean pageBean = new PageBean();
				pageBean.setPageSize(Integer.parseInt(CommonParm
						.getString("PAGE_SIZE")));
				pageBean.setPageNo(Integer.parseInt(pageNo.equals("") ? "1"
						: pageNo));
				List<MwpmBussCodeFlag> list = ceiDictDao.getPageBeanList(
						pageBean, hql);
				if (list != null && list.size() > 0) {
					MwpmBussCodeFlag law = new MwpmBussCodeFlag();
					xmlMessage += "<laws>";
					for (int i = 0; i < list.size(); i++) {
						law = (MwpmBussCodeFlag) list.get(i);
						String id = String.valueOf(law.getId());
						String dm = law.getDm();
						String ms = law.getMs() == null ? "" : law.getMs();
						String bz = law.getBz() == null ? "" : law.getBz();
						String yxq = law.getYxq() == null ? "" : law.getYxq()
								.toString().substring(0, 10);
						;
						xmlMessage += "<law>";
						xmlMessage += "<id>" + id + "</id>";
						xmlMessage += "<dm>" + dm + "</dm>";
						xmlMessage += "<ms>" + ms + "</ms>";
						xmlMessage += "<bz>" + bz + "</bz>";
						xmlMessage += "<yxq>" + yxq + "</yxq>";
						xmlMessage += "</law>";
					}
					xmlMessage += "</laws>";
					// xmlMessage = JSONArray.fromObject(list).toString();

					// List list = ceiDictDao.getALLData(hql);
					// if(list!=null && list.size()>0){
					// MwpmBussCodeFlag law = new MwpmBussCodeFlag();
					// xmlMessage += "<laws>";
					// for (int i = 0; i < list.size(); i++) {
					// law = (MwpmBussCodeFlag)list.get(i);
					// String id = String.valueOf(law.getId());
					// String dm = law.getDm();
					// String ms = law.getMs()==null?"":law.getMs();
					// String bz = law.getBz()==null?"":law.getBz();
					// String yxq
					// =law.getYxq()==null?"":law.getYxq().toString().substring(0,10);;
					// xmlMessage += "<law>";
					// xmlMessage += "<id>"+id+"</id>";
					// xmlMessage += "<dm>"+dm+"</dm>";
					// xmlMessage += "<ms>"+ms+"</ms>";
					// xmlMessage += "<bz>"+bz+"</bz>";
					// xmlMessage += "<yxq>"+yxq+"</yxq>";
					// xmlMessage += "</law>";
					// }
					// xmlMessage += "</laws>";

				} else {
					xmlMessage += "<RETURNCODE>5</RETURNCODE>";
				}
			} catch (Exception e) {
				e.printStackTrace();
				xmlMessage += "<RETURNCODE>0</RETURNCODE>";
			}
		} else {
			xmlMessage += "<RETURNCODE>-1</RETURNCODE>";
		}
		xmlMessage += "</ROOT>";
		return xmlMessage;
	}

	/**
	 * ����id��ѯ���ɷ�����ϸ��Ϣ
	 * 
	 * @author Jh
	 */
	@Override
	public String queryCodeFlagByid(String xml) {
		String error = "";
		// String xmlMessage = "<?xml version='1.0' encoding='utf-8'?>" +
		// "<ROOT>";
		String xmlMessage = "";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String id = xmlStrMap.get("id") == null ? "" : xmlStrMap.get("id");
			try {
				String hql = "from MwpmBussCodeFlag where id='" + id + "'";
				List list = ceiDictDao.getALLData(hql);
				if (list != null && list.size() > 0) {
					xmlMessage = JSONObject.fromObject(list.get(0)).toString();
					// MwpmBussCodeFlag t = new MwpmBussCodeFlag();
					// xmlMessage += "<laws>";
					// for (int i = 0; i < list.size(); i++) {
					// t = (MwpmBussCodeFlag) list.get(i);
					// String bz = t.getBz() == null ? "" : t.getBz();
					// String dm = t.getDm() == null ? "" : t.getDm();
					// String ms = t.getMs() == null ? "" : t.getMs();
					// String yxq = t.getYxq() == null ? "" : t.getYxq()
					// .toString().substring(0, 10);
					//
					// xmlMessage += "<law>";
					// xmlMessage += "<bz>" + bz + "</bz>";
					// xmlMessage += "<dm>" + dm + "</dm>";
					// xmlMessage += "<ms>" + ms + "</ms>";
					// xmlMessage += "<yxq>" + yxq + "</yxq>";
					// xmlMessage += "</law>";
					// }
					// xmlMessage += "</laws>";

				} else {
					xmlMessage += "<RETURNCODE>5</RETURNCODE>";
				}
			} catch (Exception e) {
				xmlMessage = "{'errorcode':'" + // ����˴��󷵻ر�־λ
						ErrorCode.EXPSERVER_FLAG + "'}";
				e.printStackTrace();
			}
		} else {
			xmlMessage = "{'errorcode':'" + // �ͻ��˴������󷵻ر�־λ
					ErrorCode.EXPCLIENT_FLAG + "'}";
		}
		return xmlMessage;
	}

	/**
	 * ����Ѳ���¼
	 * 
	 * @author tanJie
	 */
	@Override
	public String saveUserFankui(String xml) {
		String error = "";
		String xmlMessage = "<?xml version='1.0' encoding='utf-8'?>" + "<ROOT>";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String content = xmlStrMap.get("content") == null ? "" : xmlStrMap
					.get("content");
			String userid = xmlStrMap.get("userid") == null ? "" : xmlStrMap
					.get("userid");
			String time = xmlStrMap.get("time") == null ? "" : xmlStrMap
					.get("time");
			try {
				MwpmSysBackmes fk = new MwpmSysBackmes();
				fk.setContent(content);
				fk.setUserid(userid);
				fk.setTime(StringUtil.getDateType(time));
				ceiDictDao.saveObject(fk);
				xmlMessage += "<RETURNCODE>2</RETURNCODE>";
			} catch (Exception e) {
				e.printStackTrace();
				xmlMessage += "<RETURNCODE>0</RETURNCODE>";
			}
		} else {
			xmlMessage += "<RETURNCODE>-1</RETURNCODE>";
		}
		xmlMessage += "</ROOT>";
		return xmlMessage;
	}

	/**
	 * �г����������Ϣ¼��
	 * 
	 * @author Jh
	 */
	@Override
	public String saveEntErrorinfo(String xml) {
		String error = "";
		String xmlMessage = "<?xml version='1.0' encoding='utf-8'?>" + "<ROOT>";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String name = xmlStrMap.get("name") == null ? "" : xmlStrMap
					.get("name");
			String enrol = xmlStrMap.get("enrol") == null ? "" : xmlStrMap
					.get("enrol");
			String submitid = xmlStrMap.get("submitid") == null ? ""
					: xmlStrMap.get("submitid");
			String updatecontent = xmlStrMap.get("updatecontent") == null ? ""
					: xmlStrMap.get("updatecontent");
			String createtime = xmlStrMap.get("createtime") == null ? ""
					: xmlStrMap.get("createtime");

			try {
				MwpmBussEntupdate entinfo = new MwpmBussEntupdate();
				entinfo.setName(name);
				entinfo.setEnrol(enrol);
				entinfo.setSubmitid(submitid);
				entinfo.setUpdatecontent(updatecontent);
				entinfo.setCreatetime(StringUtil.getDateType(createtime));
				ceiDictDao.saveObject(entinfo);
				xmlMessage += "<RETURNCODE>2</RETURNCODE>";
			} catch (Exception e) {
				e.printStackTrace();
				xmlMessage += "<RETURNCODE>0</RETURNCODE>";
			}
		} else {
			xmlMessage += "<RETURNCODE>-1</RETURNCODE>";
		}
		xmlMessage += "</ROOT>";
		return xmlMessage;
	}

	/**
	 * �г����������Ϣ��ѯ
	 * 
	 * @author Jh
	 */
	@Override
	public String queryEntErrorList(String xml) {
		String error = "";
		String xmlMessage = "";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String reseauid = xmlStrMap.get("reseauid") == null ? ""
					: xmlStrMap.get("reseauid");
			String userid = xmlStrMap.get("userid") == null ? ""
					: xmlStrMap.get("userid");
			String pageNo = xmlStrMap.get("pageno") == null ? "" : xmlStrMap
					.get("pageno");
			try {
				String hql = "select u.* from Mwpm_Buss_Entupdate u,MWPM_BUSS_ENTINFO e where u.ENROL=e.ENROL "; 
				PageBean pageBean = new PageBean();
				pageBean.setPageSize(Integer.parseInt(CommonParm
						.getString("PAGE_SIZE")));
				pageBean.setPageNo(Integer.parseInt(pageNo.equals("") ? "1"
						: pageNo));
				if (!userid.equals("") && reseauid.equals("")) {
					hql += "and reseauid in (select a.ID from mwpm_sys_reseau a, MWPM_SYS_UNITUSER b where a.unitid = b.unitid  and b.userid='"
							+ userid + "')";
				}else if(userid.equals("") && !reseauid.equals("")){
					hql += " and reseauid='"+reseauid + "'";
				}
				hql += " LIMIT "
						+ (pageBean.getPageNo() * pageBean.getPageSize() - pageBean
								.getPageSize()) + "," + pageBean.getPageSize();
				List<MwpmBussEntinfo> list = ceiDictDao.getListByNativeSql(hql);
				if (list != null && list.size() > 0)
					xmlMessage = JSONArray.fromObject(list).toString();
				else
					xmlMessage = "{'errorcode':'" + // ����˴��󷵻ر�־λ
							ErrorCode.NODATA_FLAG + "'}";
			} catch (Exception e) {
				e.printStackTrace();
				xmlMessage = "{'errorcode':'" + // ����˴��󷵻ر�־λ
						ErrorCode.EXPSERVER_FLAG + "'}";
				e.printStackTrace();
			}
		} else {
			xmlMessage = "{'errorcode':'" + // �ͻ��˴������󷵻ر�־λ
					ErrorCode.EXPCLIENT_FLAG + "'}";
		}
		return xmlMessage;
	}

	@Override
	public String queryEntErrorInfo(String xml) {
		String error = "";
		String xmlMessage = "";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String id = xmlStrMap.get("id") == null ? "" : xmlStrMap.get("id");
			try {
				String hql = "from MwpmBussEntupdate where id='" + id + "'";
				List list = ceiDictDao.getALLData(hql);
				if (list != null && list.size() > 0) {
					xmlMessage = JSONObject.fromObject(list.get(0)).toString();
				} else {
					xmlMessage += "<RETURNCODE>5</RETURNCODE>";
				}
			} catch (Exception e) {
				e.printStackTrace();
				xmlMessage += "<RETURNCODE>0</RETURNCODE>";
			}
		} else {
			xmlMessage += "<RETURNCODE>-1</RETURNCODE>";
		}
		return xmlMessage;
	}

	/**
	 * ƴ��sql
	 * 
	 * @param adbook
	 * @return
	 */
	private String getWhereHql(MwpmBussCodeFlag adbook) {
		// TODO Auto-generated method stub
		String hql = "";
		String temp = adbook.getBz();
		if (temp != null && temp.length() > 0) {
			hql += " and bz like '%" + temp + "%' ";
		}
		temp = adbook.getFlag();
		if (temp != null && temp.length() > 0) {
			hql += " and flag = '" + temp + "' ";
		}
		temp = adbook.getDm();
		if (temp != null && temp.length() > 0) {
			hql += " and dm like '%" + temp + "%' ";
		}
		return hql;
	}

	/**
	 * ������ҵ��γ��
	 */
	@Override
	public String updateEntInfo(String xml) {
		String error = "";
		String xmlMessage = "";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String id = xmlStrMap.get("id") == null ? "0" : xmlStrMap.get("id");
			String lat = xmlStrMap.get("lat") == null ? "0" : xmlStrMap
					.get("lat");
			String long1 = xmlStrMap.get("long1") == null ? "0" : xmlStrMap
					.get("long1");
			try {
				String hql = "update MwpmBussEntinfo set id='" + id + "'";
				if (!long1.equals("")) {
					hql = hql + ",long1='" + long1 + "'";
				}
				if (!lat.equals("")) {
					hql = hql + ",lat='" + lat + "'";
				}
				hql = hql + " where id ='" + id + "'";
				ceiDictDao.updateXYInfo(hql);
			} catch (Exception e) {
				xmlMessage = "{'errorcode':'" + // ������쳣
						ErrorCode.EXPSERVER_FLAG + "'}";
				e.printStackTrace();
			}
		} else {
			xmlMessage = "{'errorcode':'" + // ������쳣
					ErrorCode.EXPCLIENT_FLAG + "'}";
		}
		return xmlMessage;
	}

	/**
	 * ��ȡ�û�����
	 */
	@Override
	public String getUser(String xml) {
		String error = "";
		String xmlMessage = "";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String loginname = xmlStrMap.get("loginname") == null ? ""
					: xmlStrMap.get("loginname");
			String password = xmlStrMap.get("password") == null ? ""
					: xmlStrMap.get("password");

			if (loginname.equals("") || password.equals("")) {
				xmlMessage = "{'errorcode':'" + // �û�������Ϊ�շ��ر�־λ
						ErrorCode.PASSNULL_FLAG + "'}";
				return xmlMessage;
			}
			try {
				String sql = "from MwpmSysUserinfo ";
				sql += "where LOGINNAME='" + loginname + "'";
				sql += " and PASSWORD='" + password + "'";
				List list = ceiDictDao.getALLData(sql);
				if (list != null && list.size() > 0)
					xmlMessage = JSONObject.fromObject(list.get(0)).toString();
				else
					xmlMessage = "{'errorcode':'" + // �û���������󷵻ر�־λ
							ErrorCode.PASSERROR_FLAG + "'}";
			} catch (Exception e) {
				xmlMessage = "{'errorcode':'" + // ����˴��󷵻ر�־λ
						ErrorCode.EXPSERVER_FLAG + "'}";
				e.printStackTrace();
			}
		} else {
			xmlMessage = "{'errorcode':'" + // �ͻ��˴������󷵻ر�־λ
					ErrorCode.EXPCLIENT_FLAG + "'}";
		}
		return xmlMessage;
	}
	
	

	/**
	 * ��ҳ��ѯ��ҵ�б�
	 */
	@Override
	public String queryEntInfos(String xml) {
		String error = "";
		String xmlMessage = "";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String reseauid = xmlStrMap.get("reseauid") == null ? ""
					: xmlStrMap.get("reseauid");
			String userid = xmlStrMap.get("userid") == null ? ""
					: xmlStrMap.get("userid");
			String enrol = xmlStrMap.get("enrol") == null ? "" : xmlStrMap
					.get("enrol");
			String name = xmlStrMap.get("name") == null ? "" : xmlStrMap
					.get("name");
			String member = xmlStrMap.get("member") == null ? "" : xmlStrMap
					.get("member");
			String address = xmlStrMap.get("address") == null ? "" : xmlStrMap
					.get("address");
			String earcap = xmlStrMap.get("earcap") == null ? "" : xmlStrMap
					.get("earcap");
			String workarea = xmlStrMap.get("workarea") == null ? ""
					: xmlStrMap.get("workarea");
			String pageNo = xmlStrMap.get("pageno") == null ? "" : xmlStrMap
					.get("pageno");
			PageBean pageBean = new PageBean();
			pageBean.setPageSize(Integer.parseInt(CommonParm
					.getString("PAGE_SIZE")));
			pageBean.setPageNo(Integer.parseInt(pageNo.equals("") ? "1"
					: pageNo));
			try {
				String hql = "select * from Mwpm_Buss_Entinfo where claimstatus='yrl' ";
				String[][] likeValues = new String[6][2];
				likeValues[0][0] = "enrol";
				likeValues[0][1] = enrol;
				likeValues[1][0] = "name";
				likeValues[1][1] = name;
				likeValues[2][0] = "member";
				likeValues[2][1] = member;
				likeValues[3][0] = "address";
				likeValues[3][1] = address;
				likeValues[4][0] = "earcap";
				likeValues[4][1] = earcap;
				likeValues[5][0] = "workarea";
				likeValues[5][1] = workarea;
				hql = StringUtil.contactLikeHql(hql, likeValues);
				if (!userid.equals("") && reseauid.equals("")) {
					hql += "and reseauid in (select a.ID from mwpm_sys_reseau a, MWPM_SYS_UNITUSER b where a.unitid = b.unitid  and b.userid='"
							+ userid + "')";
				}else if(userid.equals("") && !reseauid.equals("")){
					hql += " and reseauid='"+reseauid + "'";
				}
				hql += " LIMIT "
						+ (pageBean.getPageNo() * pageBean.getPageSize() - pageBean
								.getPageSize()) + "," + pageBean.getPageSize();
				List<MwpmBussEntinfo> list = ceiDictDao.getListByNativeSql(hql);
				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig.registerJsonValueProcessor(java.util.Date.class,
						new JsonDateValueProcessor());
				if (list != null && list.size() > 0)
					xmlMessage = JSONArray.fromObject(list, jsonConfig)
							.toString();
				else
					xmlMessage = "{'errorcode':'" + // û�����ݱ�־λ
							ErrorCode.NODATA_FLAG + "'}";
			} catch (Exception e) {
				xmlMessage = "{'errorcode':'" + // ����˴��󷵻ر�־λ
						ErrorCode.EXPSERVER_FLAG + "'}";
				e.printStackTrace();
			}
		} else {
			xmlMessage = "{'errorcode':'" + // �ͻ��˴������󷵻ر�־λ
					ErrorCode.EXPCLIENT_FLAG + "'}";
		}
		return xmlMessage;
	}

	/**
	 * ��ҳ��ѯѲ�������б�
	 */
	@Override
	public String queryPatrolTask(String xml) {
		String error = "";
		String xmlMessage = "";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String name = xmlStrMap.get("name") == null ? "" : xmlStrMap
					.get("name");
			String createtimes = xmlStrMap.get("createtimes") == null ? ""
					: xmlStrMap.get("createtimes");
			String createtimee = xmlStrMap.get("createtimee") == null ? ""
					: xmlStrMap.get("createtimee");
			String pageNo = xmlStrMap.get("pageno") == null ? "" : xmlStrMap
					.get("pageno");
			try {
				PageBean pageBean = new PageBean();
				pageBean.setPageSize(Integer.parseInt(CommonParm
						.getString("PAGE_SIZE")));
				pageBean.setPageNo(Integer.parseInt(pageNo.equals("") ? "1"
						: pageNo));
				String sql = "select * from "
				// + "MWPM_BUSS_ENTINFO ,MWPM_BUSS_PATROL_ENTINFO,"
						+ "MWPM_BUSS_PATROL " + "where 1=1 ";
				// + "MWPM_BUSS_ENTINFO.ID = MWPM_BUSS_PATROL_ENTINFO.EID "
				// + "and MWPM_BUSS_PATROL_ENTINFO.PID=MWPM_BUSS_PATROL.ID "
				// + "and  RESEAUID='"
				// + reseauid
				// + "' "
				if (!name.equals(""))
					sql += " and NAME like '" + name + "'   ";
				if (!createtimee.equals(""))
					sql += " and CREATETIME <="
							+ StringUtil.getCurDate("date", "'" + createtimee
									+ "'", "toData") + "   ";
				if (!createtimes.equals(""))
					sql += " and CREATETIME >="
							+ StringUtil.getCurDate("date", "'" + createtimes
									+ "'", "toData") + "   ";
				sql += " ORDER BY CREATETIME DESC";
				sql += " LIMIT "
						+ (pageBean.getPageNo() * pageBean.getPageSize() - pageBean
								.getPageSize()) + "," + pageBean.getPageSize();
				List list = ceiDictDao.getListByNativeSql(sql);
				if (list != null && list.size() > 0)
					xmlMessage = JSONArray.fromObject(list).toString();
				else
					xmlMessage = "{'errorcode':'" + 
							ErrorCode.NODATA_FLAG + "'}";
			} catch (Exception e) {
				xmlMessage = "{'errorcode':'" + 
						ErrorCode.EXPSERVER_FLAG + "'}";
				e.printStackTrace();
			}
		} else {
			xmlMessage = "{'errorcode':'" + 
					ErrorCode.EXPCLIENT_FLAG + "'}";
		}
		return xmlMessage;
	}

	/**
	 * ��ҳ��ѯ�����¶�Ӧ�Ĺ�˾�б�
	 */
	@Override
	public String queryCompanyByPatrolId(String xml) {
		String error = "";
		String xmlMessage = "";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String id = xmlStrMap.get("id") == null ? "" : xmlStrMap.get("id");
			String pageNo = xmlStrMap.get("pageno") == null ? "" : xmlStrMap
					.get("pageno");
			String reseauid = xmlStrMap.get("reseauid") == null ? "" : xmlStrMap
					.get("reseauid");
			try {
				PageBean pageBean = new PageBean();
				pageBean.setPageSize(Integer.parseInt(CommonParm
						.getString("PAGE_SIZE")));
				pageBean.setPageNo(Integer.parseInt(pageNo.equals("") ? "1"
						: pageNo));
				final String sql = "select MWPM_BUSS_ENTINFO.ID,MWPM_BUSS_ENTINFO.NAME,MWPM_BUSS_ENTINFO.ENROL,MWPM_BUSS_ENTINFO.MEMBER,MWPM_BUSS_ENTINFO.ADDRESS "
						+ "from MWPM_BUSS_PATROL_ENTINFO ,MWPM_BUSS_ENTINFO "
						+ "where "
						+ "MWPM_BUSS_PATROL_ENTINFO.EID = MWPM_BUSS_ENTINFO.ID AND MWPM_BUSS_PATROL_ENTINFO.PID = '"
						+ id
						+ "'"
						+" LIMIT "
						+ (pageBean.getPageNo() * pageBean.getPageSize() - pageBean
								.getPageSize()) + "," + pageBean.getPageSize();

				List list = ceiDictDao.getListByNativeSql(sql);
				if (list != null && list.size() > 0)
					xmlMessage = JSONArray.fromObject(list).toString();
				else
					xmlMessage = "{'errorcode':'" + // �����ݷ��ر�־λ
							ErrorCode.NODATA_FLAG + "'}";
			} catch (Exception e) {
				xmlMessage = "{'errorcode':'" + // �ͻ��˴������󷵻ر�־λ
						ErrorCode.EXPSERVER_FLAG + "'}";
				e.printStackTrace();
			}
		} else {
			xmlMessage = "{'errorcode':'" + // �ͻ��˴������󷵻ر�־λ
					ErrorCode.EXPCLIENT_FLAG + "'}";
		}
		return xmlMessage;
	}

	/**
	 * ��ѯ�Ƿ��п쵽�ڵ�����
	 */
	@Override
	public String queryTimeTask(String xml) {
		String error = "";
		String xmlMessage = "";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String reseauid = xmlStrMap.get("reseauid") == null ? ""
					: xmlStrMap.get("reseauid");
			String pageNo = xmlStrMap.get("pageno") == null ? "" : xmlStrMap
					.get("pageno");
			try {
				PageBean pageBean = new PageBean();
				pageBean.setPageSize(Integer.parseInt(CommonParm
						.getString("PAGE_SIZE")));
				pageBean.setPageNo(Integer.parseInt(pageNo.equals("") ? "1"
						: pageNo));
				final String sql = "select MWPM_BUSS_PATROL_ENTINFO.PID from "
						+ "MWPM_BUSS_ENTINFO ,MWPM_BUSS_PATROL_ENTINFO where "
						+ "MWPM_BUSS_ENTINFO.ID = MWPM_BUSS_PATROL_ENTINFO.EID "
						+ "and MWPM_BUSS_PATROL_ENTINFO.ALERTSTATUS='yj' "
						+ "and  RESEAUID = '"
						+ reseauid + "'";
				List list = ceiDictDao.getListByNativeSql(sql);
				if (list != null && list.size() > 0)
					xmlMessage = JSONArray.fromObject(list).toString();
				else
					xmlMessage = "{'errorcode':'" + // �����ݷ��ر�־λ
							ErrorCode.NODATA_FLAG + "'}";
			} catch (Exception e) {
				xmlMessage = "{'errorcode':'" + // �ͻ��˴������󷵻ر�־λ
						ErrorCode.EXPSERVER_FLAG + "'}";
				e.printStackTrace();
			}
		} else {
			xmlMessage = "{'errorcode':'" + // �ͻ��˴������󷵻ر�־λ
					ErrorCode.EXPCLIENT_FLAG + "'}";
		}
		return xmlMessage;
	}

	/**
	 * ��ҳ��ѯѲ���¼�б�
	 */
	@Override
	public String queryPatrolLOG(String xml) {
		String error = "";
		String xmlMessage = "";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String entid = xmlStrMap.get("entid") == null ? "" : xmlStrMap
					.get("entid");
			String type = xmlStrMap.get("type") == null ? "" : xmlStrMap
					.get("type");
			String pageNo = xmlStrMap.get("pageno") == null ? "" : xmlStrMap
					.get("pageno");
			try {
				String hql = "";
				if ("��֤����".equals(type)) {
					hql = "from MwpmBussPatrolLog where nid = '" + entid + "'";
				} else {
					hql = "from MwpmBussPatrolLog where entid = '" + entid
							+ "' and type='" + type + "'";
				}
				PageBean pageBean = new PageBean();
				pageBean.setPageSize(Integer.parseInt(CommonParm
						.getString("PAGE_SIZE")));
				pageBean.setPageNo(Integer.parseInt(pageNo.equals("") ? "1"
						: pageNo));
				List<MwpmBussEntinfo> list = ceiDictDao.getPageBeanList(
						pageBean, hql);
				if (list != null && list.size() > 0)
					xmlMessage = JSONArray.fromObject(list).toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return xmlMessage;
	}

	/**
	 * ��ѯ���������ҵ�б�
	 */
	@Override
	public String queryPatrolENTINFO(String xml) {
		String error = "";
		String xmlMessage = "";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String reseauid = xmlStrMap.get("reseauid") == null ? ""
					: xmlStrMap.get("reseauid");
			String pageNo = xmlStrMap.get("pageno") == null ? "" : xmlStrMap
					.get("pageno");
			try {
				String hql = "from MwpmBussEntinfo where reseauid='" + reseauid
						+ "'" + "and claimstatus='yrl'";// where name like
														// '%"+name+"%'
				PageBean pageBean = new PageBean();
				pageBean.setPageSize(Integer.parseInt(CommonParm
						.getString("PAGE_SIZE")));
				pageBean.setPageNo(Integer.parseInt(pageNo.equals("") ? "1"
						: pageNo));
				List<MwpmBussEntinfo> list = ceiDictDao.getPageBeanList(
						pageBean, hql);
				if (list != null && list.size() > 0)
					xmlMessage = JSONArray.fromObject(list).toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return xmlMessage;
	}

	/**
	 * ֪ͨ���淢��
	 * 
	 * @author Jh
	 */
	@Override
	public String saveNotice(String xml) {
		String error = "";
		String xmlMessage = "<?xml version='1.0' encoding='utf-8'?>" + "<ROOT>";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String content = xmlStrMap.get("content") == null ? "" : xmlStrMap
					.get("content");
			String title = xmlStrMap.get("title") == null ? "" : xmlStrMap
					.get("title");
			String type = xmlStrMap.get("type") == null ? "" : xmlStrMap
					.get("type");
			String userid = xmlStrMap.get("userid") == null ? "" : xmlStrMap
					.get("userid");
			String createtime = xmlStrMap.get("createtime") == null ? ""
					: xmlStrMap.get("createtime");

			try {
				MwpmSysMes mes = new MwpmSysMes();
				mes.setContent(content);
				mes.setTitle(title);
				mes.setType(type);
				mes.setUserid(userid);
				mes.setCreatetime(StringUtil.getDateType(createtime));
				ceiDictDao.saveObject(mes);
				xmlMessage += "<RETURNCODE>2</RETURNCODE>";
			} catch (Exception e) {
				e.printStackTrace();
				xmlMessage += "<RETURNCODE>0</RETURNCODE>";
			}
		} else {
			xmlMessage += "<RETURNCODE>-1</RETURNCODE>";
		}
		xmlMessage += "</ROOT>";
		return xmlMessage;
	}

	/**
	 * ֪ͨ�����ѯ
	 * 
	 * @author Jh
	 */
	@Override
	public String queryNotice(String xml) {
		String error = "";
		String xmlMessage = "";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		MwpmSysMes mes = new MwpmSysMes();
		if ("".equals(error)) {
			String titlec = xmlStrMap.get("title") == null ? "" : xmlStrMap
					.get("title");
			String screatetime = xmlStrMap.get("screatetime") == null ? ""
					: xmlStrMap.get("screatetime");
			String ecreatetime = xmlStrMap.get("ecreatetime") == null ? ""
					: xmlStrMap.get("ecreatetime");
			String pageNo = xmlStrMap.get("pageno") == null ? "" : xmlStrMap
					.get("pageno");
			mes.setTitle(titlec);
			mes.setContent(screatetime);
			mes.setUserid(ecreatetime);
			try {
				String hql = "from MwpmSysMes where 1=1  "
						+ this.getNWhereHql(mes) + "";
				hql += "ORDER BY CREATETIME DESC";
				PageBean pageBean = new PageBean();
				pageBean.setPageSize(Integer.parseInt(CommonParm
						.getString("PAGE_SIZE")));
				pageBean.setPageNo(Integer.parseInt(pageNo.equals("") ? "1"
						: pageNo));
				List list = ceiDictDao.getPageBeanList(pageBean, hql);
				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig.registerJsonValueProcessor(java.util.Date.class,
						new JsonDateValueProcessor());
				if (list != null && list.size() > 0) {
					xmlMessage = JSONArray.fromObject(list, jsonConfig)
							.toString();
				} else {
					xmlMessage = "{'errorcode':'" + // û�����ݷ��ر�־λ
							ErrorCode.NODATA_FLAG + "'}";
				}
			} catch (Exception e) {
				xmlMessage = "{'errorcode':'" + // ����˴��󷵻ر�־λ
						ErrorCode.EXPSERVER_FLAG + "'}";
				e.printStackTrace();
			}
		} else {
			xmlMessage = "{'errorcode':'" + // �ͻ��˴������󷵻ر�־λ
					ErrorCode.EXPCLIENT_FLAG + "'}";
		}
		return xmlMessage;
	}

	/**
	 * ƴ��sql
	 * 
	 * @param adbook
	 * @return
	 */
	private String getNWhereHql(MwpmSysMes mes) {
		// TODO Auto-generated method stub
		String hql = "";
		String temp = mes.getTitle();
		if (temp != null && temp.length() > 0) {
			hql += " and title like '%" + temp + "%' ";
		}
		temp = mes.getContent();

		if (temp != null && temp.length() > 0) {
			// oracle
			// hql +=
			// "logintime >=to_date('"+logInfo.getBegTime1()+"','yyyy-mm-dd')  and ";
			hql += " and createtime >="
					+ StringUtil.getCurDate("date", "'" + temp + "'", "toData")
					+ "   ";

		}
		temp = mes.getUserid();
		if (temp != null && temp.length() > 0) {
			// temp = temp + " 23:59:59";
			// oracle
			// hql +=
			// "logintime <=to_date('"+logInfo.getEndTime1()+"','yyyy-mm-dd')  and ";
			hql += " and createtime <="
					+ StringUtil.getCurDate("date", "'" + temp + "'", "toData")
					+ "   ";
		}

		return hql;
	}

	/**
	 * ֪ͨ������ϸ��Ϣ
	 * 
	 * @author Jh
	 */
	@Override
	public String queryNoticeInfo(String xml) {
		String error = "";
		String xmlMessage = "";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String id = xmlStrMap.get("id") == null ? "" : xmlStrMap.get("id");
			try {
				String hql = "from MwpmSysMes where id='" + id + "'";
				List list = ceiDictDao.getALLData(hql);

				if (list != null && list.size() > 0) {
					xmlMessage = JSONObject.fromObject(list.get(0)).toString();
					// MwpmSysMes t = new MwpmSysMes();
					// xmlMessage += "<notices>";
					// for (int i = 0; i < list.size(); i++) {
					// t = (MwpmSysMes)list.get(i);
					// String content = t.getContent()==null?"":t.getContent();
					// String title = t.getTitle()==null?"":t.getTitle();
					// String type = t.getType()==null?"":t.getType();
					// String time =
					// t.getCreatetime()==null?"":t.getCreatetime().toString().substring(0,10);
					// String username = t.getUserid()==null?"":t.getUserid();
					// // if(null!=username){
					// // hql = "from MwpmSysUserinfo where id='"+id+"'";
					// // List listus = ceiDictDao.getALLData(hql);
					// // if(listus!=null && listus.size()>0){
					// // MwpmSysUserinfo u = new MwpmSysUserinfo();
					// // for (int j = 0; j < list.size(); j++) {
					// // u = (MwpmSysUserinfo)list.get(i);
					// // username = u.getLoginname()==null?"":u.getLoginname();
					// // }
					// // }
					// // }
					// xmlMessage += "<notice>";
					// xmlMessage += "<content>"+content+"</content>";
					// xmlMessage += "<title>"+title+"</title>";
					// xmlMessage += "<type>"+type+"</type>";
					// xmlMessage += "<username>"+username+"</username>";
					// xmlMessage += "<createtime>"+time+"</createtime>";
					// xmlMessage += "</notice>";
					// }
					// xmlMessage += "</notices>";
				} else {
					xmlMessage += "<RETURNCODE>5</RETURNCODE>";
				}
			} catch (Exception e) {
				xmlMessage = "{'errorcode':'" + // ����˴��󷵻ر�־λ
						ErrorCode.EXPSERVER_FLAG + "'}";
				e.printStackTrace();
			}
		} else {
			xmlMessage = "{'errorcode':'" + // �ͻ��˴������󷵻ر�־λ
					ErrorCode.EXPCLIENT_FLAG + "'}";
		}
		return xmlMessage;
	}

	/**
	 * ��ѯ�ز��¼�б� hql�е�����
	 */
	@Override
	public String queryReturnLog(String xml) {
		String error = "";
		String xmlMessage = "";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String contentid = xmlStrMap.get("contentid") == null ? ""
					: xmlStrMap.get("contentid");
			String pageNo = xmlStrMap.get("pageno") == null ? "" : xmlStrMap
					.get("pageno");
			try {
				String hql = "from MwpmBussReturnlog where contentid='"
						+ contentid + "'";// where name like '%"+name+"%'
				PageBean pageBean = new PageBean();
				pageBean.setPageSize(Integer.parseInt(CommonParm
						.getString("PAGE_SIZE")));
				pageBean.setPageNo(Integer.parseInt(pageNo.equals("") ? "1"
						: pageNo));
				List<MwpmBussReturnlog> list = ceiDictDao.getPageBeanList(
						pageBean, hql);
				if (list != null && list.size() > 0)
					xmlMessage = JSONArray.fromObject(list).toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return xmlMessage;
	}

	/**
	 * ����Ѳ���¼�б�
	 */
	@Override
	public String savePatrolLOG(String xml) {
		String error = "";
		String xmlMessage = "<?xml version='1.0' encoding='utf-8'?>" + "<ROOT>";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			// Ѳ���¼��
			String handman= xmlStrMap.get("handman") == null ? "" : xmlStrMap
					.get("handman");
			String entid = xmlStrMap.get("entid") == null ? "" : xmlStrMap
					.get("entid");
			String content = xmlStrMap.get("content") == null ? "" : xmlStrMap
					.get("content");
			String userid = xmlStrMap.get("userid") == null ? "" : xmlStrMap
					.get("userid");
			String clock = xmlStrMap.get("clock") == null ? "" : xmlStrMap
					.get("clock");
			String type = xmlStrMap.get("type") == null ? "" : xmlStrMap
					.get("type");
			String area = xmlStrMap.get("area") == null ? "" : xmlStrMap
					.get("area");
			String remark = xmlStrMap.get("remark") == null ? "" : xmlStrMap
					.get("remark");
			String term = xmlStrMap.get("term") == null ? "" : xmlStrMap
					.get("term");
			String isrecheck = xmlStrMap.get("isrecheck") == null ? ""
					: xmlStrMap.get("isrecheck");
			String rid = xmlStrMap.get("rid") == null ? "" : xmlStrMap
					.get("rid");
			String pid = xmlStrMap.get("pid") == null ? "" : xmlStrMap
					.get("pid");
			try {
				MwpmBussPatrolLog mpl = new MwpmBussPatrolLog();
				if ("��֤����".equals(type)) {
					mpl.setNid(entid);
				} else {
					mpl.setEntid(entid);
				}
				mpl.setHandman(handman);
				mpl.setContent(content);
				mpl.setUserid(userid);
				mpl.setClock(StringUtil.getDateType(clock));
				mpl.setType(type);
				mpl.setArea(area);
				mpl.setRemark(remark);
				mpl.setTerm(StringUtil.getDateType(term));
				mpl.setIsrecheck(isrecheck);
				mpl.setRid(rid);
				String pkId = ceiDictDao.saveObjectRePK(mpl) + "";
					ceiDictDao
						.updateXYInfo("update MwpmBussPatrol set status='ywc' where id='"
								+ pid + "' and (" + "select count(*) from MwpmBussPatrolEntinfo where pid='" +
								pid +
								"' and ischecked='n')=1");
				ceiDictDao.updateXYInfo("update MwpmBussPatrolEntinfo set ischecked='y' where pid='"
						+ pid + "' and eid='"+ entid + "'");
				return pkId;
			} catch (Exception e) {
				e.printStackTrace();
				xmlMessage += "<RETURNCODE>0</RETURNCODE>";
			}
		} else {
			xmlMessage += "<RETURNCODE>-1</RETURNCODE>";
		}
		xmlMessage += "</ROOT>";
		return xmlMessage;
	}

	@Override
	public String saveNocard(String xml) {
		String error = "";
		String xmlMessage = "<?xml version='1.0' encoding='utf-8'?>" + "<ROOT>";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String content = xmlStrMap.get("content") == null ? "" : xmlStrMap
					.get("content");
			String title = xmlStrMap.get("title") == null ? "" : xmlStrMap
					.get("title");
			String type = xmlStrMap.get("type") == null ? "" : xmlStrMap
					.get("type");
			String operator = xmlStrMap.get("operator") == null ? ""
					: xmlStrMap.get("operator");
			String submittime = xmlStrMap.get("submittime") == null ? ""
					: xmlStrMap.get("submittime");
			String address = xmlStrMap.get("address") == null ? "" : xmlStrMap
					.get("address");
			String idcard = xmlStrMap.get("idcard") == null ? "" : xmlStrMap
					.get("idcard");
			String item = xmlStrMap.get("item") == null ? "" : xmlStrMap
					.get("item");
			String phone = xmlStrMap.get("phone") == null ? "" : xmlStrMap
					.get("phone");
			String result = xmlStrMap.get("result") == null ? "" : xmlStrMap
					.get("result");
			String submitid = xmlStrMap.get("submitid") == null ? ""
					: xmlStrMap.get("submitid");
			try {
				MwpmBussNocard nocard = new MwpmBussNocard();
				nocard.setAddress(address);
				nocard.setIdcard(idcard);
				nocard.setItem(item);
				nocard.setOperator(operator);
				nocard.setPhone(phone);
				nocard.setResult(result);
				nocard.setSubmitid(submitid);
				nocard.setTitle(title);
				nocard.setType(type);
				nocard.setSubmittime(StringUtil.getDateType(submittime));
				ceiDictDao.saveObject(nocard);
				xmlMessage += "<RETURNCODE>2</RETURNCODE>";
			} catch (Exception e) {
				e.printStackTrace();
				xmlMessage += "<RETURNCODE>0</RETURNCODE>";
			}
		} else {
			xmlMessage += "<RETURNCODE>-1</RETURNCODE>";
		}
		xmlMessage += "</ROOT>";
		return xmlMessage;
	}

	@Override
	public String queryNocardList(String xml) {
		String error = "";
		String xmlMessage = "";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String submittimes = xmlStrMap.get("submittimes") == null ? ""
					: xmlStrMap.get("submittimes");
			String submittimee = xmlStrMap.get("submittimee") == null ? ""
					: xmlStrMap.get("submittimee");
			String pageNo = xmlStrMap.get("pageno") == null ? "" : xmlStrMap
					.get("pageno");
			String reseauid = xmlStrMap.get("reseauid") == null ? ""
					: xmlStrMap.get("reseauid");
			

			String hqlwhere = "";
			String temp = submittimes;
			if (temp != null && temp.length() > 0) {
				hqlwhere += " and submittime >="
						+ StringUtil.getCurDate("date", "'" + temp + "'",
								"toData") + "   ";

			}
			temp = submittimee;
			if (temp != null && temp.length() > 0) {
				hqlwhere += " and submittime <="
						+ StringUtil.getCurDate("date", "'" + temp + "'",
								"toData") + "   ";
			}

			try {
				String hql = "from MwpmBussNocard where 1=1  " + hqlwhere + "";
				if(!reseauid.equals(""))
					hql+= " and reseauid='"+reseauid+"'";
				PageBean pageBean = new PageBean();
				pageBean.setPageSize(Integer.parseInt(CommonParm
						.getString("PAGE_SIZE")));
				pageBean.setPageNo(Integer.parseInt(pageNo.equals("") ? "1"
						: pageNo));
				List<MwpmBussEntinfo> list = ceiDictDao.getPageBeanList(
						pageBean, hql);
				if (list != null && list.size() > 0) {
					xmlMessage = JSONArray.fromObject(list).toString();
				} else {
					xmlMessage = "{'errorcode':'" + // û�����ݷ��ر�־λ
							ErrorCode.NODATA_FLAG + "'}";
				}
			} catch (Exception e) {
				xmlMessage = "{'errorcode':'" + // ����˴��󷵻ر�־λ
						ErrorCode.EXPSERVER_FLAG + "'}";
				e.printStackTrace();
			}
		} else {
			xmlMessage = "{'errorcode':'" + // �ͻ��˴������󷵻ر�־λ
					ErrorCode.EXPCLIENT_FLAG + "'}";
		}
		return xmlMessage;
	}

	/**
	 * ����Ѳ������
	 */
	@Override
	public String savePatrolItem(String xml) {
		String error = "";
		String xmlMessage = "<?xml version='1.0' encoding='utf-8'?>" + "<ROOT>";
		List<MwpmBussPatrolItem> xmlStrList = new ArrayList<MwpmBussPatrolItem>();
		error = ReadXML.parseInputDatasXml(xml, xmlStrList);
		if ("".equals(error)) {
			// Ѳ�������
			for (MwpmBussPatrolItem mpi : xmlStrList) {
				try {
					ceiDictDao.saveObject(mpi);
					xmlMessage += "<RETURNCODE>2</RETURNCODE>";
				} catch (Exception e) {
					e.printStackTrace();
					xmlMessage += "<RETURNCODE>0</RETURNCODE>";
				}
			}

		} else {
			xmlMessage += "<RETURNCODE>-1</RETURNCODE>";
		}
		xmlMessage += "</ROOT>";
		return xmlMessage;
	}

	/**
	 * ����֤��
	 */
	@Override
	public String savePatrolProof(String xml) {
		String error = "";
		String xmlMessage = "<?xml version='1.0' encoding='utf-8'?>" + "<ROOT>";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			// Ѳ��֤�ݱ�
			String lid = xmlStrMap.get("lid") == null ? "" : xmlStrMap
					.get("lid");
			String path = xmlStrMap.get("path") == null ? "" : xmlStrMap
					.get("path");
			String userid = xmlStrMap.get("userid") == null ? "" : xmlStrMap
					.get("userid");
			String clock = xmlStrMap.get("clock") == null ? "" : xmlStrMap
					.get("clock");
			String remark = xmlStrMap.get("remark") == null ? "" : xmlStrMap
					.get("remark");
			try {
				MwpmBussPatrolProof mpp = new MwpmBussPatrolProof();
				mpp.setLid(lid);
				mpp.setPath(path);
				mpp.setUserid(userid);
				mpp.setClock(StringUtil.getDateType(clock));
				mpp.setRemark(remark);
				ceiDictDao.saveObject(mpp);
				xmlMessage += "<RETURNCODE>2</RETURNCODE>";
			} catch (Exception e) {
				e.printStackTrace();
				xmlMessage += "<RETURNCODE>0</RETURNCODE>";
			}
		} else {
			xmlMessage += "<RETURNCODE>-1</RETURNCODE>";
		}
		xmlMessage += "</ROOT>";
		return xmlMessage;
	}

	@Override
	public String queryNocardInfo(String xml) {
		String error = "";
		String xmlMessage = "";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String id = xmlStrMap.get("id") == null ? "" : xmlStrMap.get("id");
			try {
				String hql = "from MwpmBussNocard where id='" + id + "'";
				List list = ceiDictDao.getALLData(hql);
				if (list != null && list.size() > 0) {
					xmlMessage = JSONObject.fromObject(list.get(0)).toString();
				} else {
					xmlMessage += "<RETURNCODE>5</RETURNCODE>";
				}
			} catch (Exception e) {
				xmlMessage = "{'errorcode':'" + // ����˴��󷵻ر�־λ
						ErrorCode.EXPSERVER_FLAG + "'}";
				e.printStackTrace();
			}
		} else {
			xmlMessage = "{'errorcode':'" + // �ͻ��˴������󷵻ر�־λ
					ErrorCode.EXPCLIENT_FLAG + "'}";
		}
		return xmlMessage;
	}

	@Override
	public String getUserListByGroupId(String xml) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String queryUserGroup(String xml) {
		String error = "";
		String xmlMessage = "<?xml version='1.0' encoding='utf-8'?>" + "<ROOT>";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String id = xmlStrMap.get("id") == null ? "" : xmlStrMap.get("id");
			// �õ����б�
			try {
				String hql = "from MwpmSysOrginfo";
				List<MwpmSysOrginfo> list = ceiDictDao.getALLData(hql);
				if (list != null && list.size() > 0) {
					MwpmSysOrginfo group = new MwpmSysOrginfo();
					xmlMessage += "<GROUP>";
					for (int i = 0; i < list.size(); i++) {
						group = (MwpmSysOrginfo) list.get(i);
						String gid = group.getId() == null ? "" : group.getId();
						String name = group.getOrgname() == null ? "" : group
								.getOrgname();
						String pid = group.getParentid() == null ? "" : group
								.getParentid();
						;
						xmlMessage += "<ITEM>";
						xmlMessage += "<GID>" + gid + "</GID>";
						xmlMessage += "<GNAME>" + name + "</GNAME>";
						xmlMessage += "<PID>" + pid + "</PID>";
						xmlMessage += "</ITEM>";
					}
					xmlMessage += "</GROUP>";

				} else {
					// xmlMessage += "<RETURNCODE>5</RETURNCODE>";
				}
			} catch (Exception e) {
				e.printStackTrace();
				xmlMessage += "<RETURNCODE>0</RETURNCODE>";
			}
			// �����û��б�
			try {

				String hqlou = "from MwpmSysOrguser";
				List<MwpmSysOrguser> listou = ceiDictDao.getALLData(hqlou);
				if (listou != null && listou.size() > 0) {
					MwpmSysOrguser orguser = new MwpmSysOrguser();
					xmlMessage += "<USER>";
					for (int ii = 0; ii < listou.size(); ii++) {
						orguser = (MwpmSysOrguser) listou.get(ii);
						String userid = orguser.getUserid();
						String orgid = orguser.getOrgid() == null ? ""
								: orguser.getOrgid();

						if (userid != null) {
							String hql = "from MwpmSysUserinfo where userid = '"
									+ userid + "'";
							List<MwpmSysUserinfo> list = ceiDictDao
									.getALLData(hql);
							if (list != null && list.size() > 0) {
								MwpmSysUserinfo userinfo = new MwpmSysUserinfo();
								for (int i = 0; i < list.size(); i++) {
									userinfo = (MwpmSysUserinfo) list.get(i);
									String loginname = userinfo.getUserid() == null ? ""
											: userinfo.getUserid();
									String name = userinfo.getName() == null ? ""
											: userinfo.getName();
									String phone = userinfo.getPhone() == null ? ""
											: userinfo.getPhone();
									xmlMessage += "<ITEM>";
									xmlMessage += "<UID>" + userid + "</UID>";
									xmlMessage += "<UNAME>" + name + "</UNAME>";
									xmlMessage += "<PHONE>" + phone
											+ "</PHONE>";
									xmlMessage += "<GID>" + orgid + "</GID>";
									xmlMessage += "</ITEM>";
								}

							} else {
								// xmlMessage += "<RETURNCODE>5</RETURNCODE>";
							}
						} else {
							xmlMessage += "<RETURNCODE>5</RETURNCODE>";
						}
					}
					xmlMessage += "</USER>";
				}
			} catch (Exception e) {
				e.printStackTrace();
				xmlMessage += "<RETURNCODE>0</RETURNCODE>";
			}

		} else {
			xmlMessage += "<RETURNCODE>-1</RETURNCODE>";
		}
		xmlMessage += "</ROOT>";
		return xmlMessage;
	}

	@Override
	public String getUserListByuserId(String xml) {
		String error = "";
		String xmlMessage = "";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String uid = xmlStrMap.get("userid") == null ? "" : xmlStrMap
					.get("userid");
			try {
				String hql = "from MwpmSysUserinfo where userid =  '" + uid
						+ "'";
				List<MwpmSysUserinfo> list = ceiDictDao.getALLData(hql);
				if (list != null && list.size() > 0) {
					xmlMessage += JSONObject.fromObject(list.get(0)).toString();

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return xmlMessage;
	}

	@Override
	public String getCommonUserList(String xml) {
		String error = "";
		String xmlMessage = "";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String pageNo = xmlStrMap.get("pageno") == null ? "" : xmlStrMap
					.get("pageno");
			String userid = xmlStrMap.get("userid") == null ? "" : xmlStrMap
					.get("userid");
			try {
				String sql = "";
				PageBean pageBean = new PageBean();
				pageBean.setPageSize(Integer.parseInt(CommonParm
						.getString("PAGE_SIZE")));
				pageBean.setPageNo(Integer.parseInt(pageNo.equals("") ? "1"
						: pageNo));
				List list = null;
				List unitid = ceiDictDao.getListByNativeSql("select unitid from MWPM_SYS_UNITUSER where userid='" +userid + "'");
				String unitidStr = unitid.toString().replace("[{", "").replace("}]", "").replace("UNITID=", "");
				List level = ceiDictDao.getListByNativeSql("select level from MWPM_SYS_USERINFO where userid='" +userid + "'");
				String levelStr = level.toString().replace("[{", "").replace("}]", "").replace("LEVEL=", "");
				if(levelStr.toString().equals("jz")){
					sql = "select * from mwpm_sys_userinfo";
				}else if(levelStr.toString().equals("zdz")){
					sql = "select i.* from MWPM_SYS_UNITUSER u," +
							"MWPM_SYS_USERINFO i where u.userid=i.userid and u.UNITID in "
							+"(select id from mwpm_sys_unitinfo where" +
							" parentid=(select unitid from MWPM_SYS_UNITUSER where userid='" +userid + "')"
							+" or id=(select unitid from MWPM_SYS_UNITUSER where userid='" +userid + "')"
							+ " or parentid=(select parentid from MWPM_SYS_UNITUSER where userid='" +userid + "'))"; 
				}else{
					sql = "select i.* from MWPM_SYS_UNITUSER u," +
							"MWPM_SYS_USERINFO i where u.userid=i.userid and u.UNITID ='" + unitidStr.toString() + "'";
				}
				list = ceiDictDao.getListByNativeSql(sql);
				if (list != null && list.size() > 0) {
					xmlMessage = JSONArray.fromObject(list).toString();
				} else {
					xmlMessage = "{'errorcode':'" + // �����ݷ��ر�־λ
							ErrorCode.NODATA_FLAG + "'}";
				}
			} catch (Exception e) {
				e.printStackTrace();
				xmlMessage = "{'errorcode':'" + ErrorCode.EXPSERVER_FLAG + "'}";
			}
		} else {
			xmlMessage = "{'errorcode':'" + // �����ݷ��ر�־λ
					ErrorCode.EXPCLIENT_FLAG + "'}";
		}
		return xmlMessage;
	}

	@Override
	public String queryNoticeNum(String xml) {
		String error = "";
		String xmlMessage = "";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			try {
				String sql = "select count(*) as count from MWPM_SYS_MES";
				List list = ceiDictDao.getListByNativeSql(sql);
				xmlMessage = JSONObject.fromObject(list.get(0)).toString();// ������Ϣ������
			} catch (Exception e) {
				xmlMessage = "{'errorcode':'" + // ����˴��󷵻ر�־λ
						ErrorCode.EXPSERVER_FLAG + "'}";
				e.printStackTrace();
			}
		}
		return xmlMessage;
	}

	@Override
	public String queryReturnLogList(String xml) {
		String error = "";
		String xmlMessage = "";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String lid = xmlStrMap.get("lid") == null ? "" : xmlStrMap
					.get("lid");
			String pageNo = xmlStrMap.get("pageno") == null ? "" : xmlStrMap
					.get("pageno");

			try {
				String hql = "from MwpmBussReturnlog where lid = '" + lid + "'";

				PageBean pageBean = new PageBean();
				pageBean.setPageSize(Integer.parseInt(CommonParm
						.getString("PAGE_SIZE")));
				pageBean.setPageNo(Integer.parseInt(pageNo.equals("") ? "1"
						: pageNo));
				List<MwpmBussReturnlog> list = ceiDictDao.getPageBeanList(
						pageBean, hql);
				if (list != null && list.size() > 0) {
					xmlMessage = JSONArray.fromObject(list).toString();
				} else {
					xmlMessage = "{'errorcode':'" + // û�����ݷ��ر�־λ
							ErrorCode.NODATA_FLAG + "'}";
				}
			} catch (Exception e) {
				xmlMessage = "{'errorcode':'" + // ����˴��󷵻ر�־λ
						ErrorCode.EXPSERVER_FLAG + "'}";
				e.printStackTrace();
			}
		} else {
			xmlMessage = "{'errorcode':'" + // �ͻ��˴������󷵻ر�־λ
					ErrorCode.EXPCLIENT_FLAG + "'}";
		}
		return xmlMessage;
	}

	@Override
	public String saveReturnLog(String xml) {
		String error = "";
		String xmlMessage = "<?xml version='1.0' encoding='utf-8'?>" + "<ROOT>";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {

			String checktime = xmlStrMap.get("checktime") == null ? ""
					: xmlStrMap.get("checktime");
			String checkthing = xmlStrMap.get("checkthing") == null ? ""
					: xmlStrMap.get("checkthing");
			String lid = xmlStrMap.get("lid") == null ? "" : xmlStrMap
					.get("lid");
			String disposetype = xmlStrMap.get("disposetype") == null ? ""
					: xmlStrMap.get("disposetype");

			try {
				MwpmBussReturnlog hc = new MwpmBussReturnlog();
				hc.setCheckthing(checkthing);
				hc.setLid(lid);
				hc.setDisposetype(disposetype);
				hc.setChecktime(StringUtil.getDateType(checktime));
				ceiDictDao.saveObject(hc);
				String sql = "update MwpmBussPatrolLog set isrecheck='y' where id="+"'"+lid+"'";
				ceiDictDao.updateXYInfo(sql);
				xmlMessage += "<RETURNCODE>2</RETURNCODE>";
			} catch (Exception e) {
				e.printStackTrace();
				xmlMessage += "<RETURNCODE>0</RETURNCODE>";
			}
		} else {
			xmlMessage += "<RETURNCODE>-1</RETURNCODE>";
		}
		xmlMessage += "</ROOT>";
		return xmlMessage;
	}

	@Override
	public String queryMwpmBussPatrolItemList(String xml) {
		String error = "";
		String xmlMessage = "";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String logid = xmlStrMap.get("logid") == null ? "" : xmlStrMap
					.get("logid");
			String pageNo = xmlStrMap.get("pageno") == null ? "" : xmlStrMap
					.get("pageno");

			try {
				String hql = "from MwpmBussPatrolItem where logid =  '" + logid
						+ "'";

				PageBean pageBean = new PageBean();
				pageBean.setPageSize(Integer.parseInt(CommonParm
						.getString("PAGE_SIZE")));
				pageBean.setPageNo(Integer.parseInt(pageNo.equals("") ? "1"
						: pageNo));
				List<MwpmBussPatrolItem> list = ceiDictDao.getPageBeanList(
						pageBean, hql);
				if (list != null && list.size() > 0) {
					xmlMessage = JSONArray.fromObject(list).toString();
				} else {
					xmlMessage = "{'errorcode':'" + // û�����ݷ��ر�־λ
							ErrorCode.NODATA_FLAG + "'}";
				}
			} catch (Exception e) {
				xmlMessage = "{'errorcode':'" + // ����˴��󷵻ر�־λ
						ErrorCode.EXPSERVER_FLAG + "'}";
				e.printStackTrace();
			}
		} else {
			xmlMessage = "{'errorcode':'" + // �ͻ��˴������󷵻ر�־λ
					ErrorCode.EXPCLIENT_FLAG + "'}";
		}
		return xmlMessage;
	}

	/**
	 * ����֤��
	 * 
	 * @param proofs
	 */
	public void saveMwpmBussPatrolProofs(List<MwpmBussPatrolProof> proofs)
			throws Exception {
		for (int i = 0; i < proofs.size(); i++) {
			ceiDictDao.saveObject(proofs.get(i));
		}
	}

	@Override
	public String getEntByReaId(String xml) {
		String error = "";
		String xmlMessage = "";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String reseauid = xmlStrMap.get("reseauid") == null ? ""
					: xmlStrMap.get("reseauid");
			String userid = xmlStrMap.get("userid") == null ? ""
					: xmlStrMap.get("userid");
			String id = xmlStrMap.get("id") == null ? ""
					: xmlStrMap.get("id");
			String pageNo = xmlStrMap.get("pageno") == null ? "" : xmlStrMap
					.get("pageno");
			try {
				String hql = "select * from Mwpm_Buss_Entinfo where 1 = 1" +" and id='"+id+"'";
				if (!userid.equals("") && reseauid.equals("")) {
					hql += "and reseauid in (select a.ID from mwpm_sys_reseau a, MWPM_SYS_UNITUSER b where a.unitid = b.unitid  and b.userid='"
							+ userid + "')";
				}else if(userid.equals("") && !reseauid.equals("")){
					hql += " and reseauid='"+reseauid + "'";
				}
				PageBean pageBean = new PageBean();
				pageBean.setPageSize(Integer.parseInt(CommonParm
						.getString("PAGE_SIZE")));
				pageBean.setPageNo(Integer.parseInt(pageNo.equals("") ? "1"
						: pageNo));
				hql += " LIMIT "
						+ (pageBean.getPageNo() * pageBean.getPageSize() - pageBean
								.getPageSize()) + "," + pageBean.getPageSize();
				List<MwpmBussEntinfo> list = ceiDictDao.getListByNativeSql(
						hql);
				if (list != null && list.size() > 0) {
					xmlMessage = JSONArray.fromObject(list).toString();
				} else {
					xmlMessage = "{'errorcode':'" + // û�����ݷ��ر�־λ
							ErrorCode.NOROLE_FLAG + "'}";
				}
			} catch (Exception e) {
				xmlMessage = "{'errorcode':'" + // ����˴��󷵻ر�־λ
						ErrorCode.EXPSERVER_FLAG + "'}";
				e.printStackTrace();
			}
		} else {
			xmlMessage = "{'errorcode':'" + // �ͻ��˴������󷵻ر�־λ
					ErrorCode.EXPCLIENT_FLAG + "'}";
		}
		return xmlMessage;
	}

	// ��������ID�����û�
	@Override
	public String getUserListByrId(String xml) {
		String error = "";
		String xmlMessage = "";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String reseauid = xmlStrMap.get("reseauid") == null ? ""
					: xmlStrMap.get("reseauid");
			String pageNo = xmlStrMap.get("pageno") == null ? "" : xmlStrMap
					.get("pageno");
			try {
				String hql = "from MwpmSysUserinfo where reseauid =  '"
						+ reseauid + "'";
				PageBean pageBean = new PageBean();
				pageBean.setPageSize(Integer.parseInt(CommonParm
						.getString("PAGE_SIZE")));
				pageBean.setPageNo(Integer.parseInt(pageNo.equals("") ? "1"
						: pageNo));
				List<MwpmBussPatrolItem> list = ceiDictDao.getPageBeanList(
						pageBean, hql);
				if (list != null && list.size() > 0) {
					xmlMessage = JSONArray.fromObject(list).toString();
				} else {
					xmlMessage = "{'errorcode':'" + // û�����ݷ��ر�־λ
							ErrorCode.NOROLE_FLAG + "'}";
				}
			} catch (Exception e) {
				xmlMessage = "{'errorcode':'" + // ����˴��󷵻ر�־λ
						ErrorCode.EXPSERVER_FLAG + "'}";
				e.printStackTrace();
			}
		} else {
			xmlMessage = "{'errorcode':'" + // �ͻ��˴������󷵻ر�־λ
					ErrorCode.EXPCLIENT_FLAG + "'}";
		}
		return xmlMessage;
	}

	@Override
	public String queryDictionaryList(String xml) {
		String error = "";
		String xmlMessage = "";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String groupcode = xmlStrMap.get("groupcode") == null ? ""
					: xmlStrMap.get("groupcode");
			String pageNo = xmlStrMap.get("pageno") == null ? "" : xmlStrMap
					.get("pageno");
			try {
				String hql = "from MwpmSysDictionary where groupcode =  '"
						+ groupcode + "'";
				PageBean pageBean = new PageBean();
				pageBean.setPageSize(Integer.parseInt(CommonParm
						.getString("PAGE_SIZE")));
				pageBean.setPageNo(Integer.parseInt(pageNo.equals("") ? "1"
						: pageNo));
				List<MwpmBussPatrolItem> list = ceiDictDao.getPageBeanList(
						pageBean, hql);
				if (list != null && list.size() > 0) {
					xmlMessage = JSONArray.fromObject(list).toString();
				} else {
					xmlMessage = "{'errorcode':'" + // û�����ݷ��ر�־λ
							ErrorCode.NOROLE_FLAG + "'}";
				}
			} catch (Exception e) {
				xmlMessage = "{'errorcode':'" + // ����˴��󷵻ر�־λ
						ErrorCode.EXPSERVER_FLAG + "'}";
				e.printStackTrace();
			}
		} else {
			xmlMessage = "{'errorcode':'" + // �ͻ��˴������󷵻ر�־λ
					ErrorCode.EXPCLIENT_FLAG + "'}";
		}
		return xmlMessage;
	}

	/**
	 * ��ѯר�������б�
	 */
	@Override
	public String queryZxPatrolTask(String xml) {
		String error = "";
		String xmlMessage = "";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String reseauid = xmlStrMap.get("reseauid") == null ? ""
					: xmlStrMap.get("reseauid");
			String userid = xmlStrMap.get("userid") == null ? ""
					: xmlStrMap.get("userid");
			String enrol = xmlStrMap.get("enrol") == null ? "" : xmlStrMap
					.get("enrol");
			String name = xmlStrMap.get("name") == null ? "" : xmlStrMap
					.get("name");
			String member = xmlStrMap.get("member") == null ? "" : xmlStrMap
					.get("member");
			String address = xmlStrMap.get("address") == null ? "" : xmlStrMap
					.get("address");
			String earcap = xmlStrMap.get("earcap") == null ? "" : xmlStrMap
					.get("earcap");
			String workarea = xmlStrMap.get("workarea") == null ? ""
					: xmlStrMap.get("workarea");
			String pageNo = xmlStrMap.get("pageno") == null ? "" : xmlStrMap
					.get("pageno");
			try {
				PageBean pageBean = new PageBean();
				pageBean.setPageSize(Integer.parseInt(CommonParm
						.getString("PAGE_SIZE")));
				pageBean.setPageNo(Integer.parseInt(pageNo.equals("") ? "1"
						: pageNo));
				String sql = " select a.ID,a.`NAME`,a.ENROL, c.status ,b.pid from MWPM_BUSS_ENTINFO a, MWPM_BUSS_PATROL_ENTINFO b, MWPM_BUSS_PATROL c where a.id=b.eid and b.pid=c.id ";
				if (!userid.equals("") && reseauid.equals("")) {
					sql += "and reseauid in (select a.ID from mwpm_sys_reseau a, MWPM_SYS_UNITUSER b where a.unitid = b.unitid  and b.userid='"
							+ userid + "')";
				}else if(userid.equals("") && !reseauid.equals("")){
					sql += " and reseauid='"+reseauid + "'";
				}
				String[][] likeValues = new String[6][2];
				likeValues[0][0] = "enrol";
				likeValues[0][1] = enrol;
				likeValues[1][0] = "name";
				likeValues[1][1] = name;
				likeValues[2][0] = "member";
				likeValues[2][1] = member;
				likeValues[3][0] = "address";
				likeValues[3][1] = address;
				likeValues[4][0] = "earcap";
				likeValues[4][1] = earcap;
				likeValues[5][0] = "workarea";
				likeValues[5][1] = workarea;
				sql = StringUtil.contactLikeHql(sql, likeValues);
				sql += " LIMIT "
						+ (pageBean.getPageNo() * pageBean.getPageSize() - pageBean
								.getPageSize()) + "," + pageBean.getPageSize();
				List list = ceiDictDao.getListByNativeSql(sql);
				if (list != null && list.size() > 0)
					xmlMessage = JSONArray.fromObject(list).toString();
				else
					xmlMessage = "{'errorcode':'" + // �����ݷ��ر�־λ
							ErrorCode.NODATA_FLAG + "'}";
			} catch (Exception e) {
				xmlMessage = "{'errorcode':'" + // �ͻ��˴������󷵻ر�־λ
						ErrorCode.EXPSERVER_FLAG + "'}";
				e.printStackTrace();
			}
		} else {
			xmlMessage = "{'errorcode':'" + // �ͻ��˴������󷵻ر�־λ
					ErrorCode.EXPCLIENT_FLAG + "'}";
		}
		return xmlMessage;
	}

	@Override
	public String queryUncheckedPatrolReport(String xml) {
		String error = "";
		String xmlMessage = "";

		return xmlMessage;
	}

	@Override
	public String queryEntByLatLong(String xml) {
		String error = "";
		String xmlMessage = "";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String lats = xmlStrMap.get("lats") == null ? "" : xmlStrMap
					.get("lats");
			String late = xmlStrMap.get("late") == null ? "" : xmlStrMap
					.get("late");
			String longs = xmlStrMap.get("longs") == null ? "" : xmlStrMap
					.get("longs");
			String longe = xmlStrMap.get("longe") == null ? "" : xmlStrMap
					.get("longe");
			String pageNo = xmlStrMap.get("pageno") == null ? "" : xmlStrMap
					.get("pageno");
			PageBean pageBean = new PageBean();
			pageBean.setPageSize(Integer.parseInt(CommonParm
					.getString("PAGE_SIZE")));
			pageBean.setPageNo(Integer.parseInt(pageNo.equals("") ? "1"
					: pageNo));
			try {
				String sql = "SELECT * FROM MWPM_BUSS_ENTINFO where "
						+ "lat  is not null " + "and long1 is not null "
						+ "and lat >= " + lats + " and lat<= " + late
						+ " and long1 >= " + longs + " and long1<=" + longe;
		/*		sql += " LIMIT "
						+ (pageBean.getPageNo() * pageBean.getPageSize() - pageBean
								.getPageSize()) + "," + pageBean.getPageSize();*/
				List<MwpmBussEntinfo> list = ceiDictDao.getListByNativeSql(sql);
				if (list != null && list.size() > 0) {
					xmlMessage = JSONArray.fromObject(list).toString();
				} else {
					xmlMessage = "{'errorcode':'" + // û�����ݷ��ر�־λ
							ErrorCode.NODATA_FLAG + "'}";
				}
			} catch (Exception e) {
				xmlMessage = "{'errorcode':'" + // ����˴��󷵻ر�־λ
						ErrorCode.EXPSERVER_FLAG + "'}";
				e.printStackTrace();
			}
		} else {
			xmlMessage = "{'errorcode':'" + // �ͻ��˴������󷵻ر�־λ
					ErrorCode.EXPCLIENT_FLAG + "'}";
		}
		return xmlMessage;
	}

	@Override
	public String queryPatrolLoca(String xml) {
		String error = "";
		String xmlMessage = "";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String userid = xmlStrMap.get("userid") == null ? "" : xmlStrMap
					.get("userid");
			String createtimes = xmlStrMap.get("createtimes") == null ? ""
					: xmlStrMap.get("createtimes");
			String createtimee = xmlStrMap.get("createtimee") == null ? ""
					: xmlStrMap.get("createtimee");
			try {
				String sql = "select LAT,LONG1 from "
						+ "MWPM_BUSS_PATROL_LOCA " + "where 1=1 and userid='"
						+ userid + "'";
				if (!createtimee.equals(""))
					sql += " and CREATETIME <="
							+ StringUtil.getCurDate("date", "'" + createtimee
									+ "'", "toData") + "   ";
				if (!createtimes.equals(""))
					sql += " and CREATETIME >="
							+ StringUtil.getCurDate("date", "'" + createtimes
									+ "'", "toData") + "   ";
				sql += "ORDER BY CREATETIME ASC";
				List list = ceiDictDao.getListByNativeSql(sql);
				if (list != null && list.size() > 0)
					xmlMessage = JSONArray.fromObject(list).toString();
				else
					xmlMessage = "{'errorcode':'" + // �����ݷ��ر�־λ
							ErrorCode.NODATA_FLAG + "'}";
			} catch (Exception e) {
				xmlMessage = "{'errorcode':'" + // �ͻ��˴������󷵻ر�־λ
						ErrorCode.EXPSERVER_FLAG + "'}";
				e.printStackTrace();
			}
		} else {
			xmlMessage = "{'errorcode':'" + // �ͻ��˴������󷵻ر�־λ
					ErrorCode.EXPCLIENT_FLAG + "'}";
		}
		return xmlMessage;
	}

	@Override
	public String getUserLevel(String xml) {
		String error = "";
		String xmlMessage = "";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String loginname = xmlStrMap.get("loginname") == null ? ""
					: xmlStrMap.get("loginname");

			if (loginname.equals("")) {
				xmlMessage = "{'errorcode':'" + // �û�������Ϊ�շ��ر�־λ
						ErrorCode.PASSNULL_FLAG + "'}";
				return xmlMessage;
			}
			try {
				String sql = "from MwpmSysUserinfo ";
				sql += "where LOGINNAME='" + loginname + "'";
				List list = ceiDictDao.getALLData(sql);
				if (list != null && list.size() > 0)
					xmlMessage = JSONObject.fromObject(list.get(0)).toString();
				else
					xmlMessage = "{'errorcode':'" + // �û���������󷵻ر�־λ
							ErrorCode.PASSERROR_FLAG + "'}";
			} catch (Exception e) {
				xmlMessage = "{'errorcode':'" + // ����˴��󷵻ر�־λ
						ErrorCode.EXPSERVER_FLAG + "'}";
				e.printStackTrace();
			}
		} else {
			xmlMessage = "{'errorcode':'" + // �ͻ��˴������󷵻ر�־λ
					ErrorCode.EXPCLIENT_FLAG + "'}";
		}
		return xmlMessage;
	}

	/**
	 * ������ҵ����
	 */
	@Override
	public String updateEntEmail(String xml) {
		String error = "";
		String xmlMessage = "";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String id = xmlStrMap.get("id") == null ? "0" : xmlStrMap.get("id");
			String email = xmlStrMap.get("email") == null ? "" : xmlStrMap
					.get("email");
			try {
				String hql = "update MwpmBussEntinfo set email='" + email + "'";
				hql = hql + " where id ='" + id + "'";
				ceiDictDao.updateXYInfo(hql);
			} catch (Exception e) {
				xmlMessage = "{'errorcode':'" + // ������쳣
						ErrorCode.EXPSERVER_FLAG + "'}";
				e.printStackTrace();
			}
		} else {
			xmlMessage = "{'errorcode':'" + // ������쳣
					ErrorCode.EXPCLIENT_FLAG + "'}";
		}
		return xmlMessage;
	}

	@Override
	public String queryEntListByemail(String xml) {
		String error = "";
		String xmlMessage = "";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String reseauid = xmlStrMap.get("reseauid") == null ? ""
					: xmlStrMap.get("reseauid");
			String pageNo = xmlStrMap.get("pageno") == null ? "" : xmlStrMap
					.get("pageno");
			try {

				String hql = "from MwpmBussEntinfo  where 1=1 ";
				if (!reseauid.equals("")) {
					hql += "and reseauid='" + reseauid + "'";
				}
				PageBean pageBean = new PageBean();
				pageBean.setPageSize(Integer.parseInt(CommonParm
						.getString("PAGE_SIZE")));
				pageBean.setPageNo(Integer.parseInt(pageNo.equals("") ? "1"
						: pageNo));
				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig.registerJsonValueProcessor(java.util.Date.class,
						new JsonDateValueProcessor());
				List<MwpmBussEntinfo> list = ceiDictDao.getPageBeanList(
						pageBean, hql);
				if (list != null && list.size() > 0)
					xmlMessage = JSONArray.fromObject(list, jsonConfig)
							.toString();
				else
					xmlMessage = "{'errorcode':'" + // �û���������󷵻ر�־λ
							ErrorCode.NODATA_FLAG + "'}";
			} catch (Exception e) {
				xmlMessage = "{'errorcode':'" + // ����˴��󷵻ر�־λ
						ErrorCode.EXPSERVER_FLAG + "'}";
				e.printStackTrace();
			}
		} else {
			xmlMessage = "{'errorcode':'" + // �ͻ��˴������󷵻ر�־λ
					ErrorCode.EXPCLIENT_FLAG + "'}";
		}
		return xmlMessage;
	}

	@Override
	public String sendEmail(String xml) {
		String error = "";
		String xmlMessage = "<?xml version='1.0' encoding='utf-8'?><ROOT>";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String email = xmlStrMap.get("email") == null ? "" : xmlStrMap
					.get("email");
			String content = xmlStrMap.get("content") == null ? "" : xmlStrMap
					.get("content");
			try {
				EmailUtil sendemail = new EmailUtil();
				if (email != null && email.length() > 0) {
					String[] s = email.split(";");
					for (int i = 0; i < s.length; i++) {
						sendemail.sendTextMail(sender, s[i], content);
					}
					xmlMessage += "<RETURNCODE>2</RETURNCODE>";
				} else {
					xmlMessage += "<RETURNCODE>5</RETURNCODE>";
				}
			} catch (Exception e) {
				e.printStackTrace();
				xmlMessage += "<RETURNCODE>0</RETURNCODE>";
			}
		} else {
			xmlMessage += "<RETURNCODE>-1</RETURNCODE>";
		}
		xmlMessage += "</ROOT>";
		return xmlMessage;
	}

	@Override
	public String queryPType(String xml) {
		String xmlMessage = "";
		try {
			String hql = "from MwpmBussPType";
			List<MwpmBussPatrolItem> list = ceiDictDao.getALLData(hql);
			if (list != null && list.size() > 0) {
				xmlMessage = JSONArray.fromObject(list).toString();
			} else {
				xmlMessage = "{'errorcode':'" + // û�����ݷ��ر�־λ
						ErrorCode.NOROLE_FLAG + "'}";
			}
		} catch (Exception e) {
			xmlMessage = "{'errorcode':'" + // ����˴��󷵻ر�־λ
					ErrorCode.EXPSERVER_FLAG + "'}";
			e.printStackTrace();
		}

		return xmlMessage;
	}

	@Override
	public String getPhoneIpadByUserId(String xml) {
		String error = "";
		String xmlMessage = "";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String userid = xmlStrMap.get("userid") == null ? "" : xmlStrMap
					.get("userid");
			try {
				List list = ceiDictDao.getPhoneIpadByUserId(userid);
				if (list != null && list.size() > 0)
					xmlMessage = JSONArray.fromObject(list).toString();
				else
					xmlMessage = "{'errorcode':'" + // �û���������󷵻ر�־λ
							ErrorCode.NODATA_FLAG + "'}";
			} catch (Exception e) {
				xmlMessage = "{'errorcode':'" + // ����˴��󷵻ر�־λ
						ErrorCode.EXPSERVER_FLAG + "'}";
				e.printStackTrace();
			}
		} else {
			xmlMessage = "{'errorcode':'" + // �ͻ��˴������󷵻ر�־λ
					ErrorCode.EXPCLIENT_FLAG + "'}";
		}
		return xmlMessage;
	}

	@Override
	public String savePhoneIpadByUserId(String xml) {
		String error = "";
		String xmlMessage = "{'errorcode':'" + // ����˴��󷵻ر�־λ
				ErrorCode.ALDATA_FLAG + "'}";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String userid = xmlStrMap.get("userid") == null ? "" : xmlStrMap
					.get("userid");
			String deviceid = xmlStrMap.get("deviceid") == null ? ""
					: xmlStrMap.get("deviceid");
			String type = xmlStrMap.get("type") == null ? "" : xmlStrMap
					.get("type");
			try {
				List<MwpmSysPhoneIpad> list = ceiDictDao
						.getPhoneIpadByUserId(userid);
				MwpmSysPhoneIpad mwpmSysPhoneIpad = new MwpmSysPhoneIpad();
				mwpmSysPhoneIpad.setType(type);
				mwpmSysPhoneIpad.setUserid(userid);
				mwpmSysPhoneIpad.setCode(deviceid);
				List listSize = ceiDictDao.getPhoneIpadByDeviceId(deviceid,type);
				if (listSize == null || listSize.size() == 0) {
					if (list == null || list.size() == 0) {
						ceiDictDao.saveObject(mwpmSysPhoneIpad);
					} else {
						boolean isSave = true;
						for (int i = 0; i < list.size(); i++) {
							if (list.get(i).getType().equals(type)) {
								isSave = false;
							}
						}
						if (isSave) {
							ceiDictDao.saveObject(mwpmSysPhoneIpad);
						}
					}
				} else {
					xmlMessage = "{'errorcode':'" + // ����˴��󷵻ر�־λ
							ErrorCode.DEVICEEXIST_FLAG + "'}";
				}
			} catch (Exception e) {
				xmlMessage = "{'errorcode':'" + // ����˴��󷵻ر�־λ
						ErrorCode.EXPSERVER_FLAG + "'}";
				e.printStackTrace();
			}
		} else {
			xmlMessage = "{'errorcode':'" + // �ͻ��˴������󷵻ر�־λ
					ErrorCode.EXPCLIENT_FLAG + "'}";
		}
		return xmlMessage;
	}

	@Override
	public String savePType(String xml) {
		String error = "";
		String xmlMessage = "<?xml version='1.0' encoding='utf-8'?>" + "<ROOT>";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			// Ѳ���¼��
			String lid = xmlStrMap.get("lid") == null ? "" : xmlStrMap
					.get("lid");
			String types = xmlStrMap.get("types") == null ? "" : xmlStrMap
					.get("types");

			try {
				String[] tps = types.split(",");
				for (int i = 0; i < tps.length; i++) {
					MwpmBussPatrolType type = new MwpmBussPatrolType();
					type.setLid(lid);
					type.setTid(tps[i]);
					ceiDictDao.saveObject(type);
				}

				xmlMessage += "<RETURNCODE>2</RETURNCODE>";
			} catch (Exception e) {
				e.printStackTrace();
				xmlMessage += "<RETURNCODE>0</RETURNCODE>";
			}
		} else {
			xmlMessage += "<RETURNCODE>-1</RETURNCODE>";
		}
		xmlMessage += "</ROOT>";
		return xmlMessage;
	}

	@Override
	public String getRoleTypeByUserId(String xml) {
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String error = "";
		String xmlMessage = "{'errorcode':'" + // ����˴��󷵻ر�־λ
				ErrorCode.ALDATA_FLAG + "'}";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String userid = xmlStrMap.get("userid") == null ? "" : xmlStrMap
					.get("userid");
			try {
				List list = ceiDictDao.getRoleTypeByUserId(userid);
				if (list == null || list.size() == 0) {
					xmlMessage = "{'errorcode':'" + // ����˴��󷵻ر�־λ
							ErrorCode.NODATA_FLAG + "'}";
				} else {
					xmlMessage = JSONArray.fromObject(list).toString();
				}
			} catch (Exception e) {
				xmlMessage = "{'errorcode':'" + // ����˴��󷵻ر�־λ
						ErrorCode.EXPSERVER_FLAG + "'}";
				e.printStackTrace();
			}
		} else {
			xmlMessage = "{'errorcode':'" + // �ͻ��˴������󷵻ر�־λ
					ErrorCode.EXPCLIENT_FLAG + "'}";
		}
		return xmlMessage;
	}

	@Override
	public String getReseaNameById(String xml) {
		String error = "";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String id = xmlStrMap.get("id") == null ? "" : xmlStrMap
					.get("id");
			try {
				MwpmSysReseau mwpmSysReseau = (MwpmSysReseau) ceiDictDao.getALLData("from MwpmSysReseau u where u.id='"+id+"'").get(0);
				return mwpmSysReseau.getName();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "";
	}
}
