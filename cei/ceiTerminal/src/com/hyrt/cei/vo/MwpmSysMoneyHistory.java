package com.hyrt.cei.vo;

import java.util.Date;

/**
 * MwpmSysMoneyHistory entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class MwpmSysMoneyHistory  implements java.io.Serializable {

	private String id;
	private String name;
	private String buytype;
	private String teachername;
	private String resourcetype;
	private Date buytime;
	private Integer buylong;
	private Integer price;
	private String fld1;
	private String fld2;
	private Date creattime;
	private String orderformid;

	

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBuytype() {
		return this.buytype;
	}

	public void setBuytype(String buytype) {
		this.buytype = buytype;
	}

	public String getTeachername() {
		return this.teachername;
	}

	public void setTeachername(String teachername) {
		this.teachername = teachername;
	}

	public String getResourcetype() {
		return this.resourcetype;
	}

	public void setResourcetype(String resourcetype) {
		this.resourcetype = resourcetype;
	}

	public Date getBuytime() {
		return this.buytime;
	}

	public void setBuytime(Date buytime) {
		this.buytime = buytime;
	}

	public Integer getBuylong() {
		return this.buylong;
	}

	public void setBuylong(Integer buylong) {
		this.buylong = buylong;
	}

	public Integer getPrice() {
		return this.price;
	}

	public void setPrice(Integer price) {
		this.price = price;
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

	public Date getCreattime() {
		return this.creattime;
	}

	public void setCreattime(Date creattime) {
		this.creattime = creattime;
	}

	public String getOrderformid() {
		return this.orderformid;
	}

	public void setOrderformid(String orderformid) {
		this.orderformid = orderformid;
	}

}
