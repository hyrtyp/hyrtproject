package com.edit.arcgis.android;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.xmlpull.v1.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.kxml2.io.KXmlParser;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;

import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISTiledMapServiceLayer;
import com.esri.core.geometry.Envelope;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.Polyline;
import com.esri.core.geometry.Transformation2D;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.PictureMarkerSymbol;
import com.esri.core.symbol.SimpleLineSymbol;

public class Analysis {

	public GraphicsLayer mGraphicsLayer;
	ArcGISTiledMapServiceLayer tileLayer;
	public static String solvepth = "";
	private static Point startpoint = null;
	private static Point endpoint = null;
	public static String ServerUrl = "";
	public static String ClusteringUrl = "";
	private DragEnvelopeListener DragEnvel;
	private static String content;

	Point startPoint = null;
	String lkcx;

	public static String getContent(String url){
        String content = "";
        try{
        URL Url = new URL(url);
        HttpURLConnection httpconn = (HttpURLConnection) Url.openConnection();
        httpconn.setConnectTimeout(3000);
        httpconn.setReadTimeout(5000);
        if (httpconn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            InputStreamReader isr = new InputStreamReader(
                    httpconn.getInputStream(), "utf-8");
            int i;
            while ((i = isr.read()) != -1) {
                content = content + (char) i;
            }
            isr.close();
        }
        }catch(Exception e){
            e.printStackTrace();
        }
        return content;

    }

    public static String ReadNode(String nodename) {
        String nodValue = "";
        try {
            String tag_name = "";
            new Thread(new Runnable() {
				
				@Override
				public void run() {
					content = getContent(ServerUrl);
					
				}
			}).start();
            try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            ByteArrayInputStream bis = new ByteArrayInputStream(content
                    .getBytes("UTF-8"));
            KXmlParser parser = new KXmlParser();
            parser.setInput(bis, "UTF-8");
            while (parser.next() > 1) {
                int eventType = parser.getEventType();
                switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    break;
                case XmlPullParser.END_DOCUMENT:
                    break;
                case XmlPullParser.END_TAG:
                    tag_name = "";
                    break;
                case XmlPullParser.START_TAG:
                    tag_name = parser.getName().trim();
                    break;
                case XmlPullParser.TEXT:
                    String someValue = parser.getText().trim();
                    if (tag_name.equals(nodename)) {
                        nodValue = someValue;
                    }
                    break;
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return nodValue;
    }
	
	public void EditServerUrl(String serverurl) {

		ServerUrl = serverurl;
	}

	public void EditClusteringUrl(String clusteringurl) {

		ClusteringUrl = clusteringurl;
	}


	public void RouteAnalysis(MapView map, Drawable picurl, double startx,
			double starty, double stopx, double stopy) {
		PictureMarkerSymbol pic = null;
		pic = new PictureMarkerSymbol(picurl);
		String strUrl = startx + "," + starty + ";" + stopx + "," + stopy;
		mGraphicsLayer = GetGraphicLayer(map);
		DrawLineAsyncTask drawlineasynctask = new DrawLineAsyncTask(strUrl,
				map, mGraphicsLayer, pic, ReadNode("NAServer").toString());
		drawlineasynctask.execute(100);
		Envelope initextext = new Envelope(startx, starty, stopx, stopy);
		map.zoomout();
		map.setExtent(initextext);
	}

	public void RouteAnalysisByPoints(MapView map, Drawable picurl, String str) {
		PictureMarkerSymbol pic = new PictureMarkerSymbol(picurl);
		String[] pointline = str.split(";");
		mGraphicsLayer = GetGraphicLayer(map);
		DrawLineAsyncTask drawlineasynctask = new DrawLineAsyncTask(str, map,
				mGraphicsLayer, pic, ReadNode("NAServer").toString());
		drawlineasynctask.execute(100);
		Envelope initextext = new Envelope(
		        xConversion(StringToDouble(pointline[0].split(",")[0])),
		        yConversion(StringToDouble(pointline[0].split(",")[1])),
		        xConversion(StringToDouble(pointline[pointline.length - 1].split(",")[0])),
		        yConversion(StringToDouble(pointline[pointline.length - 1].split(",")[1]))
		        );
		map.setExtent(initextext);
		map.zoomout();
	}

	public void RouteAnalysis_Touch_poess() {

		solvepth = "SolvePath_tach";
		startpoint = null;
		endpoint = null;
	}

	public void RouteAnalysis_Touch(MapView map, String picurl, Float x, Float y) {

		if (solvepth == "SolvePath_tach") {

			if (startpoint == null) {
				startpoint = map.toMapPoint(x, y);
			} else if (startpoint != null && endpoint == null) {

				endpoint = map.toMapPoint(x, y);
				String strUrl = startpoint.getX() + "," + startpoint.getY()
						+ ";" + endpoint.getX() + "," + endpoint.getY();
			} else if (startpoint != null && endpoint != null) {

				startpoint = endpoint;
				endpoint = map.toMapPoint(x, y);
				String strUrl = startpoint.getX() + "," + startpoint.getY()
						+ ";" + endpoint.getX() + "," + endpoint.getY();
			}
		}
	}

	@SuppressWarnings("unused")
	public String EnvelopeQuery(MapView map, String picurl, double MinX,
			double MinY, double MaxX, double MaxY) {
		String strUrl = ReadNode("QueryService")
				+ "/0/query?type%3D80301013&geometry=" + MinX + "," + MinY
				+ "," + MaxX + "," + MaxY
				+ "&geometryType=esriGeometryEnvelope&f=pjson";
		PictureMarkerSymbol pic = null;
		try {
			pic = new PictureMarkerSymbol(picurl);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		String result = "";
		try {
			URL url = new URL(strUrl);
			try {
				HttpURLConnection urlConn = (HttpURLConnection) url
						.openConnection();
				InputStreamReader in = new InputStreamReader(
						urlConn.getInputStream());
				BufferedReader bufferReader = new BufferedReader(in);

				String readLine = null;
				while ((readLine = bufferReader.readLine()) != null) {
					result += readLine;
				}
				in.close();
				urlConn.disconnect();
			} catch (IOException e) {
				return "";
			}
		} catch (MalformedURLException e) {
			return "";
		}
		String name = "";
		try {
			JSONArray jsonObjs = new JSONObject(result)
					.getJSONArray("features");

			for (int i = 0; i < jsonObjs.length(); i++) {

				JSONObject jsonattributes = ((JSONObject) jsonObjs.opt(i))
						.getJSONObject("attributes");
				JSONObject jsonObjgeometry = ((JSONObject) jsonObjs.opt(i))
						.getJSONObject("geometry");
				double x = Double.parseDouble(jsonObjgeometry.getString("x"));
				double y = Double.parseDouble(jsonObjgeometry.getString("y"));
				GraphicsLayer layer = GetGraphicLayer(map);
				Point pt = new Point(x, y);
				Graphic gp = new Graphic(pt, pic);
				layer.setName("Graphic");
				layer.addGraphic(gp);
			}
		} catch (JSONException e) {
			return "";
		}
		return name;
	}
	public boolean Trajectory(String point, MapView map) {
		String[] pointline = point.split(";");
		Polyline line = new Polyline();
		mGraphicsLayer = GetGraphicLayer(map);
		mGraphicsLayer.setName("SolvePath");
		line.startPath(xConversion(StringToDouble(pointline[0].split(",")[0])),
				yConversion(StringToDouble(pointline[0].split(",")[1])));
		for (int i = 1; i < pointline.length; i++) {
			line.lineTo(xConversion(StringToDouble(pointline[i].split(",")[0])),
	                yConversion(StringToDouble(pointline[i].split(",")[1])));
		}
		Envelope initextext = new Envelope(
				xConversion(StringToDouble(pointline[0].split(",")[0])),
				yConversion(StringToDouble(pointline[0].split(",")[1])),
				xConversion(StringToDouble(pointline[pointline.length - 1].split(",")[0])),
				yConversion(StringToDouble(pointline[pointline.length - 1].split(",")[1])));
		map.setExtent(initextext);
		Transformation2D transform = new Transformation2D(1);
		line.applyTransformation(transform);
		Graphic graphic = new Graphic(line, new SimpleLineSymbol(Color.BLUE, 5));
		mGraphicsLayer.addGraphic(graphic);
		map.zoomout();
		return true;
	}

	public void Topology(String Point, String ChildPoint, String FatherPoint,
			MapView map) {

		DragEnvel = new DragEnvelopeListener(map.getContext(), map);
		if (FatherPoint.equals("")) {
			FatherPoint = "0";
		}
		DragEnvel.activate(2, null, Point + "#" + ChildPoint + "#"
				+ FatherPoint + "#");

	}

	private double StringToDouble(String str) {
		double d = Double.parseDouble(str);
		return d;
	}

	private GraphicsLayer GetGraphicLayer(MapView map) {
		if (mGraphicsLayer == null) {
			mGraphicsLayer = new GraphicsLayer();
			map.addLayer(mGraphicsLayer);
		}
		return mGraphicsLayer;
	}
	
	public double xConversion(Double x) {
	    double intNum = Math.floor(x);
	    double decimalNum = x-intNum;
	    double number = intNum + decimalNum/0.9420202972381885;
        return number;
	}
	
	public double yConversion(Double y) {
        double intNum = Math.floor(y);
        double decimalNum = y-intNum;
        double number = intNum + decimalNum/1.00263135017959;
        return number;
        
    }
	
}
