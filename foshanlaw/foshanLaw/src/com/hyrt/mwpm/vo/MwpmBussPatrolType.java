package com.hyrt.mwpm.vo;

/**
 * MwpmBussPatrolType entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class MwpmBussPatrolType implements java.io.Serializable {

	// Fields

	private String id;
	private String pid;
	private String ptypename;

	// Constructors

	/** default constructor */
	public MwpmBussPatrolType() {
	}

	/** minimal constructor */
	public MwpmBussPatrolType(String id) {
		this.id = id;
	}

	/** full constructor */
	public MwpmBussPatrolType(String id, String pid, String ptypename) {
		this.id = id;
		this.pid = pid;
		this.ptypename = ptypename;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPid() {
		return this.pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getPtypename() {
		return this.ptypename;
	}

	public void setPtypename(String ptypename) {
		this.ptypename = ptypename;
	}

}