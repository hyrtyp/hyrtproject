package com.hyrt.mwpm.vo;

import java.util.Date;

/**
 * MwpmBussCaseLetter entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class MwpmBussCaseLetter implements java.io.Serializable {

	// Fields

	private String id;
	private String eid;
	private String type;
	private String lettercode;
	private String sendtype;
	private Date date;
	private Date sendtime;

	// Constructors

	/** default constructor */
	public MwpmBussCaseLetter() {
	}

	/** minimal constructor */
	public MwpmBussCaseLetter(String id) {
		this.id = id;
	}

	/** full constructor */
	public MwpmBussCaseLetter(String id, String eid, String type, String lettercode, String sendtype, Date date, Date sendtime) {
		this.id = id;
		this.eid = eid;
		this.type = type;
		this.lettercode = lettercode;
		this.sendtype = sendtype;
		this.date = date;
		this.sendtime = sendtime;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEid() {
		return this.eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLettercode() {
		return this.lettercode;
	}

	public void setLettercode(String lettercode) {
		this.lettercode = lettercode;
	}

	public String getSendtype() {
		return this.sendtype;
	}

	public void setSendtype(String sendtype) {
		this.sendtype = sendtype;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getSendtime() {
		return this.sendtime;
	}

	public void setSendtime(Date sendtime) {
		this.sendtime = sendtime;
	}

}