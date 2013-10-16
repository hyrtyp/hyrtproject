package com.hyrt.foshanLaw.f;

import com.hyrt.foshanLaw.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class FmainActivity extends Activity implements OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.f_main);
		findViewById(R.id.icon_a).setOnClickListener(this);
		findViewById(R.id.icon_b).setOnClickListener(this);
		findViewById(R.id.icon_c).setOnClickListener(this);
		findViewById(R.id.icon_d).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = null;
		switch (v.getId()) {
		case R.id.icon_a:
			intent = new Intent(this,
					FsearchActivity.class);
			intent.putExtra("TITLE", "主体巡查统计");
			intent.putExtra("M", "ShowUncheckedPatrolReport");
			break;
		case R.id.icon_b:
			intent = new Intent(this, FsearchActivity.class);
			intent.putExtra("TITLE", "主体回查统计");
			intent.putExtra("M", "ShowUnRecheckedPatrolReport");
			break;
		case R.id.icon_c:
			intent = new Intent(this, FsearchActivity.class);
			intent.putExtra("TITLE", "未巡查任务统计");
			intent.putExtra("M", "ShowCheckedEntReport");
			break;
		case R.id.icon_d:
			intent = new Intent(this, FsearchActivity.class);
			intent.putExtra("TITLE", "未回查任务统计");
			intent.putExtra("M", "ShowRecheckedEntReport");
			break;
		}
		if (intent != null)
			startActivity(intent);
	}
}
