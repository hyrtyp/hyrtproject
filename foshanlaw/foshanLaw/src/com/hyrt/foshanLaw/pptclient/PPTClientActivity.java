package com.hyrt.foshanLaw.pptclient;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.hyrt.foshanLaw.R;
import com.hyrt.foshanLaw.b.NavitApplication;
import com.hyrt.foshanLaw.pptclient.common.CommKey;
import com.hyrt.foshanLaw.pptclient.common.GlobalFunction;
import com.hyrt.foshanLaw.pptclient.dao.GroupItem;
import com.hyrt.foshanLaw.pptclient.dao.SessionUserItem;
import com.hyrt.foshanLaw.pptclient.dao.UserItem;
import com.hyrt.foshanLaw.pptclient.db.business.CacheGroupFunction;
import com.hyrt.foshanLaw.pptclient.db.business.SessionGroupFunction;
import com.hyrt.foshanLaw.pptclient.db.business.SessionItemFunction;
import com.hyrt.foshanLaw.pptclient.db.dao.SessionGroup;

public class PPTClientActivity extends Activity {
	Context context;
	TabHost th;
	ListView lv;
	ListView lv_contact;
	List<SessionGroup> sessionlst;
	List<UserItem> userlist;
	SessionBaseAdapter arr;
	SessionGroupFunction sgfun;
	ResultsRecr resultsRecr;
	CacheGroupFunction cgfun;
	String uid;
	NavitApplication app;

	// 用户选择界面使用
	Button btnnewsession;
	LinearLayout llbody;
	Vfun fun;

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		context = PPTClientActivity.this;
		app = (NavitApplication) getApplication();
		uid = app.getUid();
		// 启动服务
		//PPTService.Start(context);

		th = (TabHost)findViewById(R.id.tabhost);
		th.setup();

		RelativeLayout tb1 = (RelativeLayout) LayoutInflater.from(this)
				.inflate(R.layout.tabcard, null);
		TextView t1 = (TextView) tb1.findViewById(R.id.tabtitle);
		t1.setText("对讲记录");

		RelativeLayout tb2 = (RelativeLayout) LayoutInflater.from(this)
				.inflate(R.layout.tabcard, null);
		TextView t2 = (TextView) tb2.findViewById(R.id.tabtitle);
		t2.setText("用户列表");

		th.addTab(th.newTabSpec("t13").setIndicator(tb1).setContent(R.id.tab1));
		th.addTab(th.newTabSpec("t23").setIndicator(tb2).setContent(R.id.tab2));

		// 会话日志界面
		lv = (ListView) findViewById(R.id.lvsession);
		sgfun = new SessionGroupFunction(context);
		cgfun = new CacheGroupFunction(context);

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				ArrayList<String> receobj = cgfun.getUseridColl(uid, sessionlst
						.get(arg2).getSessionid());

				// 保存当前数据
				sessionlst.get(arg2).setMsgcount(0);
				for (int i = 0; i < sessionlst.size(); i++) {
					sgfun.update(uid, sessionlst.get(arg2).getSessionid(),
							sessionlst.get(arg2).getMsgcount());
				}
				// 清空app中的数据
				app.clearAll();

				showDialog(receobj, sessionlst.get(arg2).getSessionid(),
						sessionlst.get(arg2).getSessionname(),
						sessionlst.get(arg2).getGroup(), false);
			}

		});
		
		lv.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {
				// TODO Auto-generated method stub
				//长按删除
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setMessage("确定删除当前会话记录？").setPositiveButton("确定",new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						String ssid=sessionlst.get(arg2).getSessionid();
						//先删除数据，目前仅删除会话主记录即可
						sgfun.del(ssid);  //主记录
//						cgfun.del(uid, ssid); //成员
//						SessionItemFunction sifun=new SessionItemFunction(context);
//						sifun.del(uid, ssid);   //会话记录 
						
						
						sessionlst.remove(arg2);
						arr.notifyDataSetChanged();
					}
					
				}).setNegativeButton("取消", null);
				AlertDialog ad = builder.create();
				ad.show();
				return false;
			}
		});

		initUser();
		 GlobalFunction.setNotiSetting(context, false);
	}

	/**
	 * 对讲明细列表
	 * 
	 * @param receobj
	 * @param sessionid
	 * @param sessionname
	 * @param isgroup
	 */
	void showDialog(ArrayList<String> receobj, String sessionid,
			String sessionname, boolean isgroup, boolean iscreate) {
		Intent showDialog = new Intent(context, DialogActivity.class);
		showDialog.putExtra("sessionid", sessionid);
		showDialog.putExtra("sessionname", sessionname);
		showDialog.putExtra("isgroup", isgroup);
		showDialog.putExtra("iscreate", iscreate);
		showDialog.putStringArrayListExtra("receobj", receobj);
		startActivity(showDialog);
	}

	void reload() {
		// 历史加载数据
		sessionlst = sgfun.getList(uid);
		for (int i = 0; i < sessionlst.size(); i++) {
			String sid = sessionlst.get(i).getSessionid();
			sessionlst.get(i).setMsgcount(app.getCountBySessionid(sid));
		}
		arr = new SessionBaseAdapter(context, sessionlst);
		lv.setAdapter(arr);
	}

	@Override
	protected void onPause() {
		context.unregisterReceiver(resultsRecr);
		super.onPause();
	}

	@Override
	protected void onResume() {
		// 注册广播
		resultsRecr = new ResultsRecr();
		IntentFilter recevierFilter = new IntentFilter();
		recevierFilter.addAction(CommKey.ui_receiver);
		context.registerReceiver(resultsRecr, recevierFilter);
		reload(); // 重新加载数据
		
		super.onResume();
	}

	/**
	 * 内部接收广播，主要用于接收返回结果
	 */
	class ResultsRecr extends BroadcastReceiver {

		@Override
		public void onReceive(Context Ccontext, Intent intent) {
			String str = intent.getExtras().getString("type");
			// 目标管理返回结果
			if (str.equals(CommKey.type_new)) {
				String sid = intent.getExtras().getString("sessionid");
				app.addNew(sid);
				boolean flag = false;
				for (int i = 0; i < sessionlst.size(); i++) {
					if (sessionlst.get(i).getSessionid().equals(sid)) {
						flag = true;
						sessionlst.get(i).msgAdd();
						arr.notifyDataSetChanged();
						break;
					}
				}
				if (flag == false) {
					// 找不到现有的，直接刷新本地数据
					reload();
				}
			}
		}
	}

	
	
	@Override
	protected void onStop() {
		 
		// TODO Auto-generated method stub
		 
		super.onStop();
	}

	@Override
	protected void onDestroy() {
	// TODO Auto-generated method stub
		//PPTService.Stop(context);
		 GlobalFunction.setNotiSetting(context, true);
		super.onDestroy();
	}

	// ***********************用户选择界面

	void initUser() {
		fun = new Vfun();
		llbody = (LinearLayout) findViewById(R.id.llbody);

		Button btn = (Button) findViewById(R.id.btnnewsession);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String sname = "";
				String sid = GlobalFunction.GetUID();
				List<SessionUserItem> l = fun.getSelectUseridList();
				ArrayList<String> touser = new ArrayList<String>();
				if (l.size() == 0) {
					GlobalFunction.ShowDialog(context, "请选择对讲人员");
					return;
				} else if (l.size() == 1) {// 只选择了一个人
					sname = l.get(0).getUsername()+"、"+app.getUname()+"的对讲";;
					// 如果没有历史的，保存本地,否则不操作
					String tp = sgfun.checkSessionid(uid, l);
					if (tp.equals("") == true) {
						sgfun.add(uid, sid, sname,
								GlobalFunction.GetDateTime(), false);
						cgfun.del(uid, sid);
						cgfun.add(uid, sid, l.get(0).getUserid());
					} else {
						sid = tp;
					}
					touser.add(l.get(0).getUserid());
				} else {
					String stmp = "";
					for (int i = 0; i < l.size() && i < 3; i++) {
						// 创建sessionname
						stmp += "," + l.get(i).getUsername();
					}
					// 创建用户列表
					for (int i = 0; i < l.size(); i++) {
						touser.add(l.get(i).getUserid());
					}
					// 创建会话名称
					sname = "多人会话-" + stmp.substring(1) + "……等";
					String tp = sgfun.checkSessionid(uid, l);
					if (tp.equals("") == true) {
						sgfun.add(uid, sid, sname,
								GlobalFunction.GetDateTime(), false);
						cgfun.del(uid, sid);
						cgfun.add(uid, sid, touser);
					} else {
						sid = tp;
					}
				}
				// 界面
				showDialog(touser, sid, sname, false, true);
			}

		});

		myDialog = ProgressDialog.show(context, "正在加载用户列表", "请稍等......");
		new Thread() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				String m = "ok";
				try {
					 fun.runFromWS(context, uid); //调接口
					 //fun.run(context, uid); // 测试用，调本地
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					m = "err";
				}
				Message msg = new Message();
				Bundle b = new Bundle();
				b.putString("msg", m);
				msg.setData(b);
				msghandler.sendMessage(msg);
			}

		}.start();
	}

	ProgressDialog myDialog;
	Handler msghandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			String m = msg.getData().getString("msg");
			if ("ok".equals(m)) {
				app.setVf(fun);
				List<GroupItem> rgroup = fun.getGlist(""); // 一级目录
				for (GroupItem sub : rgroup) {
					llbody.addView(createView(sub, 1));
				}
				myDialog.dismiss();
			} else {
				myDialog.dismiss();
				Toast.makeText(context, "用户列表加载失败", Toast.LENGTH_SHORT).show();
			}
			super.handleMessage(msg);
		}

	};

	LinearLayout createView(GroupItem obj, int level) {
		// 返回项
		LinearLayout ll = new LinearLayout(context);
		ll.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		ll.setOrientation(LinearLayout.VERTICAL);

		List<UserItem> ulist = fun.getUlist(obj.getGid());
		UIGroupItem ui = new UIGroupItem(obj, ulist, uid, app.getUname(),
				obj.getLevel());
		ll.addView(ui.getView(context, level));

		int cnt = fun.getSubGroupCount(obj.getGid());
		if (cnt > 0) {
			List<GroupItem> sublist = fun.getGlist(obj.getGid());
			for (GroupItem sub : sublist) {
				ui.addView(createView(sub, level + 1));
			}
		}
		return ll;
	}

	// ************************适配器
	public class SessionBaseAdapter extends BaseAdapter {
		private LayoutInflater mInflater;
		private List<SessionGroup> listData;

		private class ViewHolder {
			public ImageView img;
			public TextView tvname;
			public TextView tvtime;
			public ImageView imgnew;
		}

		public SessionBaseAdapter(Context context, List<SessionGroup> _listData) {
			this.mInflater = LayoutInflater.from(context);
			this.listData = _listData;
		}

		@Override
		public int getCount() {
			return listData.size();
		}

		@Override
		public Object getItem(int position) {
			return listData.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.sessionitem, null);
			holder.img = (ImageView) convertView.findViewById(R.id.imgsession);
			holder.tvname = (TextView) convertView.findViewById(R.id.tvsname);
			holder.tvtime = (TextView) convertView.findViewById(R.id.tvtime);
			holder.imgnew = (ImageView) convertView.findViewById(R.id.imgnew);
			if (listData.get(position).getGroup()) {
				holder.img.setImageResource(R.drawable.many);
			} else {
				holder.img.setImageResource(R.drawable.single);
			}
			if (listData.get(position).getMsgcount() > 0) {
				holder.imgnew.setVisibility(View.VISIBLE);
			} else {
				holder.imgnew.setVisibility(View.INVISIBLE);
			}

			holder.tvname.setText(listData.get(position).getSessionname());
			holder.tvtime.setText(listData.get(position).getLasttm());

			convertView.setTag(holder);

			return convertView;
		}
	}
}