package com.hyrt.cei.vo;

public class Task implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String content;
	private String taskname;
	private String company;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTaskname() {
		return taskname;
	}
	public void setTaskname(String taskname) {
		this.taskname = taskname;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	
	

}
