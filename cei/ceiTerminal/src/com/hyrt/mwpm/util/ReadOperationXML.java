package com.hyrt.mwpm.util;

import java.io.File;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.jdom.Attribute;
import org.jdom.input.JDOMParseException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class ReadOperationXML {

	private static Logger log4j = Logger.getLogger(ReadOperationXML.class);
	private static String HQLDICT_PAGESIZE = "10";// 当hqldict.xml中的pageSize为空时，默认一页显示多少行
	private static String SIGN_BIG = "＞";// >
	private static String SIGN_LESS = "＜";// <
	private static String SIGN_LETTER = "####";// <

	/*
	 * 生发生异常时，生成返回错误消息的XML格式的文件
	 * <?xml version="1.0" encoding="gbk"?>
	<ROOT>
    	<RETURNCODE>B-E-01</RETURNCODE>
    	<RETURNMESSAGE>hqldict.xml配置XML解析错误</RETURNMESSAGE>
	</ROOT>
	 */
	public static String addErrorXml(String errorStr) {
		if(errorStr==null){
			errorStr="";
		}
		String errorMessage = "";
		org.jdom.Element element = new org.jdom.Element("ROOT");// 生成根节点

		org.jdom.Document document = new org.jdom.Document(element);// 生成document对像
		org.jdom.Element returncodeElement = new org.jdom.Element("RETURNCODE");// 生成returncode节点
		returncodeElement.setText(errorStr);
		element.addContent(returncodeElement);// 将returncode二级节点加入根节点

		org.jdom.Element returnMessageElement = new org.jdom.Element("RETURNMESSAGE");// 生成returncode节点
		String message = parseError(errorStr);
		log4j.debug("返回的错误消息---------------"+message);
		returnMessageElement.setText(message);
		element.addContent(returnMessageElement);// 将returncode二级节点加入根节点

		errorMessage = ParseXMLOutputter(document);
		return errorMessage;
	}

	// 给传入的document整理格式
	public static String ParseXMLOutputter(org.jdom.Document errorDocument) {
		// XMLOutputter xmlOut = new XMLOutputter(Format.getPrettyFormat());
		// outString = xmlOut.outputString(errorDocument);
		String outString = "";
		Format format = Format.getCompactFormat();
		format.setEncoding("gbk");
		format.setIndent("    ");
		XMLOutputter xmlOut = new XMLOutputter(format);
		outString = xmlOut.outputString(errorDocument);

		return outString;
	}

	/*
	 * 解析errdict.xml 文件将指定的错误码的描述取出
	 *  <?xml version="1.0" encoding="gb2312"?>
	 * <root> 
	 * <err errno="A-E-00" errdesc="无法预测的错误"/> 
	 * <err errno="A-E-01" errdesc="IP配置XML解析错误"/> 
	 * </root>
	 */
	public static String  parseErrorXML(Map childMap) {
		String errorMessage = "";
//		 String filepathname = "D:\\workspace\\HBT-MWPI-ACCOUNT\\WebRoot\\WEB-INF\\errdict.xml";
		 String filepathname = "file:\\" + GridStatic.ABSOLUTENESS_PATH + "WEB-INF\\errdict.xml";
		if(childMap==null){
			childMap = new HashMap();
		}		 
		try {
			SAXBuilder builder = new SAXBuilder();
			org.jdom.Document doc = builder.build(filepathname);
			org.jdom.Element root = doc.getRootElement();
			//对参数进行判断，以免产生异常
			List allChildren = root.getChildren();
			
			for (int v = 0; v < allChildren.size(); v++) {				
				org.jdom.Element childNote = (org.jdom.Element) allChildren.get(v);
				String errno = "" + childNote.getAttributeValue("errno").trim();
				String errdesc = "" + childNote.getAttributeValue("errdesc").trim();
				childMap.put(errno, errdesc);				
			}
			
		} catch (JDOMParseException e) {// XML解析异常
			//System.out.println("JDOMParseException--XXXX-");
			errorMessage = GridStatic.E_RETURN_FAILURE_ERROR_XML_B;
			log4j.error("类名：ReadOperationXML:函数名：parseErrorXML:错误：JDOMParseException", e);
			// e.printStackTrace();
			childMap.put("errdict", errorMessage);
			return errorMessage;
		} catch (Exception e) {// 无法预测的异常
			//System.out.println("Exception--XXXX-");
			errorMessage = GridStatic.E_RETURN_ERROR_B;
			log4j.error("类名：ReadOperationXML:函数名：parseErrorXML:错误：Exception", e);
			// e.printStackTrace();
			childMap.put("errdict", errorMessage);
			return errorMessage;
		}
		return errorMessage;
	}
	//从解析好的errdict.xml的map中取出指定的值
	public static String parseError(String errorCode) {
		Map map = new HashMap();
		String errorMessage = "";
		String returnMessage = "";
		// errorMessage=parseErrorXML(map);
		map = GridStatic.ERROR_DICT_DATA;

		// 当解析XML格式文件有问题时，则把编码返回
		if (map.get("errdictMessage") != null) {
			errorMessage = map.get("errdictMessage").toString();
			if (errorMessage != null && errorMessage.length() > 0) {
				return GridStatic.E_RETURN_FAILURE_ERROR_XML_B;
			}
		} else {
			Iterator it = map.entrySet().iterator();

			while (it.hasNext()) {
				Map.Entry map1 = (Map.Entry) it.next();
				String name = map1.getKey() == null ? "" : map1.getKey().toString().trim();
				String value = map1.getValue() == null ? "" : map1.getValue().toString().trim();
				if (name.equalsIgnoreCase(errorCode.trim())) {
					returnMessage = value;
					break;
				}
			}
		}
		return returnMessage;
	}
	/*
	 * 解析errdict.xml 文件将指定的错误码的描述取出 hqlXMLList该MAP中存入两个值，
	 * 1、pagesize为hqldict.xml中<pagesize>10</pagesize>内容
	 * 2、queryList为List里面每个值为一个map，map中的值为其hqldict.xml中每个query的各属性。
	 * 3、在queryList中的map中有一个wherefldMap该值为一个map记录的是<wherefld>子节点内容
	 *  <?xml version="1.0" encoding="gb2312"?> 
	 *  <root> 
	 *  <pagesize>10</pagesize> 
	 *  <query code="111" name="查询名称" querydesc="其它说明"> 
	 *  <hql>select new map(a.table1 as table1,a.table2 as table2,a.id as id) from table as a where 1=1</hql>
	 * <wherefld> 
	 * <table1>a.table1='QQQQ'</table1> 
	 * <table2>a.table2=QQQQ</table2>
	 * <start>a.start＜'QQQQ'</start> 
	 * <end>a.end＞'QQQQ'</end> 
	 * </wherefld>
	 * <outputfld>table1,table2</outputfld> 
	 * <orderby>a.table1</orderby>
	 * <orderbycom>desc</orderbycom> 
	 * <groupby>a.table1</groupby> 
	 * </query>
	 * </root>
	 */

	public static String parseHqlXML(Map hqlXMLList) {
		if (hqlXMLList == null) {
			hqlXMLList = new HashMap();
		}
		String errorMessage = "";
//	    String filepathname = "D:\\workspace\\HBT-MWPI-ACCOUNT\\WebRoot\\WEB-INF\\hqldict.xml";
		String filepathname = "file:\\" + GridStatic.ABSOLUTENESS_PATH + "WEB-INF\\hqldict.xml";
		try {
			SAXBuilder builder = new SAXBuilder();
			org.jdom.Document doc = builder.build(filepathname);
			org.jdom.Element root = doc.getRootElement();

			List allChildren = root.getChildren();
			List queryList = new ArrayList();
			for (int v = 0; v < allChildren.size(); v++) {
				Map queryMap = new HashMap();
				org.jdom.Element childNote = (org.jdom.Element) allChildren.get(v);
				String queryNote = childNote.getName();
				if (queryNote.equalsIgnoreCase("pagesize")) {
					String pageSize = childNote.getText() == null ? HQLDICT_PAGESIZE : childNote.getText().trim();
					hqlXMLList.put("pagesize", pageSize);
				} else if (queryNote.equalsIgnoreCase("query")) {
					String querycode = "" + childNote.getAttributeValue("code").trim();
					List queryChildren = childNote.getChildren();
					queryMap.put("code", querycode);
					List whereList = new ArrayList();
					for (int y = 0; y < queryChildren.size(); y++) {
						org.jdom.Element queryCodeNote = (org.jdom.Element) queryChildren.get(y);
						String queryCodeName = queryCodeNote.getName();
						Map whereMap = new HashMap();
						if (queryCodeName.equalsIgnoreCase("hqlcount")) {
							String hqlcount = queryCodeNote.getText() == null ? "" : queryCodeNote.getText().trim();
							queryMap.put("hqlcount", hqlcount);
						} else if (queryCodeName.equalsIgnoreCase("hql")) {
							String hql = queryCodeNote.getText() == null ? "" : queryCodeNote.getText().trim();
							queryMap.put("hql", hql);
						} else if (queryCodeName.equalsIgnoreCase("outputfld")) {
							String outputfld = queryCodeNote.getText() == null ? "" : queryCodeNote.getText().trim();
							queryMap.put("outputfld", outputfld);
						} else if (queryCodeName.equalsIgnoreCase("orderby")) {
							String orderby = queryCodeNote.getText() == null ? "" : queryCodeNote.getText().trim();
							queryMap.put("orderby", orderby);
						} else if (queryCodeName.equalsIgnoreCase("groupby")) {
							String groupby = queryCodeNote.getText() == null ? "" : queryCodeNote.getText().trim();
							queryMap.put("groupby", groupby);
						}else if (queryCodeName.equalsIgnoreCase("dispfld")) {
							List dispfldChildren = queryCodeNote.getChildren();
							Map dispfldMap=new HashMap();
							for (int u = 0; u < dispfldChildren.size(); u++) {			
								org.jdom.Element whereCodeNote = (org.jdom.Element) dispfldChildren.get(u);
								String dispfldName=whereCodeNote.getName();
								String dispfldValue=whereCodeNote.getText();
								dispfldMap.put(dispfldName, dispfldValue);
							}
							
							queryMap.put("dispfldMap", dispfldMap);
						}  
						else if (queryCodeName.equalsIgnoreCase("wherefld")) {
							List whereChildren = queryCodeNote.getChildren();
							Map whereAllMap=new HashMap();
							for (int u = 0; u < whereChildren.size(); u++) {
								org.jdom.Element whereCodeNote = (org.jdom.Element) whereChildren.get(u);
								String wherefld=whereCodeNote.getName();
								if(wherefld!=null && wherefld.equals("equals")){									
									List equalsChild = whereCodeNote.getChildren();
									Map equalsMap=new HashMap();
									for(int e=0;e<equalsChild.size();e++){
										org.jdom.Element whereNote = (org.jdom.Element) equalsChild.get(e);
										String whereCodeName = whereNote.getName().toLowerCase();//将从XML文件中的标签转为小写
										String whereValue = whereNote.getText()==null?"":whereNote.getText().trim();
										equalsMap.put(whereCodeName, whereValue);
									}
									whereAllMap.put("equalsMap",equalsMap);
									
								}else if(wherefld!=null && wherefld.equals("like")){
									List likeChild = whereCodeNote.getChildren();
									Map equalsMap=new HashMap();
									for(int e=0;e<likeChild.size();e++){
										org.jdom.Element whereNote = (org.jdom.Element) likeChild.get(e);
										String whereCodeName = whereNote.getName().toLowerCase();//将从XML文件中的标签转为小写
										String whereValue = whereNote.getText()==null?"":whereNote.getText().trim();
										equalsMap.put(whereCodeName, whereValue);
									}
									whereAllMap.put("likeMap",equalsMap);
								}
								
							}
							queryMap.put("wherefldMap", whereAllMap);
							//System.out.println(queryMap);
						}

					}
					queryList.add(queryMap);
				}

			}
			hqlXMLList.put("queryList", queryList);
			//System.out.println(hqlXMLList);

		} catch (JDOMParseException e) {// XML解析异常
			//System.out.println("JDOMParseException--XXXX-");
			errorMessage = GridStatic.E_RETURN_FAILURE_HQLDICT_XML_B;
			log4j.error("类名：ReadOperationXML:函数名：parseHqlXML:错误：JDOMParseException", e);
			// e.printStackTrace();

			return errorMessage;
		} catch (Exception e) {// 无法预测的异常
			//System.out.println("Exception--XXXX-");
			errorMessage = GridStatic.E_RETURN_ERROR_B;
			log4j.error("类名：ReadOperationXML:函数名：parseHqlXML:错误：Exception", e);
			return errorMessage;
		}
		return errorMessage;
	}

	/*
	 * queryCode,取得HQL的code编码,对解析过的errdict.xml数据进行处理并拼成HQL语句
	 * queryCode:要使用的HQL的编码 outputWhereXMLMap:为手机终端传入的MXL格式解析以后的MAP文件
	 * parseHqlXml：为hqldict.xml解析以后的map文件
	 * 返回值：正常情况下，将返回拼接好的HQL语句，当提供的函数编号没有找到时，则返回空串。
	 */
	public static String makeUpHql(String queryCode, Map outputWhereXMLMap, Map parseHqlXml,String querytype) {
		String hqlcount = "";
		String hql = "";
		//String outputfld = "";
		String orderby = "";
		String groupby = "";
		
		String daoHql = "";
		
		HashMap wherefldMap = new HashMap();
		boolean isHql = false;
		try{
			//对参数做判断，以免取值时产生异常
			if(queryCode==null){
				queryCode="";
			}
			if(outputWhereXMLMap==null){
				outputWhereXMLMap=new HashMap();
			}			
			if(parseHqlXml==null){
				parseHqlXml=new HashMap();
			}
			
		String pageSize = parseHqlXml.get("pagesize").toString();
		List queryList=new ArrayList();
		if(parseHqlXml.get("queryList")!=null){
			queryList=(List) parseHqlXml.get("queryList");
			}
		
		for (int v = 0; v < queryList.size(); v++) {
			Map queryMap = (HashMap) queryList.get(v);
			String code = queryMap.get("code").toString();
			
			if (code.equalsIgnoreCase(queryCode)) {
				isHql = true;
				hqlcount = queryMap.get("hqlcount") == null ? "" : queryMap.get("hqlcount").toString().trim();
				hql = queryMap.get("hql") == null ? "" : queryMap.get("hql").toString().trim();
				orderby = queryMap.get("orderby") == null ? "" : queryMap.get("orderby").toString().trim();
				groupby = queryMap.get("groupby") == null ? "" : queryMap.get("groupby").toString().trim();
				wherefldMap = queryMap.get("wherefldMap") == null ? null : (HashMap) queryMap.get("wherefldMap");
				break;
			}
		}
		
		String whereStr = "";
		if (isHql) {
			Iterator outputIt = outputWhereXMLMap.entrySet().iterator();
			while (outputIt.hasNext()) {
				Map.Entry outMap = (Map.Entry) outputIt.next();
				String outputName = outMap.getKey() == null ? "" : outMap.getKey().toString().trim().toLowerCase();
				String outValue = "";
				if (outMap.getValue() != null && outMap.getValue().toString().trim().length() > 0) {
					outValue = outMap.getValue().toString().trim();
				}
				String value="";
				if(querytype.equals("0")){//查询类型：精确0/模糊1
					Map equalsMap=(HashMap)wherefldMap.get("equalsMap");
				    value = equalsMap.get(outputName) == null ? "" : equalsMap.get(outputName).toString();
				}else{
					Map equalsMap=(HashMap)wherefldMap.get("likeMap");
					 value = equalsMap.get(outputName) == null ? "" : equalsMap.get(outputName).toString();
				}
				if (value.length() > 0 && outValue.length() > 0) {
					String whe = value.replaceAll(SIGN_LETTER, outValue);
					int v = whe.indexOf(SIGN_BIG);
					if (v > 0) {
						whe = whe.replace(SIGN_BIG, ">");
					}
					int m = whe.indexOf(SIGN_LESS);
					if (m > 0) {
						whe = whe.replace(SIGN_LESS, "<");
					}
					whereStr = whereStr + " and " + whe;
				}
			}

			if (groupby.length() > 0) {
				whereStr = whereStr + " group by " + groupby;
			}
			if (orderby.length() > 0) {
				whereStr = whereStr + " order by " + orderby;
			}
			daoHql = hql + whereStr;
			//System.out.println("hqlcount------"+hqlcount);
			//System.out.println("whereStr------"+whereStr);
			String daohqlCount=hqlcount+whereStr;
			//System.out.println("拼好的SQL:"+daohqlCount);
			for (int v = 0; v < queryList.size(); v++) {
				Map queryMap = (HashMap) queryList.get(v);
				String code = queryMap.get("code").toString();
				
				if (code.equalsIgnoreCase(queryCode)) {
				queryMap.put("hqlcountMap", daohqlCount);
				}
			}
		}
		}catch(Exception e){
			e.printStackTrace();
			log4j.error("拼SQL的方法报错了！类名：ReadOperationXML:函数名：makeUpHql:错误：Exception", e);
		}
		return daoHql;
	}

	/*查询列表的XML格式的字符串
	 * 通过取得的数据库数据生成返回的业务数据的XML格式字符串 样例： 
	 * <?xml version="1.0" encoding="gb2312"?>
	 * <ROOT> 
	 * <PAGENO>10</PAGENO> 
	 * <DISPLIST>
	 *  <start>2009-02-01</start>
	 * <table1>aaa</table1> 
	 * <table0>aaa</table0> 
	 * </DISPLIST> <DISPLIST>
	 * <ta>aaa</ta>
	 *  <st>2009-02-01</st> 
	 *  </DISPLIST>
	 *   </ROOT>
	 */
	public static String OperationQueryListDataXml(List dataList, String pageNo, int pageSum) {

		String messageXML = "";
		try {
			if (dataList == null) {
				dataList = new ArrayList();
			}
			if (pageNo == null) {
				pageNo = "1";
			}
			org.jdom.Element element = new org.jdom.Element("ROOT");// 生成根节点

			org.jdom.Document document = new org.jdom.Document(element);// 生成document对像

			//org.jdom.Element pageNoElement = new org.jdom.Element("PAGENO");// 当前页
			//pageNoElement.setText(pageNo);
			//element.addContent(pageNoElement);//
			//当传入的当前页为1时，传总页数，否则，不传该节点
			if (pageNo.equals("1")) {
				org.jdom.Element pageSumElement = new org.jdom.Element("ALLPAGES");// 总页数
				pageSumElement.setText(pageSum + "");
				element.addContent(pageSumElement);//
			}

			for (int v = 0; v < dataList.size(); v++) {
				HashMap map = (HashMap) dataList.get(v);
				// System.out.println(map);
				Iterator it = map.entrySet().iterator();
				org.jdom.Element displistElement = new org.jdom.Element("DISPLIST");// 生成returncode节点
				while (it.hasNext()) {
					Map.Entry map1 = (Map.Entry) it.next();
					String name = map1.getKey() == null ? "" : map1.getKey().toString().trim();
					String value = map1.getValue() == null ? "" : map1.getValue().toString().trim();
					org.jdom.Element dataElement = new org.jdom.Element(name.toUpperCase());// 将生成的XML格式文件转为大写
					dataElement.addContent(value);//
					displistElement.addContent(dataElement);
				}
				element.addContent(displistElement);// 将returncode二级节点加入根节点
			}

			messageXML = ParseXMLOutputter(document);
		} catch (Exception e) {
			e.printStackTrace();
			log4j.error("成生业务数据的XML格式字符串方法报错了！类名：ReadOperationXML:函数名：OperationQueryListDataXml:错误：Exception", e);
		}
		return messageXML;
	}
	/*
	 * 解析手机终端传入的分页的XML格式的字符串
	 * "<?xml version=\"1.0\" encoding=\"GB2312\"?>" + 
		"<ROOT>" + 
		"<PAGENO>1</PAGENO>" + 
		"<TABLE1>aaa</TABLE1>"+
		"<TABLE0>aaa</TABLE0>" + 
		"<START>2009-02-01</START>" + 
		"</ROOT>";
	 * 参数：
	 * xmlStr：手机终端传入的XML格式的字符串，
	 * map：传入一个空的MAP，为的是将解析的结果保存到该MAP中
	 */
	public static String parseInputDataXml(String xmlStr, Map map) {
		// Map xmlStrMap=new HashMap();
		String errorMessage = "";
		try {
			//对参数做判断，以免产生异常
			if(xmlStr==null){
				xmlStr="";
			}
			if(map==null){
				map=new HashMap();
			}
			//System.out.println("输入的XML格式的文件："+xmlStr);
			SAXBuilder builder = new SAXBuilder();
			Reader in = new StringReader(xmlStr);
			org.jdom.Document doc = builder.build(in);
			org.jdom.Element root = doc.getRootElement();
			List allChildren = root.getChildren();
			for (int v = 0; v < allChildren.size(); v++) {
				org.jdom.Element childNote = (org.jdom.Element) allChildren.get(v);
				String fieldName = childNote.getName().toLowerCase();//将传入标签名转成全部小写做为map的名子
				String fieldValue = childNote.getText() == null ? "" : childNote.getText().trim();
				//如果传入的页号为空，或为0，则将当前页写为1;
				if(fieldName.equalsIgnoreCase("pageno") && (fieldValue.length()==0 || fieldValue.equals("0"))){
					fieldValue="1";
				}
				map.put(fieldName, fieldValue);
				//System.out.println(fieldName+"输入的XML的查询字段");
			}

		} catch (JDOMParseException e) {// XML解析异常
			// log.error("Exception:",e);
			e.printStackTrace();
			errorMessage = addErrorXml(GridStatic.E_RETURN_FAILURE_OUTPUT_XML_B);
			log4j.error("类名：ReadOperationXML:函数名：parseInputDataXml:错误：JDOMParseException", e);
			return errorMessage;
		} catch (Exception e) {// 无法预测的异常
			// log.error("Exception:",e);
			e.printStackTrace();
			errorMessage = addErrorXml(GridStatic.E_RETURN_ERROR_B);
			log4j.error("类名：ReadOperationXML:函数名：parseInputDataXml:错误：Exception", e);
			return errorMessage;
		}
		return errorMessage;
	}
	/*
	 * 解析手机终端传入录入的XML格式的字符串
	 * "<?xml version=\"1.0\" encoding=\"GBk\"?>" + 
		"<ROOT>" + 
		"<TABLE1>aaa</TABLE1>"+
		"<TABLE0>aaa</TABLE0>" + 
		"<START>2009-02-01</START>" + 
		"</ROOT>";
	 * 参数：
	 * xmlStr：手机终端传入的XML格式的字符串，
	 * map：传入一个空的MAP，为的是将解析的结果保存到该MAP中
	 */
	public static String parseInputXml(String xmlStr, Map map) {
		// Map xmlStrMap=new HashMap();
		String errorMessage = "";
		try {
			//对参数做判断，以免产生异常
			if(xmlStr==null){
				xmlStr="";
			}
			if(map==null){
				map=new HashMap();
			}
			//System.out.println("输入的XML格式的文件："+xmlStr);
			SAXBuilder builder = new SAXBuilder();
			Reader in = new StringReader(xmlStr);
			org.jdom.Document doc = builder.build(in);
			org.jdom.Element root = doc.getRootElement();
			List allChildren = root.getChildren();
			for (int v = 0; v < allChildren.size(); v++) {
				org.jdom.Element childNote = (org.jdom.Element) allChildren.get(v);
				String fieldName = childNote.getName().toLowerCase();//将传入标签名转成全部小写做为map的名子
				String fieldValue = childNote.getText() == null ? "" : childNote.getText().trim();
				//如果传入的页号为空，或为0，则将当前页写为1;
				map.put(fieldName, fieldValue);
				//System.out.println(fieldName+"输入的XML的查询字段");
			}

		} catch (JDOMParseException e) {// XML解析异常
			// log.error("Exception:",e);
			e.printStackTrace();
			errorMessage = addErrorXml(GridStatic.E_RETURN_FAILURE_OUTPUT_XML_B);
			log4j.error("类名：ReadOperationXML:函数名：parseInputXml:错误：JDOMParseException", e);
			return errorMessage;
		} catch (Exception e) {// 无法预测的异常
			// log.error("Exception:",e);
			e.printStackTrace();
			errorMessage = addErrorXml(GridStatic.E_RETURN_ERROR_B);
			log4j.error("类名：ReadOperationXML:函数名：parseInputXml:错误：Exception", e);
			return errorMessage;
		}
		return errorMessage;
	}
	/*
	 * 返回详细信息的XML格式字符串
	<?xml version="1.0" encoding="GB2312"?>
	<ROOT>
	<FLD NAME="字段名1" DESC="字段中文描述1" VALUES="字段值1" ISDISP="是否显示1/0"/>
	<FLD NAME="字段名2" DESC="字段中文描述2" VALUES="字段值2" ISDISP="是否显示1/0"/>
	。。。
	</ROOT>
	参数：dataList取得数据库中的数据
	parseHqlXml：解析hql.xml文件中的map
	queryCode：要取得的唯一CODE
*/
	public static String OperationPartDataXml(List dataList, Map parseHqlXml,String queryCode) {

		String messageXML = "";
		try {
			if (dataList == null) {
				dataList = new ArrayList();
			}
			
			org.jdom.Element element = new org.jdom.Element("ROOT");// 生成根节点

			org.jdom.Document document = new org.jdom.Document(element);// 生成document对像
			List queryList=new ArrayList();
			if(parseHqlXml.get("queryList")!=null){
				queryList=(List) parseHqlXml.get("queryList");
				}
			//System.out.println(queryList);
			Map dispfldMap=new HashMap();
			for (int v = 0; v < queryList.size(); v++) {
				Map queryMap = (HashMap) queryList.get(v);
				String code = queryMap.get("code").toString();				
				if (code.equalsIgnoreCase(queryCode)) {
					dispfldMap = queryMap.get("dispfldMap") == null ? null : (HashMap) queryMap.get("dispfldMap");
					break;
				}
			}

			for (int v = 0; v < dataList.size(); v++) {
				Map map = (HashMap) dataList.get(v);
				 //System.out.println(map);
				Iterator it = map.entrySet().iterator();
				
				
				while (it.hasNext()) {
					org.jdom.Element fldElement = new org.jdom.Element("FLD");// 生成returncode节点
					Map.Entry map1 = (Map.Entry) it.next();
					String name = map1.getKey() == null ? "" : map1.getKey().toString().trim();
					String value = map1.getValue() == null ? "" : map1.getValue().toString().trim();
					Attribute nameAttri = new Attribute("NAME",name.toUpperCase());//
					Attribute valueAttri = new Attribute("VALUES",value);//
					String dispfldValue="";
					if(dispfldMap!=null){
						//System.out.println(name);
						//System.out.println(dispfldMap.get(name)+"--=========="+name);
						//数据源中取得的字段名
					if(dataList.size()>1 && v>0){
						dispfldMap.clear();
						dispfldMap.put("fjname","附件");
						dispfldMap.put("oacommonpath", "路径");
						dispfldMap.put("filelist","附件");
						dispfldMap.put("emailpath", "路径");
						if(dispfldMap.get(name)!=null && dispfldMap.get(name).toString().trim().length()>0){
							System.out.println(dispfldMap.get(name));
							System.out.println(dispfldMap.get(name));
							dispfldValue=dispfldMap.get(name).toString().trim();
							Attribute descAttri = new Attribute("DESC",dispfldValue);//
							fldElement.setAttribute(descAttri);
							Attribute isdispAttri = new Attribute("ISDISP","1");//显示
							fldElement.setAttribute(isdispAttri);
						}else{
							Attribute descAttri = new Attribute("DESC","");//
							fldElement.setAttribute(descAttri);
							Attribute isdispAttri = new Attribute("ISDISP","0");//不显示
							fldElement.setAttribute(isdispAttri);
						}
						
					}else{
						
						if(dispfldMap.get(name)!=null && dispfldMap.get(name).toString().trim().length()>0){

							dispfldValue=dispfldMap.get(name).toString().trim();
							Attribute descAttri = new Attribute("DESC",dispfldValue);//
							fldElement.setAttribute(descAttri);
							Attribute isdispAttri = new Attribute("ISDISP","1");//显示
							fldElement.setAttribute(isdispAttri);
						}else{
							Attribute descAttri = new Attribute("DESC","");//
							fldElement.setAttribute(descAttri);
							Attribute isdispAttri = new Attribute("ISDISP","0");//不显示
							fldElement.setAttribute(isdispAttri);
						}
						
					}
					
					}
				
					fldElement.setAttribute(nameAttri);
					fldElement.setAttribute(valueAttri);
					element.addContent(fldElement);// 将returncode二级节点加入根节点
					
				}
				
			}

			messageXML = ParseXMLOutputter(document);
		} catch (Exception e) {
			e.printStackTrace();
			log4j.error("成生业务数据的XML格式字符串方法报错了！类名：ReadOperationXML:函数名：OperationPartDataXml:错误：Exception", e);
		}
		return messageXML;
	}
	/*
	 * 显示页面中下拉列表。
	 * 使用本方法时，只接受code,name这种形式，且hqldict文件的HQL语句，别名也必须起名为code,name。
	 * 返回字符串
	<?xml version="1.0" encoding="GBk"?>
	<ROOT>
	<ROOT>
  <LISTNAME NAME='NAME1'>
    <FLD NAME="朱成远" CODE="05116" />
    <FLD NAME="刘清" CODE="05078" />
    <FLD NAME="李传举" CODE="05076" />
 </LISTNAME>
  <LISTNAME NAME='NAME2'>
    <FLD NAME="朱成远" CODE="05116" />
    <FLD NAME="刘清" CODE="05078" />
    <FLD NAME="李传举" CODE="05076" />
 </LISTNAME>
	。。。
	</ROOT>
	参数：element传入的根节点,
	dataList取得的数据库中的数据
	selectName:列表所要起的名子,即<LISTNAME NAME='NAME1'>中name的值
*/
	public static String PartSelectListXml(org.jdom.Element element,List dataList,String selectName) {

		String messageXML = "";
		try {
			if (dataList == null) {
				dataList = new ArrayList();
			}			
			org.jdom.Element listelement = new org.jdom.Element("LISTNAME");// 生成LISTNAME二级根节点
			Attribute listnameAttri = new Attribute("NAME",selectName);//
			listelement.setAttribute(listnameAttri);
			//org.jdom.Document document = new org.jdom.Document(element);// 生成document对像	
			for (int v = 0; v < dataList.size(); v++) {
				Map map = (HashMap) dataList.get(v);
				 //System.out.println(map);
				String code=map.get("code")==null?"":map.get("code").toString();
				String name=map.get("name")==null?"":map.get("name").toString();				
					org.jdom.Element fldElement = new org.jdom.Element("FLD");// 生成returncode节点					
					Attribute nameAttri = new Attribute("NAME",name);//
					Attribute valueAttri = new Attribute("CODE",code);//														
					fldElement.setAttribute(nameAttri);
					fldElement.setAttribute(valueAttri);
					listelement.addContent(fldElement);// 将returncode二级节点加入根节点
				
				
			}
			element.addContent(listelement);
			//messageXML = ParseXMLOutputter(document);
		} catch (Exception e) {
			messageXML=GridStatic.E_RETURN_ERROR_B;
			e.printStackTrace();
			log4j.error("成生业务数据的XML格式字符串方法报错了！类名：ReadOperationXML:函数名：OperationPartDataXml:错误：Exception", e);
		}
		return messageXML;
	}
	//取得指定编码的总记录数的HQL语句
	public static String getCountHql(Map parseHqlXMLHashMap, String queryCode) {
		List ls = (ArrayList) parseHqlXMLHashMap.get("queryList");
		String hqlcount = "";
		if (ls != null) {
			for (int v = 0; v < ls.size(); v++) {
				Map queryMap = (HashMap) ls.get(v);
				String code = queryMap.get("code").toString();

				if (code.equalsIgnoreCase(queryCode)) {
					hqlcount = queryMap.get("hqlcountMap") == null ? "" : queryMap.get("hqlcountMap").toString();
					if(hqlcount.indexOf("order")==-1){
						
					}else{
						hqlcount = hqlcount.substring(0,hqlcount.indexOf("order"));
					}
					
					System.out.println("循环里的COUNT:"+hqlcount);
				}
			}
		}
		return hqlcount;
	}
	//计算总页数
	public static int getSumPage(PageBean pageBean) {
		int pageSum = 0;
		int rowCount1 = pageBean.getRowCount();
		int pageSize1 = pageBean.getPageSize();
		int pageNo1 = pageBean.getPageNo();
		if (rowCount1 % pageSize1 == 0) {
			pageSum = rowCount1 / pageSize1;
		} else {
			pageSum = rowCount1 / pageSize1 + 1;
		}
		return pageSum;
	}
	
	public static String addUserMessageXml(Map userMap) {
		String errorMessage = "";
		org.jdom.Element element = new org.jdom.Element("ROOT");// 生成根节点

		org.jdom.Document document = new org.jdom.Document(element);// 生成document对像
		Iterator it = userMap.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry map1 = (Map.Entry) it.next();
			String name = map1.getKey() == null ? "" : map1.getKey().toString().trim().toUpperCase();
			String value = map1.getValue() == null ? " " : map1.getValue().toString().trim();
			org.jdom.Element returncodeElement = new org.jdom.Element(name);// 生成returncode节点
			returncodeElement.setText(value);
			element.addContent(returncodeElement);// 将returncode二级节点加入根节点
		}
		

		errorMessage = ParseXMLOutputter(document);
		return errorMessage;
	}



}
