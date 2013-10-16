package com.hyrt.mwpm.vo;

import java.util.Date;

/**
 * MwpmBussPatrolProof entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class MwpmBussPatrolProof implements java.io.Serializable {

	// Fields

	private String id;
	private String lid;
	private String path;
	private String userid;
	private Date clock;
	private String remark;

	// Constructors

	/** default constructor */
	public MwpmBussPatrolProof() {
	}

	/** minimal constructor */
	public MwpmBussPatrolProof(String id) {
		this.id = id;
	}

	/** full constructor */
	public MwpmBussPatrolProof(String id, String lid, String path, String userid, Date clock, String remark) {
		this.id = id;
		this.lid = lid;
		this.path = path;
		this.userid = userid;
		this.clock = clock;
		this.remark = remark;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLid() {
		return this.lid;
	}

	public void setLid(String lid) {
		this.lid = lid;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public Date getClock() {
		return this.clock;
	}

	public void setClock(Date clock) {
		this.clock = clock;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}