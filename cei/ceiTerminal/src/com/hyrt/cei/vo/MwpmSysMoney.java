package com.hyrt.cei.vo;

import java.util.Date;

/**
 * MwpmSysMoney entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class MwpmSysMoney implements java.io.Serializable {

	private String id;
	private String funid;
	private String buytype;
	private String userid;
	private String resourcetype;
	private Date buytime;
	private Date stoptime;
	private String fld1;
	private String fld2;
	private String status;

	// Constructors

	

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFunid() {
		return this.funid;
	}

	public void setFunid(String funid) {
		this.funid = funid;
	}

	public String getBuytype() {
		return this.buytype;
	}

	public void setBuytype(String buytype) {
		this.buytype = buytype;
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getResourcetype() {
		return this.resourcetype;
	}

	public void setResourcetype(String resourcetype) {
		this.resourcetype = resourcetype;
	}

	public Date getBuytime() {
		return this.buytime;
	}

	public void setBuytime(Date buytime) {
		this.buytime = buytime;
	}

	public Date getStoptime() {
		return this.stoptime;
	}

	public void setStoptime(Date stoptime) {
		this.stoptime = stoptime;
	}

	public String getFld1() {
		return this.fld1;
	}

	public void setFld1(String fld1) {
		this.fld1 = fld1;
	}

	public String getFld2() {
		return this.fld2;
	}

	public void setFld2(String fld2) {
		this.fld2 = fld2;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
