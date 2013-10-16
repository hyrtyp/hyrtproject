package com.hyrt.mwpm.vo;

import java.util.Date;


public class Caseinfo  implements java.io.Serializable {

	private int id;
	private int taskid;
	private String casename;
	private String casetype;
	private String weifa;
	private String lianren;
	private String casestatus;
	private String iscompul;
	private Date compultime;
	private String isdelay;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTaskid() {
		return taskid;
	}
	public void setTaskid(int taskid) {
		this.taskid = taskid;
	}
	public String getCasename() {
		return casename;
	}
	public void setCasename(String casename) {
		this.casename = casename;
	}
	public String getCasetype() {
		return casetype;
	}
	public void setCasetype(String casetype) {
		this.casetype = casetype;
	}
	public String getWeifa() {
		return weifa;
	}
	public void setWeifa(String weifa) {
		this.weifa = weifa;
	}
	public String getLianren() {
		return lianren;
	}
	public void setLianren(String lianren) {
		this.lianren = lianren;
	}
	public String getCasestatus() {
		return casestatus;
	}
	public void setCasestatus(String casestatus) {
		this.casestatus = casestatus;
	}
	public String getIscompul() {
		return iscompul;
	}
	public void setIscompul(String iscompul) {
		this.iscompul = iscompul;
	}
	public Date getCompultime() {
		return compultime;
	}
	public void setCompultime(Date compultime) {
		this.compultime = compultime;
	}
	public String getIsdelay() {
		return isdelay;
	}
	public void setIsdelay(String isdelay) {
		this.isdelay = isdelay;
	}

}
