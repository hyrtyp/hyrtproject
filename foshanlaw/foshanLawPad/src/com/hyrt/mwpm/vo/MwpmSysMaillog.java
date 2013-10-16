package com.hyrt.mwpm.vo;

import java.util.Date;

/**
 * MwpmSysMaillog entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class MwpmSysMaillog implements java.io.Serializable {

	// Fields

	private String id;
	private String userid;
	private String mainid;
	private String roleid;
	private Date sendtime;
	private String issuccess;

	// Constructors

	/** default constructor */
	public MwpmSysMaillog() {
	}

	/** minimal constructor */
	public MwpmSysMaillog(String id) {
		this.id = id;
	}

	/** full constructor */
	public MwpmSysMaillog(String id, String userid, String mainid, String roleid, Date sendtime, String issuccess) {
		this.id = id;
		this.userid = userid;
		this.mainid = mainid;
		this.roleid = roleid;
		this.sendtime = sendtime;
		this.issuccess = issuccess;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getMainid() {
		return this.mainid;
	}

	public void setMainid(String mainid) {
		this.mainid = mainid;
	}

	public String getRoleid() {
		return this.roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public Date getSendtime() {
		return this.sendtime;
	}

	public void setSendtime(Date sendtime) {
		this.sendtime = sendtime;
	}

	public String getIssuccess() {
		return this.issuccess;
	}

	public void setIssuccess(String issuccess) {
		this.issuccess = issuccess;
	}

}