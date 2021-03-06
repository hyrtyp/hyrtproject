package com.hyrt.cei.vo;

/**
 * MwpmDictSubeunit generated by MyEclipse Persistence Tools
 */

public class MwpmDictSubeunit implements java.io.Serializable {

	// Fields

	private String id;
	private String code;
	private String name;
	private String status;

	// Constructors

	/** default constructor */
	public MwpmDictSubeunit() {
	}

	/** minimal constructor */
	public MwpmDictSubeunit(String code) {
		this.code = code;
	}

	/** full constructor */
	public MwpmDictSubeunit(String code, String name, String status) {
		this.code = code;
		this.name = name;
		this.status = status;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}