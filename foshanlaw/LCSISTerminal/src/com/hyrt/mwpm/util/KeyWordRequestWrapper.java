package com.hyrt.mwpm.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class KeyWordRequestWrapper extends HttpServletRequestWrapper{
	 
	public Map keyMap;  
	  
	public KeyWordRequestWrapper(HttpServletRequest request,Map keyMap) {
		super(request);      
		this.keyMap = keyMap;  
	}

	 
    public Map getParameterMap() {  
        super.getContextPath();  
        Map<String,String[]> map = super.getParameterMap();  
        if(!map.isEmpty()){  
            Set<String> keySet = map.keySet();  
            Iterator<String> keyIt = keySet.iterator();  
            while(keyIt.hasNext()){  
                String key = keyIt.next();  
//              String value = map.get(key)[0];  
//              map.get(key)[0] = this.replaceParam(value);  
                //这边实现对整个数组的判断。  
                String[] values=map.get(key);  
                for(int i=0;i<values.length;i++){  
                    map.get(key)[i]=this.replaceParam(values[i]);  
                }  
            }  
        }  
        return map;  
    } 
    
    public String replaceParam(String name){  
        return Test.replaceCheck(keyMap,name);  
    } 
}
