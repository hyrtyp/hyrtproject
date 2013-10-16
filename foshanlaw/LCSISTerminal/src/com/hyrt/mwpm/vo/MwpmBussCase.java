package com.hyrt.mwpm.vo;

import java.util.Date;

/**
 * MwpmBussCase entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class MwpmBussCase implements java.io.Serializable {

	// Fields

	private String id;
	private String eid;
	private String name;
	private String submituserid;
	private String submitunitid;
	private Date createtime;
	private String description;
	private String jointuserid;
	private String idea;
	private String status;
	private String main;
	private Date registertime;
	private String property;
	private Date advclosetime;
	private Date punishtime;
	private Date forcesteptime;
	private Date fdefertime;
	private Date casedefertime;
	private Date advtime;
	private String casesource;
	private String baseinfo;
	
	

	// Constructors

	public String getCasesource() {
		return casesource;
	}

	public void setCasesource(String casesource) {
		this.casesource = casesource;
	}

	public String getBaseinfo() {
		return baseinfo;
	}

	public void setBaseinfo(String baseinfo) {
		this.baseinfo = baseinfo;
	}

	/** default constructor */
	public MwpmBussCase() {
	}

	/** minimal constructor */
	public MwpmBussCase(String id) {
		this.id = id;
	}

	/** full constructor */
	public MwpmBussCase(String id, String eid, String name, String submituserid, String submitunitid, Date createtime, String description, String jointuserid, String idea, String status, String main, Date registertime, String property, Date advclosetime, Date punishtime, Date forcesteptime, Date fdefertime, Date casedefertime, Date advtime) {
		this.id = id;
		this.eid = eid;
		this.name = name;
		this.submituserid = submituserid;
		this.submitunitid = submitunitid;
		this.createtime = createtime;
		this.description = description;
		this.jointuserid = jointuserid;
		this.idea = idea;
		this.status = status;
		this.main = main;
		this.registertime = registertime;
		this.property = property;
		this.advclosetime = advclosetime;
		this.punishtime = punishtime;
		this.forcesteptime = forcesteptime;
		this.fdefertime = fdefertime;
		this.casedefertime = casedefertime;
		this.advtime = advtime;
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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSubmituserid() {
		return this.submituserid;
	}

	public void setSubmituserid(String submituserid) {
		this.submituserid = submituserid;
	}

	public String getSubmitunitid() {
		return this.submitunitid;
	}

	public void setSubmitunitid(String submitunitid) {
		this.submitunitid = submitunitid;
	}

	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getJointuserid() {
		return this.jointuserid;
	}

	public void setJointuserid(String jointuserid) {
		this.jointuserid = jointuserid;
	}

	public String getIdea() {
		return this.idea;
	}

	public void setIdea(String idea) {
		this.idea = idea;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMain() {
		return this.main;
	}

	public void setMain(String main) {
		this.main = main;
	}

	public Date getRegistertime() {
		return this.registertime;
	}

	public void setRegistertime(Date registertime) {
		this.registertime = registertime;
	}

	public String getProperty() {
		return this.property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public Date getAdvclosetime() {
		return this.advclosetime;
	}

	public void setAdvclosetime(Date advclosetime) {
		this.advclosetime = advclosetime;
	}

	public Date getPunishtime() {
		return this.punishtime;
	}

	public void setPunishtime(Date punishtime) {
		this.punishtime = punishtime;
	}

	public Date getForcesteptime() {
		return this.forcesteptime;
	}

	public void setForcesteptime(Date forcesteptime) {
		this.forcesteptime = forcesteptime;
	}

	public Date getFdefertime() {
		return this.fdefertime;
	}

	public void setFdefertime(Date fdefertime) {
		this.fdefertime = fdefertime;
	}

	public Date getCasedefertime() {
		return this.casedefertime;
	}

	public void setCasedefertime(Date casedefertime) {
		this.casedefertime = casedefertime;
	}

	public Date getAdvtime() {
		return this.advtime;
	}

	public void setAdvtime(Date advtime) {
		this.advtime = advtime;
	}

}