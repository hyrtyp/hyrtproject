package com.hyrt.foshanLaw.remoteService;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

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
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Criteria;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;

public class StockQuoteService extends Service {

	public String[] oldLatLon;
	private LocationManager lm;
	private static final String TAG = "GpsActivity";
	public static String PIDS = "";

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
			String[] latLon = MyMapActivity.getLatLon();
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
		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		// 为获取地理位置信息时设置查询条件
		String bestProvider = lm.getBestProvider(getCriteria(), true);
		if(bestProvider != null){
			// 获取位置信息
			// 如果不设置查询要求，getLastKnownLocation方法传人的参数为LocationManager.GPS_PROVIDER
			Location location = lm.getLastKnownLocation(bestProvider);
			MyMapActivity.location1 = location;
			// 监听状态
			lm.addGpsStatusListener(listener);
		}
		// 绑定监听，有4个参数
		// 参数1，设备：有GPS_PROVIDER和NETWORK_PROVIDER两种
		// 参数2，位置信息更新周期，单位毫秒
		// 参数3，位置变化最小距离：当位置距离变化超过此值时，将更新位置信息
		// 参数4，监听
		// 备注：参数2和3，如果参数3不为0，则以参数3为准；参数3为0，则通过时间来定时更新；两者为0，则随时刷新

		// 1秒更新一次，或最小位移变化超过1米更新一次；
		// 注意：此处更新准确度非常低，推荐在service里面启动一个Thread，在run中sleep(10000);然后执行handler.sendMessage(),更新位置
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1,locationListener);
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
		lm.removeUpdates(locationListener);
	}

	// 位置监听
	private LocationListener locationListener = new LocationListener() {

		/**
		 * 位置信息变化时触发
		 */
		public void onLocationChanged(Location location) {
			MyMapActivity.location1 = location;
			//Log.i(TAG, "时间：" + location.getTime());
			//Log.i(TAG, "经度：" + location.getLongitude());
			//Log.i(TAG, "纬度：" + location.getLatitude());
			//Log.i(TAG, "海拔：" + location.getAltitude());
		}

		/**
		 * GPS状态变化时触发
		 */
		public void onStatusChanged(String provider, int status, Bundle extras) {
			switch (status) {
			// GPS状态为可见时
			case LocationProvider.AVAILABLE:
				//Log.i(TAG, "当前GPS状态为可见状态");
				break;
			// GPS状态为服务区外时
			case LocationProvider.OUT_OF_SERVICE:
				//Log.i(TAG, "当前GPS状态为服务区外状态");
				break;
			// GPS状态为暂停服务时
			case LocationProvider.TEMPORARILY_UNAVAILABLE:
				//Log.i(TAG, "当前GPS状态为暂停服务状态");
				break;
			}
		}

		/**
		 * GPS开启时触发
		 */
		public void onProviderEnabled(String provider) {
			MyMapActivity.location1 = lm.getLastKnownLocation(provider);
		}

		/**
		 * GPS禁用时触发
		 */
		public void onProviderDisabled(String provider) {
		}

	};

	// 状态监听
	GpsStatus.Listener listener = new GpsStatus.Listener() {
		public void onGpsStatusChanged(int event) {
			switch (event) {
			// 第一次定位
			case GpsStatus.GPS_EVENT_FIRST_FIX:
				//Log.i(TAG, "第一次定位");
				break;
			// 卫星状态改变
			case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
				//Log.i(TAG, "卫星状态改变");
				// 获取当前状态
				GpsStatus gpsStatus = lm.getGpsStatus(null);
				// 获取卫星颗数的默认最大值
				int maxSatellites = gpsStatus.getMaxSatellites();
				// 创建一个迭代器保存所有卫星
				Iterator<GpsSatellite> iters = gpsStatus.getSatellites()
						.iterator();
				int count = 0;
				while (iters.hasNext() && count <= maxSatellites) {
					GpsSatellite s = iters.next();
					count++;
				}
				//System.out.println("搜索到：" + count + "颗卫星");
				break;
			// 定位启动
			case GpsStatus.GPS_EVENT_STARTED:
				//Log.i(TAG, "定位启动");
				break;
			// 定位结束
			case GpsStatus.GPS_EVENT_STOPPED:
				//Log.i(TAG, "定位结束");
				break;
			}
		};
	};

	/**
	 * 返回查询条件
	 * 
	 * @return
	 */
	private Criteria getCriteria() {
		Criteria criteria = new Criteria();
		// 设置定位精确度 Criteria.ACCURACY_COARSE比较粗略，Criteria.ACCURACY_FINE则比较精细
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		// 设置是否要求速度
		criteria.setSpeedRequired(false);
		// 设置是否允许运营商收费
		criteria.setCostAllowed(false);
		// 设置是否需要方位信息
		criteria.setBearingRequired(false);
		// 设置是否需要海拔信息
		criteria.setAltitudeRequired(false);
		// 设置对电源的需求
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		return criteria;
	}

}
