package com.hyrt.cei.vo;

import java.util.Date;

/**
 * MwpmPortalIndex entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class MwpmPortalIndex  implements java.io.Serializable {
	private String id;
	private String name;
	private String path;
	private String domain;
	private String appdomain;
	private String functionid;
	private String status;
	private Date creatdate;
	private String userid;
	private String fld1;
	private String fld2;
	private String tname;

	

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getDomain() {
		return this.domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getAppdomain() {
		return this.appdomain;
	}

	public void setAppdomain(String appdomain) {
		this.appdomain = appdomain;
	}

	public String getFunctionid() {
		return this.functionid;
	}

	public void setFunctionid(String functionid) {
		this.functionid = functionid;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreatdate() {
		return this.creatdate;
	}

	public void setCreatdate(Date creatdate) {
		this.creatdate = creatdate;
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getFld1() {
		return this.fld1;
	}

	public void setFld1(String fld1) {
		this.fld1 = fld1;
	}

	public String getFld2() {
		return this.fld2;
	}

	public void setFld2(String fld2) {
		this.fld2 = fld2;
	}

	public String getTname() {
		return this.tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}
}
