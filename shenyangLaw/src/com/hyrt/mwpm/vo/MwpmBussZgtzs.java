package com.hyrt.mwpm.vo;

import java.util.Date;

/**
 * MwpmBussZgtzs entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class MwpmBussZgtzs implements java.io.Serializable {

	// Fields

	private String id;
	private String userid;
	private Date remarktime;
	private String mainid;
	private String content;
	private String seconduserid;

	// Constructors

	/** default constructor */
	public MwpmBussZgtzs() {
	}

	/** minimal constructor */
	public MwpmBussZgtzs(String id) {
		this.id = id;
	}

	/** full constructor */
	public MwpmBussZgtzs(String id, String userid, Date remarktime, String mainid, String content, String seconduserid) {
		this.id = id;
		this.userid = userid;
		this.remarktime = remarktime;
		this.mainid = mainid;
		this.content = content;
		this.seconduserid = seconduserid;
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

	public Date getRemarktime() {
		return this.remarktime;
	}

	public void setRemarktime(Date remarktime) {
		this.remarktime = remarktime;
	}

	public String getMainid() {
		return this.mainid;
	}

	public void setMainid(String mainid) {
		this.mainid = mainid;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSeconduserid() {
		return this.seconduserid;
	}

	public void setSeconduserid(String seconduserid) {
		this.seconduserid = seconduserid;
	}

}