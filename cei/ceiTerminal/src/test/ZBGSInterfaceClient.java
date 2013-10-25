package test;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.log4j.Logger;
public class ZBGSInterfaceClient {
	private static Logger log = Logger.getLogger(ZBGSInterfaceClient.class);
	public static void main(String[] args) {
		ZBGSInterfaceClient zbgs = new ZBGSInterfaceClient();
		zbgs.LoginUserInfo();
	}
	public void testBase(String xmlstr,String method){
		try {
			//ָ��service����URL/// mob.cei.gov.cn:80
//			String url = "http://localhost:8080/ceiTerminal/services/IEntinfoService";mob.cei.gov.cn:80
			String url = "http://localhost:8080/ceiTerminal/services/UserInfoService";
			
			//ָ��service������saveShInfo/getAjBlInfo(String)/saveAjBlInfo
			//����һ������(service)����(call)
			Service service = new Service();
			//ͨ��service����call����
			Call call = (Call) service.createCall();
			//����service����URL
			call.setTargetEndpointAddress(new URL(url));
			//������
			call.setOperationName(method);
			ZBGSInterfaceClient z = new ZBGSInterfaceClient();
			
			String ret = (String) call.invoke(new Object[] { xmlstr });
			try {
				System.out.println(FormatXMLStr.format(ret));
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (ServiceException se) {
			se.printStackTrace();
		} catch (MalformedURLException me) {
			me.printStackTrace();
		} catch (RemoteException re) {
			re.printStackTrace();
		}
	}
	
	public void LoginUserInfo(){
		String method = "LoginUserInfo";
		String xmlStr="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<ROOT>" +
		"<LOGINNAME>heyx</LOGINNAME>" +
		"<PASSWORD>heyx</PASSWORD>" +
		"<imagetype>pad</imagetype>" +
		"<imsitype>ipad</imsitype>"+
		"<imsicode>90:18:7C:BB:16:3B355547050735423</imsicode>" +
		"</ROOT>";	
		testBase(xmlStr, method);
	}

	
	public void queryUserInfo(){
		String method = "queryUserInfo";
		String xmlStr="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<ROOT>" +
		"<userid>40288a783a494171013a495a1b580006</userid>" +
		"</ROOT>";	
		testBase(xmlStr, method);
	}
	
	public void queryBrightness(){
		String method = "queryBrightness";
		String xmlStr="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<ROOT>" +
		"<functionid>000111</functionid>" +
		"</ROOT>";	
		testBase(xmlStr, method);
	}
	
	public void queryPhoneFunctionTree(){
		String method = "queryPhoneFunctionTree";
		String xmlStr="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<ROOT>" +
		"<id>40288a7838dc1eea0138dffa9a0f00c9</id>" +
		"<type>bg</type>" +
		"<imagetype>androidpad</imagetype>" +
		"<index>1</index>" +
		"</ROOT>";	
		testBase(xmlStr, method);
	}

	public void queryClassIsTop(){
		String method = "queryClassIsTop";
		String xmlStr="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<ROOT>" +
		"<functionid>00011101</functionid>" +
		"</ROOT>";	
		testBase(xmlStr, method);
	}
	
	public void queryReportIsTop(){
		String method = "queryReportIsTop";
		String xmlStr="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<ROOT>" +
		"<functionid>00011103</functionid>" +
		"</ROOT>";	
		testBase(xmlStr, method);
	}
	
	public void saveUserClassTime(){
		String method = "saveUserClassTime";
		String xmlStr="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<ROOT>" +
		"<userid>40288a093b694b95013b694cf2300003</userid>" +
		"<classid>40288a783d91b468013da09afe5601a2</classid>" +
		"<time>33</time>" +
		"</ROOT>";	
		testBase(xmlStr, method);
	}
	
	public void queryUserClassTime(){
		String method = "queryUserClassTime";
		String xmlStr="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<ROOT>" +
		"<userid>40288a093b694b95013b694cf2300003</userid>" +
		"<imagetype>androidpad</imagetype>" +
		"</ROOT>";	
		testBase(xmlStr, method);
	}
	
	public void queryClassInfo(){
		String method = "queryClassInfo";
		String xmlStr="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<ROOT>" +
		"<classid>11</classid>" +
		"<classtype>ipad</classtype>" +
		"</ROOT>";	
		testBase(xmlStr, method);
	}
	
	public void queryNewInfo(){
		String method = "queryNewInfo";
		String xmlStr="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<ROOT>" +
		"<classid>11</classid>" +
		"<classtype>androidpad</classtype>" +
		"</ROOT>";	
		testBase(xmlStr, method);
	}
	
	public void saveCourse(){
		String method = "saveCourse";
		String xmlStr="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<ROOT>" +
		"<userid>000001</userid>" +
		"<buytype>yw</buytype>" +
		"<resourceid>0000000103</resourceid>" +
		"</ROOT>";	
		testBase(xmlStr, method);
	}

	
	public void delBusiness(){
		String method = "delBusiness";
		String xmlStr="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<ROOT>" +
		"<userid>40288a7837735c500137735fc88e0002</userid>" +
		"<buytype>kj</buytype>" +
		"<resourceid>40288a7837978d0b0137981d95a00041</resourceid>" +
		"</ROOT>";	
		testBase(xmlStr, method);
	}
	
	public void queryCourse(){
		String method = "queryCourse";
		String xmlStr="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<ROOT>" +
		"<userid>000001</userid>" +
		"<buytype>kj</buytype>" +
		"</ROOT>";	
		testBase(xmlStr, method);
	}
	public void queryBusiness(){
		String method = "queryBusiness";
		String xmlStr="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<ROOT>" +
		"<userid>40288a7838dbbe950138dbedb75f003c</userid>" +
		"<imagetype>pad</imagetype>" +
		"</ROOT>";	
		testBase(xmlStr, method);
	}
	
	public void saveBBSMotif(){
		String method = "saveBBSMotif";
		String xmlStr="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<ROOT>" +
		"<title>��̳����321</title>" +
		"<content>�о������԰���̳����321</content>" +
		"<userid>000001</userid>" +
		"<functionid>000111</functionid>" +
		"</ROOT>";	
		testBase(xmlStr, method);
	}
	
	public void queryBBSMotifList(){
		String method = "queryBBSMotifList";
		String xmlStr="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<ROOT>" +
		"<functionid>000111</functionid>" +
		"</ROOT>";	
		testBase(xmlStr, method);
	}
	
	public void updateBBSMotif(){
		String method = "updateBBSMotif";
		String xmlStr="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<ROOT>" +
		"<id>40288a1536aa77050136aa85f6060001</id>" +
		"<content>�о������԰���̳�����о������԰���̳����</content>" +
		"</ROOT>";	
		testBase(xmlStr, method);
	}
	
	public void saveBBSFollow(){
		String method = "saveBBSFollow";
		String xmlStr="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<ROOT>" +
		"<functionid>000111</functionid>" +
		"<userid>0000</userid>" +
		"<content>�о���aaaa���԰���̳aaa���԰�aa</content>" +
		"<serial>2</serial>" +
		"<classid>11</classid>" +
		"<time>2012-06-27 17:38:55</time>" +
		"</ROOT>";	
		testBase(xmlStr, method);
	}
	public void delBBSMotif(){
		String method = "delBBSMotif";
		String xmlStr="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<ROOT>" +
		"<id>40288a1536aa77050136aa85f6060001</id>" +
		"</ROOT>";	
		testBase(xmlStr, method);
	}
	
	public void quitUserInfo(){
		String method = "quitUserInfo";
		String xmlStr="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<ROOT>" +
		"<id>000001</id>" +
		"</ROOT>";	
		testBase(xmlStr, method);
	}
	
	public void saveOperationlog(){
		String method = "saveOperationlog";
		String xmlStr="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<ROOT>" +
		"<loginid>000001</loginid>" +
		"<functionid>000001</functionid>" +
		"<functionname>000001</functionname>" +
		"</ROOT>";	
		testBase(xmlStr, method);
	}
	
	public void queryBBSFollow(){
		String method = "queryBBSFollow";
		String xmlStr="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<ROOT>" +
		"<classid>11</classid>" +
		"<functionid>000111</functionid>" +
		"</ROOT>";	
		testBase(xmlStr, method);
	}
	
	public void saveUserInfo(){
		String method = "saveUserInfo";
		String xmlStr="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<ROOT>" +
		"<loginname>tanjieaassa</loginname>" +
		"<password>tanjieaaa</password>" +
		"<name>tanjieaaa</name>" +
		"<sex>0</sex>" +
		"<email>tanjie555s55@163.com</email>" +
		"</ROOT>";	
		testBase(xmlStr, method);
	}
	
	public void updateUserInfoPassWord(){
		String method = "updateUserInfoPassWord";
		String xmlStr="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<ROOT>" +
		"<email>915956198@qq.com</email>" +
		"</ROOT>";	
		testBase(xmlStr, method);
	}
	
	public void updatePassWord(){
		String method = "updatePassWord";
		String xmlStr="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<ROOT>" +
		"<userid>40288a7838dbbe950138dbedb75f003c</userid>" +
		"<oldpassword>123</oldpassword>" +
		"<email>377721976@qq.com</email>" +
		"<idtype>����֤</idtype>" +
		"<idnum>123456</idnum>" +
		"<phonenum>18888888888</phonenum>" +
		"</ROOT>";	
		testBase(xmlStr, method);
	}
	
	public void queryClassByName(){
		String method = "queryClassByName";
		String xmlStr="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<ROOT>" +
		"<functionids>40288a783792598101379273b7a80012,40288a783792598101379282ee8c0026,40288a783792598101379284738e0028,40288a783792598101379284de21002a,40288a7837978d0b013797ed4aa60016,40288a7837978d0b013797edaaa90018,40288a7837978d0b013797ee292f001a,40288a7837978d0b013797ef2e0c001c,40288a7837978d0b013797efa129001e,40288a7837978d0b01379889ed87004f,40288a7837978d0b013798b1b2500072,40288a7837978d0b013798b2090b0074,40288a7837978d0b013798b255850076</functionids>" +
		"<imagetype>androidpad</imagetype>" +
		"<classname>ͳ�ƾ�</classname>" +
		"</ROOT>";	
		testBase(xmlStr, method);
	}
	
	public void queryClassByType(){
		String method = "queryClassByType";
		String xmlStr="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<ROOT>" +
		"<functionids>40288a783792598101379273b7a80012,40288a783792598101379282ee8c0026,40288a783792598101379284738e0028,40288a783792598101379284de21002a,40288a7837978d0b013797ed4aa60016,40288a7837978d0b013797edaaa90018,40288a7837978d0b013797ee292f001a,40288a7837978d0b013797ef2e0c001c,40288a7837978d0b013797efa129001e,40288a7837978d0b01379889ed87004f,40288a7837978d0b013798b1b2500072,40288a7837978d0b013798b2090b0074,40288a7837978d0b013798b255850076</functionids>" +
		"</ROOT>";	
		testBase(xmlStr, method);
	}
	
	public void queryClassTypeByClass(){
		String method = "queryClassTypeByClass";
		String xmlStr="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<ROOT>" +
		"<id>4b4ff6f93b508a1c013b6f08c8c301b7</id>" +
		"<imagetype>androidpad</imagetype>" +
		"<index>1</index>" +
		"</ROOT>";	
		testBase(xmlStr, method);
	}
	
	public void queryReportByType(){
		String method = "queryReportByType";
		String xmlStr="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<ROOT>" +
		"<functionids>40288a7838f9fc2c0138ff9e3c5801a9</functionids>" +
		"</ROOT>";	
		testBase(xmlStr, method);
	}

	public void addClassTime(){
		String method = "addClassTime";
		String xmlStr="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<ROOT>" +
		"<userid>0000001</userid>" +
		"<classid>11</classid>" +
		"<classtimes>124,3445,4564</classtimes>" +
		"</ROOT>";	
		testBase(xmlStr, method);
	}

	public void queryClassTime(){
		String method = "queryClassTime";
		String xmlStr="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<ROOT>" +
		"<userid>0000001</userid>" +
		"<classid>11</classid>" +
		"</ROOT>";	
		testBase(xmlStr, method);
	}
	
	public void queryMoneyClass(){
		String method = "queryMoneyClass";
		String xmlStr="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<ROOT>" +
		"<userid>4b4ff6f93bb1faa4013bbb5cbc6901ca</userid>" +
		"</ROOT>";	
		testBase(xmlStr, method);
	}

	public void queryNewsList(){
		String method = "queryNewsList";
		String xmlStr="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<ROOT>" +
		"<functionids>40288a7838f9fc2c0138fac7098b0042</functionids>" +
		"<newstype>db</newstype>" +
		"</ROOT>";	
		testBase(xmlStr, method);
	}

	public void queryNewsByFunctionId(){
		String method = "queryNewsByFunctionId";
		String xmlStr="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<ROOT>" +
		"<functionid>40288a7838e02e0e0138e035104e0009</functionid>" +
		"<userid>4b4ff6f93bcba4ae013c1a1642ba02c2</userid>" +
		"<newstype>db</newstype>" +
		"</ROOT>";	
		testBase(xmlStr, method);
	}

	public void queryNewsImage(){
		String method = "queryNewsImage";
		String xmlStr="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<ROOT>" +
		"<functionids>40288a7838dc1eea0138dc637c6b007e</functionids>" +
		"<newstype>db</newstype>" +
		"</ROOT>";	
		testBase(xmlStr, method);
	}

	public void queryNewsByName(){
		String method = "queryNewsByName";
		String xmlStr="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<ROOT>" +
		"<titlename>1</titlename>" +
		"<functionids>40288a783851266f013855e545720010,40288a783851266f013855e596410012,40288a783851266f013855e7a6070016,40288a783851266f013855e7fee50018,40288a783851266f013855e84af2001a,40288a783851266f013855e9260f001c,40288a783851266f013855e9ee51001e,40288a783851266f013855ea903e0020,40288a783851266f013855eb057e0022,40288a783851266f013855eb513c0024,40288a78387030cb013874aec63d0057</functionids>" +
		"</ROOT>";	
		testBase(xmlStr, method);
	}


	public void saveCoolect(){
		String method = "saveCoolect";
		String xmlStr="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<ROOT>" +
		"<userid>000001</userid>" +
		"<resourceid>123123123</resourceid>" +
		"<functionid>000111</functionid>" +
		"</ROOT>";	
		testBase(xmlStr, method);
	}

	public void delCollect(){
		String method = "delCollect";
		String xmlStr="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<ROOT>" +
		"<userid>000001</userid>" +
		"<resourceid>206756649508420e97299718826f35a4</resourceid>" +
		"<functionid>000111</functionid>" +
		"</ROOT>";	
		testBase(xmlStr, method);
	}

	public void queryCollect(){
		String method = "queryCollect";
		String xmlStr="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<ROOT>" +
		"<userid>000001</userid>" +
		"</ROOT>";	
		testBase(xmlStr, method);
	}
	
	public void queryReportByName(){
		String method = "queryReportByName";
		String xmlStr="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<ROOT>" +
		"<functionids>4b4ff6f93b27647f013b27c92ba5007d,4b4ff6f93b27647f013b27c94a1d007f,4b4ff6f93b27647f013b27c968a40081</functionids>" +
		"<reportname></reportname>" +
		"<imagetype>androidpad</imagetype>" +
		"<isdownsum>y</isdownsum>" +
		"<num>1</num>" +
		"<isfree>1</isfree>" +
		"</ROOT>";	
		testBase(xmlStr, method);
	}
	public void queryClassAndReport(){
		String method = "queryClassAndReport";
		String xmlStr="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<ROOT>" +
		"<loginname>caojia</loginname>" +
		"<password>123</password>" +
		"<imsitype>pad</imsitype>" +
		"</ROOT>";	
		testBase(xmlStr, method);
	}
	

	public void queryClassByTime(){
		String method = "queryClassByTime";
		String xmlStr="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<ROOT>" +
		"<functionids>40288a7838e0062c0138e023c936003d,40288a7838f9fc2c013904ce30950214,40288a7838f9fc2c013904ce6acf0216,40288a7838f9fc2c01390583848102b2,40288a7838f9fc2c01390583dc0702b4,40288a7838f9fc2c013905841af502b6,40288a7838f9fc2c0139058460c802b8,40288a7838f9fc2c01390586ceab02cc,40288a7838f9fc2c0139058705e702ce,40288a7838f9fc2c0139058743cb02d0,40288a7838f9fc2c013905877b5602d2,40288a7838f9fc2c01390587c4e202d4,40288a7838f9fc2c01390587f92102d6,40288a7838f9fc2c013905884f6f02d8,40288a7838f9fc2c013905888a7402da</functionids>" +
		"<imagetype>androidpad</imagetype>" +
		"<funid>40288a7838d597d20138dbbb18140068</funid>" +
		"</ROOT>";	
		testBase(xmlStr, method);
	}

	public void addFunctionTree(){
		String method = "addFunctionTree";
		String xmlStr="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<ROOT>" +
		"<roleid>00000001</roleid>" +
		"</ROOT>";	
		testBase(xmlStr, method);
	}
	public void queryNotice(){
		String method = "queryNotice";
		String xmlStr="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<ROOT>" +
		"<userid>40288a783b98f681013b9c5157200006</userid>" +
		"</ROOT>";	
		testBase(xmlStr, method);
	}
	
	public void queryBuyNews(){
		String method = "queryBuyNews";
		String xmlStr="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<ROOT>" +
		"<userid>40288a7838dbbe950138dbedb75f003c</userid>" +
		"<resourcetype>db</resourcetype>" +
		"</ROOT>";	
		testBase(xmlStr, method);
	}
	public void queryPassKey(){
		String method = "queryPassKey";
		String xmlStr="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<ROOT>" +
		"<paths></paths>" +
		"</ROOT>";	
		testBase(xmlStr, method);
	}
	
	public void queryMoneyReport(){
		String method = "queryMoneyReport";
		String xmlStr="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<ROOT>" +
		"<userid>4b4ff6f93e63e521013e68f2eccd00f4</userid>" +
		"</ROOT>";	
		testBase(xmlStr, method);
	}
	
	public void queryAllClassTypeReport(){
		String method = "queryAllClassTypeReport";
		String xmlStr="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<ROOT>" +
		"<num>3</num>" +
		"<reporttypeid>40288a7838e0f2f40138e15111a1002f</reporttypeid>" +
		"</ROOT>";	
		testBase(xmlStr, method);
	}
	public void queryPassKeyByReport(){
		String method = "queryPassKeyByReport";
		String xmlStr="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<ROOT>" +
		"<paths>a8654e729c58400d9aaf27bde985e090,8bb527011d6a4172afc541877e1166a1</paths>" +
		"</ROOT>";	
		testBase(xmlStr, method);
	}
	
	public void queryReportName(){
		String method = "queryReportName";
		String xmlStr="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<ROOT>" +
		"<id>00000001</id>" +
		"</ROOT>";	
		testBase(xmlStr, method);
	}
	
	public void queryClassName(){
		String method = "queryClassName";
		String xmlStr="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<ROOT>" +
		"<id>00000001</id>" +
		"</ROOT>";	
		testBase(xmlStr, method);
	}
	
	public void queryApkList(){
		String method = "queryApkList";
		String xmlStr="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<ROOT>" +
		"</ROOT>";	
		testBase(xmlStr, method);
	}
	
	public void queryFunctionAddress(){
		String method = "queryFunctionAddress";
		String xmlStr="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<ROOT><id>40288a7838d597d20138dbbb18140068</id></ROOT>";	
		testBase(xmlStr, method);
	}
}
