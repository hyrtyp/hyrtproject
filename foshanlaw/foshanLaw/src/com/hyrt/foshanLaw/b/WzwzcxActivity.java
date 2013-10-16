package com.hyrt.foshanLaw.b;

import com.hyrt.foshanLaw.R;
import com.hyrt.mwpm.vo.MwpmBussEntinfo;
import com.hyrt.mwpm.vo.MwpmBussNocard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

/**
 * 企业查询
 * @author user1
 *
 */
public class WzwzcxActivity extends Activity {
	private DatePicker dt_a_jzrq, dt_a_qsrq;
	//起始日期 截止日期
	private String qsrq, jzrq;
	// 提交按钮
	private Button submit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.d_wzwzcx);
		dt_a_qsrq = (DatePicker) findViewById(R.id.a_qsrq);
		dt_a_jzrq = (DatePicker) findViewById(R.id.a_jzrq);
		submit = (Button) findViewById(R.id.a_kscx);
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(WzwzcxActivity.this,EntInfoListActivity.class);
				MwpmBussNocard mwpmBussNocard = new MwpmBussNocard();
				qsrq = dt_a_qsrq.getYear()+"-"+(dt_a_qsrq.getMonth() + 1)+"-"+dt_a_qsrq.getDayOfMonth();
				jzrq = dt_a_jzrq.getYear()+"-"+(dt_a_jzrq.getMonth() + 1)+"-"+dt_a_jzrq.getDayOfMonth();
				mwpmBussNocard.setSubmittimes(qsrq);
				mwpmBussNocard.setSubmittimee(jzrq);
				intent.putExtra("listFlag", getIntent().getIntExtra("listFlag", EntInfoListActivity.NOCARDLIST_FLAG));
				intent.putExtra("MwpmBussNocard", mwpmBussNocard);
				startActivity(intent);
			}
		});
	}
}
