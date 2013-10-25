package com.hyrt.mwpm.vo;

import java.util.Date;

/**
 * OaRelease entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class OaRelease implements java.io.Serializable {

	// Fields

	private String id;
	private String workId;
	private String taskId;
	private String valueTitle;
	private String valueNumber;
	private String valueOrgans;
	private String valueDate;
	private String valueSpeed;
	private Byte display;
	private Byte success;
	private String userList;
	private String owner;
	private String ownerAccount;
	private Date date;
	private String html;
	private Byte isReply;
	private Date expect;

	// Constructors

	/** default constructor */
	public OaRelease() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWorkId() {
		return this.workId;
	}

	public void setWorkId(String workId) {
		this.workId = workId;
	}

	public String getTaskId() {
		return this.taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getValueTitle() {
		return this.valueTitle;
	}

	public void setValueTitle(String valueTitle) {
		this.valueTitle = valueTitle;
	}

	public String getValueNumber() {
		return this.valueNumber;
	}

	public void setValueNumber(String valueNumber) {
		this.valueNumber = valueNumber;
	}

	public String getValueOrgans() {
		return this.valueOrgans;
	}

	public void setValueOrgans(String valueOrgans) {
		this.valueOrgans = valueOrgans;
	}

	public String getValueDate() {
		return this.valueDate;
	}

	public void setValueDate(String valueDate) {
		this.valueDate = valueDate;
	}

	public String getValueSpeed() {
		return this.valueSpeed;
	}

	public void setValueSpeed(String valueSpeed) {
		this.valueSpeed = valueSpeed;
	}

	public Byte getDisplay() {
		return this.display;
	}

	public void setDisplay(Byte display) {
		this.display = display;
	}

	public Byte getSuccess() {
		return this.success;
	}

	public void setSuccess(Byte success) {
		this.success = success;
	}

	public String getUserList() {
		return this.userList;
	}

	public void setUserList(String userList) {
		this.userList = userList;
	}

	public String getOwner() {
		return this.owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getOwnerAccount() {
		return this.ownerAccount;
	}

	public void setOwnerAccount(String ownerAccount) {
		this.ownerAccount = ownerAccount;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getHtml() {
		return this.html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public Byte getIsReply() {
		return this.isReply;
	}

	public void setIsReply(Byte isReply) {
		this.isReply = isReply;
	}

	public Date getExpect() {
		return this.expect;
	}

	public void setExpect(Date expect) {
		this.expect = expect;
	}

}