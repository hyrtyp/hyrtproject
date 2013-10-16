package com.hyrt.mwpm.vo;

import java.util.Date;

/**
 * MwpmBussPatrol entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class MwpmBussPatrol implements java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private String createuserid;
	private Date createtime;
	private String period;
	private Date begintime;
	private Date alerttime;
	private Date asktime;
	private String status;

	// Constructors

	/** default constructor */
	public MwpmBussPatrol() {
	}

	/** minimal constructor */
	public MwpmBussPatrol(String id) {
		this.id = id;
	}

	/** full constructor */
	public MwpmBussPatrol(String id, String name, String createuserid, Date createtime, String period, Date begintime, Date alerttime, Date asktime, String status) {
		this.id = id;
		this.name = name;
		this.createuserid = createuserid;
		this.createtime = createtime;
		this.period = period;
		this.begintime = begintime;
		this.alerttime = alerttime;
		this.asktime = asktime;
		this.status = status;
	}

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

	public String getCreateuserid() {
		return this.createuserid;
	}

	public void setCreateuserid(String createuserid) {
		this.createuserid = createuserid;
	}

	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getPeriod() {
		return this.period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public Date getBegintime() {
		return this.begintime;
	}

	public void setBegintime(Date begintime) {
		this.begintime = begintime;
	}

	public Date getAlerttime() {
		return this.alerttime;
	}

	public void setAlerttime(Date alerttime) {
		this.alerttime = alerttime;
	}

	public Date getAsktime() {
		return this.asktime;
	}

	public void setAsktime(Date asktime) {
		this.asktime = asktime;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}