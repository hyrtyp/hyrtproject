/**
 * 
 */
package com.hyrt.foshanLaw.pptclient.db.dao;

/**
 * Description:会话对象本地缓存，本地数据表CacheGroup
 * 
 * @author 郑伟
 * @Date 2013-1-8
 * @Copyright:2013-1-8
 * @Company:华源润通（北京）科技有限公司重庆分公司
 */
public class CacheGroup {
	long id;
	String userid;
	String sessionid;

	public CacheGroup() {
		id = 0;
		userid = "0";
		sessionid = "0";
	}

	public long getId() {
		return id;
	}

	public void setId(long obj) {
		this.id = obj;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String obj) {
		if ("".equals(obj))
			this.userid = "0";
		else
			this.userid = obj;
	}

 

	public String getSessionid() {
		return sessionid;
	}

	public void setSessionid(String obj) {
		if ("".equals(obj))
			this.sessionid = "0";
		else
			this.sessionid = obj;
	}

}
