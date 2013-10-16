package com.hyrt.foshanLaw.G;

import java.io.File;
import java.util.List;

import com.hyrt.cei.util.MyTools;
import com.hyrt.foshanLaw.b.MyMapActivity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebViewClient;
import android.webkit.WebView;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class TablesActivity extends Activity {
	
	String eid, lid;
	public static String PID;
	private WebView webView;

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
		try{
			Intent intent = new Intent();
			Uri u = Uri.parse(url+"?t="+System.currentTimeMillis());
			intent.setData(u);
			intent.setAction(Intent.ACTION_VIEW);
			intent.setClassName("com.android.chrome",
					"com.android.chrome.Main");
			this.startActivity(intent);
			this.finish();
		}catch(Exception e){
			Uri uri = Uri.fromFile(new File("/sdcard/chrom.apk")); // 这里是APK路径
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setDataAndType(uri, "application/vnd.android.package-archive");
			startActivity(intent);
			this.finish();
		}
	}

}
