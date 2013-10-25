package com.hyrt.cei.vo;

/**
 * MwpmSysReportType entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class MwpmSysReportType  implements java.io.Serializable {

	private String id;
	private String name;
	private String parentid;
	private String isleaf;
	private String path;
	private Integer depth;
	private String status;
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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
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
