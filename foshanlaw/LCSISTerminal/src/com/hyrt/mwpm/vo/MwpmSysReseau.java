package com.hyrt.mwpm.vo;

import java.util.Date;

/**
 * MwpmSysReseau entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class MwpmSysReseau implements java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private String code;
	private String remark;
	private String operateid;
	private Date createtime;
	private String unitid;

	// Constructors

	/** default constructor */
	public MwpmSysReseau() {
	}

	/** minimal constructor */
	public MwpmSysReseau(String id) {
		this.id = id;
	}

	/** full constructor */
	public MwpmSysReseau(String id, String name, String code, String remark, String operateid, Date createtime, String unitid) {
		this.id = id;
		this.name = name;
		this.code = code;
		this.remark = remark;
		this.operateid = operateid;
		this.createtime = createtime;
		this.unitid = unitid;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOperateid() {
		return this.operateid;
	}

	public void setOperateid(String operateid) {
		this.operateid = operateid;
	}

	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getUnitid() {
		return this.unitid;
	}

	public void setUnitid(String unitid) {
		this.unitid = unitid;
	}

}