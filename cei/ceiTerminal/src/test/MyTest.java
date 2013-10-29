package test;

import junit.framework.TestCase;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hyrt.mwpm.webservice.service.IEntinfoService;


public class MyTest extends TestCase {

	
	public static BeanFactory factory = new ClassPathXmlApplicationContext("/spring/*.xml");
	
	/**
	 * 测试企业概要信息列表
	 */
  public void testGetShCLQuery(){
		String xml="<?xml version=\"1.0\" encoding=\"GB2312\"?>" +
			"<ROOT>" +
			"<QUERYTYPE>0</QUERYTYPE>"+
			"<PAGENO>1</PAGENO>"+
			"<PAGESIZE>10</PAGESIZE>"+
			"<SQYMC></SQYMC>"+
			"<SZCH></SZCH>"+
			"<SFZR></SFZR>"+
			"</ROOT>";	
		IEntinfoService iq = (IEntinfoService) factory.getBean("ientinfoService");
		long xy =System.currentTimeMillis();
		System.out.println(iq.testFunction());
		long xyq =System.currentTimeMillis();
		System.out.println(xyq-xy);
	}
}
