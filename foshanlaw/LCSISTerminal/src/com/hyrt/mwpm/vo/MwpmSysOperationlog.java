package com.hyrt.mwpm.vo;

import java.util.Date;

/**
 * MwpmSysOperationlog entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class MwpmSysOperationlog  implements java.io.Serializable {

	private String operateid;
	private String loginid;
	private String operatemodule;
	private String operatetype;
	private String operatebegin;
	private String operateend;
	private Date operatetime;
	private String operatedescription;
	private String operatemoduleid;
    private Date beginTime;
    private Date entTime;
    private String entTime1 = "";
    private String beginTime1 = "";
    
	

	// Property accessors

	public String getOperateid() {
		return this.operateid;
	}

	public void setOperateid(String operateid) {
		this.operateid = operateid;
	}

	public String getLoginid() {
		return this.loginid;
	}

	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}

	public String getOperatemodule() {
		return this.operatemodule;
	}

	public void setOperatemodule(String operatemodule) {
		this.operatemodule = operatemodule;
	}

	public String getOperatetype() {
		return this.operatetype;
	}

	public void setOperatetype(String operatetype) {
		this.operatetype = operatetype;
	}

	public String getOperatebegin() {
		return this.operatebegin;
	}

	public void setOperatebegin(String operatebegin) {
		this.operatebegin = operatebegin;
	}

	public String getOperateend() {
		return this.operateend;
	}

	public void setOperateend(String operateend) {
		this.operateend = operateend;
	}

	public Date getOperatetime() {
		return this.operatetime;
	}

	public void setOperatetime(Date operatetime) {
		this.operatetime = operatetime;
	}

	public String getOperatedescription() {
		return this.operatedescription;
	}

	public void setOperatedescription(String operatedescription) {
		this.operatedescription = operatedescription;
	}

	public String getOperatemoduleid() {
		return this.operatemoduleid;
	}

	public void setOperatemoduleid(String operatemoduleid) {
		this.operatemoduleid = operatemoduleid;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEntTime() {
		return entTime;
	}

	public void setEntTime(Date entTime) {
		this.entTime = entTime;
	}

	public String getEntTime1() {
		return entTime1;
	}

	public void setEntTime1(String entTime1) {
		this.entTime1 = entTime1;
	}

	public String getBeginTime1() {
		return beginTime1;
	}

	public void setBeginTime1(String beginTime1) {
		this.beginTime1 = beginTime1;
	}

}
