package com.hyrt.foshanLaw.f;

import com.hyrt.foshanLaw.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class FwebActivity extends Activity{
	private WebView view;
	private Intent intent;
	private String url;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.web);
		view = (WebView) findViewById(R.id.web);
		WebSettings webSettings = view.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setSupportZoom(true);
		webSettings.setPluginState(PluginState.ON);
		view.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(final WebView view,
					final String url) {
				view.loadUrl(url);// 载入网页
				return true;
			}// 重写点击动作,用webview载入
		});
		intent = getIntent();
		url = intent.getStringExtra("URL");
		view.loadUrl(url);
		view.requestFocus();
		view.requestFocusFromTouch();
	}
}
