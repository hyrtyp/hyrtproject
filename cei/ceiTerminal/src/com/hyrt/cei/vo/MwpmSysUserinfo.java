package com.hyrt.cei.vo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * MwpmSysUserinfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class MwpmSysUserinfo implements java.io.Serializable {

	private String userid;
	private String loginname;
	private String password;
	private String name;
	private String sex;
	private String email;
	private String dutyid;
	private String cardno;
	private String phone;
	private String creator;
	private Date createtime;
	private String usertype;
	private String unitid;
	private String status;
	private Integer seqnum;
	private String loginstate;
	private String loginstateM;
	private String unitname;
	private String otheruserid;
	private String fld1;
	private String fld2;
	private String fld3;
	private Map functionTreeHash = new HashMap();
    private String logingid;
    private String loginstate_m;
    private String functionid;
    private String certype;
    private Date firstlogintime ;
	// Constructors
    
	

	// Property accessors

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getLoginname() {
		return this.loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDutyid() {
		return this.dutyid;
	}

	public void setDutyid(String dutyid) {
		this.dutyid = dutyid;
	}

	public String getCardno() {
		return this.cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getUsertype() {
		return this.usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public String getUnitid() {
		return this.unitid;
	}

	public void setUnitid(String unitid) {
		this.unitid = unitid;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getSeqnum() {
		return this.seqnum;
	}

	public void setSeqnum(Integer seqnum) {
		this.seqnum = seqnum;
	}

	public String getLoginstate() {
		return this.loginstate;
	}

	public void setLoginstate(String loginstate) {
		this.loginstate = loginstate;
	}

	public String getLoginstateM() {
		return this.loginstateM;
	}

	public void setLoginstateM(String loginstateM) {
		this.loginstateM = loginstateM;
	}

	public String getUnitname() {
		return this.unitname;
	}

	public void setUnitname(String unitname) {
		this.unitname = unitname;
	}

	public String getOtheruserid() {
		return this.otheruserid;
	}

	public void setOtheruserid(String otheruserid) {
		this.otheruserid = otheruserid;
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

	public String getFld3() {
		return this.fld3;
	}

	public void setFld3(String fld3) {
		this.fld3 = fld3;
	}

	public Map getFunctionTreeHash() {
		return functionTreeHash;
	}

	public void setFunctionTreeHash(Map functionTreeHash) {
		this.functionTreeHash = functionTreeHash;
	}

	public String getLogingid() {
		return logingid;
	}

	public void setLogingid(String logingid) {
		this.logingid = logingid;
	}

	public String getLoginstate_m() {
		return loginstate_m;
	}

	public void setLoginstate_m(String loginstate_m) {
		this.loginstate_m = loginstate_m;
	}

	public String getFunctionid() {
		return functionid;
	}

	public void setFunctionid(String functionid) {
		this.functionid = functionid;
	}

	public String getCertype() {
		return certype;
	}

	public void setCertype(String certype) {
		this.certype = certype;
	}

	public Date getFirstlogintime() {
		return firstlogintime;
	}

	public void setFirstlogintime(Date firstlogintime) {
		this.firstlogintime = firstlogintime;
	}


}
