package com.hyrt.foshanLaw.e;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyrt.cei.webservice.service.Service;
import com.hyrt.foshanLaw.R;
import com.hyrt.foshanLaw.R.id;

public class SendEmailActivity extends Activity implements OnClickListener,
		OnItemClickListener {
	Button img1, img2;
	TextView tv_top, tv1, tv2;
	String str_reseauid, str_level;
	int pageno;
	ListView lv;
	boolean flag = true;
	private List<EntInfo> list = null;
	private List<EntInfo> data = null;
	List<EntInfo> select = new ArrayList<EntInfo>();;
	List<HashMap<String, Object>> adapterlist = null;
	private MyAdapter adapter;
	private Context context;
	String str_content;
	// 更多按钮
	private Button footer;
	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			// 绑定适配器
			// 点击更多的时候要刷新适配器
			if (adapter == null) {
				showCheckBoxList();
				adapter = new MyAdapter(SendEmailActivity.this, adapterlist);
				lv.setAdapter(adapter);

			}
			if (msg.what == 3) {
				Toast.makeText(getApplicationContext(), "没有更多数据！",
						Toast.LENGTH_SHORT).show();
				footer.setVisibility(View.GONE);

			}
			if (msg.what == 2) {
				showCheckBoxList();
				adapter = new MyAdapter(SendEmailActivity.this, adapterlist);
				lv.setAdapter(adapter);
				// adapter.notifyDataSetChanged();
				footer.setVisibility(View.GONE);
			}
			if (msg.what == 4) {
				Toast.makeText(SendEmailActivity.this, "您没有选择发送的企业",
						Toast.LENGTH_SHORT).show();
			}
			if (msg.what == 5) {
				System.out.println("发送短信");
				/*
				 * AlertDialog dialog = new AlertDialog.Builder(
				 * getApplicationContext()).setTitle("消息提示")
				 * .setMessage("短信发送成功").setPositiveButton("确定", null)
				 * .create(); dialog.show();
				 */

				/*Toast.makeText(SendEmailActivity.this, "短信发送成功",
						Toast.LENGTH_SHORT).show();*/

			} else {
				showCheckBoxList();
				adapter = new MyAdapter(SendEmailActivity.this, adapterlist);
				lv.setAdapter(adapter);
				img1.setText("全选");
				flag = true;

				/*
				 * showCheckBoxList(); adapter.notifyDataSetChanged();
				 */
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_hastitle);
		context = SendEmailActivity.this;
		// 取得法律的内容，全局变量
		str_content = getIntent().getExtras().getString("ms");
		img1 = (Button) findViewById(R.id.top_button_left);
		img2 = (Button) findViewById(id.top_button_right);
		tv_top = (TextView) findViewById(R.id.top_text);
		tv_top.setText("短信发送列表");
		tv1 = (TextView) findViewById(R.id.text1);
		tv2 = (TextView) findViewById(R.id.text2);
		tv1.setText("企业名称");
		tv2.setText("法人代表");
		lv = (ListView) findViewById(R.id.list);
		View view = LayoutInflater.from(SendEmailActivity.this).inflate(
				R.layout.common_listview_bottom, null);
		footer = (Button) view.findViewById(R.id.morebtn);
		lv.addFooterView(view);
		img1.setText("全选");
		img2.setText("发送短信");
		img1.setOnClickListener(this);
		img2.setOnClickListener(this);
		footer.setOnClickListener(this);
		lv.setOnItemClickListener(this);
		initData();
		loadData();
	}

	private void initData() {
		SharedPreferences settings = getSharedPreferences("userinfo",
				Activity.MODE_PRIVATE);
		str_level = settings.getString("level", "jz");
		if ("wgzrr".equals(str_level) || "".equals(str_level)
				|| null == str_level) {
			str_reseauid = settings.getString("reseauid", "1");
		} else {
			str_reseauid = "";
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.top_button_left:
			// 全选
			if (flag == true) {
				selectAll();
				flag = false;
				img1.setText("取消全选");
			} else {
				cacel();
				flag = true;
				img1.setText("全选");
			}
			break;

		case R.id.top_button_right:
			// 发送邮件
			sendEmail();
			break;
		case R.id.morebtn:
			// 加载数据
			System.out.println("加载数据");
			addmore();
			break;
		}

	}

	private void loadData() {
		list = new ArrayList<EntInfo>();
		new Thread() {

			@Override
			public void run() {
				pageno++;
				String rs = Service.queryEntListByemail(str_reseauid, pageno
						+ "");
				if (rs.equals("{'errorcode':'4'}")) {
					handler.sendEmptyMessage(3);
					return;
				}
				System.out.println(rs);
				try {

					JSONArray arr = new JSONArray(rs);
					for (int i = 0; i < arr.length(); i++) {
						JSONObject object = (JSONObject) arr.get(i);
						EntInfo ent = new EntInfo();
						String id = object.getString("id");
						ent.setId(id);
						String ent_name = object.getString("name");
						ent.setEnt_name(ent_name);
						String per_name = object.getString("member1");
						ent.setLaw_name(per_name);
						String email = object.getString("email");
						ent.setEmail(email);
						list.add(ent);
					}
					handler.sendEmptyMessage(1);

				} catch (JSONException e) {
					System.out.println(e);
					e.printStackTrace();
				}

			}

		}.start();
	}

	private void addmore() {
		data = new ArrayList<EntInfo>();
		new Thread() {

			@Override
			public void run() {
				pageno++;
				String rs = Service.queryEntListByemail(str_reseauid, pageno
						+ "");
				if (rs.equals("{'errorcode':'4'}")) {
					handler.sendEmptyMessage(3);
					return;
				}
				System.out.println(rs);
				try {

					JSONArray arr = new JSONArray(rs);
					for (int i = 0; i < arr.length(); i++) {
						JSONObject object = (JSONObject) arr.get(i);
						EntInfo ent = new EntInfo();
						String id = object.getString("id");
						ent.setId(id);
						String ent_name = object.getString("name");
						ent.setEnt_name(ent_name);
						String per_name = object.getString("member1");
						ent.setLaw_name(per_name);
						String email = object.getString("email");
						ent.setEmail(email);
						data.add(ent);
					}
					list.addAll(data);
					Message msg = new Message();
					msg.arg1 = data.size();
					if (msg.arg1 > 8) {
						handler.sendEmptyMessage(1);
					} else {
						handler.sendEmptyMessage(2);
					}

				} catch (JSONException e) {
					System.out.println(e);
					e.printStackTrace();
				}

			}

		}.start();

	}

	public void showCheckBoxList() {
		adapterlist = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < list.size(); i++) {
			// 适配器中要显示的数据
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("item_ent", list.get(i));
			map.put("item_check", false);
			adapterlist.add(map);
		}

	}

	private void selectAll() {
		select = new ArrayList<EntInfo>();
		for (int i = 0; i < list.size(); i++) {
			MyAdapter.isSelected.put(i, true);
			select.add(list.get(i));
		}
		adapter.notifyDataSetChanged();

	}

	private void cacel() {
		select = new ArrayList<EntInfo>();
		for (int i = 0; i < list.size(); i++) {
			if (MyAdapter.isSelected.get(i) == true) {
				MyAdapter.isSelected.put(i, false);
				select.remove(list.get(i));
			}

		}
		adapter.notifyDataSetChanged();

	}

	private void sendEmail() {
		new Thread() {

			@Override
			public void run() {
				StringBuffer sb = new StringBuffer();
				String rs = "";
				if (select != null && select.size() > 0) {
					for (int i = 0; i < select.size(); i++) {
						if (select.get(i).getEmail() != null
								&& !("").equals(select.get(i).getEmail())) {
							sb.append(select.get(i).getEmail() + ";");
						}
					}
					//if (sb.toString().length() - 1 > 0 && !"".equals(sb)) {
						// 发送短信操作
						Intent sendIntent = new Intent(Intent.ACTION_SENDTO,
								Uri.parse("smsto:" + sb));

						sendIntent.putExtra("sms_body", str_content); // 用于附带短信内容，可不加

						context.startActivity(sendIntent);

						/*
						 * //调用接口发送邮件的操作 rs = Service.sendEmail(
						 * sb.toString().substring(0, sb.toString().length() -
						 * 1), str_content);
						 */
						handler.sendEmptyMessage(5);

					 /*else {
						System.out.println("您选择的企业没有邮箱");

					}*/

				} else {
					handler.sendEmptyMessage(4);

				}

			}

		}.start();

	}

	public static class MyAdapter extends BaseAdapter {
		Context mContext;
		List<HashMap<String, Object>> listdata;
		LayoutInflater inflater;
		public static HashMap<Integer, Boolean> isSelected;

		public MyAdapter(Context mContext,
				List<HashMap<String, Object>> listdata) {
			this.mContext = mContext;
			this.listdata = listdata;
			inflater = LayoutInflater.from(mContext);
			init();
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return listdata.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return listdata.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		public void init() {
			// 加载更多的时候会重新绑定适配器，会执行init方法，所有的数据都处于未选中状态
			// 如果用adapter.noticeDataSetChanged()方法，不会更改原有状态
			isSelected = new HashMap<Integer, Boolean>();
			for (int i = 0; i < listdata.size(); i++) {
				isSelected.put(i, false);
			}
		}

		@Override
		public View getView(int arg0, View view, ViewGroup arg2) {
			ViewHolder holder = null;
			if (holder == null) {
				holder = new ViewHolder();
				if (view == null) {
					view = inflater.inflate(R.layout.email_item, null);
				}
				holder.tv_qym = (TextView) view.findViewById(R.id.textview1);
				holder.tv_frm = (TextView) view.findViewById(R.id.textview2);
				holder.cb = (CheckBox) view.findViewById(R.id.checkbox);
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}
			// 内容显示
			HashMap<String, Object> maps = listdata.get(arg0);
			holder.tv_qym.setText(((EntInfo) maps.get("item_ent"))
					.getEnt_name());
			holder.tv_frm.setText(((EntInfo) maps.get("item_ent"))
					.getLaw_name());
			holder.cb.setChecked(isSelected.get(arg0));

			return view;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int arg2, long arg3) {
		ViewHolder holder = (ViewHolder) view.getTag();
		holder.cb.toggle();// 在每次获取点击的item时改变checkbox的状态
		MyAdapter.isSelected.put(arg2, holder.cb.isChecked()); // 同时修改map的值保存状态
		if (holder.cb.isChecked() == true) {
			select.add(list.get(arg2));
		} else {
			select.remove(list.get(arg2));
		}

	}

}
