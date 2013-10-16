package com.hyrt.foshanLaw.e;

import com.hyrt.foshanLaw.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class SystemKnowActivity extends Activity implements OnClickListener {
	ImageView img1, img2, img3, img4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		System.out.println("刚进入知识库");
		setContentView(R.layout.sys_know);
		System.out.println("系统知识库选择界面");
		img1 = (ImageView) findViewById(R.id.icon_e_a);
		img2 = (ImageView) findViewById(R.id.icon_e_b);
		img3 = (ImageView) findViewById(R.id.icon_e_c);
		img4 = (ImageView) findViewById(R.id.icon_e_d);
		img1.setOnClickListener(this);
		img2.setOnClickListener(this);
		img3.setOnClickListener(this);
		img4.setOnClickListener(this);

	}

	@Override
	public void onClick(View arg0) {
		Intent intent = null;
		switch (arg0.getId()) {
		case R.id.icon_e_a:
			intent = new Intent(SystemKnowActivity.this, LawActivity.class);
			intent.putExtra("model", 0);
			break;
		case R.id.icon_e_b:
			intent = new Intent(SystemKnowActivity.this, LawActivity.class);
			intent.putExtra("model", 1);
			break;
		case R.id.icon_e_c:
			intent = new Intent(SystemKnowActivity.this, LawActivity.class);
			intent.putExtra("model", 2);
			break;
		case R.id.icon_e_d:
			intent = new Intent(SystemKnowActivity.this, SaveUserFankui.class);
		}
		if (intent != null) {
			startActivity(intent);
		}

	}

}
