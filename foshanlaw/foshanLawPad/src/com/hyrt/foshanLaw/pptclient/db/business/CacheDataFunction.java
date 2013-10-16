/**
 * 
 */
package com.hyrt.foshanLaw.pptclient.db.business;

import java.util.ArrayList;
import java.util.List;

import com.hyrt.foshanLaw.pptclient.db.DBHelper;
import com.hyrt.foshanLaw.pptclient.db.dao.CacheData;

 

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

/**
 * Description:CacheData表操作类
 * @author 郑伟
 * @Date 2013-1-7
 * @Copyright:2013-1-7
 * @Company:华源润通（北京）科技有限公司重庆分公司
 */
public class CacheDataFunction {
	Context context;
	String tablename;
	DBHelper db;
	public CacheDataFunction(Context _context){
		context = _context;
		tablename = "cachedata";
		db = new DBHelper(context);
	}
	
	/**
	 * 保存对讲录音记录
	 * @param result
	 * @param filepath
	 */
	public void add(long _id,String sessionid, String result,String filepath,String filename,String uid) {
		if(result.equals("")||filepath.equals("")) return;
		ContentValues values = new ContentValues();
		values.put("_id", _id);
		values.put("result", result);
		values.put("filepath", filepath);
		values.put("filename", filename);
		values.put("sessionid", sessionid);
		values.put("filesend", 0);
		values.put("uid", uid);
		db.insert(tablename, values);
		db.close();
	}

	/**
	 * @param re
	 *            需要删除的记录id集合
	 */
	public void del() {
		db.del(tablename, null, null);
		db.close();
	}
	
	public void del(String id){
		db.del(tablename, id);
		db.close();
	}

	/**
	 * 更新或者删除数据
	 * @param lst
	 */
	public void del(List<CacheData> lst){
		if(lst==null||lst.size()==0) return;
		for(CacheData tmp:lst){
			if(tmp.isDel()){
				db.del(tablename, ""+tmp.getId());
			}else if(tmp.isSend()){
				ContentValues values = new ContentValues();
				values.put("filesend", 1);
				db.update(tablename, ""+tmp.getId(), values);
			}
		}
		db.close();
	}
	
	
	
	/**
	 * @return 本地数据记录，内容为json串，可直接发送
	 */
	public List<CacheData> getList(String uid) {
		List<CacheData> re = new ArrayList<CacheData>();
		Cursor c = db.GetList(tablename, false, new String[] {"_id", "result","filepath","filename","filesend","sessionid" },
				"uid=?", new String[]{uid}, null, null, null, null);
		if (c.moveToFirst()) {
			for (int i = 0, j = c.getCount(); i < j; i++) {
				c.moveToPosition(i);
				CacheData tmp=new CacheData();
				tmp.setId(c.getLong(0));
				tmp.setResult(c.getString(1));
				tmp.setFilepath(c.getString(2));
				tmp.setFilename(c.getString(3));
				tmp.setSessionid(c.getString(5));
				if(c.getInt(4)==0){
					tmp.setSend(false);
				}else{
					tmp.setSend(true);
				}
				re.add(tmp);
			}
		}
		c.close();
		db.close();
		return re;
	}
 
}
