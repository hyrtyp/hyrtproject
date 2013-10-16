package com.hyrt.mwpm.vo;

import java.util.Date;

/**
 * MwpmBussNocard entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class MwpmBussNocard implements java.io.Serializable {

	// Fields

	private String id;
	private String title;
	private String operator;
	private String birthplace;
	private String idcard;
	private String phone;
	private String address;
	private String type;
	private String item;
	private String result;
	private String submitid;
	private Date submittime;

	private String reseauid;
	// Constructors

	public String getReseauid() {
		return reseauid;
	}

	public void setReseauid(String reseauid) {
		this.reseauid = reseauid;
	}

	/** default constructor */
	public MwpmBussNocard() {
	}

	/** minimal constructor */
	public MwpmBussNocard(String id) {
		this.id = id;
	}

	/** full constructor */
	public MwpmBussNocard(String id, String title, String operator, String birthplace, String idcard, String phone, String address, String type, String item, String result, String submitid, Date submittime) {
		this.id = id;
		this.title = title;
		this.operator = operator;
		this.birthplace = birthplace;
		this.idcard = idcard;
		this.phone = phone;
		this.address = address;
		this.type = type;
		this.item = item;
		this.result = result;
		this.submitid = submitid;
		this.submittime = submittime;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getBirthplace() {
		return this.birthplace;
	}

	public void setBirthplace(String birthplace) {
		this.birthplace = birthplace;
	}

	public String getIdcard() {
		return this.idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getItem() {
		return this.item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getResult() {
		return this.result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getSubmitid() {
		return this.submitid;
	}

	public void setSubmitid(String submitid) {
		this.submitid = submitid;
	}

	public Date getSubmittime() {
		return this.submittime;
	}

	public void setSubmittime(Date submittime) {
		this.submittime = submittime;
	}

}