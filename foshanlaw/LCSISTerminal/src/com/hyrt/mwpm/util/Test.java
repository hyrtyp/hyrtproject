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
	                map.put("ok"+i, "no"+i);//��properties�ļ��е�key-value��ŵ�һ��map��  
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
	                name=name.replace(key, value);//���ڷ���map�е�keyֵʵ���滻����  
	                  
	            }  
	        }  
	        return name;  
	    }  
	    
	    
	    public static void main(String[] args) {
	    	MmochatFilterService m = new MmochatFilterService();
	    	String a = "�����������B����B̫�ȸ�������B����ͻȻ��Ȼ�Ҳ�����һ�Ҳ�������";
	    	String b = MmochatFilterService.getFilterString(a);
	    	System.out.println(b);
		}

}
