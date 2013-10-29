package com.hyrt.mwpm.webservice.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.log4j.Logger;
import org.hibernate.exception.GenericJDBCException;
import org.hibernate.hql.ast.QuerySyntaxException;
import org.springframework.orm.hibernate3.HibernateQueryException;

import com.hyrt.mwpm.util.GridStatic;
import com.hyrt.mwpm.util.ReadOperationXML;
import com.hyrt.mwpm.webservice.dao.IEntinfoDao;



public class LoginWebservice {
	private static Logger log = Logger.getLogger(LoginWebservice.class);
    private IEntinfoDao ientinfoDao;
	
	//定义变量		
	//用户登录表
	private String login_webservice = "LOGIN_WEBSERVICE";
	
	public IEntinfoDao getIentinfoDao() {
		return ientinfoDao;
	}

	public void setIentinfoDao(IEntinfoDao ientinfoDao) {
		this.ientinfoDao = ientinfoDao;
	}

	public String userLogin(String xml) {
		// TODO Auto-generated method stub
		return this.getLoginMessageFunction(xml);
	}
	
	public String getLoginMessageFunction(String xml) {
		//System.out.println(xml);
		Map array = new HashMap();
		String errorMessage = "";
		String returnMessage = "";
		try {
			// 解析传入的XML格式的文件
			errorMessage = ReadOperationXML.parseInputDataXml(xml, array);
			String querytype = "0";// 查询类型：精确0/模糊1
			if (errorMessage.length() == 0) {// 当没有异常时
				// 取得当前页
				Map parseHqlXMLHashMap = new HashMap();
				// 解析HQL的XML文件
				// errorMessage=ReadOperationXML.parseHqlXML(parseHqlXMLHashMap);
				// 从内存中取得解析HQL的XML文件的MAP
				parseHqlXMLHashMap = GridStatic.HQL_DICT_DATA;

				if (errorMessage.length() == 0 && parseHqlXMLHashMap != null) {
					errorMessage = parseHqlXMLHashMap.get("error") == null ? "" : parseHqlXMLHashMap.get("error").toString();
				}
				// 当解析的HQL.xml文件有错误时
				if (errorMessage.length() > 0) {
					errorMessage = ReadOperationXML.addErrorXml(errorMessage);
					return errorMessage;
				} else {
					// 查询的编号，条件的MAP，传入解析好的hqldict.xml整个数据的MAP
					// 根据HQL的查询编号，和解析传入的XML和HQL的文件，进行组合HQL语句。
					String hql = ReadOperationXML.makeUpHql(login_webservice, array, parseHqlXMLHashMap, querytype);
					if (hql.length() > 0) {
						//log.error(hql + "输入正确的HQL语句");
						// 取得总记录数的HQL语句
						List allData = ientinfoDao.getALLData(hql);
						//编码表转成文字
						//allData=getDBCodeDesc(allData);
						
						// System.out.println(allData);
						if (allData != null && allData.size() > 0) {// 当查询有数据时，则开始计算总页数和拼凑返回给客户端的XML格式的文件
							//returnMessage = ReadOperationXML.OperationPartDataXml(allData, parseHqlXMLHashMap, hqlXmlPageQuery_code);
							Map userMap=(HashMap)allData.get(0);
							returnMessage=ReadOperationXML.addUserMessageXml(userMap);
						} else {// 当没有数据时，则返回没有找到查询结果的编码
							errorMessage = ReadOperationXML.addErrorXml(GridStatic.V_SUCCESS_NO_QUERYDATA_B);
							return errorMessage;
						}
					} else {
						// 提供的主编号有问题
						errorMessage = ReadOperationXML.addErrorXml(GridStatic.E_DB_HQL_CODE_ERROR_B);
						return errorMessage;
					}
				}
			} else {
				// 传入的XML格式的文件有问题
				errorMessage = ReadOperationXML.addErrorXml(GridStatic.E_RETURN_FAILURE_OUTPUT_XML_B);
				return errorMessage;
			}
		} catch (HibernateQueryException e) {
			// 查询错误
			log.error("QuerySyntaxException", e);
			errorMessage = ReadOperationXML.addErrorXml(GridStatic.E_DB_QUERY_ERROR_B);
			e.printStackTrace();
			return errorMessage;
		} catch (QuerySyntaxException e) {
			// 查询错误
			log.error("QuerySyntaxException", e);
			errorMessage = ReadOperationXML.addErrorXml(GridStatic.E_DB_QUERY_ERROR_B);
			e.printStackTrace();
			return errorMessage;

		} catch (GenericJDBCException e) {
			// 连接错误
			log.error("GenericJDBCException", e);
			errorMessage = ReadOperationXML.addErrorXml(GridStatic.E_DB_CONNECTION_ERROR_B);
			e.printStackTrace();
			return errorMessage;
		} catch (Exception e) {// 这里还要捕执行HQL语句的异常和数据库连接异常
			log.error("GenericJDBCException", e);
			errorMessage = ReadOperationXML.addErrorXml(GridStatic.E_RETURN_ERROR_B);
			e.printStackTrace();
			return errorMessage;
		}
		//System.out.println(returnMessage);
		return returnMessage;

	}
	

//	//查询编码表
//	public List getDBCodeDesc(List dateList){
//		//企业类型CodeQylx
//		//币种CodeBz
//		//主体类别CodeZtlb
//		//经营状态CodeJyzt
//		//管辖工商所CodeDept
//		//巡查组CodeXcz
//		//System.out.println("之前："+dateList);
//		for(int v=0;v<dateList.size();v++){
//			Map linkMap=new LinkedHashMap();
//			Map map=(HashMap)dateList.get(v);
//			String unitid=map.get("unitid")==null?"":map.get("unitid").toString();
//			//登陆机关
//			map.put("unitid", unitid);
//			map.put("unitname", gridDaoImp.getDjjgCode(unitid));
//			
//			//管辖工商所名子
//			String deptid=map.get("deptid")==null?"":map.get("deptid").toString();
//			map.put("deptname", gridDaoImp.getName("CodeDept",deptid));
//			//System.out.println(deptid+"{{{{{{{{{{{{{{{{{{");
//			//巡查组取得巡查组的名子
//			String xczid=map.get("xczid")==null?"":map.get("xczid").toString();
//			map.put("xczname", gridDaoImp.getZcz(xczid));
//			
//			
//		}
//		//System.out.println("之后："+dateList);
//		return dateList;
//		
//	}
	
}
