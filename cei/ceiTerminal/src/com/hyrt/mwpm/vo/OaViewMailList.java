package com.hyrt.mwpm.vo;

import java.util.Date;

/**
 * OaViewMailListId entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class OaViewMailList implements java.io.Serializable {

	// Fields

	private String id;
	private String title;
	private String message;
	private String fileId;
	private String fileList;
	private String userList;
	private String owner;
	private String ownerAccount;
	private Date date;
	private String userName;
	private String account;
	private Byte view;
	private Date viewDate;
	private Byte isDel;
	private Byte oisDel;

	// Constructors

	/** default constructor */
	public OaViewMailList() {
	}

	/** minimal constructor */
	public OaViewMailList(String id) {
		this.id = id;
	}

	/** full constructor */
	public OaViewMailList(String id, String title, String message,
			String fileId, String fileList, String userList, String owner,
			String ownerAccount, Date date, String userName, String account,
			Byte view, Date viewDate, Byte isDel, Byte oisDel) {
		this.id = id;
		this.title = title;
		this.message = message;
		this.fileId = fileId;
		this.fileList = fileList;
		this.userList = userList;
		this.owner = owner;
		this.ownerAccount = ownerAccount;
		this.date = date;
		this.userName = userName;
		this.account = account;
		this.view = view;
		this.viewDate = viewDate;
		this.isDel = isDel;
		this.oisDel = oisDel;
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

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getFileId() {
		return this.fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getFileList() {
		return this.fileList;
	}

	public void setFileList(String fileList) {
		this.fileList = fileList;
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

	public Byte getIsDel() {
		return this.isDel;
	}

	public void setIsDel(Byte isDel) {
		this.isDel = isDel;
	}

	public Byte getOisDel() {
		return this.oisDel;
	}

	public void setOisDel(Byte oisDel) {
		this.oisDel = oisDel;
	}

	
}