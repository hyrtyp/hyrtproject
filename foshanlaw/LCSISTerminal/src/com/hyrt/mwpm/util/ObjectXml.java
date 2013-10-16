package com.hyrt.mwpm.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class ObjectXml {
	private XStream xstream;

	public ObjectXml() {
		if (xstream == null) {
			xstream = new XStream(new DomDriver());
		}
	}

	/**
	 * 初始化生成XML文件
	 * 
	 * @param o
	 *            (object对象)，xmlName(XML文件生成的地址)
	 * 
	 * @return null
	 * 
	 */
	public void createXml(Object o, String xmlName) {

		xstream.alias("", Object.class);
		try {
			FileOutputStream fos = new FileOutputStream(new File(xmlName));
			OutputStreamWriter osr = new OutputStreamWriter(fos, Charset
					.forName("UTF-8"));
			xstream.toXML(o, osr);
			osr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 读取XML文件
	 * 
	 * @param obj
	 *            (entity对象),xmlName(XML文件生成的地址)
	 * 
	 * @return list集合
	 */
	public List<Object> readXml(Class obj, String xmlName,String name) {
		ArrayList<Object> dp = new ArrayList<Object>();
		File ftemp = new File(xmlName);
		xstream.alias(name, obj);
		try {
			dp = (ArrayList) xstream.fromXML(new InputStreamReader(
					new FileInputStream(ftemp), Charset.forName("UTF-8")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return dp;
	}

	/**
	 * 修改XML节点代码
	 * 
	 * @param id
	 *            (entity类对象id), o(object对象)，xmlName(XML文件生成的地址)
	 * 
	 * @return null
	 * 
	 */
	public void upDateMySQLRecentHost(String id, Object o, String xmlName,String name) {
		File ftemp = new File(xmlName);
		xstream.alias(name, o.getClass());
		try {
			ArrayList<Object> dp = (ArrayList) xstream
					.fromXML(new InputStreamReader(new FileInputStream(ftemp),
							Charset.forName("UTF-8")));
			for (int k = 0; dp != null && k < dp.size(); k++) {
				Object da = dp.get(k);
				if (da.equals(id)) {
					dp.remove(k); // 移除 指定的节点
					dp.add(o); // 添加 定义的新节点
				}
			}
			FileOutputStream fos = new FileOutputStream(new File(xmlName));
			OutputStreamWriter osr = new OutputStreamWriter(fos, Charset
					.forName("UTF-8"));
			xstream.toXML(dp, osr);
			osr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 新增XML文件和添加XML节点代码
	 * 
	 * @param o
	 *            (object对象)，xmlName(XML文件生成的地址)
	 * 
	 * @return null
	 */
	public void AddMySQLRecentHost(Object o, String xmlName,String name) {
		File ftemp = new File(xmlName);
		xstream.alias(name, o.getClass());
		try {
			ArrayList<Object> dp = (ArrayList) xstream
					.fromXML(new InputStreamReader(new FileInputStream(ftemp),
							Charset.forName("UTF-8")));
			dp.add(o); // 添加到 List集合里面。
			FileOutputStream fos = new FileOutputStream(new File(xmlName));
			OutputStreamWriter osr = new OutputStreamWriter(fos, Charset
					.forName("UTF-8"));
			xstream.toXML(dp, osr);
			osr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 删除XML节点代码
	 * 
	 * @param id
	 *            (entity类对象id), obj(entity对象)，xmlName(XML文件生成的地址)
	 * 
	 * @return null
	 */
	public void deleteMySQLRecentHost(String id, Class obj, String xmlName,String name)
			{

		File ftemp = new File(xmlName);
		xstream.alias(name, obj);
		try {
			ArrayList<Object> dp = (ArrayList) xstream
					.fromXML(new InputStreamReader(new FileInputStream(ftemp),
							Charset.forName("UTF-8")));
			for (int k = 0; dp != null && k < dp.size(); k++) {
				Object da = dp.get(k);
				if (da.equals(id)) {
					dp.remove(k); // 移除 指定的节点
				}
			}
			FileOutputStream fos = new FileOutputStream(new File(xmlName));
			OutputStreamWriter osr = new OutputStreamWriter(fos, Charset
					.forName("UTF-8"));
			xstream.toXML(dp, osr);
			osr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
}
