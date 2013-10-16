package com.hyrt.mwpm.vo;

import java.util.Date;

/**
 * MwpmBussEntupdate entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class MwpmBussEntupdate implements java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private String enrol;
	private String updatecontent;
	private String operateid;
	private Date createtime;
	private String state;
	private String submitid;

	// Constructors

	/** default constructor */
	public MwpmBussEntupdate() {
	}

	/** minimal constructor */
	public MwpmBussEntupdate(String id) {
		this.id = id;
	}

	/** full constructor */
	public MwpmBussEntupdate(String id, String name, String enrol, String updatecontent, String operateid, Date createtime, String state, String submitid) {
		this.id = id;
		this.name = name;
		this.enrol = enrol;
		this.updatecontent = updatecontent;
		this.operateid = operateid;
		this.createtime = createtime;
		this.state = state;
		this.submitid = submitid;
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

	public String getEnrol() {
		return this.enrol;
	}

	public void setEnrol(String enrol) {
		this.enrol = enrol;
	}

	public String getUpdatecontent() {
		return this.updatecontent;
	}

	public void setUpdatecontent(String updatecontent) {
		this.updatecontent = updatecontent;
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

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getSubmitid() {
		return this.submitid;
	}

	public void setSubmitid(String submitid) {
		this.submitid = submitid;
	}

}