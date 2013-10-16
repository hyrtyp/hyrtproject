package com.edit.arcgis.android;

import android.graphics.Color;
import android.os.AsyncTask;

import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.MapView;
import com.esri.core.geometry.Point;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.PictureMarkerSymbol;
import com.esri.core.symbol.SimpleLineSymbol;
import com.esri.core.tasks.ags.na.NAFeaturesAsFeature;
import com.esri.core.tasks.ags.na.Route;
import com.esri.core.tasks.ags.na.RoutingParameters;
import com.esri.core.tasks.ags.na.RoutingResult;
import com.esri.core.tasks.ags.na.RoutingTask;
import com.esri.core.tasks.ags.na.StopGraphic;

public class DrawLineAsyncTask extends AsyncTask<Integer, Integer, String> {

	private String strUrl = "";
	@SuppressWarnings("unused")
	private MapView thisMap = null;
	private GraphicsLayer mGraphicsLayer;
	private PictureMarkerSymbol picture;
	private Route curRoute = null;
	private RoutingResult mResults = null;
	private String path = "";
	
	private Analysis mAnalysis;

	public DrawLineAsyncTask(String url, MapView map,
			GraphicsLayer graphicslayer, PictureMarkerSymbol pic, String path) {
		this.strUrl = url;
		this.thisMap = map;
		this.mGraphicsLayer = graphicslayer;
		this.mGraphicsLayer.setName("SolvePath");
		this.picture = pic;
		this.path = path;
		mAnalysis = new Analysis();
	}

	@Override
	protected void onPreExecute() {

	}

	@Override
	protected String doInBackground(Integer... params) {
		mResults = null;
		Thread t = new Thread() {
			public void run() {
				String[] pointline = strUrl.split(";");
				Point[] pp = new Point[pointline.length];
				for (int i = 0; i < pp.length; i++) {
					pp[i] = new Point(
							mAnalysis.xConversion(StringToDouble(pointline[i].split(",")[0])),
		                    mAnalysis.yConversion(StringToDouble(pointline[i].split(",")[1]))
                            );
				}
				StopGraphic[] sg = new StopGraphic[pointline.length];
				for (int i = 0; i < sg.length; i++) {
					sg[i] = new StopGraphic(pp[i]);
				}
				RoutingParameters rp = new RoutingParameters();
				rp.setReturnDirections(false);
				NAFeaturesAsFeature rfaf = new NAFeaturesAsFeature();
				rfaf.setFeatures(sg);
				rfaf.setCompressedRequest(true);
				rp.setStops(rfaf);
				RoutingTask rt = new RoutingTask(path,null);
				try {
					mResults = rt.solve(rp);//这出异常
					curRoute = mResults.getRoutes().get(0);
					SimpleLineSymbol routeSymbol = new SimpleLineSymbol(Color.BLUE,
							5);
					Graphic routeGraphic = new Graphic(curRoute.getRoute()
							.getGeometry(), routeSymbol);
					Graphic startgp = new Graphic(pp[0], picture);
					Graphic endgp = new Graphic(pp[pp.length - 1], picture);
					mGraphicsLayer.addGraphics(new Graphic[] { routeGraphic,
							startgp, endgp });
				} catch (Exception e) {
					e.printStackTrace();//
				}
			}
		};
		t.start();
		return "ִ";
	}

	@Override
	protected void onProgressUpdate(Integer... progress) {
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
	}

	@SuppressWarnings("unused")
	private GraphicsLayer GetGraphicLayer(MapView map) {
		if (mGraphicsLayer == null) {
			mGraphicsLayer = new GraphicsLayer();
			map.addLayer(mGraphicsLayer);
		}
		return mGraphicsLayer;
	}

	private double StringToDouble(String str) {
		double d = Double.parseDouble(str);
		return d;
	}
}
