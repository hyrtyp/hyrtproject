package com.hyrt.mwpm.vo;

import java.util.Date;

/**
 * MwpmBussCaseRoom entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class MwpmBussCaseRoom implements java.io.Serializable {

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
	private String roomsum;
	private Date punishtime;
	private String forfeit;
	private String roomtype;

	// Constructors

	/** default constructor */
	public MwpmBussCaseRoom() {
	}

	/** minimal constructor */
	public MwpmBussCaseRoom(String id) {
		this.id = id;
	}

	/** full constructor */
	public MwpmBussCaseRoom(String id, String eid, String checkuserid, String submituserid, Date checksignintime, String checkremark, String checkresult, Date checktime, String proresstype, String roomsum, Date punishtime, String forfeit, String roomtype) {
		this.id = id;
		this.eid = eid;
		this.checkuserid = checkuserid;
		this.submituserid = submituserid;
		this.checksignintime = checksignintime;
		this.checkremark = checkremark;
		this.checkresult = checkresult;
		this.checktime = checktime;
		this.proresstype = proresstype;
		this.roomsum = roomsum;
		this.punishtime = punishtime;
		this.forfeit = forfeit;
		this.roomtype = roomtype;
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

	public String getRoomsum() {
		return this.roomsum;
	}

	public void setRoomsum(String roomsum) {
		this.roomsum = roomsum;
	}

	public Date getPunishtime() {
		return this.punishtime;
	}

	public void setPunishtime(Date punishtime) {
		this.punishtime = punishtime;
	}

	public String getForfeit() {
		return this.forfeit;
	}

	public void setForfeit(String forfeit) {
		this.forfeit = forfeit;
	}

	public String getRoomtype() {
		return this.roomtype;
	}

	public void setRoomtype(String roomtype) {
		this.roomtype = roomtype;
	}

}