package com.hyrt.cei.vo;

import java.util.Date;

/**
 * MwpmWorkflowInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class MwpmWorkflowInfo  implements java.io.Serializable {

	private String id;
	private String resourceid;
	private String type;
	private String status;
	private String operaterid;
	private Date operatdate;
	private String remark;

	// Constructors

	

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getResourceid() {
		return this.resourceid;
	}

	public void setResourceid(String resourceid) {
		this.resourceid = resourceid;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOperaterid() {
		return this.operaterid;
	}

	public void setOperaterid(String operaterid) {
		this.operaterid = operaterid;
	}

	public Date getOperatdate() {
		return this.operatdate;
	}

	public void setOperatdate(Date operatdate) {
		this.operatdate = operatdate;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
