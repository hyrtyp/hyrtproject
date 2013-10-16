/**
 * 
 */
package com.hyrt.foshanLaw.pptclient.db.business;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.hyrt.foshanLaw.pptclient.db.DBHelper;
import com.hyrt.foshanLaw.pptclient.db.dao.SessionItem;

/**
 * Description:SessionItem 会话记录操作类
 * @author 郑伟
 * @Date 2013-1-8
 * @Copyright:2013-1-8
 * @Company:华源润通（北京）科技有限公司重庆分公司
 */
public class SessionItemFunction {
	Context context;
	String tablename;
	DBHelper db;

	public SessionItemFunction(Context _context) {
		context = _context;
		tablename = "sessionitem";
		db = new DBHelper(context);
	}
	
 
	/**
	 * 添加记录
	 * @param obj
	 */
	public void add(long _id,String userid,SessionItem obj){
		ContentValues values = new ContentValues();
		values.put("_id", _id);
		values.put("uid", userid);
		values.put("filepath", obj.getFilepath());
		values.put("sessionid", obj.getSessionid());
		values.put("tm", obj.getTime());
		values.put("ftppath", obj.getFtppath());
		values.put("fuid", obj.getFuid());
		values.put("scd", obj.getSecond());
		values.put("issend", obj.getIssend());
		values.put("errmsg", obj.getErrmsg());
		db.insert(tablename, values);
		db.close();
	}
	
	/**
	 * 返回记录集
	 * @param sessionid 会话id
	 * @param id 某个时间点
	 * @param size 每页数量
	 * @param before true=之前的记录，false=之后的记录
	 * @return
	 */
	public List<SessionItem> getList(String userid,String sessionid,long id,int size,boolean before){
		List<SessionItem> re=new ArrayList<SessionItem>();
		String where;
		if(before){
			where="uid=? and _id<? and sessionid=?";
		}else{
			where="uid=? and _id>? and sessionid=?";
		}
		Cursor c = db.GetList(tablename, false, new String[] {"_id", "filepath","sessionid","tm","ftppath","fuid","scd","issend","errmsg" },
				where, new String[]{userid,""+id,sessionid}, null, null, "_id", ""+size);
		if (c.moveToFirst()) {
			for (int i = 0, j = c.getCount(); i < j; i++) {
				c.moveToPosition(i);
				SessionItem tmp=new SessionItem();
				tmp.setId(c.getLong(0));
				tmp.setFilepath(c.getString(1));
				tmp.setSessionid(c.getString(2));
				tmp.setTm(c.getString(3));
				tmp.setFtppath(c.getString(4));
				tmp.setFuid(c.getString(5));
				tmp.setSecond(c.getString(6));
				tmp.setIssend(c.getInt(7));
				tmp.setErrmsg(c.getString(8));
				re.add(tmp);
			}
		}
		c.close();
		db.close();
		return re;
		
	}
	
	/**
	 * 删除指定会话id对应的记录
	 * @param sessionid
	 */
	public void del(String userid,String sessionid){
		db.del(tablename, "uid=? and sessionid=?", new String[]{userid,sessionid});
		db.close();
	}
	
	/**
	 * 更新记录发送情况,仅更新发送标识和成功信息
	 * @param userid
	 * @param obj
	 */
	public void update(String userid,SessionItem obj){
		ContentValues values = new ContentValues();
		values.put("issend", obj.getIssend());
		values.put("errmsg", obj.getErrmsg());
		db.update(tablename, "_id=? and uid=?", new String[]{""+obj.getId(),userid}, values);
		db.close();
	}
	
	/**
	 * 是否存在相同的会话
	 * @param id 时间戳
	 * @param sessionid 会话id
	 * @return
	 */
	public boolean isExsit(long id,String sessionid){
		boolean flag= db.isexsit(tablename, "_id=? and sessionid=?", new String[]{""+id,sessionid});
		db.close();
		return flag;
	}
}
