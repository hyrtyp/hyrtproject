package com.hyrt.cei.vo;

public class Column{
	
	private boolean pkFlag;
	private String defaultValue;
	private String name;
	private String type;
	private int length;
	private String code;
	
	public boolean isPkFlag() {
		return pkFlag;
	}
	public void setPkFlag(boolean pkFlag) {
		this.pkFlag = pkFlag;
	}
	
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
}