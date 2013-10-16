package com.hyrt.foshanLaw.f;

import com.hyrt.cei.util.MyTools;
import com.hyrt.foshanLaw.R;
import com.hyrt.foshanLaw.b.EntInfoListActivity;
import com.hyrt.mwpm.vo.MwpmBussPatrol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class FsearchActivity extends Activity {
	// 起始时间日期控件
	private DatePicker dp_qsrq;
	// 截止时间日期控件
	private DatePicker dp_jzrq;

	// 起始日期
	private String qsrq;
	// 截止日期
	private String jzrq;

	private String URL;

	private TextView top_text;
	private Intent i;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.f_tjfx);
		dp_qsrq = (DatePicker) findViewById(R.id.f_qsrq);
		dp_jzrq = (DatePicker) findViewById(R.id.f_jzrq);
		top_text = (TextView) findViewById(R.id.top_text);
		i = getIntent();
		top_text.setText(i.getStringExtra("TITLE"));
		findViewById(R.id.f_kscx).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				qsrq = dp_qsrq.getYear() + "-" + (dp_qsrq.getMonth() + 1) + "-"
						+ dp_qsrq.getDayOfMonth();
				jzrq = dp_jzrq.getYear() + "-" + (dp_jzrq.getMonth() + 1) + "-"
						+ dp_jzrq.getDayOfMonth();
				URL = MyTools.TJFX_URL + i.getStringExtra("M") + "&pageBeanType=menu&startTime="
						+ qsrq + "&endTime=" + jzrq + "&phoneHost=phoneHost";
				Intent intent = new Intent(FsearchActivity.this,
						FwebActivity.class);
				intent.putExtra("URL", URL);
				startActivity(intent);
			}
		});

	}
}
