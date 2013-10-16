package com.hyrt.mwpm.vo;

/**
 * MwpmSysUnituser entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class MwpmSysUnituser implements java.io.Serializable {

	// Fields

	private String id;
	private String unitid;
	private String userid;

	// Constructors

	/** default constructor */
	public MwpmSysUnituser() {
	}

	/** minimal constructor */
	public MwpmSysUnituser(String id) {
		this.id = id;
	}

	/** full constructor */
	public MwpmSysUnituser(String id, String unitid, String userid) {
		this.id = id;
		this.unitid = unitid;
		this.userid = userid;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUnitid() {
		return this.unitid;
	}

	public void setUnitid(String unitid) {
		this.unitid = unitid;
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

}