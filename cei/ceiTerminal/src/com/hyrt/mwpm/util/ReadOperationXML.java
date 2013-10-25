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
	private static String HQLDICT_PAGESIZE = "10";// ��hqldict.xml�е�pageSizeΪ��ʱ��Ĭ��һҳ��ʾ������
	private static String SIGN_BIG = "��";// >
	private static String SIGN_LESS = "��";// <
	private static String SIGN_LETTER = "####";// <

	/*
	 * �������쳣ʱ�����ɷ��ش�����Ϣ��XML��ʽ���ļ�
	 * <?xml version="1.0" encoding="gbk"?>
	<ROOT>
    	<RETURNCODE>B-E-01</RETURNCODE>
    	<RETURNMESSAGE>hqldict.xml����XML��������</RETURNMESSAGE>
	</ROOT>
	 */
	public static String addErrorXml(String errorStr) {
		if(errorStr==null){
			errorStr="";
		}
		String errorMessage = "";
		org.jdom.Element element = new org.jdom.Element("ROOT");// ���ɸ��ڵ�

		org.jdom.Document document = new org.jdom.Document(element);// ����document����
		org.jdom.Element returncodeElement = new org.jdom.Element("RETURNCODE");// ����returncode�ڵ�
		returncodeElement.setText(errorStr);
		element.addContent(returncodeElement);// ��returncode�����ڵ������ڵ�

		org.jdom.Element returnMessageElement = new org.jdom.Element("RETURNMESSAGE");// ����returncode�ڵ�
		String message = parseError(errorStr);
		log4j.debug("���صĴ�����Ϣ---------------"+message);
		returnMessageElement.setText(message);
		element.addContent(returnMessageElement);// ��returncode�����ڵ������ڵ�

		errorMessage = ParseXMLOutputter(document);
		return errorMessage;
	}

	// �������document�����ʽ
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
	 * ����errdict.xml �ļ���ָ���Ĵ����������ȡ��
	 *  <?xml version="1.0" encoding="gb2312"?>
	 * <root> 
	 * <err errno="A-E-00" errdesc="�޷�Ԥ��Ĵ���"/> 
	 * <err errno="A-E-01" errdesc="IP����XML��������"/> 
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
			//�Բ��������жϣ���������쳣
			List allChildren = root.getChildren();
			
			for (int v = 0; v < allChildren.size(); v++) {				
				org.jdom.Element childNote = (org.jdom.Element) allChildren.get(v);
				String errno = "" + childNote.getAttributeValue("errno").trim();
				String errdesc = "" + childNote.getAttributeValue("errdesc").trim();
				childMap.put(errno, errdesc);				
			}
			
		} catch (JDOMParseException e) {// XML�����쳣
			//System.out.println("JDOMParseException--XXXX-");
			errorMessage = GridStatic.E_RETURN_FAILURE_ERROR_XML_B;
			log4j.error("������ReadOperationXML:��������parseErrorXML:����JDOMParseException", e);
			// e.printStackTrace();
			childMap.put("errdict", errorMessage);
			return errorMessage;
		} catch (Exception e) {// �޷�Ԥ����쳣
			//System.out.println("Exception--XXXX-");
			errorMessage = GridStatic.E_RETURN_ERROR_B;
			log4j.error("������ReadOperationXML:��������parseErrorXML:����Exception", e);
			// e.printStackTrace();
			childMap.put("errdict", errorMessage);
			return errorMessage;
		}
		return errorMessage;
	}
	//�ӽ����õ�errdict.xml��map��ȡ��ָ����ֵ
	public static String parseError(String errorCode) {
		Map map = new HashMap();
		String errorMessage = "";
		String returnMessage = "";
		// errorMessage=parseErrorXML(map);
		map = GridStatic.ERROR_DICT_DATA;

		// ������XML��ʽ�ļ�������ʱ����ѱ��뷵��
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
	 * ����errdict.xml �ļ���ָ���Ĵ����������ȡ�� hqlXMLList��MAP�д�������ֵ��
	 * 1��pagesizeΪhqldict.xml��<pagesize>10</pagesize>����
	 * 2��queryListΪList����ÿ��ֵΪһ��map��map�е�ֵΪ��hqldict.xml��ÿ��query�ĸ����ԡ�
	 * 3����queryList�е�map����һ��wherefldMap��ֵΪһ��map��¼����<wherefld>�ӽڵ�����
	 *  <?xml version="1.0" encoding="gb2312"?> 
	 *  <root> 
	 *  <pagesize>10</pagesize> 
	 *  <query code="111" name="��ѯ����" querydesc="����˵��"> 
	 *  <hql>select new map(a.table1 as table1,a.table2 as table2,a.id as id) from table as a where 1=1</hql>
	 * <wherefld> 
	 * <table1>a.table1='QQQQ'</table1> 
	 * <table2>a.table2=QQQQ</table2>
	 * <start>a.start��'QQQQ'</start> 
	 * <end>a.end��'QQQQ'</end> 
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
										String whereCodeName = whereNote.getName().toLowerCase();//����XML�ļ��еı�ǩתΪСд
										String whereValue = whereNote.getText()==null?"":whereNote.getText().trim();
										equalsMap.put(whereCodeName, whereValue);
									}
									whereAllMap.put("equalsMap",equalsMap);
									
								}else if(wherefld!=null && wherefld.equals("like")){
									List likeChild = whereCodeNote.getChildren();
									Map equalsMap=new HashMap();
									for(int e=0;e<likeChild.size();e++){
										org.jdom.Element whereNote = (org.jdom.Element) likeChild.get(e);
										String whereCodeName = whereNote.getName().toLowerCase();//����XML�ļ��еı�ǩתΪСд
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

		} catch (JDOMParseException e) {// XML�����쳣
			//System.out.println("JDOMParseException--XXXX-");
			errorMessage = GridStatic.E_RETURN_FAILURE_HQLDICT_XML_B;
			log4j.error("������ReadOperationXML:��������parseHqlXML:����JDOMParseException", e);
			// e.printStackTrace();

			return errorMessage;
		} catch (Exception e) {// �޷�Ԥ����쳣
			//System.out.println("Exception--XXXX-");
			errorMessage = GridStatic.E_RETURN_ERROR_B;
			log4j.error("������ReadOperationXML:��������parseHqlXML:����Exception", e);
			return errorMessage;
		}
		return errorMessage;
	}

	/*
	 * queryCode,ȡ��HQL��code����,�Խ�������errdict.xml���ݽ��д���ƴ��HQL���
	 * queryCode:Ҫʹ�õ�HQL�ı��� outputWhereXMLMap:Ϊ�ֻ��ն˴����MXL��ʽ�����Ժ��MAP�ļ�
	 * parseHqlXml��Ϊhqldict.xml�����Ժ��map�ļ�
	 * ����ֵ����������£�������ƴ�Ӻõ�HQL��䣬���ṩ�ĺ������û���ҵ�ʱ���򷵻ؿմ���
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
			//�Բ������жϣ�����ȡֵʱ�����쳣
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
				if(querytype.equals("0")){//��ѯ���ͣ���ȷ0/ģ��1
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
			//System.out.println("ƴ�õ�SQL:"+daohqlCount);
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
			log4j.error("ƴSQL�ķ��������ˣ�������ReadOperationXML:��������makeUpHql:����Exception", e);
		}
		return daoHql;
	}

	/*��ѯ�б��XML��ʽ���ַ���
	 * ͨ��ȡ�õ����ݿ��������ɷ��ص�ҵ�����ݵ�XML��ʽ�ַ��� ������ 
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
			org.jdom.Element element = new org.jdom.Element("ROOT");// ���ɸ��ڵ�

			org.jdom.Document document = new org.jdom.Document(element);// ����document����

			//org.jdom.Element pageNoElement = new org.jdom.Element("PAGENO");// ��ǰҳ
			//pageNoElement.setText(pageNo);
			//element.addContent(pageNoElement);//
			//������ĵ�ǰҳΪ1ʱ������ҳ�������򣬲����ýڵ�
			if (pageNo.equals("1")) {
				org.jdom.Element pageSumElement = new org.jdom.Element("ALLPAGES");// ��ҳ��
				pageSumElement.setText(pageSum + "");
				element.addContent(pageSumElement);//
			}

			for (int v = 0; v < dataList.size(); v++) {
				HashMap map = (HashMap) dataList.get(v);
				// System.out.println(map);
				Iterator it = map.entrySet().iterator();
				org.jdom.Element displistElement = new org.jdom.Element("DISPLIST");// ����returncode�ڵ�
				while (it.hasNext()) {
					Map.Entry map1 = (Map.Entry) it.next();
					String name = map1.getKey() == null ? "" : map1.getKey().toString().trim();
					String value = map1.getValue() == null ? "" : map1.getValue().toString().trim();
					org.jdom.Element dataElement = new org.jdom.Element(name.toUpperCase());// �����ɵ�XML��ʽ�ļ�תΪ��д
					dataElement.addContent(value);//
					displistElement.addContent(dataElement);
				}
				element.addContent(displistElement);// ��returncode�����ڵ������ڵ�
			}

			messageXML = ParseXMLOutputter(document);
		} catch (Exception e) {
			e.printStackTrace();
			log4j.error("����ҵ�����ݵ�XML��ʽ�ַ������������ˣ�������ReadOperationXML:��������OperationQueryListDataXml:����Exception", e);
		}
		return messageXML;
	}
	/*
	 * �����ֻ��ն˴���ķ�ҳ��XML��ʽ���ַ���
	 * "<?xml version=\"1.0\" encoding=\"GB2312\"?>" + 
		"<ROOT>" + 
		"<PAGENO>1</PAGENO>" + 
		"<TABLE1>aaa</TABLE1>"+
		"<TABLE0>aaa</TABLE0>" + 
		"<START>2009-02-01</START>" + 
		"</ROOT>";
	 * ������
	 * xmlStr���ֻ��ն˴����XML��ʽ���ַ�����
	 * map������һ���յ�MAP��Ϊ���ǽ������Ľ�����浽��MAP��
	 */
	public static String parseInputDataXml(String xmlStr, Map map) {
		// Map xmlStrMap=new HashMap();
		String errorMessage = "";
		try {
			//�Բ������жϣ���������쳣
			if(xmlStr==null){
				xmlStr="";
			}
			if(map==null){
				map=new HashMap();
			}
			//System.out.println("�����XML��ʽ���ļ���"+xmlStr);
			SAXBuilder builder = new SAXBuilder();
			Reader in = new StringReader(xmlStr);
			org.jdom.Document doc = builder.build(in);
			org.jdom.Element root = doc.getRootElement();
			List allChildren = root.getChildren();
			for (int v = 0; v < allChildren.size(); v++) {
				org.jdom.Element childNote = (org.jdom.Element) allChildren.get(v);
				String fieldName = childNote.getName().toLowerCase();//�������ǩ��ת��ȫ��Сд��Ϊmap������
				String fieldValue = childNote.getText() == null ? "" : childNote.getText().trim();
				//��������ҳ��Ϊ�գ���Ϊ0���򽫵�ǰҳдΪ1;
				if(fieldName.equalsIgnoreCase("pageno") && (fieldValue.length()==0 || fieldValue.equals("0"))){
					fieldValue="1";
				}
				map.put(fieldName, fieldValue);
				//System.out.println(fieldName+"�����XML�Ĳ�ѯ�ֶ�");
			}

		} catch (JDOMParseException e) {// XML�����쳣
			// log.error("Exception:",e);
			e.printStackTrace();
			errorMessage = addErrorXml(GridStatic.E_RETURN_FAILURE_OUTPUT_XML_B);
			log4j.error("������ReadOperationXML:��������parseInputDataXml:����JDOMParseException", e);
			return errorMessage;
		} catch (Exception e) {// �޷�Ԥ����쳣
			// log.error("Exception:",e);
			e.printStackTrace();
			errorMessage = addErrorXml(GridStatic.E_RETURN_ERROR_B);
			log4j.error("������ReadOperationXML:��������parseInputDataXml:����Exception", e);
			return errorMessage;
		}
		return errorMessage;
	}
	/*
	 * �����ֻ��ն˴���¼���XML��ʽ���ַ���
	 * "<?xml version=\"1.0\" encoding=\"GBk\"?>" + 
		"<ROOT>" + 
		"<TABLE1>aaa</TABLE1>"+
		"<TABLE0>aaa</TABLE0>" + 
		"<START>2009-02-01</START>" + 
		"</ROOT>";
	 * ������
	 * xmlStr���ֻ��ն˴����XML��ʽ���ַ�����
	 * map������һ���յ�MAP��Ϊ���ǽ������Ľ�����浽��MAP��
	 */
	public static String parseInputXml(String xmlStr, Map map) {
		// Map xmlStrMap=new HashMap();
		String errorMessage = "";
		try {
			//�Բ������жϣ���������쳣
			if(xmlStr==null){
				xmlStr="";
			}
			if(map==null){
				map=new HashMap();
			}
			//System.out.println("�����XML��ʽ���ļ���"+xmlStr);
			SAXBuilder builder = new SAXBuilder();
			Reader in = new StringReader(xmlStr);
			org.jdom.Document doc = builder.build(in);
			org.jdom.Element root = doc.getRootElement();
			List allChildren = root.getChildren();
			for (int v = 0; v < allChildren.size(); v++) {
				org.jdom.Element childNote = (org.jdom.Element) allChildren.get(v);
				String fieldName = childNote.getName().toLowerCase();//�������ǩ��ת��ȫ��Сд��Ϊmap������
				String fieldValue = childNote.getText() == null ? "" : childNote.getText().trim();
				//��������ҳ��Ϊ�գ���Ϊ0���򽫵�ǰҳдΪ1;
				map.put(fieldName, fieldValue);
				//System.out.println(fieldName+"�����XML�Ĳ�ѯ�ֶ�");
			}

		} catch (JDOMParseException e) {// XML�����쳣
			// log.error("Exception:",e);
			e.printStackTrace();
			errorMessage = addErrorXml(GridStatic.E_RETURN_FAILURE_OUTPUT_XML_B);
			log4j.error("������ReadOperationXML:��������parseInputXml:����JDOMParseException", e);
			return errorMessage;
		} catch (Exception e) {// �޷�Ԥ����쳣
			// log.error("Exception:",e);
			e.printStackTrace();
			errorMessage = addErrorXml(GridStatic.E_RETURN_ERROR_B);
			log4j.error("������ReadOperationXML:��������parseInputXml:����Exception", e);
			return errorMessage;
		}
		return errorMessage;
	}
	/*
	 * ������ϸ��Ϣ��XML��ʽ�ַ���
	<?xml version="1.0" encoding="GB2312"?>
	<ROOT>
	<FLD NAME="�ֶ���1" DESC="�ֶ���������1" VALUES="�ֶ�ֵ1" ISDISP="�Ƿ���ʾ1/0"/>
	<FLD NAME="�ֶ���2" DESC="�ֶ���������2" VALUES="�ֶ�ֵ2" ISDISP="�Ƿ���ʾ1/0"/>
	������
	</ROOT>
	������dataListȡ�����ݿ��е�����
	parseHqlXml������hql.xml�ļ��е�map
	queryCode��Ҫȡ�õ�ΨһCODE
*/
	public static String OperationPartDataXml(List dataList, Map parseHqlXml,String queryCode) {

		String messageXML = "";
		try {
			if (dataList == null) {
				dataList = new ArrayList();
			}
			
			org.jdom.Element element = new org.jdom.Element("ROOT");// ���ɸ��ڵ�

			org.jdom.Document document = new org.jdom.Document(element);// ����document����
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
					org.jdom.Element fldElement = new org.jdom.Element("FLD");// ����returncode�ڵ�
					Map.Entry map1 = (Map.Entry) it.next();
					String name = map1.getKey() == null ? "" : map1.getKey().toString().trim();
					String value = map1.getValue() == null ? "" : map1.getValue().toString().trim();
					Attribute nameAttri = new Attribute("NAME",name.toUpperCase());//
					Attribute valueAttri = new Attribute("VALUES",value);//
					String dispfldValue="";
					if(dispfldMap!=null){
						//System.out.println(name);
						//System.out.println(dispfldMap.get(name)+"--=========="+name);
						//����Դ��ȡ�õ��ֶ���
					if(dataList.size()>1 && v>0){
						dispfldMap.clear();
						dispfldMap.put("fjname","����");
						dispfldMap.put("oacommonpath", "·��");
						dispfldMap.put("filelist","����");
						dispfldMap.put("emailpath", "·��");
						if(dispfldMap.get(name)!=null && dispfldMap.get(name).toString().trim().length()>0){
							System.out.println(dispfldMap.get(name));
							System.out.println(dispfldMap.get(name));
							dispfldValue=dispfldMap.get(name).toString().trim();
							Attribute descAttri = new Attribute("DESC",dispfldValue);//
							fldElement.setAttribute(descAttri);
							Attribute isdispAttri = new Attribute("ISDISP","1");//��ʾ
							fldElement.setAttribute(isdispAttri);
						}else{
							Attribute descAttri = new Attribute("DESC","");//
							fldElement.setAttribute(descAttri);
							Attribute isdispAttri = new Attribute("ISDISP","0");//����ʾ
							fldElement.setAttribute(isdispAttri);
						}
						
					}else{
						
						if(dispfldMap.get(name)!=null && dispfldMap.get(name).toString().trim().length()>0){

							dispfldValue=dispfldMap.get(name).toString().trim();
							Attribute descAttri = new Attribute("DESC",dispfldValue);//
							fldElement.setAttribute(descAttri);
							Attribute isdispAttri = new Attribute("ISDISP","1");//��ʾ
							fldElement.setAttribute(isdispAttri);
						}else{
							Attribute descAttri = new Attribute("DESC","");//
							fldElement.setAttribute(descAttri);
							Attribute isdispAttri = new Attribute("ISDISP","0");//����ʾ
							fldElement.setAttribute(isdispAttri);
						}
						
					}
					
					}
				
					fldElement.setAttribute(nameAttri);
					fldElement.setAttribute(valueAttri);
					element.addContent(fldElement);// ��returncode�����ڵ������ڵ�
					
				}
				
			}

			messageXML = ParseXMLOutputter(document);
		} catch (Exception e) {
			e.printStackTrace();
			log4j.error("����ҵ�����ݵ�XML��ʽ�ַ������������ˣ�������ReadOperationXML:��������OperationPartDataXml:����Exception", e);
		}
		return messageXML;
	}
	/*
	 * ��ʾҳ���������б�
	 * ʹ�ñ�����ʱ��ֻ����code,name������ʽ����hqldict�ļ���HQL��䣬����Ҳ��������Ϊcode,name��
	 * �����ַ���
	<?xml version="1.0" encoding="GBk"?>
	<ROOT>
	<ROOT>
  <LISTNAME NAME='NAME1'>
    <FLD NAME="���Զ" CODE="05116" />
    <FLD NAME="����" CODE="05078" />
    <FLD NAME="���" CODE="05076" />
 </LISTNAME>
  <LISTNAME NAME='NAME2'>
    <FLD NAME="���Զ" CODE="05116" />
    <FLD NAME="����" CODE="05078" />
    <FLD NAME="���" CODE="05076" />
 </LISTNAME>
	������
	</ROOT>
	������element����ĸ��ڵ�,
	dataListȡ�õ����ݿ��е�����
	selectName:�б���Ҫ�������,��<LISTNAME NAME='NAME1'>��name��ֵ
*/
	public static String PartSelectListXml(org.jdom.Element element,List dataList,String selectName) {

		String messageXML = "";
		try {
			if (dataList == null) {
				dataList = new ArrayList();
			}			
			org.jdom.Element listelement = new org.jdom.Element("LISTNAME");// ����LISTNAME�������ڵ�
			Attribute listnameAttri = new Attribute("NAME",selectName);//
			listelement.setAttribute(listnameAttri);
			//org.jdom.Document document = new org.jdom.Document(element);// ����document����	
			for (int v = 0; v < dataList.size(); v++) {
				Map map = (HashMap) dataList.get(v);
				 //System.out.println(map);
				String code=map.get("code")==null?"":map.get("code").toString();
				String name=map.get("name")==null?"":map.get("name").toString();				
					org.jdom.Element fldElement = new org.jdom.Element("FLD");// ����returncode�ڵ�					
					Attribute nameAttri = new Attribute("NAME",name);//
					Attribute valueAttri = new Attribute("CODE",code);//														
					fldElement.setAttribute(nameAttri);
					fldElement.setAttribute(valueAttri);
					listelement.addContent(fldElement);// ��returncode�����ڵ������ڵ�
				
				
			}
			element.addContent(listelement);
			//messageXML = ParseXMLOutputter(document);
		} catch (Exception e) {
			messageXML=GridStatic.E_RETURN_ERROR_B;
			e.printStackTrace();
			log4j.error("����ҵ�����ݵ�XML��ʽ�ַ������������ˣ�������ReadOperationXML:��������OperationPartDataXml:����Exception", e);
		}
		return messageXML;
	}
	//ȡ��ָ��������ܼ�¼����HQL���
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
					
					System.out.println("ѭ�����COUNT:"+hqlcount);
				}
			}
		}
		return hqlcount;
	}
	//������ҳ��
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
		org.jdom.Element element = new org.jdom.Element("ROOT");// ���ɸ��ڵ�

		org.jdom.Document document = new org.jdom.Document(element);// ����document����
		Iterator it = userMap.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry map1 = (Map.Entry) it.next();
			String name = map1.getKey() == null ? "" : map1.getKey().toString().trim().toUpperCase();
			String value = map1.getValue() == null ? " " : map1.getValue().toString().trim();
			org.jdom.Element returncodeElement = new org.jdom.Element(name);// ����returncode�ڵ�
			returncodeElement.setText(value);
			element.addContent(returncodeElement);// ��returncode�����ڵ������ڵ�
		}
		

		errorMessage = ParseXMLOutputter(document);
		return errorMessage;
	}



}
