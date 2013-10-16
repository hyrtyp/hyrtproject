package com.hyrt.foshanLaw.b;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.edit.arcgis.android.Analysis;
import com.edit.arcgis.android.Operation;
import com.esri.android.map.Callout;
import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.MapView;
import com.esri.android.map.event.OnSingleTapListener;
import com.esri.core.geometry.Point;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.PictureMarkerSymbol;
import com.hyrt.foshanLaw.R;

public class ArgGisActivity extends Activity {

	private MapView mMapView;
	public static final int DRAW_LINE_FLAG = 1;
	public static final int DRAW_LINES_FLAG = 2;
	public static final int DRAW_POINT_FLAG = 3;
	public static final int DRAW_REG_FLAG = 4;
	public static final int DRAW_POINTS_FLAG = 5;
	public static final int DRAW_CLICKPOINTS_FLAG = 6;
	public static final String GIS_DATA_KEY = "GIS_DATA_KEY";
	public static final String GIS_KEY = "GIS_KEY";
	private GraphicsLayer mGraphicsLayer;
	private Operation operation;
	private JSONArray jsonArray;
	private List<Point> points = new ArrayList<Point>();

	/**
	 * 检测网络是否可用
	 * 
	 * @return
	 */
	public boolean isNetworkConnected() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return ni != null && ni.isConnectedOrConnecting();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.arg_map);
		mMapView = (MapView) findViewById(R.id.arg_mapview);
		Analysis dr = new Analysis();
		dr.EditServerUrl("http://10.20.5.131:8081/Android/config.xml");
		dr.EditClusteringUrl("http://10.20.5.131:8081/Android/ClusteringConfig.xml");
		operation = new Operation();
		if (!isNetworkConnected())
			operation.LoadLocalMap(mMapView);
		else
			operation.LoadMap(mMapView);
		new Handler().post(new Runnable() {

			@Override
			public void run() {
				try {
					switch (getIntent().getIntExtra(GIS_KEY, DRAW_POINT_FLAG)) {
					case DRAW_POINT_FLAG:
						drawPoint();
						break;
					case DRAW_LINE_FLAG:
						new Handler().postDelayed(new Runnable() {

							@Override
							public void run() {
								drawLine();
							}
						}, 5000);
						break;
					case DRAW_REG_FLAG:
						drawReg();
						break;
					case DRAW_POINTS_FLAG:
						new Handler().postDelayed(new Runnable() {

							@Override
							public void run() {
								drawLines();
							}
						}, 5000);
						break;
					case DRAW_CLICKPOINTS_FLAG:
						drawClickPoints();
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
					//Toast.makeText(ArgGisActivity.this, "位置不合法",Toast.LENGTH_LONG).show();
					//ArgGisActivity.this.finish();
				}
			}
		});
	}

	protected void drawClickPoints() throws Exception {
		jsonArray = new JSONArray(getIntent().getStringExtra(GIS_DATA_KEY));
		points.clear();
		for (int i = 0; i < jsonArray.length(); i++) {
			try {
				Point startpt = new Point(xConversion(StringToDouble(jsonArray
						.getJSONObject(i).getString("LONG1"))),
						yConversion(StringToDouble(jsonArray.getJSONObject(i).getString(
								"LAT"))));// 创建一个点对象
				points.add(startpt);
				Graphic startgp = new Graphic(startpt, new PictureMarkerSymbol(
						ArgGisActivity.this.getResources().getDrawable(
								R.drawable.mark)));// 设置样式
				GetGraphicLayer(mMapView).addGraphic(startgp);
			} catch (Exception e) {
				points.add(new Point(10000000, 10000000));
				e.printStackTrace();
			}
		}
		final Callout callout = mMapView.getCallout();// 通过MapView获取Callout实例对象
		callout.setStyle(R.xml.callout_style);// 为Callout设置样式文件
		final View popView = ArgGisActivity.this.getLayoutInflater().inflate(
				R.layout.pop_entinfo, null);
		mMapView.setOnSingleTapListener(new OnSingleTapListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void onSingleTap(float x, float y) {
				int currentIndex = -1;
				HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
				for (int i = 0; i < points.size(); i++) {
					if (points.get(i).getX() != 10000000) {
						Point targetPoint = mMapView.toScreenPoint(points
								.get(i));
						Double gapValue = (x - targetPoint.getX())
								* (x - targetPoint.getX())
								+ (y - targetPoint.getY())
								* (y - targetPoint.getY());
						map.put(gapValue.intValue(), i);
						System.out.println(gapValue.intValue() + " : " + i);
					}
				}
				Set<Integer> keyValues = map.keySet();
				callout.setMaxHeight(300);
				callout.setMaxWidth(300);
				callout.hide();
				callout.setOffset(0, -15);// 设置偏移量
				try {
					Iterator<Integer> iterator = keyValues.iterator();
					int oldDistance = 1000;
					while (iterator.hasNext()) {
						int newDistance = iterator.next();
						if (newDistance < oldDistance) {
							currentIndex = map.get(newDistance);
							oldDistance = newDistance;
						}
					}
					((TextView) popView.findViewById(R.id.content))
							.setText("人员姓名 ："
									+ jsonArray.getJSONObject(currentIndex)
											.getString("NAME"));
					callout.show(points.get(currentIndex), popView);// 设置弹出窗显示的内容
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
	}

	/**
	 * 圈画操作
	 */
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
		((SelectionView) findViewById(R.id.selectview)).setMapView(mMapView);
	}

	private void drawLine() {
		final Handler handler = new Handler();

		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Analysis analysis = new Analysis();
					String[] latLon = MyMapActivity.getLatLon();
					String[] targetLatLon = getIntent().getStringExtra(GIS_DATA_KEY).split("!@");
					/*String[] latLon = {"22.95963","113.08315"};
					String[] targetLatLon = {"22.95854","113.08919"};*/
					analysis.RouteAnalysisByPoints(mMapView, getResources()
							.getDrawable(R.drawable.mark),latLon[1] + "," + latLon[0] + ";" + targetLatLon[1] + "," + targetLatLon[0]);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}).start();

	}

	private void drawLines() {
		try {
			jsonArray = new JSONArray(getIntent().getStringExtra(GIS_DATA_KEY));
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (int i = 0; i < jsonArray.length(); i++) {
			try {
				Point startpt = new Point(xConversion(StringToDouble(jsonArray
						.getJSONObject(i).getString("LONG1"))),
						yConversion(StringToDouble(jsonArray.getJSONObject(i).getString(
								"LAT"))));// 创建一个点对象
				Graphic startgp = new Graphic(startpt, new PictureMarkerSymbol(
						ArgGisActivity.this.getResources().getDrawable(
								R.drawable.mark)));// 设置样式
				GetGraphicLayer(mMapView).addGraphic(startgp);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void drawPoint() {
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				String[] targetLatLon = getIntent()
						.getStringExtra(GIS_DATA_KEY).split("!@");
				operation.CenterAt(mMapView,
						Double.parseDouble(targetLatLon[1]),
						Double.parseDouble(targetLatLon[0]));
				Point startpt = new Point(xConversion(StringToDouble(targetLatLon[1])),
						yConversion(StringToDouble(targetLatLon[0])));// 创建一个点对象
				Graphic startgp = new Graphic(startpt, new PictureMarkerSymbol(
						getResources().getDrawable(R.drawable.mark)));// 设置样式
				GetGraphicLayer(mMapView).addGraphic(startgp);
			}
		}, 5000);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mMapView.pause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mMapView.unpause();
	}

	/*
	 * String类型转换Double类型
	 */
	/*private double StringToDouble(String str) {
		double d = Double.parseDouble(str);
		return d;
	}*/

	
	private double StringToDouble(String str) { if (str.length() > 10) str =
	 str.substring(0, 9); double d = Double.parseDouble(str); return d; }

	/**
	 * 获取当前图层
	 * 
	 * @param map
	 * @return
	 */
	private GraphicsLayer GetGraphicLayer(MapView map) {
		if (mGraphicsLayer == null) {
			mGraphicsLayer = new GraphicsLayer();
			map.addLayer(mGraphicsLayer);
		}
		return mGraphicsLayer;
	}

	/**
	 * x坐标 偏移
	 * 
	 * @param x
	 *            X坐标
	 * @return number 修正后的X坐标
	 */
	public double xConversion(Double x) {
		double intNum = Math.floor(x);
		double decimalNum = x - intNum;
		double number = intNum + decimalNum / 0.9420202972381885;
		return number;
	}

	/**
	 * y坐标偏移
	 * 
	 * @param y
	 *            Y坐标
	 * @return number 修正后的X坐标
	 */
	public double yConversion(Double y) {
		double intNum = Math.floor(y);
		double decimalNum = y - intNum;
		double number = intNum + decimalNum / 1.00263135017959;
		return number;
	}

}