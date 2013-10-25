package com.hyrt.cei.vo;

import java.util.Date;

/**
 * MwpmSysForumFollow entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class MwpmSysForumFollow  implements java.io.Serializable {

	private String id;
	private String functionid;
	private String content;
	private Date time;
	private String userid;
	private int serial;
	private String fld1;
	private String fld2;
	// Constructors

	
	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getTime() {
		return this.time;
	}

	public void setTime(Date time) {
		this.time = time;
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

	public String getFunctionid() {
		return functionid;
	}

	public void setFunctionid(String functionid) {
		this.functionid = functionid;
	}

	public int getSerial() {
		return serial;
	}

	public void setSerial(int serial) {
		this.serial = serial;
	}
	
}
