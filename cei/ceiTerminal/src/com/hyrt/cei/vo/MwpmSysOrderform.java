package com.hyrt.cei.vo;

import java.util.Date;

/**
 * MwpmSysOrderform entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class MwpmSysOrderform implements java.io.Serializable {

	private String id;
	private String num;
	private String userid;
	private Date buytime;
	private Integer money;
	private String mode;
	private String thirdnum;
	private String state;
	private String fld1;
	private String fld2;

	// Constructors

	

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNum() {
		return this.num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public Date getBuytime() {
		return this.buytime;
	}

	public void setBuytime(Date buytime) {
		this.buytime = buytime;
	}

	public Integer getMoney() {
		return this.money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}

	public String getMode() {
		return this.mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getThirdnum() {
		return this.thirdnum;
	}

	public void setThirdnum(String thirdnum) {
		this.thirdnum = thirdnum;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
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

}
