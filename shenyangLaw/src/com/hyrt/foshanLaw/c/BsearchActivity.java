package com.hyrt.foshanLaw.c;


import com.hyrt.foshanLaw.R;
import com.hyrt.foshanLaw.b.EntInfoListActivity;
import com.hyrt.mwpm.vo.MwpmBussPatrol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

public class BsearchActivity extends Activity {
	// 任务名称输入框控件
	private EditText et_rwmc;
	// 任务状态下拉框控件
	private Spinner s_ruzt;
	// 起始时间日期控件
	private DatePicker dp_qsrq;
	// 截止时间日期控件
	private DatePicker dp_jzrq;
	
	//任务名称
	private String rwmc;
	// 起始日期
	private String qsrq;
	// 截止日期
	private String jzrq;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.c_rwcx);
		et_rwmc = (EditText) findViewById(R.id.c_rwmc);
		dp_qsrq = (DatePicker) findViewById(R.id.c_qsrq);
		dp_jzrq = (DatePicker) findViewById(R.id.c_jzrq);
		
		findViewById(R.id.c_kscx).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				qsrq = dp_qsrq.getYear() + "-" + (dp_qsrq.getMonth() + 1) + "-"
						+ dp_qsrq.getDayOfMonth();
				jzrq = dp_jzrq.getYear() + "-" + (dp_jzrq.getMonth()+1) + "-"
						+ dp_jzrq.getDayOfMonth();
				Intent intent = new Intent(BsearchActivity.this,EntInfoListActivity.class);
				MwpmBussPatrol mwpmBussPatrol = new MwpmBussPatrol();
				mwpmBussPatrol.setName(et_rwmc.getText().toString());
				mwpmBussPatrol.setStime(qsrq);
				mwpmBussPatrol.setEtime(jzrq);
				intent.putExtra("MwpmBussPatrol", mwpmBussPatrol);
				intent.putExtra("listFlag",EntInfoListActivity.TASKLIST_FLAG);
				startActivity(intent);
			}
		});
		
		
	}
}
