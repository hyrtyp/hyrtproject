package com.hyrt.cei.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 工具类
 * 
 * 
 */
public class MyTools {
	
	public static String url;
	public static String UPDATE_GISTIME;
	public static String LOCAL_PATH;
	public static String CHECK_TASKTIME;
	public static String UPLOAD_PATH;
	public static String TJFX_URL;
	public static String RETCOAD;
	public static String TABLE_URL;
	static {
		loadConfigurations();
	}

	public static void loadConfigurations() {
		InputStream in = MyTools.class
				.getResourceAsStream("/assets/configuration.properties");
		Properties p = new Properties();
		try {
			p.load(in);
			url = p.getProperty("URL");
			UPDATE_GISTIME = p.getProperty("UPDATE_GISTIME");
			LOCAL_PATH = p.getProperty("LOCAL_PATH");
			CHECK_TASKTIME = p.getProperty("CHECK_TASKTIME");
			UPLOAD_PATH = p.getProperty("UPLOAD_PATH");
			TJFX_URL = p.getProperty("TJFX_URL");
			RETCOAD=p.getProperty("RETCODE");
			TABLE_URL=p.getProperty("TABLE_URL");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
