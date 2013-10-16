package com.hyrt.mwpm.vo;

import java.util.Date;

/**
 * MwpmSysBackmes entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class MwpmSysBackmes implements java.io.Serializable {

	// Fields

	private String id;
	private String content;
	private String userid;
	private Date time;

	// Constructors

	/** default constructor */
	public MwpmSysBackmes() {
	}

	/** minimal constructor */
	public MwpmSysBackmes(String id) {
		this.id = id;
	}

	/** full constructor */
	public MwpmSysBackmes(String id, String content, String userid, Date time) {
		this.id = id;
		this.content = content;
		this.userid = userid;
		this.time = time;
	}

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

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public Date getTime() {
		return this.time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

}