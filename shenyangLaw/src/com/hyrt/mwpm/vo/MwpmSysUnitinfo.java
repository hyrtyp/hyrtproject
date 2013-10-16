package com.hyrt.mwpm.vo;

/**
 * MwpmSysUnitinfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class MwpmSysUnitinfo implements java.io.Serializable {

	// Fields

	private String id;
	private String orgname;
	private String parentid;
	private String isleaf;
	private String unitcode;
	private String path;
	private Integer depth;

	// Constructors

	/** default constructor */
	public MwpmSysUnitinfo() {
	}

	/** minimal constructor */
	public MwpmSysUnitinfo(String id) {
		this.id = id;
	}

	/** full constructor */
	public MwpmSysUnitinfo(String id, String orgname, String parentid, String isleaf, String unitcode, String path, Integer depth) {
		this.id = id;
		this.orgname = orgname;
		this.parentid = parentid;
		this.isleaf = isleaf;
		this.unitcode = unitcode;
		this.path = path;
		this.depth = depth;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrgname() {
		return this.orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	public String getParentid() {
		return this.parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public String getIsleaf() {
		return this.isleaf;
	}

	public void setIsleaf(String isleaf) {
		this.isleaf = isleaf;
	}

	public String getUnitcode() {
		return this.unitcode;
	}

	public void setUnitcode(String unitcode) {
		this.unitcode = unitcode;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Integer getDepth() {
		return this.depth;
	}

	public void setDepth(Integer depth) {
		this.depth = depth;
	}

}