package com.hyrt.foshanLaw.b;

/*import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.LocationListener;
import com.baidu.mapapi.MKLocationManager;
import com.baidu.mapapi.MapActivity;*/
import com.hyrt.foshanLaw.R;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

public class MyMapActivity extends Activity {

	//private NavitApplication app;
	/*private MKLocationManager mLocationManager;
	LocationListener mLocationListener = null;*/
	public static Location location1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.map);
		//app = (NavitApplication) this.getApplication();
		/*if (app.mapManager == null) {
			app.mapManager = new BMapManager(getApplication());
			app.mapManager.init(app.mStrKey,new NavitApplication.MyGeneralListener());
		}
		app.mapManager.start();
		// 如果使用地图SDK，请初始化地图Activity
		super.initMapActivity(app.mapManager);
		// 当前位置定位
		mLocationManager = app.mapManager.getLocationManager();
		mLocationManager.enableProvider(MKLocationManager.MK_GPS_PROVIDER);
		mLocationManager.disableProvider(MKLocationManager.MK_NETWORK_PROVIDER);
		mLocationListener = new LocationListener() {

			@Override
			public void onLocationChanged(Location location) {
				if (location != null) {
					location1 = location;
				}
			}
		};
		mLocationManager.requestLocationUpdates(mLocationListener);*/
		this.finish();
	}
	
	//获取当前经纬度
	public static String[] getLatLon(){
		String[] latLon = {"22.95766","113.09573"};
		if(location1 != null){
			latLon = new String[2];
			latLon[0] = location1.getLatitude()+"";
			latLon[1] = location1.getLongitude()+"";
		}
		return latLon;
	}
	
	/*@Override
	protected boolean isRouteDisplayed() {
		return false;
	}*/

}
