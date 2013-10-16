/**
 * 
 */
package com.hyrt.foshanLaw.pptclient.dao;

/**
 * Description:
 * @author 郑伟
 * @Date 2013-1-10
 * @Copyright:2013-1-10
 * @Company:华源润通（北京）科技有限公司重庆分公司
 */
public class GroupItem {
	String gid;
	String gname;
	String pid;
	int level=0;
	boolean isown;   //用户在组内的标识
	
	public GroupItem(String _groupid,String _groupname,String _pid){
		gid=_groupid;
		gname=_groupname;
		pid=_pid;
		level=0;
		isown=false;
	}
	
	
	
	public boolean isOwn() {
		return isown;
	}

	public void setIsown(boolean won) {
		this.isown = won;
	}



	public int getLevel(){
		return level;
	}
	
	public void setLevel(int obj){
		level=obj;
	}
	
	public GroupItem Clone(){
		GroupItem obj=new GroupItem(this.gid,this.gname,this.pid);
		obj.setIsown(this.isown);
		return obj;
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getGname() {
		return gname;
	}

	public void setGname(String gname) {
		this.gname = gname;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}
	
	
}
