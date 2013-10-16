package com.hyrt.mwpm.vo;

/**
 * MwpmSysOrguser entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class MwpmSysOrguser implements java.io.Serializable {

	// Fields

	private String id;
	private String orgid;
	private String userid;

	// Constructors

	/** default constructor */
	public MwpmSysOrguser() {
	}

	/** minimal constructor */
	public MwpmSysOrguser(String id) {
		this.id = id;
	}

	/** full constructor */
	public MwpmSysOrguser(String id, String orgid, String userid) {
		this.id = id;
		this.orgid = orgid;
		this.userid = userid;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrgid() {
		return this.orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

}