package com.hyrt.mwpm.vo;

import java.util.Date;

/**
 * MwpmBussPatrolXy entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class MwpmBussPatrolXy implements java.io.Serializable {

	// Fields

	private String id;
	private String userid;
	private Date remarktime;
	private String lat;
	private String lang;

	// Constructors

	/** default constructor */
	public MwpmBussPatrolXy() {
	}

	/** minimal constructor */
	public MwpmBussPatrolXy(String id) {
		this.id = id;
	}

	/** full constructor */
	public MwpmBussPatrolXy(String id, String userid, Date remarktime, String lat, String lang) {
		this.id = id;
		this.userid = userid;
		this.remarktime = remarktime;
		this.lat = lat;
		this.lang = lang;
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

	public String getLat() {
		return this.lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLang() {
		return this.lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

}