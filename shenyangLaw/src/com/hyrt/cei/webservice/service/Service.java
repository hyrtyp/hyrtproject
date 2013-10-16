package com.hyrt.cei.webservice.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.Buffer;
import java.nio.CharBuffer;
import java.util.Date;
import java.util.List;
import com.hyrt.cei.util.MyTools;
import com.hyrt.cei.util.StringUtil;
import com.hyrt.cei.util.WriteOrRead;
import com.hyrt.cei.webservice.wsdl.AgentServiceSoapBindingImpl;
import com.hyrt.cei.webservice.wsdl.Configuration;
import com.hyrt.mwpm.vo.MwpmBussCase;
import com.hyrt.mwpm.vo.MwpmBussEntupdate;
import com.hyrt.mwpm.vo.MwpmBussNocard;
import com.hyrt.mwpm.vo.MwpmBussPatrol;
import com.hyrt.mwpm.vo.MwpmBussPatrolItem;
import com.hyrt.mwpm.vo.MwpmBussPatrolLog;
import com.hyrt.mwpm.vo.MwpmBussEntinfo;
import com.hyrt.mwpm.vo.MwpmBussReturnlog;
import com.hyrt.mwpm.vo.MwpmSysMes;
import com.hyrt.mwpm.vo.MwpmSysUserinfo;

/**
 * 调用webservice
 * 
 */

public class Service {
	private static AgentServiceSoapBindingImpl agent;

	static {
		Configuration.setConfiguration(MyTools.url);
		agent = new AgentServiceSoapBindingImpl();
	}

	/**
	 * 更新用户经纬度
	 * 
	 * @param lon
	 * @param lat
	 * @return
	 */
	public static String updateUser(String lon, String lat, String userid) {
		String rs = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<ROOT>"
				+ "<userid>" + userid + "</userid><longitude>" + lon
				+ "</longitude><latitude>" + lat + "</latitude>" + "</ROOT>";
		try {
			rs = agent.updateUser(rs);
		} catch (Exception e) {
			rs = "<?xml version='1.0' encoding='UTF-8'?>" + "<ROOT>"
					+ "<RETURNCODE>-1</RETURNCODE>" + "</ROOT>";
		}
		return rs;
	}

	public static String queryTaskByUserid() {
		String rs = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<ROOT>"
				+ "<userid>1</userid>" + "</ROOT>";
		try {
			rs = agent.queryTaskByUserid(rs);
		} catch (Exception e) {
			rs = "<?xml version='1.0' encoding='UTF-8'?>" + "<ROOT>"
					+ "<RETURNCODE>-1</RETURNCODE>" + "</ROOT>";
		}
		return rs;
	}

	public static String updateCaseByIsDreceive(String id) {
		String rs = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<ROOT>"
				+ "<taskid>" + id + "</taskid>" + "</ROOT>";
		try {
			rs = agent.updateCaseByIsDreceive(rs);
		} catch (Exception e) {
			rs = "<?xml version='1.0' encoding='UTF-8'?>" + "<ROOT>"
					+ "<RETURNCODE>-1</RETURNCODE>" + "</ROOT>";
		}
		return rs;
	}

	public static String queryPatrol(String userid) {
		String rs = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<ROOT>"
				+ "<userid>" + userid + "</userid>" + "</ROOT>";
		try {
			rs = agent.queryPatrol(rs);
		} catch (Exception e) {
			rs = "<?xml version='1.0' encoding='UTF-8'?>" + "<ROOT>"
					+ "<RETURNCODE>-1</RETURNCODE>" + "</ROOT>";
		}
		return rs;
	}

	/**
	 * 分页查看企业列表
	 */
	public static String queryEntInfos(MwpmBussEntinfo mwpmBussEntinfo,
			int pageNo) {
		String rs = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<ROOT>";
		if (mwpmBussEntinfo != null && mwpmBussEntinfo.getEnrol() != null)
			rs += "<enrol>" + mwpmBussEntinfo.getEnrol() + "</enrol>";
		if (mwpmBussEntinfo != null && mwpmBussEntinfo.getName() != null)
			rs += "<name>" + mwpmBussEntinfo.getName() + "</name>";
		if (mwpmBussEntinfo != null && mwpmBussEntinfo.getMember() != null)
			rs += "<member>" + mwpmBussEntinfo.getMember() + "</member>";
		if (mwpmBussEntinfo != null && mwpmBussEntinfo.getAddress() != null)
			rs += "<address>" + mwpmBussEntinfo.getAddress() + "</address>";
		if (mwpmBussEntinfo != null && mwpmBussEntinfo.getEarcap() != null)
			rs += "<earcap>" + mwpmBussEntinfo.getEarcap() + "</earcap>";
		if (mwpmBussEntinfo != null && mwpmBussEntinfo.getWorkarea() != null)
			rs += "<workarea>" + mwpmBussEntinfo.getWorkarea() + "</workarea>";
		if (mwpmBussEntinfo != null && mwpmBussEntinfo.getReseauid() != null)
			rs += "<reseauid>" + mwpmBussEntinfo.getReseauid() + "</reseauid>";
		if (mwpmBussEntinfo != null)
			rs += "<userid>"
					+ (mwpmBussEntinfo.getUserid() == null ? ""
							: mwpmBussEntinfo.getUserid()) + "</userid>";
		rs += "<pageno>" + pageNo + "</pageno>" + "</ROOT>";
		try {
			rs = agent.queryEntInfos(rs);
		} catch (Exception e) {
			rs = "<?xml version='1.0' encoding='UTF-8'?>" + "<ROOT>"
					+ "<RETURNCODE>-1</RETURNCODE>" + "</ROOT>";
		}
		return rs;
	}

	/**
	 * 分页查看企业列表
	 */
	public static String queryPatrolTask(MwpmBussPatrol mwpmBussPatrol,
			int pageNo,String reseauid) {
		String rs = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<ROOT>";
		if (mwpmBussPatrol != null && mwpmBussPatrol.getStime() != null)
			rs += "<createtimes>" + mwpmBussPatrol.getStime()
					+ "</createtimes>";
		if (mwpmBussPatrol != null && mwpmBussPatrol.getEtime() != null)
			rs += "<createtimee>" + mwpmBussPatrol.getEtime()
					+ "</createtimee>";
		if (mwpmBussPatrol != null && mwpmBussPatrol.getName() != null)
			rs += "<name>" + mwpmBussPatrol.getName() + "</name>";
		rs += "<pageno>" + pageNo + "</pageno>";
		
		rs +="<reseauid>" +reseauid+ "</reseauid>"+"</ROOT>";
		try {
			rs = agent.queryPatrolTask(rs);
		} catch (Exception e) {
			rs = "<?xml version='1.0' encoding='UTF-8'?>" + "<ROOT>"
					+ "<RETURNCODE>-1</RETURNCODE>" + "</ROOT>";
		}
		return rs;
	}

	/**
	 * 更新企业经纬度
	 * 
	 * @return
	 */
	public static String updateEntInfo(String id, String lat, String long1) {
		String rs = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<ROOT>"
				+ "<id>" + id + "</id>" + "<lat>" + lat + "</lat>" + "<long1>"
				+ long1 + "</long1>" + "</ROOT>";
		try {
			rs = agent.updateEntInfo(rs);
		} catch (Exception e) {
			rs = "<?xml version='1.0' encoding='UTF-8'?>" + "<ROOT>"
					+ "<RETURNCODE>-1</RETURNCODE>" + "</ROOT>";
		}
		return rs;
	}

	public static String getUser(String loginName, String password) {
		String rs = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<ROOT>"
				+ "<loginname>" + loginName + "</loginname>" + "<password>"
				+ password + "</password>" + "</ROOT>";
		try {
			rs = agent.getUser(rs);
		} catch (Exception e) {
			rs = "<?xml version='1.0' encoding='UTF-8'?>" + "<ROOT>"
					+ "<RETURNCODE>-1</RETURNCODE>" + "</ROOT>";
		}
		return rs;
	}

	/**
	 * 分页查看认领企业列表
	 */
	public static String queryPatrolENTINFO(String reseauid, String page) {
		String rs = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<ROOT>"
				+ "<reseauid>" + reseauid + "</reseauid>" + "<pageno>" + page
				+ "</pageno>" + "</ROOT>";
		try {
			rs = agent.queryPatrolENTINFO(rs);
		} catch (Exception e) {
			rs = "<?xml version='1.0' encoding='UTF-8'?>" + "<ROOT>"
					+ "<RETURNCODE>-1</RETURNCODE>" + "</ROOT>";
		}
		return rs;
	}

	/**
	 * 查询法律法规，办公指引，巡查指引 dm:编码 bm:名称
	 * */
	public static String queryCodeFlag(int flag, String dm, String bm,
			String pageno) {
		String rs = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<ROOT>"
				+ "<flag>" + flag + "</flag>" 
				+ "<dm>" + dm + "</dm>"
				+ "<bz>"  + bm + "</bz>" 
				+ "<pageno>" + pageno + "</pageno>" + "</ROOT>";
		try {
			rs = agent.queryCodeFlag(rs);
		} catch (Exception e) {
			rs = "<?xml version='1.0' encoding='UTF-8'?>" + "<ROOT>"
					+ "<RETURNCODE>-1</RETURNCODE>" + "</ROOT>";
			System.out.println(e);
		}
		return rs;

	}

	/**
	 * 法律法规详细，办公指引详细，巡查指引详细
	 * */
	public static String queryCodeFlagByid(String id) {
		String rs = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<ROOT>"
				+ "<id>" + id + "</id>" + "</ROOT>";
		try {
			rs = agent.queryCodeFlagByid(rs);
		} catch (Exception e) {
			rs = "<?xml version='1.0' encoding='UTF-8'?>" + "<ROOT>"
					+ "<RETURNCODE>-1</RETURNCODE>" + "</ROOT>";
		}
		return rs;

	}

	/**
	 * 用户信息反馈(目前还有问题)
	 * */
	public static String saveUserFankui(int flag, String dm, String bm) {
		String rs = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<ROOT>"
				+ "<flag>" + flag + "</flag>" + "<dm>" + dm + "</dm>" + "<bm>"
				+ bm + "</bm>" + "</ROOT>";
		try {
			rs = agent.queryCodeFlag(rs);
		} catch (Exception e) {
			rs = "<?xml version='1.0' encoding='UTF-8'?>" + "<ROOT>"
					+ "<RETURNCODE>-1</RETURNCODE>" + "</ROOT>";
		}
		return rs;

	}

	/**
	 * 市场主体信息纠错(传入的参数暂时不对)
	 * */
	public static String saveEntErrorinfo(MwpmBussEntupdate entupdate) {
		String rs = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<ROOT>"
				+ "<name>" + entupdate.getName() + "</name>" + "<enrol>"
				+ entupdate.getEnrol() + "</enrol>" + "<submitid>"
				+ entupdate.getSubmitid() + "</submitid>" + "<updatecontent>"
				+ entupdate.getUpdatecontent() + "</updatecontent>"
				+ "<createtime>"
				+ StringUtil.DateToStr(entupdate.getCreatetime())
				+ "</createtime>" + "</ROOT>";
		try {
			rs = agent.saveEntErrorinfo(rs);
		} catch (Exception e) {
			rs = "<?xml version='1.0' encoding='UTF-8'?>" + "<ROOT>"
					+ "<RETURNCODE>-1</RETURNCODE>" + "</ROOT>";
		}
		return rs;

	}

	/**
	 * 分页查看巡查任务列表
	 */
	public static String queryPatrolLOG(String entid, String page, String type) {
		String rs = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<ROOT>"
				+ "<entid>" + entid + "</entid>" + "<type>" + type + "</type>"
				+ "<pageno>" + page + "</pageno>" + "</ROOT>";
		try {
			rs = agent.queryPatrolLOG(rs);
		} catch (Exception e) {
			rs = "<?xml version='1.0' encoding='UTF-8'?>" + "<ROOT>"
					+ "<RETURNCODE>-1</RETURNCODE>" + "</ROOT>";
		}
		return rs;
	}

	/**
	 * 查询回查记录列表
	 */
	public static String queryCompanyByPatrolId(String id, int page,String reseauid) {
		String rs = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<ROOT>"
				+ "<id>" + id + "</id>" + "<pageno>" + page + "</pageno>"
				+"<reseauid>" +reseauid+ "</reseauid>"
				+ "</ROOT>";
		try {
			rs = agent.queryCompanyByPatrolId(rs);
		} catch (Exception e) {
			rs = "<?xml version='1.0' encoding='UTF-8'?>" + "<ROOT>"
					+ "<RETURNCODE>-1</RETURNCODE>" + "</ROOT>";
		}
		return rs;
	}

	/**
	 * 查询回查记录列表
	 */
	public static String queryTimeTask(String reseauid, int page) {
		String rs = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<ROOT>"
				+ "<reseauid>" + reseauid + "</reseauid>" + "<pageno>" + page
				+ "</pageno>" + "</ROOT>";
		try {
			rs = agent.queryTimeTask(rs);
		} catch (Exception e) {
			rs = "<?xml version='1.0' encoding='UTF-8'?>" + "<ROOT>"
					+ "<RETURNCODE>-1</RETURNCODE>" + "</ROOT>";
		}
		return rs;
	}

	public static String queryNoticeCount() {
		String rs = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<ROOT>"
				+ "</ROOT>";
		try {
			rs = agent.queryNoticeCount(rs);
		} catch (Exception e) {
			rs = "<?xml version='1.0' encoding='UTF-8'?>" + "<ROOT>"
					+ "<RETURNCODE>-1</RETURNCODE>" + "</ROOT>";
		}
		return rs;
	}

	public static String queryNotice(MwpmSysMes mwpmSysMes, int pageNo) {
		String rs = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<ROOT>";
		if (mwpmSysMes != null && mwpmSysMes.getTitle() != null)
			rs += "<title>" + mwpmSysMes.getTitle() + "</title>";
		if (mwpmSysMes != null && mwpmSysMes.getCreatetimes() != null)
			rs += "<screatetime>" + mwpmSysMes.getCreatetimes() + "</screatetime>";
		if (mwpmSysMes != null && mwpmSysMes.getCreatetimee() != null)
			rs += "<ecreatetime>" + mwpmSysMes.getCreatetimee() + "</ecreatetime>";
		rs += "<pageno>" + pageNo + "</pageno>";
		rs += "</ROOT>";
		try {
			rs = agent.queryNotice(rs);
		} catch (Exception e) {
			rs = "<?xml version='1.0' encoding='UTF-8'?>" + "<ROOT>"
					+ "<RETURNCODE>-1</RETURNCODE>" + "</ROOT>";
		}
		return rs;
	}

	/**
	 * 插入巡查记录列表
	 */
	public static String savePatrolLOG(MwpmBussPatrolLog patrolLog, String pid) {
		String rs = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<entid>"
				+ patrolLog.getEntid()
				+ "</entid>"
				+ "<content>"
				+ patrolLog.getContent()
				+ "</content>"
				+ "<userid>"
				+ patrolLog.getUserid()
				+ "</userid>"
				+ "<clock>"
				+ StringUtil.DateToStr(patrolLog.getClock())
				+ "</clock>"
				+ "<type>"
				+ patrolLog.getType()
				+ "</type>"
				+ "<area>"
				+ patrolLog.getArea()
				+ "</area>"
				+ "<handman>"
				+ patrolLog.getHandman()
				+ "</handman>"
				+ "<remark>"
				+ patrolLog.getRemark()
				+ "</remark>"
				+ "<term>"
				+ StringUtil.DateToStr(patrolLog.getTerm())
				+ "</term>"
				+ "<isrecheck>"
				+ patrolLog.getIsrecheck()
				+ "</isrecheck>"
				+ "<rid>"
				+ patrolLog.getRid()
				+ "</rid>"
				+ "<pid>"
				+ (pid == null ? "hyx" : pid) + "</pid>" + "</ROOT>";
		try {
			rs = agent.savePatrolLOG(rs);
		} catch (Exception e) {
			rs = "<?xml version='1.0' encoding='UTF-8'?>" + "<ROOT>"
					+ "<RETURNCODE>-1</RETURNCODE>" + "</ROOT>";
		}
		return rs;
	}

	/**
	 * 插入巡查事项列表
	 */
	public static String savePatrolItem(List<MwpmBussPatrolItem> patrolItems) {
		String rs = "<?xml version=\"1.0\" encoding=\"utf-8\"?> <ROOT><items>";
		for (MwpmBussPatrolItem patrolItem : patrolItems) {
			rs += "<item>" + "<logid>" + patrolItem.getLogid() + "</logid>"
					+ "<contentid>" + patrolItem.getContentid()
					+ "</contentid>" + "<qid>" + patrolItem.getQid() + "</qid>"
					+ "<disposeid>" + patrolItem.getDisposeid()
					+ "</disposeid>" + "</item>";
		}
		rs += "</items></ROOT>";
		try {
			rs = agent.savePatrolItem(rs);
		} catch (Exception e) {
			rs = "<?xml version='1.0' encoding='UTF-8'?>" + "<ROOT>"
					+ "<RETURNCODE>-1</RETURNCODE>" + "</ROOT>";
		}
		return rs;
	}

	/**
	 * 通过标题，日期查询公告信息
	 */
	public static String queryNotice(String title, String Strattime,
			String Endtime, int pageNo) {
		String rs = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<ROOT>"
				+ "<title>" + title + "</title>" + "<screatetime>" + Strattime
				+ "</screatetime>" + "<ecreatetime>" + Endtime
				+ "</ecreatetime>" + "<pageno>" + pageNo + "</pageno>"
				+ "</ROOT>";
		try {
			rs = agent.queryNotice(rs);
		} catch (Exception e) {
			rs = "<?xml version='1.0' encoding='UTF-8'?>" + "<ROOT>"
					+ "<RETURNCODE>-1</RETURNCODE>" + "</ROOT>";
			System.out.println(e);
			e.printStackTrace();
		}
		return rs;

	}

	/**
	 * 公告详细查询(通过id)
	 */
	public static String queryNoticeInfo(String id) {
		String rs = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<ROOT>"
				+ "<id>" + id + "</id>" + "</ROOT>";
		try {
			rs = agent.queryNoticeInfo(rs);
		} catch (Exception e) {
			rs = "<?xml version='1.0' encoding='UTF-8'?>" + "<ROOT>"
					+ "<RETURNCODE>-1</RETURNCODE>" + "</ROOT>";
			System.out.println(e);
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * 公告发布
	 */
	public static String saveNotice(String title, String content,
			String userid, String createtime, String type) {
		String rs = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<ROOT>"
				+ "<title>" + title + "</title>" + "<content>" + content
				+ "</content>" + "<userid>" + userid + "</userid>"
				+ "<createtime>" + createtime + "</createtime>" + "<type>"
				+ "</type>" + type + "</ROOT>";
		try {
			rs = agent.saveNotice(rs);
		} catch (Exception e) {
			rs = "<?xml version='1.0' encoding='UTF-8'?>" + "<ROOT>"
					+ "<RETURNCODE>-1</RETURNCODE>" + "</ROOT>";
			System.out.println(e);
			e.printStackTrace();
		}
		return rs;

	}

	public static String queryNoCards(MwpmBussNocard mwpmBussNocard, int pageNo) {
		String rs = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<ROOT>";
		if (mwpmBussNocard != null && mwpmBussNocard.getSubmittimes() != null)
			rs += "<submittimes>" + mwpmBussNocard.getSubmittimes()
					+ "</submittimes>";
		if (mwpmBussNocard != null && mwpmBussNocard.getSubmittimee() != null)
			rs += "<submittimee>" + mwpmBussNocard.getSubmittimee()
					+ "</submittimee>";
		if (mwpmBussNocard != null && mwpmBussNocard.getReseauid() != null)
			rs += "<reseauid>" + mwpmBussNocard.getReseauid() + "</reseauid>";

		rs += "<pageno>" + pageNo + "</pageno>" + "</ROOT>";
		try {
			rs = agent.queryNoCards(rs);
		} catch (Exception e) {
			rs = "<?xml version='1.0' encoding='UTF-8'?>" + "<ROOT>"
					+ "<RETURNCODE>-1</RETURNCODE>" + "</ROOT>";
			System.out.println(e);
			e.printStackTrace();
		}
		return rs;
	}

	// 巡查事项列表
	public static String queryMwpmBussPatrolItemList(String lid, String pageNo) {
		String rs = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<ROOT>"
				+ "<logid>" + lid + "</logid>" + "<pageno>" + pageNo
				+ "</pageno>" + "</ROOT>";
		try {
			rs = agent.queryMwpmBussPatrolItemList(rs);
		} catch (Exception e) {
			rs = "<?xml version='1.0' encoding='UTF-8'?>" + "<ROOT>"
					+ "<RETURNCODE>-1</RETURNCODE>" + "</ROOT>";
			System.out.println(e);
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * 查询回查记录列表
	 */
	public static String queryReturnLogList(String contentid, String page) {
		String rs = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<ROOT>"
				+ "<lid>" + contentid + "</lid>" + "<pageno>" + page
				+ "</pageno>" + "</ROOT>";
		try {
			rs = agent.queryReturnLogList(rs);
		} catch (Exception e) {
			rs = "<?xml version='1.0' encoding='UTF-8'?>" + "<ROOT>"
					+ "<RETURNCODE>-1</RETURNCODE>" + "</ROOT>";
		}
		return rs;
	}

	/*
	 * 插入回查记录
	 */
	public static String saveReturnLog(MwpmBussReturnlog returnLog) {
		/*
		 * <ROOT> <contentid>1111</contentid> <checkthing>1111<eckthing>
		 * <disposetype>1</disposetype> <checktime>2013-01-15<ecktime> </ROOT>
		 */
		String rs = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<ROOT>"
				+ "<lid>" + returnLog.getLid() + "</lid>" + "<checkthing>"
				+ returnLog.getCheckthing() + "</checkthing>" + "<disposetype>"
				+ returnLog.getDisposetype() + "</disposetype>" + "<checktime>"
				+ StringUtil.DateToStr(returnLog.getChecktime())
				+ "</checktime>" + "</ROOT>";
		try {
			rs = agent.saveReturnLog(rs);
		} catch (Exception e) {
			rs = "<?xml version='1.0' encoding='UTF-8'?>" + "<ROOT>"
					+ "<RETURNCODE>-1</RETURNCODE>" + "</ROOT>";
		}
		return rs;
	}

	public static String getEntByReaId(MwpmBussEntinfo mwpmBussEntinfo,
			String id) {
		String rs = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<ROOT>";
		if (mwpmBussEntinfo != null && mwpmBussEntinfo.getReseauid() != null)
			rs += "<reseauid>" + mwpmBussEntinfo.getReseauid() + "</reseauid>";
		if (mwpmBussEntinfo != null)
			rs += "<userid>"
					+ (mwpmBussEntinfo.getUserid() == null ? ""
							: mwpmBussEntinfo.getUserid()) + "</userid>";
		rs += "<id>"+id+"</id>"+"</ROOT>";
		try {
			rs = agent.getEntByReaId(rs);
		} catch (Exception e) {
			rs = "<?xml version='1.0' encoding='UTF-8'?>" + "<ROOT>"
					+ "<RETURNCODE>-1</RETURNCODE>" + "</ROOT>";
			System.out.println(e);
			e.printStackTrace();
		}
		return rs;
	}

	public static String saveCase(MwpmBussCase c) {

		String rs = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<ROOT>"
				+ "<eid>"
				+ c.getEid()
				+ "</eid>"
				+ "<name>"
				+ c.getName()
				+ "</name>"
				+ "<submituserid>"
				+ c.getSubmituserid()
				+ "</submituserid>"
				+ "<submitunitid>"
				+ c.getSubmitunitid()
				+ "</submitunitid>"
				+ "<createtime>"
				+ StringUtil.DateToStr(c.getCreatetime())
				+ "</createtime>"
				+ "<jointuserid>"
				+ c.getJointuserid()
				+ "</jointuserid>"
				+ "<idea>"
				+ c.getIdea()
				+ "</idea>"
				+ "<status>"
				+ c.getStatus()
				+ "</status>"
				+ "<main>"
				+ c.getMain()
				+ "</main>"
				+ "<property>"
				+ c.getProperty()
				+ "</property>"
				+ "<casesource>"
				+ c.getCasesource()
				+ "</casesource>"
				+ "<baseinfo>"
				+ c.getBaseinfo()
				+ "</baseinfo>" + "</ROOT>";
		try {
			rs = agent.saveCase(rs);
		} catch (Exception e) {
			rs = "<?xml version='1.0' encoding='UTF-8'?>" + "<ROOT>"
					+ "<RETURNCODE>-1</RETURNCODE>" + "</ROOT>";
			System.out.println(e);
			e.printStackTrace();
		}
		return rs;
	}

	public static String getUserListByrId(String reseauid, String pageno) {
		String rs = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<ROOT>"
				+ "<reseauid>" + reseauid + "</reseauid>" + "<pageno>" + pageno
				+ "</pageno>" + "</ROOT>";
		try {
			rs = agent.getUserListByrId(rs);
		} catch (Exception e) {
			rs = "<?xml version='1.0' encoding='UTF-8'?>" + "<ROOT>"
					+ "<RETURNCODE>-1</RETURNCODE>" + "</ROOT>";
			System.out.println(e);
			e.printStackTrace();
		}
		return rs;
	}

	public static String queryEntErrorList(MwpmBussEntinfo mwpmBussEntinfo,
			int pageno) {
		String rs = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<ROOT>";
		if (mwpmBussEntinfo != null && mwpmBussEntinfo.getUserid() != null)
			rs += "<userid>" + mwpmBussEntinfo.getUserid() + "</userid>";
		if (mwpmBussEntinfo != null && mwpmBussEntinfo.getReseauid() != null)
			rs += "<reseauid>" + mwpmBussEntinfo.getReseauid() + "</reseauid>";
		rs += "<pageno>" + pageno + "</pageno>" + "</ROOT>";
		try {
			rs = agent.queryEntErrorList(rs);
		} catch (Exception e) {
			rs = "<?xml version='1.0' encoding='UTF-8'?>" + "<ROOT>"
					+ "<RETURNCODE>-1</RETURNCODE>" + "</ROOT>";
			e.printStackTrace();
		}
		return rs;
	}

	public static String saveNocard(MwpmBussNocard nocard) {
		String rs = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<ROOT>"
				+ "<title>"
				+ nocard.getTitle()
				+ "</title>"
				+ "<operator>"
				+ nocard.getOperator()
				+ "</operator>"
				+ "<idcard>"
				+ nocard.getIdcard()
				+ "</idcard>"
				+ "<phone>"
				+ nocard.getPhone()
				+ "</phone>"
				+ "<address>"
				+ nocard.getAddress()
				+ "</address>"
				+ "<type>"
				+ nocard.getType()
				+ "</type>"
				+ "<item>"
				+ nocard.getItem()
				+ "</item>"
				+ "<result>"
				+ nocard.getResult()
				+ "</result>"
				+ "<submitid>"
				+ nocard.getSubmitid()
				+ "</submitid>"
				+ "<submittime>"
				+ StringUtil.DateToStr(nocard.getSubmittime())
				+ "</submittime>" + "</ROOT>";
		try {
			rs = agent.saveNocard(rs);
		} catch (Exception e) {
			rs = "<?xml version='1.0' encoding='UTF-8'?>" + "<ROOT>"
					+ "<RETURNCODE>-1</RETURNCODE>" + "</ROOT>";
			System.out.println(e);
			e.printStackTrace();
		}
		return rs;
	}

	public static String queryZxPatrolTask(MwpmBussEntinfo mwpmBussEntinfo,
			int pageNo) {
		String rs = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<ROOT>";
		if (mwpmBussEntinfo != null && mwpmBussEntinfo.getEnrol() != null)
			rs += "<enrol>" + mwpmBussEntinfo.getEnrol() + "</enrol>";
		if (mwpmBussEntinfo != null && mwpmBussEntinfo.getName() != null)
			rs += "<name>" + mwpmBussEntinfo.getName() + "</name>";
		if (mwpmBussEntinfo != null && mwpmBussEntinfo.getMember() != null)
			rs += "<member>" + mwpmBussEntinfo.getMember() + "</member>";
		if (mwpmBussEntinfo != null && mwpmBussEntinfo.getAddress() != null)
			rs += "<address>" + mwpmBussEntinfo.getAddress() + "</address>";
		if (mwpmBussEntinfo != null && mwpmBussEntinfo.getEarcap() != null)
			rs += "<earcap>" + mwpmBussEntinfo.getEarcap() + "</earcap>";
		if (mwpmBussEntinfo != null && mwpmBussEntinfo.getWorkarea() != null)
			rs += "<workarea>" + mwpmBussEntinfo.getWorkarea() + "</workarea>";
		if (mwpmBussEntinfo != null && mwpmBussEntinfo.getReseauid() != null)
			rs += "<reseauid>" + mwpmBussEntinfo.getReseauid() + "</reseauid>";
		rs += "<pageno>" + pageNo + "</pageno>" + "</ROOT>";
		try {
			rs = agent.queryZxPatrolTask(rs);
		} catch (Exception e) {
			rs = "<?xml version='1.0' encoding='UTF-8'?>" + "<ROOT>"
					+ "<RETURNCODE>-1</RETURNCODE>" + "</ROOT>";
			System.out.println(e);
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * 用户信息反馈(目前还有问题)
	 */
	public static String saveUserFankui(String content, String userid,
			String time) {
		String rs = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<ROOT>"
				+ "<content>" + content + "</content>" + "<userid>" + userid
				+ "</userid>" + "<time>" + time + "</time>" + "</ROOT>";
		try {
			rs = agent.saveUserFankui(rs);
		} catch (Exception e) {
			rs = "<?xml version='1.0' encoding='UTF-8'?>" + "<ROOT>"
					+ "<RETURNCODE>-1</RETURNCODE>" + "</ROOT>";
			System.out.println(e);
			e.printStackTrace();
		}
		return rs;

	}

	/**
	 * 市场主体信息纠错(传入的参数暂时不对)
	 * */
	public static String saveEntErrorinfo(String name, String enrol,
			String submitid, String updatecontent, String time) {
		String rs = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<ROOT>"
				+ "<name>" + name + "</name>" + "<enrol>" + enrol + "</enrol>"
				+ "<submitid>" + submitid + "</submitid>" + "<updatecontent>"
				+ updatecontent + "</updatecontent>" + "<time>" + time
				+ "</time>" + "</ROOT>";
		try {
			rs = agent.saveEntErrorinfo(rs);
		} catch (Exception e) {
			rs = "<?xml version='1.0' encoding='UTF-8'?>" + "<ROOT>"
					+ "<RETURNCODE>-1</RETURNCODE>" + "</ROOT>";
		}
		return rs;

	}

	/**
	 * 获取用户级别
	 * 
	 * @param reId
	 * @return
	 */
	public static String getUserLevel(String loginname) {
		String rs = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<ROOT>"
				+ "<loginname>" + loginname + "</loginname>" + "</ROOT>";
		try {
			rs = agent.getUserLevel(rs);
		} catch (Exception e) {
			rs = "<?xml version='1.0' encoding='UTF-8'?>" + "<ROOT>"
					+ "<RETURNCODE>-1</RETURNCODE>" + "</ROOT>";
			System.out.println(e);
			e.printStackTrace();
		}
		return rs;
	}

	/*
	 * 邮箱补录
	 */
	public static String updateEntEmail(String eid, String email) {
		String rs = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<ROOT>"
				+ "<id>" + eid + "</id>" + "<email>" + email + "</email>"
				+ "</ROOT>";

		try {
			rs = agent.updateEntEmail(rs);
		} catch (Exception e) {
			rs = "<?xml version='1.0' encoding='UTF-8'?>" + "<ROOT>"
					+ "<RETURNCODE>-1</RETURNCODE>" + "</ROOT>";
			System.out.println(e);
			e.printStackTrace();
		}
		return rs;
	}

	public static String getCommonUserList(int pageNo) {
		String rs = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<ROOT>"
				+ "<pageno>" + pageNo + "</pageno>" + "</ROOT>";
		try {
			rs = agent.getCommonUserList(rs);
		} catch (Exception e) {
			rs = "<?xml version='1.0' encoding='UTF-8'?>" + "<ROOT>"
					+ "<RETURNCODE>-1</RETURNCODE>" + "</ROOT>";
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * 获取企业发送邮件列表
	 */
	public static String queryEntListByemail(String reseauid, String pageNo) {
		String rs = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<ROOT>"
				+ "<reseauid>" + reseauid + "</reseauid>" + "<pageno>" + pageNo
				+ "</pageno>" + "</ROOT>";
		try {
			rs = agent.queryEntListByemail(rs);
		} catch (Exception e) {
			rs = "<?xml version='1.0' encoding='UTF-8'?>" + "<ROOT>"
					+ "<RETURNCODE>-1</RETURNCODE>" + "</ROOT>";
			System.out.println(e);
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * 发送邮件 sendEmail
	 */
	public static String sendEmail(String email, String content) {
		String rs = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<ROOT>"
				+ "<email>" + email + "</email>" + "<content>" + content
				+ "</content>" + "</ROOT>";
		try {
			rs = agent.sendEmail(rs);
		} catch (Exception e) {
			rs = "<?xml version='1.0' encoding='UTF-8'?>" + "<ROOT>"
					+ "<RETURNCODE>-1</RETURNCODE>" + "</ROOT>";
			System.out.println(e);
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * 查询巡查轨迹
	 */
	public static String queryPatrolLoca(String userid, String createtimes,
			String createtimee) {
		String rs = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<ROOT>"
				+ "<userid>" + userid + "</userid>" + "<createtimes>"
				+ createtimes + "</createtimes>" + "<createtimee>"
				+ createtimee + "</createtimee>" + "</ROOT>";
		try {
			rs = agent.queryPatrolLoca(rs);
		} catch (Exception e) {
			rs = "<?xml version='1.0' encoding='UTF-8'?>" + "<ROOT>"
					+ "<RETURNCODE>-1</RETURNCODE>" + "</ROOT>";
			System.out.println(e);
			e.printStackTrace();
		}
		return rs;
	}

	public static String queryDictionaryList(String ajxz, String page) {
		String rs = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<groupcode>ajxz</groupcode>" + "<pageno>" + page
				+ "</pageno>" + "</ROOT>";
		try {
			rs = agent.queryDictionaryList(rs);
		} catch (Exception e) {
			rs = "<?xml version='1.0' encoding='UTF-8'?>" + "<ROOT>"
					+ "<RETURNCODE>-1</RETURNCODE>" + "</ROOT>";
			System.out.println(e);
			e.printStackTrace();
		}
		return rs;
	}

	public static String queryPType() {
		String rs = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "</ROOT>";
		try {
			rs = agent.queryPType(rs);
		} catch (Exception e) {
			rs = "<?xml version='1.0' encoding='UTF-8'?>" + "<ROOT>"
					+ "<RETURNCODE>-1</RETURNCODE>" + "</ROOT>";
			System.out.println(e);
			e.printStackTrace();
		}
		return rs;
	}

	public static String savePhoneIpadByUserId(String userid, String type,
			String deviceId) {
		String rs = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<userid>" + userid + "</userid>" + "<deviceId>" + deviceId
				+ "</deviceId>" + "<type>" + type + "</type>" + "</ROOT>";
		try {
			rs = agent.savePhoneIpadByUserId(rs);
		} catch (Exception e) {
			rs = "<?xml version='1.0' encoding='UTF-8'?>" + "<ROOT>"
					+ "<RETURNCODE>-1</RETURNCODE>" + "</ROOT>";
			System.out.println(e);
			e.printStackTrace();
		}
		return rs;
	}

	public static String getPhoneIpadByUserId(String userid) {
		String rs = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<userid>" + userid + "</userid>" + "</ROOT>";
		try {
			rs = agent.getPhoneIpadByUserId(rs);
		} catch (Exception e) {
			rs = "<?xml version='1.0' encoding='UTF-8'?>" + "<ROOT>"
					+ "<RETURNCODE>-1</RETURNCODE>" + "</ROOT>";
			System.out.println(e);
			e.printStackTrace();
		}
		return rs;
	}

	public static String getRoleTypeByUserId(String userid) {
		String rs = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<userid>" + userid + "</userid>" + "</ROOT>";
		try {
			rs = agent.getRoleTypeByUserId(rs);
		} catch (Exception e) {
			rs = "<?xml version='1.0' encoding='UTF-8'?>" + "<ROOT>"
					+ "<RETURNCODE>-1</RETURNCODE>" + "</ROOT>";
			e.printStackTrace();
		}
		return rs;
	}

	// 插入巡查记录和类型对应表
	public static String savePType(String pk, String typesid) {
		String rs = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<ROOT>"
				+ "<lid>" + pk + "</lid>" + "<types>" + typesid + "</types>"
				+ "</ROOT>";
		try {
			rs = agent.savePType(rs);
		} catch (Exception e) {
			rs = "<?xml version='1.0' encoding='UTF-8'?>" + "<ROOT>"
					+ "<RETURNCODE>-1</RETURNCODE>" + "</ROOT>";
			System.out.println(e);
			e.printStackTrace();
		}
		return rs;
	}

	public static String getCommonUserList(int pageNo, String userid) {
		String rs = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<ROOT>"
				+ "<pageno>" + pageNo + "</pageno>" + "<userid>" + userid
				+ "</userid>" + "</ROOT>";
		try {
			rs = agent.getCommonUserList(rs);
		} catch (Exception e) {
			rs = "<?xml version='1.0' encoding='UTF-8'?>" + "<ROOT>"
					+ "<RETURNCODE>-1</RETURNCODE>" + "</ROOT>";
			e.printStackTrace();
		}
		return rs;
	}
	
	public static String queryEntByLatLong(double lats, double late,
			double longs, double longe) {
		String rs = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<ROOT>"
				+ "<late>" + lats + "</late>" + "<lats>" + late + "</lats>"
				+ "<longs>" + longs + "</longs>" + "<longe>" + longe + "</longe>"
				+ "</ROOT>";
		try {
			rs = agent.queryEntByLatLong(rs);
		} catch (Exception e) {
			rs = "<?xml version='1.0' encoding='UTF-8'?>" + "<ROOT>"
					+ "<RETURNCODE>-1</RETURNCODE>" + "</ROOT>";
			e.printStackTrace();
		}
		return rs;
	}
	
	public static String getReseaNameById(String reseaId) {
		String rs = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" 
				+ "<ROOT>"
				+ "<id>" + reseaId + "</id>" 
				+ "</ROOT>";
		try {
			rs = agent.getReseaNameById(rs);
		} catch (Exception e) {
			rs = "<?xml version='1.0' encoding='UTF-8'?>" + "<ROOT>"
					+ "<RETURNCODE>-1</RETURNCODE>" + "</ROOT>";
			e.printStackTrace();
		}
		return rs;
	}
	
	
	
}
