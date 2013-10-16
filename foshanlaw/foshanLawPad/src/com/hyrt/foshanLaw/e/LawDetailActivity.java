package com.hyrt.foshanLaw.e;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.hyrt.cei.adapter.LawDetailAdapter;
import com.hyrt.cei.util.XmlUtil;
import com.hyrt.cei.webservice.service.Service;
import com.hyrt.foshanLaw.R;

public class LawDetailActivity extends Activity implements OnClickListener {
	List<Law> list;
	TextView et_fg, et_bm, et_date, et_content, et_top;
	String ids;
	Button img1, img2;
	JSONArray arr;
	Law law;
	int flag;
	Context mcontext;
	LawDetailAdapter adapter;
	List<Map<String, String>> listdata;
	ListView listview;
	private TextView tv_dm, tv_bz, tv_content;
	String dm, bz, ms;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feng_list_hastitle);
		mcontext = LawDetailActivity.this;
		ids = getIntent().getExtras().getString("id");
		// listview = (ListView) findViewById(R.id.list);
		et_top = (TextView) findViewById(R.id.top_text);
		img1 = (Button) findViewById(R.id.top_button_left);
		img2 = (Button) findViewById(R.id.top_button_right);
		tv_dm = (TextView) findViewById(R.id.text1);
		tv_bz = (TextView) findViewById(R.id.text2);
		tv_content = (TextView) findViewById(R.id.flfgnr);
		flag = getIntent().getExtras().getInt("flag");
		initData();
		loadData();
		// tv_content.setText(law.getMs());
		img1.setVisibility(View.INVISIBLE);

		if (flag == 0) {
			img2.setOnClickListener(this);
			img2.setText("发送短信");
		} else {
			img2.setVisibility(View.INVISIBLE);
		}

	}

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			if (listdata != null) {
				// adapter = new LawDetailAdapter(mcontext, listdata);
				// listview.setAdapter(adapter);
				// tv_content.setText(law.getMs());
				tv_dm.setText(law.getDm());
				tv_bz.setText(law.getBz());
				tv_content.setText(law.getMs());
			}

		}

	};

	private void initData() {
		switch (flag) {
		case 0:
			et_top.setText("法律法规详细");
			break;

		case 1:
			et_top.setText("办案工作指引详细");
			break;
		case 2:
			et_top.setText("检查工作指引详细");
			break;
		}

	}

	// 调用详细
	private void loadData() {
		new Thread() {

			@Override
			public void run() {

				try {
					listdata = new ArrayList<Map<String, String>>();
					Map<String, String> map1 = new HashMap<String, String>();
					Map<String, String> map2 = new HashMap<String, String>();
					Map<String, String> map3 = new HashMap<String, String>();
					Map<String, String> map4 = new HashMap<String, String>();
					System.out.println("rs");
					String rs = Service.queryCodeFlagByid(ids);
					law = new Law();
					JSONObject object = new JSONObject(rs);
					String dm = object.getString("dm");
					map1.put("somekey", "法律法规代码");
					map1.put("value", dm);
					law.setDm(dm);
					String bz = object.getString("bz");
					map2.put("somekey", "法律法规名称");
					map2.put("value", bz);
					law.setBz(bz);
					String time = new Date(Long.parseLong(object.getJSONObject(
							"yxq").getString("time"))).toLocaleString()
							.substring(0, 9);
					law.setYxq(time);
					map3.put("somekey", "法律法规有效期");
					map3.put("value", time);
					ms = object.getString("ms");
					map4.put("somekey", "法律法规内容");
					map4.put("value", ms);
					law.setMs(ms);
					listdata.add(map1);
					listdata.add(map2);
					listdata.add(map3);
					// listdata.add(map4);
					handler.sendEmptyMessage(1);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}.start();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.top_button_right:
			Intent intent = new Intent(LawDetailActivity.this,
					SendEmailActivity.class);
			intent.putExtra("ms", law.getMs());
			startActivity(intent);
			break;

		default:
			break;
		}

	}

}
