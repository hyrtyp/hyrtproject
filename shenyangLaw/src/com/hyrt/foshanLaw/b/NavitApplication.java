package com.hyrt.foshanLaw.b;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.hyrt.cei.util.MyTools;
import com.hyrt.foshanLaw.pptclient.Vfun;
import android.app.Activity;
import android.app.Application;
import android.content.Context;

public class NavitApplication extends Application {

	private static Application that;
	private ArrayList<Activity> activitys;
	static boolean m_bKeyRight = true;
	
	Map<String,Integer> mapNew;
	String uid="1";
	String uname="用户2";
	Vfun vf;
 
	public static Application getInstance(){
		return that;
	}
	
	public Vfun getVf() {
		if(vf==null){
			vf=new Vfun();
		}
		return vf;
	}

	public void setVf(Vfun vf) {
		this.vf = vf;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getUid(){
		return uid;
	}
	
	public void setUid(String userid){
		this.uid=userid;
	}
 
	
	
	/**
	 * 记录新的会话
	 * @param sessionid
	 */
	public void addNew(String sessionid){
		if(mapNew==null){
			mapNew=new HashMap<String,Integer>();
			mapNew.put(sessionid, 1);
		}else{
			if(mapNew.containsKey(sessionid)){
				Integer i=mapNew.get(sessionid);
				i++;
				mapNew.put(sessionid, i);
			}else{
				mapNew.put(sessionid, 1);
			}
		}
	}
	
	/**
	 * 是否有新的会话
	 * @param sessionid
	 * @return
	 */
	public boolean hasNew(String sessionid){
		boolean flag=false;
		if(mapNew==null){
			mapNew=new HashMap<String,Integer>();
		}else{
			if(mapNew.containsKey(sessionid)){
				Integer i=mapNew.get(sessionid);
				if(i>0) flag=true;
			}
		}
		return flag;
	}
	
	public int getCountBySessionid(String sessionid){
		int flag=0;
		if(mapNew==null){
			mapNew=new HashMap<String,Integer>();
		}else{
			if(mapNew.containsKey(sessionid)){
				flag=mapNew.get(sessionid);
			}
		}
		return flag;
	}
	
	/**
	 * 移除会话
	 * @param sessionid
	 */
	public void clearNew(String sessionid){
		if(mapNew==null){
			mapNew=new HashMap<String,Integer>();
		}else{
			mapNew.remove(sessionid);
		}
	}
	
	public void clearAll(){
		if(mapNew==null){
			mapNew=new HashMap<String,Integer>();
		}else{
			mapNew.clear();
		}
	}

	public ArrayList<Activity> getActivitys() {
		return activitys;
	}

	public void onCreate() {
		super.onCreate();
		that = this;
		File basePath = new File(MyTools.LOCAL_PATH);
		if (!basePath.exists())
			basePath.mkdirs();
	}
	
	@Override
	public void onLowMemory() {
		super.onLowMemory();
	}

}