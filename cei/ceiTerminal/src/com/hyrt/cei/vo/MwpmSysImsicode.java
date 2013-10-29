package com.hyrt.cei.vo;

/**
 * MwpmSysImsicode entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class MwpmSysImsicode  implements java.io.Serializable {

	private String id;
	private String imsicode;
	private String userid;
	private String status;
	private String type;

	// Constructors

	

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getImsicode() {
		return this.imsicode;
	}

	public void setImsicode(String imsicode) {
		this.imsicode = imsicode;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
