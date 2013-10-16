package com.hyrt.mwpm.vo;


public class Task  implements java.io.Serializable {

	private int id;
	private int userid;
	private String company;
	private String taskname;
	private String content;
	private int isdreceive;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getTaskname() {
		return taskname;
	}
	public void setTaskname(String taskname) {
		this.taskname = taskname;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getIsdreceive() {
		return isdreceive;
	}
	public void setIsdreceive(int isdreceive) {
		this.isdreceive = isdreceive;
	}
	
}
