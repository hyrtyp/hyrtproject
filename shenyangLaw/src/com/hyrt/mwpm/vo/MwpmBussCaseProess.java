package com.hyrt.mwpm.vo;

import java.util.Date;

/**
 * MwpmBussCaseProess entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class MwpmBussCaseProess implements java.io.Serializable {

	// Fields

	private String id;
	private String eid;
	private String checkuserid;
	private String submituserid;
	private Date checksignintime;
	private String checkremark;
	private String checkresult;
	private Date checktime;
	private String proresstype;

	// Constructors

	/** default constructor */
	public MwpmBussCaseProess() {
	}

	/** minimal constructor */
	public MwpmBussCaseProess(String id) {
		this.id = id;
	}

	/** full constructor */
	public MwpmBussCaseProess(String id, String eid, String checkuserid, String submituserid, Date checksignintime, String checkremark, String checkresult, Date checktime, String proresstype) {
		this.id = id;
		this.eid = eid;
		this.checkuserid = checkuserid;
		this.submituserid = submituserid;
		this.checksignintime = checksignintime;
		this.checkremark = checkremark;
		this.checkresult = checkresult;
		this.checktime = checktime;
		this.proresstype = proresstype;
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

	public String getCheckuserid() {
		return this.checkuserid;
	}

	public void setCheckuserid(String checkuserid) {
		this.checkuserid = checkuserid;
	}

	public String getSubmituserid() {
		return this.submituserid;
	}

	public void setSubmituserid(String submituserid) {
		this.submituserid = submituserid;
	}

	public Date getChecksignintime() {
		return this.checksignintime;
	}

	public void setChecksignintime(Date checksignintime) {
		this.checksignintime = checksignintime;
	}

	public String getCheckremark() {
		return this.checkremark;
	}

	public void setCheckremark(String checkremark) {
		this.checkremark = checkremark;
	}

	public String getCheckresult() {
		return this.checkresult;
	}

	public void setCheckresult(String checkresult) {
		this.checkresult = checkresult;
	}

	public Date getChecktime() {
		return this.checktime;
	}

	public void setChecktime(Date checktime) {
		this.checktime = checktime;
	}

	public String getProresstype() {
		return this.proresstype;
	}

	public void setProresstype(String proresstype) {
		this.proresstype = proresstype;
	}

}