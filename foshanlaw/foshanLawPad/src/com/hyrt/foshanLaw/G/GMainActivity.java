package com.hyrt.foshanLaw.G;

import com.hyrt.foshanLaw.R;
import com.hyrt.foshanLaw.b.EntInfoListActivity;
import com.hyrt.foshanLaw.b.WzwzcxActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class GMainActivity extends Activity implements OnClickListener {
	private ImageView rcxc, zxxc, qyjc, wzwz,yxbl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.g_main);
		initView();
	}

	private void initView() {
		rcxc = (ImageView) findViewById(R.id.g_rcxc);
		rcxc.setOnClickListener(this);
		zxxc = (ImageView) findViewById(R.id.g_zxxc);
		zxxc.setOnClickListener(this);
		qyjc = (ImageView) findViewById(R.id.g_qyjc);
		qyjc.setOnClickListener(this);
		wzwz = (ImageView) findViewById(R.id.g_wzwz);
		wzwz.setOnClickListener(this);
		yxbl= (ImageView) findViewById(R.id.g_yxbl);
		yxbl.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.g_rcxc:
           intent=new  Intent(this, EntinfoMainActivity.class);
           intent.putExtra("flage", EntinfoMainActivity.RC_FLAGE);
			break;
		case R.id.g_zxxc:
			intent=new  Intent(this, EntinfoMainActivity.class);
	        intent.putExtra("flage", EntinfoMainActivity.ZX_FLAGE);
			break;
		case R.id.g_qyjc:
			intent=new  Intent(this, EntinfoMainActivity.class);
	        intent.putExtra("flage", EntinfoMainActivity.JC_FLAGE);
			break;
		case R.id.g_wzwz:
			intent=new  Intent(this, EntInfoListActivity.class);
			intent.putExtra("listFlag", getIntent().getIntExtra("listFlag", EntInfoListActivity.NOCARDLIST_FLAG));
			intent.putExtra("inFlag", "yes");
			//intent=new Intent(this, WzwzInActivity.class);
			break;
		case R.id.g_yxbl:
			intent=new  Intent(this, EntinfoMainActivity.class);
	        intent.putExtra("flage", EntinfoMainActivity.YX_FLAGE);
			break;
		default:
			break;
		}
		if (intent != null)
			startActivity(intent);
	}
}
