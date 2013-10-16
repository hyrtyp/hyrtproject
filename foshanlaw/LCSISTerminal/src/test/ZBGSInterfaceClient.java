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
		//��ѯ�ֳ����ܿ�������
		zbgs.getCommonUserList();
		// ��ѯ��ҵ��Ϣ�б�
		// zbgs.queryEntInfos();
		// zbgs.queryEntInfos();
		// ������ҵ��γ��
		// zbgs.updateEntInfo();
		// Ѳ����������ѯ
		// zbgs.();
		// ���ɷ���
		// zbgs.queryCodeFlag();
		// Ѳ���¼�б�
		 //zbgs.queryPatrolLOG();
		// ���ɷ�����ϸ
		// zbgs.queryCodeFlagByid();
		// �û���Ϣ����
		// zbgs.saveUserFankui();
		// �г�������Ϣ����
		// zbgs.saveEntErrorinfo();
		// �г����������ϸ��Ϣ
		// zbgs.queryEntErrorInfo();
		// ��ȡ�û���Ϣ
		//zbgs.getUser();
		// zbgs.saveEntErrorinfo();
		// �г�������Ϣ����
		// zbgs.queryEntErrorList();
		// ���淢��
		// zbgs.saveNotice();
		// �����ѯ
		// zbgs.queryNotice();
		// ������ϸ
		// zbgs.queryNoticeInfo();
		// zbgs.saveEntErrorinfo();
		// ��ѯ���������ҵ�б�
		// zbgs.queryPatrolENTINFO();
		// ��֤���յǼ�
		// zbgs.saveNocard();
		// ��֤���ղ�ѯ
		// zbgs.queryNocardList();
		// zbgs.queryNocardList();
		// ��֤������ϸ��Ϣ
		// zbgs.queryNocardInfo();

		// zbgs.queryPatrolENTINFO();
		// ��ѯ�ز��¼�б�
		// zbgs.queryReturnLog();
		// ����Ѳ���¼
		 zbgs.savePatrolLOG();
		// ����Ѳ������
		// zbgs.savePatrolItem();
		// ����֤��
		// zbgs.savePatrolProof();
		// zbgs.savePatrolLOG();
		// ��ѯ�����µ�����
		// zbgs.queryPatrolTask();
		// ��ѯ����˾�б�
		// zbgs.queryCompanyByPatrolId();
		/*
		 * ���ں�������ӿ�
		 */
		// �û����б�
		// zbgs.queryUserGroup();
		// �����û�id�õ��û�����
		// zbgs.getUserListByuserId();

		// zbgs.getUserListByuserId();
		// ��ѯ�쵽�ڵ�����
		// zbgs.queryTimeTask();
		// ��ѯ���������
		// zbgs.queryNoticeCount();

		// zbgs.uploadProofs();
		// ��ѯ��Χ�ڵ���ҵ
		// zbgs.queryEntByLatLong();
		// ��ѯѲ���¼
		// zbgs.queryPatrolLoca();
		// zbgs.queryPatrolLoca();

		// �жϸ��û��ǲ����жӳ����ϼ���
		// zbgs.getUserLevel();
		// ��ҵ���䲹¼
		// zbgs.updateEntEmail();
		// ���ɷ��淢�ʼ�ʱ��ѯ��ҵ�б�
		// zbgs.queryEntListByemail();
		// ����Ⱥ��
		// zbgs.sendEmail();
		// zbgs.queryPType();
		// Ѳ�������б�
		// zbgs.queryMwpmBussPatrolItemList();
		// �ز��б�
		// zbgs.queryReturnLogList();
		// �ز�¼��
		// zbgs.saveReturnLog();
		// �����ϴ�֤��
		// zbgs.uploadProofs();
		// ����
		// zbgs.saveCase();
		// ��������ID�����û���Ϣ
		// zbgs.getUserListByrId();
		// �����ֵ��ѯ
		// zbgs.queryDictionaryList();
		// ר��Ѳ���б�
		// zbgs.queryZxPatrolTask();
		// zbgs.uploadProofs();

		// ͳ�Ʒ��������ú�̨���ӣ��˷��������ã�
		// zbgs.queryUncheckedPatrolReport();

		// zbgs.uploadProofs();
		// ��ѯ��Χ�ڵ���ҵ
		// zbgs.queryEntByLatLong();
		// ��ѯѲ���¼
		// zbgs.queryPatrolLoca();
		// zbgs.queryPatrolLoca();

		// �жϸ��û��ǲ����жӳ����ϼ���
		// zbgs.getUserLevel();
		// ��ҵ���䲹¼
		// zbgs.updateEntEmail();
		// ���ɷ��淢�ʼ�ʱ��ѯ��ҵ�б�
		// zbgs.queryEntListByemail();
		// ����Ⱥ��
		// zbgs.sendEmail();
		// zbgs.queryPType();
		// zbgs.savePType();
		// ���û�Ȩ��
		// zbgs.savePType();
	}

	/**
	 * @param xmlstr
	 * @param method
	 */
	public void testBase(String xmlstr, String method) {
		try {
			// ָ��service����URL//
			// String url =
			// "http://61.233.18.66:8080/LCSISTerminal/services/UserInfoService";
			String url = "http://localhost:8080/LCSISTerminal/services/UserInfoService";

			// ָ��service������saveShInfo/getAjBlInfo(String)/saveAjBlInfo
			// ����һ������(service)����(call)
			Service service = new Service();
			// ͨ��service����call����
			Call call = (Call) service.createCall();
			// ����service����URL
			call.setTargetEndpointAddress(new URL(url));
			// ������
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
				+ "<username>�󷢶�</username>" + "<longitude>���</longitude>"
				+ "<latitude>���</latitude>" + "</ROOT>";
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
				+ "<REMARK>û����</REMARK>" + "</ITEM></ROOT>");
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

	// ��ѯ��ҵ�б�
	public void queryEntInfos() {
		String method = "queryEntInfos";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<name>��ɽ��</name>" + "<pageno>" + 1 + "</pageno>" + "</ROOT>";
		testBase(xmlStr, method);
	}

	// ���ɷ����ѯ
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
	 * ������ҵ��γ��
	 */
	public void updateEntInfo() {
		String method = "updateEntInfo";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<id>40288a073c192096013c19249a4e0003</id>"
				+ "<lat>1111111</lat>" + "<long1>222222</long1>" + "</ROOT>";
		testBase(xmlStr, method);
	}

	/**
	 * Ѳ���������
	 */
	public void queryPatrolTask() {
		String method = "queryPatrolTask";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<reseauid>1</reseauid>" + "<pageno>1</pageno>" + "</ROOT>";
		testBase(xmlStr, method);
	}

	/**
	 * Ѳ���¼�б�
	 */
	public void queryPatrolLOG() {
		String method = "queryPatrolLOG";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<entid>40288a073c1934a0013c1966f5ac0031</entid>"
				+ "<pageno>1</pageno>" + "</ROOT>";
		testBase(xmlStr, method);
	}

	// ���ɷ����ѯ��ϸ
	public void queryCodeFlagByid() {
		String method = "queryCodeFlagByid";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<id>40288d063c18d025013c18d1d2a20003</id>" + "</ROOT>";
		testBase(xmlStr, method);
	}

	// �û���Ϣ����¼��
	public void saveUserFankui() {
		String method = "saveUserFankui";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<content>�󷢶�</content>" + "<userid>1</userid>"
				+ "<time>2013-01-09</time>" + "</ROOT>";
		testBase(xmlStr, method);
	}

	// �г�������Ϣ����¼��
	public void saveEntErrorinfo() {
		String method = "saveEntErrorinfo";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<name>�󷢶�</name>" + "<enrol>�󷢶�</enrol>"
				+ "<submitid>1</submitid>"
				+ "<updatecontent>�󷢶�</updatecontent>"
				+ "<createtime>2013-01-09</createtime>" + "</ROOT>";
		testBase(xmlStr, method);
	}

	/**
	 * ��ȡ�û���Ϣ
	 */
	public void getUser() {
		String method = "getUser";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<loginname>admin</loginname>" + "<password>admin</password>"
				+ "</ROOT>";
		testBase(xmlStr, method);
	}

	/**
	 * ��ѯ���������ҵ�б�
	 */
	public void queryPatrolENTINFO() {
		String method = "queryPatrolENTINFO";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<reseauid>1</reseauid>" + "<pageno>1</pageno>" + "</ROOT>";
		testBase(xmlStr, method);
	}

	/**
	 * ��ѯ�쵽�ڵ�����
	 */
	public void queryTimeTask() {
		String method = "queryTimeTask";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<reseauid>1</reseauid>" + "<pageno>1</pageno>" + "</ROOT>";
		testBase(xmlStr, method);
	}

	// �г�������Ϣ�����ѯ
	public void queryEntErrorinfo() {
		String method = "queryEntErrorinfo";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<submitid>1</submitid>" + "<pageno>1</pageno>" + "</ROOT>";
		testBase(xmlStr, method);
	}

	// �ж��Ƿ��й���֪ͨ����Ȩ��

	// ����֪ͨ����
	public void saveNotice() {
		String method = "saveNotice";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<title>����֪ͨ</title>" + "<content>����֪ͨ</content>"
				+ "<userid>1</userid>" + "<createtime>2013-01-09</createtime>"
				+ "<type>1</type>" + "</ROOT>";
		testBase(xmlStr, method);
	}

	// ����֪ͨ��ѯ
	public void queryNotice() {
		String method = "queryNotice";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<title></title>" + "<submittimes>2013-01-01</submittimes>"
				+ "<submittimee>2013-01-21</submittimee>"
				+ "<pageno>1</pageno>" + "</ROOT>";
		testBase(xmlStr, method);

	}

	// ����֪ͨ��ϸ��Ϣ
	public void queryNoticeInfo() {
		String method = "queryNoticeInfo";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<id>40288d063c1eeeeb013c1ef0531f0001</id>" + "</ROOT>";
		testBase(xmlStr, method);
	}

	// ��ѯ�ز��¼�б�
	public void queryReturnLog() {
		String method = "queryReturnLog";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<contentid>40288d063c1eeeeb013c1ef0531f0001</contentid>"
				+ "</ROOT>";
		testBase(xmlStr, method);
	}

	// ����Ѳ���¼
	public void savePatrolLOG() {
		String method = "savePatrolLOG";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<entid>40288a073c7f78a1013c7f9933640028</entid>"
				+ "<pid>40288a093d6cf935013d6d06e5b60005</pid>"
				+ "<content>���</content>" + "<userid>111</userid>"
				+ "<clock>2013-01-10 16:23:03</clock>" + "<type>���</type>"
				+ "<area>���</area>" + "<remark>�󷢶�</remark>"
				+ "<term>2013-01-10 16:23:03</term>"
				+ "<isrecheck>0</isrecheck>" + "<rid>0</rid>" + "</ROOT>";
		testBase(xmlStr, method);
	}

	// ��֤���յǼ�
	public void saveNocard() {
		String method = "saveNocard";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<title>hhhhh</title>" + "<operator>��Ӫ��</operator>"
				+ "<idcard>���֤��</idcard>" + "<phone>�绰</phone>"
				+ "<address>��ϸ��ַ</address>" + "<type>������֤����</type>"
				+ "<item>��Ӫ��Ŀ</item>" + "<result>������</result>"
				+ "<submitid>�ύ��</submitid>"
				+ "<submittime>2013-01-11</submittime>" + "</ROOT>";
		testBase(xmlStr, method);
	}

	// ��֤���ղ�ѯ
	public void queryNocardList() {
		String method = "queryNocardList";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<submittimes>2013-01-10</submittimes>"
				+ "<submittimee>2013-01-11</submittimee>"
				+ "<pageno>1</pageno>" + "</ROOT>";
		testBase(xmlStr, method);
	}

	// ��ѯ������ҵ�б�
	public void queryCompanyByPatrolId() {
		String method = "queryCompanyByPatrolId";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<id>40288a063c1ef10b013c1f03982c000c</id>"
				+ "<pageno>1</pageno>" + "</ROOT>";
		testBase(xmlStr, method);
	}

	// ����Ѳ������
	public void savePatrolItem() {
		String method = "savePatrolItem";
		String xmlStr = "<?xml version='1.0' encoding='utf-8'?> <ROOT>"
				+ "<items>" + "<item>"
				+ "<logid>40288a273c3c9bf2013c3c9c94b30001</logid>"
				+ "<contentid>��ҵ˽Ӫ������</contentid>"
				+ "<qid>�����ڴ������վ�Ӫ</qid><disposeid>��ʽ���</disposeid>" + "</item>"
				+ "<item>" + "<logid>40288a273c3c9bf2013c3c9c94b30001</logid>"
				+ "<contentid>�г��ල����</contentid><qid>δ���涨�������Ǽ�2</qid>"
				+ "<disposeid>��ʽ���</disposeid>" + "</item>" + "</items>"
				+ "</ROOT>";
		testBase(xmlStr, method);
	}

	// ����֤��
	public void savePatrolProof() {
		String method = "savePatrolProof";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<lid>1111</lid>" + "<path>path</path>"
				+ "<userid>userid</userid>"
				+ "<clock>2013-01-10 16:23:03</clock>"
				+ "<remark>remark</remark>" + "</ROOT>";
		testBase(xmlStr, method);
	}

	// ��֤���ղ�ѯ
	public void queryNocardInfo() {
		String method = "queryNocardInfo";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<id>40288c183c22ba71013c22bc16d00001</id>" + "</ROOT>";
		testBase(xmlStr, method);
	}

	// �г����������ϸ ��Ϣ
	public void queryEntErrorInfo() {
		String method = "queryEntErrorInfo";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<id>40288d063c1eb417013c1eb619b80001</id>" + "</ROOT>";
		testBase(xmlStr, method);
	}

	// �û����б�
	public void queryUserGroup() {
		String method = "queryUserGroup";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<id></id>" + "</ROOT>";
		testBase(xmlStr, method);
	}

	// �����û���id��ѯ�û��б�
	public void getUserListByGroupId() {
		String method = "getUserListByGroupId";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<id>40288d063c1eb417013c1eb619b80001</id>" + "</ROOT>";
		testBase(xmlStr, method);
	}

	// �����û�id�õ��û�����
	public void getUserListByuserId() {
		String method = "getUserListByuserId";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<uid>40288a073c3c431a013c3cbe8b8e0004</uid>" + "</ROOT>";
		testBase(xmlStr, method);
	}

	// ��ȡ���������
	public void queryNoticeCount() {
		String method = "queryNoticeNum";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "</ROOT>";
		testBase(xmlStr, method);
	}

	// Ѳ�������б��ѯ
	public void queryMwpmBussPatrolItemList() {
		String method = "queryMwpmBussPatrolItemList";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<logid>40288a273c3cd4cd013c3d39c2cb0014</logid>"
				+ "<pageno>1</pageno>" + "</ROOT>";
		testBase(xmlStr, method);
	}

	// �ز��б��ѯ
	public void queryReturnLogList() {
		String method = "queryReturnLogList";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<contentid>��ҵ˽Ӫ������1111</contentid>" + "<pageno>1</pageno>"
				+ "</ROOT>";
		testBase(xmlStr, method);
	}

	// �ز�¼��
	public void saveReturnLog() {
		String method = "saveReturnLog";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<contentid>1111</contentid>"
				+ "<checkthing>1111</checkthing>"
				+ "<disposetype>1</disposetype>"
				+ "<checktime>2013-01-15</checktime>" + "</ROOT>";
		testBase(xmlStr, method);
	}

	// δѲ������ͳ���б�
	public void queryUncheckedPatrolReport() {
		String method = "queryUncheckedPatrolReport";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<flag>1</flag>" + "<startTime>2013-01-16</startTime>"
				+ "<endTime>2013-01-17</endTime>" + "</ROOT>";
		testBase(xmlStr, method);
	}

	// ��ѯ·��
	public void queryPatrolLoca() {
		String method = "queryPatrolLoca";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<createtimes>2013-01-15</createtimes>"
				+ "<createtimee>2013-01-18</createtimee>" + "</ROOT>";
		testBase(xmlStr, method);

	}

	/**
	 * ��ȡ�û�����
	 */
	public void getUserLevel() {
		String method = "getUserLevel";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<loginname>jh</loginname>" + "</ROOT>";
		testBase(xmlStr, method);
	}

	// ���䷢����ҵ �б�
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
	 * ������ҵ����
	 */
	public void updateEntEmail() {
		String method = "updateEntEmail";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<id>40288a073c192096013c19249a4e0003</id>"
				+ "<email>jiacunhe@foxmail.com</email>" + "</ROOT>";
		testBase(xmlStr, method);
	}

	/**
	 * ���䷢��
	 */
	public void sendEmail() {
		String method = "sendEmail";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
				+ "<ROOT>"
				+ "<email>377721976@qq.com</email>"
				+ "<content>���ģ����sdfsadfasdfsdavfasdfasdvfasdfadsvfadsfafadfwerfwqrwreqe����</content>"
				+ "</ROOT>";
		// String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
		// + "<ROOT>"
		// + "<email>jiacunhe@163.com;jiacunhe@foxmail.com</email>"
		// +
		// "<content>���ģ����sdfsadfasdfsdavfasdfasdvfasdfadsvfadsfafadfwerfwqrwreqe����</content>"
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
