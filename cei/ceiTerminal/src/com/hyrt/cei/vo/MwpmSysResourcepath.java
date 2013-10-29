package com.hyrt.cei.vo;

import java.util.Date;

/**
 * MwpmSysResourcepath entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class MwpmSysResourcepath implements java.io.Serializable {

	// Fields

	private String id;
	private String resourceid;
	private String path;
	private String classtype;
	private String fld1;
	private String passkey;
	private String type;
	private String status;
	private Date time;

	// Constructors

	/** default constructor */
	public MwpmSysResourcepath() {
	}

	/** minimal constructor */
	public MwpmSysResourcepath(String id) {
		this.id = id;
	}

	/** full constructor */
	public MwpmSysResourcepath(String id, String resourceid, String path, String classtype, String fld1, String passkey, String type, String status, Date time) {
		this.id = id;
		this.resourceid = resourceid;
		this.path = path;
		this.classtype = classtype;
		this.fld1 = fld1;
		this.passkey = passkey;
		this.type = type;
		this.status = status;
		this.time = time;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getResourceid() {
		return this.resourceid;
	}

	public void setResourceid(String resourceid) {
		this.resourceid = resourceid;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getClasstype() {
		return this.classtype;
	}

	public void setClasstype(String classtype) {
		this.classtype = classtype;
	}

	public String getFld1() {
		return this.fld1;
	}

	public void setFld1(String fld1) {
		this.fld1 = fld1;
	}

	public String getPasskey() {
		return this.passkey;
	}

	public void setPasskey(String passkey) {
		this.passkey = passkey;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getTime() {
		return this.time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

}