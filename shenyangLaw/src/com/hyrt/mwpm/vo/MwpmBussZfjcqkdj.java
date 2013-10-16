package com.hyrt.mwpm.vo;

import java.util.Date;

/**
 * MwpmBussZfjcqkdj entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class MwpmBussZfjcqkdj implements java.io.Serializable {

	// Fields

	private String id;
	private String userid;
	private String remarktime;
	private String mainid;
	private String code;
	private String jcdw;
	private String jcry;
	private Date jcsj;
	private String jcqk;
	private String clyj;
	private String wgzj;
	private String xcfzr;
	private String lxdh;
	private Date qzrq;

	// Constructors

	/** default constructor */
	public MwpmBussZfjcqkdj() {
	}

	/** minimal constructor */
	public MwpmBussZfjcqkdj(String id) {
		this.id = id;
	}

	/** full constructor */
	public MwpmBussZfjcqkdj(String id, String userid, String remarktime, String mainid, String code, String jcdw, String jcry, Date jcsj, String jcqk, String clyj, String wgzj, String xcfzr, String lxdh, Date qzrq) {
		this.id = id;
		this.userid = userid;
		this.remarktime = remarktime;
		this.mainid = mainid;
		this.code = code;
		this.jcdw = jcdw;
		this.jcry = jcry;
		this.jcsj = jcsj;
		this.jcqk = jcqk;
		this.clyj = clyj;
		this.wgzj = wgzj;
		this.xcfzr = xcfzr;
		this.lxdh = lxdh;
		this.qzrq = qzrq;
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

	public String getRemarktime() {
		return this.remarktime;
	}

	public void setRemarktime(String remarktime) {
		this.remarktime = remarktime;
	}

	public String getMainid() {
		return this.mainid;
	}

	public void setMainid(String mainid) {
		this.mainid = mainid;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getJcdw() {
		return this.jcdw;
	}

	public void setJcdw(String jcdw) {
		this.jcdw = jcdw;
	}

	public String getJcry() {
		return this.jcry;
	}

	public void setJcry(String jcry) {
		this.jcry = jcry;
	}

	public Date getJcsj() {
		return this.jcsj;
	}

	public void setJcsj(Date jcsj) {
		this.jcsj = jcsj;
	}

	public String getJcqk() {
		return this.jcqk;
	}

	public void setJcqk(String jcqk) {
		this.jcqk = jcqk;
	}

	public String getClyj() {
		return this.clyj;
	}

	public void setClyj(String clyj) {
		this.clyj = clyj;
	}

	public String getWgzj() {
		return this.wgzj;
	}

	public void setWgzj(String wgzj) {
		this.wgzj = wgzj;
	}

	public String getXcfzr() {
		return this.xcfzr;
	}

	public void setXcfzr(String xcfzr) {
		this.xcfzr = xcfzr;
	}

	public String getLxdh() {
		return this.lxdh;
	}

	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
	}

	public Date getQzrq() {
		return this.qzrq;
	}

	public void setQzrq(Date qzrq) {
		this.qzrq = qzrq;
	}

}