package com.hyrt.foshanLaw.b;

import com.hyrt.foshanLaw.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class MainChoiceActivity extends Activity implements OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bmain_choice);
		findViewById(R.id.icon_a).setOnClickListener(this);
		findViewById(R.id.icon_b).setOnClickListener(this);
		findViewById(R.id.icon_c).setOnClickListener(this);
		findViewById(R.id.icon_d).setOnClickListener(this);
		findViewById(R.id.icon_e).setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		SharedPreferences settings = getSharedPreferences(
				"userinfo",
				Activity.MODE_PRIVATE);
		String level = settings.getString("level", "");
		Intent intent = null;
		switch (v.getId()) {
		case R.id.icon_a:
			intent = new Intent(this,BqycxActivity.class);
			intent.putExtra("listFlag", EntInfoListActivity.ENTINFOLIST_FLAG);
			intent.putExtra(BqycxActivity.HIDE_RIGHT_FLAG, true);
			break;
		case R.id.icon_b:
			intent = new Intent(this,BqycxActivity.class);
			intent.putExtra("listFlag", EntInfoListActivity.ENTINFOLINELIST_FLAG);
			intent.putExtra(BqycxActivity.HIDE_RIGHT_FLAG, true);
			break;
		case R.id.icon_c:
			if(level == null || level.equals("") || level.equals("wgzrr")){
				intent = new Intent(this,BxcgjcxActivity.class);
			}else{
				intent = new Intent(this,EntInfoListActivity.class);
				intent.putExtra("listFlag",EntInfoListActivity.COMMUSERLIST_FLAG);
			}
			break;
		case R.id.icon_d:
			intent = new Intent(this,GDMapActivity.class);
			if(level == null || level.equals("") || level.equals("wgzrr")){
				String[] myLatLon = MyMapActivity.latLon;
				if(myLatLon == null || myLatLon[0] == null){
					AlertDialog.Builder builder = new Builder(MainChoiceActivity.this);
					builder.setTitle("提示");
					builder.setMessage("获取不到当前位置！");
					builder.create().show();
					return;
				}
				intent.putExtra(GDMapActivity.GIS_KEY, GDMapActivity.DRAW_POINT_FLAG);
				intent.putExtra(GDMapActivity.GIS_DATA_KEY, myLatLon[0] + "!@" + myLatLon[1]);
			}else{
				intent = new Intent(this,EntInfoListActivity.class);
				intent.putExtra("listFlag",EntInfoListActivity.COMMUSERLIST_FLAG);
				intent.putExtra("point",true);
			}
			break;
		case R.id.icon_e:
			intent = new Intent(this,GDMapActivity.class);
			intent.putExtra(GDMapActivity.GIS_KEY, GDMapActivity.DRAW_REG_FLAG);
			break;
		}
	
		if (intent != null)
			startActivity(intent);
	}

}
