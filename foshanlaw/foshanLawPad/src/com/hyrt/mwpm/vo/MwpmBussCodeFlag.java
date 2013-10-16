package com.hyrt.mwpm.vo;

import java.util.Date;

/**
 * MwpmBussRole entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class MwpmBussCodeFlag implements java.io.Serializable {
	// Fields

	private String id;
	private String dm;
	private String ms;
	private String bz;
	private String flag;
	private String parentid;
	private Date yxq;

	// Constructors

	/** default constructor */
	public MwpmBussCodeFlag() {
	}

	/** minimal constructor */
	public MwpmBussCodeFlag(String id) {
		this.id = id;
	}

	/** full constructor */
	public MwpmBussCodeFlag(String id, String dm, String ms, String bz, String flag, String parentid, Date yxq) {
		this.id = id;
		this.dm = dm;
		this.ms = ms;
		this.bz = bz;
		this.flag = flag;
		this.parentid = parentid;
		this.yxq = yxq;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDm() {
		return this.dm;
	}

	public void setDm(String dm) {
		this.dm = dm;
	}

	public String getMs() {
		return this.ms;
	}

	public void setMs(String ms) {
		this.ms = ms;
	}

	public String getBz() {
		return this.bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getFlag() {
		return this.flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getParentid() {
		return this.parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public Date getYxq() {
		return this.yxq;
	}

	public void setYxq(Date yxq) {
		this.yxq = yxq;
	}

}