/**
 * 
 */
package com.hyrt.foshanLaw.pptclient.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:录音文件类,不操作本地数据库.
 * 
 * @author 郑伟
 * @Date 2013-1-7
 * @Copyright:2013-1-7
 * @Company:华源润通（北京）科技有限公司重庆分公司
 */
public class FileObj {

	String filename;
	int second;
	String time;
	boolean group;
	String filepath;
	List<String> receobj;

	public FileObj() {
		filename = "0";
		second = 0;
		time = "0";
		group = false;
		receobj = new ArrayList<String>();
		filepath = "0";
	}

	/**
	 * 设置发送对象
	 * 
	 * @param lst
	 * 
	 */
	public void AddReceiver(List<String> lst) {
		receobj.clear();
		if(lst.size()>0)
		receobj.addAll(lst);
	}

	public void AddReceiver(String touserid) {
		receobj.clear();
		receobj.add(touserid);
	}

	public String getArrayListStr() {
		String s = "";
		if (receobj.size() > 0) {
			for (String o : receobj) {
				s += ",\"" + o + "\"";
			}
			if (s.length() > 0) {
				s = s.substring(1);
			}
		}
		return s;
	}

	// ***************以下是getter setter

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public int getSecond() {
		return second;
	}

	public void setSecond(int second) {
		this.second = second;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public boolean isGroup() {
		return group;
	}

	public void setGroup(boolean group) {
		this.group = group;
	}

	public List<String> getReceobj() {
		return receobj;
	}

	public void setReceobj(List<String> receobj) {
		this.receobj = receobj;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

}
