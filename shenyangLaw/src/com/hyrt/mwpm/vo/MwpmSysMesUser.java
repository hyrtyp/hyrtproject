package com.hyrt.mwpm.vo;

/**
 * MwpmSysMesUser entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class MwpmSysMesUser implements java.io.Serializable {

	// Fields

	private String id;
	private String mid;
	private String userid;
	private String type;

	// Constructors

	/** default constructor */
	public MwpmSysMesUser() {
	}

	/** minimal constructor */
	public MwpmSysMesUser(String id) {
		this.id = id;
	}

	/** full constructor */
	public MwpmSysMesUser(String id, String mid, String userid, String type) {
		this.id = id;
		this.mid = mid;
		this.userid = userid;
		this.type = type;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMid() {
		return this.mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

}