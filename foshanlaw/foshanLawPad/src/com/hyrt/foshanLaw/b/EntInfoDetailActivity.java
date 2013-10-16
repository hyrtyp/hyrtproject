package com.hyrt.foshanLaw.b;

import java.lang.reflect.Field;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hyrt.cei.adapter.CommonListAdapter;
import com.hyrt.cei.util.ColumnsUtil;
import com.hyrt.cei.vo.Column;
import com.hyrt.cei.webservice.service.Service;
import com.hyrt.foshanLaw.R;
import com.hyrt.foshanLaw.G.PatrolEntinfohisActivity;
import com.hyrt.foshanLaw.e.InforRecorActivity;
import com.hyrt.mwpm.vo.MwpmBussEntinfo;
import com.hyrt.mwpm.vo.MwpmBussEntupdate;
import com.hyrt.mwpm.vo.MwpmBussNocard;
import com.hyrt.mwpm.vo.MwpmSysMes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class EntInfoDetailActivity extends Activity {
	
	//网格名称
	private StringBuilder reseaName;
	//用户姓名
	private StringBuilder userName = new StringBuilder();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		findViewById(R.id.top_button_left).setVisibility(View.INVISIBLE);
		findViewById(R.id.top_button_right).setVisibility(View.INVISIBLE);
		switch (getIntent().getIntExtra("listFlag",
				EntInfoListActivity.ENTINFOLIST_FLAG)) {
		case EntInfoListActivity.NOTICELIST_FLAG:
			initNotice();
			break;
		case EntInfoListActivity.ENTERPRISERECORDLIST_FLAG:
			initEnterprRecord();
			break;
		case EntInfoListActivity.NOCARDLIST_FLAG:
			initNoCard();
			break;
		case EntInfoListActivity.ERRORENT_FLAG:
			initErrorEnt();
			break;
		}
	}

	private void initErrorEnt() {
		((TextView) findViewById(R.id.top_text)).setText("信息纠错详细");
		MwpmBussEntupdate mwpmBussEntupdate = (MwpmBussEntupdate) getIntent()
				.getSerializableExtra("detailObject");
		JSONArray jsonArray = new JSONArray();
		try {
			Class class1 = Class.forName("com.hyrt.mwpm.vo.MwpmBussEntupdate");
			Field[] fields = class1.getDeclaredFields();
			String jsonArrayStr = "[";
			Column[] columns = ColumnsUtil.getColumnsByTableCode(this,
					"MWPM_BUSS_ENTUPDATE");
			for (int i = 0; i < columns.length; i++) {
				String content = "";
				for (int j = 0; j < fields.length; j++) {
					fields[j].setAccessible(true);
					if (fields[j].getName().equals(
							columns[i].getCode().toLowerCase())) {
						content = (String) fields[j].get(mwpmBussEntupdate);
					}
				}
				if (!columns[i].getName().equals("主键")) {

					if (i != columns.length - 1)
						jsonArrayStr += "{'title' : '" + columns[i].getName()
								+ ":' , " + "'content' : '" + content + "'"
								+ "},";
					else
						jsonArrayStr += "{'title' : '" + columns[i].getName()
								+ ":' , " + "'content' : '" + content + "'"
								+ "}";
				}
			}
			jsonArrayStr += "]";
			jsonArray = new JSONArray(jsonArrayStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		CommonListAdapter commonListAdapter = new CommonListAdapter(this,
				jsonArray, -1);
		ListView listView = (ListView) findViewById(R.id.list);
		listView.setAdapter(commonListAdapter);
	}

	private void initNoCard() {
		((TextView) findViewById(R.id.top_text)).setText("无证无照详细");
		final MwpmBussNocard mwpmBussNocard = (MwpmBussNocard) getIntent()
				.getSerializableExtra("detailObject");
		JSONArray jsonArray = new JSONArray();
		((Button) findViewById(R.id.top_button_right)).setText("企业检查");
		findViewById(R.id.top_button_right).setVisibility(View.VISIBLE);
		findViewById(R.id.top_button_right).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(EntInfoDetailActivity.this,
								PatrolEntinfohisActivity.class);
						intent.putExtra("hide", "true");
						intent.putExtra("eid", mwpmBussNocard.getId());
						intent.putExtra("type","无证无照");
						intent.putExtra("flage", "wzwz");
						intent.putExtra("ename", mwpmBussNocard.getTitle());
						intent.putExtra("zcnumber", mwpmBussNocard.getOperator());
						startActivity(intent);
					}
				});
		try {
			Class class1 = Class.forName("com.hyrt.mwpm.vo.MwpmBussNocard");
			Field[] fields = class1.getDeclaredFields();
			String jsonArrayStr = "[";
			Column[] columns = ColumnsUtil.getColumnsByTableCode(this,
					"MWPM_BUSS_NOCARD");
			for (int i = 0; i < columns.length; i++) {
				String content = "";
				for (int j = 0; j < fields.length; j++) {
					fields[j].setAccessible(true);
					if (fields[j].getName().equals(
							columns[i].getCode().toLowerCase())) {
						content = (String) fields[j].get(mwpmBussNocard);
					}
					
				}
				
				if (!columns[i].getName().equals("主键")) {
					if(columns[i].getName().equals("提交人")){
						final String userId = content;
						new Thread(new Runnable() {
							
							@Override
							public void run() {
								try {
									userName.append(new JSONObject(Service.getUserListByuserId1(userId)).get("name"));
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}).start();
						Thread.sleep(2000);
						content = userName.toString();
					}
						
					if (i != columns.length - 1)
						jsonArrayStr += "{'title' : '"
								+ columns[i].getName() + ":' , "
								+ "'content' : '" + content + "'" + "},";
					else
						jsonArrayStr += "{'title' : '"
								+ columns[i].getName() + ":' , "
								+ "'content' : '" + content + "'" + "}";
				}

			}
			jsonArrayStr += "]";
			jsonArray = new JSONArray(jsonArrayStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		CommonListAdapter commonListAdapter = new CommonListAdapter(this,
				jsonArray, -1);
		ListView listView = (ListView) findViewById(R.id.list);
		listView.setAdapter(commonListAdapter);
	}

	private void initEnterprRecord() {
		((TextView) findViewById(R.id.top_text)).setText("主体详细");
		final MwpmBussEntinfo mwpmBussEntinfo = (MwpmBussEntinfo) getIntent()
				.getSerializableExtra("detailObject");
		new Thread(new Runnable(){
			public void run(){
				reseaName = new StringBuilder(Service.getReseaNameById(mwpmBussEntinfo.getReseauid()));
			}
		}).start();
		JSONArray jsonArray = new JSONArray();
		try {
			Class class1 = Class.forName("com.hyrt.mwpm.vo.MwpmBussEntinfo");
			Field[] fields = class1.getDeclaredFields();
			String jsonArrayStr = "[";
			Column[] columns = ColumnsUtil.getColumnsByTableCode(this,
					"MWPM_BUSS_ENTINFO");
			for (int i = 0; i < columns.length; i++) {
				String content = "";
				for (int j = 0; j < fields.length; j++) {
					fields[j].setAccessible(true);
					if (fields[j].getName().equals(
							columns[i].getCode().toLowerCase())) {
						content = (String) fields[j].get(mwpmBussEntinfo);
					}
				}
				if (!columns[i].getName().equals("主键ID")) {
						jsonArrayStr += "{'title' : '" + columns[i].getName()
						+ " : ', " + "'content' : '" +content + "'" + "},";
				}

			}
			jsonArrayStr += "]";
			jsonArray = new JSONArray(jsonArrayStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		CommonListAdapter commonListAdapter = new CommonListAdapter(this,
				jsonArray, -1,reseaName);
		ListView listView = (ListView) findViewById(R.id.list);
		listView.setAdapter(commonListAdapter);
		findViewById(R.id.top_button_right).setVisibility(View.VISIBLE);
		((Button) findViewById(R.id.top_button_right)).setText("企业检查");
		findViewById(R.id.top_button_right).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(EntInfoDetailActivity.this,
								PatrolEntinfohisActivity.class);
						intent.putExtra("hide", "true");
						intent.putExtra("eid", mwpmBussEntinfo.getId());
						intent.putExtra("ename", mwpmBussEntinfo.getName());
						intent.putExtra("type","日常检查");
						intent.putExtra("zcnumber", mwpmBussEntinfo.getEnrol());
						intent.putExtra("eid", mwpmBussEntinfo.getAddress());
						intent.putExtra("member", mwpmBussEntinfo.getMember());
						intent.putExtra("address", mwpmBussEntinfo.getAddress());
						startActivity(intent);
					}
				});
		((Button) findViewById(R.id.top_button_left)).setText("纠错录入");
		// findViewById(R.id.top_button_left).setVisibility(View.VISIBLE);
		findViewById(R.id.top_button_left).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(EntInfoDetailActivity.this,
								InforRecorActivity.class);
						intent.putExtra("eid", mwpmBussEntinfo.getId());
						intent.putExtra("ename", mwpmBussEntinfo.getName());
						intent.putExtra("zcnumber", mwpmBussEntinfo.getEnrol());
						startActivity(intent);
					}
				});
	}

	private void initNotice() {
		((TextView) findViewById(R.id.top_text)).setText("公告详细");
		MwpmSysMes mwpmSysMes = (MwpmSysMes) getIntent().getSerializableExtra(
				"detailObject");
		JSONArray jsonArray = new JSONArray();
		try {
			Class class1 = Class.forName("com.hyrt.mwpm.vo.MwpmSysMes");
			Field[] fields = class1.getDeclaredFields();
			String jsonArrayStr = "[";
			Column[] columns = ColumnsUtil.getColumnsByTableCode(this,
					"MWPM_SYS_MES");
			for (int i = 0; i < columns.length; i++) {
				String content = "";
				for (int j = 0; j < fields.length; j++) {
					fields[j].setAccessible(true);
					if (fields[j].getName().equals(
							columns[i].getCode().toLowerCase())) {
						content = (String) fields[j].get(mwpmSysMes);
					}
				}
				if (!columns[i].getName().equals("公告类型")
						&& !columns[i].getName().equals("标识")) {
					if (i != columns.length - 1)
						jsonArrayStr += "{'title' : '" + columns[i].getName()
								+ ":' , " + "'content' : '" + content + "'"
								+ "},";
					else
						jsonArrayStr += "{'title' : '" + columns[i].getName()
								+ ":' , " + "'content' : '" + content + "'"
								+ "}";

				}

			}
			jsonArrayStr += "]";
			jsonArray = new JSONArray(jsonArrayStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		CommonListAdapter commonListAdapter = new CommonListAdapter(this,
				jsonArray, -1);
		ListView listView = (ListView) findViewById(R.id.list);
		listView.setAdapter(commonListAdapter);
	}

}
