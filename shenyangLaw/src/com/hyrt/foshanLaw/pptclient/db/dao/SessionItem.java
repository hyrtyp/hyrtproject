/**
 * 
 */
package com.hyrt.foshanLaw.pptclient.db.dao;

import java.io.Serializable;

import com.hyrt.foshanLaw.pptclient.common.GlobalFunction;

/**
 * Description:本地表sessionitem ，会话记录
 * 
 * @author 郑伟
 * @Date 2013-1-8
 * @Copyright:2013-1-8
 * @Company:华源润通（北京）科技有限公司重庆分公司
 */
public class SessionItem implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	long id;
	String sessionid;
	String tm;
	String filepath;
	String ftppath;
	String fuid;
	String second;
	int issend;  //1=发送成功
	String errmsg;  //错误信息

	public SessionItem() {
		id = 0;
		sessionid = "0";
		tm = "0";
		filepath = "0";
		ftppath = "0";
		fuid = "0";
		second = "0";
		issend=2;   // 2=成功，1=发送中,0=失败
		errmsg="发送中";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSessionid() {
		return sessionid;
	}

	public void setSessionid(String obj) {
		if ("".equals(obj))
			this.sessionid = "0";
		else
			this.sessionid = obj;
	}

	public String getTime() {
		return tm;
	}
	
	public String getTm() {
		return tm.replace(GlobalFunction.GetDate(), "今天");
	}

	public void setTm(String obj) {
		if ("".equals(obj))
			this.tm = "0";
		else
			this.tm = obj;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String obj) {
		if ("".equals(obj))
			this.filepath = "0";
		else
			this.filepath = obj;
	}

	public String getFtppath() {
		return ftppath;
	}

	public void setFtppath(String obj) {
		if ("".equals(obj))
			this.ftppath = "0";
		else
			this.ftppath = obj;
	}

	public String getFuid() {
		return fuid;
	}

	public void setFuid(String obj) {
		if ("".equals(obj))
			this.fuid = "0";
		else
			this.fuid = obj;
	}

	public String getSecond() {
		return second;
	}

	public void setSecond(String obj) {
		if ("".equals(obj))
			this.second = "0";
		else
			this.second = obj;
	}

 

	public int getIssend() {
		return issend;
	}

	public void setIssend(int issend) {
		this.issend = issend;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

}
