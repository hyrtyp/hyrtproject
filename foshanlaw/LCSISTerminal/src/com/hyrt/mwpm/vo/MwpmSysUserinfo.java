package com.hyrt.mwpm.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.hyrt.mwpm.util.DesUtils;

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
	private String certype;
	private String cardno;
	private String phone;
	private String creator;
	private Date createtime;
	private String userType;
	
	private String status;
	private Integer seqnum;
	private String loginstate;
	private String loginstateM;
	private String level;//队长,股长,
	private String reseauid;//所属网格
	private String lat;
	private String long1;
	
	
	private String logingid;
	private Map functionTreeHash = new HashMap();
	private String loginstate_m;
	private List userGroupList=new ArrayList();
	private List unitList=new ArrayList();
	

	
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

	public List getUserGroupList() {
		return userGroupList;
	}

	public void setUserGroupList(List userGroupList) {
		this.userGroupList = userGroupList;
	}

	public List getUnitList() {
		return unitList;
	}

	public void setUnitList(List unitList) {
		this.unitList = unitList;
	}

	public String getLoginstate_m() {
		return loginstate_m;
	}

	public void setLoginstate_m(String loginstate_m) {
		this.loginstate_m = loginstate_m;
	}

	public String getLogingid() {
		return logingid;
	}

	public void setLogingid(String logingid) {
		this.logingid = logingid;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getReseauid() {
		return reseauid;
	}

	public void setReseauid(String reseauid) {
		this.reseauid = reseauid;
	}

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

	
	public Map getFunctionTreeHash() {
		return functionTreeHash;
	}

	public void setFunctionTreeHash(Map functionTreeHash) {
		this.functionTreeHash = functionTreeHash;
	}

	

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getCertype() {
		return certype;
	}

	public void setCertype(String certype) {
		this.certype = certype;
	}

	

}
