package com.hyrt.mwpm.servlet;


import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class GetBeanServlet extends HttpServlet {
	private static WebApplicationContext context;

	public void init() throws ServletException {
			ServletContext application = getServletContext();
	        context = WebApplicationContextUtils.getWebApplicationContext(application);//»ñÈ¡springµÄcontext
	}

	public static Object getBean(String id) {
		Object bean = context.getBean(id);
		return bean;
	}

}
