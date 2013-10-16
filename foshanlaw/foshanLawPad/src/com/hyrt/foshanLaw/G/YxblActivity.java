package com.hyrt.foshanLaw.G;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hyrt.cei.webservice.service.Service;
import com.hyrt.foshanLaw.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class YxblActivity extends Activity {
	private TextView g_stzch, g_stmc;
	private EditText g_yx;
	private String eid, zcNumber, eName;
	private Button g_lr;
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Toast.makeText(getApplicationContext(), "补录成功", Toast.LENGTH_SHORT)
					.show();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.g_yxbl);
		eid = getIntent().getStringExtra("eid");
		eName = getIntent().getStringExtra("ename");
		zcNumber = getIntent().getStringExtra("zcnumber");
		initView();

	}

	private void initView() {
		g_stzch = (TextView) findViewById(R.id.g_stzch);
		g_stzch.setText(zcNumber);
		g_stmc = (TextView) findViewById(R.id.g_stmc);
		g_stmc.setText(eName);
		g_yx = (EditText) findViewById(R.id.g_yx);
		g_lr = (Button) findViewById(R.id.g_lr);
		g_lr.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String yx = g_yx.getText().toString();
				// 手机号正则表达式
				String sss = "^((13[0-9])|(15[0-9])|(18[0-9]))\\d{8}$";
				/*
				 * //邮箱正则表达式 String check =
				 * "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$"
				 * ;
				 */
				Pattern regex = Pattern.compile(sss);
				Matcher matcher = regex.matcher(yx);
				boolean isMatched = matcher.matches();
				if (isMatched) {
					new Thread(runnable).start();
				} else {
					Toast.makeText(getApplicationContext(), "手机号格式不对！",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	Runnable runnable = new Runnable() {

		@Override
		public void run() {
			String rtu = Service.updateEntEmail(eid, g_yx.getText().toString());
			if (rtu.equals("")) {
				handler.sendEmptyMessage(1);
			}
		}
	};
}
