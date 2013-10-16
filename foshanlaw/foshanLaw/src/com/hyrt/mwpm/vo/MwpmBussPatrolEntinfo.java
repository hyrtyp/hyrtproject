package com.hyrt.mwpm.vo;

/**
 * MwpmBussPatrolEntinfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class MwpmBussPatrolEntinfo implements java.io.Serializable {

	// Fields

	private String id;
	private String pid;
	private String eid;
	private String ischecked;

	// Constructors

	/** default constructor */
	public MwpmBussPatrolEntinfo() {
	}

	/** minimal constructor */
	public MwpmBussPatrolEntinfo(String id) {
		this.id = id;
	}

	/** full constructor */
	public MwpmBussPatrolEntinfo(String id, String pid, String eid) {
		this.id = id;
		this.pid = pid;
		this.eid = eid;
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

	public String getEid() {
		return this.eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public String getIschecked() {
		return ischecked;
	}

	public void setIschecked(String ischecked) {
		this.ischecked = ischecked;
	}

}