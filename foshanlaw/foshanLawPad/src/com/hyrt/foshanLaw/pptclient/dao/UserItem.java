/**
 * 
 */
package com.hyrt.foshanLaw.pptclient.dao;
 
/**
 * Description: UI成员类
 * @author 郑伟
 * @Date 2013-1-9
 * @Copyright:2013-1-9
 * @Company:华源润通（北京）科技有限公司重庆分公司
 */
public class UserItem {
	String userid;
	String username;
	String phone;
	String gid;
	boolean  select;
	
	public UserItem(String _userid,String _username,String _phone,String _gid){
		userid=_userid;
		username=_username;
		phone=_phone;
		gid=_gid;
		 select=false;
	}

	
	
	public boolean isSelect() {
		return select;
	}
 
	public void setSelect(boolean select) {
		this.select = select;
	}



	public UserItem Clone(){
		UserItem obj=new UserItem(this.userid,this.username,this.phone,this.gid);
		return obj;
	}
	
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}
}
