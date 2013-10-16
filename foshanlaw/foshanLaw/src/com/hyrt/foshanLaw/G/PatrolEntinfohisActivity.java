package com.hyrt.foshanLaw.G;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.hyrt.cei.webservice.service.Service;
import com.hyrt.foshanLaw.R;
import com.hyrt.mwpm.vo.MwpmBussPatrolLog;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PatrolEntinfohisActivity extends Activity implements
		OnItemClickListener {
	private String eid, zcNumber, eName, hide,type,flage,pid;
	private Button rightBut;
	private ListView list;
	private List<MwpmBussPatrolLog> patrolLogData;
	private PatrolAdapter adapter;
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
				adapter = new PatrolAdapter(PatrolEntinfohisActivity.this,
						patrolLogData);
				list.setAdapter(adapter);
				list.setOnItemClickListener(PatrolEntinfohisActivity.this);
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.patrol_list);
		pid=getIntent().getStringExtra("pid");
		eid = getIntent().getStringExtra("eid"); //无证无照显示id
		eName = getIntent().getStringExtra("ename");//无证无照显示标题
		zcNumber = getIntent().getStringExtra("zcnumber");//无证无照显示经营者
		type=getIntent().getStringExtra("type");
		hide = getIntent().getStringExtra("hide");
		flage= getIntent().getStringExtra("flage");
		if (eid != null && !eid.equals(""))
		initData();
		initView();
		if("ywc".equals(getIntent().getStringExtra("status")))
			findViewById(R.id.top_button_right).setVisibility(View.GONE);
	}

	private void initData() {
		patrolLogData = new ArrayList<MwpmBussPatrolLog>();
		new Thread() {
			@Override
			public void run() {
				String retu = Service.queryPatrolLOG(eid, pageSize + "",type);
				try {
					JSONArray arr = new JSONArray(retu);
					for (int i = 0; i < arr.length(); i++) {
						MwpmBussPatrolLog patrolLog = new MwpmBussPatrolLog();
						JSONObject temp = (JSONObject) arr.get(i);
						String id = temp.getString("id");
						patrolLog.setId(id);
						String entid = temp.getString("entid");
						patrolLog.setEntid(entid);
						String content = temp.getString("content");
						patrolLog.setContent(content);
						String userid = temp.getString("userid");
						patrolLog.setUserid(userid);
						Date clock= new Date(Long.parseLong(temp.getJSONObject(
								"clock").getString("time")));// 显示
						patrolLog.setClock(clock);
						Date term= new Date(Long.parseLong(temp.getJSONObject(
								"term").getString("time")));// 显示
						patrolLog.setTerm(term);
						String type = temp.getString("type");
						patrolLog.setType(type);
						String area = temp.getString("area");
						patrolLog.setArea(area);
						String remark = temp.getString("remark");
						patrolLog.setRemark(remark);
						String isrecheck = temp.getString("isrecheck");
						patrolLog.setIsrecheck(isrecheck);
						patrolLogData.add(patrolLog);
					}
					handler.sendEmptyMessage(1);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}.start();
	}

	private void initView() {
		TextView textTop = (TextView) findViewById(R.id.top_text);
		TextView text1 = (TextView) findViewById(R.id.text1);
		TextView text2 = (TextView) findViewById(R.id.text2);
		TextView textNumber = (TextView) findViewById(R.id.number);
		TextView textName = (TextView) findViewById(R.id.name);
		TextView member = (TextView) findViewById(R.id.member);
		TextView address = (TextView) findViewById(R.id.address);
		if("wzwz".equals(flage)){
			textTop.setText("无证无照检查");
			textNumber.setText(zcNumber != null ? "经营者：" + zcNumber : "");
			textName.setText(eName != null ? "记录标题：" + eName : "");
		}else{
			textTop.setText("企业检查");
			textNumber.setText(zcNumber != null ? "实体注册号：" + zcNumber : "");
			textName.setText(eName != null ? "实体名称：" + eName : "");
			member.setText("法定代表人：" +getIntent().getStringExtra("member"));
			address.setText("地址：" +getIntent().getStringExtra("address"));
		}
		text1.setText("记录时间");
		text2.setText("责改日期");
		
		findViewById(R.id.top_button_left).setVisibility(View.GONE);
		if ("true".equals(hide)) {
			findViewById(R.id.top_button_right).setVisibility(View.GONE);
		} else {
			rightBut = (Button) findViewById(R.id.top_button_right);
			rightBut.setText("录入");
			rightBut.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					Intent intent = new Intent(PatrolEntinfohisActivity.this,
							SavePatrolLogActivity.class);
					intent.putExtra("eid", eid);
					intent.putExtra("type", type);
					intent.putExtra("pid", pid);
					intent.putExtra("tid", getIntent().getStringExtra("tid"));
					startActivity(intent);
					PatrolEntinfohisActivity.this.finish();
				}
			});
		}
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
		MwpmBussPatrolLog patrolLog = (MwpmBussPatrolLog) arg0.getAdapter()
				.getItem(arg2);
		String lid = patrolLog.getId();
		Intent intent = new Intent(this, PatrolItemActivity.class);
		intent.putExtra("lid", lid);
		intent.putExtra("type", type);
		SharedPreferences patrolLogId = getSharedPreferences("patrolLog",
				Activity.MODE_PRIVATE);
		Editor editor = patrolLogId.edit();
		editor.putString("lid",lid);
		editor.putString("eid",eid);
		editor.commit();
		startActivity(intent);
	}

	private void addMore() {
		pageSize++;
		final List<MwpmBussPatrolLog> data = new ArrayList<MwpmBussPatrolLog>();
		new Thread() {
			@Override
			public void run() {
				String retu = Service.queryPatrolLOG(eid, pageSize + "",type);
				if (retu.equals("")) {
					handler.sendEmptyMessage(3);
					return;
				}
				try {
					JSONArray arr = new JSONArray(retu);
					for (int i = 0; i < arr.length(); i++) {
						MwpmBussPatrolLog patrolLog = new MwpmBussPatrolLog();
						JSONObject temp = (JSONObject) arr.get(i);
						String id = temp.getString("id");
						patrolLog.setId(id);
						String entid = temp.getString("entid");
						patrolLog.setEntid(entid);
						String content = temp.getString("content");
						patrolLog.setContent(content);
						String userid = temp.getString("userid");
						patrolLog.setUserid(userid);
						Date clock= new Date(Long.parseLong(temp.getJSONObject(
								"clock").getString("time")));// 显示
						patrolLog.setClock(clock);
						Date term= new Date(Long.parseLong(temp.getJSONObject(
								"term").getString("time")));// 显示
						patrolLog.setTerm(term);
						String type = temp.getString("type");
						patrolLog.setType(type);
						String area = temp.getString("area");
						patrolLog.setArea(area);
						String remark = temp.getString("remark");
						patrolLog.setRemark(remark);
						String isrecheck = temp.getString("isrecheck");
						patrolLog.setIsrecheck(isrecheck);
						data.add(patrolLog);
					}
					patrolLogData.addAll(data);
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
