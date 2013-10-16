package com.edit.arcgis.android;

import java.util.HashMap;
import java.util.Map;
import java.lang.*;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.widget.LinearLayout;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import com.esri.android.map.Callout;
import com.esri.android.map.CalloutStyle;
import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.Layer;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISDynamicMapServiceLayer;
import com.esri.android.map.ags.ArcGISLocalTiledLayer;
import com.esri.android.map.ags.ArcGISTiledMapServiceLayer;
import com.esri.core.geometry.Point;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.PictureMarkerSymbol;
import com.esri.core.symbol.SimpleMarkerSymbol;
import com.esri.core.symbol.SimpleMarkerSymbol.STYLE;
import com.esri.core.symbol.TextSymbol;
import com.esri.core.geometry.Envelope;

public class Operation {

    ArcGISTiledMapServiceLayer tileLayer;

    ArcGISLocalTiledLayer local;

    ArcGISDynamicMapServiceLayer dynamicLayer;

    public GraphicsLayer mGraphicsLayer;
    Handler handler = new Handler();

    public void LoadMap(final MapView map) {
        	new Thread(new Runnable() {
				
				@Override
				public void run() {
					  Analysis analysis = new Analysis();
					final String loadMapUrl = analysis.ReadNode("LoadMap");
					handler.post(new Runnable() {
						
						@Override
						public void run() {
							   tileLayer = new ArcGISTiledMapServiceLayer(loadMapUrl);
					            // //"http://125.89.77.55:6080/arcgis/rest/services/FoShan_Cache/MapServer");
					            map.addLayer(tileLayer);
					            map.setMaxResolution(0.00475892201166056);//
					            map.centerAt(new Point(113.044, 23.049), true);
					            //map.zoomToScale(new Point(113.044, 23.049),0.00475892201166056);
					            setExtent(map);
						}
					});
				}
			}).start();
    }

    public void setExtent(MapView map) {
        Envelope initextext = new Envelope(112.301638, 22.650981, 113.460164,
                23.599324);
        map.setExtent(initextext);
    }

    public Boolean LoadLocalMap(MapView map) {
        try {
            local = new ArcGISLocalTiledLayer(
                    "file:///mnt/sdcard/map/foshan/Layers");
            map.addLayer(local);
            map.setMaxResolution(0.00475892201166056);
            map.centerAt(new Point(113.044, 23.049), true);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public Boolean LoadDynamicMapServiceLayerMap(MapView map) {
        try {
            Analysis analysis = new Analysis();
            dynamicLayer = new ArcGISDynamicMapServiceLayer(
                    analysis.ReadNode("LoadDynamicMapServiceLayerMap"));
            map.addLayer(dynamicLayer);
            map.centerAt(new Point(113.044, 23.049), true);
            map.setMaxResolution(0.00475892201166056);//
            map.centerAt(new Point(113.044, 23.049), true);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public void ZoomIn(MapView map) {
        map.zoomin();
    }
    public void ZoomOut(MapView map) {
        map.zoomout();
    }
    public void CenterAt(MapView map, double x, double y) {

        Analysis analysis = new Analysis();

        double x1 = analysis.xConversion(x);
        double y1 = analysis.yConversion(y);
        x = x1;
        y = y1;
        map.centerAt(new Point(x, y), true);
    }

    public Boolean ClearGraphicLayer(MapView map) {
        try {
            Layer[] layers = map.getLayers();
            for (int i = 0; i < layers.length; i++) {
                if (layers[i].getName() == "Graphic") {
                    map.removeLayer(layers[i]);
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean ClearLayer(MapView map, String str) {
        try {
            Layer[] layers = map.getLayers();
            for (int i = 0; i < layers.length; i++) {
                if (layers[i].getName() == str) {
                    map.removeLayer(layers[i]);
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean ClearTopologyLayer(MapView map) {
        try {
            Layer[] layers = map.getLayers();
            for (int i = 0; i < layers.length; i++) {
                if (layers[i].getName() == "Topology") {
                    map.removeLayer(layers[i]);
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean ClearSolvePathLayer(MapView map) {
        try {
            Layer[] layers = map.getLayers();
            for (int i = 0; i < layers.length; i++) {
                if (layers[i].getName() == "SolvePath") {
                    map.removeLayer(layers[i]);
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean Clustering(MapView map, double x, double y, String number) {
        try {
            mGraphicsLayer = GetGraphicLayer(map);
            mGraphicsLayer.setName("Graphic");

            double cor_x = x;
            Graphic endgp = new Graphic(new Point(x, y),
                    new SimpleMarkerSymbol(Color.rgb(77, 152, 221), 20,
                            STYLE.CIRCLE));
            mGraphicsLayer.addGraphic(endgp);
            TextSymbol ts = new TextSymbol(14, number,
                    Color.rgb(232, 241, 246),
                    TextSymbol.HorizontalAlignment.CENTER,
                    TextSymbol.VerticalAlignment.MIDDLE);
            Graphic gp = new Graphic(new Point(cor_x, y), ts);

            mGraphicsLayer.addGraphic(gp);

            return true;
        } catch (Exception e) {
            return false;
        }

    }

 
    public Boolean CreateLabel(MapView map, Point pt, String url, String nood) {
        try {
            
            Analysis analysis = new Analysis();
            double x0 = analysis.xConversion(pt.getX());
            double y0 = analysis.yConversion(pt.getY());
            pt = new Point(x0,y0);
            
            Map<String, Object> id = new HashMap<String, Object>();
            id.put("name", nood);
            PictureMarkerSymbol pic = new PictureMarkerSymbol(url);
            Graphic ptgr = new Graphic(pt, pic, id, null);
            Layer[] layers = map.getLayers();
            for (int i = 0; i < layers.length; i++) {
                if (layers[i].getName() == "Graphic") {
                    mGraphicsLayer = (GraphicsLayer) layers[i];
                }
            }
            if (mGraphicsLayer == null) {
                mGraphicsLayer = new GraphicsLayer();
                map.addLayer(mGraphicsLayer);
                mGraphicsLayer.setName("Graphic");
            }
            mGraphicsLayer.addGraphic(ptgr);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public Boolean LoadPoints(MapView map, String pointInfos, String url) {
        try {
            String[] Points = pointInfos.split(";");
            for (int i = 0; i < Points.length; i++) {
                String[] pointInfo = Points[i].split(",");
                // String strTitle=pointInfo[1];
                double X = Double.parseDouble(pointInfo[2]);
                double Y = Double.parseDouble(pointInfo[3]);
                Point mPoint = new Point(X, Y);
                CreateLabel(map, mPoint, url, "");
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void ShowCallout(Point point, MapView map, String txt) {
        Callout callout = map.getCallout();
        CalloutStyle cStyle = new CalloutStyle();
        cStyle.setMaxHeight(800);
        cStyle.setMaxWidth(600);
        cStyle.setBackgroundAlpha(155);
        cStyle.setBackgroundColor(Color.WHITE);
        cStyle.setTitleTextColor(Color.BLACK);
        LinearLayout layout = new LinearLayout(map.getContext());
        layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.FILL_PARENT));
        layout.setOrientation(LinearLayout.VERTICAL);
        TextView tx = new TextView(map.getContext());
        tx.setText(txt);
        tx.setTextColor(Color.BLACK);
        tx.setTextSize(15);
        tx.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.FILL_PARENT));
        layout.addView(tx);
        callout.setStyle(cStyle);
        callout.show(point, layout);
    }

    public void CloseCallout(MapView map) {

        Callout callout = map.getCallout();
        callout.hide();
    }

    private GraphicsLayer GetGraphicLayer(MapView map) {
        if (mGraphicsLayer == null) {
            mGraphicsLayer = new GraphicsLayer();
            map.addLayer(mGraphicsLayer);
        }
        return mGraphicsLayer;
    }
}
