package com.hyrt.cei.vo;

/**
 * MwpmSysOperatiResource entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class MwpmSysOperatiResource  implements java.io.Serializable {

	private String id;
	private String functionid;
	private String classid;
	private String type;
	private String ischeck;

	

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFunctionid() {
		return this.functionid;
	}

	public void setFunctionid(String functionid) {
		this.functionid = functionid;
	}

	public String getClassid() {
		return this.classid;
	}

	public void setClassid(String classid) {
		this.classid = classid;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIscheck() {
		return this.ischeck;
	}

	public void setIscheck(String ischeck) {
		this.ischeck = ischeck;
	}

}
