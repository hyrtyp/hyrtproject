/**
 * 
 */
package com.hyrt.foshanLaw.pptclient.db.business;

import java.util.ArrayList;
import java.util.List;

import com.hyrt.foshanLaw.pptclient.db.DBHelper;
import com.hyrt.foshanLaw.pptclient.db.dao.CacheGroup;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

/**
 * Description:CacheGroup表操作类
 * 
 * @author 郑伟
 * @Date 2013-1-8
 * @Copyright:2013-1-8
 * @Company:华源润通（北京）科技有限公司重庆分公司
 */
public class CacheGroupFunction {
	Context context;
	String tablename;
	DBHelper db;

	public CacheGroupFunction(Context _context) {
		context = _context;
		tablename = "cachegroup";
		db = new DBHelper(context);
	}

	public void add(String userid, String sessionid, List<String> lst) {
		for (String obj : lst) {
			ContentValues values = new ContentValues();
			values.put("_id", System.currentTimeMillis());
			values.put("uid", userid);
			values.put("fuid", obj);
			values.put("sessionid", sessionid);
			db.insert(tablename, values);
		}
		db.close();
	}

	/**
	 * 添加单个人
	 * 
	 * @param _userid
	 * @param _username
	 * @param _sessionid
	 */
	public void add(String _userid, String _sessionid, String _fuid) {
		ContentValues values = new ContentValues();
		values.put("_id", System.currentTimeMillis());
		values.put("uid", _userid);
		values.put("fuid", _fuid);
		values.put("sessionid", _sessionid);
		db.insert(tablename, values);
		db.close();
	}

	/**
	 * 删除指令会话的记录
	 * 
	 * @param sessionid
	 */
	public void del(String userid,String sessionid) {
		db.del(tablename, "uid=? and sessionid=?", new String[] { userid,sessionid });
		db.close();
	}

	/**
	 * 返回指定会话id对应的用户信息
	 * 
	 * @param sessionid
	 * @return
	 */
	public List<CacheGroup> getList(String userid, String sessionid) {
		List<CacheGroup> re = new ArrayList<CacheGroup>();
		String where = "sessionid=? and uid=?";
		String[] cols = new String[] { "_id", "fuid", "sessionid" };
		Cursor c = db.GetList(tablename, false, cols, where, new String[] {
				sessionid, userid }, null, null, "fuid", null);
		if (c.moveToFirst()) {
			for (int i = 0, j = c.getCount(); i < j; i++) {
				c.moveToPosition(i);
				CacheGroup tmp = new CacheGroup();
				tmp.setId(c.getLong(0));
				tmp.setUserid(c.getString(1));
				// tmp.setUsername(c.getString(2));
				tmp.setSessionid(c.getString(2));
				re.add(tmp);
			}
		}
		c.close();
		db.close();
		return re;
	}

	public ArrayList<String> getUseridColl(String userid, String sessionid) {
		ArrayList<String> re = new ArrayList<String>();
		String where = "sessionid=? and uid=?";
		String[] cols = new String[] { "fuid" };
		Cursor c = db.GetList(tablename, false, cols, where, new String[] {
				sessionid, userid }, null, null, "fuid", null);
		if (c.moveToFirst()) {
			if (c.moveToFirst()) {
				for (int i = 0, j = c.getCount(); i < j; i++) {
					c.moveToPosition(i);
					String tmp = c.getString(0);
					re.add(tmp);
				}
			}
		}
		c.close();
		db.close();
		return re;
	}
}
