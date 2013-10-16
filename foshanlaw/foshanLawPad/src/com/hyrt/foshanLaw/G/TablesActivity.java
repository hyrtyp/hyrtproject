package com.hyrt.foshanLaw.G;

import com.hyrt.cei.util.MyTools;
import com.hyrt.foshanLaw.b.MyMapActivity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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
		eid = patrolLogId.getString("eid", "");
		String url = MyTools.TABLE_URL;
		url = url.replace("eId=", "eId=" + eid);
		url = url.replace("lid=", "lid=" + lid);
		url = url.replace("coorX=", "coorX=" + MyMapActivity.getLatLon()[0]);
		url = url.replace("coorY=", "coorY=" + MyMapActivity.getLatLon()[1]);
		if (PID != null)
			url = url.replace("pId=", "pId=" + PID);// MyMapActivity
		SharedPreferences settings = getSharedPreferences("userinfo",
				Activity.MODE_PRIVATE);
		url = url.replace("loginname=",
				"loginname=" + settings.getString("name", ""));
		Intent intent = new Intent();
		Uri u = Uri.parse(url+"?t="+System.currentTimeMillis());
		intent.setData(u);
		intent.setAction(Intent.ACTION_VIEW);
		intent.setClassName("com.android.browser",
				"com.android.browser.BrowserActivity");
		this.startActivity(intent);
		this.finish();
	}

}
