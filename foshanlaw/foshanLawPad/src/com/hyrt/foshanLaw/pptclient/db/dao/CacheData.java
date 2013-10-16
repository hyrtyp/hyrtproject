/**
 * 
 */
package com.hyrt.foshanLaw.pptclient.db.dao;
 

/**
 * Description:字典表CacheData对象，存放单次的对讲录音记录
 * 
 * @author 郑伟
 * @Date 2013-1-7
 * @Copyright:2013-1-7
 * @Company:华源润通（北京）科技有限公司重庆分公司
 */
public class CacheData {
	long id;
	String result;
	String filepath;
	String filename;
	boolean filesend;//文件上传标识符
	boolean del;
	String sessionid;

	public CacheData() {
		id = 0;
		result = "0";
		filesend = false;
		filepath = "0";
		filename="0";
		del=false;
		sessionid="0";
	}

	public long getId() {
		return id;
	}

	public void setId(long obj) {
		this.id = obj;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String obj) {
		if ("".equals(obj))
			this.result = "0";
		else
			this.result = obj;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String obj) {
		if ("".equals(obj))
			this.filepath = "0";
		else
			this.filepath = obj;
	}

	public boolean isSend() {
		return filesend;
	}

	public void setSend(boolean obj) {
		this.filesend = obj;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String obj) {
		if ("".equals(obj))
			this.filename = "0";
		else
			this.filename = obj;
	}

	public boolean isDel() {
		return del;
	}

	public void setDel(boolean del) {
		this.del = del;
	}

	public String getSessionid() {
		return sessionid;
	}

	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}
	
	
}
