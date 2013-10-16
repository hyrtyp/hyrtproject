package com.hyrt.mwpm.vo;

import java.util.Date;

/**
 * MwpmBussReturnlog entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class MwpmBussReturnlog implements java.io.Serializable {

	// Fields

	private String id;
	private String lid;
	private String checkthing;
	private String disposetype;
	private Date checktime;

	// Constructors

	/** default constructor */
	public MwpmBussReturnlog() {
	}

	/** minimal constructor */
	public MwpmBussReturnlog(String id) {
		this.id = id;
	}

	/** full constructor */
	public MwpmBussReturnlog(String id, String lid, String checkthing, String disposetype, Date checktime) {
		this.id = id;
		this.lid = lid;
		this.checkthing = checkthing;
		this.disposetype = disposetype;
		this.checktime = checktime;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLid() {
		return this.lid;
	}

	public void setLid(String lid) {
		this.lid = lid;
	}

	public String getCheckthing() {
		return this.checkthing;
	}

	public void setCheckthing(String checkthing) {
		this.checkthing = checkthing;
	}

	public String getDisposetype() {
		return this.disposetype;
	}

	public void setDisposetype(String disposetype) {
		this.disposetype = disposetype;
	}

	public Date getChecktime() {
		return this.checktime;
	}

	public void setChecktime(Date checktime) {
		this.checktime = checktime;
	}

}