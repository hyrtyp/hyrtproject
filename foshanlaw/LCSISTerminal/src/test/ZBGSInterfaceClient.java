package test;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.log4j.Logger;

import com.hyrt.mwpm.util.FormFile;
import com.hyrt.mwpm.util.PostFile;

public class ZBGSInterfaceClient {
	private static Logger log = Logger.getLogger(ZBGSInterfaceClient.class);

	public static void main(String[] args) {
		final ZBGSInterfaceClient zbgs = new ZBGSInterfaceClient();
		//查询局长所能看到的人
		zbgs.getCommonUserList();
		// 查询企业信息列表
		// zbgs.queryEntInfos();
		// zbgs.queryEntInfos();
		// 更新企业经纬度
		// zbgs.updateEntInfo();
		// 巡查任务类表查询
		// zbgs.();
		// 法律法规
		// zbgs.queryCodeFlag();
		// 巡查记录列表
		 //zbgs.queryPatrolLOG();
		// 法律法规详细
		// zbgs.queryCodeFlagByid();
		// 用户信息反馈
		// zbgs.saveUserFankui();
		// 市场主体信息纠错
		// zbgs.saveEntErrorinfo();
		// 市场主体纠错详细信息
		// zbgs.queryEntErrorInfo();
		// 获取用户信息
		//zbgs.getUser();
		// zbgs.saveEntErrorinfo();
		// 市场主体信息纠错
		// zbgs.queryEntErrorList();
		// 公告发布
		// zbgs.saveNotice();
		// 公告查询
		// zbgs.queryNotice();
		// 公告详细
		// zbgs.queryNoticeInfo();
		// zbgs.saveEntErrorinfo();
		// 查询以认领的企业列表
		// zbgs.queryPatrolENTINFO();
		// 无证无照登记
		// zbgs.saveNocard();
		// 无证无照查询
		// zbgs.queryNocardList();
		// zbgs.queryNocardList();
		// 无证无照详细信息
		// zbgs.queryNocardInfo();

		// zbgs.queryPatrolENTINFO();
		// 查询回查记录列表
		// zbgs.queryReturnLog();
		// 插入巡查记录
		 zbgs.savePatrolLOG();
		// 插入巡查事项
		// zbgs.savePatrolItem();
		// 插入证据
		// zbgs.savePatrolProof();
		// zbgs.savePatrolLOG();
		// 查询网格下的任务
		// zbgs.queryPatrolTask();
		// 查询任务公司列表
		// zbgs.queryCompanyByPatrolId();
		/*
		 * 网内呼叫所需接口
		 */
		// 用户组列表
		// zbgs.queryUserGroup();
		// 根据用户id得到用户属性
		// zbgs.getUserListByuserId();

		// zbgs.getUserListByuserId();
		// 查询快到期的任务
		// zbgs.queryTimeTask();
		// 查询公告的数量
		// zbgs.queryNoticeCount();

		// zbgs.uploadProofs();
		// 查询范围内的企业
		// zbgs.queryEntByLatLong();
		// 查询巡查记录
		// zbgs.queryPatrolLoca();
		// zbgs.queryPatrolLoca();

		// 判断该用户是不是中队长以上级别
		// zbgs.getUserLevel();
		// 企业邮箱补录
		// zbgs.updateEntEmail();
		// 法律法规发邮件时查询企业列表
		// zbgs.queryEntListByemail();
		// 邮箱群发
		// zbgs.sendEmail();
		// zbgs.queryPType();
		// 巡查事项列表
		// zbgs.queryMwpmBussPatrolItemList();
		// 回查列表
		// zbgs.queryReturnLogList();
		// 回查录入
		// zbgs.saveReturnLog();
		// 测试上传证据
		// zbgs.uploadProofs();
		// 案件
		// zbgs.saveCase();
		// 根据网格ID查找用户信息
		// zbgs.getUserListByrId();
		// 数据字典查询
		// zbgs.queryDictionaryList();
		// 专项巡查列表
		// zbgs.queryZxPatrolTask();
		// zbgs.uploadProofs();

		// 统计分析（调用后台连接，此方法暂无用）
		// zbgs.queryUncheckedPatrolReport();

		// zbgs.uploadProofs();
		// 查询范围内的企业
		// zbgs.queryEntByLatLong();
		// 查询巡查记录
		// zbgs.queryPatrolLoca();
		// zbgs.queryPatrolLoca();

		// 判断该用户是不是中队长以上级别
		// zbgs.getUserLevel();
		// 企业邮箱补录
		// zbgs.updateEntEmail();
		// 法律法规发邮件时查询企业列表
		// zbgs.queryEntListByemail();
		// 邮箱群发
		// zbgs.sendEmail();
		// zbgs.queryPType();
		// zbgs.savePType();
		// 查用户权限
		// zbgs.savePType();
	}

	/**
	 * @param xmlstr
	 * @param method
	 */
	public void testBase(String xmlstr, String method) {
		try {
			// 指出service所在URL//
			// String url =
			// "http://61.233.18.66:8080/LCSISTerminal/services/UserInfoService";
			String url = "http://localhost:8080/LCSISTerminal/services/UserInfoService";

			// 指出service方法名saveShInfo/getAjBlInfo(String)/saveAjBlInfo
			// 创建一个服务(service)调用(call)
			Service service = new Service();
			// 通过service创建call对象
			Call call = (Call) service.createCall();
			// 设置service所在URL
			call.setTargetEndpointAddress(new URL(url));
			// 方法名
			call.setOperationName(method);
			ZBGSInterfaceClient z = new ZBGSInterfaceClient();

			String ret = (String) call.invoke(new Object[] { xmlstr });
			try {
				System.out.println(ret);
				// System.out.println(FormatXMLStr.format(ret));
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

	public void saveUser() {
		String method = "saveUser";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<username>大发饿</username>" + "<longitude>大大</longitude>"
				+ "<latitude>嗖嗖嗖</latitude>" + "</ROOT>";
		testBase(xmlStr, method);
	}

	private void getUserListByrId() {
		String method = "getUserListByrId";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<reseauid>1</reseauid>" + "<pageno>1</pageno>" + "</ROOT>";
		testBase(xmlStr, method);
	}

	public void queryEntByLatLong() {
		String method = "queryEntByLatLong";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<lats>116.2816</lats>" + "<longs>39.9547</longs>"
				+ "<late>116.2818</late>" + "<longe>39.9549</longe>"
				+ "</ROOT>";
		testBase(xmlStr, method);
	}

	private void uploadProofs() {
		Map map = new HashMap<String, String>();
		map.put("xmlStr", "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<ROOT><ITEM>" + "<LID>1</LID>" + "<PATH>2</PATH>"
				+ "<USERID>3</USERID>" + "<CLOCK>4</CLOCK>"
				+ "<REMARK>没问题</REMARK>" + "</ITEM></ROOT>");
		try {
			String rs = PostFile
					.post("http://localhost:8080/LCSISTerminal/photo/uploadPicture.page",
							map, new FormFile[] { new FormFile("image",
									"D:/aaa.jpg") });
			System.out.println(rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void queryTaskByUserid() {
		String method = "queryTaskByUserid";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<userid>1</userid>" + "</ROOT>";
		testBase(xmlStr, method);
	}

	public void updateUser() {
		String method = "updateUser";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<userid>111</userid>"
				+ "<longitude>116.29462432861328</longitude>"
				+ "<latitude>39.948856353759766</latitude>" + "</ROOT>";
		testBase(xmlStr, method);
	}

	public void updateTaskByIsDreceive() {
		String method = "updateTaskByIsDreceive";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<taskid>1</taskid>" + "</ROOT>";
		testBase(xmlStr, method);
	}

	public void savePatrol() {
		String method = "savePatrol";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<taskid>1</taskid>" + "</ROOT>";
		testBase(xmlStr, method);
	}

	public void saveCase() {
		String method = "saveCase";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<ROOT>"
				+ "<eid>111111</eid>" + "<name>111111</name>"
				+ "<submituserid>111111</submituserid>"
				+ "<submitunitid>111111</submitunitid>"
				+ "<createtime>2013-01-09</createtime>"
				+ "<jointuserid>11111</jointuserid>" + "<idea>11111</idea>"
				+ "<status>11111</status>" + "<main>11111</main>"
				+ "<property>11111</property>"
				+ "<casesource>11111</casesource>"
				+ "<baseinfo>11111</baseinfo>" + "</ROOT>";
		testBase(xmlStr, method);
	}

	public void queryPatrol() {
		String method = "queryPatrol";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "</ROOT>";
		testBase(xmlStr, method);
	}

	// 查询企业列表
	public void queryEntInfos() {
		String method = "queryEntInfos";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<name>佛山市</name>" + "<pageno>" + 1 + "</pageno>" + "</ROOT>";
		testBase(xmlStr, method);
	}

	// 法律法规查询
	public void queryCodeFlag() {
		String method = "queryCodeFlag";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<flag></flag>" + "<dm></dm>" + "<bz></bz>"
				+ "<pageno>2</pageno>" + "</ROOT>";
		// String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		// "<ROOT>"
		// + "<flag></flag>" + "<dm></dm>" + "<bz></bz>"
		// + "<pageno>1</pageno>" + "</ROOT>";
		testBase(xmlStr, method);
	}

	/**
	 * 更新企业经纬度
	 */
	public void updateEntInfo() {
		String method = "updateEntInfo";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<id>40288a073c192096013c19249a4e0003</id>"
				+ "<lat>1111111</lat>" + "<long1>222222</long1>" + "</ROOT>";
		testBase(xmlStr, method);
	}

	/**
	 * 巡查任务类表
	 */
	public void queryPatrolTask() {
		String method = "queryPatrolTask";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<reseauid>1</reseauid>" + "<pageno>1</pageno>" + "</ROOT>";
		testBase(xmlStr, method);
	}

	/**
	 * 巡查记录列表
	 */
	public void queryPatrolLOG() {
		String method = "queryPatrolLOG";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<entid>40288a073c1934a0013c1966f5ac0031</entid>"
				+ "<pageno>1</pageno>" + "</ROOT>";
		testBase(xmlStr, method);
	}

	// 法律法规查询详细
	public void queryCodeFlagByid() {
		String method = "queryCodeFlagByid";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<id>40288d063c18d025013c18d1d2a20003</id>" + "</ROOT>";
		testBase(xmlStr, method);
	}

	// 用户信息反馈录入
	public void saveUserFankui() {
		String method = "saveUserFankui";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<content>大发饿</content>" + "<userid>1</userid>"
				+ "<time>2013-01-09</time>" + "</ROOT>";
		testBase(xmlStr, method);
	}

	// 市场主体信息纠错录入
	public void saveEntErrorinfo() {
		String method = "saveEntErrorinfo";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<name>大发饿</name>" + "<enrol>大发饿</enrol>"
				+ "<submitid>1</submitid>"
				+ "<updatecontent>大发饿</updatecontent>"
				+ "<createtime>2013-01-09</createtime>" + "</ROOT>";
		testBase(xmlStr, method);
	}

	/**
	 * 获取用户信息
	 */
	public void getUser() {
		String method = "getUser";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<loginname>admin</loginname>" + "<password>admin</password>"
				+ "</ROOT>";
		testBase(xmlStr, method);
	}

	/**
	 * 查询以认领的企业列表
	 */
	public void queryPatrolENTINFO() {
		String method = "queryPatrolENTINFO";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<reseauid>1</reseauid>" + "<pageno>1</pageno>" + "</ROOT>";
		testBase(xmlStr, method);
	}

	/**
	 * 查询快到期的任务
	 */
	public void queryTimeTask() {
		String method = "queryTimeTask";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<reseauid>1</reseauid>" + "<pageno>1</pageno>" + "</ROOT>";
		testBase(xmlStr, method);
	}

	// 市场主体信息纠错查询
	public void queryEntErrorinfo() {
		String method = "queryEntErrorinfo";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<submitid>1</submitid>" + "<pageno>1</pageno>" + "</ROOT>";
		testBase(xmlStr, method);
	}

	// 判断是否有公告通知发布权限

	// 公告通知发布
	public void saveNotice() {
		String method = "saveNotice";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<title>公告通知</title>" + "<content>公告通知</content>"
				+ "<userid>1</userid>" + "<createtime>2013-01-09</createtime>"
				+ "<type>1</type>" + "</ROOT>";
		testBase(xmlStr, method);
	}

	// 公告通知查询
	public void queryNotice() {
		String method = "queryNotice";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<title></title>" + "<submittimes>2013-01-01</submittimes>"
				+ "<submittimee>2013-01-21</submittimee>"
				+ "<pageno>1</pageno>" + "</ROOT>";
		testBase(xmlStr, method);

	}

	// 公告通知详细信息
	public void queryNoticeInfo() {
		String method = "queryNoticeInfo";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<id>40288d063c1eeeeb013c1ef0531f0001</id>" + "</ROOT>";
		testBase(xmlStr, method);
	}

	// 查询回查记录列表
	public void queryReturnLog() {
		String method = "queryReturnLog";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<contentid>40288d063c1eeeeb013c1ef0531f0001</contentid>"
				+ "</ROOT>";
		testBase(xmlStr, method);
	}

	// 插入巡查记录
	public void savePatrolLOG() {
		String method = "savePatrolLOG";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<entid>40288a073c7f78a1013c7f9933640028</entid>"
				+ "<pid>40288a093d6cf935013d6d06e5b60005</pid>"
				+ "<content>大大</content>" + "<userid>111</userid>"
				+ "<clock>2013-01-10 16:23:03</clock>" + "<type>大大</type>"
				+ "<area>嗖嗖嗖</area>" + "<remark>大发饿</remark>"
				+ "<term>2013-01-10 16:23:03</term>"
				+ "<isrecheck>0</isrecheck>" + "<rid>0</rid>" + "</ROOT>";
		testBase(xmlStr, method);
	}

	// 无证无照登记
	public void saveNocard() {
		String method = "saveNocard";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<title>hhhhh</title>" + "<operator>经营者</operator>"
				+ "<idcard>身份证号</idcard>" + "<phone>电话</phone>"
				+ "<address>详细地址</address>" + "<type>无照无证类型</type>"
				+ "<item>经营项目</item>" + "<result>处理结果</result>"
				+ "<submitid>提交人</submitid>"
				+ "<submittime>2013-01-11</submittime>" + "</ROOT>";
		testBase(xmlStr, method);
	}

	// 无证无照查询
	public void queryNocardList() {
		String method = "queryNocardList";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<submittimes>2013-01-10</submittimes>"
				+ "<submittimee>2013-01-11</submittimee>"
				+ "<pageno>1</pageno>" + "</ROOT>";
		testBase(xmlStr, method);
	}

	// 查询任务企业列表
	public void queryCompanyByPatrolId() {
		String method = "queryCompanyByPatrolId";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<id>40288a063c1ef10b013c1f03982c000c</id>"
				+ "<pageno>1</pageno>" + "</ROOT>";
		testBase(xmlStr, method);
	}

	// 插入巡查事项
	public void savePatrolItem() {
		String method = "savePatrolItem";
		String xmlStr = "<?xml version='1.0' encoding='utf-8'?> <ROOT>"
				+ "<items>" + "<item>"
				+ "<logid>40288a273c3c9bf2013c3c9c94b30001</logid>"
				+ "<contentid>企业私营个体监管</contentid>"
				+ "<qid>场所内存在无照经营</qid><disposeid>书式告诫</disposeid>" + "</item>"
				+ "<item>" + "<logid>40288a273c3c9bf2013c3c9c94b30001</logid>"
				+ "<contentid>市场监督管理</contentid><qid>未按规定办理变更登记2</qid>"
				+ "<disposeid>书式告诫</disposeid>" + "</item>" + "</items>"
				+ "</ROOT>";
		testBase(xmlStr, method);
	}

	// 插入证据
	public void savePatrolProof() {
		String method = "savePatrolProof";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<lid>1111</lid>" + "<path>path</path>"
				+ "<userid>userid</userid>"
				+ "<clock>2013-01-10 16:23:03</clock>"
				+ "<remark>remark</remark>" + "</ROOT>";
		testBase(xmlStr, method);
	}

	// 无证无照查询
	public void queryNocardInfo() {
		String method = "queryNocardInfo";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<id>40288c183c22ba71013c22bc16d00001</id>" + "</ROOT>";
		testBase(xmlStr, method);
	}

	// 市场主体纠错详细 信息
	public void queryEntErrorInfo() {
		String method = "queryEntErrorInfo";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<id>40288d063c1eb417013c1eb619b80001</id>" + "</ROOT>";
		testBase(xmlStr, method);
	}

	// 用户组列表
	public void queryUserGroup() {
		String method = "queryUserGroup";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<id></id>" + "</ROOT>";
		testBase(xmlStr, method);
	}

	// 根据用户组id查询用户列表
	public void getUserListByGroupId() {
		String method = "getUserListByGroupId";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<id>40288d063c1eb417013c1eb619b80001</id>" + "</ROOT>";
		testBase(xmlStr, method);
	}

	// 根据用户id得到用户属性
	public void getUserListByuserId() {
		String method = "getUserListByuserId";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<uid>40288a073c3c431a013c3cbe8b8e0004</uid>" + "</ROOT>";
		testBase(xmlStr, method);
	}

	// 获取公告的数量
	public void queryNoticeCount() {
		String method = "queryNoticeNum";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "</ROOT>";
		testBase(xmlStr, method);
	}

	// 巡查事项列表查询
	public void queryMwpmBussPatrolItemList() {
		String method = "queryMwpmBussPatrolItemList";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<logid>40288a273c3cd4cd013c3d39c2cb0014</logid>"
				+ "<pageno>1</pageno>" + "</ROOT>";
		testBase(xmlStr, method);
	}

	// 回查列表查询
	public void queryReturnLogList() {
		String method = "queryReturnLogList";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<contentid>企业私营个体监管1111</contentid>" + "<pageno>1</pageno>"
				+ "</ROOT>";
		testBase(xmlStr, method);
	}

	// 回查录入
	public void saveReturnLog() {
		String method = "saveReturnLog";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<contentid>1111</contentid>"
				+ "<checkthing>1111</checkthing>"
				+ "<disposetype>1</disposetype>"
				+ "<checktime>2013-01-15</checktime>" + "</ROOT>";
		testBase(xmlStr, method);
	}

	// 未巡查任务统计列表
	public void queryUncheckedPatrolReport() {
		String method = "queryUncheckedPatrolReport";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<flag>1</flag>" + "<startTime>2013-01-16</startTime>"
				+ "<endTime>2013-01-17</endTime>" + "</ROOT>";
		testBase(xmlStr, method);
	}

	// 查询路线
	public void queryPatrolLoca() {
		String method = "queryPatrolLoca";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<createtimes>2013-01-15</createtimes>"
				+ "<createtimee>2013-01-18</createtimee>" + "</ROOT>";
		testBase(xmlStr, method);

	}

	/**
	 * 获取用户级别
	 */
	public void getUserLevel() {
		String method = "getUserLevel";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<loginname>jh</loginname>" + "</ROOT>";
		testBase(xmlStr, method);
	}

	// 邮箱发送企业 列表
	public void queryEntListByemail() {
		String method = "queryEntListByemail";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<reseauid></reseauid>" + "<pageno>1</pageno>" + "</ROOT>";
		// String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		// "<ROOT>"
		// + "<reseauid>1</reseauid>" + "<pageno>" + 1 + "</pageno>"
		// + "</ROOT>";
		testBase(xmlStr, method);
	}

	/**
	 * 更新企业邮箱
	 */
	public void updateEntEmail() {
		String method = "updateEntEmail";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<id>40288a073c192096013c19249a4e0003</id>"
				+ "<email>jiacunhe@foxmail.com</email>" + "</ROOT>";
		testBase(xmlStr, method);
	}

	/**
	 * 邮箱发送
	 */
	public void sendEmail() {
		String method = "sendEmail";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
				+ "<ROOT>"
				+ "<email>377721976@qq.com</email>"
				+ "<content>大规模法律sdfsadfasdfsdavfasdfasdvfasdfadsvfadsfafadfwerfwqrwreqe法律</content>"
				+ "</ROOT>";
		// String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
		// + "<ROOT>"
		// + "<email>jiacunhe@163.com;jiacunhe@foxmail.com</email>"
		// +
		// "<content>大规模法律sdfsadfasdfsdavfasdfasdvfasdfadsvfadsfafadfwerfwqrwreqe法律</content>"
		// + "</ROOT>";
		testBase(xmlStr, method);
	}

	public void queryDictionaryList() {
		String method = "queryDictionaryList";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<groupcode>ajxz</groupcode>" + "<pageno>1</pageno>"
				+ "</ROOT>";
		testBase(xmlStr, method);
	}

	public void queryZxPatrolTask() {
		String method = "queryZxPatrolTask";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<reseauid>1</reseauid>" + "<pageno>1</pageno>" + "</ROOT>";
		testBase(xmlStr, method);
	}

	public void queryPType() {
		String method = "queryPType";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<id></id>" + "</ROOT>";
		testBase(xmlStr, method);
	}

	public void savePType() {
		String method = "savePType";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<lid>123</lid>" + "<types>1,2</types>" + "</ROOT>";
		testBase(xmlStr, method);
	}

	public void getRoleTypeByUserId() {
		String method = "getRoleTypeByUserId";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<userid>0000</userid>" + "</ROOT>";
		testBase(xmlStr, method);
	}
	
	public void getCommonUserList() {
		String method = "getCommonUserList";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<pageno>1</pageno>" + "</ROOT>";
		testBase(xmlStr, method);
	}
}
