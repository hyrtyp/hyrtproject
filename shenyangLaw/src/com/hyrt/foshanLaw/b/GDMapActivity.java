package com.hyrt.foshanLaw.b;

import java.util.List;

import org.json.JSONArray;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.search.core.AMapException;
import com.amap.api.search.core.LatLonPoint;
import com.amap.api.search.route.Route;
import com.amapv2.cn.apis.route.RouteOverlay;
import com.amapv2.cn.apis.util.AMapUtil;
import com.amapv2.cn.apis.util.Constants;
import com.hyrt.foshanLaw.R;

public class GDMapActivity extends Activity {

	private MapView aMapView;
	private AMap aMap;
	public static final int DRAW_LINE_FLAG = 1;
	public static final int DRAW_LINES_FLAG = 2;
	public static final int DRAW_POINT_FLAG = 3;
	public static final int DRAW_REG_FLAG = 4;
	public static final int DRAW_POINTS_FLAG = 5;
	public static final int DRAW_CLICKPOINTS_FLAG = 6;
	public static final String GIS_DATA_KEY = "GIS_DATA_KEY";
	public static final String GIS_KEY = "GIS_KEY";
	private JSONArray jsonArray;
	private ProgressDialog progDialog;
	private List<Route> routeResult;
	private Route route;
	private RouteOverlay routeOverlay;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gd_map);
		aMapView = (MapView) findViewById(R.id.gd_mapview);
		aMapView.onCreate(savedInstanceState);
		new Handler().post(new Runnable() {

			@Override
			public void run() {
				try {
					switch (getIntent().getIntExtra(GIS_KEY, DRAW_POINT_FLAG)) {
					case DRAW_POINT_FLAG:
						drawPoint();
						break;
					case DRAW_LINE_FLAG:
						drawLine();
						break;
					case DRAW_REG_FLAG:
						drawReg();
						break;
					case DRAW_POINTS_FLAG:
						drawLines();
						break;
					case DRAW_CLICKPOINTS_FLAG:
						drawClickPoints();
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
					Toast.makeText(GDMapActivity.this, "位置不合法",
							Toast.LENGTH_LONG).show();
					GDMapActivity.this.finish();
				}
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		aMapView.onResume();
		setUpMapIfNeeded();
	}

	private void setUpMapIfNeeded() {
		if (aMap == null) {
			aMap = aMapView.getMap();
			if (AMapUtil.checkReady(GDMapActivity.this, aMap)) {
				setUpMap();
			}
		}
	}

	private void setUpMap() {
		CameraPosition targetPosition = new CameraPosition.Builder().target(Constants.SHENYANG).zoom(18).build();
		aMap.animateCamera(CameraUpdateFactory.newCameraPosition(targetPosition));
	}

	@Override
	protected void onPause() {
		super.onPause();
		aMapView.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		aMapView.onDestroy();
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		aMapView.onLowMemory();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		aMapView.onSaveInstanceState(outState);
	}

	protected void drawClickPoints() throws Exception {
		jsonArray = new JSONArray(getIntent().getStringExtra(GIS_DATA_KEY));
		for (int i = 0; i < jsonArray.length(); i++) {
			double lng = StringToDouble(jsonArray.getJSONObject(i).getString(
					"LONG1"));
			double lat = StringToDouble(jsonArray.getJSONObject(i).getString(
					"LAT"));
			// 添加多个可点击的点
		}
	}

	private void drawReg() {
		final Button lockBtn = (Button) findViewById(R.id.lock_btn);
		lockBtn.setVisibility(View.VISIBLE);
		lockBtn.setOnClickListener(new OnClickListener() {

			private boolean isLock;

			@Override
			public void onClick(View v) {
				if (!isLock) {
					isLock = true;
					lockBtn.setBackgroundResource(R.drawable.lock_lock);
					((SelectionView) findViewById(R.id.selectview))
							.setLock(false);
				} else {
					isLock = false;
					lockBtn.setBackgroundResource(R.drawable.lock_unlock);
					((SelectionView) findViewById(R.id.selectview))
							.setLock(true);
				}
			}
		});
		((SelectionView) findViewById(R.id.selectview)).setMapView(aMap);
	}

	private void drawLine() {
		String[] latLon = MyMapActivity.latLon;
		String[] targetLatLon = getIntent().getStringExtra(GIS_DATA_KEY).split(
				"!@");
		if (latLon.length <= 1 || targetLatLon.length <= 1) {
			showToast("数据错误，无法查询路径！");
			return;
		}
		searchRouteResult(
				new LatLonPoint(Double.parseDouble(latLon[0]),
						Double.parseDouble(latLon[1])),
				new LatLonPoint(Double.parseDouble(targetLatLon[0]), Double
						.parseDouble(targetLatLon[1])));
	}

	public void searchRouteResult(LatLonPoint startPoint, LatLonPoint endPoint) {
		progDialog = ProgressDialog.show(GDMapActivity.this, null, "正在获取线路",
				true, true);
		final Route.FromAndTo fromAndTo = new Route.FromAndTo(startPoint,
				endPoint);
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {

				try {
					routeResult = Route.calculateRoute(GDMapActivity.this,
							fromAndTo, Route.WalkDefault);
					if (progDialog.isShowing()) {
						if (routeResult != null || routeResult.size() > 0)
							routeHandler.sendMessage(Message
									.obtain(routeHandler,
											Constants.ROUTE_SEARCH_RESULT));
					}
				} catch (AMapException e) {
					Message msg = new Message();
					msg.what = Constants.ROUTE_SEARCH_ERROR;
					msg.obj = e.getErrorMessage();
					routeHandler.sendMessage(msg);
				}
			}
		});
		t.start();

	}

	private Handler routeHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == Constants.ROUTE_SEARCH_RESULT) {
				progDialog.dismiss();
				if (routeResult != null && routeResult.size() > 0) {
					route = routeResult.get(0);
					if (route != null) {
						routeOverlay = new RouteOverlay(GDMapActivity.this,
								aMap, route);
						routeOverlay.removeFormMap();
						routeOverlay.addMarkerLine();
					}
				}
			} else if (msg.what == Constants.ROUTE_SEARCH_ERROR) {
				progDialog.dismiss();
				showToast((String) msg.obj);
			}
		}
	};

	public void showToast(String showString) {
		Toast.makeText(getApplicationContext(), showString, Toast.LENGTH_SHORT)
				.show();
	}

	private void drawLines() {
		String points = getIntent().getStringExtra(GIS_DATA_KEY);
		PolylineOptions polylineOptions = new PolylineOptions();
		String[] pointArray = points.split(";");
		for (int i = 0; i < pointArray.length; i++) {
			String[] latLng = pointArray[i].split(",");
			if (latLng.length <= 1)
				continue;
			if (i == 0) {
				CameraPosition targetPosition = new CameraPosition.Builder()
						.target(new LatLng(StringToDouble(latLng[1]),
								StringToDouble(latLng[0]))).zoom(18)
						.bearing(70).tilt(0).build();
				aMap.animateCamera(CameraUpdateFactory
						.newCameraPosition(targetPosition));
			}
			polylineOptions.add(new LatLng(StringToDouble(latLng[1]),
					StringToDouble(latLng[0])));
		}
		aMap.addPolyline(polylineOptions.color(Color.RED).width(5));
	}

	private void drawPoint() {
		String[] targetLatLon = getIntent().getStringExtra(GIS_DATA_KEY).split(
				"!@");
		aMap.addMarker(new MarkerOptions().position(
				new LatLng(StringToDouble(targetLatLon[0]),
						StringToDouble(targetLatLon[1]))));
		CameraPosition targetPosition = new CameraPosition.Builder()
				.target(new LatLng(StringToDouble(targetLatLon[0]),
						StringToDouble(targetLatLon[1]))).zoom(18).bearing(70)
				.tilt(0).build();
		aMap.animateCamera(CameraUpdateFactory
				.newCameraPosition(targetPosition));
	}

	private double StringToDouble(String str) {
		double d = Double.parseDouble(str);
		return d;
	}

}