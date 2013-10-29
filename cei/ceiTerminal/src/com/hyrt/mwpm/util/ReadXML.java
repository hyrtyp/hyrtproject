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
	/**����XML��ʽ��
	 *	String str = " <?xml version='1.0' encoding='GBK' standalone='yes' ?>"+
						"<ROOT>"+
						"<ID>01000001</ID>"+
						"<LOGINNAME>����</ LOGINNAME >"+
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
        
		}catch(JDOMParseException e){//XML�����쳣
			log.error("Exception:",e);      
			e.printStackTrace();
			return errorMessage;
		}
		catch(Exception e){//�޷�Ԥ����쳣
			log.error("Exception:",e);  
			e.printStackTrace();
			return errorMessage;
		}
         return errorMessage;
         }
	private  static String getChildNotePutMap(Map xmlStrMap,org.jdom.Element childNote,String[] childName){
		//ID�û�ID;LOGINNAME�û�����;PASSWORD�û�������
		//String childName[]={"ID","LOGINNAME","PASSWORD"};
		String errorMessage="";
       try{
		String elementName = childNote.getName();//ȡ�õ�ǰ�ڵ������
        for(int v=0;v<childName.length;v++){
        	if (elementName.equalsIgnoreCase(childName[v])) {
              	 String noteText = childNote.getText();//ȡ�ñ�ǩ����
              	 xmlStrMap.put(childName[v], noteText);
             }
        }
       }
		catch(Exception e){//�޷�Ԥ����쳣
			e.printStackTrace();
			log.error("Exception:",e);  
			return errorMessage;
		}
		return errorMessage;
	}
	public   static String addErrorXml(String errorStr){
		 String errorMessage="";
		org.jdom.Element element = new org.jdom.Element("ROOT");//���ɸ��ڵ�
		org.jdom.Document document = new org.jdom.Document(element);//����document����
		org.jdom.Element returncodeElement = new org.jdom.Element("RETURNCODE");//����returncode�ڵ�	
		returncodeElement.setText(errorStr);
		element.addContent(returncodeElement);//��returncode�����ڵ������ڵ�
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

		} catch (Exception e) {// �޷�Ԥ����쳣
			errorMessage = "false";
		}
		return errorMessage;
	}
}
