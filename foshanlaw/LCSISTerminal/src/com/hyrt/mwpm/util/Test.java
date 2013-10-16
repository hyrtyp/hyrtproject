package com.hyrt.mwpm.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class Test {
	
	 public static Map readProperties(String src) {  
	        Map map = new HashMap();  
	        try {  
	        	for (int i = 0; i < 100; i++) {
	                map.put("ok"+i, "no"+i);//把properties文件中的key-value存放到一个map中  
				}
	            return map;  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	        return null;  
	    }  
	  
	    public static String replaceCheck(Map map,String name) {  
	        Set<String> keys = map.keySet();  
	        Iterator<String> iter = keys.iterator();  
	        while (iter.hasNext()) {  
	            String key = iter.next();  
	            String value = (String) map.get(key);  
	            if (name.contains(key)) {  
	                name=name.replace(key, value);//对于符合map中的key值实现替换功能  
	                  
	            }  
	        }  
	        return name;  
	    }  
	    
	    
	    public static void main(String[] args) {
	    	MmochatFilterService m = new MmochatFilterService();
	    	String a = "大风歌放入你妈B你妈B太热给他你妈B让他突然浑然我操你妈一我操你妈体";
	    	String b = MmochatFilterService.getFilterString(a);
	    	System.out.println(b);
		}

}
