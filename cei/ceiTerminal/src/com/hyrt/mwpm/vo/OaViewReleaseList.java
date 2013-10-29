package com.hyrt.mwpm.vo;

import java.util.Date;

/**
 * OaViewReleaseListId entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class OaViewReleaseList implements java.io.Serializable {

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
	private String userName;
	private String account;
	private Byte view;
	private Date viewDate;
	private Byte isReply;
	private Date expect;

	// Constructors

	/** default constructor */
	public OaViewReleaseList() {
	}

	/** minimal constructor */
	public OaViewReleaseList(String id) {
		this.id = id;
	}

	/** full constructor */
	public OaViewReleaseList(String id, String workId, String taskId,
			String valueTitle, String valueNumber, String valueOrgans,
			String valueDate, String valueSpeed, Byte display, Byte success,
			String userList, String owner, String ownerAccount, Date date,
			String html, String userName, String account, Byte view,
			Date viewDate, Byte isReply, Date expect) {
		this.id = id;
		this.workId = workId;
		this.taskId = taskId;
		this.valueTitle = valueTitle;
		this.valueNumber = valueNumber;
		this.valueOrgans = valueOrgans;
		this.valueDate = valueDate;
		this.valueSpeed = valueSpeed;
		this.display = display;
		this.success = success;
		this.userList = userList;
		this.owner = owner;
		this.ownerAccount = ownerAccount;
		this.date = date;
		this.html = html;
		this.userName = userName;
		this.account = account;
		this.view = view;
		this.viewDate = viewDate;
		this.isReply = isReply;
		this.expect = expect;
	}

	// Property accessors

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

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Byte getView() {
		return this.view;
	}

	public void setView(Byte view) {
		this.view = view;
	}

	public Date getViewDate() {
		return this.viewDate;
	}

	public void setViewDate(Date viewDate) {
		this.viewDate = viewDate;
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