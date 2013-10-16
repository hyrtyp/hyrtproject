/**
 * 
 */
package com.hyrt.foshanLaw.pptclient;

import com.hyrt.foshanLaw.R;
import com.hyrt.foshanLaw.b.NavitApplication;
import com.hyrt.foshanLaw.pptclient.common.GlobalFunction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Description:测试用登陆界面
 * @author 郑伟
 * @Date 2013-1-13
 * @Copyright:2013-1-13
 * @Company:华源润通（北京）科技有限公司重庆分公司
 */
public class TestActivity extends Activity{
	NavitApplication app;
	String userid="1";
	String uname="";
	String[] ss;
	String[] uid;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.test);
		app=(NavitApplication)getApplication();
		ListView lv=(ListView)findViewById(R.id.lvtest);
		 
		 ss=new String[]{"贾贺","贾存贺","杨龙","叶朋","heyx"};
		 uid=new String[]{"40288afe3c7efbb6013c7f1d7dc50023","40288afe3c80397e013c80a5ed360039","40288afe3c7fb1de013c7fb925db002b","40288afe3c7f28d7013c7f9f8d4e0017","40288afe3c7efbb6013c7f282fb0002d"};
		ArrayAdapter<String> arr=new ArrayAdapter<String>(TestActivity.this, android.R.layout.simple_list_item_1, ss);
		lv.setAdapter(arr);
		
		lv.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				userid=uid[arg2];
				uname=ss[arg2];
				start();

			}
			
		});
 
	}
	
	void start(){
		GlobalFunction.setUserId(TestActivity.this, userid);
		app.setUid(userid);
		app.setUname(uname);
		Intent ie=new Intent(TestActivity.this,PPTClientActivity.class);
		PPTService.Start(TestActivity.this);
		startActivity(ie);
	}
}
