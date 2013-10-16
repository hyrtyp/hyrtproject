package com.hyrt.mwpm.vo;

import java.util.Date;

/**
 * MwpmBussPatrolLog entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class MwpmBussPatrolLog implements java.io.Serializable {

	// Fields

	private String id;
	private String entid;
	private String nid;
	private String content;
	private String userid;
	private Date clock;
	private String type;
	private String area;
	private String remark;
	private Date term;
	private String isrecheck;
	private String rid;
	private String handman;
	
	

	public String getHandman() {
		return handman;
	}

	public void setHandman(String handman) {
		this.handman = handman;
	}

	// Constructors
	public String getNid() {
		return nid;
	}

	public void setNid(String nid) {
		this.nid = nid;
	}

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public String getIsrecheck() {
		return isrecheck;
	}

	public void setIsrecheck(String isrecheck) {
		this.isrecheck = isrecheck;
	}

	/** default constructor */
	public MwpmBussPatrolLog() {
	}

	/** minimal constructor */
	public MwpmBussPatrolLog(String id) {
		this.id = id;
	}

	/** full constructor */
	public MwpmBussPatrolLog(String id, String entid, String content, String userid, Date clock, String type, String area, String remark, Date term) {
		this.id = id;
		this.entid = entid;
		this.content = content;
		this.userid = userid;
		this.clock = clock;
		this.type = type;
		this.area = area;
		this.remark = remark;
		this.term = term;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEntid() {
		return this.entid;
	}

	public void setEntid(String entid) {
		this.entid = entid;
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

	public Date getClock() {
		return this.clock;
	}

	public void setClock(Date clock) {
		this.clock = clock;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getArea() {
		return this.area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getTerm() {
		return this.term;
	}

	public void setTerm(Date term) {
		this.term = term;
	}

}