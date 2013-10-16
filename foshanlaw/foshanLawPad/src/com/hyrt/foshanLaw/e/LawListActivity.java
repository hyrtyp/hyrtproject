package com.hyrt.foshanLaw.e;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyrt.cei.adapter.LawAdapter;
import com.hyrt.cei.util.XmlUtil;
import com.hyrt.cei.webservice.service.Service;
import com.hyrt.foshanLaw.R;

public class LawListActivity extends Activity implements OnClickListener,
		OnItemClickListener {
	List<Law> list;
	List<Law> datas;
	LawAdapter adapter;
	Context context;
	private ListView listview;
	int flag;
	String flags, str_dm, str_bz;
	TextView tv1, tv2, tv3;
	private Button footers;
	Button img_left, img_right;
	int pageno;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = LawListActivity.this;
		setContentView(R.layout.list_hastitle);
		tv1 = (TextView) findViewById(R.id.text1);
		tv2 = (TextView) findViewById(R.id.text2);
		tv3 = (TextView) findViewById(R.id.top_text);
		img_left = (Button) findViewById(R.id.top_button_left);
		img_right = (Button) findViewById(R.id.top_button_right);
		img_left.setVisibility(View.INVISIBLE);
		img_right.setVisibility(View.INVISIBLE);
		String[] args = getIntent().getStringArrayExtra("args");
		flags = args[0];
		Integer num = Integer.parseInt(flags);
		flag = num.intValue();
		str_dm = args[1];
		str_bz = args[2];
		initView();
		initData();
		loadData();

	}

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 2:
				footers.setVisibility(View.GONE);
				adapter.notifyDataSetChanged();
				break;
			case 3:
				Toast.makeText(LawListActivity.this, "没有更多数据",
						Toast.LENGTH_LONG).show();
				footers.setVisibility(View.GONE);
				break;
			default:
				if (list != null) {
					adapter = new LawAdapter(context, list);
					listview.setAdapter(adapter);

				}

			}

		}
	};

	private void loadData() {
		pageno++;
		list = new ArrayList<Law>();
		new Thread() {

			@Override
			public void run() {

				String str = Service.queryCodeFlag(flag, str_dm, str_bz, pageno
						+ "");
				if (str.equals("")) {
					handler.sendEmptyMessage(3);
					return;
				}
				list = XmlUtil.parseLaws(str, list);
				try {
					if (list != null) {
						handler.sendEmptyMessage(1);
					}

				} catch (Exception e) {
					System.out.println(e);
					e.printStackTrace();

				}

			}

		}.start();

	}

	private void addmore() {
		pageno++;
		final List<Law> data = new ArrayList<Law>();
		datas = new ArrayList<Law>();
		new Thread() {

			@Override
			public void run() {

				String str = Service.queryCodeFlag(flag, str_dm, str_bz, pageno
						+ "");
				if (str.equals("")) {
					handler.sendEmptyMessage(3);
					return;
				}
				datas = XmlUtil.parseLaws(str, data);
				list.addAll(datas);
				Message msg = new Message();
				msg.arg1 = datas.size();
				try {
					if (datas != null && msg.arg1 > 8) {
						handler.sendEmptyMessage(1);
					} else {
						handler.sendEmptyMessage(2);
					}

				} catch (Exception e) {
					System.out.println(e);
					e.printStackTrace();

				}

			}

		}.start();
	}

	private void initView() {
		listview = (ListView) findViewById(R.id.list);
		listview.setCacheColorHint(0);
		listview.setOnItemClickListener(LawListActivity.this);
		View view = LayoutInflater.from(LawListActivity.this).inflate(
				R.layout.common_listview_bottom, null);
		footers = (Button) view.findViewById(R.id.morebtn);
		listview.addFooterView(view);
		footers.setOnClickListener(LawListActivity.this);

	}

	private void initData() {
		switch (flag) {
		case 0:
			tv1.setText("法律法规代码");
			tv2.setText("法律法规名称");
			tv3.setText("法律法规列表");
			break;

		case 1:
			tv1.setText("办案工作指引代码");
			tv2.setText("办案工作名称");
			tv3.setText("办案工作指引列表");
			break;
		case 2:
			tv1.setText("检查工作指引代码");
			tv2.setText("检查工作指引名称");
			tv3.setText("检查工作指引列表");
			break;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intent = new Intent(LawListActivity.this,
				LawDetailActivity.class);
		Law law = (Law) arg0.getAdapter().getItem(arg2);
		intent.putExtra("id", law.getId() != null ? law.getId() : "");
		intent.putExtra("flag", flag);
		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.morebtn:
			// 加载更多
			addmore();
			break;

		default:
			break;
		}

	}
}
