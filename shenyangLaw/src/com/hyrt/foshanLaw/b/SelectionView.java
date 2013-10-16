package com.hyrt.foshanLaw.b;

import org.json.JSONArray;
import org.json.JSONException;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMap.InfoWindowAdapter;
import com.amap.api.maps.AMap.OnMarkerClickListener;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amapv2.cn.apis.util.AMapUtil;
import com.hyrt.cei.webservice.service.Service;
import com.hyrt.foshanLaw.R;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SelectionView extends View  implements OnMarkerClickListener,InfoWindowAdapter{
	private float sx, sy, ex, ey;
	private boolean lock = true;
	private AMap amap;
	private Context context;
	private String jsonResult;
	private JSONArray jsonArray;

	public AMap getMapView() {
		return amap;
	}

	public void setMapView(AMap amap) {
		this.amap = amap;
		this.amap.setOnMarkerClickListener(this);
		this.amap.setInfoWindowAdapter(this);
	}

	public SelectionView(Context context) {
		this(context, null);
		this.context = context;
	}

	public SelectionView(Context context, AttributeSet attr) {
		super(context, attr);
		this.context = context;
		setFocusable(true);
		setFocusableInTouchMode(true);
		setClickable(true);
		setLongClickable(true);
		setDrawingCacheEnabled(true);
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Paint mPaint = new Paint();
		mPaint.setColor(Color.argb(50, 255, 0, 0));
		canvas.drawRect(sx, sy, ex, ey, mPaint);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (lock) {
			return false;
		}
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			sx = event.getX();
			sy = event.getY();
			invalidate();
			break;
		case MotionEvent.ACTION_MOVE:
			ex = event.getX();
			ey = event.getY();
			invalidate();
			break;
		case MotionEvent.ACTION_UP:
			// this.lock=true;
			if (sx > ex) {
				ex = sx + ex;
				sx = ex - sx;
				ex = ex - sx;
			}
			if (sy > ey) {
				ey = sy + ey;
				sy = ey - sy;
				ey = ey - sy;
			}
			AlertDialog.Builder builder = new Builder(
					SelectionView.this.getContext());
			builder.setTitle("提示");
			builder.setMessage("您确认选取此区域！");
			builder.setPositiveButton("确认",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							LatLng startPoint = amap.getProjection().fromScreenLocation(new Point((int)sx,(int)sy));
							LatLng endPoint = amap.getProjection().fromScreenLocation(new Point((int)ex,(int)ey));
							drawEntPoints(startPoint, endPoint);
						}
					});
			builder.setNegativeButton("取消",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							SelectionView.this.invalidate();
							sx = sy = ex = ey = 0;
						}
					});
			builder.create().show();
			break;
		}
		return true;
	}

	public boolean isLock() {
		return lock;
	}

	public void setLock(boolean lock) {
		this.lock = lock;
	}

	/*
	 * String类型转换Double类型
	 */
	private double StringToDouble(String str) {
		double d = Double.parseDouble(str);
		return d;
	}

	/**
	 * 圈画后显示企业布局
	 */
	Handler handler = new Handler(){
		public void dispatchMessage(android.os.Message msg) {
			try {
				jsonArray = new JSONArray(jsonResult);
				onClearMap(amap);
				for (int i = 0; i < jsonArray.length(); i++) {
					LatLng startpt = new LatLng(StringToDouble(jsonArray
							.getJSONObject(i).getString("LAT")),
							StringToDouble(jsonArray.getJSONObject(i).getString(
									"LONG1")));// 创建一个点对象
					amap.addMarker(new MarkerOptions().title("公司名称 ：" + jsonArray.getJSONObject(i).getString("NAME"))
							.snippet( "公司地址 ：" + jsonArray.getJSONObject(i).getString("ADDRESS"))
							.position(startpt)
							);
					sx = sy = ex = ey = 0;
					SelectionView.this.invalidate();
				}
			} catch (JSONException e) {
				sx = sy = ex = ey = 0;
				SelectionView.this.invalidate();
				Toast.makeText(context, "区域内无企业实体..", Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			}
		}
	};
	
	/**
	 * 清空地图上所有已经标注的marker
	 */
	public void onClearMap(AMap amap) {
		if (AMapUtil.checkReady(context, amap)) {
			amap.clear();
		}
	}
	
	private void drawEntPoints(final LatLng startPoint,final LatLng endPoint) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				jsonResult = Service.queryEntByLatLong(startPoint.latitude,
						endPoint.latitude, startPoint.longitude, endPoint.longitude);
				handler.sendEmptyMessage(1);
			}
		}).start();
	}
	
	@Override
	public boolean onMarkerClick(Marker marker) {
		marker.showInfoWindow();
		return false;
	}

	@Override
	public View getInfoContents(Marker arg0) {
		return getInfoView(context, arg0.getTitle());
	}
	
	public View getInfoView(Context cnt, String title) {
		LinearLayout ll_parents = new LinearLayout(cnt);
		ll_parents.setOrientation(LinearLayout.VERTICAL);
		ll_parents.setBackgroundResource(R.drawable.custom_info_bubble);

		LinearLayout ll_child1 = new LinearLayout(cnt);
		ll_child1.setOrientation(LinearLayout.HORIZONTAL);
		ll_child1.setGravity(Gravity.AXIS_PULL_BEFORE);
		TextView titleVw = new TextView(cnt);
		titleVw.setTextColor(Color.BLACK);
		titleVw.setText(title);
		titleVw.setPadding(3, 0, 0, 3);
		ll_child1.addView(titleVw, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));
		ll_parents.addView(ll_child1, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));
		return ll_parents;
	}

	@Override
	public View getInfoWindow(Marker arg0) {
		return getInfoView(context, arg0.getTitle());
	}
	
}
