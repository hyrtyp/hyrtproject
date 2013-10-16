package com.hyrt.mwpm.vo;

import java.util.Date;

/**
 * MwpmBussEntinfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class MwpmBussEntinfo implements java.io.Serializable {

	// Fields

	private String id;
	private String enrol;
	private String name;
	private String wordname;
	private String member;
	private String address;
	private String post;
	private String enttype;
	private String entpro;
	private String domunit;
	private String djjg;
	private String tel;
	private String entstatus;
	private String appdate;
	private String earcap;
	private String workarea;
	private String createdate;
	private String workstopdate;
	private String hynum;
	private String hytype;
	private String hycode;
	private String dah;
	private String sddjjg;
	private String tz;
	private String createtime;
	private String lastdate;
	private String optionid;
	private String reseauid;
	private String claimstatus;
	private String userid;
	private String lat;
	private String long1;
	private String rname;
	
	private String pid;
	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getPstatus() {
		return pstatus;
	}

	public void setPstatus(String pstatus) {
		this.pstatus = pstatus;
	}

	private String pstatus;
	
	

	// Constructors

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLong1() {
		return long1;
	}

	public void setLong1(String long1) {
		this.long1 = long1;
	}

	/** default constructor */
	public MwpmBussEntinfo() {
	}

	/** minimal constructor */
	public MwpmBussEntinfo(String id) {
		this.id = id;
	}

	/** full constructor */
	public MwpmBussEntinfo(String id, String enrol, String name, String wordname, String member, String address, String post, String enttype, String entpro, String domunit, String djjg, String tel, String entstatus, String appdate, String earcap, String workarea, String createdate, String workstopdate, String hynum, String hytype, String hycode, String dah, String sddjjg, String tz, Date createtime, Date lastdate, String optionid, String reseauid, String claimstatus, String userid) {
		this.id = id;
		this.enrol = enrol;
		this.name = name;
		this.wordname = wordname;
		this.member = member;
		this.address = address;
		this.post = post;
		this.enttype = enttype;
		this.entpro = entpro;
		this.domunit = domunit;
		this.djjg = djjg;
		this.tel = tel;
		this.entstatus = entstatus;
		this.appdate = appdate;
		this.earcap = earcap;
		this.workarea = workarea;
		this.createdate = createdate;
		this.workstopdate = workstopdate;
		this.hynum = hynum;
		this.hytype = hytype;
		this.hycode = hycode;
		this.dah = dah;
		this.sddjjg = sddjjg;
		this.tz = tz;
		this.optionid = optionid;
		this.reseauid = reseauid;
		this.claimstatus = claimstatus;
		this.userid = userid;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEnrol() {
		return this.enrol;
	}

	public void setEnrol(String enrol) {
		this.enrol = enrol;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWordname() {
		return this.wordname;
	}

	public void setWordname(String wordname) {
		this.wordname = wordname;
	}

	public String getMember() {
		return this.member;
	}

	public void setMember(String member) {
		this.member = member;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPost() {
		return this.post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getEnttype() {
		return this.enttype;
	}

	public void setEnttype(String enttype) {
		this.enttype = enttype;
	}

	public String getEntpro() {
		return this.entpro;
	}

	public void setEntpro(String entpro) {
		this.entpro = entpro;
	}

	public String getDomunit() {
		return this.domunit;
	}

	public void setDomunit(String domunit) {
		this.domunit = domunit;
	}

	public String getDjjg() {
		return this.djjg;
	}

	public void setDjjg(String djjg) {
		this.djjg = djjg;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEntstatus() {
		return this.entstatus;
	}

	public void setEntstatus(String entstatus) {
		this.entstatus = entstatus;
	}

	public String getAppdate() {
		return this.appdate;
	}

	public void setAppdate(String appdate) {
		this.appdate = appdate;
	}

	public String getEarcap() {
		return this.earcap;
	}

	public void setEarcap(String earcap) {
		this.earcap = earcap;
	}

	public String getWorkarea() {
		return this.workarea;
	}

	public void setWorkarea(String workarea) {
		this.workarea = workarea;
	}

	public String getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	public String getWorkstopdate() {
		return this.workstopdate;
	}

	public void setWorkstopdate(String workstopdate) {
		this.workstopdate = workstopdate;
	}

	public String getHynum() {
		return this.hynum;
	}

	public void setHynum(String hynum) {
		this.hynum = hynum;
	}

	public String getHytype() {
		return this.hytype;
	}

	public void setHytype(String hytype) {
		this.hytype = hytype;
	}

	public String getHycode() {
		return this.hycode;
	}

	public void setHycode(String hycode) {
		this.hycode = hycode;
	}

	public String getDah() {
		return this.dah;
	}

	public void setDah(String dah) {
		this.dah = dah;
	}

	public String getSddjjg() {
		return this.sddjjg;
	}

	public void setSddjjg(String sddjjg) {
		this.sddjjg = sddjjg;
	}

	public String getTz() {
		return this.tz;
	}

	public void setTz(String tz) {
		this.tz = tz;
	}

	public String getOptionid() {
		return this.optionid;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getLastdate() {
		return lastdate;
	}

	public void setLastdate(String lastdate) {
		this.lastdate = lastdate;
	}

	public void setOptionid(String optionid) {
		this.optionid = optionid;
	}

	public String getReseauid() {
		return this.reseauid;
	}

	public void setReseauid(String reseauid) {
		this.reseauid = reseauid;
	}

	public String getClaimstatus() {
		return this.claimstatus;
	}

	public void setClaimstatus(String claimstatus) {
		this.claimstatus = claimstatus;
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getRname() {
		return rname;
	}

	public void setRname(String rname) {
		this.rname = rname;
	}
	
	

}