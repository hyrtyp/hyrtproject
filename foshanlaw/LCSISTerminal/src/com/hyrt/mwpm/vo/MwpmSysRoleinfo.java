package com.hyrt.mwpm.vo;

/**
 * MwpmSysRoleinfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class MwpmSysRoleinfo  implements java.io.Serializable {

	private String roleid;
	private String name;
	private Integer seqnum;
	private String description;
	private String type;
	 private String rightName;
	// Constructors

	

	// Property accessors

	public String getRoleid() {
		return this.roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSeqnum() {
		return this.seqnum;
	}

	public void setSeqnum(Integer seqnum) {
		this.seqnum = seqnum;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRightName() {
		return rightName;
	}

	public void setRightName(String rightName) {
		this.rightName = rightName;
	}

}
