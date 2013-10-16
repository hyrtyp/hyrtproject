package com.hyrt.foshanLaw.G;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.hyrt.cei.util.StringUtil;
import com.hyrt.cei.webservice.service.Service;
import com.hyrt.foshanLaw.R;
import com.hyrt.mwpm.vo.MwpmBussEntinfo;
import com.hyrt.mwpm.vo.MwpmBussPatrolItem;
import com.hyrt.mwpm.vo.MwpmBussPatrolLog;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class PatrolItemActivity extends Activity implements OnItemClickListener {
	private ListView list;
	private String lid;
	private List<MwpmBussPatrolItem> EntinfoData;
	private PatrolItemAdapter adapter;
	private int pageSize = 1;
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
				adapter = new PatrolItemAdapter(PatrolItemActivity.this,
						EntinfoData);
				list.setAdapter(adapter);
				//list.setOnItemClickListener(PatrolItemActivity.this);
			}
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
		EntinfoData = new ArrayList<MwpmBussPatrolItem>();
		new Thread() {
			@Override
			public void run() {
				// 40288a273c3dd732013c3dd9dd0d0001
				// 40288a273c3dd732013c3dd9dd0d0001
				String retu = Service.queryMwpmBussPatrolItemList(lid, pageSize
						+ "");
				try {
					JSONArray arr = new JSONArray(retu);
					for (int i = 0; i < arr.length(); i++) {
						MwpmBussPatrolItem patrolItem = new MwpmBussPatrolItem();
						JSONObject temp = (JSONObject) arr.get(i);
						String logid = temp.getString("logid");
						patrolItem.setLogid(logid);
						String contentid = temp.getString("contentid");
						patrolItem.setContentid(contentid);
						String qid = temp.getString("qid");
						patrolItem.setQid(qid);
						String disposeid = temp.getString("disposeid");
						patrolItem.setDisposeid(disposeid);
						EntinfoData.add(patrolItem);
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
		textTop.setText("巡查事项列表");
		TextView text1 = (TextView) findViewById(R.id.text1);
		text1.setText("问题");
		TextView text2 = (TextView) findViewById(R.id.text2);
		text2.setText("巡查事项");
		findViewById(R.id.top_button_left).setVisibility(View.GONE);
		Button reghtBut = (Button) findViewById(R.id.top_button_right);
		reghtBut.setText("回查列表");
		reghtBut.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(PatrolItemActivity.this,
						ReturnLogActivity.class);
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

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		MwpmBussPatrolItem patrolLog = (MwpmBussPatrolItem) arg0.getAdapter()
				.getItem(arg2);
		String lid = patrolLog.getContentid();
		Intent intent = new Intent(this, ReturnLogActivity.class);
		intent.putExtra("cid", lid);
		startActivity(intent);
	}

	private void addMore() {
		pageSize++;
		final List<MwpmBussPatrolItem> data = new ArrayList<MwpmBussPatrolItem>();
		new Thread() {
			@Override
			public void run() {
				String retu = Service.queryMwpmBussPatrolItemList(lid, pageSize
						+ "");
				if (retu.equals("{'errorcode':'4'}")) {
					handler.sendEmptyMessage(3);
					return;
				}
				try {
					JSONArray arr = new JSONArray(retu);
					for (int i = 0; i < arr.length(); i++) {
						MwpmBussPatrolItem patrolItem = new MwpmBussPatrolItem();
						JSONObject temp = (JSONObject) arr.get(i);
						String logid = temp.getString("logid");
						patrolItem.setLogid(logid);
						String contentid = temp.getString("contentid");
						patrolItem.setContentid(contentid);
						String qid = temp.getString("qid");
						patrolItem.setQid(qid);
						String disposeid = temp.getString("disposeid");
						patrolItem.setDisposeid(disposeid);
						data.add(patrolItem);
					}
					EntinfoData.addAll(data);
					Message msg = new Message();
					msg.what = 2;
					msg.arg1 = data.size();
					handler.sendMessage(msg);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}

		}.start();
	}
}
