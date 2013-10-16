package com.hyrt.foshanLaw.b;

import com.hyrt.foshanLaw.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class DMainChoiceActivity extends Activity implements OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dmain_choice);
		findViewById(R.id.icon_a).setOnClickListener(this);
		findViewById(R.id.icon_b).setOnClickListener(this);
		findViewById(R.id.icon_c).setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.icon_a:
			intent = new Intent(this,BqycxActivity.class);
			intent.putExtra("listFlag", EntInfoListActivity.ENTERPRISERECORDLIST_FLAG);
			break;
		case R.id.icon_b:
			intent = new Intent(DMainChoiceActivity.this,WzwzcxActivity.class);
			break;
		case R.id.icon_c:
			intent = new Intent(DMainChoiceActivity.this,EntInfoListActivity.class);
			intent.putExtra("listFlag",EntInfoListActivity.ERRORENT_FLAG);
			break;
		}
		if (intent != null)
			startActivity(intent);
	}

}
