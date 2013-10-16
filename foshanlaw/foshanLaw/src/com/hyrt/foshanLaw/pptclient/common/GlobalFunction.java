/**
 * 
 */
package com.hyrt.foshanLaw.pptclient.common;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

import com.hyrt.foshanLaw.R;
import com.hyrt.foshanLaw.pptclient.DialogActivity;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Log;
import android.view.View;

/**
 * Description:
 * 
 * @author 郑伟
 * @Date 2013-1-7
 * @Copyright:2013-1-7
 * @Company:华源润通（北京）科技有限公司重庆分公司
 */
public class GlobalFunction {
	/**
	 * @param context
	 * @return 网络是否可用
	 */
	public static boolean checkNetWork(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
		if (activeNetInfo != null && activeNetInfo.isConnected()) {
			return true;
		}
		return false;
	}

	/**
	 * 服务是否已经启动
	 * 
	 * @param context
	 * @param _servicename
	 * @return
	 */
	public static boolean isRunning(Context context ) {
		boolean flag = false;
		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningServiceInfo> services = activityManager
				.getRunningServices(50);
		Iterator<RunningServiceInfo> l = services.iterator();
		
		while (l.hasNext()) {
			RunningServiceInfo si = (RunningServiceInfo) l.next();
			if(si.service.getPackageName().startsWith("com.hyrt.foshanLaw")){
				if(si.service.getClassName().equals("com.hyrt.foshanLaw.pptclient.PPTService")){
				flag = true;
				break;
				}
			}
		}
		return flag;
	}

	/**
	 * 定时广播
	 * 
	 * @param context
	 * @param intent
	 *            设定好的intent
	 * @param delaySecond
	 *            推迟秒数
	 */
	public static void sendPendingBroadcast(Context context, Intent intent,
			int delaySecond) {
		Calendar c = Calendar.getInstance(Locale.CHINA);
		c.add(Calendar.SECOND, delaySecond);
		Random rd = new Random();
		PendingIntent pi = PendingIntent.getBroadcast(context,
				rd.nextInt(1000), intent, 0);
		AlarmManager am = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		am.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi);
	}

	/**
	 * @return 是否存在SD卡
	 */
	static boolean hasSDCard() {
		boolean flag = true;
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			flag = true;
		} else {
			flag = false;
		}
		return flag;
	}

	/**
	 * @return 临时文件存放目录
	 */
	public static File getDirectory() {
		if (hasSDCard()) {
			return Environment.getExternalStorageDirectory();
		} else {
			return Environment.getDownloadCacheDirectory();
		}
	}

	/**
	 * @param path
	 *            文件夹路径
	 * @param isCreate
	 *            是否创建
	 * @return 文件夹是否存在或者是否创建成功
	 */
	public static synchronized boolean hasDirectory(String path,
			boolean isCreate) {
		boolean flag = false;
		File file = new File(path);
		if (file.exists()) {
			flag = true;
		} else if (isCreate) {
			file.mkdirs();
			if (file.exists()) {
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * @param l
	 *            时间的long值
	 * @return YYYY-MM=DD HH:MM:SS的时间表达式
	 */
	public static String GetDateTime(long l) {
		if (l > 0) {
			Calendar c = Calendar.getInstance(Locale.CHINA);
			c.setTimeInMillis(l);
			int myear = c.get(Calendar.YEAR);
			int mmonth = c.get(Calendar.MONTH) + 1;
			int mday = c.get(Calendar.DAY_OF_MONTH);
			int hour = c.get(Calendar.HOUR_OF_DAY);
			int min = c.get(Calendar.MINUTE);
			int sec = c.get(Calendar.SECOND);
			String re = String.format("%d-%02d-%02d %02d:%02d:%02d", myear,
					mmonth, mday, hour, min, sec);
			return re;
		} else {
			return "";
		}
	}

	/**
	 * @return 当前时间字符串
	 */
	public static String GetDateTime() {
		Calendar c = Calendar.getInstance(Locale.CHINA);
		int myear = c.get(Calendar.YEAR);
		int mmonth = c.get(Calendar.MONTH) + 1;
		int mday = c.get(Calendar.DAY_OF_MONTH);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int min = c.get(Calendar.MINUTE);
		int sec = c.get(Calendar.SECOND);
		String re = String.format("%d-%02d-%02d %02d:%02d:%02d", myear, mmonth,
				mday, hour, min, sec);
		return re;
	}

	public static String DecodeDateTime(String str) {
		if (str.length() == 14) {
			String year = str.substring(0, 4);
			String month = str.substring(4, 6);
			String day = str.substring(6, 8);
			String hour = str.substring(8, 10);
			String min = str.substring(10, 12);
			String second = str.substring(12, 14);
			String re = String.format("%s-%s-%s %s:%s:%s", year, month, day,
					hour, min, second);
			return re;
		} else {
			return str;
		}
	}

	/**
	 * YYYYMMDDHHMMSS
	 * 
	 * @return
	 */
	public static String GetDateTimeShort() {
		Calendar c = Calendar.getInstance(Locale.CHINA);
		int myear = c.get(Calendar.YEAR);
		int mmonth = c.get(Calendar.MONTH) + 1;
		int mday = c.get(Calendar.DAY_OF_MONTH);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int min = c.get(Calendar.MINUTE);
		int sec = c.get(Calendar.SECOND);
		String tm = String.format("%d%02d%02d%02d%02d%02d", myear, mmonth,
				mday, hour, min, sec);
		return tm;
	}

	/*
	 * 创建UID码
	 */
	public static String GetUID() {
		UUID uuid = UUID.randomUUID();
		String a = uuid.toString().replace("-", "");
		return a;
	}

	/**
	 * 取得临时文件存放路径
	 * 
	 * @param filename
	 * @return
	 */
	public static String GetTmpPath(String filename) {
		File file;
		if (hasSDCard()) {
			String s = Environment.getExternalStorageDirectory().getPath()
					+ "/PTT";
			file = new File(s);
			if (file.exists() == false) {
				file.mkdirs();
			}
		} else {
			file = Environment.getDownloadCacheDirectory();
		}
		String s = file.getPath() + "/" + filename;
		return s;
	}

	/**
	 * 文件是否存在
	 * 
	 * @return
	 */
	public static boolean fileExsit(String filepath) {
		if ("".equals(filepath) || "0".equals(filepath))
			return false;
		File f = new File(filepath);
		if (f.exists()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * UI向Service发送广播的方法
	 * 
	 * @param context
	 * @param sessionid
	 *            会话id，如果没有，传""
	 */
	public static void sendBroadcastToService(Context context, String sessionid) {
		Intent ie = new Intent();
		ie.setAction(CommKey.svr_receiver);
		ie.putExtra("type", "type_update");
		ie.putExtra("sessionid", sessionid);
		context.sendBroadcast(ie);
	}

	/*
	 * 取得时间
	 */
	public static String GetDate() {
		Calendar c = Calendar.getInstance(Locale.CHINA);
		int myear = c.get(Calendar.YEAR);
		int mmonth = c.get(Calendar.MONTH) + 1;
		int mday = c.get(Calendar.DAY_OF_MONTH);
		String re = String.format("%d-%02d-%02d", myear, mmonth, mday);
		return re;
	}

	/**
	 * 取得用户ID
	 * 
	 * @param context
	 * @return
	 */
	public static String getUserId(Context context) {
		SharedPreferences pre = context.getSharedPreferences("PPT",
				Context.MODE_PRIVATE);
		String re = pre.getString("USERID", "");

		return re;
	}

	/**
	 * 设置弹出通知
	 * 
	 * @param context
	 * @param userid
	 */
	public static void setUserId(Context context, String userid) {
		SharedPreferences.Editor edit = context.getSharedPreferences("PPT",
				Context.MODE_PRIVATE).edit();
		edit.putString("USERID", userid);
		edit.commit();
	}

 
	/**
	 * 是否需要通知
	 * @param context
	 * @return
	 */
	static boolean getNotiSetting(Context context) {
		SharedPreferences pre = context.getSharedPreferences("PPT",
				Context.MODE_PRIVATE);
		int p=pre.getInt("ISNOTICE", 0);
		//ISNOTICE 越大，越不需要通知
		boolean re ;
		if(p>0){
			re=false;
		}else{
			re=true;
		}
		return re;
	}

	/**
	 * 设置通知，
	 * 
	 * @param context
	 * @param flag  true=需要通知 false=不需要
	 */
	public static void setNotiSetting(Context context, boolean flag) {
		SharedPreferences sh= context.getSharedPreferences("PPT",
				Context.MODE_PRIVATE);
		SharedPreferences.Editor edit = sh.edit();
		int p=sh.getInt("ISNOTICE", 0);   
		//ISNOTICE 越大，越不需要通知
		if(flag){
			if(p>0)
				p--;
		}else{
			p++;
		}
		 
		edit.putInt("ISNOTICE", p);
		edit.commit();
	}
	
	public static void init_NotiSetting(Context context){
		SharedPreferences.Editor edit= context.getSharedPreferences("PPT",
				Context.MODE_PRIVATE).edit();
		edit.putInt("ISNOTICE",0);
		edit.commit();
	}

	/**
	 * 通用提示框
	 * 
	 * @param context
	 * @param msg
	 */
	public static void ShowDialog(Context context, String msg) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(msg).setPositiveButton("确定", null);
		AlertDialog ad = builder.create();
		ad.show();
	}
	
	/**
	 * 通用提示框
	 * 
	 * @param context
	 * @param v 视图
	 */
	public static void ShowDialog(Context context,View v ) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setView(v).setPositiveButton("确定", null);
		AlertDialog ad = builder.create();
		ad.show();
	}
	
	

	/**
	 * 弹出通知
	 * 
	 * @param context
	 * @param sessionid
	 *            会话id
	 * @param sessionname
	 *            会话名称
	 * @param isgroup
	 *            是否分组
	 * @param iscreate
	 *            是否新建
	 * @param receobj
	 *            接收人
	 */
	public static void showNotifa(Context context, String sessionid,
			String sessionname, boolean isgroup, ArrayList<String> receobj ) {
		if (getNotiSetting(context)==true) {
			
			Notification notification = new Notification();
			notification.icon = R.drawable.icon;
			notification.defaults = Notification.DEFAULT_LIGHTS;

			notification.defaults |= Notification.DEFAULT_SOUND;

			// notification.defaults |= Notification.DEFAULT_VIBRATE;

			notification.flags |= Notification.FLAG_AUTO_CANCEL;

			notification.when = System.currentTimeMillis();
			notification.tickerText = sessionname;

			// Intent intent = new Intent(context,
			// NotificationDetailsActivity.class);
			Intent showDialog = new Intent(context, DialogActivity.class);
			showDialog.putExtra("sessionid", sessionid);
			showDialog.putExtra("sessionname", sessionname);
			showDialog.putExtra("isgroup", isgroup);
			showDialog.putExtra("iscreate", false);
			showDialog.putStringArrayListExtra("receobj", receobj);

			PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
					showDialog, PendingIntent.FLAG_UPDATE_CURRENT);
			notification.setLatestEventInfo(context, "新消息", sessionname,
					contentIntent);
			NotificationManager notificationManager = (NotificationManager) context
					.getSystemService(Context.NOTIFICATION_SERVICE);
			Random random = new Random(System.currentTimeMillis());
			notificationManager.notify(random.nextInt(), notification);
		}
	}
}
