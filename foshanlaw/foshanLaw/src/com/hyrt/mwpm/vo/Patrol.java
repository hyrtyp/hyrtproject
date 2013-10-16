package com.hyrt.mwpm.vo;

import java.util.Date;


public class Patrol  implements java.io.Serializable {

	private int id;
	private String registId;
	private String company;
	private String contact;
	private String phone;
	private String majorproject;
	private String monitoringarea;
	private String checktype;
	private String checkproject1;
	private String checkproject2;
	private String checkproject3;
	private String checkproject4;
	private String checkproject5;
	private String checkproject6;
	private String note;
	private Date creattime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRegistId() {
		return registId;
	}
	public void setRegistId(String registId) {
		this.registId = registId;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMajorproject() {
		return majorproject;
	}
	public void setMajorproject(String majorproject) {
		this.majorproject = majorproject;
	}
	public String getMonitoringarea() {
		return monitoringarea;
	}
	public void setMonitoringarea(String monitoringarea) {
		this.monitoringarea = monitoringarea;
	}
	public String getChecktype() {
		return checktype;
	}
	public void setChecktype(String checktype) {
		this.checktype = checktype;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getCheckproject1() {
		return checkproject1;
	}
	public void setCheckproject1(String checkproject1) {
		this.checkproject1 = checkproject1;
	}
	public String getCheckproject2() {
		return checkproject2;
	}
	public void setCheckproject2(String checkproject2) {
		this.checkproject2 = checkproject2;
	}
	public String getCheckproject3() {
		return checkproject3;
	}
	public void setCheckproject3(String checkproject3) {
		this.checkproject3 = checkproject3;
	}
	public String getCheckproject4() {
		return checkproject4;
	}
	public void setCheckproject4(String checkproject4) {
		this.checkproject4 = checkproject4;
	}
	public String getCheckproject5() {
		return checkproject5;
	}
	public void setCheckproject5(String checkproject5) {
		this.checkproject5 = checkproject5;
	}
	public String getCheckproject6() {
		return checkproject6;
	}
	public void setCheckproject6(String checkproject6) {
		this.checkproject6 = checkproject6;
	}
	public Date getCreattime() {
		return creattime;
	}
	public void setCreattime(Date creattime) {
		this.creattime = creattime;
	}
	

}
