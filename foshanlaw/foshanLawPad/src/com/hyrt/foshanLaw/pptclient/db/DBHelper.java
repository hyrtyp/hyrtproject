/**
 * 
 */
package com.hyrt.foshanLaw.pptclient.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Description:本地数据库操作类
 * 
 * @author 郑伟
 * @Date 2013-1-7
 * @Copyright:2013-1-7
 * @Company:华源润通（北京）科技有限公司重庆分公司
 */
public class DBHelper extends SQLiteOpenHelper {
	private static final String DB_NAME = "PPT.DB";

	private SQLiteDatabase db;
	final private static int ver = 9;

	public DBHelper(Context context) {
		super(context, DB_NAME, null, ver);
		// TODO Auto-generated constructor stub
		// db = this.getWritableDatabase();

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		if (db==null||db.isOpen()==false)

			db = this.getWritableDatabase();
		String sql1 = "create table cachedata(_id long primary key, result varchar(4000),  filepath varchar(400),filename varchar(50),filesend int,uid varchar(40),sessionid varchar(40));";
		String sql2 = "create table sessiongroup(_id long primary key,sessionid varchar(40),sessionname varchar(40),isgroup int,uid varchar(40),tm varchar(40),newcnt int );";
		String sql3 = "create table sessionitem(_id long primary key,sessionid varchar(40),tm varchar(40),filepath varchar(400),ftppath varchar(400),fuid varchar(40),scd varchar(10), uid varchar(40),issend int,errmsg varchar(40));";
		String sql4 = "create table cachegroup(_id long primary key,sessionid varchar(40),fuid varchar(40),uid varchar(40) );";
		db.execSQL(sql1);
		db.execSQL(sql2);
		db.execSQL(sql3);
		db.execSQL(sql4);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub\
		if (db==null||db.isOpen()==false)

			db = this.getWritableDatabase();
		db.execSQL("DROP TABLE IF EXISTS cachedata");
		db.execSQL("DROP TABLE IF EXISTS sessiongroup");
		db.execSQL("DROP TABLE IF EXISTS sessionitem");
		db.execSQL("DROP TABLE IF EXISTS cachegroup");
		onCreate(db);
	}

	/*
	 * 插入
	 */
	public boolean insert(String tablename, ContentValues values) {
		boolean flag = true;
		if (db==null||db.isOpen()==false)

			db = this.getWritableDatabase();
		try {
			db.insert(tablename, null, values);
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/*
	 * 删除记录（指定主键）
	 */
	public void del(String tablename, String id) {
		if (db==null||db.isOpen()==false)

			db = this.getWritableDatabase();
		String whereClause = "_id=?";
		String[] whereArgs = { String.valueOf(id) };
		db.delete(tablename, whereClause, whereArgs);
	}

	/*
	 * 删除记录（指定条件）
	 */
	public void del(String tablename, String where, String[] Args) {
		if (db==null||db.isOpen()==false)

			db = this.getWritableDatabase();
		db.delete(tablename, where, Args);
	}

	/*
	 * 更新表
	 */
	public void update(String tablename, String id, ContentValues values) {
		if (db==null||db.isOpen()==false)

			db = this.getWritableDatabase();
		String whereClause = "_id=?";
		String[] whereArgs = { String.valueOf(id) };
		db.update(tablename, values, whereClause, whereArgs);
	}

	/*
	 * 更新流量表专用
	 */
	public void update(String tablename, String where, String[] args,
			ContentValues values) {
		if (db==null||db.isOpen()==false)

			db = this.getWritableDatabase();
		db.update(tablename, values, where, args);
	}

	/*
	 * 存在返回true
	 */
	boolean isexsit(String tablename, String _id) {
		boolean flag = false;
		if (db==null||db.isOpen()==false)

			db = this.getWritableDatabase();
		String whereClause = "_id=?";
		String[] whereArgs = { String.valueOf(_id) };
		Cursor c = db.query(tablename, null, whereClause, whereArgs, null,
				null, null, null);
		if (c.moveToFirst())
			flag = true;
		else
			flag = false;
		c.close();
		return flag;
	}

	public boolean isexsit(String tablename, String where, String[] args) {
		boolean flag = false;
		if (db==null||db.isOpen()==false)

			db = this.getWritableDatabase();
		Cursor c = db
				.query(tablename, null, where, args, null, null, null, "1");
		if (c.moveToFirst())
			flag = true;
		else
			flag = false;
		c.close();
		return flag;
	}

	// /*
	// * 取得列表
	// */
	// public Cursor GetList(String tablename,String[] columns,String
	// where,String[] args,String groupby,String having,String order,String
	// limit) {
	// if (!db.isOpen())
	// db = getWritableDatabase();
	// Cursor c =db.query(tablename, columns, where, args, groupby, having,
	// order,limit);
	// return c;
	// }

	/*
	 * 取得列表(不重复)
	 */
	public Cursor GetList(String tablename, boolean distinct, String[] columns,
			String where, String[] args, String groupby, String having,
			String order, String limit) {
		if (db==null||db.isOpen()==false)
			db = this.getWritableDatabase();
		Cursor c = db.query(distinct, tablename, columns, where, args, groupby,
				having, order, limit);
		return c;
	}

	public void close() {
		if (db!=null&&db.isOpen())
			db.close();
	}

}
