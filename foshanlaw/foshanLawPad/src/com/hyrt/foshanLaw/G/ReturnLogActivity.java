package com.hyrt.foshanLaw.G;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.hyrt.cei.util.StringUtil;
import com.hyrt.cei.webservice.service.Service;
import com.hyrt.foshanLaw.R;
import com.hyrt.mwpm.vo.MwpmBussEntinfo;
import com.hyrt.mwpm.vo.MwpmBussPatrolItem;
import com.hyrt.mwpm.vo.MwpmBussReturnlog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ReturnLogActivity extends Activity {

	private ListView list;
	private List<MwpmBussReturnlog> returnlogData;
	private ReturnLogAdapter adapter;
	private int pageSize = 1;
	private String lid;
	private TextView more;
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 2) {
				if (msg.arg1 < 10) {
					Toast.makeText(getApplicationContext(), "没有更多数据！",
							Toast.LENGTH_SHORT).show();
					more.setVisibility(View.GONE);
				}
				adapter.notifyDataSetChanged();
			} else if (msg.what == 3) {
				Toast.makeText(getApplicationContext(), "没有更多数据！",
						Toast.LENGTH_SHORT).show();
				more.setVisibility(View.GONE);
			} else {
				adapter = new ReturnLogAdapter(ReturnLogActivity.this,
						returnlogData);
				list.setAdapter(adapter);
			}
			// list.setOnItemClickListener(ReturnLogActivity.this);
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_hastitle);
		lid = getIntent().getStringExtra("lid");
		initData();
		initView();
	}

	private void initData() {
		returnlogData = new ArrayList<MwpmBussReturnlog>();
		new Thread() {
			@Override
			public void run() {
				String retu = Service.queryReturnLogList(lid, pageSize + "");
				try {
					JSONArray arr = new JSONArray(retu);
					for (int i = 0; i < arr.length(); i++) {
						MwpmBussReturnlog returnlog = new MwpmBussReturnlog();
						JSONObject temp = (JSONObject) arr.get(i);
						String id = temp.getString("id");
						returnlog.setId(id);
						String lid = temp.getString("lid");
						returnlog.setLid(lid);
						String checkthing = temp.getString("checkthing");
						returnlog.setCheckthing(checkthing);
						String disposetype = temp.getString("disposetype");
						returnlog.setDisposetype(disposetype);
						Date checktime =new Date(Long.parseLong(temp.getJSONObject("checktime").getString("time")));
						returnlog.setChecktime(checktime);
						returnlogData.add(returnlog);
					}
					handler.sendEmptyMessage(1);
				} catch (Exception e) {
					// TODO: handle exception
				}

			}

		}.start();
	}

	private void initView() {
		TextView textTop = (TextView) findViewById(R.id.top_text);
		textTop.setText("回查列表");
		TextView text1 = (TextView) findViewById(R.id.text1);
		text1.setText("回查情况");
		TextView text2 = (TextView) findViewById(R.id.text2);
		text2.setText("回查时间");
		findViewById(R.id.top_button_left).setVisibility(View.GONE);
		Button reghtBut = (Button) findViewById(R.id.top_button_right);
		reghtBut.setText("回查录入");
		reghtBut.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(ReturnLogActivity.this,
						SaveReturnLogActivity.class);
				intent.putExtra("lid", lid);
				startActivity(intent);
			}
		});
		list = (ListView) findViewById(R.id.list);
		View view = LayoutInflater.from(this).inflate(
				R.layout.common_listview_bottom, null);
		more = (TextView) view.findViewById(R.id.morebtn);
		list.addFooterView(view);
		more.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				addMore();
			}
		});
	}

	private void addMore() {
		pageSize++;
		final List<MwpmBussReturnlog> data = new ArrayList<MwpmBussReturnlog>();
		new Thread() {
			@Override
			public void run() {
				String retu = Service.queryReturnLogList(lid, pageSize + "");
				if (retu.equals("{'errorcode':'4'}")) {
					handler.sendEmptyMessage(3);
					return;
				}
				try {
					JSONArray arr = new JSONArray(retu);
					for (int i = 0; i < arr.length(); i++) {
						MwpmBussReturnlog returnlog = new MwpmBussReturnlog();
						JSONObject temp = (JSONObject) arr.get(i);
						String id = temp.getString("id");
						returnlog.setId(id);
						String lid = temp.getString("lid");
						returnlog.setLid(lid);
						String checkthing = temp.getString("checkthing");
						returnlog.setCheckthing(checkthing);
						String disposetype = temp.getString("disposetype");
						returnlog.setDisposetype(disposetype);
						Date checktime =new Date(Long.parseLong(temp.getJSONObject("checktime").getString("time")));
						returnlog.setChecktime(checktime);
						data.add(returnlog);
					}
					returnlogData.addAll(data);
					Message msg = new Message();
					msg.what = 2;
					msg.arg1 = data.size();
					handler.sendMessage(msg);
				} catch (Exception e) {
					// TODO: handle exception
					handler.sendEmptyMessage(3);
				}
			}

		}.start();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		initData();
	}
}
