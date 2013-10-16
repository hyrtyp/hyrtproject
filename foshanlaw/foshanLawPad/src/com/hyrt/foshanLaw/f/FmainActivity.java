package com.hyrt.foshanLaw.f;

import com.hyrt.foshanLaw.R;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;


/*乐从分局每月巡查记录表
http://192.168.1.11:8090/LCSIS//patrol.do?method=findLcfjmyxcjlb&phoneHost
=当前登录账号
中队巡查统计数
http://192.168.1.11:8090/LCSIS//patrol.do?method=findZdxctjs&phoneHost
=当前登录账号
中队月份巡查户
http://192.168.1.11:8090/LCSIS//patrol.do?method=findZdyfxch&phoneHost
=当前登录账号
文体娱乐类型查询统计
http://192.168.1.11:8090/LCSIS//patrol.do?method=findZdwtxctjs&phoneHost
=当前登录账号 */
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
		findViewById(R.id.tjs).setOnClickListener(this);
		findViewById(R.id.jls).setOnClickListener(this);
		findViewById(R.id.xch).setOnClickListener(this);
		findViewById(R.id.cxtj).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		SharedPreferences settings = getSharedPreferences(
				"userinfo",
				Activity.MODE_PRIVATE);
		String name = settings.getString("name", "");
		Intent intent = null;
		switch (v.getId()) {
		case R.id.icon_a:
			intent = new Intent(this,
					FsearchActivity.class);
			intent.putExtra("TITLE", "主体检查统计");
			intent.putExtra("M", "ShowUncheckedPatrolReport");
			break;
		case R.id.icon_b:
			intent = new Intent(this, FsearchActivity.class);
			intent.putExtra("TITLE", "主体回查统计");
			intent.putExtra("M", "ShowUnRecheckedPatrolReport");
			break;
		case R.id.icon_c:
			intent = new Intent(this, FsearchActivity.class);
			intent.putExtra("TITLE", "未检查任务统计");
			intent.putExtra("M", "ShowCheckedEntReport");
			break;
		case R.id.icon_d:
			intent = new Intent(this, FsearchActivity.class);
			intent.putExtra("TITLE", "未回查任务统计");
			intent.putExtra("M", "ShowRecheckedEntReport");
			break;
		case R.id.tjs:
			intent = new Intent(this,
					FwebActivity.class);
			intent.putExtra("URL", "http://192.168.1.11:8090/LCSIS//patrol.do?method=findLcfjmyxcjlb&phoneHost="+name);
			break;
		case R.id.jls:
			intent = new Intent(this,
					FwebActivity.class);
			intent.putExtra("URL", "http://192.168.1.11:8090/LCSIS//patrol.do?method=findZdxctjs&phoneHost="+name);
			break;
		case R.id.xch:
			intent = new Intent(this,
					FwebActivity.class);
			intent.putExtra("URL", "http://192.168.1.11:8090/LCSIS//patrol.do?method=findZdyfxch&phoneHost="+name);
			break;
		case R.id.cxtj:
			intent = new Intent(this,
					FwebActivity.class);
			intent.putExtra("URL", "http://192.168.1.11:8090/LCSIS//patrol.do?method=findZdwtxctjs&phoneHost="+name);
			break;
		}
		if (intent != null)
			startActivity(intent);
	}
}
