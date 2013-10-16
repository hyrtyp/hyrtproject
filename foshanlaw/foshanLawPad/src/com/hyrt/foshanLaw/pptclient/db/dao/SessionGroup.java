/**
 * 
 */
package com.hyrt.foshanLaw.pptclient.db.dao;

/**
 * Description:本地表sessiongroup，存放会话分组
 * 
 * @author 郑伟
 * @Date 2013-1-8
 * @Copyright:2013-1-8
 * @Company:华源润通（北京）科技有限公司重庆分公司
 */
public class SessionGroup {
	long id;   //
	String sessionid;  //分组id
	String sessionname;  //分组名称
	String lasttm;    //最后通话时间
	boolean group;   //true=组会话
	
	int msgcount;

	public SessionGroup() {
		id = 0;
		sessionid = "0";
		sessionname = "0";
		group=false;
		msgcount=0;
	}
 

	public long getId() {
		return id;
	}

	public void setId(long obj) {
		this.id = obj;
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

	public String getSessionname() {
		return sessionname;
	}

	public void setSessionname(String obj) {
		if (obj.equals(""))
			this.sessionname = "0";
		else
			this.sessionname = obj;
	}

	public String getLasttm() {
		return lasttm;
	}

	public void setLasttm(String obj) {
		if (obj.equals(""))
			this.lasttm = "0";
		else
			this.lasttm = obj;
	}

	public boolean isGroup() {
		return group;
	}

	public void setGroup(boolean group) {
		this.group = group;
	}

	public boolean getGroup(){
		return this.group;
	}
	public int getMsgcount() {
		return msgcount;
	}

	public void setMsgcount(int msgcount) {
		this.msgcount = msgcount;
	}

	public void msgAdd(){
		msgcount++;
	}
	
	public void msgReset(){
		msgcount=0;
	}
}
