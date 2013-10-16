package com.hyrt.mwpm.vo;

/**
 * MwpmSysPhoneIpad entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class MwpmSysPhoneIpad implements java.io.Serializable {

	// Fields

	private String id;
	private String userid;
	private String type;
	private String code;
	private String status;

	// Constructors

	/** default constructor */
	public MwpmSysPhoneIpad() {
	}

	/** minimal constructor */
	public MwpmSysPhoneIpad(String id) {
		this.id = id;
	}

	/** full constructor */
	public MwpmSysPhoneIpad(String id, String userid, String type, String code, String status) {
		this.id = id;
		this.userid = userid;
		this.type = type;
		this.code = code;
		this.status = status;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}