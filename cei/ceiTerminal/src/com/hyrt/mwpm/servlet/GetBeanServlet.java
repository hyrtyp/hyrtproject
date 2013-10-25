package com.hyrt.mwpm.servlet;

import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.hyrt.mwpm.util.GridStatic;
import com.hyrt.mwpm.util.ReadOperationXML;



public class GetBeanServlet extends HttpServlet {
	private static WebApplicationContext context;

	public void init() throws ServletException {
		
		try {		
			GridStatic.ABSOLUTENESS_PATH = getServletContext().getRealPath("/");
			ServletContext application;
		       
	        application = getServletContext();
	        context = WebApplicationContextUtils.getWebApplicationContext(application);//获取spring的context
	        
	        HashMap parseHqlXMLHashMap=new HashMap();
	        //初始化hqldict.xml文件到静态存储区中
	        String HQLerrorCode=ReadOperationXML.parseHqlXML(GridStatic.HQL_DICT_DATA);
	        //errdict.xml
	        String errorCode=ReadOperationXML.parseErrorXML(GridStatic.ERROR_DICT_DATA);
	        //System.out.println("错误码－－－－："+errorCode);
	        if(HQLerrorCode.length()>0){
	        	GridStatic.HQL_DICT_DATA.put("error", HQLerrorCode);
	        }
	        if(errorCode.length()>0){
	        	GridStatic.ERROR_DICT_DATA.put("errdictMessage", errorCode);
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Object getBean(String id) {
		Object bean = context.getBean(id);
		//System.out.println("servlet====" + bean);
		return bean;
	}

}
