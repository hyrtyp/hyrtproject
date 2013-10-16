package com.hyrt.mwpm.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * Ñ²²é¹ì¼£
 * @author yepeng
 *
 */
public class MwpmBussPatrolLoca implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String userid;
	private String lat;
	private String long1;
	private Date createtime;
	
	public MwpmBussPatrolLoca(){
		
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLong1() {
		return long1;
	}
	public void setLong1(String long1) {
		this.long1 = long1;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
	
}
