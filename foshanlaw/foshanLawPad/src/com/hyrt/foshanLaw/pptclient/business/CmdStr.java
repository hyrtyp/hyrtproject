/**
 * 
 */
package com.hyrt.foshanLaw.pptclient.business;
 

import com.hyrt.foshanLaw.pptclient.common.FileObj;

/**
 * Description:各指令json串
 * 
 * @author 郑伟
 * @Date 2013-1-9
 * @Copyright:2013-1-9
 * @Company:华源润通（北京）科技有限公司重庆分公司
 */
public class CmdStr {

	/**
	 * 返回登陆json串
	 * 
	 * @param stamp
	 *            时间戳
	 * @param userid
	 *            用户id
	 * @return
	 */
	public static String getLoginstr(String stamp, String userid) {
		if(userid.equals("")==false){
			String ss = String.format(
				"{\"cmd\":\"login\",\"uid\":\"%s\",\"stamp\":\"%s\"}", userid,
				stamp);
			return ss;
		}
		else{
			return "";
		}
	}

	/**
	 * 查询会话信息json串
	 * 
	 * @param stamp
	 * @param userid
	 * @param sessionid
	 * @return
	 */
	public static String getSessionInfo(String stamp, String userid,
			String sessionid) {
		String s = String
				.format("{\"cmd\":\"info\",\"uid\":\"%s\",\"sessionid\":\"%s\",\"stamp\":\"%s\"}",
						userid, sessionid, stamp);
		return s;
	}

	/**
	 * 生成文件上传的json串
	 * @param stamp 时间戳
	 * @param userid 用户id
	 * @param obj  FileObj对象
	 * @param sessionid 会话id
	 * @param sessionname 会话名称
	 * @return
	 */
	public static String getFileInfo(String stamp, String userid, FileObj obj,
			String sessionid, String sessionname) {
		/*
		 * { \"cmd\":\"file\", \"uid\":\"用户id\", \"filename\":\"文件名称\",
		 * \"path\":\"ftp根目录路径\", \"second\":\"时长\", \"time\":\"对讲开始时间\",
		 * \"receobj\":[\"对象1id\",\"对象2id\"], \"type\":\"1=组发,0=单人或者多个人\",
		 * \"stamp\":\"时间戳\", \"sessionid\":\"会话id\", \"sessionname"\:\"会话名称\" }
		 */
		StringBuilder sb = new StringBuilder();
		sb.append("{\"cmd\":\"file\",");
		sb.append("\"uid\":\"" + userid + "\",");
		sb.append("\"filename\":\"" + obj.getFilename() + "\",");
		// ftp路径 ： userid/PPT_STAMP.amr
		//String ftppath = userid + "/PPT_" + stamp + ".amr";
		String ftppath = sessionid+"/"+userid + "/" + obj.getFilename();
		sb.append("\"path\":\"" + ftppath + "\",");
		sb.append("\"second\":\"" + obj.getSecond() + "\",");
		sb.append("\"time\":\"" + obj.getTime() + "\",");
		// sb.append("\"receobj\":[\"对象1id\",\"对象2id\"],

		if (obj.isGroup()) {
			sb.append("\"type\":\"1\",");
			sb.append("\"receobj\":[\"\"],");
		} else {
			sb.append("\"type\":\"0\",");
			sb.append("\"receobj\":[" + obj.getArrayListStr() + "],");
		}
		sb.append("\"stamp\":\"" + stamp + "\",");
		sb.append("\"sessionid\":\"" + sessionid + "\",");
		sb.append("\"sessionname\":\"" + sessionname + "\"}");
		return sb.toString();
	}
	
	public static String getReturnMsg(String cmd,String uid,String stamp){
		String str=String.format("{\"cmd\":\"b%s\",\"uid\":\"%s\",\"stamp\":\"%s\"}", cmd,uid,stamp);
		return str;
	}
}
