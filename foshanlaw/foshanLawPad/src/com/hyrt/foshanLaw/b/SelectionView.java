package com.hyrt.foshanLaw.b;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.esri.android.map.Callout;
import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.MapView;
import com.esri.android.map.event.OnSingleTapListener;
import com.esri.core.geometry.Point;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.PictureMarkerSymbol;
import com.esri.core.symbol.TextSymbol;
import com.hyrt.cei.webservice.service.Service;
import com.hyrt.foshanLaw.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

public class SelectionView extends View {
	private float sx, sy, ex, ey;
	private boolean lock = true;
	private MapView mapView;
	private Context context;

	public MapView getMapView() {
		return mapView;
	}

	public void setMapView(MapView mapView) {
		this.mapView = mapView;
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
							Point startPoint = mapView.toMapPoint(sx, sy);
							Point endPoint = mapView.toMapPoint(ex, ey);
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
	 * 获取当前图层
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
	 * 圈画后显示企业布局
	 */
	private String jsonResult;
	private JSONArray jsonArray;
	private List<Point> points = new ArrayList<Point>();
	private GraphicsLayer mGraphicsLayer;
	Handler handler = new Handler(){
		public void dispatchMessage(android.os.Message msg) {
			try {
				jsonArray = new JSONArray(jsonResult);
				points.clear();
				for (int i = 0; i < jsonArray.length(); i++) {
					Point startpt = new Point(StringToDouble(jsonArray
							.getJSONObject(i).getString("LONG1")),
							StringToDouble(jsonArray.getJSONObject(i).getString(
									"LAT")));// 创建一个点对象
					points.add(startpt);
					Graphic startgp = new Graphic(startpt, new PictureMarkerSymbol(
							context.getResources().getDrawable(R.drawable.mark)));// 设置样式
					GetGraphicLayer(mapView).addGraphic(startgp);
					sx = sy = ex = ey = 0;
					SelectionView.this.invalidate();
				}
				final Callout callout = mapView.getCallout();//通过MapView获取Callout实例对象 
				callout.setStyle(R.xml.callout_style);//为Callout设置样式文件 
				final View popView = ((Activity)context).getLayoutInflater().inflate(
						R.layout.pop_entinfo, null);
				mapView.setOnSingleTapListener(new OnSingleTapListener() {
					private static final long serialVersionUID = 1L;

					@Override
					public void onSingleTap(float x, float y) {
						int gapOldValue = 1000;
						int currentIndex = -1;
						for(int i=0;i<points.size();i++){
							Point targetPoint = mapView.toScreenPoint(points.get(i));
							int gapValue = (int) ((x-targetPoint.getX())*(x-targetPoint.getX()) + (y-targetPoint.getY())* (y-targetPoint.getY()));
							if(gapOldValue >gapValue){
								currentIndex = i;
								gapOldValue = gapValue;
							}	
						}
						callout.setMaxHeight(700);
						callout.setMaxWidth(1500);
						callout.hide();
						callout.setOffset(0, -15);//设置偏移量 
						try {
							((TextView)popView.findViewById(R.id.content)).setText("公司名称 ：" + jsonArray.getJSONObject(currentIndex).getString("NAME") + "\n" 
									+ "公司地址 ：" + jsonArray.getJSONObject(currentIndex).getString("ADDRESS") + "\n" +"企业注册号 ："+ jsonArray.getJSONObject(currentIndex).getString("ENROL"));
							callout.show(points.get(currentIndex), popView);//设置弹出窗显示的内容 
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	};
	
	private void drawEntPoints(final Point startPoint,final Point endPoint) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				jsonResult = Service.queryEntByLatLong(startPoint.getY(),
						endPoint.getY(), startPoint.getX(), endPoint.getX());
				handler.sendEmptyMessage(1);
			}
		}).start();
	}
	
}
