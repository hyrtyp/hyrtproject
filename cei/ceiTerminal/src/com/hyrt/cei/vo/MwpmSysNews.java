package com.hyrt.cei.vo;

import java.util.Date;

/**
 * MwpmSysNews entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class MwpmSysNews  implements java.io.Serializable {

	private String id;
	private String title;
	private String subhead;
	private Date time;
	private String author;
	private String teachername;
	private String write;
	private String releasenum;
	private String classkey;
	private String isfree;
	private String ppath;
	private Integer downsum;
	private String status;
	private Date creattime;
	private Date issuetime;
	private String fld1;
	private String fld2;

	

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubhead() {
		return this.subhead;
	}

	public void setSubhead(String subhead) {
		this.subhead = subhead;
	}

	public Date getTime() {
		return this.time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTeachername() {
		return this.teachername;
	}

	public void setTeachername(String teachername) {
		this.teachername = teachername;
	}

	public String getWrite() {
		return this.write;
	}

	public void setWrite(String write) {
		this.write = write;
	}

	public String getReleasenum() {
		return this.releasenum;
	}

	public void setReleasenum(String releasenum) {
		this.releasenum = releasenum;
	}

	public String getClasskey() {
		return this.classkey;
	}

	public void setClasskey(String classkey) {
		this.classkey = classkey;
	}

	public String getIsfree() {
		return this.isfree;
	}

	public void setIsfree(String isfree) {
		this.isfree = isfree;
	}

	public String getPpath() {
		return this.ppath;
	}

	public void setPpath(String ppath) {
		this.ppath = ppath;
	}

	public Integer getDownsum() {
		return this.downsum;
	}

	public void setDownsum(Integer downsum) {
		this.downsum = downsum;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreattime() {
		return this.creattime;
	}

	public void setCreattime(Date creattime) {
		this.creattime = creattime;
	}

	public Date getIssuetime() {
		return this.issuetime;
	}

	public void setIssuetime(Date issuetime) {
		this.issuetime = issuetime;
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


}
