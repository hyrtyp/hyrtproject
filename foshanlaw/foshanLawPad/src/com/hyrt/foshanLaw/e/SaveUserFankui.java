package com.hyrt.foshanLaw.e;

import java.util.Date;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hyrt.cei.util.StringUtil;
import com.hyrt.cei.webservice.service.Service;
import com.hyrt.foshanLaw.R;

public class SaveUserFankui extends Activity implements OnClickListener {
	TextView tv_userid;
	EditText et_content;
	Button btn_tj;
	String str_title, str_content, str_userid, str_time;
	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			Toast.makeText(SaveUserFankui.this, "用户信息提交成功", Toast.LENGTH_LONG)
					.show();
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.b_yhxxfklr);
		tv_userid = (TextView) findViewById(R.id.b_dqdlr);
		et_content = (EditText) findViewById(R.id.b_fknr);
		btn_tj = (Button) findViewById(R.id.a_kscx);
		btn_tj.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.a_kscx:
			str_content = et_content.getText().toString();
			if (str_content != null && !str_content.equals("")) {
				sendData();
			} else {
				Toast.makeText(SaveUserFankui.this, "您没有填写标题或内容",
						Toast.LENGTH_LONG).show();
			}

			break;

		default:
			break;
		}

	}

	private void sendData() {
		new Thread() {

			@Override
			public void run() {
				getData();
				String rs = Service.saveUserFankui(str_content, str_userid,
						str_time);
				System.out.println(rs);
				handler.sendEmptyMessage(1);

			}

		}.start();

	}

	private void getData() {
		SharedPreferences settings = getSharedPreferences("userinfo",
				Activity.MODE_PRIVATE);
		str_userid = settings.getString("userid", "admin");
		Date date = new Date();
		str_time = StringUtil.DateToStr(date);

	}

}
