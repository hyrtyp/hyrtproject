package com.hyrt.mwpm.vo;

/**
 * MwpmBussPatrolType entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class MwpmBussPType implements java.io.Serializable {

	// Fields

	private String id;
	private String ptypename;

	// Constructors

	/** default constructor */
	public MwpmBussPType() {
	}

	/** minimal constructor */
	public MwpmBussPType(String id) {
		this.id = id;
	}

	/** full constructor */
	public MwpmBussPType(String id, String ptypename) {
		this.id = id;
		this.ptypename = ptypename;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPtypename() {
		return this.ptypename;
	}

	public void setPtypename(String ptypename) {
		this.ptypename = ptypename;
	}

}