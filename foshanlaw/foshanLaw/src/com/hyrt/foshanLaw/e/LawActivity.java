package com.hyrt.foshanLaw.e;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hyrt.cei.adapter.LawAdapter;
import com.hyrt.cei.util.XmlUtil;
import com.hyrt.foshanLaw.R;

public class LawActivity extends Activity implements OnClickListener,
		OnItemClickListener {
	Button img1, img2;
	Button btn1, btn2;
	EditText et1, et2;
	TextView tv1, tv2;
	List<Law> list;
	LawAdapter adapter;
	Context context;
	int flag = 0;
	int modelId;
	int num;
	String str_dm, str_bz;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.e);
		initView();
		context = LawActivity.this;
		list = new ArrayList<Law>();
		modelId = getIntent().getIntExtra("model", 0);
		System.out.println(modelId);
		initData();

	}

	private void initView() {
		img1 = (Button) findViewById(R.id.top_button_left);
		img2 = (Button) findViewById(R.id.top_button_right);
		img1.setVisibility(View.INVISIBLE);
		img2.setVisibility(View.INVISIBLE);
		btn1 = (Button) findViewById(R.id.law_rule_config_configSubmit_button);
		btn1.setOnClickListener(this);
		btn2 = (Button) findViewById(R.id.law_rule_config_configCancel_button);
		btn2.setOnClickListener(this);
		tv1 = (TextView) findViewById(R.id.top_text);
		tv2 = (TextView) findViewById(R.id.loginServerText);
		et1 = (EditText) findViewById(R.id.law_rule_code_editText);
		et2 = (EditText) findViewById(R.id.law_rule_describe_editText);
		str_dm = et1.getText().toString();
		str_bz = et2.getText().toString();
	}

	private void initData() {

		switch (modelId) {
		case 0:
			flag = 0;
			tv1.setText("法律法规查询");
			tv2.setText("法律法规编码");
			break;

		case 1:
			flag = 1;
			tv1.setText("办案工作指引");
			tv2.setText("办案指引编码");
			break;
		case 2:
			flag = 2;
			tv1.setText("检查工作指引");
			tv2.setText("检查指引编码");
			break;
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.law_rule_config_configSubmit_button:
			str_dm = et1.getText().toString();
			str_bz = et2.getText().toString();
			Intent intent = new Intent(context, LawListActivity.class);
			String[] args = new String[] { flag + "", str_dm, str_bz };
			intent.putExtra("args", args);
			startActivity(intent);
			break;

		case R.id.law_rule_config_configCancel_button:
			clear();
			break;
		}

	}

	// 清空数据
	private void clear() {
		et1.setText("");
		et2.setText("");
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

	}

}
