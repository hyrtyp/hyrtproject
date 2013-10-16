package com.hyrt.mwpm.vo;

/**
 * MwpmBussPatrolItem entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class MwpmBussPatrolItem implements java.io.Serializable {

	// Fields

	private String id;
	private String logid;
	private String contentid;
	private String qid;
	private String disposeid;

	// Constructors

	/** default constructor */
	public MwpmBussPatrolItem() {
	}

	/** minimal constructor */
	public MwpmBussPatrolItem(String id) {
		this.id = id;
	}

	/** full constructor */
	public MwpmBussPatrolItem(String id, String logid, String contentid, String qid, String disposeid) {
		this.id = id;
		this.logid = logid;
		this.contentid = contentid;
		this.qid = qid;
		this.disposeid = disposeid;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLogid() {
		return this.logid;
	}

	public void setLogid(String logid) {
		this.logid = logid;
	}

	public String getContentid() {
		return this.contentid;
	}

	public void setContentid(String contentid) {
		this.contentid = contentid;
	}

	public String getQid() {
		return this.qid;
	}

	public void setQid(String qid) {
		this.qid = qid;
	}

	public String getDisposeid() {
		return this.disposeid;
	}

	public void setDisposeid(String disposeid) {
		this.disposeid = disposeid;
	}

}