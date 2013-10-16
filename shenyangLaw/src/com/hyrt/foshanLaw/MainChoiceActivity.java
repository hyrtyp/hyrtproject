package com.hyrt.foshanLaw;

import com.hyrt.NFC_ReTAG_PRO.NFC_ReTAG;
import com.hyrt.foshanLaw.G.GMainActivity;
import com.hyrt.foshanLaw.b.DMainChoiceActivity;
import com.hyrt.foshanLaw.b.EntInfoListActivity;
import com.hyrt.foshanLaw.b.GgcxActivity;
import com.hyrt.foshanLaw.e.SystemKnowActivity;
import com.hyrt.foshanLaw.f.FmainActivity;
import com.hyrt.foshanLaw.i.ImainActivity;
import com.hyrt.foshanLaw.pptclient.PPTClientActivity;
import com.hyrt.foshanLaw.pptclient.PPTService;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;

public class MainChoiceActivity extends Activity implements OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_choice);
		// 启动服务
		//PPTService.Start(this.getApplicationContext());
		findViewById(R.id.icon_a).setOnClickListener(this);
		findViewById(R.id.icon_b).setOnClickListener(this);
		findViewById(R.id.icon_c).setOnClickListener(this);
		findViewById(R.id.icon_d).setOnClickListener(this);
		findViewById(R.id.icon_e).setOnClickListener(this);
		findViewById(R.id.icon_f).setOnClickListener(this);
		findViewById(R.id.icon_g).setOnClickListener(this);
		findViewById(R.id.icon_h).setOnClickListener(this);
		findViewById(R.id.icon_i).setOnClickListener(this);

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			AlertDialog alertDialog = new AlertDialog.Builder(this)
					.setTitle("退出")
					.setMessage("确定退出系统？")
					.setIcon(R.drawable.icon)
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									MainChoiceActivity.this.finish();
								}
							})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub

								}
							}).create();
			alertDialog.show();
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {

		case R.id.icon_a:
			intent = new Intent(this, EntInfoListActivity.class);
			intent.putExtra("listFlag", EntInfoListActivity.TASKLIST_FLAG);
			break;
		case R.id.icon_b:
			intent = new Intent(this, GMainActivity.class);
			break;
		case R.id.icon_c:
			intent = new Intent(this, PPTClientActivity.class);
			break;
		case R.id.icon_d:
			intent = new Intent(this,
					com.hyrt.foshanLaw.b.MainChoiceActivity.class);
			break;
		case R.id.icon_e:
			intent = new Intent(this, GgcxActivity.class);
			break;
		case R.id.icon_f:
			intent = new Intent(this, DMainChoiceActivity.class);
			break;
		case R.id.icon_g:
			intent = new Intent(this, FmainActivity.class);
			break;
		case R.id.icon_h:
			intent = new Intent(this, SystemKnowActivity.class);
			break;
		case R.id.icon_i:
			intent = new Intent(this, ImainActivity.class);
			break;
		}
		if (intent != null)
			startActivity(intent);
	}
	
	@Override
	protected void onDestroy() {
		//PPTService.Stop(this.getApplicationContext());
		super.onDestroy();
	}

}
