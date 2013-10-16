package com.hyrt.foshanLaw.pptclient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyrt.foshanLaw.R;
import com.hyrt.foshanLaw.b.NavitApplication;
import com.hyrt.foshanLaw.pptclient.business.CmdStr;
import com.hyrt.foshanLaw.pptclient.business.MediaFunction;
import com.hyrt.foshanLaw.pptclient.common.CommKey;
import com.hyrt.foshanLaw.pptclient.common.FileObj;
import com.hyrt.foshanLaw.pptclient.common.GlobalFunction;
import com.hyrt.foshanLaw.pptclient.dao.SessionUserItem;
import com.hyrt.foshanLaw.pptclient.db.business.CacheDataFunction;
import com.hyrt.foshanLaw.pptclient.db.business.CacheGroupFunction;
import com.hyrt.foshanLaw.pptclient.db.business.SessionGroupFunction;
import com.hyrt.foshanLaw.pptclient.db.business.SessionItemFunction;
import com.hyrt.foshanLaw.pptclient.db.dao.SessionItem;
 

/**
 * Description:对讲明细界面
 * 
 * @author 郑伟
 * @Date 2013-1-14
 * @Copyright:2013-1-14
 * @Company:华源润通（北京）科技有限公司重庆分公司
 */
public class DialogActivity extends Activity {
	Context context;
	ListView lv;
	List<SessionItem> lst;
	DialogBaseAdapter arr;
	DialogResultRecr dialogResultRecr;
	String uid;
	MediaFunction mfun;
	List<SessionItem> list;
	boolean isgroup;
	boolean canuse; // 是否可用
	ImageView imgmic;
	List<String> receobj;
	String sessionid;
	String sessionname;
	SessionItemFunction sifun;
	SessionGroupFunction sgfun;
	Vfun vfun;
	Map<String,String > sulist;
	NavitApplication app;
	boolean iscreate;

	
	Handler refrushHandler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			long _id=msg.getData().getLong("id", 0);
			//_id;
			//msg
			//处理发送后的刷新
			SessionItem tmp=null;
			for(int i=0;i<lst.size();i++){
				if(lst.get(i).getId()==_id){
					tmp=lst.get(i);
					break;
				}
			}
			if(tmp!=null){
				//发送成功
				tmp.setIssend(2);
				tmp.setErrmsg("发送成功");
				arr.notifyDataSetChanged();
				sifun.update(uid, tmp);
			}else{
			}
			super.handleMessage(msg);
		}
		
	};
	
	
	Runnable delayRun=new Runnable(){

		@Override
		public void run() {
			// TODO Auto-generated method stub
			boolean flag=false;  //有更新才刷新
			for(int i=0;i<lst.size();i++){
				if(lst.get(i).getIssend()==1&&System.currentTimeMillis()-lst.get(i).getId()>14000){
					//30秒超时
					flag=true;
					lst.get(i).setIssend(0);
					lst.get(i).setErrmsg("发送失败");
					sifun.update(uid, lst.get(i));
				}
			}
			if(flag)
			arr.notifyDataSetChanged();
		}
		
	};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialoglist);
		context = DialogActivity.this;
		app = (NavitApplication) getApplication();

		if (GlobalFunction.checkNetWork(context)) {
			canuse = true;
		} else {
			canuse = false;
		}

		mfun = new MediaFunction(context);
		sifun = new SessionItemFunction(context);
		sgfun = new SessionGroupFunction(context);
		vfun = app.getVf();

		// 传过来的参数
		Intent theIntent = this.getIntent();
		uid = app.getUid();
		sessionid = theIntent.getStringExtra("sessionid");
		sessionname = theIntent.getStringExtra("sessionname");
		receobj = theIntent.getStringArrayListExtra("receobj");
		isgroup = theIntent.getBooleanExtra("isgroup",false);
		iscreate = theIntent.getBooleanExtra("iscreate",false);
		
	 
			sulist=vfun.getAllUseridList();
		 
		// UI声明和设置值
		TextView tvtitle = (TextView) findViewById(R.id.tvtitle);
		tvtitle.setText(sessionname);

		imgmic = (ImageView) findViewById(R.id.imgmic);

		ImageButton btn_talk = (ImageButton) this.findViewById(R.id.btn_talk);
		if (isgroup == false && receobj.size() == 0) {
			// 多人会话但是没有内容
			btn_talk.setVisibility(View.GONE);
 
		}

		btn_talk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// mfun.RecordStop();
			}

		});

		btn_talk.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub

				if (event.getAction() == MotionEvent.ACTION_UP) {
					// 放开
					imgmic.setVisibility(View.INVISIBLE);
					FileObj fo = mfun.RecordStop();
					if (fo != null) {
						
						long _id=System.currentTimeMillis();   //当次会话的id
						// fo.AddReceiver(receobj);
						String json = CmdStr.getFileInfo(
								String.valueOf(_id),
								uid, fo, sessionid, sessionname);
						// 保存会话json
						CacheDataFunction save = new CacheDataFunction(context);
						save.add(_id,sessionid, json, fo.getFilepath(),
								fo.getFilename(), uid);

						// 生成sessionItem,保存
						SessionItem si = new SessionItem();
						si.setId(_id);
						si.setFilepath(fo.getFilepath());
						si.setSessionid(sessionid);
						si.setFuid(uid);
						si.setSecond("" + fo.getSecond());
						String ftppath = "/" + sessionid + "/" + uid + "/"
								+ fo.getFilename();
						si.setFtppath(ftppath);
						si.setTm(fo.getTime());
						si.setErrmsg("发送中");
						si.setIssend(1);
						sifun.add(_id,uid, si);

						// 界面展示
						AddSessionItem(si);
						//30秒后更新
						refrushHandler.postDelayed(delayRun, 15000);

						// 发送广播
						GlobalFunction.sendBroadcastToService(context,
								sessionid);

						// 更新会话组信息
						sgfun.update(uid, sessionid, fo.getTime());
					}
				} else if (event.getAction() == MotionEvent.ACTION_DOWN) {
					// 按下
					if (canuse == false) {
						if (mfun.isRecording()) {
							mfun.RecordStop();
						}
						Toast.makeText(context, "网络不可用，请先检查网络！",
								Toast.LENGTH_SHORT).show();
						return false;
					}
					if (isgroup == false && receobj.size() == 0) {
						// 既不是组又没用户
						Toast.makeText(context, "无接收人员！", Toast.LENGTH_SHORT)
								.show();
						return false;
					}
					imgmic.setVisibility(View.VISIBLE);
					mfun.mpStop();
					mfun.RecordStart(isgroup, uid, sessionid, receobj);
				}
				return false;
			}
		});

		// 会话日志界面
		long stamp = System.currentTimeMillis();
		lv = (ListView) findViewById(R.id.lv);
		lst = sifun.getList(uid, sessionid, stamp, 100, true);
		arr = new DialogBaseAdapter(context, lst);
		lv.setAdapter(arr);
		
		checkNoSendRecord();
	}

	void AddSessionItem(SessionItem sobj) {
		lst.add(sobj);
		if (lst.size() > 100) {
			if(lst.get(0).getIssend()==2)
				lst.remove(0);
		}
		arr.notifyDataSetChanged();
	}

	@Override
	protected void onPause() {
		mfun.close();
		context.unregisterReceiver(dialogResultRecr);
		GlobalFunction.setNotiSetting(context, true);
		super.onPause();
	}

	@Override
	protected void onResume() {
		// 注册广播
		dialogResultRecr = new DialogResultRecr();
		IntentFilter recevierFilter = new IntentFilter();
		recevierFilter.addAction(CommKey.ui_receiver);
		recevierFilter.addAction(CommKey.netstart_receiver);
		recevierFilter.addAction(CommKey.netstop_receiver);
		recevierFilter.addAction(CommKey.ftp_msg);
		context.registerReceiver(dialogResultRecr, recevierFilter);
		GlobalFunction.setNotiSetting(context, false);
		super.onResume();
	}

	/**
	 * 判断有没有“发送中”的记录，如果有，启动30秒的检查机制
	 */
	void checkNoSendRecord(){
		boolean flag=false;
		for(SessionItem o:lst){
			if(o.getIssend()!=2){
				flag=true;
				break;
			}
		}
		if(flag){
			//30秒后更新
			refrushHandler.postDelayed(delayRun, 15000);
		}
	}

	
	/**
	 * 内部接收广播，主要用于接收返回结果
	 */
	class DialogResultRecr extends BroadcastReceiver {

		@Override
		public void onReceive(Context Ccontext, Intent intent) {

			String action = intent.getAction().toString();
			if (action.equals(CommKey.ui_receiver)) {
				// 目标管理返回结果
				String str = intent.getExtras().getString("type");
				if (str.equals(CommKey.type_newitem)) {
					String sid=intent.getExtras().getString("sessionid");
					if(sessionid.equals(sid)){
						SessionItem itemObj = (SessionItem) intent.getExtras()
						.getSerializable("object");
						AddSessionItem(itemObj);
					}
				} else if (str.equals(CommKey.type_new)) {
					String sid = intent.getExtras().getString("sessionid");
					if (sessionid.equals(sid) == false) {
						// 非当前会话则添加
						app.addNew(sid);
					}
				} else if (str.equals(CommKey.type_sessioninfo)) {
					if (iscreate == false) {
						//不是创建人无视这广播
						String sid = intent.getExtras().getString("sessionid");
						if (sid.equals(sessionid)) {
							//只更新本sessionid的receobj
							CacheGroupFunction cgfun = new CacheGroupFunction(
									context);
							List<String> tmp = cgfun.getUseridColl(uid,
									sessionid);
							receobj.clear();
							receobj.addAll(tmp);
						}
					}
				}else if(str.equals(CommKey.type_returnmsg)){
					//返回的信息
	 
					long id=intent.getExtras().getLong("id");
				 
					//更新id对应的值
					Message msg=refrushHandler.obtainMessage();
					Bundle b=new Bundle();
					b.putLong("id", id);
					msg.setData(b);
					refrushHandler.sendMessage(msg);
				} else  {

				}
			} else if (CommKey.netstart_receiver.equals(action)) {
				canuse = true;
			} else if (CommKey.netstop_receiver.equals(action)) {
				canuse = false;
			} else if (CommKey.ftp_msg.equals(action)) {
				//ftp错误信息
				String msg=intent.getExtras().getString("msg");
				long id=intent.getExtras().getLong("stamp");
				
				Toast.makeText(Ccontext, "数据上传失败:"+msg, Toast.LENGTH_SHORT).show();
				boolean flag=false;
				for(int i=0;i<lst.size();i++){
					if(lst.get(i).getId()==id&&lst.get(i).getFuid().equals(uid)){
						lst.get(i).setIssend(0);
						lst.get(i).setErrmsg("发送失败");
						sifun.update(uid, lst.get(i));
						flag=true;
						break;
					}
				}
				if(flag){
					arr.notifyDataSetChanged();
				}
			}
		}
	}

	String getName(String uid) {
		String s = "暂不知晓";
		if(sulist.containsKey(uid)){
			s=sulist.get(uid);
		}
		return s;
	}

	public class DialogBaseAdapter extends BaseAdapter {
		private LayoutInflater mInflater;
		private List<SessionItem> listData;

		private class ViewHolder {
			public TextView tvsname;
			public TextView tvsecond;
			public TextView tvtm;
			public LinearLayout imgcall;
			public Button btnresend;
		}

		public DialogBaseAdapter(Context context, List<SessionItem> _listData) {
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
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			ViewHolder holder = null;
			holder = new ViewHolder();
			
			if (listData.get(position).getFuid().equals(uid)) {
				convertView = mInflater.inflate(R.layout.dialogitem_send, null);
				holder.tvsname = (TextView) convertView
						.findViewById(R.id.tvname);
				holder.tvsname.setText("我");
			} else {
				convertView = mInflater.inflate(R.layout.dialogitem_get, null);
				holder.tvsname = (TextView) convertView
						.findViewById(R.id.tvname);
				String fromname = getName(listData.get(position).getFuid());
				holder.tvsname.setText(fromname);
			}
			holder.btnresend=(Button)convertView
					.findViewById(R.id.btnresend);
			holder.tvsecond = (TextView) convertView
					.findViewById(R.id.tvsecond);
			holder.tvtm = (TextView) convertView.findViewById(R.id.tvtm);

			holder.tvsecond.setText(listData.get(position).getSecond() + "″");
			

			holder.imgcall = (LinearLayout) convertView
					.findViewById(R.id.imgcall);
			holder.imgcall.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// 播放

					mfun.mpStart(listData.get(position).getFtppath(), listData
							.get(position).getFilepath());
				}

			});
			
			
			
			//隐藏和显示 重新发送的按钮
			if(listData.get(position).getIssend()==1){
				//发送中
				holder.btnresend.setVisibility(View.GONE);
				holder.tvtm.setText(listData.get(position).getErrmsg());
			}
				else if(listData.get(position).getIssend()==2){
					//发送成功了
					holder.btnresend.setVisibility(View.GONE);
					holder.tvtm.setText(listData.get(position).getTm());
			}else{
				//发送失败
				holder.btnresend.setVisibility(View.VISIBLE);
				holder.tvtm.setText(listData.get(position).getErrmsg());
			}
			
			holder.btnresend.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//重新发送所有
					// 发送广播,提交数据时第二个参数无意义
					for(int i=0;i<listData.size();i++){
						SessionItem si=listData.get(i);
						if (si.getFuid().equals(uid)&&si.getIssend()!=2) {
							si.setErrmsg("发送中");
							si.setIssend(1);
						}
					}
					
					DialogBaseAdapter.this.notifyDataSetChanged();
					GlobalFunction.sendBroadcastToService(context,
							"resend");
					refrushHandler.postDelayed(delayRun, 15000);
				}
			});
			convertView.setTag(holder);
			return convertView;
		}
	}

	
	// 菜单
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {

			super.onCreateOptionsMenu(menu);
			menu.add(0, Menu.FIRST, 0, "会话成员").setIcon(
					android.R.drawable.ic_menu_info_details);
			 
			return true;
		}

		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			int pos = item.getItemId() - Menu.FIRST;
			switch (pos) {

			case 0:
				 //弹出会话成员
				if(!isgroup){
				ListView lv=new ListView(context);
				if(receobj.size()==0){
					CacheGroupFunction cgfun = new CacheGroupFunction(
							context);
					List<String> tmp = cgfun.getUseridColl(uid,
							sessionid);
					receobj.clear();
					receobj.addAll(tmp);
				}
				List<String> ls=new ArrayList<String>();
				for(String ss:receobj){
					String m=getName(ss);
					ls.add(m);
				}
				ls.add("我["+app.getUname()+"]");
				ArrayAdapter<String> arr=new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,ls);
				lv.setAdapter(arr);
				GlobalFunction.ShowDialog(context, lv);
				}else{
					GlobalFunction.ShowDialog(context, "当前会话组为:"+sessionname);
				}
			default:
				break;
			}

			return true;
		}
}