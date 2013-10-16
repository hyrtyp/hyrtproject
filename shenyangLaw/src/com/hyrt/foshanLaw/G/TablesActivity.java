package com.hyrt.foshanLaw.G;

import com.hyrt.cei.util.MyTools;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class TablesActivity extends Activity {
    String eid,lid;
    public static String PID;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		SharedPreferences patrolLogId = getSharedPreferences("patrolLog",
				Activity.MODE_PRIVATE);
		lid = patrolLogId.getString("lid", "");
		eid=patrolLogId.getString("eid", "");
		String url=MyTools.TABLE_URL;
		url=url.replace("eId=", "eId="+eid);
		url=url.replace("lid=", "lid="+lid);
		if(PID != null)
			url=url.replace("pId=", "pId="+PID);
		//url="http://192.168.10.126:8080/LCSIS/patrol.do?method=showTable&eId=40288a073c1934a0013c1966f57d002b&lid=40288afe3c602be7013c60321735007b&pId=&typeIds=&dbtablename=MwpmBussRcaqxcb&phoneHost=phoneHost";
		WebView webView=new WebView(this);
		webView.getSettings().setUseWideViewPort(true);
		webView.getSettings().setLoadWithOverviewMode(true);
		webView.getSettings().setJavaScriptEnabled(true); 
		webView.getSettings().setBuiltInZoomControls(true);
		webView.getSettings().setDisplayZoomControls (false);
		//webView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		webView.loadUrl(url+getSharedPreferences("userinfo", Activity.MODE_PRIVATE).getString("userid", ""));
		setContentView(webView);
	}

}
