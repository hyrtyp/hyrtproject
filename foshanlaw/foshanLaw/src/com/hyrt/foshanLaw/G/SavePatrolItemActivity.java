package com.hyrt.foshanLaw.G;

import java.util.ArrayList;
import java.util.List;

import com.hyrt.cei.util.MyTools;
import com.hyrt.cei.util.StringUtil;
import com.hyrt.cei.webservice.service.Service;
import com.hyrt.foshanLaw.R;
import com.hyrt.mwpm.vo.MwpmBussPatrolItem;
import com.hyrt.mwpm.vo.MwpmBussPatrolLog;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SavePatrolItemActivity extends Activity {
	private LinearLayout spnsLayout;
	private Button next;
	private Button leftBtn;
	private String logId;
	private Handler handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if(msg.what==1){
					//Toast.makeText(getApplicationContext(), "录入成功！", Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(getApplicationContext(), "录入失败！", Toast.LENGTH_SHORT).show();
				}
			}};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.g_xcjl2);
		logId=getIntent().getStringExtra("pk");
		initView();
	}

	private void initView() {
		leftBtn = (Button)findViewById(R.id.top_button_left);
		leftBtn.setText("表单录入");
		leftBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(SavePatrolItemActivity.this,TablesActivity.class));
			}
		});
		next = (Button) findViewById(R.id.next);
		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				new Thread(runnable).start();
			}
		});
		spnsLayout = (LinearLayout) findViewById(R.id.spns);
		for (int i = 1; i < spnsLayout.getChildCount(); i++) {
			LinearLayout layout = (LinearLayout) spnsLayout.getChildAt(i);
			final Spinner spinner0 = (Spinner) layout.getChildAt(0);
			final Spinner spinner1 = (Spinner) layout.getChildAt(1);
			spinner0.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// 建立数据源
					String[] mItems = {};
					if (spinner0.getSelectedItem().toString()
							.equals("企业私营个体监管")) {
						mItems = getResources().getStringArray(R.array.wt_1);
					} else if (spinner0.getSelectedItem().toString()
							.equals("市场监督管理")) {
						mItems = getResources().getStringArray(R.array.wt_2);
					} else if (spinner0.getSelectedItem().toString()
							.equals("广告监督管理")) {
						mItems = getResources().getStringArray(R.array.wt_3);
					} else if (spinner0.getSelectedItem().toString()
							.equals("商标监督管理")) {
						mItems = getResources().getStringArray(R.array.wt_4);
					} else if (spinner0.getSelectedItem().toString()
							.equals("消费者权益保护")) {
						mItems = getResources().getStringArray(R.array.wt_5);
					} else if (spinner0.getSelectedItem().toString()
							.equals("经济监察")) {
						mItems = getResources().getStringArray(R.array.wt_6);
					} else if (spinner0.getSelectedItem().toString()
							.equals("合同监督管理")) {
						mItems = getResources().getStringArray(R.array.wt_7);
					}
					// 建立Adapter并且绑定数据源
					ArrayAdapter<String> _Adapter = new ArrayAdapter<String>(
							SavePatrolItemActivity.this,
							android.R.layout.simple_spinner_item, mItems);
					// 绑定 Adapter到控件
					 //设置下拉列表的风格  
					_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  

					spinner1.setAdapter(_Adapter);

				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {

				}
			});
		}
	}

	Runnable runnable = new Runnable() {

		@Override
		public void run() {
			List<MwpmBussPatrolItem> items=new ArrayList<MwpmBussPatrolItem>();
			for(int i = 1; i < spnsLayout.getChildCount(); i++){
				LinearLayout layout = (LinearLayout) spnsLayout.getChildAt(i);
				final Spinner spinner0 = (Spinner) layout.getChildAt(0);
				final Spinner spinner1 = (Spinner) layout.getChildAt(1);
				final Spinner spinner2 = (Spinner) layout.getChildAt(2);
				if(!spinner0.getSelectedItem().toString().equals("--请选择--")){
					MwpmBussPatrolItem patrolItem = new MwpmBussPatrolItem();
					patrolItem.setLogid(logId);
					patrolItem.setContentid(spinner0.getSelectedItem().toString());
					patrolItem.setQid(spinner1.getSelectedItem().toString());
					patrolItem.setDisposeid(spinner2.getSelectedItem().toString());
					items.add(patrolItem);
				}
			}
			String res=Service.savePatrolItem(items);
			if(res.contains("<RETURNCODE>2</RETURNCODE>")||res.equals("<?xml version='1.0' encoding='utf-8'?><ROOT></ROOT>")){
				handler.sendEmptyMessage(1);
				// 巡查事项保存成功，跳转保存证据上传表
				Intent intent = new Intent(SavePatrolItemActivity.this,
						SavePatrolProofActivity.class);
				startActivity(intent);
				SavePatrolItemActivity.this.finish();
			}else{
				handler.sendEmptyMessage(2);
			}
		}
	};
}
