package com.edit.arcgis.android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;

import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.Layer;
import com.esri.android.map.MapOnTouchListener;
import com.esri.android.map.MapView;
import com.esri.core.geometry.Envelope;
import com.esri.core.geometry.Line;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.Polyline;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.FillSymbol;
import com.esri.core.symbol.LineSymbol;
import com.esri.core.symbol.MarkerSymbol;
import com.esri.core.symbol.PictureMarkerSymbol;
import com.esri.core.symbol.SimpleFillSymbol;
import com.esri.core.symbol.SimpleLineSymbol;
import com.esri.core.symbol.SimpleMarkerSymbol;
import com.esri.core.symbol.SimpleMarkerSymbol.STYLE;

public class DragEnvelopeListener extends MapOnTouchListener {

	public DragEnvelopeListener(Context context, MapView view) {

		super(context, view);
		this.tempLayer = new GraphicsLayer();
		this.map = view;
		this.map.addLayer(this.tempLayer);

		markerSymbol = new SimpleMarkerSymbol(Color.BLACK, 16,
				SimpleMarkerSymbol.STYLE.CIRCLE);
		lineSymbol = new SimpleLineSymbol(Color.BLACK, 2);
		fillSymbol = new SimpleFillSymbol(Color.BLUE);
		fillSymbol.setAlpha(50);

		if (envelope == null) {
			envelope = new Envelope();
			drawGraphic = new Graphic(envelope, fillSymbol);
			graphicID = tempLayer.addGraphic(drawGraphic);
		}
		//
		// drawListener= new DragEnvelopeListener(this.map.getContext(),
		// this.map);
		// defaultListener = new MapOnTouchListener(this.map.getContext(),
		// this.map);
	}

	public static final int ENVELOPE = 1;
	public static final int TOPLOPE = 2;
	private Handler handler;
	private static String str;
	private Envelope envelope;
	private MapView map = null;
	private Point startPoint = null;
	private Graphic drawGraphic;
	private int graphicID;
	public GraphicsLayer mGraphicsLayer;
	public GraphicsLayer mPointGraphicsLayer;
	private static int drawType;

	public int getDrawType() {
		return drawType;
	}

	@SuppressWarnings("static-access")
	public void setDrawType(int drawType) {
		this.drawType = drawType;
	}

	private MarkerSymbol markerSymbol;
	private LineSymbol lineSymbol;
	private FillSymbol fillSymbol;
	private GraphicsLayer tempLayer;
	@SuppressWarnings("unused")
	private DragEnvelopeListener drawListener;
	@SuppressWarnings("unused")
	private MapOnTouchListener defaultListener;
	public static boolean active;

	@SuppressWarnings("static-access")
	public void activate(int drawType, Handler handler, String str) {
		if (this.map == null)
			return;
		this.deactivate();
		this.handler = handler;
		// this.map.setOnTouchListener(drawListener);
		// this.drawType = drawType;
		this.drawType = drawType;
		this.str = str;
		active = true;
		switch (this.drawType) {
		case ENVELOPE:
			this.envelope = new Envelope();
			drawGraphic = new Graphic(this.envelope, this.fillSymbol);
			break;

		}
		graphicID =tempLayer.addGraphic(drawGraphic);
	}

	/*
	 * 清空图层
	 */
	public void Clear() {
		this.tempLayer.removeAll();
		this.deactivate();
	}

	@SuppressWarnings("static-access")
	public void deactivate() {
		// this.map.setOnTouchListener(defaultListener);
		// this.tempLayer.removeAll();
		this.active = false;
		this.drawType = -1;
		this.envelope = null;
		this.drawGraphic = null;
		this.startPoint = null;
	}

	@SuppressWarnings("static-access")
	private void sendDrawEndEvent() {
		int type = this.drawType;
		this.deactivate();
		this.activate(type, this.handler, this.str);
	}

	public MarkerSymbol getMarkerSymbol() {
		return markerSymbol;
	}

	public void setMarkerSymbol(MarkerSymbol markerSymbol) {
		this.markerSymbol = markerSymbol;
	}

	public LineSymbol getLineSymbol() {
		return lineSymbol;
	}

	public void setLineSymbol(LineSymbol lineSymbol) {
		this.lineSymbol = lineSymbol;
	}

	public FillSymbol getFillSymbol() {
		return fillSymbol;
	}

	public void setFillSymbol(FillSymbol fillSymbol) {
		this.fillSymbol = fillSymbol;
	}

	@SuppressWarnings("static-access")
	@Override
	public boolean onTouch(View view, MotionEvent event) {

		if (this.active && ((drawType == ENVELOPE) || (drawType == TOPLOPE))
				&& (event.getAction() == MotionEvent.ACTION_DOWN)) {
			Point point = map.toMapPoint(event.getX(), event.getY());
			switch (drawType) {
			case ENVELOPE:
				startPoint = point;
				envelope.setCoords(point.getX(), point.getY(), point.getX(),
						point.getY());
				break;
			case TOPLOPE:

				mGraphicsLayer = GetGraphicLayer(map, "Topology");
				mPointGraphicsLayer = GetPGraphicLayer(map, "Graphic");
				mGraphicsLayer.setName("Topology");
				mPointGraphicsLayer.setName("Graphic");
				String[] pointline = str.split("#");
				if (!pointline[0].equals("") && !pointline[1].equals("")
						&& !pointline[2].equals("0")) {

					String[] Child_Point = pointline[1].split(";");
					String[] Point = pointline[0].split(",");
					String[] FatherPoint = pointline[2].split(",");
					for (int i = 0; i < Child_Point.length; i++) {
						String[] ChildPoint = Child_Point[i].split(",");
						try {
							float angle = 0;
							float angle1 = 0;
							// ------------------调用服务计算本点到子点的角度-------------------------------------
							Analysis analysis = new Analysis();
							PictureMarkerSymbol pic = new PictureMarkerSymbol(
									analysis.ReadNode("img"));
							Analysis ar = new Analysis();// ar.ReadNode("TopologyService")
							URL url = new URL(ar.ReadNode("TopologyService")
									+ Child_Point[i] + ";" + pointline[0]);
							HttpURLConnection urlConn = (HttpURLConnection) url
									.openConnection();
							InputStreamReader in = new InputStreamReader(
									urlConn.getInputStream());
							BufferedReader bufferReader = new BufferedReader(in);

							String readLine = null;
							while ((readLine = bufferReader.readLine()) != null) {
								angle = Float.parseFloat(readLine);
							}
							in.close();
							urlConn.disconnect();
							pic.setAngle(angle);

							// ------------------调用服务计算本点到父点的角度--------------------------------------
							PictureMarkerSymbol pic1 = new PictureMarkerSymbol(
									analysis.ReadNode("img"));
							URL url1 = new URL(ar.ReadNode("TopologyService")
									+ pointline[0] + ";" + pointline[2]);
							HttpURLConnection urlConn1 = (HttpURLConnection) url1
									.openConnection();
							InputStreamReader in1 = new InputStreamReader(
									urlConn1.getInputStream());
							BufferedReader bufferReader1 = new BufferedReader(
									in1);

							while ((readLine = bufferReader1.readLine()) != null) {
								angle1 = Float.parseFloat(readLine);
							}
							in1.close();
							urlConn1.disconnect();
							pic1.setAngle(angle1);
							// ------------------画本点到子点的线--------------------------------------------------
							Line chline = new Line();
							chline.setStart(new Point(Double
									.parseDouble(Point[0]), Double
									.parseDouble(Point[1])));// 起始点
							chline.setEnd(new Point(Double
									.parseDouble(ChildPoint[0]), Double
									.parseDouble(ChildPoint[1])));// 终止点
							Polyline chpoly = new Polyline();
							chpoly.addSegment(chline, true);// 添加线段到Polyline对象中
							Graphic graphic = new Graphic(chpoly,
									new SimpleLineSymbol(Color.BLUE, 1));
							Graphic SPgraphic = new Graphic(new Point(
									Double.parseDouble(Point[0]),
									Double.parseDouble(Point[1])), pic);
							mGraphicsLayer.addGraphic(graphic);
							mGraphicsLayer.addGraphic(SPgraphic);

							// ------------------画本点到父点的线--------------------------------------------------
							Line faline = new Line();
							faline.setStart(new Point(Double
									.parseDouble(Point[0]), Double
									.parseDouble(Point[1])));// 起始点
							faline.setEnd(new Point(Double
									.parseDouble(FatherPoint[0]), Double
									.parseDouble(FatherPoint[1])));// 终止点
							Polyline fapoly = new Polyline();
							fapoly.addSegment(faline, true);// 添加线段到Polyline对象中
							Graphic falinegraphic = new Graphic(fapoly,
									new SimpleLineSymbol(Color.BLUE, 1));
							Graphic falinegraphicPgraphic = new Graphic(
									new Point(Double.parseDouble(FatherPoint[0]),
											Double.parseDouble(FatherPoint[1])), pic1);
							mGraphicsLayer.addGraphic(falinegraphic);
							mGraphicsLayer.addGraphic(falinegraphicPgraphic);

							Graphic statgp = new Graphic(new Point(
									Double.parseDouble(Point[0]),
									Double.parseDouble(Point[1])),
									new SimpleMarkerSymbol(Color.rgb(77, 152,
											221), 20, STYLE.CIRCLE));
							Graphic endgp = new Graphic(new Point(
									Double.parseDouble(ChildPoint[0]),
									Double.parseDouble(ChildPoint[1])),
									new SimpleMarkerSymbol(Color.rgb(77, 152,
											221), 20, STYLE.CIRCLE));
							Graphic pongp = new Graphic(new Point(
									Double.parseDouble(FatherPoint[0]),
									Double.parseDouble(FatherPoint[1])),
									new SimpleMarkerSymbol(Color.rgb(77, 152,
											221), 20, STYLE.CIRCLE));
							mPointGraphicsLayer.addGraphic(statgp);
							mPointGraphicsLayer.addGraphic(endgp);
							mPointGraphicsLayer.addGraphic(pongp);

						} catch (MalformedURLException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}

				}
				if (!pointline[0].equals("") && pointline[1].equals("")
						&& !pointline[2].equals("0")) {
					String[] Point = pointline[0].split(",");
					String[] FatherPoint = pointline[2].split(",");

					try {
						float angle = 0;
						Analysis ar = new Analysis();
						String readLine = "";
						Analysis analysis = new Analysis();
						// ------------------调用服务求本点到父点的角度-----------------------------------------
						PictureMarkerSymbol pic = new PictureMarkerSymbol(
								analysis.ReadNode("img"));
						URL url = new URL(ar.ReadNode("TopologyService")
								+ pointline[0] + ";" + pointline[2]);
						HttpURLConnection urlConn = (HttpURLConnection) url
								.openConnection();
						InputStreamReader in = new InputStreamReader(
								urlConn.getInputStream());
						BufferedReader bufferReader = new BufferedReader(in);

						while ((readLine = bufferReader.readLine()) != null) {
							angle = Float.parseFloat(readLine);
						}
						in.close();
						urlConn.disconnect();
						pic.setAngle(angle);
						// ------------------画出本点到父点的线-----------------------------------------------
						Line faline = new Line();
						faline.setStart(new Point(Double.parseDouble(Point[0]),
								Double.parseDouble(Point[1])));// 起始点
						faline.setEnd(new Point(Double
								.parseDouble(FatherPoint[0]), Double
								.parseDouble(FatherPoint[1])));// 终止点
						Polyline fapoly = new Polyline();
						fapoly.addSegment(faline, true);// 添加线段到Polyline对象中
						Graphic falinegraphic = new Graphic(fapoly,
								new SimpleLineSymbol(Color.BLUE, 1));
						Graphic falinegraphicPgraphic = new Graphic(new Point(
								Double.parseDouble(FatherPoint[0]),
								Double.parseDouble(FatherPoint[1])), pic);
						mGraphicsLayer.addGraphic(falinegraphic);
						mGraphicsLayer.addGraphic(falinegraphicPgraphic);

						Graphic statgp = new Graphic(new Point(
								Double.parseDouble(Point[0]),
								Double.parseDouble(Point[1])),
								new SimpleMarkerSymbol(Color.rgb(77, 152, 221),
										20, STYLE.CIRCLE));
						Graphic endgp = new Graphic(new Point(
								Double.parseDouble(FatherPoint[0]),
								Double.parseDouble(FatherPoint[1])),
								new SimpleMarkerSymbol(Color.rgb(77, 152, 221),
										20, STYLE.CIRCLE));
						mPointGraphicsLayer.addGraphic(statgp);
						mPointGraphicsLayer.addGraphic(endgp);

					} catch (MalformedURLException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				if (!pointline[0].equals("") && !pointline[1].equals("")
						&& pointline[2].equals("0")) {

					String[] Child_Point = pointline[1].split(";");
					String[] Point = pointline[0].split(",");
					for (int i = 0; i < Child_Point.length; i++) {
						String[] ChildPoint = Child_Point[i].split(",");

						try {
							float angle = 0;
							Analysis analysis = new Analysis();
							// ------------------调用服务求本点到子点的角度-----------------------------------------
							PictureMarkerSymbol pic = new PictureMarkerSymbol(
									analysis.ReadNode("img"));
							Analysis ar = new Analysis();
							URL url = new URL(ar.ReadNode("TopologyService")
									+ Child_Point[i]  + ";" + pointline[0]);
							HttpURLConnection urlConn = (HttpURLConnection) url
									.openConnection();
							InputStreamReader in = new InputStreamReader(
									urlConn.getInputStream());
							BufferedReader bufferReader = new BufferedReader(in);

							String readLine = null;
							while ((readLine = bufferReader.readLine()) != null) {
								angle = Float.parseFloat(readLine);
							}
							in.close();
							urlConn.disconnect();
							pic.setAngle(angle);
							// ------------------画出本点到子点的线-------------------------------------------------------------------
							Line chline = new Line();
							chline.setStart(new Point(Double
									.parseDouble(Point[0]), Double
									.parseDouble(Point[1])));// 起始点
							chline.setEnd(new Point(Double
									.parseDouble(ChildPoint[0]), Double
									.parseDouble(ChildPoint[1])));// 终止点
							Polyline chpoly = new Polyline();
							chpoly.addSegment(chline, true);// 添加线段到Polyline对象中
							Graphic graphic = new Graphic(chpoly,
									new SimpleLineSymbol(Color.BLUE, 1));
							Graphic SPgraphic = new Graphic(new Point(
									Double.parseDouble(Point[0]),
									Double.parseDouble(Point[1])), pic);
							mGraphicsLayer.addGraphic(graphic);
							mGraphicsLayer.addGraphic(SPgraphic);

							Graphic statgp = new Graphic(new Point(
									Double.parseDouble(Point[0]),
									Double.parseDouble(Point[1])),
									new SimpleMarkerSymbol(Color.rgb(77, 152,
											221), 20, STYLE.CIRCLE));
							Graphic endgp = new Graphic(new Point(
									Double.parseDouble(ChildPoint[0]),
									Double.parseDouble(ChildPoint[1])),
									new SimpleMarkerSymbol(Color.rgb(77, 152,
											221), 20, STYLE.CIRCLE));
							mPointGraphicsLayer.addGraphic(statgp);
							mPointGraphicsLayer.addGraphic(endgp);

						} catch (MalformedURLException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				if (pointline[0].equals("") && pointline[1].equals("")
						&& pointline[2].equals("")) {

				}

				// onDragPointerUp(event, event);

				break;

			}
		}

		return super.onTouch(view, event);
	}

	@Override
	public boolean onDragPointerMove(MotionEvent from, MotionEvent to) {
		if (active && (drawType == ENVELOPE)) {
			Point point = map.toMapPoint(to.getX(), to.getY());
			switch (drawType) {
			case ENVELOPE:
				envelope.setXMin(startPoint.getX() > point.getX() ? point
						.getX() : startPoint.getX());
				envelope.setYMin(startPoint.getY() > point.getY() ? point
						.getY() : startPoint.getY());
				envelope.setXMax(startPoint.getX() < point.getX() ? point
						.getX() : startPoint.getX());
				envelope.setYMax(startPoint.getY() < point.getY() ? point
						.getY() : startPoint.getY());
				tempLayer.updateGraphic(graphicID, envelope.copy());
				break;

			}

			return true;
		}
		return super.onDragPointerMove(from, to);
	}

	public boolean onDragPointerUp(MotionEvent from, MotionEvent to) {
		if (active && ((drawType == ENVELOPE) || (drawType == TOPLOPE))) {
			Point point = map.toMapPoint(to.getX(), to.getY());
			switch (drawType) {
			case ENVELOPE:
				envelope.setXMin(startPoint.getX() > point.getX() ? point
						.getX() : startPoint.getX());
				envelope.setYMin(startPoint.getY() > point.getY() ? point
						.getY() : startPoint.getY());
				envelope.setXMax(startPoint.getX() < point.getX() ? point
						.getX() : startPoint.getX());
				envelope.setYMax(startPoint.getY() < point.getY() ? point
						.getY() : startPoint.getY());
				String Name = startPoint.getX() + "," + startPoint.getY() + ";"
						+ point.getX() + "," + point.getY();
				Message msg = handler.obtainMessage(1, Name);// 发送消息
				handler.sendMessage(msg);

				break;
			case TOPLOPE:

				GraphicsLayer layer = null;
				Layer[] layers = map.getLayers();
				for (int i = 0; i < layers.length; i++) {
					if (layers[i].getName() == "Topology") {
						layer = (GraphicsLayer) layers[i];
					}
				}
				if (layer != null) {
					map.removeLayer(layer);
				}
				break;

			}

			// Analysis dr = new Analysis();
			// dr.EnvelopeQuery(this.map,
			// this.map.getResources().getDrawable(R.drawable.ic_launcher),startPoint.getX(),
			// startPoint.getY(),
			// point.getX(), point.getY());
			sendDrawEndEvent();
			startPoint = null;
			this.Clear();
			return true;
		}
		return super.onDragPointerUp(from, to);
	}

	@Override
	public boolean onSingleTap(MotionEvent event) {
		// if (active && (drawType == ENVELOPE)) {
		// return true;
		// }

		return super.onSingleTap(event);
	}

	/**
	 * 获得 GetGraphicLayer
	 * 
	 * @param map
	 * @return
	 */
	private GraphicsLayer GetGraphicLayer(MapView map, String str) {

		mGraphicsLayer = null;
		Layer[] layers = map.getLayers();
		for (int i = 0; i < layers.length; i++) {
			if (layers[i].getName() == str) {
				mGraphicsLayer = (GraphicsLayer) layers[i];
			}
		}
		if (mGraphicsLayer == null) {
			mGraphicsLayer = new GraphicsLayer();
			map.addLayer(mGraphicsLayer);
		}
		return mGraphicsLayer;
	}

	private GraphicsLayer GetPGraphicLayer(MapView map, String str) {

		mPointGraphicsLayer = null;
		Layer[] layers = map.getLayers();
		for (int i = 0; i < layers.length; i++) {
			if (layers[i].getName() == str) {
				mPointGraphicsLayer = (GraphicsLayer) layers[i];
			}
		}
		if (mPointGraphicsLayer == null) {
			mPointGraphicsLayer = new GraphicsLayer();
			map.addLayer(mPointGraphicsLayer);
		}
		return mPointGraphicsLayer;
	}

	@Override
	public boolean onDoubleTap(MotionEvent event) {
		// if (active && (drawType == ENVELOPE)) {
		// return true;
		// }
		return super.onDoubleTap(event);
	}
}