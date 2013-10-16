package com.hyrt.foshanLaw.e;

import java.io.Serializable;

public class Law implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;//法律法规id
	private String dm;//编号
	private String ms;//内容
	private String bz;//法律法规名称
	private String yxq;//有效日期

	public String getYxq() {
		return yxq;
	}

	public void setYxq(String yxq) {
		this.yxq = yxq;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDm() {
		return dm;
	}

	public void setDm(String dm) {
		this.dm = dm;
	}

	public String getMs() {
		return ms;
	}

	public void setMs(String ms) {
		this.ms = ms;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
