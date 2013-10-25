package com.hyrt.mwpm.util;

import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jdom.input.JDOMParseException;
import org.jdom.input.SAXBuilder;

public class ReadXML {
	
	private static Logger log = Logger.getLogger(ReadXML.class);
	/**解析XML格式：
	 *	String str = " <?xml version='1.0' encoding='GBK' standalone='yes' ?>"+
						"<ROOT>"+
						"<ID>01000001</ID>"+
						"<LOGINNAME>张三</ LOGINNAME >"+
						"<PASSWORD>123</PASSWORD>"+
						"</ROOT>";

	 */
	public static String parseXML(String xmlStr,Map xmlStrMap,String[] childName){
		//Map xmlStrMap=new HashMap();
		 String errorMessage="";
		try{
		 SAXBuilder builder = new SAXBuilder();
         Reader in = new StringReader(xmlStr);
         org.jdom.Document doc = builder.build(in);
         org.jdom.Element root = doc.getRootElement();
         List allChildren = root.getChildren();
         for (int v = 0; v < allChildren.size(); v++) {
         org.jdom.Element childNote = (org.jdom.Element) allChildren.get(v);
         errorMessage=getChildNotePutMap(xmlStrMap,childNote,childName);
         }
        
		}catch(JDOMParseException e){//XML解析异常
			log.error("Exception:",e);      
			e.printStackTrace();
			return errorMessage;
		}
		catch(Exception e){//无法预测的异常
			log.error("Exception:",e);  
			e.printStackTrace();
			return errorMessage;
		}
         return errorMessage;
         }
	private  static String getChildNotePutMap(Map xmlStrMap,org.jdom.Element childNote,String[] childName){
		//ID用户ID;LOGINNAME用户名字;PASSWORD用户新密码
		//String childName[]={"ID","LOGINNAME","PASSWORD"};
		String errorMessage="";
       try{
		String elementName = childNote.getName();//取得当前节点的名子
        for(int v=0;v<childName.length;v++){
        	if (elementName.equalsIgnoreCase(childName[v])) {
              	 String noteText = childNote.getText();//取得标签内容
              	 xmlStrMap.put(childName[v], noteText);
             }
        }
       }
		catch(Exception e){//无法预测的异常
			e.printStackTrace();
			log.error("Exception:",e);  
			return errorMessage;
		}
		return errorMessage;
	}
	public   static String addErrorXml(String errorStr){
		 String errorMessage="";
		org.jdom.Element element = new org.jdom.Element("ROOT");//生成根节点
		org.jdom.Document document = new org.jdom.Document(element);//生成document对像
		org.jdom.Element returncodeElement = new org.jdom.Element("RETURNCODE");//生成returncode节点	
		returncodeElement.setText(errorStr);
		element.addContent(returncodeElement);//将returncode二级节点加入根节点
		errorMessage=PrincipalUtil.ParseXMLOutputter(document);
		return errorMessage;
	}
	public static String getTrace(Throwable t) {
        StringWriter stringWriter= new StringWriter();
        PrintWriter writer= new PrintWriter(stringWriter);
        t.printStackTrace(writer);
        StringBuffer buffer= stringWriter.getBuffer();
        return buffer.toString();
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

		} catch (Exception e) {// 无法预测的异常
			errorMessage = "false";
		}
		return errorMessage;
	}
}
