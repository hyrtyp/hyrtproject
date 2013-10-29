package com.hyrt.cei.vo;

/**
 * MwpmSysSubsystem entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class MwpmSysSubsystem  implements java.io.Serializable {

	private String subsystemid;
	private String loginid;
	private String loginpass;
	private String type;
	private String username;

	// Constructors

	

	// Property accessors

	public String getSubsystemid() {
		return this.subsystemid;
	}

	public void setSubsystemid(String subsystemid) {
		this.subsystemid = subsystemid;
	}

	public String getLoginid() {
		return this.loginid;
	}

	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}

	public String getLoginpass() {
		return this.loginpass;
	}

	public void setLoginpass(String loginpass) {
		this.loginpass = loginpass;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


}
