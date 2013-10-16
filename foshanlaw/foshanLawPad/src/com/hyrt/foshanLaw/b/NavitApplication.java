package com.hyrt.foshanLaw.b;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.LocationListener;
import com.baidu.mapapi.MKEvent;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.MKSearch;*/
import com.hyrt.cei.exception.CrashHandler;
import com.hyrt.cei.util.FileUtil;
import com.hyrt.cei.util.MyTools;
import com.hyrt.cei.util.ZipUtils;
import com.hyrt.foshanLaw.pptclient.Vfun;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.location.LocationManager;

public class NavitApplication extends Application {

	private static Application that;
	private ArrayList<Activity> activitys;
	static boolean m_bKeyRight = true;
/*	public BMapManager mapManager;
	public String mStrKey = "E5C5CEF28181251FAB8EE0D3DBD1E6DD562EEFB6";
	public MKSearch mMKSearch;
	LocationListener mLocationListener = null;*/
	
	Map<String,Integer> mapNew;
	String uid="1";
	String uname="用户2";
	Vfun vf;
 
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
	
	public void clearAll(   ){
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
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					if(!new File("/mnt/sdcard/map.zip").exists())
						FileUtil.copyFile(new BufferedInputStream(that.getAssets().open("map.zip")), new File("/mnt/sdcard/map.zip"));
					if(!new File("/mnt/sdcard/map").exists())
					ZipUtils.unzip("/mnt/sdcard/map.zip", "/mnt/sdcard/map");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	/*	mapManager = new BMapManager(that);
		mapManager.init(mStrKey, new MyGeneralListener());
		mMKSearch = new MKSearch();*/
		//CrashHandler crashHandler = CrashHandler.getInstance();
		//crashHandler.init(getApplicationContext());
	}

/*	public static class MyGeneralListener implements MKGeneralListener {
		@Override
		public void onGetNetworkState(int iError) {
		}

		@Override
		public void onGetPermissionState(int iError) {
			if (iError == MKEvent.ERROR_PERMISSION_DENIED) {
				m_bKeyRight = false;
			}
		}
		
	}*/
	
	@Override
	public void onLowMemory() {
		super.onLowMemory();
	}

	/*@Override
	public void onTerminate() {
		mapManager.destroy();
		mapManager = null;
		super.onTerminate();
	}*/
}
