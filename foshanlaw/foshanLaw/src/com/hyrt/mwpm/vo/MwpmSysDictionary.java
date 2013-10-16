package com.hyrt.mwpm.vo;

/**
 * MwpmSysDictionary entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class MwpmSysDictionary implements java.io.Serializable {

	// Fields

	private String id;
	private String dataname;
	private String groupcode;
	private String groupname;

	// Constructors

	/** default constructor */
	public MwpmSysDictionary() {
	}

	/** minimal constructor */
	public MwpmSysDictionary(String id) {
		this.id = id;
	}

	/** full constructor */
	public MwpmSysDictionary(String id, String dataname, String groupcode, String groupname) {
		this.id = id;
		this.dataname = dataname;
		this.groupcode = groupcode;
		this.groupname = groupname;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDataname() {
		return this.dataname;
	}

	public void setDataname(String dataname) {
		this.dataname = dataname;
	}

	public String getGroupcode() {
		return this.groupcode;
	}

	public void setGroupcode(String groupcode) {
		this.groupcode = groupcode;
	}

	public String getGroupname() {
		return this.groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

}