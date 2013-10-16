package com.hyrt.mwpm.vo;

import java.util.Date;

/**
 * MwpmSysMes entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class MwpmSysMes implements java.io.Serializable {

	// Fields

	private String id;
	private String title;
	private String content;
	private String userid;
	private String createtime;
	private String type;
	private String createtimes;
	private String createtimee;
	

	// Constructors

	public String getCreatetimes() {
		return createtimes;
	}

	public void setCreatetimes(String createtimes) {
		this.createtimes = createtimes;
	}

	public String getCreatetimee() {
		return createtimee;
	}

	public void setCreatetimee(String createtimee) {
		this.createtimee = createtimee;
	}

	/** default constructor */
	public MwpmSysMes() {
	}

	/** minimal constructor */
	public MwpmSysMes(String id) {
		this.id = id;
	}

	/** full constructor */
	public MwpmSysMes(String id, String title, String content, String userid, String createtime, String type) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.userid = userid;
		this.createtime = createtime;
		this.type = type;
	}

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

	public String getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

}