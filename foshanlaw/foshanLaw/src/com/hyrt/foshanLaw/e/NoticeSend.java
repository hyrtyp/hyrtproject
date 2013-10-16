package com.hyrt.foshanLaw.e;

import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.AlteredCharSequence;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hyrt.cei.util.StringUtil;
import com.hyrt.cei.webservice.service.Service;
import com.hyrt.foshanLaw.R;

public class NoticeSend extends Activity implements OnClickListener {
	Button btn_fb, btn_clear;
	EditText et_title, et_content;
	String str_title, str_content, str_type, str_userid, str_time,
			str_loginName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.b_ggfb);
		initView();

	}

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				Toast.makeText(NoticeSend.this, "公告发布成功", Toast.LENGTH_SHORT)
						.show();
			}
		}

	};

	private void initView() {

		et_title = (EditText) findViewById(R.id.et_title);
		et_content = (EditText) findViewById(R.id.et_content);
		btn_fb = (Button) findViewById(R.id.a_kscx);
		btn_fb.setOnClickListener(this);
		// btn_clear=(Button)findViewById(R.id.);

		SharedPreferences settings = getSharedPreferences("userinfo",
				Activity.MODE_PRIVATE);
		str_userid = settings.getString("userid", "admin");
		str_loginName = settings.getString("name", "admin");
		str_type = "1";
		Date date = new Date();
		str_time = StringUtil.DateToStr(date);
		System.out.println(str_time);

	}

	private void clear() {
		et_title.setText("");
		et_content.setText("");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.a_kscx:
			str_title = et_title.getText().toString();
			str_content = et_content.getText().toString();
			if ((str_title != null && !str_title.equals(""))
					&& (str_content != null && !str_content.equals(""))) {
				sendData();
			} else {
				Toast.makeText(NoticeSend.this, "您没有填写标题或内容", Toast.LENGTH_LONG)
						.show();
			}
			break;
		default:
			clear();
			break;
		}

	}

	private void dialog() {
		AlertDialog.Builder builder = new Builder(NoticeSend.this);
		builder.setTitle("提示").setMessage("发布成功").create().show();
	}

	private void sendData() {
		new Thread() {

			@Override
			public void run() {

				String rs = Service.saveNotice(str_title, str_content,
						str_loginName, str_time, str_type);
				handler.sendEmptyMessage(1);

			}

		}.start();

	}

}
