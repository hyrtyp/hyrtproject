/**
 * 
 */
package com.hyrt.foshanLaw.pptclient.db.business;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.hyrt.foshanLaw.pptclient.dao.SessionUserItem;
import com.hyrt.foshanLaw.pptclient.db.DBHelper;
import com.hyrt.foshanLaw.pptclient.db.dao.CacheGroup;
import com.hyrt.foshanLaw.pptclient.db.dao.SessionGroup;

/**
 * Description:SessionGroup表操作类
 * 
 * @author 郑伟
 * @Date 2013-1-8
 * @Copyright:2013-1-8
 * @Company:华源润通（北京）科技有限公司重庆分公司
 */
public class SessionGroupFunction {
	Context context;
	String tablename;
	DBHelper db;

	public SessionGroupFunction(Context _context) {
		context = _context;
		tablename = "sessiongroup";
		db = new DBHelper(context);
	}

	public boolean isExist(String uid,String sessionid) {
		boolean flag= db.isexsit(tablename, "uid=? and sessionid=?", new String[] { uid,sessionid });
		db.close();
		return flag;
	}
	
	public boolean isGroup(String uid,String sessionid) {
		boolean flag= db.isexsit(tablename, "uid=? and sessionid=? and isgroup=1", new String[] { uid,sessionid });
		db.close();
		return flag;
	}

 
	/**
	 * 取得会话组名称
	 * @param uid  用户id
	 * @param sessionid  
	 * @return
	 */
	public String getSessionName(String uid,String sessionid){
		String re="未知会话";
		Cursor c = db.GetList(tablename, false, new String[] {"sessionname"}, "uid=? and sessionid=?",
				new String[] { uid,sessionid }, null, null, null, "1");
		if (c.moveToFirst()) {
			re=c.getString(0);
		}
		c.close();
		db.close();
		return re;
	}
	
	/**
	 * 新增记录
	 * @param uid  用户id
	 * @param sessionid  
	 * @param sessionname
	 * @param lasttm
	 * @param title  本地显示标题
	 * @param isgroup 是否分组
	 */
	public void add(String uid,String sessionid, String sessionname, String lasttm ,
			boolean isgroup) {
		ContentValues values = new ContentValues();
		values.put("_id", System.currentTimeMillis());
		values.put("sessionid", sessionid);
		values.put("uid", uid);
		values.put("sessionname", sessionname);
		values.put("tm", lasttm);
		if (isgroup)
			values.put("isgroup", 1);
		else
			values.put("isgroup", 0);
		values.put("newcnt", 0);
		db.insert(tablename, values);
		db.close();
	}

	public List<SessionGroup> getList(String uid) {
		List<SessionGroup> re = new ArrayList<SessionGroup>();
		Cursor c = db.GetList(tablename, false, new String[] { "_id",
				"sessionid", "sessionname", "tm", "isgroup","newcnt"  }, "uid=?",
				new String[] { uid }, null, null, "tm desc", null);
		if (c.moveToFirst()) {
			for (int i = 0, j = c.getCount(); i < j; i++) {
				c.moveToPosition(i);
				SessionGroup tmp = new SessionGroup();
				tmp.setId(c.getLong(0));
				tmp.setSessionid(c.getString(1));
				tmp.setSessionname(c.getString(2));
				tmp.setLasttm(c.getString(3));
				if (c.getInt(4) == 0) {
					tmp.setGroup(false);
				} else {
					tmp.setGroup(true);
				}
				tmp.setMsgcount(c.getInt(5));
				re.add(tmp);
			}
		}
		c.close();
		db.close();
		return re;
	}

	/**
	 * 删除值
	 * 
	 * @param sessionid
	 */
	public void del(String sessionid) {
		db.del(tablename, "sessionid=?", new String[] { sessionid });
		db.close();
	}

	/**
	 * 更新最后时间
	 * 
	 * @param sessionid
	 *            会话值
	 * @param tm
	 *            更新时间
	 */
	public void update(String uid,String sessionid, String tm) {
		ContentValues values = new ContentValues();
		values.put("tm", tm);
		db.update(tablename, "uid=? and sessionid=?", new String[] {uid, sessionid }, values);
		db.close();
	}
	
 
	
	/**
	 * 保存次数
	 * @param uid
	 * @param sessionid
	 * @param count
	 */
	public void update(String uid,String sessionid,int count){
		ContentValues values = new ContentValues();
		values.put("newcnt", count);
		db.update(tablename, "uid=? and sessionid=?", new String[] {uid, sessionid }, values);
		db.close();
	}
	
	
	/**
	 * 判断选择的人 返回"" 表示没有找到匹配的
	 * @param selectusers
	 * @return
	 */
	public String checkSessionid(String uid,List<SessionUserItem> selectusers){
		if(selectusers.size()==0) return "";
		//取得sessionid列表
		List<String> sessioncol=new ArrayList<String>();
		Cursor c = db.GetList(tablename, true, new String[] { 
				"sessionid"  }, "uid=?",
				new String[] { uid }, null, null, null, null);
		if (c.moveToFirst()) {
			for (int i = 0, j = c.getCount(); i < j; i++) {
				c.moveToPosition(i);
				sessioncol.add(c.getString(0));
			}
		}
		c.close();
		if(sessioncol.size()==0) return "";
		
		String sid="";
		//循环判断
		CacheGroupFunction cgf=new CacheGroupFunction(context);
		for(String sessionid:sessioncol){
			List<CacheGroup> tmp=cgf.getList(uid, sessionid);
			if(tmp.size()==selectusers.size()){
				//长度吻合判断
				boolean flag=true;
				for(int i=0;i<selectusers.size();i++){
					if(isExsitInCacheGroup(tmp,selectusers.get(i).getUserid())==false){
						//总算找到了异常
						flag=false;
						break;
					}
				}
				if(flag){
					//完全匹配
					sid=sessionid;
					break;
				}
			}
		}
		db.close();
		return sid;
	}
 
	boolean isExsitInCacheGroup(List<CacheGroup> source,String userid){
		boolean flag=false;
		for(int i=0;i<source.size();i++){
			if(source.get(i).getUserid().equals(userid)){
				flag=true;
				break;
			}
		}
		return flag;
	}
}
