package com.hyrt.mwpm.vo;

import java.util.Date;

/**
 * MwpmSysLoginlog entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class MwpmSysLoginlog  implements java.io.Serializable {

	private String loginid;
	private String usercode;
	private String username;
	private String unitname;
	private String loginip;
	private Date logintime;
	private String deviceid;
	private Date loginouttime;
	private String logintype;
	 private String loginCount;
     private String begTime1;
     private String endTime1;
     private Date begTime;
     private Date endTime;
	// Constructors

	
	// Property accessors

	public String getLoginid() {
		return this.loginid;
	}

	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}

	public String getUsercode() {
		return this.usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUnitname() {
		return this.unitname;
	}

	public void setUnitname(String unitname) {
		this.unitname = unitname;
	}

	public String getLoginip() {
		return this.loginip;
	}

	public void setLoginip(String loginip) {
		this.loginip = loginip;
	}

	public Date getLogintime() {
		return this.logintime;
	}

	public void setLogintime(Date logintime) {
		this.logintime = logintime;
	}

	public String getDeviceid() {
		return this.deviceid;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	public Date getLoginouttime() {
		return this.loginouttime;
	}

	public void setLoginouttime(Date loginouttime) {
		this.loginouttime = loginouttime;
	}

	public String getLogintype() {
		return this.logintype;
	}

	public void setLogintype(String logintype) {
		this.logintype = logintype;
	}

	public String getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(String loginCount) {
		this.loginCount = loginCount;
	}

	public String getBegTime1() {
		return begTime1;
	}

	public void setBegTime1(String begTime1) {
		this.begTime1 = begTime1;
	}

	public String getEndTime1() {
		return endTime1;
	}

	public void setEndTime1(String endTime1) {
		this.endTime1 = endTime1;
	}

	public Date getBegTime() {
		return begTime;
	}

	public void setBegTime(Date begTime) {
		this.begTime = begTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

}
