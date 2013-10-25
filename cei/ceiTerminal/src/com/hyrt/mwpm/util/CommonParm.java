package com.hyrt.mwpm.util;

import java.util.MissingResourceException;
import java.util.Random;
import java.util.ResourceBundle;

/**

* 获取配置资源文件 [公共参数] 信息

* @author tanJie

*/

public class CommonParm {
	
	private static ResourceBundle resourceBundle  = ResourceBundle.getBundle("commonparm");

	public static String getString(String key) {
		if (key == null || key.equals("") || key.equals("null")) {
			return "";
		}
		String result = "";
		try {
			result = resourceBundle.getString(key);
			
		} catch (MissingResourceException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	


	 
	//获取随机数和随机字母组合
	public String getCharAndNumr(int length)   
	{   
	    String val = "";   
	           
	    Random random = new Random();   
	    for(int i = 0; i < length; i++)   
	    {   
	        String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // 输出字母还是数字   
	               
	        if("char".equalsIgnoreCase(charOrNum)) // 字符串   
	        {   
	            int choice = 97; //取得小写字母   
	            val += (char) (choice + random.nextInt(26));   
	        }   
	        else if("num".equalsIgnoreCase(charOrNum)) // 数字   
	        {   
	            val += String.valueOf(random.nextInt(10));   
	        }   
	    }   
	           
	    return val;   
	} 
}


