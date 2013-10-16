package com.hyrt.foshanLaw.remoteService;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.hyrt.cei.util.MyTools;
import com.hyrt.cei.util.TimeOutHelper;
import com.hyrt.cei.util.WriteOrRead;
import com.hyrt.foshanLaw.R;
import com.hyrt.foshanLaw.b.EntInfoListActivity;
import com.hyrt.foshanLaw.b.MyMapActivity;
import com.hyrt.foshanLaw.pptclient.PPTService;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;

public class StockQuoteService extends Service implements
AMapLocationListener{

	public String[] oldLatLon;
	public static String PIDS = "";
	private LocationManagerProxy locationManager;

	// 唯一的轮询handler
	public Handler handler = new Handler() {
		@Override
		public void dispatchMessage(Message msg) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					updateUserLatLon();
				}
			}).start();
			// 移除以前的轮询任务。
			handler.removeCallbacks(loopRunnable);
			// 新增轮询任务。
			handler.postDelayed(loopRunnable,
					Integer.parseInt(MyTools.UPDATE_GISTIME));
		}
	};

	// 检查公共或者任务
	public Handler checkHandler = new Handler() {
		@Override
		public void dispatchMessage(Message msg) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					notifTask();
					notiNoticeNum();
				}
			}).start();
			// 移除以前的轮询任务。
			checkHandler.removeCallbacks(ckeckLoopRunnable);
			// 新增轮询任务。
			checkHandler.postDelayed(ckeckLoopRunnable,
					Integer.parseInt(MyTools.CHECK_TASKTIME));
		}
	};

	private void updateUserLatLon() {
		File file = new File(MyTools.LOCAL_PATH + "gis.txt");
		if (file.exists())
			file.delete();
		try {
			FileWriter writer = new FileWriter(file);
			writer.write(System.currentTimeMillis() + "");
			String[] latLon = MyMapActivity.latLon;
			if (latLon != null && !latLon.equals(oldLatLon) ) {
				writer.write(latLon[0] + "," + latLon[1]);
				String userid = WriteOrRead.read(MyTools.LOCAL_PATH,
						"userid.txt");
				if (!userid.equals("")) {
					com.hyrt.cei.webservice.service.Service.updateUser(
							latLon[1], latLon[0], userid);
					oldLatLon = latLon;
				}
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 通知任务
	 */
	private void notifTask() {
		String reseauid = WriteOrRead.read(MyTools.LOCAL_PATH, "reseauid.txt");
		if (!reseauid.equals("")) {
			String jsonResult = com.hyrt.cei.webservice.service.Service
					.queryTimeTask(reseauid, 1);
			try {
				JSONArray dataEntInfos = null;
				String errorCode = TimeOutHelper.ALDATA_FLAG;
				try {
					dataEntInfos = new JSONArray(jsonResult);
				} catch (Exception e) {
					errorCode = new JSONObject(jsonResult)
							.getString("errorcode");
					e.printStackTrace();
				}
				if (!errorCode.equals(TimeOutHelper.NODATA_FLAG)
						&& dataEntInfos.length() > 0) {
					PIDS = jsonResult;
					// 通知
					NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
					Notification notification = new Notification(
							R.drawable.icon, "消息提醒", System.currentTimeMillis());
					notification.defaults = Notification.DEFAULT_LIGHTS;
					notification.defaults |= Notification.DEFAULT_SOUND;
					Intent intent = new Intent(StockQuoteService.this,
							EntInfoListActivity.class);
					intent.putExtra("listFlag",
							EntInfoListActivity.TASKLIST_FLAG);
					PendingIntent contentIntent = PendingIntent.getActivity(
							StockQuoteService.this, 0, intent, 0);
					notification.setLatestEventInfo(StockQuoteService.this,
							"消息提醒", "有未完成的任务，请点击查看！", contentIntent);
					if (nm != null && notification != null) {
						nm.notify(R.string.time_task, notification);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void notiNoticeNum() {
		String jsonResult = com.hyrt.cei.webservice.service.Service
				.queryNoticeCount();
		try {
			JSONObject jsonObject = new JSONObject(jsonResult);
			SharedPreferences settings = getSharedPreferences("userinfo",
					Activity.MODE_PRIVATE);
			int count = settings.getInt("count", 0);
			if (count < Integer.parseInt(jsonObject.getString("count"))) {
				NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
				Notification notification = new Notification(R.drawable.icon,
						"消息提醒", System.currentTimeMillis());
				notification.defaults = Notification.DEFAULT_LIGHTS;
				notification.defaults |= Notification.DEFAULT_SOUND;
				Intent intent = new Intent(StockQuoteService.this,
						EntInfoListActivity.class);
				intent.putExtra("listFlag", EntInfoListActivity.NOTICELIST_FLAG);
				PendingIntent contentIntent = PendingIntent.getActivity(
						StockQuoteService.this, 1, intent, 1);
				notification.setLatestEventInfo(StockQuoteService.this, "消息提醒",
						"有新公告，请点击查看！", contentIntent);
				if (nm != null && notification != null) {
					nm.notify(R.string.app_name, notification);
				}
				Editor editor = settings.edit();
				editor.putInt("count",
						Integer.parseInt(jsonObject.getString("count")));
				editor.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 触发任务
	Runnable loopRunnable = new Runnable() {

		@Override
		public void run() {
			handler.sendEmptyMessage(1);
		}
	};

	// 触发任务
	Runnable ckeckLoopRunnable = new Runnable() {

		@Override
		public void run() {
			checkHandler.sendEmptyMessage(1);
		}
	};

	public class StockQuoteServiceImpl extends IStockQuoteService.Stub {

		@Override
		public double getQuote(String ticker) throws RemoteException {
			return 20.0;
		}

	}

	@Override
	public void onCreate() {
		super.onCreate();
		handler.sendEmptyMessage(1);
		checkHandler.sendEmptyMessage(1);
		locationManager = LocationManagerProxy.getInstance(this);
		// Location API定位采用GPS和网络混合定位方式，时间最短是5000毫秒
		locationManager.requestLocationUpdates(
				LocationProviderProxy.AMapNetwork, 5000, 10, this);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		//by zhengwei 交由“登陆”去启动
		//PPTService.Start(StockQuoteService.this);
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return new StockQuoteServiceImpl();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		//by zhengwei 服务结束的时候 结束对讲服务
		PPTService.Stop(StockQuoteService.this);
		if (locationManager != null) {
			locationManager.removeUpdates(this);
			locationManager.destory();
		}
		locationManager = null;
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLocationChanged(AMapLocation location) {
		if (location != null) {
			if (location.getProvider()
					.equals(LocationManagerProxy.GPS_PROVIDER)) {
				MyMapActivity.latLon[0] = location.getLatitude()+"";
				MyMapActivity.latLon[1] = location.getLongitude()+"";
			}
		}
		
	}

}
