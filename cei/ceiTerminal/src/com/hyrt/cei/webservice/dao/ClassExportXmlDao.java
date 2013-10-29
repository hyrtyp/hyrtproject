package com.hyrt.cei.webservice.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.hyrt.cei.vo.MwpmDictFunction;
import com.hyrt.cei.vo.MwpmSysClass;
import com.hyrt.mwpm.util.ObjectXml;

/**
 *    author:  tanjie
 *    project: �����������·�������Դ���μ������棩xml�ļ�
 *    describe��
 */
@Repository("classExportXmlDao")
public class ClassExportXmlDao extends ObjectXml{
	private static Logger log4j = Logger.getLogger(ClassExportXmlDao.class);
	
	
	

	/**
	 * �����û���Ϣ
	 * 
	 */
	public void addClass(Object o,String xmlName,String name,int num) {
		if(num==0){
			List list = new ArrayList();
			list.add(o);
			super.createXml(list, xmlName);
		}else{
			super.AddMySQLRecentHost(o, xmlName,name);
		}
	}




	public List<Object> findRole(String xmlName, String name) {
		return super.readXml(MwpmDictFunction.class, xmlName,name);
	}


	public List<Object> findClass(String xmlName, String name) {
		return super.readXml(MwpmSysClass.class, xmlName,name);
	}


	public List<Object> findFunction(String xmlName, String name) {
		return super.readXml(MwpmDictFunction.class, xmlName,name);
	}



}
