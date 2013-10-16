package com.hyrt.mwpm.util;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class KeyWordFilter implements Filter{
	
	private FilterConfig filterConfig;  
    
    public static HashMap keyMap = null;  
    public static String path;  
      
    public void init(FilterConfig filterConfig) throws ServletException {  
        this.filterConfig=filterConfig;  
        String keyWordPath = filterConfig.getInitParameter("key");  
        path = filterConfig.getServletContext().getRealPath(keyWordPath);  
          
    }  
      
    public void doFilter(ServletRequest request, ServletResponse response,   
            FilterChain chain) throws IOException, ServletException {  
        HttpServletRequest req = (HttpServletRequest)request;  
        if(keyMap == null){  
                keyMap = (HashMap)Test.readProperties(path);  
        }  
        if(req.getMethod().equals("POST")){  
            chain.doFilter(new KeyWordRequestWrapper(req,keyMap), response);  
        }else{  
            chain.doFilter(request, response);  
        }  
    }  
  
    @Override  
    public void destroy() {  
        this.filterConfig = null;   
    }  
}
