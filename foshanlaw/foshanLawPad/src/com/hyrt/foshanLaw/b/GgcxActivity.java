package com.hyrt.foshanLaw.b;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.hyrt.cei.webservice.service.Service;
import com.hyrt.foshanLaw.R;
import com.hyrt.foshanLaw.e.NoticeSend;
import com.hyrt.mwpm.vo.MwpmSysMes;

/**
 * 公告查询
 * 
 * @author user1
 * 
 */
public class GgcxActivity extends Activity {
	// 名称
	private String mc;
	// 提交按钮
	private Button submit;
	// 清空按钮
	private Button clear;
	// 名称输入框
	private EditText edit_mc;
	Button btn_fb;
	String str_username, str_userid;
	boolean flag;
	//起始日期 截止日期
	private String qsrq, jzrq;
	private DatePicker dt_a_jzrq, dt_a_qsrq;
	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 2) {
				btn_fb.setVisibility(View.INVISIBLE);
			} else {
				btn_fb.setVisibility(View.VISIBLE);

			}

		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.b_ggcx);
		dt_a_qsrq = (DatePicker) findViewById(R.id.a_qsrq);
		dt_a_jzrq = (DatePicker) findViewById(R.id.a_jzrq);
		edit_mc = (EditText) findViewById(R.id.a_mc);
		submit = (Button) findViewById(R.id.a_kscx);
		SharedPreferences settings = getSharedPreferences("userinfo",
				Activity.MODE_PRIVATE);
		str_username = settings.getString("name", "jch");
		btn_fb = (Button) findViewById(R.id.top_right_btn);
		getData();
		if (btn_fb.VISIBLE == View.VISIBLE) {
			btn_fb.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(GgcxActivity.this,
							NoticeSend.class);
					startActivity(intent);
				}
			});

		}
		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mc = edit_mc.getText().toString();
				Intent intent = new Intent(GgcxActivity.this,
						EntInfoListActivity.class);
				MwpmSysMes mwpmSysMes = new MwpmSysMes();
				qsrq = dt_a_qsrq.getYear()+"-"+(dt_a_qsrq.getMonth() + 1)+"-"+dt_a_qsrq.getDayOfMonth();
				jzrq = dt_a_jzrq.getYear()+"-"+(dt_a_jzrq.getMonth() + 1)+"-"+dt_a_jzrq.getDayOfMonth();
				mwpmSysMes.setTitle(mc);
				mwpmSysMes.setCreatetimes(qsrq);
				mwpmSysMes.setCreatetimee(jzrq);
				intent.putExtra(
						"listFlag",
						getIntent().getIntExtra("listFlag",
								EntInfoListActivity.NOTICELIST_FLAG));
				intent.putExtra("MwpmSysMes", mwpmSysMes);
				startActivity(intent);
			}
		});
	}

	private void getData() {
		new Thread() {

			@Override
			public void run() {
				String rs = Service.getUserLevel(str_username);
				try {
					JSONObject object = new JSONObject(rs);
					String level;
					level = object.getString("level");
					System.out.println(level);
					if ("wgzrr".equals(level) || "".equals(level)) {
						flag = false;
						handler.sendEmptyMessage(2);
					} else {
						flag = true;
						handler.sendEmptyMessage(1);
					}
				} catch (JSONException e) {
					System.out.println(e);
					e.printStackTrace();
				}
			}

		}.start();

	}

}
