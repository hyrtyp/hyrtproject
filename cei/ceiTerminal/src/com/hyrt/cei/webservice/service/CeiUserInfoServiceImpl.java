package com.hyrt.cei.webservice.service;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.orm.hibernate3.HibernateQueryException;

import com.hyrt.cei.vo.MwpmPortalIndex;
import com.hyrt.cei.vo.MwpmSysClass;
import com.hyrt.cei.vo.MwpmSysForumFollow;
import com.hyrt.cei.vo.MwpmSysImsicode;
import com.hyrt.cei.vo.MwpmSysLoginlog;
import com.hyrt.cei.vo.MwpmSysNewCollect;
import com.hyrt.cei.vo.MwpmSysOperationlog;
import com.hyrt.cei.vo.MwpmSysRoleuser;
import com.hyrt.cei.vo.MwpmSysUpdateloadPackage;
import com.hyrt.cei.vo.MwpmSysUserBusinessCourse;
import com.hyrt.cei.vo.MwpmSysUserClassTime;
import com.hyrt.cei.vo.MwpmSysUserinfo;
import com.hyrt.cei.webservice.dao.CeiDictDao;
import com.hyrt.cei.webservice.dao.ClassExportXmlDao;
import com.hyrt.mwpm.util.CommonParm;
import com.hyrt.mwpm.util.DesUtils;
import com.hyrt.mwpm.util.EmailUtil;
import com.hyrt.mwpm.util.GridStatic;
import com.hyrt.mwpm.util.NekoHtmlUtil;
import com.hyrt.mwpm.util.PageBean;
import com.hyrt.mwpm.util.ReadOperationXML;
import com.hyrt.mwpm.util.ReadXML;

public class CeiUserInfoServiceImpl implements CeiUserInfoService{

	private static Logger log = Logger.getLogger(CeiUserInfoServiceImpl.class);

	//relative path的定义
	public  final static String RELATIVEPATH = "http://localhost:8080/";
	
	private CeiDictDao ceiDictDao;

	private JavaMailSender sender;
	
	private ClassExportXmlDao classExportXmlDao;

	public JavaMailSender getSender() {
		return sender;
	}



	public void setSender(JavaMailSender sender) {
		this.sender = sender;
	}



	public CeiDictDao getCeiDictDao() {
		return ceiDictDao;
	}
	


	public void setCeiDictDao(CeiDictDao ceiDictDao) {
		this.ceiDictDao = ceiDictDao;
	}


	public ClassExportXmlDao getClassExportXmlDao() {
		return classExportXmlDao;
	}



	public void setClassExportXmlDao(ClassExportXmlDao classExportXmlDao) {
		this.classExportXmlDao = classExportXmlDao;
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
									 allData = ceiDictDao.getInfo(hql);
								}else{
									 allData = ceiDictDao.getInfo(hql);
								}
								//测试list的值
								System.out.println("allData------------------------"+allData);
									
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

						List allData = new ArrayList();
//						allData = ientinfoDao.getResultData(
//								hql, pageBean, hqlcount);
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
						e1.printStackTrace();
					}
					}
					if(!("").equals(path)){
						try {
							NekoHtmlUtil.extractTextFromHTML(path.replaceAll("&nbsp", ""), linkMap);
						} catch (UnsupportedEncodingException e) {
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
	public String queryXfgwLists(String xml) {
		Map array = new HashMap();
		// 解析传入的XML格式的文件
		log.debug("保存信息 传入  xml --------------------" + xml);
		String errorMessage = ReadOperationXML.parseInputDataXml(xml, array);
		if (errorMessage.length() == 0) {
			MwpmSysUserinfo user = new MwpmSysUserinfo();
			setXfgwValues(user,array);
			//保存处理公文回复表
//			ientinfoDao.saveObject(orl);
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

	
	private void setXfgwValues(MwpmSysUserinfo user,Map array) {
	}
	
	/**
	 * 终端首次登录接口
	 * @author tanJie
	 */
	@Override
	public String loginUserInfo(String xml) {
		String error = "";
		String xmlMessage = "<?xml version='1.0' encoding='utf-8'?>" + "<ROOT>";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String userId = "";
			Map map = new HashMap();
			String loginname = xmlStrMap.get("loginname")==null?"":xmlStrMap.get("loginname");
			String password = xmlStrMap.get("password")==null?"":xmlStrMap.get("password");
			String imageType = xmlStrMap.get("imagetype")==null?"pad":xmlStrMap.get("imagetype");
			String imsicode = xmlStrMap.get("imsicode")==null?"":xmlStrMap.get("imsicode");
			String imsitype = xmlStrMap.get("imsitype")==null?"":xmlStrMap.get("imsitype");
			int num = 0;
			String login = "";
			String background = "";
			String wcolor = "";
			//default version code
			String functionId = CommonParm.getString("FUNCTION_ID");
			String fulladdressPath = CommonParm.getString("FULL_ADDRESS_PATH");
			String paotoPath = CommonParm.getString("TEL_PHOTO_PATH");
			try {
				DesUtils d=new DesUtils();
				loginname = d.encrypt(loginname);
				password = d.encrypt(password);
				//查处用户组织ID、用户所属版本ID、组织名称
				List userInfoList = ceiDictDao.getInfo("select f.userid as userid,f.loginname as loginname,f.password as password,f.unitid as unitid,f.FIRSTLOGINTIME as firstlogintime," +
						"f.functionid as functionid,f.UNITNAME as unitname" +
						" from MWPM_SYS_USERINFO f " +
						"where 1=1 and f.status='0' and f.loginname='"+loginname+"' and f.password='"+password+"'");
				if(userInfoList==null || userInfoList.size()==0){
					xmlMessage += "<RETURNCODE>1</RETURNCODE>"; //登录帐号或密码错误	
				}else{
						map = (HashMap)userInfoList.get(0);
						functionId = map.get("functionid")==null?functionId:(String)map.get("functionid");
						userId = map.get("userid")==null?"000001":(String)map.get("userid");
							//设备号比对
							String sql = "select id as id,userid as userid,imsicode as imsicode from MWPM_SYS_IMSICODE " +
									"where 0=0  and type='"+imsitype+"' and (imsicode='"+imsicode+"' and userid='"+userId+"')";
							List imsicodeList = ceiDictDao.getInfo(sql);
							if(imsicodeList!=null && imsicodeList.size()>0){
								num = 1;
								/*if(imsicodeList.size()==1){//如果返回记录为1，且设备号和用户名比对相同， 则设备号比对成功；返回多条才为设备号比对不成功
									HashMap imsiMap = (HashMap)imsicodeList.get(0);
									String imsiuserid = imsiMap.get("userid").toString();
									String imsicodeOld = imsiMap.get("imsicode").toString();
									if(imsicodeOld.equals(imsicode) && imsiuserid.equals(userId)){
										num = 1;
									}else{
										num = 0;
										xmlMessage += "<RETURNCODE>2</RETURNCODE>"; //设备编号不对
										functionId = CommonParm.getString("FUNCTION_ID");
										userId = CommonParm.getString("LOGIN_USER_ID");
									}
								}else{
									num = 0;
									xmlMessage += "<RETURNCODE>2</RETURNCODE>"; //设备编号不对
									functionId = CommonParm.getString("FUNCTION_ID");
									userId = CommonParm.getString("LOGIN_USER_ID");
								}*/
							}else{
								if(imsitype != null && imsitype.length() >0){
									String repeatDeviceSql = "select * from MWPM_SYS_IMSICODE where " +
											"(type like '%"+imsitype.substring(1)+"%'"+" and userid='"+userId+"') or imsicode='"+imsicode+"'";
									List deviceList = ceiDictDao.getInfo(repeatDeviceSql);
									if(deviceList != null && deviceList.size() > 0){
										xmlMessage += "<RETURNCODE>2</RETURNCODE>"; //设备编号不对
										num = 0;
									}else{
										num = 1;
										MwpmSysImsicode ic = new MwpmSysImsicode();
										ic.setImsicode(imsicode);
										ic.setUserid(map.get("userid").toString());
										ic.setType(imsitype);
										ic.setStatus("0");
										//为了测试用例，记录设备号的接口暂时关闭
										ceiDictDao.saveObject(ic);
									}
								}
								
								
							}
							if(num==1){
								xmlMessage += "<user>"; 
								xmlMessage += "<userid>"+map.get("userid").toString()+"</userid>"; 
								//登录成功修改用户表的登录状态（TIME）
								String firstlogintime = map.get("firstlogintime")==null?"":map.get("firstlogintime").toString();
								String hql = "update MwpmSysUserinfo set userid='"+map.get("userid").toString()+"' " ;
								//判断是否首次登录
								if(firstlogintime.equals("")){
									Date date = new Date();
									SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
									hql = hql+",firstlogintime='"+sdf.format(date)+"'";
								}
								hql = hql+	" where userid='"+map.get("userid").toString()+"'";
								ceiDictDao.updateXYInfo(hql);
								
								//查询组织名称
								List orgList = ceiDictDao.getALLData("select new map(orgname as orgname) from MwpmSysOrginfo where id='"+map.get("unitid").toString()+"'");
								String orgname = "";
								if(orgList!=null && orgList.size()>0){
									HashMap omap = (HashMap)orgList.get(0);
									orgname = map.get("orgname")==null?"":map.get("orgname").toString();
								}
								
							    //保存用户登录日志
								MwpmSysLoginlog l = new MwpmSysLoginlog();
								String unitname = "";
								orgname = d.decrypt(orgname);
								loginname = d.decrypt(map.get("loginname").toString());
								l.setUsercode(map.get("userid").toString());
								l.setUsername(loginname);
								l.setUnitname(orgname);
								l.setDeviceid(imsicode);
								l.setLogintime(new Date());
								l.setLogintype("0");
								ceiDictDao.saveObject(l);
								xmlMessage += "<loginid>"+l.getLoginid()+"</loginid>"; //返回登录日志id
								xmlMessage += "</user>"; 
								
							}
				}
				
				
				//根据版本号与设备类型获取版本样式
				String hqlcss = "select new map(login as login,background as background,wcolor as wcolor,issuetime as issuetime)" +
						" from MwpmSysPhoneCss where 1=1 and functionid='"+functionId+"'";
				if(imageType.equals("pad")){
					hqlcss = hqlcss + " and type='pad'";
				}else{
					hqlcss = hqlcss + " and type='tel'";
				}
				List phoneCss = ceiDictDao.getALLData(hqlcss);
				if(phoneCss!=null && phoneCss.size()>0){
					HashMap phoneCssMap = (HashMap)phoneCss.get(0);
					login = phoneCssMap.get("login")==null?"":(String)phoneCssMap.get("login");//登陆logo图标
					background = phoneCssMap.get("background")==null?"":(String)phoneCssMap.get("background");//背景图片
					wcolor = phoneCssMap.get("wcolor")==null?"":(String)phoneCssMap.get("wcolor");//字体颜色
				}
				if(!login.equals("")){
					login = fulladdressPath+paotoPath+login;
				}
				if(!background.equals("")){
					background = fulladdressPath+paotoPath+background;
				}
//				xmlMessage += "<login>"+login+"</login>"; 
				xmlMessage += "<background>"+background+"</background>"; 
				xmlMessage += "<wcolor>"+wcolor+"</wcolor>"; 
				
				//根据版本号获取折扣率
				String agiorate = "";
				List aglist = ceiDictDao.getInfo("select f.agiorate as agiorate " +
						" from  MWPM_SYS_PHONE_FUNCTION f where f.id='"+functionId+"'");
				if(aglist!=null && aglist.size()>0){
					HashMap agmap = (HashMap)aglist.get(0);
					agiorate = agmap.get("agiorate")==null?"":agmap.get("agiorate").toString();
				}
				xmlMessage += "<agiorate>"+agiorate+"</agiorate>"; 
				//根据版本号获取版本信息（所有授权的functionid）
				List list = new ArrayList();
				list = ceiDictDao.getInfo("select f.id as id,f.path as path " +
						" from MWPM_SYS_SECURITYACL s left join MWPM_SYS_PHONE_FUNCTION f on s.FUNCTIONID=f.ID" +
						" left join MWPM_SYS_ROLEUSER r on r.ROLEID=s.ROLEID where r.userid='"+userId+"'");
				
				
				//当list结果为空时，默认给通用版角色的权限；40288a7838dbbe950138dbedb75f003c为通用版下的用户id (曹佳)
				if(list==null || list.size()==0){
					userId = CommonParm.getString("LOGIN_USER_ID");
					list = ceiDictDao.getInfo("select f.id as id,f.path as path " +
							" from MWPM_SYS_SECURITYACL s left join MWPM_SYS_PHONE_FUNCTION f on s.FUNCTIONID=f.ID" +
							" left join MWPM_SYS_ROLEUSER r on r.ROLEID=s.ROLEID where r.userid='"+userId+"'");
				}
				
				//组织function结构
					String ids = "";
					Map map2 = new HashMap();
					List list1 = new ArrayList();
					for (int j = 0; j < list.size(); j++) {
						Map map1 = (HashMap)list.get(j);
						String a = map1.get("id").toString();
						String b = map1.get("path").toString();
						String[] c = b.split("//");
						for (int k = 0; k < c.length; k++) {
							if(map2.get(c[k]) == null){
								if(!c[k].equals("")){
									map2.put(c[k], c[k]);
									ids = ids+"'"+c[k]+"',";
								}
							}
							if(map2.get(a) == null){
								if(!a.equals("")){
									map2.put(a, a);
									ids = ids+"'"+a+"',";
								}
							}
						}
					}
					
					//根据授权的functionid结构获取所有的栏目信息
					List phoneFunctionList = ceiDictDao.getInfo("select id as id,name as name,description as description,parentid as parentid,path as path," +
							"type as type,operationimage as operationimage,issuetime as issuetime,ipadimage as ipadimage,ipadImageTime as ipadImageTime,agiorate as agiorate" +
							" from MWPM_SYS_PHONE_FUNCTION where 1=1 and id in ("+ids.substring(0, ids.length()-1)+")");
					if(phoneFunctionList!=null && phoneFunctionList.size()>0){
						xmlMessage += "<phonefunctions>"; 
						for (int j = 0; j < phoneFunctionList.size(); j++) {
							HashMap phoneFunctionMap = (HashMap)phoneFunctionList.get(j);
							String id = phoneFunctionMap.get("id")==null?"":(String)phoneFunctionMap.get("id");
							String name = phoneFunctionMap.get("name")==null?"":(String)phoneFunctionMap.get("name");
							String parentid = phoneFunctionMap.get("parentid")==null?"":(String)phoneFunctionMap.get("parentid");
							String description = phoneFunctionMap.get("description")==null?"":(String)phoneFunctionMap.get("description");
							String path = phoneFunctionMap.get("path")==null?"":(String)phoneFunctionMap.get("path");
							String type = phoneFunctionMap.get("type")==null?"":(String)phoneFunctionMap.get("type");
							String operationimage = phoneFunctionMap.get("operationimage")==null?"":(String)phoneFunctionMap.get("operationimage");
							Date issuetime = (Date) phoneFunctionMap.get("issuetime");
							String ipadimage = phoneFunctionMap.get("ipadimage")==null?"":(String)phoneFunctionMap.get("ipadimage");
							Date ipadImageTime = (Date) phoneFunctionMap.get("ipadImageTime");
							String time1 = "";
							String time2 = "";
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							if(issuetime!=null){
								time1 = sdf.format(issuetime);
							}
							if(ipadImageTime!=null){
								time2 = sdf.format(ipadImageTime);
							}
							xmlMessage += "<phonefunction>"; 
							xmlMessage += "<id>"+id+"</id>"; 
							xmlMessage += "<name>"+name+"</name>"; 
							xmlMessage += "<parentid>"+parentid+"</parentid>"; 
							xmlMessage += "<path>"+path+"</path>"; 
							xmlMessage += "<description>"+description+"</description>"; 
							xmlMessage += "<type>"+type+"</type>"; 
							if(imageType.equals("pad")){
								xmlMessage += "<operationimage>"+fulladdressPath+paotoPath+ipadimage+"</operationimage>"; 
								xmlMessage += "<issuetime>"+time2+"</issuetime>";
							}else{
								xmlMessage += "<operationimage>"+fulladdressPath+paotoPath+operationimage+"</operationimage>"; 
								xmlMessage += "<issuetime>"+time1+"</issuetime>";
							}
							xmlMessage += "</phonefunction>"; 
						
						}
						xmlMessage += "</phonefunctions>"; 
					}
				
			} catch (Exception e) {
				xmlMessage += "<RETURNCODE>-2</RETURNCODE>";
				xmlMessage += "</ROOT>";
				e.printStackTrace();
				return xmlMessage;
			}
		} else {
			xmlMessage += "<RETURNCODE>-1</RETURNCODE>";
		}
		xmlMessage += "</ROOT>";
		return xmlMessage;
	}
	
	
	/**
	 * 查询用户详细信息
	 * @author tanJie
	 */
	@Override
	public String queryUserInfo(String xml) {
		String error = "";
		String xmlMessage = "<?xml version='1.0' encoding='utf-8'?>" + "<ROOT>";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String userid = xmlStrMap.get("userid")==null?"":xmlStrMap.get("userid");
			List userInfoList = ceiDictDao.getInfo("select u.name as name,u.email as email,u.phone as phone,u.unitname as unitname,a.integral as integral,u.loginname as loginname,u.sex as sex,u.certype as certype,u.cardno as cardno " +
					"from MWPM_SYS_USERINFO u left join MWPM_SYS_ACCOUNTINFO a on u.userid=a.userid where 1=1 and u.userid='"+userid+"'");
			if(userInfoList!=null && userInfoList.size()>0){
				HashMap map = (HashMap)userInfoList.get(0);
				String name = map.get("name")==null?"":(String)map.get("name");
				String email = map.get("email")==null?"":(String)map.get("email");
				String phone = map.get("phone")==null?"":(String)map.get("phone");
				String unitname = map.get("unitname")==null?"":(String)map.get("unitname");
				String integral = map.get("integral")==null?"":""+map.get("integral");
				String loginname = map.get("loginname")==null?"":(String)map.get("loginname");
				String certype = map.get("certype")==null?"":""+map.get("certype");
				String cardno = map.get("cardno")==null?"":""+map.get("cardno");
				String sex = map.get("sex")==null?"":""+map.get("sex");
				if(sex!=null && sex.length()>0){
					if(sex.equals("1")){
						sex="女";
					}else{
						sex="男";
					}
				}
				if(certype!=null && certype.length()>0){
					if(certype.equals("0")){
						certype="身份证";
					}else if(certype.equals("1")) {
						certype="学生证";
					}else if(certype.equals("2")) {
						certype="工作证";
					}else if(certype.equals("3")) {
						certype="士兵证";
					}else if(certype.equals("4")) {
						certype="军官证";
					}else if(certype.equals("5")) {
						certype="护照";
					}
				}
				try {
					DesUtils d=new DesUtils();
					name = d.decrypt(name);
					unitname = d.decrypt(unitname);
					loginname = d.decrypt(loginname);
					cardno = d.decrypt(cardno);
					email = d.decrypt(email);
					phone = d.decrypt(phone);
				} catch (Exception e) {
					xmlMessage += "<RETURNCODE>-1</RETURNCODE>";
					e.printStackTrace();
				}
				xmlMessage += "<user>";
				xmlMessage += "<userid>"+userid+"</userid>";
				xmlMessage += "<name>"+name+"</name>";
				xmlMessage += "<loginname>"+loginname+"</loginname>";
				xmlMessage += "<sex>"+sex+"</sex>";
				xmlMessage += "<email>"+email+"</email>";
				xmlMessage += "<phone>"+phone+"</phone>";
				xmlMessage += "<unitname>"+unitname+"</unitname>";
				xmlMessage += "<integral>"+integral+"</integral>";
				xmlMessage += "<certype>"+certype+"</certype>";
				xmlMessage += "<cardno>"+cardno+"</cardno>";
				xmlMessage += "</user>";
			}
		} else {
			xmlMessage += "<RETURNCODE>-1</RETURNCODE>";
		}
		xmlMessage += "</ROOT>";
		return xmlMessage;
	}
	
	

	/**
	 * 查询智慧海详细业务
	 * @author tanJie
	 */
	@Override
	public String queryBrightness(String xml) {
		String error = "";
		String xmlMessage = "<?xml version='1.0' encoding='utf-8'?>" + "<ROOT>";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String functionid = xmlStrMap.get("functionid")==null?"":xmlStrMap.get("functionid");
			String imageType = xmlStrMap.get("imageType")==null?"":xmlStrMap.get("imageType");
			String fulladdressPath = CommonParm.getString("FULL_ADDRESS_PATH");
			String paotoPath = CommonParm.getString("PHOTO_PATH");
			List brightnessList = ceiDictDao.getInfo("select b.funid as funid,f.name as name,f.operationimage as operationimage," +
					"f.parentid as parentid,f.issuetime as issuetime,f.ipadimage as ipadimage,f.ipadImageTime as ipadImageTime" +
					" from MWPM_SYS_BRIGHTNESS b left join MWPM_SYS_PHONE_FUNCTION f on b.funid=f.id where 1=1 and b.functionid='"+functionid+"'");
			if(brightnessList!=null && brightnessList.size()>0){
				System.out.println(brightnessList);
				xmlMessage += "<brightness>";
				for (int i = 0; i < brightnessList.size(); i++) {
					HashMap map = (HashMap)brightnessList.get(i);
					String funid = map.get("funid")==null?"":(String)map.get("funid");
					String name = map.get("name")==null?"":(String)map.get("name");
					String operationimage = map.get("operationimage")==null?"":(String)map.get("operationimage");
					String parentid = map.get("parentid")==null?"":(String)map.get("parentid");
					Date issuetime = (Date) map.get("issuetime");
					String ipadimage = map.get("ipadimage")==null?"":(String)map.get("ipadimage");
					Date ipadImageTime = (Date) map.get("ipadImageTime");
					String time1 = "";
					String time2 = "";
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					if(issuetime!=null){
						time1 = sdf.format(issuetime);
					}
					if(ipadImageTime!=null){
						time2 = sdf.format(ipadImageTime);
					}
					xmlMessage += "<bright>";
					xmlMessage += "<funid>"+funid+"</funid>";
					xmlMessage += "<name>"+name+"</name>";
					if(imageType.equals("pad")){
						xmlMessage += "<operationimage>"+fulladdressPath+paotoPath+ipadimage+"</operationimage>"; 
						xmlMessage += "<issuetime>"+time2+"</issuetime>";
					}else{
						xmlMessage += "<operationimage>"+fulladdressPath+paotoPath+operationimage+"</operationimage>"; 
						xmlMessage += "<issuetime>"+time1+"</issuetime>";
					}
					if(parentid.equals(functionid)){
						xmlMessage += "<isorder>0</isorder>";
					}else{
						xmlMessage += "<isorder>1</isorder>";
					}
					xmlMessage += "</bright>";
				}
				xmlMessage += "</brightness>";
			}
		} else {
			xmlMessage += "<RETURNCODE>-1</RETURNCODE>";
		}
		xmlMessage += "</ROOT>";
		return xmlMessage;
	}


	/**
	 * 查询列表信息
	 * @author tanJie
	 */
	@Override
	public String queryPhoneFunctionTree(String xml) {
		String error = "";
		String xmlMessage = "<?xml version='1.0' encoding='utf-8'?>" + "<ROOT>";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String id = xmlStrMap.get("id")==null?"":xmlStrMap.get("id");
			String type = xmlStrMap.get("type")==null?"":xmlStrMap.get("type");
			String imagetype = xmlStrMap.get("imagetype")==null?"":xmlStrMap.get("imagetype");
			int index = Integer.parseInt(xmlStrMap.get("index")==null?"1":xmlStrMap.get("index"));
			String fulladdressPath = CommonParm.getString("FULL_ADDRESS_PATH");
			String bookPath = CommonParm.getString("BOOK_ZIP_PATH");
			String LOOK_PATH = CommonParm.getString("LOOK_PATH");
			String photoPath = CommonParm.getString("PHOTO_PATH");
			String bookRecousePath = CommonParm.getString("BOOK_RECOUSE_PATH");
			int PAGING = Integer.parseInt(CommonParm.getString("REPORT_NUM")==null?"0":CommonParm.getString("REPORT_NUM"));
			int CLASSNUM = Integer.parseInt(CommonParm.getString("CLASSNUM")==null?"10000":CommonParm.getString("CLASSNUM"));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(type.equals("")){
				xmlMessage += "<RETURNCODE>0</RETURNCODE>";
			}else{
				if(!id.equals("")){
					if(type.equals("kj")){
//						String sql = "select re.classid as classid,re.ischeck as ischeck,c.name as name,c.teachername as author," +
//						"c.classlength as classlength,c.intro as intro,c.setnum as setnum,c.TIME as creattime,rp.path as path " +
//						"from MWPM_SYS_OPERATI_RESOURCE re left join MWPM_SYS_CLASS c on re.classid=c.id " +
//						"left join MWPM_SYS_RESOURCEPATH rp on re.classid=rp.RESOURCEID " +
//						"where 1=1 and c.status='1' and re.functionid='"+id+"' and rp.type='kj'";
						String sqlpaging = " SELECT TOP "+CLASSNUM+" *  FROM (SELECT ROW_NUMBER() OVER (ORDER BY re.classid) AS RowNumber," +
								"re.classid as classid,re.ischeck as ischeck,c.name as name,c.teachername as author," +
								"c.classlength as classlength,c.intro as intro,c.setnum as setnum,c.TIME as creattime,c.isfree as isfree,rp.path as path,rp.passkey as passkey " +
								//版本与课件对应关系表
								"from MWPM_SYS_OPERATI_RESOURCE re left join MWPM_SYS_CLASS c on re.classid=c.id " +
								//资源下载观看地址
								"left join MWPM_SYS_RESOURCEPATH rp on re.classid=rp.RESOURCEID " +
								"where 1=1 and c.status='1' and re.functionid='"+id+"' and rp.type='kj' " +
								") A WHERE RowNumber > "+CLASSNUM+"*("+index+"-1)  order by a.creattime desc";
						List classList = ceiDictDao.getInfo(sqlpaging);
						if(classList != null && classList.size()>0){
							xmlMessage += "<classgroup>";
							for (int i = 0; i < classList.size(); i++) {
								HashMap map = (HashMap)classList.get(i);
								String classid = map.get("classid")==null?"":(String)map.get("classid");
								String ischeck = map.get("ischeck")==null?"":(String)map.get("ischeck");
								String name = map.get("name")==null?"":(String)map.get("name");
								String author = map.get("author")==null?"":(String)map.get("author");
								String classlength = map.get("classlength")==null?"":(String)map.get("classlength");
								String path = map.get("path")==null?"":(String)map.get("path");
								String intro = map.get("intro")==null?"":(String)map.get("intro");
								String passkey = map.get("passkey")==null?"":(String)map.get("passkey");
								String isfree = map.get("isfree")==null?"":((Character)map.get("isfree")).toString();
								Date creattime1 = (Date) map.get("creattime");
								String creattime = "";
								if(creattime1!=null){
									creattime = sdf.format(creattime1);
								}
								Integer setnum= map.get("setnum")==null?0:(Integer)map.get("setnum");
								String url = "";
								if(imagetype.equals("androidpad")){
									url = "/an1";
								}
								else if(imagetype.equals("ipad")){
									url = "/i1";
								}
								else if(imagetype.equals("android")){
									url = "/an2";
								}else{
									url = "/i2";
								}
								String lookpath=fulladdressPath+bookRecousePath+path+url+LOOK_PATH;
								String downpath=fulladdressPath+bookPath+path+url+".zip";
								xmlMessage += "<class>";
								xmlMessage += "<classid>"+classid+"</classid>";
								xmlMessage += "<ischeck>"+ischeck+"</ischeck>";
								xmlMessage += "<name>"+name+"</name>";
								xmlMessage += "<teachername>"+author+"</teachername>";
								xmlMessage += "<path>"+fulladdressPath+photoPath+path+"</path>";
								xmlMessage += "<classlength>"+classlength+"</classlength>";
								xmlMessage += "<setnum>"+setnum+"</setnum>";
								xmlMessage += "<protime>"+creattime+"</protime>";
								xmlMessage += "<downpath>"+downpath+"</downpath>";
								xmlMessage += "<lookpath>"+lookpath+"</lookpath>";
								xmlMessage += "<intro>"+intro+"</intro>";
								xmlMessage += "<passkey>"+passkey+"</passkey>";
								xmlMessage += "<isfree>"+isfree+"</isfree>";
								xmlMessage += "</class>";
							}
							xmlMessage += "</classgroup>";
						}	
					}else if(type.equals("bg")){
						//int ReportNum = Integer.parseInt(CommonParm.getString("PERORT_NUM")==null?"0":CommonParm.getString("PERORT_NUM"));
						String sql = " SELECT TOP "+PAGING+" *  FROM (SELECT ROW_NUMBER() OVER (ORDER BY re.classid) AS RowNumber," +
								"re.classid as classid,re.ischeck as ischeck,r.name as name,r.AUTHOR as author,r.scroll as scroll,"+
								"r.intro as intro,r.TIME as creattime,r.price as price,r.isfree as isfree,rp.path as path,rp.passkey as passkey "+
								"from MWPM_SYS_OPERATI_RESOURCE re left join MWPM_SYS_REPORT r on re.classid=r.id "+
								"left join MWPM_SYS_RESOURCEPATH rp on re.classid=rp.RESOURCEID "+
								"where 1=1 and r.status='1' and re.functionid='"+id+"' and rp.type='bg'"+
								" ) A  WHERE RowNumber > "+PAGING+"*("+index+"-1)  order by a.creattime desc";;
						List classList = ceiDictDao.getInfo(sql);
						if(classList != null && classList.size()>0){
							xmlMessage += "<reportgroup>";
							for (int i = 0; i < classList.size(); i++) {
								HashMap map = (HashMap)classList.get(i);
								String classid = map.get("classid")==null?"":(String)map.get("classid");
								String ischeck = map.get("ischeck")==null?"":(String)map.get("ischeck");
								String name = map.get("name")==null?"":(String)map.get("name");
								String author = map.get("author")==null?"":(String)map.get("author");
								String path = map.get("path")==null?"":(String)map.get("path");
								String intro = map.get("intro")==null?"":(String)map.get("intro");
								String price = map.get("price")==null?"":(String)map.get("price");
								String scroll = map.get("scroll")==null?"":(String)map.get("scroll");
								String passkey = map.get("passkey")==null?"":(String)map.get("passkey");
								String isfree = map.get("isfree")==null?"":((Character)map.get("isfree")).toString();
								Date creattime1 = (Date) map.get("creattime");
								String creattime = "";
								if(creattime1!=null){
									creattime = sdf.format(creattime1);
								}
								String downpath=fulladdressPath+bookPath+path+"/bg.zip";
								xmlMessage += "<report>";
								xmlMessage += "<classid>"+classid+"</classid>";
								xmlMessage += "<ischeck>"+ischeck+"</ischeck>";
								xmlMessage += "<name>"+name+"</name>";
								xmlMessage += "<teachername>"+author+"</teachername>";
								xmlMessage += "<path>"+fulladdressPath+photoPath+path+"</path>";
								xmlMessage += "<price>"+price+"</price>";
								xmlMessage += "<protime>"+creattime+"</protime>";
								xmlMessage += "<downpath>"+downpath+"</downpath>";
								xmlMessage += "<intro>"+intro+"</intro>";
								xmlMessage += "<scroll>"+scroll+"</scroll>";
								xmlMessage += "<passkey>"+passkey+"</passkey>";
								xmlMessage += "<isfree>"+isfree+"</isfree>";
								xmlMessage += "</report>";
							}
							xmlMessage += "</reportgroup>";
						}	
					}
				}
			}
		} else {
			xmlMessage += "<RETURNCODE>-1</RETURNCODE>";
		}
		xmlMessage += "</ROOT>";
		return xmlMessage;
	}
	

	/**
	 * 保存学习记录
	 * @author tanJie
	 */
	@Override
	public String saveUserClassTime(String xml) {
		String error = "";
		String xmlMessage = "<?xml version='1.0' encoding='utf-8'?>" + "<ROOT>";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String userid = xmlStrMap.get("userid")==null?"":xmlStrMap.get("userid");
			String classid = xmlStrMap.get("classid")==null?"":xmlStrMap.get("classid");
			String time = xmlStrMap.get("time")==null?"0":xmlStrMap.get("time");
			String fld1 = xmlStrMap.get("iscompleted")==null?"0":xmlStrMap.get("iscompleted");
			List list = ceiDictDao.getALLData("select new map(t.time as time) from MwpmSysUserClassTime t where  t.userid='"+userid+"' and  t.classid='"+classid+"'");
			int timeInt = 0;
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (time.contains(".")) {
				timeInt = Integer.parseInt(time.substring(0,time.lastIndexOf(".")));
			} else {
				timeInt = Integer.parseInt(time);
			} 
			try {
				if(list!=null && list.size()>0){
					HashMap map = (HashMap)list.get(0);
					int oldtime =  (Integer) (map.get("time")==null?0:map.get("time"));
					oldtime = oldtime+timeInt;
					String hql ="update MwpmSysUserClassTime set time="+oldtime;
					if(fld1.equals("1")){
						hql = hql + ",fld1='1'";
					}
					hql = hql +",fld2='"+sdf.format(date)+"' where userid='"+userid+"' and classid='"+classid+"'";
					ceiDictDao.updateXYInfo(hql);
					xmlMessage += "<RETURNCODE>1</RETURNCODE>";
				}else{
					MwpmSysUserClassTime uct = new MwpmSysUserClassTime();
					uct.setClassid(classid);
					uct.setUserid(userid);
					uct.setTime(timeInt);
					uct.setFld2(date);
					uct.setFld1(fld1);
					uct.setFirsttime(date);
					ceiDictDao.saveObject(uct);
					xmlMessage += "<RETURNCODE>1</RETURNCODE>";
				}
			} catch (Exception e) {
				xmlMessage += "<RETURNCODE>0</RETURNCODE>";
				e.printStackTrace();
			}
		} else {
			xmlMessage += "<RETURNCODE>-1</RETURNCODE>";
		}
		xmlMessage += "</ROOT>";
		return xmlMessage;
	}


	/**
	 * 查询学习记录
	 * @author tanJie
	 */
	@Override
	public String queryUserClassTime(String xml) {
		String error = "";
		String xmlMessage = "<?xml version='1.0' encoding='utf-8'?>" + "<ROOT>";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String userid = xmlStrMap.get("userid")==null?"":xmlStrMap.get("userid");
			String imagetype = xmlStrMap.get("imagetype")==null?"":xmlStrMap.get("imagetype");
			String fulladdressPath = CommonParm.getString("FULL_ADDRESS_PATH");
			String bookPath = CommonParm.getString("BOOK_ZIP_PATH");
			String LOOK_PATH = CommonParm.getString("LOOK_PATH");
			String photoPath = CommonParm.getString("PHOTO_PATH");
			String bookRecousePath = CommonParm.getString("BOOK_RECOUSE_PATH");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			List list = ceiDictDao.getALLData("select new map(t.firsttime as firsttime,c.id as classid,c.teachername as teachername,c.classlength as classlength," +
					"c.creattime as creattime,c.intro as intro,p.passkey as passkey,c.setnum as setnum,t.time as time,c.name as name,p.path as path,t.fld1 as iscompleted) " +
					"from MwpmSysUserClassTime t,MwpmSysClass c,MwpmSysResourcepath p where t.classid=c.id and c.id=p.resourceid " +
					"and t.userid = '"+userid+"' order by t.fld2 desc");
			String url = "";
			if(imagetype.equals("androidpad")){
				url = "/an1";
			}
			else if(imagetype.equals("ipad")){
				url = "/i1";
			}
			else if(imagetype.equals("android")){
				url = "/an2";
			}else{
				url = "/i2";
			}
			if(list!=null && list.size()>0){
				xmlMessage += "<classtimes>";
				for (int i = 0; i < list.size(); i++) {
					HashMap map = (HashMap)list.get(i);
					String classid = map.get("classid")==null?"":(String)map.get("classid");
					String name = map.get("name")==null?"0":(String)map.get("name");
					String teachername = map.get("teachername")==null?"":(String)map.get("teachername");
					String classlength = map.get("classlength")==null?"":(String)map.get("classlength");
					String intro = map.get("intro")==null?"":(String)map.get("intro");
					String passkey = map.get("passkey")==null?"":(String)map.get("passkey");
					String iscompleted = map.get("iscompleted")==null?"0":(String)map.get("iscompleted");
					int setnum = (Integer) (map.get("setnum")==null?0:map.get("setnum"));
					Date creattime1 = (Date) map.get("creattime");
					String creattime = "";
					if(creattime1!=null){
						creattime = sdf.format(creattime1);
					}
					Date firsttime1 = (Date) map.get("firsttime");
					String firsttime = "";
					if(firsttime1!=null){
						firsttime = sdf.format(firsttime1);
					}
					String path = map.get("path")==null?"":(String)map.get("path");
					int headtime = (Integer) (map.get("time")==null?0:map.get("time"));
					String lookpath=fulladdressPath+bookRecousePath+path+url+LOOK_PATH;
					String downpath=fulladdressPath+bookPath+path+url+".zip";
					xmlMessage += "<classtime>";
					xmlMessage += "<firsttime>"+firsttime+"</firsttime>";
					xmlMessage += "<time>"+headtime+"</time>";
					xmlMessage += "<classid>"+classid+"</classid>";
					xmlMessage += "<name>"+name+"</name>";
					xmlMessage += "<teachername>"+teachername+"</teachername>";
					xmlMessage += "<path>"+fulladdressPath+photoPath+path+"</path>";
					xmlMessage += "<classlength>"+classlength+"</classlength>";
					xmlMessage += "<protime>"+creattime+"</protime>";
					xmlMessage += "<downpath>"+downpath+"</downpath>";
					xmlMessage += "<setnum>"+setnum+"</setnum>";
					xmlMessage += "<lookpath>"+lookpath+"</lookpath>";
					xmlMessage += "<intro>"+intro+"</intro>";
					xmlMessage += "<passkey>"+passkey+"</passkey>";
					xmlMessage += "<iscompleted>"+iscompleted+"</iscompleted>";
					xmlMessage += "</classtime>";
				}
				xmlMessage += "</classtimes>";
			}
		} else {
			xmlMessage += "<RETURNCODE>-1</RETURNCODE>";
		}
		xmlMessage += "</ROOT>";
		return xmlMessage;
	}

	
	
	

	/**
	 * 查询自选课
	 * @author tanJie
	 */
	@Override
	public String queryCourse(String xml) {
		String error = "";
		String xmlMessage = "<?xml version='1.0' encoding='utf-8'?>" + "<ROOT>";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String userid = xmlStrMap.get("userid")==null?"":xmlStrMap.get("userid");
			String buytype = xmlStrMap.get("buytype")==null?"":xmlStrMap.get("buytype");
			String imagetype = xmlStrMap.get("imagetype")==null?"":xmlStrMap.get("imagetype");
			List list = ceiDictDao.getInfo("select c.id as classid,c.name as name,c.CLASSLENGTH as classlength,c.PPATH as ppath," +
 "c.creattime as creattime,c.TEACHERNAME as teachername,c.setnum as setnum,c.intro as intro,c.isfree as isfree,rp.path as path,rp.passkey as passkey " +
					"from MWPM_SYS_CLASS c left join  MWPM_SYS_USER_BUSINESS_COURSE bc on bc.resourceid=c.id " +
					"left join MWPM_SYS_RESOURCEPATH rp on c.id=rp.RESOURCEID " +
					"where rp.type='kj' and bc.buytype='"+buytype+"' and bc.userid='"+userid+"' order by bc.fld1 desc");
			try {
				if(list!=null && list.size()>0){
					String fulladdressPath = CommonParm.getString("FULL_ADDRESS_PATH"); 
					String bookPath = CommonParm.getString("BOOK_ZIP_PATH");
					String bookRecousePath = CommonParm.getString("BOOK_RECOUSE_PATH"); 
					String lookPath = CommonParm.getString("LOOK_PATH"); 
					String photoPath = CommonParm.getString("PHOTO_PATH"); 
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					xmlMessage += "<classgroup>";
					for (int i = 0; i < list.size(); i++) {
						HashMap map = (HashMap)list.get(i);
						String classid = map.get("classid")==null?"":(String)map.get("classid");
						String name = map.get("name")==null?"":(String)map.get("name");
						String teachername = map.get("teachername")==null?"":(String)map.get("teachername");
						String path = map.get("path")==null?"":(String)map.get("path");
						String classlength = map.get("classlength")==null?"":(String)map.get("classlength");
						Integer setnum = map.get("setnum")==null?0:(Integer) map.get("setnum");
						String passkey = map.get("passkey")==null?"":(String)map.get("passkey");
						String intro = map.get("intro")==null?"":(String)map.get("intro");
						String isfree = map.get("isfree")==null?"":((Character)map.get("isfree")).toString();
						String url = "";
						if(imagetype.equals("androidpad")){
							url = "/an1";
						}
						else if(imagetype.equals("ipad")){
							url = "/i1";
						}
						else if(imagetype.equals("android")){
							url = "/an2";
						}else{
							url = "/i2";
						}
						String downpath=fulladdressPath+bookRecousePath+path+url+lookPath;
						String lookpath=fulladdressPath+bookPath+path+url+".zip";
						Date creattime1 = (Date) map.get("creattime");
						String creattime = "";
						if(creattime1!=null){
							creattime = sdf.format(creattime1);
						}
						xmlMessage += "<class>";
						xmlMessage += "<classid>"+classid+"</classid>";
						xmlMessage += "<name>"+name+"</name>";
						xmlMessage += "<teachername>"+teachername+"</teachername>";
						xmlMessage += "<path>"+fulladdressPath+photoPath+path+"</path>";
						xmlMessage += "<classlength>"+classlength+"</classlength>";
						xmlMessage += "<setnum>"+setnum+"</setnum>";
						xmlMessage += "<protime>"+creattime+"</protime>";
						xmlMessage += "<downpath>"+downpath+"</downpath>";
						xmlMessage += "<intro>"+intro+"</intro>";
						xmlMessage += "<lookpath>"+lookpath+"</lookpath>";
						xmlMessage += "<passkey>"+passkey+"</passkey>";
						xmlMessage += "<isfree>"+isfree+"</isfree>";
						xmlMessage += "</class>";
					}
					xmlMessage += "</classgroup>";
				}
			} catch (Exception e) {
				xmlMessage += "<RETURNCODE>0</RETURNCODE>";
			}
		} else {
			xmlMessage += "<RETURNCODE>-1</RETURNCODE>";
		}
		xmlMessage += "</ROOT>";
		return xmlMessage;
	}
	

	/**
	 * 新增自选课
	 * @author tanJie
	 */
	@Override
	public String saveCourse(String xml) {
		String error = "";
		String xmlMessage = "<?xml version='1.0' encoding='utf-8'?>" + "<ROOT>";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String userid = xmlStrMap.get("userid")==null?"":xmlStrMap.get("userid");
			String buytype = xmlStrMap.get("buytype")==null?"":xmlStrMap.get("buytype");
			String resourceid = xmlStrMap.get("resourceid")==null?"":xmlStrMap.get("resourceid");
			try {
				List list = ceiDictDao.getALLData("select new map(c.id as id) from MwpmSysUserBusinessCourse c " +
						"where c.buytype='"+buytype+"' and c.userid='"+userid+"' and c.resourceid='"+resourceid+"'");
				String id = "";
				if(list!=null && list.size()>0){
					HashMap map = (HashMap)list.get(0);
					id = map.get("id")==null?"0":(String)map.get("id");
				}else{
					MwpmSysUserBusinessCourse sbc = new MwpmSysUserBusinessCourse();
					sbc.setUserid(userid);
					sbc.setResourceid(resourceid);
					sbc.setBuytype(buytype);
					sbc.setFld1(new Date());
					ceiDictDao.saveObject(sbc);
					id = sbc.getId();
				}
				xmlMessage += "<RETURNCODE>"+id+"</RETURNCODE>";
			} catch (Exception e) {
				xmlMessage += "<RETURNCODE>0</RETURNCODE>";
			}
		} else {
			xmlMessage += "<RETURNCODE>-1</RETURNCODE>";
		}
		xmlMessage += "</ROOT>";
		return xmlMessage;
	}
	


	/**
	 * 删除个人智慧海
	 * @author tanJie
	 */
	@Override
	public String delBusiness(String xml) {
		String error = "";
		String xmlMessage = "<?xml version='1.0' encoding='utf-8'?>" + "<ROOT>";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String userid = xmlStrMap.get("userid")==null?"":xmlStrMap.get("userid");
			String buytype = xmlStrMap.get("buytype")==null?"":xmlStrMap.get("buytype");
			String resourceid = xmlStrMap.get("resourceid")==null?"":xmlStrMap.get("resourceid");
			try {
				List list = ceiDictDao.getALLData("select new map(c.id as id) from MwpmSysUserBusinessCourse c " +
						"where c.buytype='"+buytype+"' and c.userid='"+userid+"' and c.resourceid='"+resourceid+"'");
				if(list!=null && list.size()>0){
					HashMap map = (HashMap)list.get(0);
					MwpmSysUserBusinessCourse sbc = new MwpmSysUserBusinessCourse();
					String id = map.get("id")==null?"0":(String)map.get("id");
					sbc.setId(id);
					ceiDictDao.delObject(sbc);
					xmlMessage += "<RETURNCODE>1</RETURNCODE>";
				}else{
					xmlMessage += "<RETURNCODE>1</RETURNCODE>";
				}
			} catch (Exception e) {
				xmlMessage += "<RETURNCODE>0</RETURNCODE>";
			}
		} else {
			xmlMessage += "<RETURNCODE>-1</RETURNCODE>";
		}
		xmlMessage += "</ROOT>";
		return xmlMessage;
	}
	

	/**
	 * 查询个人智慧海
	 * @author tanJie
	 */
	@Override
	public String queryBusiness(String xml) {
		String error = "";
		String xmlMessage = "<?xml version='1.0' encoding='utf-8'?>" + "<ROOT>";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String userid = xmlStrMap.get("userid")==null?"":xmlStrMap.get("userid");
			String imageType = xmlStrMap.get("imagetype")==null?"":xmlStrMap.get("imagetype");
			String fulladdressPath = CommonParm.getString("FULL_ADDRESS_PATH");
			String paotoPath = CommonParm.getString("TEL_PHOTO_PATH");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				List phoneFunctionList = ceiDictDao.getALLData("select new map(f.id as id,f.name as name," +
						"f.parentid as parentid,f.path as path,f.type as type,f.operationimage as operationimage," +
						"f.issuetime as issuetime,f.ipadimage as ipadimage,f.ipadImageTime as ipadImageTime) " +
						"from MwpmSysUserBusinessCourse c,MwpmDictFunction f where c.resourceid=f.id " +
						"and c.buytype='yw' and c.userid='"+userid+"'");
				if(phoneFunctionList!=null && phoneFunctionList.size()>0){
					xmlMessage += "<phonefunctions>"; 
					for (int j = 0; j < phoneFunctionList.size(); j++) {
						HashMap phoneFunctionMap = (HashMap)phoneFunctionList.get(j);
						String resourceid = phoneFunctionMap.get("id")==null?"":(String)phoneFunctionMap.get("id");
						String name = phoneFunctionMap.get("name")==null?"":(String)phoneFunctionMap.get("name");
						String parentid = phoneFunctionMap.get("parentid")==null?"":(String)phoneFunctionMap.get("parentid");
						String path = phoneFunctionMap.get("path")==null?"":(String)phoneFunctionMap.get("path");
						String type = phoneFunctionMap.get("type")==null?"":(String)phoneFunctionMap.get("type");
						String operationimage = phoneFunctionMap.get("operationimage")==null?"":(String)phoneFunctionMap.get("operationimage");
						String issuetime = phoneFunctionMap.get("issuetime")==null?"":(String) phoneFunctionMap.get("issuetime");
						String ipadimage = phoneFunctionMap.get("ipadimage")==null?"":(String)phoneFunctionMap.get("ipadimage");
						String ipadImageTime = phoneFunctionMap.get("ipadImageTime")==null?"":(String) phoneFunctionMap.get("ipadImageTime");
						String time1 = "";
						String time2 = "";
						if(issuetime!=null && !issuetime.equals("")){
							time1 = issuetime.substring(0,issuetime.length()-2);
						}
						if(ipadImageTime!=null && !ipadImageTime.equals("")){
							time2 =ipadImageTime.substring(0,ipadImageTime.length()-2);
						}
						xmlMessage += "<phonefunction>"; 
						xmlMessage += "<resourceid>"+resourceid+"</resourceid>"; 
						xmlMessage += "<name>"+name+"</name>"; 
						xmlMessage += "<parentid>"+parentid+"</parentid>"; 
						xmlMessage += "<path>"+path+"</path>"; 
						xmlMessage += "<type>"+type+"</type>"; 
						if(imageType.equals("pad")){
							xmlMessage += "<operationimage>"+fulladdressPath+paotoPath+ipadimage+"</operationimage>"; 
							xmlMessage += "<issuetime>"+time2+"</issuetime>";
						}else{
							xmlMessage += "<operationimage>"+fulladdressPath+paotoPath+operationimage+"</operationimage>"; 
							xmlMessage += "<issuetime>"+time1+"</issuetime>";
						}
						xmlMessage += "</phonefunction>"; 

					}
					xmlMessage += "</phonefunctions>"; 
				}
			} catch (Exception e) {
				xmlMessage += "<RETURNCODE>0</RETURNCODE>";
			}
		} else {
			xmlMessage += "<RETURNCODE>-1</RETURNCODE>";
		}
		xmlMessage += "</ROOT>";
		return xmlMessage;
	}


	/**
	 * 查询课件评论列表
	 * @author tanJie
	 */
	@Override
	public String queryBBSMotifList(String xml) {
		String error = "";
		String xmlMessage = "<?xml version='1.0' encoding='utf-8'?>" + "<ROOT>";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String functionid = xmlStrMap.get("functionid")==null?"":xmlStrMap.get("functionid");
			try {
				List BBSMotifList = ceiDictDao.getALLData("select new map(m.id as id,m.title as title,m.time as time," +
						"m.userid as userid,m.num as num,m.follownum as follownum,u.name as name) " +
						"from MwpmSysForumMotif m,MwpmSysUserinfo u where m.userid=u.userid " +
						"and m.functionid='"+functionid+"'");
				if(BBSMotifList!=null && BBSMotifList.size()>0){
					xmlMessage += "<bbsmotifs>"; 
					for (int j = 0; j < BBSMotifList.size(); j++) {
						HashMap motifMap = (HashMap)BBSMotifList.get(j);
						String id = motifMap.get("id")==null?"":(String)motifMap.get("id");
						String title = motifMap.get("title")==null?"":(String)motifMap.get("title");
						Date time = (Date) motifMap.get("time");
						String userid = motifMap.get("userid")==null?"":(String)motifMap.get("userid");
						int num = motifMap.get("num")==null?0:Integer.parseInt(motifMap.get("num").toString());
						int follownum = motifMap.get("follownum")==null?0:Integer.parseInt(motifMap.get("follownum").toString());
						String name = motifMap.get("name")==null?"":(String)motifMap.get("name");
						String time1 = "";
						if(time!=null){
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							time1 = sdf.format(time);
						}
						xmlMessage += "<bbsmotif>"; 
						xmlMessage += "<id>"+id+"</id>"; 
						xmlMessage += "<title>"+title+"</title>"; 
						xmlMessage += "<time>"+time1+"</time>"; 
						xmlMessage += "<userid>"+userid+"</userid>"; 
						xmlMessage += "<num>"+num+"</num>"; 
						xmlMessage += "<follownum>"+follownum+"</follownum>";
						xmlMessage += "<name>"+name+"</name>"; 
						xmlMessage += "</bbsmotif>"; 

					}
					xmlMessage += "</bbsmotifs>"; 
				}
			} catch (Exception e) {
				xmlMessage += "<RETURNCODE>0</RETURNCODE>";
			}
		} else {
			xmlMessage += "<RETURNCODE>-1</RETURNCODE>";
		}
		xmlMessage += "</ROOT>";
		
		return xmlMessage;
	}


	

	/**
	 * 新增课件评论
	 * @author tanJie
	 */
	@Override
	public String saveBBSFollow(String xml) {
		String error = "";
		String xmlMessage = "<?xml version='1.0' encoding='utf-8'?>" + "<ROOT>";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			this.init();
			String functionid = xmlStrMap.get("functionid")==null?"":xmlStrMap.get("functionid");
			String content = xmlStrMap.get("content")==null?"":xmlStrMap.get("content");
			String userid = xmlStrMap.get("userid")==null?"":xmlStrMap.get("userid");
			String serial = xmlStrMap.get("serial")==null?"1":xmlStrMap.get("serial");
			String time = xmlStrMap.get("time")==null?"":xmlStrMap.get("time");
			String classid = xmlStrMap.get("classid")==null?"1":xmlStrMap.get("classid");
			Date d = new Date();
			String bb = this.getFilterString(content);
			try {
				if(!userid.equals("")){
					MwpmSysForumFollow ff = new MwpmSysForumFollow();
					ff.setFunctionid(functionid);
					ff.setContent(bb);
					ff.setUserid(userid);
					ff.setSerial(Integer.parseInt(serial));
					ff.setFld1(classid);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					ff.setTime(d);
					//区分父子贴
					if(time.equals("")){
						String times = sdf.format(d);
						ff.setFld2(times);
					}else{
						ff.setFld2(time);
					}
					ceiDictDao.saveObject(ff);
					
					
					List list = ceiDictDao.getALLData("select new map(bc.id as id) from MwpmSysUserBusinessCourse bc " +
							"where bc.buytype='bbs' and bc.userid='"+userid+"' and bc.resourceid='"+classid+"'");		
					//统计使用
					if(list==null || list.size()==0){
						MwpmSysUserBusinessCourse sbc = new MwpmSysUserBusinessCourse();
						sbc.setUserid(userid);
						sbc.setResourceid(classid);
						sbc.setBuytype("bbs");
						sbc.setFld1(new Date());
						ceiDictDao.saveObject(sbc);
					}
					Date time2 = ff.getTime();
					String timeString = sdf.format(time2);
					xmlMessage += "<RETURNCODE>"+timeString+"</RETURNCODE>";
					xmlMessage += "<content>"+bb+"</content>";
				}else{
					xmlMessage += "<RETURNCODE>-2</RETURNCODE>";
				}
			} catch (Exception e) {
				xmlMessage += "<RETURNCODE>0</RETURNCODE>";
			}
		} else {
			xmlMessage += "<RETURNCODE>-1</RETURNCODE>";
		}
		xmlMessage += "</ROOT>";
		return xmlMessage;
	}

	
	


	/**
	 * 用户注销登录
	 * @author tanJie
	 */
	@Override
	public String quitUserInfo(String xml) {
		String error = "";
		String xmlMessage = "<?xml version='1.0' encoding='utf-8'?>" + "<ROOT>";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {

			String userid = xmlStrMap.get("userid")==null?"":xmlStrMap.get("userid");
			try {
				if(!userid.equals("")){
					ceiDictDao.updateXYInfo("update MwpmSysUserinfo set loginstateM='0' where userid='"+userid+"'");
					xmlMessage += "<RETURNCODE>1</RETURNCODE>";
				}else{
					xmlMessage += "<RETURNCODE>-2</RETURNCODE>";
				}
			} catch (Exception e) {
				xmlMessage += "<RETURNCODE>0</RETURNCODE>";
			}
		} else {
			xmlMessage += "<RETURNCODE>-1</RETURNCODE>";
		}
		xmlMessage += "</ROOT>";
		return xmlMessage;
	}
	



	/**
	 * 新增操作日志记录
	 * @author tanJie
	 */
	@Override
	public String saveOperationlog(String xml) {
		String error = "";
		String xmlMessage = "<?xml version='1.0' encoding='utf-8'?>" + "<ROOT>";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String loginid = xmlStrMap.get("loginid")==null?"":xmlStrMap.get("loginid");
			String functionid = xmlStrMap.get("functionid")==null?"":xmlStrMap.get("functionid");
			String functionname = xmlStrMap.get("functionname")==null?"":xmlStrMap.get("functionname");
			try {
				if(!loginid.equals("") || !loginid.equals("null")){
					MwpmSysOperationlog ol = new MwpmSysOperationlog();
					ol.setLoginid(loginid);
					ol.setOperatemodule(functionname);
					ol.setOperatemoduleid(functionid);
					ol.setOperatetime(new Date());
					ol.setOperatetype("access");
					ceiDictDao.saveObject(ol);
					xmlMessage += "<RETURNCODE>"+ol.getOperateid()+"</RETURNCODE>";
				}else{
					xmlMessage += "<RETURNCODE>-2</RETURNCODE>";
				}
			} catch (Exception e) {
				xmlMessage += "<RETURNCODE>0</RETURNCODE>";
			}
		} else {
			xmlMessage += "<RETURNCODE>-1</RETURNCODE>";
		}
		xmlMessage += "</ROOT>";
		return xmlMessage;
	}
	
	
	
	/**
	 * 查询课件评论详细信息
	 * @author tanJie
	 */
	@Override
	public String queryBBSFollow(String xml) {
		String error = "";
		String xmlMessage = "<?xml version='1.0' encoding='utf-8'?>" + "<ROOT>";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String functionid = xmlStrMap.get("functionid")==null?"":xmlStrMap.get("functionid");
			String classid = xmlStrMap.get("classid")==null?"":xmlStrMap.get("classid");
			List list = ceiDictDao.getInfo("select ff.content as content,ff.serial as serial,ff.fld2 as fld2,ff.time as time,u.name as name " +
					"from MWPM_SYS_FORUM_FOLLOW ff left join MWPM_SYS_USERINFO u on ff.userid=u.userid " +
					"where 0=0 and ff.functionid='"+functionid+"' and ff.fld1='"+classid+"' order by ff.fld2,ff.serial");
			try {
				if(list!=null && list.size()>0){
					xmlMessage += "<bbsfollows>";
					for (int i = 0; i < list.size(); i++) {
						HashMap map = (HashMap)list.get(i);
						String content = map.get("content")==null?"":(String)map.get("content");
						String name = map.get("name")==null?"":(String)map.get("name");
						int serial = map.get("serial")==null?1:(Integer) map.get("serial");
						String fld2 = map.get("fld2")==null?"":(String)map.get("fld2");
						Date time = (Date) map.get("time");
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String time1 = "";
						if(time!=null){
							time1 = sdf.format(time);
						}
						try {
							DesUtils d=new DesUtils();
							name = d.decrypt(name);
						} catch (Exception e) {
							xmlMessage += "<RETURNCODE>-2</RETURNCODE>";
						}
						xmlMessage += "<bbsfollow>";
						xmlMessage += "<content>"+content+"</content>";
						xmlMessage += "<name>"+name+"</name>";
						xmlMessage += "<serial>"+serial+"</serial>";
						xmlMessage += "<time>"+time1+"</time>";
						xmlMessage += "<stringtime>"+fld2+"</stringtime>";
						xmlMessage += "</bbsfollow>";
					}
					xmlMessage += "</bbsfollows>";
				}
			} catch (Exception e) {
				xmlMessage += "<RETURNCODE>0</RETURNCODE>";
			}
		} else {
			xmlMessage += "<RETURNCODE>-1</RETURNCODE>";
		}
		xmlMessage += "</ROOT>";
		return xmlMessage;
	}
	

	
	
	/**
	 * 通用版用户信息
	 * @author tanJie
	 */
	@Override
	public String saveUserInfo(String xml) {
		String error = "";
		String xmlMessage = "<?xml version='1.0' encoding='utf-8'?>" + "<ROOT>";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String loginname = xmlStrMap.get("loginname")==null?"":xmlStrMap.get("loginname");
			String password = xmlStrMap.get("password")==null?"":xmlStrMap.get("password");
			String name = xmlStrMap.get("name")==null?"":xmlStrMap.get("name");
			String sex = xmlStrMap.get("sex")==null?"":xmlStrMap.get("sex");
			String email = xmlStrMap.get("email")==null?"":xmlStrMap.get("email");
			String cardno = xmlStrMap.get("idnum")==null?"":xmlStrMap.get("idnum");
			String certype = xmlStrMap.get("idtype")==null?"":xmlStrMap.get("idtype");
			String phone = xmlStrMap.get("phonenum")==null?"":xmlStrMap.get("phonenum");
			String functionid = CommonParm.getString("FUNCTION_ID");
			String unitid = CommonParm.getString("UNIT_ID");
			try {
				DesUtils d=new DesUtils();
				loginname = d.encrypt(loginname);
				password = d.encrypt(password);
				name = d.encrypt(name);
				email = d.encrypt(email);
				cardno = d.encrypt(cardno);
				phone = d.encrypt(phone);
				List list = ceiDictDao.getALLData("from MwpmSysUserinfo where loginname='"+loginname+"' and functionid='00000001'");
				List emailList = ceiDictDao.getALLData("from MwpmSysUserinfo where email='"+email+"' and functionid='00000001'");
				if(list!=null && list.size()>0){
					xmlMessage += "<RETURNCODE>1</RETURNCODE>"; //用户名存在
				}
				else if(emailList!=null && emailList.size()>0){
					xmlMessage += "<RETURNCODE>2</RETURNCODE>"; //邮箱存在
				}
				else{
					MwpmSysUserinfo user = new MwpmSysUserinfo();
					user.setLoginname(loginname);
					user.setPassword(password);
					user.setName(name);
					user.setSex(sex);
					user.setEmail(email);
					user.setFunctionid(functionid);
					user.setCreatetime(new Date());
					user.setStatus("0");
					user.setUnitid(unitid);
					user.setUsertype("tel");
					user.setCertype(certype);
					user.setCardno(cardno);
					user.setPhone(phone);
					ceiDictDao.saveObject(user);
					MwpmSysRoleuser role = new MwpmSysRoleuser();
					role.setUserid(user.getUserid());
					role.setRoleid("00000001");
					ceiDictDao.saveObject(role);
					xmlMessage += "<RETURNCODE>"+user.getUserid()+"</RETURNCODE>";
				}
			} catch (Exception e) {
				xmlMessage += "<RETURNCODE>0</RETURNCODE>";
			}
		} else {
			xmlMessage += "<RETURNCODE>-1</RETURNCODE>";
		}
		xmlMessage += "</ROOT>";
		return xmlMessage;
	}
	
	
	/**
	 * 重置密码
	 * @author tanJie
	 */
	@Override
	public String updateUserInfoPassWord(String xml) {
		String error = "";
		String xmlMessage = "<?xml version='1.0' encoding='utf-8'?>" + "<ROOT>";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		final EmailUtil springMail = new EmailUtil();
		if ("".equals(error)) {
			//String loginname = xmlStrMap.get("loginname")==null?"":xmlStrMap.get("loginname");
			String email = xmlStrMap.get("email")==null?"":xmlStrMap.get("email");
			CommonParm cp = new CommonParm();
			String random = cp.getCharAndNumr(8);
			String content = "新密码为："+random+"    【请及时修改系统随机密码】";
			String email1 = email;
 			try {
 	 			DesUtils d=new DesUtils();
 	 			//loginname = d.encrypt(loginname);
 	 			email = d.encrypt(email);
 	 			random = d.encrypt(random);
 				List list = ceiDictDao.getALLData("select userid from MwpmSysUserinfo where email='"+email+"'");
 				if(list!=null && list.size()>0){
 	 				String hql ="update MwpmSysUserinfo set password='"+random+"' where email='"+email+"'";
// 	 				if(!loginname.equals("")){
// 	 					hql = hql+" and loginname='"+loginname+"'";
// 	 				}
 					//ceiDictDao.updateXYInfo(hql);
 					springMail.sendTextMail(sender, email1, content);
 					xmlMessage += "<RETURNCODE>1</RETURNCODE>";
 				}else{
 	 				System.out.println("---------------------------------");
 					springMail.sendTextMail(sender, email1, content);
 				}
			} catch (Exception e) {
				xmlMessage += "<RETURNCODE>0</RETURNCODE>";
			}
		} else {
			xmlMessage += "<RETURNCODE>-1</RETURNCODE>";
		}
		xmlMessage += "</ROOT>";
		return xmlMessage;
	}
	
	
	/**
	 * 修改密码
	 * @author tanJie
	 */
	@Override
	public String updatePassWord(String xml) {
		String error = "";
		String xmlMessage = "<?xml version='1.0' encoding='utf-8'?>" + "<ROOT>";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		final EmailUtil springMail = new EmailUtil();
		if ("".equals(error)) {
			String userid = xmlStrMap.get("userid")==null?"":xmlStrMap.get("userid");
			String password = xmlStrMap.get("newpassword")==null?"":xmlStrMap.get("newpassword");
			String oldpassword = xmlStrMap.get("oldpassword")==null?"":xmlStrMap.get("oldpassword");
			String email = xmlStrMap.get("email")==null?"":xmlStrMap.get("email");
			String cardno = xmlStrMap.get("idnum")==null?"":xmlStrMap.get("idnum");
			String certype = xmlStrMap.get("idtype")==null?"":xmlStrMap.get("idtype");
			String phone = xmlStrMap.get("phonenum")==null?"":xmlStrMap.get("phonenum");
 			try {
 	 			DesUtils d=new DesUtils();
 	 			if(!password.equals("")){
 	 	 			password = d.encrypt(password);
 	 			}
 	 			if(!password.equals("")){
 	 				oldpassword = d.encrypt(oldpassword);
 	 			}
 	 			if(!email.equals("")){
 	 	 			email = d.encrypt(email);
 	 			}
 	 			if(!cardno.equals("")){
 	 	 			cardno = d.encrypt(cardno);
 	 			}
 	 			if(!phone.equals("")){
 	 	 			phone = d.encrypt(phone);
 	 			}
 	 			List emailList = ceiDictDao.getALLData("select new map(userid) from MwpmSysUserinfo where email='"+email+"'");
 				if(emailList!=null && emailList.size()>0){
	 					xmlMessage += "<RETURNCODE>2</RETURNCODE>";
 				}else{
 	 				List list = ceiDictDao.getALLData("select new map(userid) from MwpmSysUserinfo where userid='"+userid+"' and password='"+oldpassword+"'");
 	 				if(list!=null && list.size()>0){
 	 	 				String hql ="update MwpmSysUserinfo set userid='"+userid+"'" ;
 	 	 						if(!password.equals("")){
 	 	 							hql = hql +",password='"+password+"'" ;
 	 	 						}
 	 	 						if(!email.equals("")){
 	 	 							hql = hql +",email='"+email+"'" ;
 	 	 						}
 	 	 						if(!cardno.equals("")){
 	 	 							hql = hql +",cardno='"+cardno+"'" ;
 	 	 						}
 	 	 						if(!certype.equals("")){
 	 	 							hql = hql +",certype='"+certype+"'" ;
 	 	 						}
 	 	 						if(!phone.equals("")){
 	 	 							hql = hql +",phone='"+phone+"'" ;
 	 	 						}
 	 	 						hql = hql + "where userid='"+userid+"'";
 	 					ceiDictDao.updateXYInfo(hql);
 	 					xmlMessage += "<RETURNCODE>1</RETURNCODE>";
 	 				}else{
 	 					xmlMessage += "<RETURNCODE>-2</RETURNCODE>";
 	 				}
 				}
			} catch (Exception e) {
				xmlMessage += "<RETURNCODE>0</RETURNCODE>";
			}
		} else {
			xmlMessage += "<RETURNCODE>-1</RETURNCODE>";
		}
		xmlMessage += "</ROOT>";
		return xmlMessage;
	}
	
	
	/**
	 * 按课件名称模糊查询课件列表
	 * @author tanJie
	 */
	@Override
	public String queryClassByName(String xml) {
		String error = "";
		String xmlMessage = "<?xml version='1.0' encoding='utf-8'?>" + "<ROOT>";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		final EmailUtil springMail = new EmailUtil();
		if ("".equals(error)) {
			String functionids = xmlStrMap.get("functionids")==null?"":xmlStrMap.get("functionids");
			String classname = xmlStrMap.get("classname")==null?"":xmlStrMap.get("classname");
			String imagetype = xmlStrMap.get("imagetype")==null?"":xmlStrMap.get("imagetype");
			String fulladdressPath = CommonParm.getString("FULL_ADDRESS_PATH");
			String bookPath = CommonParm.getString("BOOK_ZIP_PATH");
			String LOOK_PATH = CommonParm.getString("LOOK_PATH");
			String bookRecousePath = CommonParm.getString("BOOK_RECOUSE_PATH");
			String photoPath = CommonParm.getString("PHOTO_PATH");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 			try {
 				String[] functionid = functionids.split(",");
 				String fid = "";
 				if(functionid!=null && functionid.length>0){
 					fid = "'";
 					for (int i = 0; i < functionid.length; i++) {
 	 					if(i!=(functionid.length-1)){
 	 						fid = fid+functionid[i]+"','";
 	 					}else{
 	 						fid=fid+functionid[i]+"'";
 	 					}
					}
 				}	
 				String sql = "select distinct(re.classid) as classid,re.ischeck as ischeck,c.name as name,c.teachername as author," +
				"c.classlength as classlength,c.intro as intro,c.setnum as setnum,c.TIME as creattime,c.isfree as isfree,rp.path as path,rp.passkey as passkey " +
				"from MWPM_SYS_OPERATI_RESOURCE re left join MWPM_SYS_CLASS c on re.classid=c.id " +
				"left join MWPM_SYS_RESOURCEPATH rp on re.classid=rp.RESOURCEID " +
				"where 1=1 and c.status='1' and re.functionid in ("+fid+") and rp.type='kj'";
				if(!classname.equals("")){
					sql = sql+" and c.NAME like '%"+classname+"%'";
				}
				sql = sql +" order by c.time desc";
				List classList = ceiDictDao.getInfo(sql);
				if(classList != null && classList.size()>0){
					xmlMessage += "<classgroup>";
					for (int i = 0; i < classList.size(); i++) {
						HashMap map = (HashMap)classList.get(i);
						String classid = map.get("classid")==null?"":(String)map.get("classid");
						String ischeck = map.get("ischeck")==null?"":(String)map.get("ischeck");
						String name = map.get("name")==null?"":(String)map.get("name");
						String author = map.get("author")==null?"":(String)map.get("author");
						String classlength = map.get("classlength")==null?"":(String)map.get("classlength");
						String path = map.get("path")==null?"":(String)map.get("path");
						String intro = map.get("intro")==null?"":(String)map.get("intro");
						Date creattime1 = (Date) map.get("creattime");
						String passkey = map.get("passkey")==null?"":(String)map.get("passkey");
						String isfree = map.get("isfree")==null?"":((Character)map.get("isfree")).toString();
						String creattime = "";
						if(creattime1!=null){
							creattime = sdf.format(creattime1);
						}
						Integer setnum= map.get("setnum")==null?0:(Integer) map.get("setnum");
						String url = "";
						if(imagetype.equals("androidpad")){
							url = "/an1";
						}
						else if(imagetype.equals("ipad")){
							url = "/i1";
						}
						else if(imagetype.equals("android")){
							url = "/an2";
						}else{
							url = "/i2";
						}
						String lookpath=fulladdressPath+bookRecousePath+path+url+LOOK_PATH;
						String downpath=fulladdressPath+bookPath+path+url+".zip";
						xmlMessage += "<class>";
						xmlMessage += "<classid>"+classid+"</classid>";
						xmlMessage += "<ischeck>"+ischeck+"</ischeck>";
						xmlMessage += "<name>"+name+"</name>";
						xmlMessage += "<teachername>"+author+"</teachername>";
						xmlMessage += "<path>"+fulladdressPath+photoPath+path+"</path>";
						xmlMessage += "<classlength>"+classlength+"</classlength>";
						xmlMessage += "<setnum>"+setnum+"</setnum>";
						xmlMessage += "<protime>"+creattime+"</protime>";
						xmlMessage += "<downpath>"+downpath+"</downpath>";
						xmlMessage += "<lookpath>"+lookpath+"</lookpath>";
						xmlMessage += "<intro>"+intro+"</intro>";
						xmlMessage += "<passkey>"+passkey+"</passkey>";
						xmlMessage += "<isfree>"+isfree+"</isfree>";
						xmlMessage += "</class>";
					}
					xmlMessage += "</classgroup>";
				}
			} catch (Exception e) {
				xmlMessage += "<RETURNCODE>0</RETURNCODE>";
			}
		} else {
			xmlMessage += "<RETURNCODE>-1</RETURNCODE>";
		}
		xmlMessage += "</ROOT>";
		return xmlMessage;
	}
	

	/**
	 * 按业务id查询分类列表
	 * @author tanJie
	 */
	@Override
	public String queryClassByType(String xml) {
		String error = "";
		String xmlMessage = "<?xml version='1.0' encoding='utf-8'?>" + "<ROOT>";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		final EmailUtil springMail = new EmailUtil();
		if ("".equals(error)) {
			String functionids = xmlStrMap.get("functionids")==null?"":xmlStrMap.get("functionids");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 			try {
 				String[] functionid = functionids.split(",");
 				String fid = "";
 				List classList = new ArrayList();
 				if(functionid!=null && functionid.length>0){
 					fid = "'";
 					for (int i = 0; i < functionid.length; i++) {
 	 					if(i!=(functionid.length-1)){
 	 						fid = fid+functionid[i]+"','";
 	 					}else{
 	 						fid=fid+functionid[i]+"'";
 	 					}
					}
 	 			String sql = "select ct.id as id,ct.path as path from MWPM_SYS_OPERATION_CLASS oc  left join MWPM_SYS_CLASS_TYPE ct on oc.classid=ct.id " +
						"where oc.functionid in ("+fid+")";
 	 			classList = ceiDictDao.getInfo(sql);
 				}
 				String ids = "";
				Map map2 = new HashMap();
				List list1 = new ArrayList();
				for (int j = 0; j < classList.size(); j++) {
					Map map1 = (HashMap)classList.get(j);
					String a = map1.get("id").toString();
					String b = map1.get("path").toString();
					String[] c = b.split("/");
					for (int k = 0; k < c.length; k++) {
						if(map2.get(c[k]) == null){
							if(!c[k].equals("")){
								map2.put(c[k], c[k]);
								ids = ids+"'"+c[k]+"',";
							}
						}
						if(map2.get(a) == null){
							if(!a.equals("")){
								map2.put(a, a);
								ids = ids+"'"+a+"',";
							}
						}
					}
				}
				if(!ids.equals("")){
					List classTypeList = ceiDictDao.getInfo(" select ct.id as id,ct.name as name,ct.parentid as parentid " +
							"from MWPM_SYS_CLASS_TYPE ct where id in ("+ids.substring(0, ids.length()-1)+")");
					if(classTypeList!=null && classTypeList.size()>0){
						xmlMessage += "<classtypes>";
						for (int i = 0; i < classTypeList.size(); i++) {
							HashMap map = (HashMap)classTypeList.get(i);
							String id = map.get("id")==null?"":(String)map.get("id");
							String name = map.get("name")==null?"":(String)map.get("name");
							String parentid = map.get("parentid")==null?"":(String)map.get("parentid");
							xmlMessage += "<classtype>";
							xmlMessage += "<id>"+id+"</id>";
							xmlMessage += "<name>"+name+"</name>";
							xmlMessage += "<parentid>"+parentid+"</parentid>";
							xmlMessage += "</classtype>";
						}
						xmlMessage += "</classtypes>";
					}else{
						xmlMessage += "<RETURNCODE>5</RETURNCODE>";
					}
				}else{
					xmlMessage += "<RETURNCODE>5</RETURNCODE>";
				}
			} catch (Exception e) {
				xmlMessage += "<RETURNCODE>0</RETURNCODE>";
			}
		} else {
			xmlMessage += "<RETURNCODE>-1</RETURNCODE>";
		}
		xmlMessage += "</ROOT>";
		return xmlMessage;
	}
	
	/**
	 * 按分类id查询详细课件列表
	 * @author tanJie
	 */
	@Override
	public String queryClassTypeByClass(String xml) {
		String error = "";
		String xmlMessage = "<?xml version='1.0' encoding='utf-8'?>" + "<ROOT>";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		final EmailUtil springMail = new EmailUtil();
		if ("".equals(error)) {
			String id = xmlStrMap.get("id")==null?"":xmlStrMap.get("id");
			String imagetype = xmlStrMap.get("imagetype")==null?"":xmlStrMap.get("imagetype");
			int index = Integer.parseInt(xmlStrMap.get("index")==null?"1":xmlStrMap.get("index"));
			String fulladdressPath = CommonParm.getString("FULL_ADDRESS_PATH");
			String bookPath = CommonParm.getString("BOOK_ZIP_PATH");
			String LOOK_PATH = CommonParm.getString("LOOK_PATH");
			String bookRecousePath = CommonParm.getString("BOOK_RECOUSE_PATH");
			String photoPath = CommonParm.getString("PHOTO_PATH");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			int PAGING = Integer.parseInt(CommonParm.getString("REPORT_NUM")==null?"0":CommonParm.getString("REPORT_NUM"));
 			try {
 				String sqlpaging ="SELECT TOP "+PAGING+" *  FROM (SELECT ROW_NUMBER() OVER (ORDER BY c.time desc) AS RowNumber,c.id as classid,"+ 
 						    "c.name as name,c.teachername as author,c.classlength as classlength,c.intro as intro,"+
 						    "c.setnum as setnum,c.TIME as creattime,c.isfree as isfree,rp.path as path,rp.passkey as passkey "+
 						    "from MWPM_SYS_CLASSIFICATION cl left join MWPM_SYS_CLASS c on cl.RESOURCEID=c.id "+
 						    "left join MWPM_SYS_RESOURCEPATH rp on cl.RESOURCEID=rp.RESOURCEID "+
 						    "where 1=1 and c.status='1' and cl.typeid='"+id+"' and rp.type='kj'"+
 						    ") A WHERE RowNumber > "+PAGING+"*("+index+"-1)";
 				List classList = ceiDictDao.getInfo(sqlpaging);
 				System.out.println(classList.size());
				if(classList != null && classList.size()>0){
					xmlMessage += "<classgroup>";
					for (int i = 0; i < classList.size(); i++) {
						HashMap map = (HashMap)classList.get(i);
						String classid = map.get("classid")==null?"":(String)map.get("classid");
						String name = map.get("name")==null?"":(String)map.get("name");
						String author = map.get("author")==null?"":(String)map.get("author");
						String classlength = map.get("classlength")==null?"":(String)map.get("classlength");
						String path = map.get("path")==null?"":(String)map.get("path");
						String intro = map.get("intro")==null?"":(String)map.get("intro");
						Date creattime1 = (Date) map.get("creattime");
						String passkey = map.get("passkey")==null?"":(String)map.get("passkey");
						String isfree = map.get("isfree")==null?"":((Character)map.get("isfree")).toString();
						String creattime = "";
						if(creattime1!=null){
							creattime = sdf.format(creattime1);
						}
						Integer setnum= map.get("setnum")==null?0:(Integer)map.get("setnum");
						String url = "";
						if(imagetype.equals("androidpad")){
							url = "/an1";
						}
						else if(imagetype.equals("ipad")){
							url = "/i1";
						}
						else if(imagetype.equals("android")){
							url = "/an2";
						}else{
							url = "/i2";
						}
						String lookpath=fulladdressPath+bookRecousePath+path+url+LOOK_PATH;
						String downpath=fulladdressPath+bookPath+path+url+".zip";
						xmlMessage += "<class>";
						xmlMessage += "<classid>"+classid+"</classid>";
						xmlMessage += "<name>"+name+"</name>";
						xmlMessage += "<teachername>"+author+"</teachername>";
						xmlMessage += "<path>"+fulladdressPath+photoPath+path+"</path>";
						xmlMessage += "<classlength>"+classlength+"</classlength>";
						xmlMessage += "<setnum>"+setnum+"</setnum>";
						xmlMessage += "<protime>"+creattime+"</protime>";
						xmlMessage += "<downpath>"+downpath+"</downpath>";
						xmlMessage += "<lookpath>"+lookpath+"</lookpath>";
						xmlMessage += "<intro>"+intro+"</intro>";
						xmlMessage += "<passkey>"+passkey+"</passkey>";
						xmlMessage += "<isfree>"+isfree+"</isfree>";
						xmlMessage += "</class>";
					}
					xmlMessage += "</classgroup>";
				}
			} catch (Exception e) {
				xmlMessage += "<RETURNCODE>0</RETURNCODE>";
			}
		} else {
			xmlMessage += "<RETURNCODE>-1</RETURNCODE>";
		}
		xmlMessage += "</ROOT>";
		return xmlMessage;
	}
	
	

	/**
	 * 按业务id查询报告分类列表
	 * @author tanJie
	 */
	@Override
	public String queryReportByType(String xml) {
		String error = "";
		String xmlMessage = "<?xml version='1.0' encoding='utf-8'?>" + "<ROOT>";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		final EmailUtil springMail = new EmailUtil();
		if ("".equals(error)) {
			String functionids = xmlStrMap.get("functionids")==null?"":xmlStrMap.get("functionids");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 			try {
 				String[] functionid = functionids.split(",");
 				String fid = "";
 				List classList = new ArrayList();
 				if(functionid!=null && functionid.length>0){
 					fid = "and (";
 					for (int i = 0; i < functionid.length; i++) {
 	 					if(i==0){
 	 						fid = fid+" oc.functionid='"+functionid[i]+"' ";
 	 					}else{
 	 						fid=fid+" or oc.functionid='"+functionid[i]+"' ";
 	 					}
					}
 					fid = fid + ") ";
 	 			String sql = "select rt.id as id,rt.path as path " +
 	 					"from MWPM_SYS_OPERATION_CLASS oc left join MWPM_SYS_REPORT_TYPE rt on oc.classid=rt.id " +
						"where 0=0 "+fid;
 	 			classList = ceiDictDao.getInfo(sql);
 				}
 				String ids = "";
				Map map2 = new HashMap();
				List list1 = new ArrayList();
				for (int j = 0; j < classList.size(); j++) {
					Map map1 = (HashMap)classList.get(j);
					String a = map1.get("id").toString();
					String b = map1.get("path").toString();
					String[] c = b.split("/");
					for (int k = 0; k < c.length; k++) {
						if(map2.get(c[k]) == null){
							if(!c[k].equals("")){
								map2.put(c[k], c[k]);
								ids = ids+"'"+c[k]+"',";
							}
						}
						if(map2.get(a) == null){
							if(!a.equals("")){
								map2.put(a, a);
								ids = ids+"'"+a+"',";
							}
						}
					}
				}
				List classTypeList = ceiDictDao.getInfo(" select rt.id as id,rt.name as name,rt.parentid as parentid " +
						"from MWPM_SYS_REPORT_TYPE rt where id in ("+ids.substring(0, ids.length()-1)+")");
				if(classTypeList!=null && classTypeList.size()>0){
					xmlMessage += "<classtypes>";
					for (int i = 0; i < classTypeList.size(); i++) {
						HashMap map = (HashMap)classTypeList.get(i);
						String id = map.get("id")==null?"":(String)map.get("id");
						String name = map.get("name")==null?"":(String)map.get("name");
						String parentid = map.get("parentid")==null?"":(String)map.get("parentid");
						xmlMessage += "<classtype>";
						xmlMessage += "<id>"+id+"</id>";
						xmlMessage += "<name>"+name+"</name>";
						xmlMessage += "<parentid>"+parentid+"</parentid>";
						xmlMessage += "</classtype>";
					}
					xmlMessage += "</classtypes>";
				}else{
					xmlMessage += "<classtypes>5</classtypes>";
				}
			} catch (Exception e) {
				xmlMessage += "<RETURNCODE>0</RETURNCODE>";
			}
		} else {
			xmlMessage += "<RETURNCODE>-1</RETURNCODE>";
		}
		xmlMessage += "</ROOT>";
		return xmlMessage;
	}
	
	
	/**
	 * 按分类id查询详细课件列表
	 * @author tanJie
	 */
	@Override
	public String queryReportTypeByReport(String xml) {
		String error = "";
		String xmlMessage = "<?xml version='1.0' encoding='utf-8'?>" + "<ROOT>";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		final EmailUtil springMail = new EmailUtil();
		if ("".equals(error)) {
			String id = xmlStrMap.get("id")==null?"":xmlStrMap.get("id");
			String imagetype = xmlStrMap.get("imagetype")==null?"":xmlStrMap.get("imagetype");
			int index = Integer.parseInt(xmlStrMap.get("index")==null?"1":xmlStrMap.get("index"));
			String fulladdressPath = CommonParm.getString("FULL_ADDRESS_PATH");
			String bookPath = CommonParm.getString("BOOK_ZIP_PATH");
			String LOOK_PATH = CommonParm.getString("LOOK_PATH");
			String bookRecousePath = CommonParm.getString("BOOK_RECOUSE_PATH");
			String photoPath = CommonParm.getString("PHOTO_PATH");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//int NEW_NUM = Integer.parseInt(CommonParm.getString("NEW_NUM")==null?"0":CommonParm.getString("NEW_NUM"));
 			try {
 				String sqlpaging ="SELECT c.name as name,c.teachername as author,c.classlength as classlength,c.intro as intro,"+
 						    "c.setnum as setnum,c.TIME as creattime,c.isfree as isfree,rp.path as path,rp.passkey as passkey "+
 						    "from MWPM_SYS_CLASSIFICATION cl left join MWPM_SYS_CLASS c on cl.RESOURCEID=c.id "+
 						    "left join MWPM_SYS_RESOURCEPATH rp on cl.RESOURCEID=rp.RESOURCEID "+
 						    "where 1=1 and c.status='1' and cl.typeid='"+id+"' and rp.type='kj' order by c.time desc";
 				List classList = ceiDictDao.getInfo(sqlpaging);
				if(classList != null && classList.size()>0){
					xmlMessage += "<classgroup>";
					for (int i = 0; i < classList.size(); i++) {
						HashMap map = (HashMap)classList.get(i);
						String name = map.get("name")==null?"":(String)map.get("name");
						String author = map.get("author")==null?"":(String)map.get("author");
						String classlength = map.get("classlength")==null?"":(String)map.get("classlength");
						String path = map.get("path")==null?"":(String)map.get("path");
						String intro = map.get("intro")==null?"":(String)map.get("intro");
						String passkey = map.get("passkey")==null?"":(String)map.get("passkey");
						String isfree = map.get("isfree")==null?"":((Character)map.get("isfree")).toString();
						Date creattime1 = (Date) map.get("creattime");
						String creattime = "";
						if(creattime1!=null){
							creattime = sdf.format(creattime1);
						}
						Integer setnum= map.get("setnum")==null?0:(Integer)map.get("setnum");
						String url = "";
						if(imagetype.equals("androidpad")){
							url = "/an1";
						}
						else if(imagetype.equals("ipad")){
							url = "/i1";
						}
						else if(imagetype.equals("android")){
							url = "/an2";
						}else{
							url = "/i2";
						}
						String lookpath=fulladdressPath+bookRecousePath+path+url+LOOK_PATH;
						String downpath=fulladdressPath+bookPath+path+url+".zip";
						xmlMessage += "<class>";
						xmlMessage += "<name>"+name+"</name>";
						xmlMessage += "<teachername>"+author+"</teachername>";
						xmlMessage += "<path>"+fulladdressPath+photoPath+path+"</path>";
						xmlMessage += "<classlength>"+classlength+"</classlength>";
						xmlMessage += "<setnum>"+setnum+"</setnum>";
						xmlMessage += "<protime>"+creattime+"</protime>";
						xmlMessage += "<downpath>"+downpath+"</downpath>";
						xmlMessage += "<lookpath>"+lookpath+"</lookpath>";
						xmlMessage += "<intro>"+intro+"</intro>";
						xmlMessage += "<passkey>"+passkey+"</passkey>";
						xmlMessage += "<isfree>"+isfree+"</isfree>";
						xmlMessage += "</class>";
					}
					xmlMessage += "</classgroup>";
				}
			} catch (Exception e) {
				xmlMessage += "<RETURNCODE>0</RETURNCODE>";
			}
		} else {
			xmlMessage += "<RETURNCODE>-1</RETURNCODE>";
		}
		xmlMessage += "</ROOT>";
		return xmlMessage;
	}
	
	/**
	 * 新增课件学习时长
	 * @author tanJie
	 */
	@Override
	public String addClassTime(String xml) {
		String error = "";
		String xmlMessage = "<?xml version='1.0' encoding='utf-8'?>" + "<ROOT>";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String userid = xmlStrMap.get("userid")==null?"":xmlStrMap.get("userid");
			String classid = xmlStrMap.get("classid")==null?"":xmlStrMap.get("classid");
			String classtime = xmlStrMap.get("classtime")==null?"":xmlStrMap.get("classtime");
			String timenum = xmlStrMap.get("timenum")==null?"":xmlStrMap.get("timenum");
 			try {
 				String sql = "select new map(ct.id as id) from MwpmSysUserClassTime ct where ct.userid='"+userid+"' and ct.classid='"+classid+"'";
	 			List list = ceiDictDao.getInfo(sql);
	 			if(list!=null && list.size()>0){
 	 	 			Map map = (HashMap)list.get(0);
 					String id = map.get("id")==null?"":(String)map.get("id");
 					String hql ="";
 					ceiDictDao.updateXYInfo(hql);	
	 			}else{
	 				if(!classtime.equals("")){
							MwpmSysUserClassTime uct = new MwpmSysUserClassTime();
							uct.setClassid(classid);
							uct.setUserid(userid);
							uct.setTime(Integer.parseInt(classtime));
							ceiDictDao.saveObject(uct);
	 					xmlMessage += "<RETURNCODE>1</RETURNCODE>";
	 				}else{
	 					xmlMessage += "<RETURNCODE>-2</RETURNCODE>";
	 				}
	 			}
			} catch (Exception e) {
				xmlMessage += "<RETURNCODE>0</RETURNCODE>";
			}
		} else {
			xmlMessage += "<RETURNCODE>-1</RETURNCODE>";
		}
		xmlMessage += "</ROOT>";
		return xmlMessage;
	}
	
	

	/**查询课件学习时长
	 * @author tanJie
	 */
	@Override
	public String queryClassTime(String xml) {
		String error = "";
		String xmlMessage = "<?xml version='1.0' encoding='utf-8'?>" + "<ROOT>";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String userid = xmlStrMap.get("userid")==null?"":xmlStrMap.get("userid");
			String classid = xmlStrMap.get("classid")==null?"":xmlStrMap.get("classid");
 			try {
 				if(!userid.equals("") && !classid.equals("")){
 	 				String sql = "select ct.time as time from MWPM_SYS_USER_CLASS_TIME ct where ct.userid='"+userid+"' and ct.classid='"+classid+"'";
 	 				List list = ceiDictDao.getInfo(sql);
 	 				if(list!=null && list.size()>0){
 						xmlMessage += "<classtimegroup>";
 	 	 				for (int i = 0; i < list.size(); i++) {
 	 	 					Map map = (HashMap)list.get(i);
 							Integer time = map.get("time")==null?0:(Integer)map.get("time");
 							
 	 						xmlMessage += "<classtime>";
 	 						xmlMessage += "<time>"+time+"</time>";
 	 						
 	 						xmlMessage += "</classtime>";
 						}
 						xmlMessage += "</classtimegroup>";
 	 				}
 				}
			} catch (Exception e) {
				xmlMessage += "<RETURNCODE>0</RETURNCODE>";
			}
		} else {
			xmlMessage += "<RETURNCODE>-1</RETURNCODE>";
		}
		xmlMessage += "</ROOT>";
		return xmlMessage;
	}
	
	

	

	/**
	 * 筛选客户端传过来的课件集合根据购买的课件
	 * @author tanJie
	 */
	@Override
	public String queryMoneyClass(String xml) {
		String error = "";
		String xmlMessage = "<?xml version='1.0' encoding='utf-8'?>" + "<ROOT>";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String userid = xmlStrMap.get("userid")==null?"":xmlStrMap.get("userid");
			String classids = xmlStrMap.get("classids")==null?"":xmlStrMap.get("classids");
			Map classidMap = new HashMap();
 			try {
 				if(!userid.equals("")){
 					//查询购买记录（包含业务和资源）
 	 				String sql = "select m.FUNID as funid,m.BUYTYPE as buytype,m.RESOURCETYPE as resourcetype,m.STOPTIME as stoptime " +
 	 						"from MWPM_SYS_MONEY m where m.userid='"+userid+"'";
 	 				List moneylist = ceiDictDao.getInfo(sql);
 	 				if(moneylist!=null && moneylist.size()>0){
 	 					
 	 					//通过客户端传过来的课件id
 	 	 				String[] classid=classids.split(",");
 	 	 				//查询购买课件的详细信息
 		 				String sqlStr = " select r.classid as classid,r.functionid as functionid,c.name as name," +
 		 						"c.time as time,c.TEACHERNAME as teachername,c.isfree as isfree,f.path as path " +
 		 						"from MWPM_SYS_OPERATI_RESOURCE r left join MWPM_SYS_CLASS c on r.CLASSID=c.id "+
 		 						"left join MWPM_SYS_PHONE_FUNCTION f on r.FUNCTIONID = f.id where 0=0 ";
 	 	 				List list = new ArrayList();
 	 	 				if(classid!=null && classid.length>0){
 	 	 					for (int i = 0; i < classid.length; i++) {
 	 	 						if(i==0){
 	 								sqlStr = sqlStr + "and( r.CLASSID='"+classid[i]+"'";
 	 	 						}
 	 	 						if(i!=0){
 	 	 							sqlStr = sqlStr + " or r.CLASSID='"+classid[i]+"'";
 	 	 						}
 	 	 						if(classid.length==(i+1)){
 	 								sqlStr = sqlStr + " )";
 	 	 						}
 							}
 	 	 					list = ceiDictDao.getInfo(sqlStr);
 	 	 				}
 	 	 				
 	 	 				//筛选客户端传来的课件
 	 	 				if(list!=null && list.size()>0){
 	 	 					for (int i = 0; i < list.size(); i++) {
 								Map map = (HashMap)list.get(i);
 								String path = map.get("path")==null?"":(String)map.get("path");
 								for (int j = 0; j < moneylist.size(); j++) {
									Map moneyMap = (HashMap)moneylist.get(j);
	 								String classId = map.get("classid")==null?"":(String)map.get("classid");
									String classidm = classidMap.get(classId)==null?"":classidMap.get(classId).toString();
									if(!classidm.equals(classId)){
										String funid = moneyMap.get("funid")==null?"":(String)moneyMap.get("funid");
										String buytype = moneyMap.get("buytype")==null?"":(String)moneyMap.get("buytype");
										String resourcetype = moneyMap.get("resourcetype")==null?"":(String)moneyMap.get("resourcetype");
										//根据业务筛选
										if(buytype.equals("yw")){
											if(path.contains(funid)){
				 								String name = map.get("name")==null?"":(String)map.get("name");
				 								String teachername = map.get("teachername")==null?"":(String)map.get("teachername");
				 								Date time = (Date)map.get("time");
				 								String time1 = "";
				 								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				 								if(time!=null){
				 									time1 = sdf.format(time);
				 								}
												xmlMessage += "<class>";
												xmlMessage += "<classid>"+classId+"</classid>";
												xmlMessage += "<name>"+name+"</name>";
												xmlMessage += "<teachername>"+teachername+"</teachername>";
												xmlMessage += "<time>"+time1+"</time>";
												xmlMessage += "</class>";
												classidMap.put(classId, classId);
												break;
											}
										}
										//根据课件id筛选
										if(resourcetype.equals("kj") && classId.equals(funid)){
											String name = map.get("name")==null?"":(String)map.get("name");
			 								String teachername = map.get("teachername")==null?"":(String)map.get("teachername");
			 								Date time = (Date)map.get("time");
			 								String time1 = "";
			 								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			 								if(time!=null){
			 									time1 = sdf.format(time);
			 								}
											xmlMessage += "<class>";
											xmlMessage += "<classid>"+classId+"</classid>";
											xmlMessage += "<name>"+name+"</name>";
											xmlMessage += "<teachername>"+teachername+"</teachername>";
											xmlMessage += "<time>"+time1+"</time>";
											xmlMessage += "</class>";
											classidMap.put(classId, classId);
										}
									}
								}
 							}
 	 	 				}
 	 				}
 				}
			} catch (Exception e) {
				xmlMessage += "<RETURNCODE>0</RETURNCODE>";
				e.printStackTrace();
			}
		} else {
			xmlMessage += "<RETURNCODE>-1</RETURNCODE>";
		}
		xmlMessage += "</ROOT>";
		return xmlMessage;
	}
	
	
	
	/**
	 * 政经资讯首页查询图片信息
	 * @author tanJie
	 */
	@Override
	public String queryNewsImage(String xml) {
		String error = "";
		String xmlMessage = "<?xml version='1.0' encoding='utf-8'?>" + "<ROOT>";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		final EmailUtil springMail = new EmailUtil();
		if ("".equals(error)) {
			String functionids = xmlStrMap.get("functionids")==null?"":xmlStrMap.get("functionids");
			String newstype = xmlStrMap.get("newstype")==null?"":xmlStrMap.get("newstype");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String fulladdressPath = CommonParm.getString("FULL_ADDRESS_PATH");
			String imagepath = CommonParm.getString("IMAGE_PATH"); 
			int imageNum = Integer.parseInt(CommonParm.getString("IMAGE_NUM")==null?"0":CommonParm.getString("IMAGE_NUM")); 
 			try {
 				String[] functionid = functionids.split(",");
 				String fid = "";
 				List classList = new ArrayList();
 				if(functionid!=null && functionid.length>0){
 					fid = "and (";
 					for (int i = 0; i < functionid.length; i++) {
 	 					if(i==0){
 	 						fid = fid+" r.functionid='"+functionid[i]+"' ";
 	 					}else{
 	 						fid=fid+" or r.functionid='"+functionid[i]+"' ";
 	 					}
					}
 					fid = fid + ") ";
 	 			String sql = " SELECT TOP "+imageNum+" *  FROM (" +
 	 						 "SELECT ROW_NUMBER() OVER (ORDER BY n.time desc) AS RowNumber," +
 	 						 "n.id as id,n.TITLE as title,n.SUBHEAD as subhead,n.time as time,n.isfree as isfree " +
 	 						 " from  MWPM_SYS_OPERATI_RESOURCE r left join MWPM_SYS_NEWS n on r.classid=n.id "+
 	 						 "where 0=0 ";
 	 			if(!newstype.equals("")){
 	 				sql = sql+"and r.type='db'" ;
 	 			}else{
  	 				sql = sql+"and r.type='new'" ;
 	 			}
 	 			sql = sql+ " and n.status='1' and n.PPATH is not null " + fid+ ") A WHERE RowNumber > "+imageNum+"*(1-1)";
 	 			classList = ceiDictDao.getInfo(sql);
 	 			if(classList!=null && classList.size()>0){
 					xmlMessage += "<newsgroup>";
 	 				for (int i = 0; i < classList.size(); i++) {
						HashMap map = (HashMap)classList.get(i);
						String id = map.get("id")==null?"":(String)map.get("id");
						String title = map.get("title")==null?"":(String)map.get("title");
						String subhead = map.get("subhead")==null?"":(String)map.get("subhead");
						String isfree = map.get("isfree")==null?"":((Character) map.get("isfree")).toString();
						Date time = (Date)map.get("time");
						String time1 = sdf.format(time);
						String imgPath = fulladdressPath+imagepath+id+"/new.png";
	 					xmlMessage += "<new>";
	 					xmlMessage += "<id>"+id+"</id>";
	 					xmlMessage += "<title>"+title+"</title>";
	 					xmlMessage += "<subhead>"+subhead+"</subhead>";
	 					xmlMessage += "<time>"+time1+"</time>";
	 					xmlMessage += "<imagepath>"+imgPath+"</imagepath>";
	 					xmlMessage += "<isfree>"+isfree+"</isfree>";
	 					xmlMessage += "</new>";
					}
 					xmlMessage += "</newsgroup>";
 	 			}
 				}else{
 					xmlMessage += "<RETURNCODE>5</RETURNCODE>";
 				}
			} catch (Exception e) {
				xmlMessage += "<RETURNCODE>0</RETURNCODE>";
			}
		} else {
			xmlMessage += "<RETURNCODE>-1</RETURNCODE>";
		}
		xmlMessage += "</ROOT>";
		return xmlMessage;
	}

	
	/**
	 * 政经资讯首页查询信息
	 * @author tanJie
	 */
	@Override
	public String queryNewsList(String xml) {
		String error = "";
		String xmlMessage = "<?xml version='1.0' encoding='utf-8'?>" + "<ROOT>";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		final EmailUtil springMail = new EmailUtil();
		if ("".equals(error)) {
			String functionids = xmlStrMap.get("functionids")==null?"":xmlStrMap.get("functionids");
			String newstype = xmlStrMap.get("newstype")==null?"":xmlStrMap.get("newstype");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			int newNum = Integer.parseInt(CommonParm.getString("RANK_NUM")==null?"0":CommonParm.getString("RANK_NUM")); 
 			try {
 				String[] functionid = functionids.split(",");
 				String fid = "";
 				List classList = new ArrayList();
 				if(functionid!=null && functionid.length>0){
 					fid = "and (";
 					for (int i = 0; i < functionid.length; i++) {
 	 					if(i==0){
 	 						fid = fid+" r.functionid='"+functionid[i]+"' ";
 	 					}else{
 	 						fid=fid+" or r.functionid='"+functionid[i]+"' ";
 	 					}
					}
 					fid = fid + ") ";
 	 			/*String sql = " SELECT TOP "+newNum+" *  FROM (" +
 	 						 "SELECT ROW_NUMBER() OVER (ORDER BY n.time desc) AS RowNumber," +
 	 						 "n.id as id,n.TITLE as title,n.SUBHEAD as subhead,n.time as time " +
 	 						 " from  MWPM_SYS_OPERATI_RESOURCE r left join MWPM_SYS_NEWS n on r.classid=n.id "+
 	 						 "where r.type='new' " + fid+
 	 						 ") A WHERE RowNumber > "+newNum+"*(1-1)";*/
 					String sql = " SELECT TOP "+newNum+" *  FROM (" +
					 "SELECT ROW_NUMBER() OVER (ORDER BY n.time desc) AS RowNumber," +
					 "n.id as id,n.TITLE as title,n.SUBHEAD as subhead,n.time as time,r.funName as funName,n.isfree as isfree,r.functionid as functionid " +
					 " from   (select f.name as funName,t.classid as classid,t.type as type,t.functionid as functionid " +
					 " from MWPM_SYS_PHONE_FUNCTION as f,MWPM_SYS_OPERATI_RESOURCE as t " +
					 " where f.id=t.FUNCTIONID) r left join MWPM_SYS_NEWS n on r.classid=n.id "+
					 "where n.status='1' ";
				if(!newstype.equals("")){
 	 				sql = sql+"and r.type='db' " ;
 	 			}else{
  	 				sql = sql+"and r.type='new' " ;
 	 			}
 	 			sql = sql+ fid+") A WHERE RowNumber > "+newNum+"*(1-1)";	
 			System.out.println(sql);
 			classList = ceiDictDao.getInfo(sql);
 			if(classList!=null && classList.size()>0){
 					xmlMessage += "<newsgroup>";
 	 				for (int i = 0; i < classList.size(); i++) {
						HashMap map = (HashMap)classList.get(i);
						String id = map.get("id")==null?"":(String)map.get("id");
						String title = map.get("title")==null?"":(String)map.get("title");
						String subhead = map.get("subhead")==null?"":(String)map.get("subhead");
						Date time = (Date)map.get("time");
						String funName = map.get("funName")==null?"":(String)map.get("funName");
						String isfree = map.get("isfree")==null?"":((Character) map.get("isfree")).toString();
						String functionId = map.get("functionid")==null?"":(String)map.get("functionid");
						String time1 = sdf.format(time);
	 					xmlMessage += "<new>";
	 					xmlMessage += "<id>"+id+"</id>";
	 					xmlMessage += "<title>"+title+"</title>";
	 					xmlMessage += "<subhead>"+subhead+"</subhead>";
	 					xmlMessage += "<time>"+time1+"</time>";
	 					xmlMessage += "<funName>"+funName+"</funName>";
	 					xmlMessage += "<isfree>"+isfree+"</isfree>";
	 					xmlMessage += "<functionid>"+functionId+"</functionid>";
	 					xmlMessage += "</new>";
					}
 					xmlMessage += "</newsgroup>";
 	 			}
 				}else{
 					xmlMessage += "<RETURNCODE>5</RETURNCODE>";
 				}
			} catch (Exception e) {
				xmlMessage += "<RETURNCODE>0</RETURNCODE>";
			}
		} else {
			xmlMessage += "<RETURNCODE>-1</RETURNCODE>";
		}
		xmlMessage += "</ROOT>";
		return xmlMessage;
	}
	
	/**
	 * 政经资讯首页业务查询信息
	 * @author tanJie
	 */
	@Override
	public String queryNewsByFunctionId(String xml) {
		String error = "";
		String xmlMessage = "<?xml version='1.0' encoding='utf-8'?>" + "<ROOT>";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		final EmailUtil springMail = new EmailUtil();
		if ("".equals(error)) {
			String functionid = xmlStrMap.get("functionid")==null?"":xmlStrMap.get("functionid");
			String newstype = xmlStrMap.get("newstype")==null?"":xmlStrMap.get("newstype");
			String userid = xmlStrMap.get("userid")==null?"":xmlStrMap.get("userid");
			String num = xmlStrMap.get("num")==null?"":xmlStrMap.get("num");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String fulladdressPath = CommonParm.getString("FULL_ADDRESS_PATH");
			String imagepath = CommonParm.getString("IMAGE_PATH"); 
			int newNum = 0;
	 		String sql = "";
			HashMap collectMap = new HashMap();

			//查询用户购买咨询进行比对
			String hql = "select new map(c.resourceid as resourceid) from MwpmSysNewCollect c " +
						"where c.buytype='zx' and c.userid='"+userid+"'";
			List list = ceiDictDao.getALLData(hql);
	 		if(list!=null && list.size()>0){
	 			for (int i = 0; i < list.size(); i++) {
	 				HashMap map = (HashMap)list.get(i);
	 				String resourceid = map.get("resourceid").toString();
	 				collectMap.put(resourceid, resourceid);
				}
	 		}
			if(!num.equals("")){
				newNum = Integer.parseInt(num);
				sql = " SELECT TOP "+newNum+" *  FROM (" +
				 "SELECT ROW_NUMBER() OVER (ORDER BY  n.isfree desc,n.time desc) AS RowNumber," +
				 "n.id as id,n.TITLE as title,n.SUBHEAD as subhead,n.time as time,n.isfree as isfree " +
				 " from  MWPM_SYS_OPERATI_RESOURCE r left join MWPM_SYS_NEWS n on r.classid=n.id "+
				 "where 0=0 " ;
 					if(!newstype.equals("")){
 	 	 				sql = sql+"and r.type='db'" ;
 	 	 			}else{
 	  	 				sql = sql+"and r.type='new'" ;
 	 	 			}
 					sql = sql+" and n.status='1' and r.functionid='" + functionid+"' "+
				 ") A WHERE RowNumber > "+newNum+"*(1-1)";
			}else{
				sql = "SELECT n.id as id,n.TITLE as title,n.SUBHEAD as subhead,n.time as time,n.isfree as isfree,n.time " +
				 " from  MWPM_SYS_OPERATI_RESOURCE r left join MWPM_SYS_NEWS n on r.classid=n.id "+
				 "where 0=0 ";
 					if(!newstype.equals("")){
 	 	 				sql = sql+"and r.type='db'";
 	 	 			}else{
 	  	 				sql = sql+"and r.type='new'";
 	 	 			}
 					sql = sql+" and n.status='1' and r.functionid='" + functionid+"' ORDER BY n.isfree desc,n.time desc";
			}
 			try {
 				List classList = new ArrayList();
 				queryCollect(xmlMessage);
 	 			classList = ceiDictDao.getInfo(sql);
 	 			if(classList!=null && classList.size()>0){ 	 				
 	 				xmlMessage += "<newsgroup>";
 	 				for (int i = 0; i < classList.size(); i++) {
						HashMap map = (HashMap)classList.get(i);
						String id = map.get("id")==null?"":(String)map.get("id");
						String title = map.get("title")==null?"":(String)map.get("title");
						String subhead = map.get("subhead")==null?"":(String)map.get("subhead");
						String isfree = map.get("isfree")==null?"":((Character) map.get("isfree")).toString();
						Date time = (Date)map.get("time");
						String time1 = sdf.format(time);
						String isCollect = "1";  //1：未收藏; 0：已收藏
						if(collectMap.get(id)!=null){
							isCollect = "0";
						}
	 					xmlMessage += "<new>";
	 					xmlMessage += "<id>"+id+"</id>";
	 					xmlMessage += "<title>"+title+"</title>";
	 					xmlMessage += "<subhead>"+subhead+"</subhead>";
	 					xmlMessage += "<time>"+time1+"</time>";
	 					xmlMessage += "<isfree>"+isfree+"</isfree>";
	 					xmlMessage += "<iscollect>"+isCollect+"</iscollect>";
	 					xmlMessage += "<functionid>"+functionid+"</functionid>";
	 					if(!num.equals("")){
							String imgPath = fulladdressPath+imagepath+id+"/new.png";
		 					xmlMessage += "<imagepath>"+imgPath+"</imagepath>";
	 					}
	 					xmlMessage += "</new>";
					}
 					xmlMessage += "</newsgroup>";
 				}else{
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
	 * 政经资讯按名称查询信息
	 * @author tanJie
	 */
	@Override
	public String queryNewsByName(String xml) {
		String error = "";
		String xmlMessage = "<?xml version='1.0' encoding='utf-8'?>" + "<ROOT>";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		final EmailUtil springMail = new EmailUtil();
		if ("".equals(error)) {
			String functionids = xmlStrMap.get("functionids")==null?"":xmlStrMap.get("functionids");
			String titlename = xmlStrMap.get("titlename")==null?"":xmlStrMap.get("titlename");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			int newNum = Integer.parseInt(CommonParm.getString("NEW_NUM")==null?"0":CommonParm.getString("NEW_NUM")); 
 			try {
 				String[] functionid = functionids.split(",");
 				String fid = "";
 				List classList = new ArrayList();
 				if(functionid!=null && functionid.length>0){
 					fid = "and (";
 					for (int i = 0; i < functionid.length; i++) {
 	 					if(i==0){
 	 						fid = fid+" r.functionid='"+functionid[i]+"' ";
 	 					}else{
 	 						fid=fid+" or r.functionid='"+functionid[i]+"' ";
 	 					}
					}
 					fid = fid + ") ";
 	 			String sql = " SELECT new map(r.functionid as functionid,n.id as id,n.title as title,n.subhead as subhead,n.time as time,n.isfree as isfree) " +
 	 						 " from  MwpmSysOperatiResource r,MwpmSysNews n where r.classid=n.id "+
 	 						 "and n.status='1' and r.type='new' " + fid;
 	 			if(!titlename.equals("")){
 	 	 			sql = sql+" and n.title like '%"+titlename+"%'";
 	 			}
 	 			sql = sql+" order by n.isfree desc,n.time desc";
 	 			classList = ceiDictDao.getALLData(sql);
 	 			if(classList!=null && classList.size()>0){
 					xmlMessage += "<newsgroup>";
 	 				for (int i = 0; i < classList.size(); i++) {
						HashMap map = (HashMap)classList.get(i);
						String id = map.get("id")==null?"":(String)map.get("id");
						String title = map.get("title")==null?"":(String)map.get("title");
						String subhead = map.get("subhead")==null?"":(String)map.get("subhead");
						String isfree = map.get("isfree")==null?"":map.get("isfree").toString();
						String funid = map.get("functionid")==null?"":(String)map.get("functionid");
						Date time = (Date)map.get("time");
						String time1 = sdf.format(time);
	 					xmlMessage += "<new>";
	 					xmlMessage += "<id>"+id+"</id>";
	 					xmlMessage += "<title>"+title+"</title>";
	 					xmlMessage += "<subhead>"+subhead+"</subhead>";
	 					xmlMessage += "<time>"+time1+"</time>";
	 					xmlMessage += "<isfree>"+isfree+"</isfree>";
	 					xmlMessage += "<functionid>"+funid+"</functionid>";
	 					xmlMessage += "</new>";
					}
 					xmlMessage += "</newsgroup>";
 	 			}
 				}else{
 					xmlMessage += "<RETURNCODE>5</RETURNCODE>";
 				}
			} catch (Exception e) {
				xmlMessage += "<RETURNCODE>0</RETURNCODE>";
			}
		} else {
			xmlMessage += "<RETURNCODE>-1</RETURNCODE>";
		}
		xmlMessage += "</ROOT>";
		return xmlMessage;
	}
	
	
	/**
	 * 新增资讯收藏信息
	 * @author tanJie
	 */
	@Override
	public String saveCoolect(String xml) {
		String error = "";
		String xmlMessage = "<?xml version='1.0' encoding='utf-8'?>" + "<ROOT>";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String userid = xmlStrMap.get("userid")==null?"":xmlStrMap.get("userid");
			String resourceid = xmlStrMap.get("resourceid")==null?"":xmlStrMap.get("resourceid");
			String functionid = xmlStrMap.get("functionid")==null?"":xmlStrMap.get("functionid");
			try {
				List list = ceiDictDao.getALLData("select new map(a.id as id) from MwpmSysNewCollect as a" +
						" where buytype='zx' and userid='"+userid+"' and resourceid='"+resourceid+"' and fld1='"+functionid+"'");
				String id = "0";
				if(list!=null && list.size()>0){
					HashMap map = (HashMap)list.get(0);
					id = map.get("id")==null?"0":(String)map.get("id");
				}else{
					MwpmSysNewCollect sbc = new MwpmSysNewCollect();
					sbc.setUserid(userid);
					sbc.setResourceid(resourceid);
					sbc.setBuytype("zx");
					sbc.setFld1(functionid);
					ceiDictDao.saveObject(sbc);
					id = sbc.getId();
				}
				xmlMessage += "<RETURNCODE>"+id+"</RETURNCODE>";
			} catch (Exception e) {
				xmlMessage += "<RETURNCODE>0</RETURNCODE>";
				e.printStackTrace();
			}
		} else {
			xmlMessage += "<RETURNCODE>-1</RETURNCODE>";
		}
		xmlMessage += "</ROOT>";
		return xmlMessage;
	}

	
	/**
	 * 删除资讯收藏
	 * @author tanJie
	 */
	@Override
	public String delCollect(String xml) {
		String error = "";
		String xmlMessage = "<?xml version='1.0' encoding='utf-8'?>" + "<ROOT>";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String userid = xmlStrMap.get("userid")==null?"":xmlStrMap.get("userid");
			String resourceid = xmlStrMap.get("resourceid")==null?"":xmlStrMap.get("resourceid");
			String functionid = xmlStrMap.get("functionid")==null?"":xmlStrMap.get("functionid");
			try {
				List list = ceiDictDao.getALLData("select new map(a.id as id) from MwpmSysNewCollect a" +
						" where a.buytype='zx' and a.userid='"+userid+"' and a.resourceid='"+resourceid+"' and a.fld1='"+functionid+"'");
				if(list!=null && list.size()>0){
					HashMap map = (HashMap)list.get(0);
					MwpmSysNewCollect sbc = new MwpmSysNewCollect();
					String id = map.get("id")==null?"0":(String)map.get("id");
					sbc.setId(id);
					ceiDictDao.delObject(sbc);
					xmlMessage += "<RETURNCODE>1</RETURNCODE>";
				}else{
					xmlMessage += "<RETURNCODE>-2</RETURNCODE>";
				}
			} catch (Exception e) {
				xmlMessage += "<RETURNCODE>0</RETURNCODE>";
			}
		} else {
			xmlMessage += "<RETURNCODE>-1</RETURNCODE>";
		}
		xmlMessage += "</ROOT>";
		return xmlMessage;
	}
	
	/**
	 * 查询资讯收藏信息
	 * @author tanJie
	 */
	@Override
	public String queryCollect(String xml) {
		String error = "";
		String xmlMessage = "<?xml version='1.0' encoding='utf-8'?>" + "<ROOT>";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String userid = xmlStrMap.get("userid")==null?"":xmlStrMap.get("userid");
			String hql = "select new map( c.id as id,c.title as title,c.subhead as subhead,c.time as time,c.isfree as isfree,bc.fld1 as functionid) " +
			"from MwpmSysNews c,MwpmSysNewCollect bc where bc.resourceid=c.id " +
			"and bc.buytype='zx' and c.status='1' and bc.userid='"+userid+"'";
			List list = ceiDictDao.getALLData(hql);
			try {
				if(list!=null && list.size()>0){
					String fulladdressPath = CommonParm.getString("FULL_ADDRESS_PATH"); 
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					xmlMessage += "<newsgroup>";
					for (int i = 0; i < list.size(); i++) {
						HashMap map = (HashMap)list.get(i);
						String id = map.get("id")==null?"":(String)map.get("id");
						String title = map.get("title")==null?"":(String)map.get("title");
						String subhead = map.get("subhead")==null?"":(String)map.get("subhead");
						String isfree = map.get("isfree")==null?"":map.get("isfree").toString();
						String functionid = map.get("functionid")==null?"":(String)map.get("functionid");
						Date time = (Date) map.get("time");
						String creattime = "";
						if(time!=null){
							creattime = sdf.format(time);
						}
						xmlMessage += "<new>";
						xmlMessage += "<id>"+id+"</id>";
						xmlMessage += "<title>"+title+"</title>";
						xmlMessage += "<subhead>"+subhead+"</subhead>";
						xmlMessage += "<time>"+creattime+"</time>";
						xmlMessage += "<isfree>"+isfree+"</isfree>";
						xmlMessage += "<functionid>"+functionid+"</functionid>";
						xmlMessage += "</new>";
					}
					xmlMessage += "</newsgroup>";
				}
			} catch (Exception e) {
				xmlMessage += "<RETURNCODE>0</RETURNCODE>";
			}
		} else {
			xmlMessage += "<RETURNCODE>-1</RETURNCODE>";
		}
		xmlMessage += "</ROOT>";
		return xmlMessage;
	}
	
	

	/**
	 * 查询用户购买的资讯栏目
	 * @author tanJie
	 */
	@Override
	public String queryBuyNews(String xml) {
		String error = "";
		String xmlMessage = "<?xml version='1.0' encoding='utf-8'?>" + "<ROOT>";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String userid = xmlStrMap.get("userid")==null?"":xmlStrMap.get("userid");
			String resourcetype = xmlStrMap.get("resourcetype")==null?"new":xmlStrMap.get("resourcetype");
			String hql = "select new map(m.funid as funid) " +
					"from MwpmSysMoney m where 0=0 "; 
				if(resourcetype.equals("new")){
					hql = hql +" and m.resourcetype ='new' ";
				}
				if(resourcetype.equals("db")){
					hql = hql +" and m.resourcetype ='db' ";
				}
				hql = hql + "and m.status='wdq' and m.userid='"+userid+"'";
			List list = ceiDictDao.getALLData(hql);
			try {
				if(list!=null && list.size()>0){
					xmlMessage += "<moneygroup>";
					for (int i = 0; i < list.size(); i++) {
						HashMap map = (HashMap)list.get(i);
						String funid = map.get("funid")==null?"":(String)map.get("funid");
						String hql1 = "select new map(f.id as id) " +
						"from MwpmDictFunction f where 0=0 and f.path like '%"+funid+"%' or f.id='"+funid+"'"; 
						List list1 = ceiDictDao.getALLData(hql1);
						if(list1!=null && list1.size()>0){
							for (int j = 0; j < list1.size(); j++) {	
								HashMap map1 = (HashMap)list1.get(j);
								String id = map1.get("id")==null?"":(String)map1.get("id");
								xmlMessage += "<money>";
								xmlMessage += "<funid>"+id+"</funid>";
								xmlMessage += "</money>";
							}
						}
					}
					xmlMessage += "</moneygroup>";
				}else{

					xmlMessage += "<moneygroup><money><funid>-11</funid></money></moneygroup>";
				}
			} catch (Exception e) {
				xmlMessage += "<RETURNCODE>0</RETURNCODE>";
			}
		} else {
			xmlMessage += "<RETURNCODE>-1</RETURNCODE>";
		}
		xmlMessage += "</ROOT>";
		return xmlMessage;
	}
	
	
	/**
	 * 查询通知公告
	 * @author tanJie
	 */
	@Override
	public String queryNotice(String xml) {
		String error = "";
		String xmlMessage = "<?xml version='1.0' encoding='utf-8'?>" + "<ROOT>";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String userid = xmlStrMap.get("userid")==null?"":xmlStrMap.get("userid");
			List unitidList = ceiDictDao.getALLData("select new map(unitid as unitid) from MwpmSysUserinfo where userid='"+userid+"'");
			if(unitidList!=null && unitidList.size()>0){
				HashMap unitMap = (HashMap)unitidList.get(0);
				String unitid = unitMap.get("unitid")==null?"":unitMap.get("unitid").toString();
				if(!unitid.equals("")){
					List pathList = ceiDictDao.getALLData("select new map(path as path) from MwpmSysOrginfo where id='"+unitid+"'");
					if(pathList!=null && pathList.size()>0){
						HashMap pathMap = (HashMap)pathList.get(0);
						String path = pathMap.get("path")==null?"":pathMap.get("path").toString();
						String[] paths = path.split("/");
						String ids = "";
						for (int i = 0; i < paths.length; i++) {
							ids = ids +"'"+paths[i]+"',";
						}
						ids = ids +"'"+unitid+"'";
						List list = ceiDictDao.getALLData("select new map(n.id as id,n.title as title,n.subhead as subhead,n.creattime as time) "+
								"from MwpmSysNotice n where "+
								"n.orgid in ("+ids+") order by n.creattime desc");
						try {
			 	 			if(list!=null && list.size()>0){
			 					xmlMessage += "<newsgroup>";
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			 	 				for (int i = 0; i < list.size(); i++) {
									HashMap map = (HashMap)list.get(i);
									String id = map.get("id")==null?"":(String)map.get("id");
									String title = map.get("title")==null?"":(String)map.get("title");
									String subhead = map.get("subhead")==null?"":(String)map.get("subhead");
									Date time = (Date)map.get("time");
									String time1 = sdf.format(time);
				 					xmlMessage += "<new>";
				 					xmlMessage += "<id>"+id+"</id>";
				 					xmlMessage += "<title>"+title+"</title>";
				 					xmlMessage += "<subhead>"+subhead+"</subhead>";
				 					xmlMessage += "<time>"+time1+"</time>";
				 					xmlMessage += "</new>";
								}
			 					xmlMessage += "</newsgroup>";
			 				}else{
			 					xmlMessage += "<RETURNCODE>5</RETURNCODE>";
			 				}
						} catch (Exception e) {
							xmlMessage += "<RETURNCODE>0</RETURNCODE>";
						}
					}
				}
			}
		} else {
			xmlMessage += "<RETURNCODE>-1</RETURNCODE>";
		}
		xmlMessage += "</ROOT>";
		return xmlMessage;
	}
	
	
	
	/**
	 * 查询用户购买课件和报告(PC同步工具)（业务与分类下的）
	 * @author tanJie
	 */
	@Override
	public String queryClassAndReport(String xml) {
		String error = "";
		String xmlMessage = "<?xml version='1.0' encoding='utf-8'?>" + "<ROOT>";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String loginname = xmlStrMap.get("loginname")==null?"":xmlStrMap.get("loginname");
			String password = xmlStrMap.get("password")==null?"":xmlStrMap.get("password");
			String imsitype = xmlStrMap.get("imsitype")==null?"pad":xmlStrMap.get("imsitype");
			try { 
				DesUtils d=new DesUtils();
				loginname = d.encrypt(loginname);
				password = d.encrypt(password);
				//验证用户信息
				List userInfoList = ceiDictDao.getInfo("select f.userid as userid,f.loginname as loginname,f.password as password,f.unitid as unitid," +
						"f.functionid as functionid,f.UNITNAME as unitname" +
						" from MWPM_SYS_USERINFO f " +
						"where 1=1 and f.loginname='"+loginname+"' and f.password='"+password+"'");
				if(userInfoList!=null && userInfoList.size()>0){
					String fulladdressPath = CommonParm.getString("FULL_ADDRESS_PATH");
					String bookPath = CommonParm.getString("BOOK_ZIP_PATH");
					String url = "";
					HashMap map = (HashMap)userInfoList.get(0);
					String userid = map.get("userid").toString();
					//获取设备号，供客户端比对
					String imsisql = "select imsicode from MWPM_SYS_IMSICODE where userid='"+userid+"'";
					if(imsitype.equals("pad")){
						imsisql = imsisql+" and type='apad'";
						url = "/an1";
					}
					if(imsitype.equals("phone")){
						imsisql = imsisql+" and type='aphone'";
						url = "/an2";
					}
					List imsicodeList =  ceiDictDao.getInfo(imsisql);
					if(imsicodeList!=null && imsicodeList.size()>0){
						HashMap imsicodeMap = (HashMap)imsicodeList.get(0);
						String imsicode = imsicodeMap.get("imsicode")==null?"":imsicodeMap.get("imsicode").toString();
						xmlMessage += "<imsicode>"+imsicode+"</imsicode>";
					}
					//查询用户购买的资源
					String sql = "select m.BUYTYPE as buytype,m.RESOURCETYPE as resourcetype,m.FUNID as funid" +
							" from MWPM_SYS_MONEY m where m.STATUS='wdq' and m.userid='"+userid+"'";
					List moneyList = ceiDictDao.getInfo(sql);
					String kjywid = "";
					String kjid="";
					String bgid="";
					List list1 = new ArrayList();
					if(moneyList!=null && moneyList.size()>0){
						for (int i = 0; i < moneyList.size(); i++) {
							HashMap moneyMap = (HashMap)moneyList.get(i);
							String buytype = moneyMap.get("buytype")==null?"":moneyMap.get("buytype").toString();
							String resourcetype = moneyMap.get("resourcetype")==null?"":moneyMap.get("resourcetype").toString();
							String funid = moneyMap.get("funid")==null?"":moneyMap.get("funid").toString();
							if(buytype.equals("yw")){
								List list =this.queryTreeFunction(list1,funid);
								if(list!=null && list.size()>0){
									for (int j = 0; j < list.size(); j++) {
										HashMap fidMap = (HashMap)list.get(j);
										String functionid = fidMap.get("id").toString();
										kjywid = kjywid +" oc.functionid ='"+functionid+"' or";
									}
								}
							}
							if(buytype.equals("zy") && resourcetype.equals("kj")){
								kjid = kjid +" c.id ='"+funid+"' or";
							}
							if(buytype.equals("zy") && resourcetype.equals("bg")){
								bgid = bgid +" r.id ='"+funid+"' or";
							}
						}
						
					List operationList =new ArrayList();
					List operatiList =new ArrayList();
					if(kjywid.length()>3){
						//通过functionid集合查询业务下分类集合(课件和报告集合)
						operationList = ceiDictDao.getInfo("select c.resourceid,c.type " +
								"from MWPM_SYS_CLASSIFICATION c left join MWPM_SYS_OPERATION_CLASS oc on c.TYPEID=oc.CLASSID " +
								"where 0=0 and ("+kjywid.substring(0, kjywid.length()-3)+")");

						//通过functionid集合查询业务下直接对应的课件和报告集合
						operatiList = ceiDictDao.getInfo("select oc.classid,oc.type " +
								"from MWPM_SYS_OPERATI_RESOURCE oc " +
								"where 0=0 and ("+kjywid.substring(0, kjywid.length()-3)+")");
					}
					if(operationList!=null && operationList.size()>0){
						for (int i = 0; i < operationList.size(); i++) {
							HashMap resourceMap = (HashMap)operationList.get(i);
							String resourceid = resourceMap.get("resourceid")==null?"":resourceMap.get("resourceid").toString();
							String type = resourceMap.get("type")==null?"":resourceMap.get("type").toString();
							if(type.equals("kj")){
								kjid = kjid+" c.id='"+resourceid+"' or";
							}
							if(type.equals("bg")){
								bgid = bgid+" r.id='"+resourceid+"' or";
							}
						}
						
					}
					if(operatiList!=null && operatiList.size()>0){
						for (int i = 0; i < operatiList.size(); i++) {
							HashMap classMap = (HashMap)operatiList.get(i);
							String classid = classMap.get("classid")==null?"":classMap.get("classid").toString();
							String type = classMap.get("type")==null?"":classMap.get("type").toString();
							if(type.equals("kj")){
								kjid = kjid+" c.id='"+classid+"' or";
							}
							if(type.equals("bg")){
								bgid = bgid+" r.id='"+classid+"' or";
							}
						}
						
					}
					//得到报告id集合查询购买的报告
					String reportsql="select r.name,re.path,re.type " +
									  "from MWPM_SYS_REPORT r left join MWPM_SYS_RESOURCEPATH re on r.id=re.RESOURCEID " +
									  "where re.type='bg' ";
					//得到课件id集合查询购买的课件
					String classsql = "select c.name,re.path,re.type " +
									  "from MWPM_SYS_CLASS c left join MWPM_SYS_RESOURCEPATH re on c.id=re.RESOURCEID " +
									  "where re.type='kj' ";
					List reportList = new ArrayList();
					List classList =  new ArrayList();
					if(!bgid.equals("")){
						reportsql = reportsql+" and ("+bgid.substring(0,bgid.length()-3)+")";
						reportList = ceiDictDao.getInfo(reportsql);
					}
					if(!kjid.equals("")){
						classsql = classsql+" and ("+kjid.substring(0,kjid.length()-3)+")";
						classList = ceiDictDao.getInfo(classsql);
					}
					if(reportList!=null && reportList.size()>0){
						for (int i = 0; i < reportList.size(); i++) {
							xmlMessage += "<resource>";
							HashMap reprotMap = (HashMap)reportList.get(i);
							String name = reprotMap.get("name")==null?"":reprotMap.get("name").toString();
							String path = reprotMap.get("path")==null?"":reprotMap.get("path").toString();
							String type = reprotMap.get("type")==null?"":reprotMap.get("type").toString();
							String downpath=fulladdressPath+bookPath+path+"/bg.zip";
							xmlMessage += "<name>"+name+"</name>";
							xmlMessage += "<downpath>"+downpath+"</downpath>";
							xmlMessage += "<type>"+type+"</type>";
							xmlMessage += "</resource>";
						}
					}
					if(classList!=null && classList.size()>0){
						for (int i = 0; i < classList.size(); i++) {
							xmlMessage += "<resource>";
							HashMap reprotMap = (HashMap)classList.get(i);
							String name = reprotMap.get("name")==null?"":reprotMap.get("name").toString();
							String path = reprotMap.get("path")==null?"":reprotMap.get("path").toString();
							String type = reprotMap.get("type")==null?"":reprotMap.get("type").toString();
							String downpath=fulladdressPath+bookPath+path+url+".zip";
							xmlMessage += "<name>"+name+"</name>";
							xmlMessage += "<downpath>"+downpath+"</downpath>";
							xmlMessage += "<type>"+type+"</type>";
							xmlMessage += "</resource>";
						}
					}

				}else{
					xmlMessage += "<RETURNCODE>4</RETURNCODE>";
				}
				}else{
					xmlMessage += "<RETURNCODE>5</RETURNCODE>";
				}

			} catch (Exception e) {
				xmlMessage += "<RETURNCODE>0</RETURNCODE>";
			}
		} else {
			xmlMessage += "<RETURNCODE>-1</RETURNCODE>";
		}
		xmlMessage += "</ROOT>";
		return xmlMessage;
	}
	
	
	

	/**
	 * 按报告名称模糊查询报告列表
	 * @author tanJie
	 */
	@Override
	public String queryReportByName(String xml) {
		String error = "";
		String xmlMessage = "<?xml version='1.0' encoding='utf-8'?>" + "<ROOT>";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		final EmailUtil springMail = new EmailUtil();
		if ("".equals(error)) {
			String functionids = xmlStrMap.get("functionids")==null?"":xmlStrMap.get("functionids");
			String is_free = xmlStrMap.get("isfree")==null?"":xmlStrMap.get("isfree");
			String isnew = xmlStrMap.get("isnew")==null?"":xmlStrMap.get("isnew");
			String reportname = xmlStrMap.get("reportname")==null?"":xmlStrMap.get("reportname");
			String imagetype = xmlStrMap.get("imagetype")==null?"":xmlStrMap.get("imagetype");
			String isDownsum = xmlStrMap.get("isdownsum")==null?"":xmlStrMap.get("isdownsum");//是否以下载量排序,当为y时代表以下载量排序
			String num = xmlStrMap.get("num")==null?"18":xmlStrMap.get("num");
			String fulladdressPath = CommonParm.getString("FULL_ADDRESS_PATH");
			String bookPath = CommonParm.getString("BOOK_ZIP_PATH");
			String LOOK_PATH = CommonParm.getString("LOOK_PATH");
			String photoPath = CommonParm.getString("PHOTO_PATH");
			String reportnewNum = CommonParm.getString("REPORTNEW_NUM");
			String reportNum = CommonParm.getString("REPORT_NUM");
			String rnum = isnew.equals("1")?reportnewNum:reportNum;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 			try {
 				String[] functionid = functionids.split(",");
 				String fid = "";
 				String classFid="";
 				if(functionid!=null && functionid.length>0){
 					fid = "and (";
 					classFid = "and (";
 					for (int i = 0; i < functionid.length; i++) {
 	 					if(i==0){
 	 						fid = fid+" re.functionid='"+functionid[i]+"' ";
 	 						classFid = classFid+" f.id='"+functionid[i]+"' ";
 	 					}else{
 	 						fid=fid+" or re.functionid='"+functionid[i]+"' ";
 	 						classFid=classFid+" or f.id='"+functionid[i]+"' ";
 	 					}
					}
 					fid = fid + ") ";
 					classFid = classFid + ") ";
 				}	
 				String orderby="";
// 				int pageNum=0;
// 				if(!num.equals("")){
// 					pageNum=Integer.parseInt(num);					
//				}
 				if(isDownsum!=null && isDownsum.length()>0 && isDownsum.equalsIgnoreCase("y")){
 					orderby=" order by r.downsum desc";
 				}else{
 					orderby=" order by r.time desc";
 				}
 				/*String sql = "select re.classid as classid,re.ischeck as ischeck,r.name as name,r.AUTHOR as author,"+
				"r.intro as intro,r.TIME as creattime,r.price as price,rp.path as path "+
				"from MWPM_SYS_OPERATI_RESOURCE re left join MWPM_SYS_REPORT r on re.classid=r.id "+
				"left join MWPM_SYS_RESOURCEPATH rp on re.classid=rp.RESOURCEID "+
				"where 1=1 and r.status='1' "+fid+" and rp.type='bg'";
 				*/
 				
 				 
 						  
 				String sql = "SELECT TOP "+rnum+" *  FROM ( SELECT ROW_NUMBER() OVER ("+orderby+") AS RowNumber," +
 						" r.id AS classid, name, author, intro,r.isfree as isfree," +
 						" r.time AS creattime, price,r.passkey as passkey, r.path AS path,r.scroll as scroll, downsum "+
 				" FROM   (SELECT r.id, r.name, r.author, r.intro, r.time, r.price,r.isfree,rp.passkey, rp.path,r.scroll,r.downsum"+
 				" FROM   MWPM_SYS_OPERATI_RESOURCE AS re INNER JOIN"+
 				"   MWPM_SYS_REPORT AS r ON re.CLASSID = r.ID INNER JOIN"+
 				"   MWPM_SYS_RESOURCEPATH AS rp ON re.CLASSID = rp.RESOURCEID"+
 				" WHERE   (r.STATUS = '1') AND (rp.TYPE = 'bg') "+fid;
 				if(!is_free.equals("")){
 					sql = sql + " and r.isfree='"+is_free+"'";
 				}
 				sql = sql+" UNION "+
 				" SELECT  DISTINCT    r.id, r.name, r.author, r.intro, r.time, r.price,r.isfree,rp.passkey, rp.path, r.scroll, r.downsum"+
 				 " FROM   MWPM_SYS_PHONE_FUNCTION AS f INNER JOIN"+
 				 " MWPM_SYS_OPERATION_CLASS AS fc ON f.ID = fc.FUNCTIONID INNER JOIN"+
 				 " MWPM_SYS_REPORT_TYPE AS rt ON fc.CLASSID = rt.ID INNER JOIN"+
 				 " MWPM_SYS_CLASSIFICATION AS rc ON rt.ID = rc.TYPEID INNER JOIN"+
 				 " MWPM_SYS_REPORT AS r ON rc.RESOURCEID = r.ID INNER JOIN"+
 				 " MWPM_SYS_RESOURCEPATH AS rp ON r.ID = rp.RESOURCEID"+
 				 " WHERE (fc.TYPE = 'bg') AND (rc.TYPE = 'bg') and r.status='1' "+classFid;
 				if(!is_free.equals("")){
 					sql = sql + " and r.isfree='"+is_free+"'";
 				}
 				sql = sql+") as r";
 				
				if(!reportname.equals("")){
					sql = sql+" where r.NAME like '%"+reportname+"%'";
				}
				sql = sql+") A WHERE RowNumber > "+rnum+" *("+num+"-1)";
				String sqlString = "";
				List classList = ceiDictDao.getInfo(sql);
				if(classList != null && classList.size()>0){
					xmlMessage += "<classgroup>";
					for (int i = 0; i < classList.size(); i++) {
						HashMap map = (HashMap)classList.get(i);
						String classid = map.get("classid")==null?"":(String)map.get("classid");
						String name = map.get("name")==null?"":(String)map.get("name");
						String author = map.get("author")==null?"":(String)map.get("author");
						String path = map.get("path")==null?"":(String)map.get("path");
						String intro = map.get("intro")==null?"":(String)map.get("intro");
						String price = map.get("price")==null?"":(String)map.get("price");
						String scroll = map.get("scroll")==null?"":(String)map.get("scroll");
						String passkey = map.get("passkey")==null?"":(String)map.get("passkey");
						int downsum = map.get("downsum")==null?0:Integer.parseInt(map.get("downsum").toString());
						String isfree = map.get("isfree")==null?"":((Character)map.get("isfree")).toString();
						Date creattime1 = (Date) map.get("creattime");						
						String creattime = "";
						if(creattime1!=null){
							creattime = sdf.format(creattime1);
						}						
						String lookpath=fulladdressPath+photoPath+path;
						String downpath=fulladdressPath+bookPath+path+"/bg.zip";
						xmlMessage += "<class>";
						xmlMessage += "<classid>"+classid+"</classid>";
						xmlMessage += "<name>"+name+"</name>";
						xmlMessage += "<author>"+author+"</author>";
						xmlMessage += "<path>"+lookpath+"</path>";
						xmlMessage += "<creattime>"+creattime+"</creattime>";
						xmlMessage += "<downpath>"+downpath+"</downpath>";
						xmlMessage += "<intro>"+intro+"</intro>";
						xmlMessage += "<price>"+price+"</price>";
						xmlMessage += "<scroll>"+scroll+"</scroll>";
						xmlMessage += "<passkey>"+passkey+"</passkey>";
						xmlMessage += "<isfree>"+isfree+"</isfree>";
						xmlMessage += "<downsum>"+downsum+"</downsum>";
						xmlMessage += "</class>";
					}
					xmlMessage += "</classgroup>";
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
	 * 按functionid查询子菜单
	 * @author tanJie
	 */
	private List queryTreeFunction(List alist, String functionid) {
		List functionList = ceiDictDao.getALLData("select new map(f.id as id,f.parentid as parentid,f.name as name,f.type as type) " +
				"from MwpmDictFunction f where f.parentid='"+functionid+"'");
		if(functionList!=null && functionList.size()>0){
			for (int j = 0; j < functionList.size(); j++) {
				HashMap functionMap = (HashMap)functionList.get(j);
				String id = functionMap.get("id")==null?"":functionMap.get("id").toString();
				String type = functionMap.get("type")==null?"":functionMap.get("type").toString();
				if(!type.equals("")){
					alist.add(functionMap);
				}else{
					this.queryTreeFunction(alist,id);
				}
			}
		}else{
			List list = ceiDictDao.getALLData("select new map(f.id as id,f.parentid as parentid,f.name as name,f.type as type) " +
					"from MwpmDictFunction f where f.id='"+functionid+"'");
			if(list!=null && list.size()>0){
				HashMap functionMap = (HashMap)list.get(0);
				alist.add(functionMap);
			}
		}
		return alist;
	}
	
	/**
	 * 清空资讯收藏
	 * @author tanJie
	 */
	public String clearCollect(String xml){
		String error = "";
		String xmlMessage = "<?xml version='1.0' encoding='utf-8'?>" + "<ROOT>";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String userid = xmlStrMap.get("userid")==null?"":xmlStrMap.get("userid");			
			try {
				ceiDictDao.updateXYInfo("delete   MwpmSysNewCollect  a where a.buytype='zx' and a.userid='"+userid+"' ");																			
					xmlMessage += "<RETURNCODE>1</RETURNCODE>";
				
			} catch (Exception e) {
				xmlMessage += "<RETURNCODE>0</RETURNCODE>";
			}
		} else {
			xmlMessage += "<RETURNCODE>-1</RETURNCODE>";
		}
		xmlMessage += "</ROOT>";
		return xmlMessage;
	}
/*
 * 显示全部的免费报告
 * */
	public String queryAllFreeReport(String xml){
		String error = "";
		String xmlMessage = "<?xml version='1.0' encoding='utf-8'?>" + "<ROOT>";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String num = xmlStrMap.get("num")==null?"":xmlStrMap.get("num");
			String imagetype = xmlStrMap.get("imagetype")==null?"":xmlStrMap.get("imagetype");
			String fulladdressPath = CommonParm.getString("FULL_ADDRESS_PATH");
			String bookPath = CommonParm.getString("BOOK_ZIP_PATH");
			String photoPath = CommonParm.getString("PHOTO_PATH");
			try {
				PageBean pageBean=new PageBean();
				pageBean.setPageNo(1);				
				String hql="select new map(a.id as id,a.name as name,a.intro as intro,a.author as author,a.isfree as isfree,b.path as path,b.passkey as passkey,a.scroll as scroll) " +
						" from MwpmSysReport as a,MwpmSysResourcepath as b where a.id=b.resourceid and a.isfree='1' " +
						" and b.type='bg' and a.status='1'";
				if(!num.equals("")){
					pageBean.setPageSize(Integer.parseInt(num));					
				}else{
					pageBean.setPageSize(1);
				}				
				List ls=ceiDictDao.getPageBeanList(pageBean,hql);
				if(ls!=null && ls.size()>0){
					for (int j = 0; j < ls.size(); j++) {
						HashMap functionMap = (HashMap)ls.get(j);
						String id = functionMap.get("id")==null?"":functionMap.get("id").toString();
						String name = functionMap.get("name")==null?"":functionMap.get("name").toString();
						String intro = functionMap.get("intro")==null?"":functionMap.get("intro").toString();
						String author = functionMap.get("author")==null?"":functionMap.get("author").toString();
						String path = functionMap.get("path")==null?"":(String)functionMap.get("path");
						String scroll = functionMap.get("scroll")==null?"":(String)functionMap.get("scroll");
						String passkey = functionMap.get("passkey")==null?"":(String)functionMap.get("passkey");
						String isfree = functionMap.get("isfree")==null?"":(String)functionMap.get("isfree");
						xmlMessage += "<class>";
						xmlMessage += "<id>"+id+"</id>";
						xmlMessage += "<name>"+name+"</name>";
						xmlMessage += "<intro>"+intro+"</intro>";
						xmlMessage += "<author>"+author+"</author>";	
						xmlMessage += "<scroll>"+scroll+"</scroll>";	
						xmlMessage += "<passkey>"+passkey+"</passkey>";	
						xmlMessage += "<isfree>"+isfree+"</isfree>";	
						String url = "";
						if(imagetype.equals("androidpad")){
							url = "/an1";
						}
						else if(imagetype.equals("ipad")){
							url = "/i1";
						}
						else if(imagetype.equals("android")){
							url = "/an2";
						}else{
							url = "/i2";
						}
						String lookpath=fulladdressPath+photoPath+path;
						String downpath=fulladdressPath+bookPath+path+"/bg.zip";
						xmlMessage += "<path>"+lookpath+"</path>";
						xmlMessage += "<downpath>"+downpath+"</downpath>";
						xmlMessage += "</class>";
					}
				}									
			} catch (Exception e) {
				xmlMessage += "<RETURNCODE>0</RETURNCODE>";
			}
		} else {
			xmlMessage += "<RETURNCODE>-1</RETURNCODE>";
		}
		xmlMessage += "</ROOT>";
		return xmlMessage;
	}
	
/*取得某一分类下的所有报告列表,在这里不包括直指报告*/
	public String queryAllClassTypeReport(String xml){
		String error = "";
		String xmlMessage = "<?xml version='1.0' encoding='utf-8'?>" + "<ROOT>";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String num = xmlStrMap.get("num")==null?"":xmlStrMap.get("num");
			String imagetype = xmlStrMap.get("imagetype")==null?"":xmlStrMap.get("imagetype");
			String reporttypeid = xmlStrMap.get("reporttypeid")==null?"":xmlStrMap.get("reporttypeid");
			String fulladdressPath = CommonParm.getString("FULL_ADDRESS_PATH");
			String bookPath = CommonParm.getString("BOOK_ZIP_PATH");
			String photoPath = CommonParm.getString("PHOTO_PATH");
			String reportnum = CommonParm.getString("REPORT_NUM");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				PageBean pageBean=new PageBean();
				pageBean.setPageNo(1);	
				String rtid = "";
				if(!reporttypeid.equals("")){
					String[] rtids = reporttypeid.split(",");
					for (int i = 0; i < rtids.length; i++) {
						if(i==0){
							rtid = "rc.typeid = '"+rtids[i]+"'";
						}else{
							rtid = rtid +" or rc.typeid = '"+rtids[i]+"'";
						}
					}
				}

				String sql = " SELECT TOP "+reportnum+" *  FROM (" +
				 "SELECT ROW_NUMBER() OVER (ORDER BY r.id desc) AS RowNumber," +
				"  r.id as reportid, r.name as name, r.author as author, r.intro as intro,r.scroll as scroll,"+
				"r.time as creattime, r.price as price,r.isfree as isfree, rp.path as path,rp.passkey as passkey"+
				" FROM  "+
				" MWPM_SYS_CLASSIFICATION as rc ,"+
				" MWPM_SYS_REPORT as r ,"+
				" MWPM_SYS_RESOURCEPATH as rp "+
				" WHERE  rc.resourceid = r.id and r.id = rp.resourceid and "+
				" (rc.type = 'bg') AND ("+rtid+") and r.status='1'"+
				 ") A WHERE RowNumber > "+reportnum+"*("+num+"-1)";
				
				List ls=ceiDictDao.getInfo(sql);
				if(ls!=null && ls.size()>0){
					for (int j = 0; j < ls.size(); j++) {
						HashMap functionMap = (HashMap)ls.get(j);
						String id = functionMap.get("reportid")==null?"":functionMap.get("reportid").toString();
						String name = functionMap.get("name")==null?"":functionMap.get("name").toString();
						String intro = functionMap.get("intro")==null?"":functionMap.get("intro").toString();
						String author = functionMap.get("author")==null?"":functionMap.get("author").toString();
						String path = functionMap.get("path")==null?"":(String)functionMap.get("path");
						String price = functionMap.get("price")==null?"":(String)functionMap.get("price");
						String scroll = functionMap.get("scroll")==null?"":(String)functionMap.get("scroll");
						String passkey = functionMap.get("passkey")==null?"":(String)functionMap.get("passkey");
						String isfree = functionMap.get("isfree")==null?"":functionMap.get("isfree").toString();
						Date creattime1 = (Date) functionMap.get("creattime");						
						String creattime = "";
						if(creattime1!=null){
							creattime = sdf.format(creattime1);
						}
						xmlMessage += "<class>";
						xmlMessage += "<id>"+id+"</id>";
						xmlMessage += "<name>"+name+"</name>";
						xmlMessage += "<intro>"+intro+"</intro>";
						xmlMessage += "<author>"+author+"</author>";
						xmlMessage += "<creattime>"+creattime+"</creattime>";
						xmlMessage += "<price>"+price+"</price>";
						xmlMessage += "<scroll>"+scroll+"</scroll>";
						xmlMessage += "<passkey>"+passkey+"</passkey>";
						xmlMessage += "<isfree>"+isfree+"</isfree>";
						String url = "";
						if(imagetype.equals("androidpad")){
							url = "/an1";
						}
						else if(imagetype.equals("ipad")){
							url = "/i1";
						}
						else if(imagetype.equals("android")){
							url = "/an2";
						}else{
							url = "/i2";
						}
						String lookpath=fulladdressPath+photoPath+path;
						String downpath=fulladdressPath+bookPath+path+"/bg.zip";
						xmlMessage += "<path>"+lookpath+"</path>";
						xmlMessage += "<downpath>"+downpath+"</downpath>";
						xmlMessage += "</class>";
					}
				}									
			} catch (Exception e) {
				xmlMessage += "<RETURNCODE>0</RETURNCODE>";
			}
		} else {
			xmlMessage += "<RETURNCODE>-1</RETURNCODE>";
		}
		xmlMessage += "</ROOT>";
		return xmlMessage;
	}
	
	
	
	
	
	
	
	/**
	 * 
	 * @author tanJie
	 */
	@Override
	public String addFunctionTree(String xml) {
		String error = "";
		String xmlMessage = "<?xml version='1.0' encoding='utf-8'?>" + "<ROOT>";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String roleid = xmlStrMap.get("roleid")==null?"":xmlStrMap.get("roleid");
			try {
				//生成角色对应的功能树菜单
				/**
				List list = ceiDictDao.getALLData("select new map(r.roleid as roleid) from MwpmSysRoleinfo r where r.type='tel'");
				if(list!=null && list.size()>0){
					for (int i = 0; i < list.size(); i++) {
						HashMap map = (HashMap)list.get(i);
						String roleid = map.get("roleid")==null?"":map.get("roleid").toString(); 
						this.queryRoleIdByFunction(roleid);
					}
				}
				**/
				
				//查询角色对应的功能树
				/**
				String xmlPath = CommonParm.getString("XML_PATH");
				List list =  classExportXmlDao.findClass(xmlPath+roleid+".xml", "MwpmDictFunction");
				 */
				
//				this.queryFunctionByTree(0, "000111", "000111");
			} catch (Exception e) {
				xmlMessage += "<RETURNCODE>0</RETURNCODE>";
			}
		} else {
			xmlMessage += "<RETURNCODE>-1</RETURNCODE>";
		}
		xmlMessage += "</ROOT>";
		return xmlMessage;
	}
	

	
	/**
	 * 按functionid查询子菜单
	 * 传入参数:a 
	 * @author tanJie
	 */
	/*private void queryFunctionByTree(int a,String functionid,String fid) {
		List functionList = ceiDictDao.getALLData("select new map(f.id as id,f.parentid as parentid,f.name as name,f.type as type) " +
				"from MwpmDictFunction f where f.parentid='"+functionid+"'");
		if(functionList!=null && functionList.size()>0){
			for (int j = 0; j < functionList.size(); j++) {
				HashMap functionMap = (HashMap)functionList.get(j);
				String id = functionMap.get("id")==null?"":functionMap.get("id").toString();
				String parentid = functionMap.get("parentid")==null?"":functionMap.get("parentid").toString();
				String type = functionMap.get("type")==null?"":functionMap.get("type").toString();
				String name = functionMap.get("name")==null?"":functionMap.get("name").toString();
				if(!type.equals("")){
					MwpmDictFunction f = new MwpmDictFunction();
					f.setId(id);
					f.setParentid(parentid);
					f.setType(type);
					f.setName(name);
					classExportXmlDao.addClass(f, "d:/aaa/"+fid+".xml", "function", a);
					a++;
				}else{
					this.queryFunctionByTree(a,id,fid);
				}
			}
		}else{
			List list = ceiDictDao.getALLData("select new map(f.id as id,f.parentid as parentid,f.name as name,f.type as type) " +
					"from MwpmDictFunction f where f.id='"+functionid+"'");
			if(list!=null && list.size()>0){
				HashMap functionMap = (HashMap)list.get(0);
				String id = functionMap.get("id")==null?"":functionMap.get("id").toString();
				String parentid = functionMap.get("parentid")==null?"":functionMap.get("parentid").toString();
				String type = functionMap.get("type")==null?"":functionMap.get("type").toString();
				String name = functionMap.get("name")==null?"":functionMap.get("name").toString();
				MwpmDictFunction f = new MwpmDictFunction();
				f.setId(id);
				f.setParentid(parentid);
				f.setType(type);
				f.setName(name);
				classExportXmlDao.addClass(f, "d:/aaa/"+fid+".xml", "function", a);
				a++;
			}
		}
	}
	*/
	
	
	/**
	 * 按functionid集合查询所属课件列表
	 * @author tanJie
	 */
	private void queryFunctionIdByClass(String funid,String functionids,int num,int index) {
		String kjid="";
		List operationList =new ArrayList();
		List operatiList =new ArrayList();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String xmlPath = CommonParm.getString("XML_PATH");
		String newclass = CommonParm.getString("NEWCLASS");
		if(functionids.length()>0){
			//通过functionid集合查询业务下课件分类集合
			operationList = ceiDictDao.getALLData("select new map(c.resourceid as resourceid,c.type as type) " +
					"from MwpmSysClassification c ,MwpmSysOperationClass oc where c.typeid=oc.classid " +
					" and c.type='kj' and ("+functionids+")");

			//通过functionid集合查询业务下直接对应的课件集合
			operatiList = ceiDictDao.getALLData("select new map(oc.classid as classid,oc.type as type) " +
					"from MwpmSysOperatiResource oc " +
					"where 0=0 and oc.type='kj' and ("+functionids+")");
		}
		if(operationList!=null && operationList.size()>0){
			for (int i = 0; i < operationList.size(); i++) {
				HashMap resourceMap = (HashMap)operationList.get(i);
				String resourceid = resourceMap.get("resourceid")==null?"":resourceMap.get("resourceid").toString();
				String type = resourceMap.get("type")==null?"":resourceMap.get("type").toString();
				if(type.equals("kj")){
					kjid = kjid+" c.id='"+resourceid+"' or";
				}
			}
			
		}
		if(operatiList!=null && operatiList.size()>0){
			for (int i = 0; i < operatiList.size(); i++) {
				HashMap classMap = (HashMap)operatiList.get(i);
				String classid = classMap.get("classid")==null?"":classMap.get("classid").toString();
				String type = classMap.get("type")==null?"":classMap.get("type").toString();
				if(type.equals("kj")){
					kjid = kjid+" c.id='"+classid+"' or";
				}
			}
			
		}
		List classList = new ArrayList();
		
		//通过获得的课件id查询出对应的课件列表信息
		if(!kjid.equals("")){ 
			if(num!=0 && index!=0){
				String sql = " SELECT TOP "+num+" *  FROM (" +
				 "SELECT ROW_NUMBER() OVER (ORDER BY c.time desc) AS RowNumber," +
				 "c.id as classid,c.name as name,c.teachername as author,c.classlength as classlength,c.isfree as isfree," +
				 "c.intro as intro,c.setnum as setnum,c.TIME as creattime,re.path as path,re.passkey as passkey " +
				 "from MWPM_SYS_CLASS c left join MWPM_SYS_RESOURCEPATH re on c.id=re.RESOURCEID " +
				 "where c.status='1' and re.type='kj' "+
				 " and ("+kjid.substring(0,kjid.length()-3)+")"+
				 ") A WHERE RowNumber > "+num+"*("+index+"-1)";
				classList = ceiDictDao.getInfo(sql);
			}else{
				String classsql = "select c.id as classid,c.name as name,c.teachername as author,c.classlength as classlength,c.isfree as isfree," +
				  "c.intro as intro,c.setnum as setnum,c.TIME as creattime,re.path as path,re.passkey as passkey " +
				  "from MWPM_SYS_CLASS c left join MWPM_SYS_RESOURCEPATH re on c.id=re.RESOURCEID " +
				  "where c.status='1' and re.type='kj' and ("+kjid.substring(0,kjid.length()-3)+") order by c.time desc";
				classList = ceiDictDao.getInfo(classsql);
			}
		}
		
		if(classList!=null && classList.size()>0){
			for (int i = 0; i < classList.size(); i++) {
				HashMap map = (HashMap)classList.get(i);
				String classid = map.get("classid")==null?"":(String)map.get("classid");
				String name = map.get("name")==null?"":(String)map.get("name");
				String author = map.get("author")==null?"":(String)map.get("author");
				String classlength = map.get("classlength")==null?"":(String)map.get("classlength");
				String path = map.get("path")==null?"":(String)map.get("path");
				String intro = map.get("intro")==null?"":(String)map.get("intro");
				Date creattime = (Date) map.get("creattime");
				Integer setnum= map.get("setnum")==null?0:(Integer) map.get("setnum");
				String passkey = map.get("passkey")==null?"":(String)map.get("passkey");
				String isfree = map.get("isfree")==null?"":((Character)map.get("isfree")).toString();
				MwpmSysClass sc = new MwpmSysClass();
				sc.setId(classid);
				sc.setName(name);
				sc.setTeachername(author);
				sc.setClasslength(classlength);
				sc.setPath(path);
				sc.setIntro(intro);
				sc.setTime(creattime);
				sc.setSetnum(setnum);
				sc.setIsfree(isfree);
				sc.setPasskey(passkey);
				classExportXmlDao.addClass(sc, xmlPath+newclass+funid+".xml", "MwpmSysClass", i);
			}
		}
		
	}

	
	
	/**
	 * 查询最新课件列表(返回20条信息)
	 * @author tanJie
	 */
	@Override
	public String queryClassByTime(String xml) {
		String error = "";
		String xmlMessage = "<?xml version='1.0' encoding='utf-8'?>" + "<ROOT>";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		final EmailUtil springMail = new EmailUtil();
		if ("".equals(error)) {
			String functionids = xmlStrMap.get("functionids")==null?"":xmlStrMap.get("functionids");
			String funid = xmlStrMap.get("funid")==null?"":xmlStrMap.get("funid");
			String imagetype = xmlStrMap.get("imagetype")==null?"":xmlStrMap.get("imagetype");
			String fulladdressPath = CommonParm.getString("FULL_ADDRESS_PATH");
			String bookPath = CommonParm.getString("BOOK_ZIP_PATH");
			String LOOK_PATH = CommonParm.getString("LOOK_PATH");
			String xmlPath = CommonParm.getString("XML_PATH");
			String newclass = CommonParm.getString("NEWCLASS");
			String bookRecousePath = CommonParm.getString("BOOK_RECOUSE_PATH");
			String photoPath = CommonParm.getString("PHOTO_PATH");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 			try {
 				String[] functionid = functionids.split(",");
 				String fid = "";
 				if(functionid!=null && functionid.length>0){
 					for (int i = 0; i < functionid.length; i++) {
 	 					if(i==0){
 	 						fid = fid+" oc.functionid='"+functionid[i]+"' ";
 	 					}else{
 	 						fid=fid+" or oc.functionid='"+functionid[i]+"' ";
 	 					}
					}
 				}		
 				//判断xml文件是否存在，如果存在则直接读取xml文件中的数据，不存在则生成xml文件再读取
 				File file=new File(xmlPath+newclass+funid+".xml");
 				if(!file.exists()){
 	 				//生成20条记录
 	 				this.queryFunctionIdByClass(funid, fid, 20, 1);
 				}
				List classList = classExportXmlDao.findClass(xmlPath+newclass+funid+".xml", "MwpmSysClass");
				if(classList != null && classList.size()>0){
					xmlMessage += "<classgroup>";
					for (int i = 0; i < classList.size(); i++) {
						MwpmSysClass map = (MwpmSysClass)classList.get(i);
						String classid = map.getId();
						String name = map.getName();
						String author = map.getTeachername();
						String classlength = map.getClasslength();
						String path = map.getPath();
						String intro = map.getIntro();
						Date creattime1 = (Date) map.getTime();
						String creattime = "";
						if(creattime1!=null){
							creattime = sdf.format(creattime1);
						}
						Integer setnum= map.getSetnum();
						String passkey = map.getPasskey();
						String isfree = map.getIsfree();
						String url = "";
						if(imagetype.equals("androidpad")){
							url = "/an1";
						}
						else if(imagetype.equals("ipad")){
							url = "/i1";
						}
						else if(imagetype.equals("android")){
							url = "/an2";
						}else{
							url = "/i2";
						}
						String lookpath=fulladdressPath+bookRecousePath+path+url+LOOK_PATH;
						String downpath=fulladdressPath+bookPath+path+url+".zip";
						xmlMessage += "<class>";
						xmlMessage += "<classid>"+classid+"</classid>";
						xmlMessage += "<name>"+name+"</name>";
						xmlMessage += "<teachername>"+author+"</teachername>";
						xmlMessage += "<path>"+fulladdressPath+photoPath+path+"</path>";
						xmlMessage += "<classlength>"+classlength+"</classlength>";
						xmlMessage += "<setnum>"+setnum+"</setnum>";
						xmlMessage += "<protime>"+creattime+"</protime>";
						xmlMessage += "<downpath>"+downpath+"</downpath>";
						xmlMessage += "<isfree>"+isfree+"</isfree>";
						xmlMessage += "<lookpath>"+lookpath+"</lookpath>";
						xmlMessage += "<intro>"+intro+"</intro>";
						xmlMessage += "<passkey>"+passkey+"</passkey>";
						xmlMessage += "</class>";
					}
					xmlMessage += "</classgroup>";
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
	 * 根据下载资源信息查询密钥
	 * @author tanJie
	 */
	@Override
	public String queryPassKey(String xml){
		String error = "";
		String xmlMessage = "<?xml version='1.0' encoding='utf-8'?>" + "<ROOT>";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String paths = xmlStrMap.get("paths")==null?"":xmlStrMap.get("paths");			
			try {
				String path = "";
				if(!paths.equals("")){
					String[] pathname = paths.split(",");
					if(pathname!=null && pathname.length>0){
						for (int i = 0; i < pathname.length; i++) {
							if(i==0){
								path = path + " p.path like '%"+pathname[i]+"%'";
							}else{
								path = path + " or p.path like '%"+pathname[i]+"%'";
							}
						}
						String sqlStr = "select new map(p.passkey as passkey,c.id as id,c.name as name,c.time as time,c.classlength as classlength,c.teachername as teachername,p.path as path) " +
						"from MwpmSysResourcepath p,MwpmSysClass c where c.id=p.resourceid and p.type='kj' ";
						if(!path.equals("")){
							sqlStr = sqlStr+"and ( "+path+")";
						}
						List list = ceiDictDao.getALLData(sqlStr);
						if(list!=null && list.size()>0){
							xmlMessage += "<classgroup>";
							for (int i = 0; i < list.size(); i++) {
								HashMap map = (HashMap)list.get(i);
								String passkey = map.get("passkey")==null?"":map.get("passkey").toString();
								String id = map.get("id")==null?"":map.get("id").toString();
								String name = map.get("name")==null?"":map.get("name").toString();
								String teachername = map.get("teachername")==null?"":map.get("teachername").toString();
								Date time = (Date)map.get("time");
								String time1 = "";
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								if(time!=null){
									time1 = sdf.format(time);
								}
								String path1 = map.get("path")==null?"":map.get("path").toString();
								String classlength = map.get("classlength")==null?"":map.get("classlength").toString();
								xmlMessage += "<class>";
								xmlMessage += "<id>"+id+"</id>";
								xmlMessage += "<name>"+name+"</name>";
								xmlMessage += "<protime>"+time1+"</protime>";
								xmlMessage += "<teachername>"+teachername+"</teachername>";
								xmlMessage += "<passkey>"+passkey+"</passkey>";
								xmlMessage += "<downpath>"+path1+"</downpath>";
								xmlMessage += "<classlength>"+classlength+"</classlength>";
								xmlMessage += "</class>";
							}
							xmlMessage += "</classgroup>";
						}else{
							xmlMessage += "<RETURNCODE>5</RETURNCODE>";
						}
					}
				}else{
					xmlMessage += "<RETURNCODE>5</RETURNCODE>";
				}
			} catch (Exception e) {
				xmlMessage += "<RETURNCODE>0</RETURNCODE>";
			}
		} else {
			xmlMessage += "<RETURNCODE>-1</RETURNCODE>";
		}
		xmlMessage += "</ROOT>";
		return xmlMessage;
	}
	
	


	
	/**
	 * 根据下载资源信息查询密钥
	 * @author tanJie
	 */
	@Override
	public String queryPassKeyByReport(String xml){
		String error = "";
		String xmlMessage = "<?xml version='1.0' encoding='utf-8'?>" + "<ROOT>";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String paths = xmlStrMap.get("paths")==null?"":xmlStrMap.get("paths");			
			try {
				String path = "";
				if(!paths.equals("")){
					String[] pathname = paths.split(",");
					if(pathname!=null && pathname.length>0){
						for (int i = 0; i < pathname.length; i++) {
							if(i==0){
								path = path + " p.path like '%"+pathname[i]+"%'";
							}else{
								path = path + " or p.path like '%"+pathname[i]+"%'";
							}
						}
						String sqlStr = "select new map(r.id as classid,r.name as name,r.time as time,r.author as author," +
								"r.intro as intro,r.price as price,r.isfree as isfree,p.passkey as passkey,p.path as path," +
								"r.scroll as scroll,r.downsum as downsum) " +
						"from MwpmSysResourcepath p,MwpmSysReport r where r.id=p.resourceid and p.type='bg' ";
						if(!path.equals("")){
							sqlStr = sqlStr+"and ( "+path+")";
						}
						List list = ceiDictDao.getALLData(sqlStr);
						if(list!=null && list.size()>0){
							String fulladdressPath = CommonParm.getString("FULL_ADDRESS_PATH");
							String bookPath = CommonParm.getString("BOOK_ZIP_PATH");
							String photoPath = CommonParm.getString("PHOTO_PATH");
							xmlMessage += "<classgroup>";
							for (int i = 0; i < list.size(); i++) {
								HashMap map = (HashMap)list.get(i);
								String classid = map.get("classid")==null?"":map.get("classid").toString();
								String name = map.get("name")==null?"":map.get("name").toString();
								Date time = (Date)map.get("time");
								String time1 = "";
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								if(time!=null){
									time1 = sdf.format(time);
								}
								String author = map.get("author")==null?"":map.get("author").toString();
								String dpath = map.get("path")==null?"":map.get("path").toString();
								String intro = map.get("intro")==null?"":map.get("intro").toString();
								String price = map.get("price")==null?"":map.get("price").toString();
								String scroll = map.get("scroll")==null?"":map.get("scroll").toString();
								String passkey = map.get("passkey")==null?"":map.get("passkey").toString();
								String isfree = map.get("isfree")==null?"":map.get("isfree").toString();
								String downpath=fulladdressPath+bookPath+dpath+"/bg.zip";
								String lookpath=fulladdressPath+photoPath+dpath;
								xmlMessage += "<class>";
								xmlMessage += "<classid>"+classid+"</classid>";
								xmlMessage += "<name>"+name+"</name>";
								xmlMessage += "<author>"+author+"</author>";
								xmlMessage += "<path>"+lookpath+"</path>";
								xmlMessage += "<creattime>"+time1+"</creattime>";
								xmlMessage += "<downpath>"+downpath+"</downpath>";
								xmlMessage += "<intro>"+intro+"</intro>";
								xmlMessage += "<price>"+price+"</price>";
								xmlMessage += "<scroll>"+scroll+"</scroll>";
								xmlMessage += "<passkey>"+passkey+"</passkey>";
								xmlMessage += "<isfree>"+isfree+"</isfree>";
								xmlMessage += "</class>";
							}
							xmlMessage += "</classgroup>";
						}else{
							xmlMessage += "<RETURNCODE>5</RETURNCODE>";
						}
					}
				}else{
					xmlMessage += "<RETURNCODE>5</RETURNCODE>";
				}
			} catch (Exception e) {
				xmlMessage += "<RETURNCODE>0</RETURNCODE>";
			}
		} else {
			xmlMessage += "<RETURNCODE>-1</RETURNCODE>";
		}
		xmlMessage += "</ROOT>";
		return xmlMessage;
	}
	
	

	/**查询购买报告
	 * @author tanJie
	 */
	@Override
	public String queryMoneyReport(String xml) {
		String error = "";
		String xmlMessage = "<?xml version='1.0' encoding='utf-8'?>" + "<ROOT>";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String userid = xmlStrMap.get("userid")==null?"":xmlStrMap.get("userid");
			try { 
					String url = "";
					String sql = "select m.BUYTYPE as buytype,m.RESOURCETYPE as resourcetype,m.FUNID as funid" +
							" from MWPM_SYS_MONEY m where m.STATUS='wdq' and m.userid='"+userid+"'";
					List moneyList = ceiDictDao.getInfo(sql);
					String kjywid = "";
					String bgid="";
					List list1 = new ArrayList();
					HashMap idMap = new HashMap();
					if(moneyList!=null && moneyList.size()>0){
						for (int i = 0; i < moneyList.size(); i++) {
							HashMap moneyMap = (HashMap)moneyList.get(i);
							String buytype = moneyMap.get("buytype")==null?"":moneyMap.get("buytype").toString();
							String resourcetype = moneyMap.get("resourcetype")==null?"":moneyMap.get("resourcetype").toString();
							String funid = moneyMap.get("funid")==null?"":moneyMap.get("funid").toString();
							if(buytype.equals("yw")){
								List list =this.queryTreeFunction(list1,funid);
								if(list!=null && list.size()>0){
									for (int j = 0; j < list.size(); j++) {
										HashMap fidMap = (HashMap)list.get(j);
										String functionid = fidMap.get("id").toString();
										kjywid = kjywid +" oc.functionid ='"+functionid+"' or";
									}
								}
							}
							if(buytype.equals("zy") && resourcetype.equals("bg")){
								if(idMap.get(funid)==null){
									xmlMessage += "<report>";
									xmlMessage += "<id>"+funid+"</id>";
									xmlMessage += "</report>";
									idMap.put(funid, funid);
								}
							}
						}
						
					List operationList =new ArrayList();
					List operatiList =new ArrayList();
					if(kjywid.length()>3){
						//通过functionid集合查询业务下分类集合(课件和报告集合)
						operationList = ceiDictDao.getInfo("select c.resourceid,c.type " +
								"from MWPM_SYS_CLASSIFICATION c left join MWPM_SYS_OPERATION_CLASS oc on c.TYPEID=oc.CLASSID " +
								"where 0=0 and ("+kjywid.substring(0, kjywid.length()-3)+")");

						//通过functionid集合查询业务下直接对应的课件和报告集合
						operatiList = ceiDictDao.getInfo("select oc.classid,oc.type " +
								"from MWPM_SYS_OPERATI_RESOURCE oc " +
								"where 0=0 and ("+kjywid.substring(0, kjywid.length()-3)+")");
					}
					if(operationList!=null && operationList.size()>0){
						for (int i = 0; i < operationList.size(); i++) {
							HashMap resourceMap = (HashMap)operationList.get(i);
							String resourceid = resourceMap.get("resourceid")==null?"":resourceMap.get("resourceid").toString();
							String type = resourceMap.get("type")==null?"":resourceMap.get("type").toString();
							if(type.equals("bg")){
								if(idMap.get(resourceid)==null){
									xmlMessage += "<report>";
									xmlMessage += "<id>"+resourceid+"</id>";
									xmlMessage += "</report>";
									idMap.put(resourceid, resourceid);
								}
							}
						}
						
					}
					if(operatiList!=null && operatiList.size()>0){
						for (int i = 0; i < operatiList.size(); i++) {
							HashMap classMap = (HashMap)operatiList.get(i);
							String classid = classMap.get("classid")==null?"":classMap.get("classid").toString();
							String type = classMap.get("type")==null?"":classMap.get("type").toString();
							if(type.equals("bg")){
								if(idMap.get(classid)==null){
									xmlMessage += "<report>";
									xmlMessage += "<id>"+classid+"</id>";
									xmlMessage += "</report>";
									idMap.put(classid, classid);
								}
							}
						}
						
					}
				}else{
					xmlMessage += "<RETURNCODE>4</RETURNCODE>";
				}

			} catch (Exception e) {
				xmlMessage += "<RETURNCODE>0</RETURNCODE>";
			}
		} else {
			xmlMessage += "<RETURNCODE>-1</RETURNCODE>";
		}
		xmlMessage += "</ROOT>";
		return xmlMessage;
	}
	
	

	
	/**
	 * 修改下载次数
	 * @author tanJie
	 */
	@Override
	public String updatedownsum(String xml){
		String error = "";
		String xmlMessage = "<?xml version='1.0' encoding='utf-8'?>" + "<ROOT>";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String id = xmlStrMap.get("id")==null?"":xmlStrMap.get("id");
			String type = xmlStrMap.get("type")==null?"":xmlStrMap.get("type");
			try {
				if(type.equals("kj")){
					String hql ="update MwpmSysClass set downsum = downsum+1 where id='"+id+"'";
					ceiDictDao.updateXYInfo(hql);
				}
				if(type.equals("bg")){
					String hql ="update MwpmSysReport set downsum = downsum+1 where id='"+id+"'";
					ceiDictDao.updateXYInfo(hql);
				}
				xmlMessage += "<RETURNCODE>1</RETURNCODE>";
			} catch (Exception e) {
				xmlMessage += "<RETURNCODE>0</RETURNCODE>";
			}
		} else {
			xmlMessage += "<RETURNCODE>-1</RETURNCODE>";
		}
		xmlMessage += "</ROOT>";
		return xmlMessage;
	}
	

	
	/**
	 * 查询报告属性名称
	 * @author tanJie
	 */
	@Override
	public String queryReportName(String xml){
		String error = "";
		String xmlMessage = "<?xml version='1.0' encoding='utf-8'?>" + "<ROOT>";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String id = xmlStrMap.get("id")==null?"":xmlStrMap.get("id");
			List list = ceiDictDao.getALLData("select new map(name as name,intro as intro,author as author,unit as unit,list as list,price as price) from MwpmSysReportAttribute where functionid='"+id+"'");
			if(list!=null && list.size()>0){
				HashMap map = (HashMap)list.get(0);
				String name = map.get("name")==null?"报告名称":map.get("name").toString();
				String intro = map.get("intro")==null?"介绍":map.get("intro").toString();
				String author = map.get("author")==null?"作者":map.get("author").toString();
				String unit = map.get("unit")==null?"单位":map.get("unit").toString();
				String lists = map.get("list")==null?"目录":map.get("list").toString();
				String price = map.get("price")==null?"价格":map.get("price").toString();
				xmlMessage += "<reportname>";
				xmlMessage += "<name>"+name+"</name>";
				xmlMessage += "<intro>"+intro+"</intro>";
				xmlMessage += "<author>"+author+"</author>";
				xmlMessage += "<unit>"+unit+"</unit>";
				xmlMessage += "<list>"+lists+"</list>";
				xmlMessage += "<price>"+price+"</price>";
				xmlMessage += "</reportname>";
			}else{
				xmlMessage += "<reportname>";
				xmlMessage += "<name>报告名称</name>";
				xmlMessage += "<intro>介绍</intro>";
				xmlMessage += "<author>作者</author>";
				xmlMessage += "<unit>单位</unit>";
				xmlMessage += "<list>目录</list>";
				xmlMessage += "<price>价格</price>";
				xmlMessage += "</reportname>";
			}
		} else {
			xmlMessage += "<RETURNCODE>-1</RETURNCODE>";
		}
		xmlMessage += "</ROOT>";
		return xmlMessage;
	}


	
	/**
	 * 查询课件属性名称
	 * @author tanJie
	 */
	@Override
	public String queryClassName(String xml){
		String error = "";
		String xmlMessage = "<?xml version='1.0' encoding='utf-8'?>" + "<ROOT>";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String id = xmlStrMap.get("id")==null?"":xmlStrMap.get("id");
			List list = ceiDictDao.getALLData("select new map(name as name,intro as intro,teachername as teachername,price as price) from MwpmSysClassAttribute where functionid='"+id+"'");
			if(list!=null && list.size()>0){
				HashMap map = (HashMap)list.get(0);
				String name = map.get("name")==null?"课件名称":map.get("name").toString();
				String intro = map.get("intro")==null?"介绍":map.get("intro").toString();
				String teachername = map.get("teachername")==null?"讲师姓名":map.get("teachername").toString();
				String price = map.get("price")==null?"价格":map.get("price").toString();
				xmlMessage += "<reportname>";
				xmlMessage += "<name>"+name+"</name>";
				xmlMessage += "<intro>"+intro+"</intro>";
				xmlMessage += "<teachername>"+teachername+"</teachername>";
				xmlMessage += "<price>"+price+"</price>";
				xmlMessage += "</reportname>";
			}else{
				xmlMessage += "<reportname>";
				xmlMessage += "<name>课件名称</name>";
				xmlMessage += "<intro>介绍</intro>";
				xmlMessage += "<teachername>讲师姓名</teachername>";
				xmlMessage += "<price>价格</price>";
				xmlMessage += "</reportname>";
			}
		} else {
			xmlMessage += "<RETURNCODE>-1</RETURNCODE>";
		}
		xmlMessage += "</ROOT>";
		return xmlMessage;
	}
	
	private static final char endTag = (char) (1); // 关键词结束符
	@SuppressWarnings("unchecked")
	private static Map<Character, HashMap> filterMap = new HashMap<Character, HashMap>(
			1024);

	@SuppressWarnings("unchecked")
	public void init() {
		// TODO:加载过滤词库
		List list = new ArrayList();
		list = ceiDictDao.getALLData("select new map(keyword as keyword) from MwpmSysKeyword");	
		if(list!=null && list.size()>0){
			String[] filterWordList = new String[list.size()];
			HashMap map = new HashMap();
			for (int i = 0; i < list.size(); i++) {
				map = (HashMap)list.get(i);
				String keyword = map.get("keyword")==null?"":map.get("keyword").toString();
				try {
					if(!keyword.equals("")){
						filterWordList[i]=keyword;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		for (String filterWord : filterWordList) {
			char[] charArray = filterWord.trim().toCharArray();
			int len = charArray.length;
			if (len > 0) {
				Map<Character, HashMap> subMap = filterMap;
				for (int i = 0; i < len - 1; i++) {
					Map<Character, HashMap> obj = subMap.get(charArray[i]);
					if (obj == null) {
						// 新索引，增加HashMap
						int size = (int) Math.max(2, 16 / Math.pow(2, i));
						HashMap<Character, HashMap> subMapTmp = new HashMap<Character, HashMap>(
								size);
						subMap.put(charArray[i], subMapTmp);
						subMap = subMapTmp;
					} else {
						// 索引已经存在
						subMap = obj;
					}
				}
				// 处理最后一个字符
				Map<Character, HashMap> obj = subMap.get(charArray[len - 1]);
				if (obj == null) {
					// 新索引，增加HashMap，并设置结束符
					int size = (int) Math.max(2, 16 / Math.pow(2, len - 1));
					HashMap<Character, HashMap> subMapTmp = new HashMap<Character, HashMap>(
							size);
					subMapTmp.put(endTag, null);
					subMap.put(charArray[len - 1], subMapTmp);
				} else {
					// 索引已经存在,设置结束符
					obj.put(endTag, null);
				}
			}
		}

		}
	}

	// 返回是否包含需要过滤的词,匹配到最短的关键词就返回结果
	@SuppressWarnings("unchecked")
	public static boolean isHasFilterWord(String info) {
		if (info == null || info.length() == 0) {
			return false;
		}
		char[] charArray = info.toCharArray();
		int len = charArray.length;
		for (int i = 0; i < len; i++) {
			int index = i;
			Map<Character, HashMap> sub = filterMap.get(charArray[index]);
			while (sub != null) {
				if (sub.containsKey(endTag)) {
					// 匹配结束
					return true;
				} else {
					index++;
					if (index >= len) {
						// 字符串结束
						return false;
					}
					sub = sub.get(charArray[index]);
				}
			}
		}
		return false;
	}

	// 将字符串中包含的关键字过滤并替换为*，然后退回替换后的字符串
	public static String getFilterString(String info) {
		return getFilterString(info, "*");
	}

	// 将字符串中包含的关键词过滤并替换为指定字符串，然后退回替换后的字符串
	// 尽量匹配最长的关键词再替换，关键词最长不超过maxKeyWordLen
	@SuppressWarnings("unchecked")
	public static String getFilterString(String info, String replaceTag) {
		if (info == null || info.length() == 0 || replaceTag == null
				|| replaceTag.length() == 0) {
			return info;
		}
		char[] charArray = info.toCharArray();
		int len = charArray.length;
		String newInfo = "";
		int i = 0;
		while (i < len) {
			int end = -1;
			int index;
			Map<Character, HashMap> sub = filterMap;
			for (index = i; index < len; index++) {
				sub = sub.get(charArray[index]);
				if (sub == null) {
					// 匹配失败，将已匹配的最长字符进行替换
					if (end == -1) {
						// 没匹配到任何关键词
						newInfo += charArray[i];
						i++;
						break;
					} else {
						// 将最长匹配字符串替换为特定字符
						for (int j = i; j <= end; j++) {
							newInfo += replaceTag;
						}
						i = end + 1;
						break;
					}
				} else {
					if (sub.containsKey(endTag)) {
						// 匹配
						end = index;
					}
				}
			}
			if (index >= len) {
				// 字符串结束
				if (end == -1) {
					// 没匹配到任何关键词
					newInfo += charArray[i];
					i++;
				} else {
					// 将最长匹配字符串替换为特定字符
					for (int j = i; j <= end; j++) {
						newInfo += replaceTag;
					}
					i = end + 1;
				}
			}
		}
		return newInfo;
	}


	/**
	 * 最新apk下载地址
	 */
	@Override
	public String queryApkList(String xml) {
		String error = "";
		String xmlMessage = "<?xml version='1.0' encoding='utf-8'?>" + "<ROOT>";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String id = xmlStrMap.get("id")==null?"":xmlStrMap.get("id");
			List list = ceiDictDao.getALLData("from MwpmSysUpdateloadPackage p where isupdate='1'");
			String apkPath = CommonParm.getString("APKPATH");
			xmlMessage += "<update>";
			if(list!=null && list.size()>0){
				MwpmSysUpdateloadPackage p =  new MwpmSysUpdateloadPackage();
				for (int i = 0; i < list.size(); i++) {
					p = (MwpmSysUpdateloadPackage)list.get(i);
					String version = p.getEditionnum();
					String name = p.getPackagename();
					String url = apkPath+name;
					if(name.length()>4){
						name = name.substring(0,name.length()-4);
					}
					String type = p.getType();
					xmlMessage += "<"+type+"version>"+version+"</"+type+"version>";
					xmlMessage += "<"+type+"name>"+name+"</"+type+"name>";
					xmlMessage += "<"+type+"url>"+url+"</"+type+"url>";
				}
			}
			xmlMessage += "</update>";
		} else {
			xmlMessage += "<RETURNCODE>-1</RETURNCODE>";
		}
		xmlMessage += "</ROOT>";
		return xmlMessage;
	}


	/**
	 * 版本对应的网站地址
	 */
	@Override
	public String queryFunctionAddress(String xml) {
		String error = "";
		String xmlMessage = "<?xml version='1.0' encoding='utf-8'?>" + "<ROOT>";
		Map<String, String> xmlStrMap = new HashMap<String, String>();
		error = ReadXML.parseInputDataXml(xml, xmlStrMap);
		if ("".equals(error)) {
			String id = xmlStrMap.get("id")==null?"":xmlStrMap.get("id");
			List list = ceiDictDao.getALLData("from MwpmPortalIndex p where functionid='"+id+"'");
			if(list!=null && list.size()>0){
				MwpmPortalIndex p =  new MwpmPortalIndex();
					p = (MwpmPortalIndex)list.get(0);
					String address = p.getDomain();
					xmlMessage += "<address>"+address+"</address>";
			}
		} else {
			xmlMessage += "<RETURNCODE>-1</RETURNCODE>";
		}
		xmlMessage += "</ROOT>";
		return xmlMessage;
	}
}
