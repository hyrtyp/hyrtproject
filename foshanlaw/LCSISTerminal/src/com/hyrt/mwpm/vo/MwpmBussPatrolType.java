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
	private String tid;
	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getLid() {
		return lid;
	}

	public void setLid(String lid) {
		this.lid = lid;
	}

	private String lid;

	// Constructors

	/** default constructor */
	public MwpmBussPatrolType() {
	}

	/** minimal constructor */
	public MwpmBussPatrolType(String id) {
		this.id = id;
	}

	/** full constructor */
	public MwpmBussPatrolType(String id, String pid, String tid,String lid) {
		this.id = id;
		this.pid = pid;
		this.tid = tid;
		this.lid = lid;
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

}