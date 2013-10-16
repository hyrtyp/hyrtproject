package com.hyrt.foshanLaw.G;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.hyrt.cei.vo.Patrol;
import com.hyrt.cei.webservice.service.Service;
import com.hyrt.foshanLaw.R;
import com.hyrt.foshanLaw.b.BqycxActivity;
import com.hyrt.foshanLaw.b.EntInfoListActivity;
import com.hyrt.foshanLaw.e.InforRecorActivity;
import com.hyrt.mwpm.vo.MwpmBussEntinfo;

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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class EntinfoMainActivity extends Activity implements
		OnItemClickListener, OnClickListener {
	public static final int DF_FLAGE = 0;
	public static final int RC_FLAGE = 1;
	public static final int ZX_FLAGE = 2;
	public static final int JC_FLAGE = 3;
	public static final int YX_FLAGE = 4;
	public static String NAME;//name:用户权限；pid：任务ID；pstatus:任务状态
	private int flage;
	private ListView list;
	private String userid,xcbs,level,reseauid;
	private List<MwpmBussEntinfo> EntinfoData;
	private MwpmBussEntinfo bussEntinfo;
	private EntinfoAdapter adapter;
	private int pageSize = 1;
	private TextView text1, text2, textTop, more;
	private Button leftBut;
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			if(msg.what==2){
				if(msg.arg1<8){
					Toast.makeText(getApplicationContext(), "没有更多数据！", Toast.LENGTH_SHORT).show();
					more.setVisibility(View.GONE);
				}
				adapter.notifyDataSetChanged();
			}else if(msg.what==3){
				Toast.makeText(getApplicationContext(), "没有更多数据！", Toast.LENGTH_SHORT).show();
				more.setVisibility(View.GONE);
			}else{
			adapter = new EntinfoAdapter(EntinfoMainActivity.this, EntinfoData);
			list.setAdapter(adapter);
			list.setOnItemClickListener(EntinfoMainActivity.this);
			more.setVisibility(View.VISIBLE);
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_hastitle);
		SharedPreferences settings = getSharedPreferences("userinfo",
				Activity.MODE_PRIVATE);
		userid = settings.getString("userid", "");
		level = settings.getString("level", "");
		reseauid=settings.getString("reseauid", "");
		NAME=settings.getString("roletype", "");
		flage = getIntent().getIntExtra("flage", DF_FLAGE);
		bussEntinfo=(MwpmBussEntinfo) getIntent().getSerializableExtra("MwpmBussEntinfo");
		initView();
		leftBut=(Button) findViewById(R.id.top_button_left);
		leftBut.setVisibility(View.VISIBLE);
		leftBut.setOnClickListener(this);
		leftBut.setText("查询");
		switch (flage) {
		case RC_FLAGE:
			initData(RC_FLAGE);
			xcbs="rccx";
			break;
		case ZX_FLAGE:
			textTop.setText("专项企业列表");
			initData(ZX_FLAGE);
			
			xcbs="zxcx";
			break;
		case JC_FLAGE:
			textTop.setText("企业纠错列表");
			xcbs="qyjc";
			initData(RC_FLAGE);
			break;
		case YX_FLAGE:
			textTop.setText("手机号补录");
			xcbs="yxbl";
			initData(RC_FLAGE);
			break;
		default:
			break;
		}
	}

	private void initData(final int flage) {
		EntinfoData = new ArrayList<MwpmBussEntinfo>();
		if(bussEntinfo==null){
			bussEntinfo=new MwpmBussEntinfo();
		}
		
		if(level.equals("jz")){
			bussEntinfo.setUserid(null);
			bussEntinfo.setReseauid(null);
		}else if(level.equals("wgzrr")){
			bussEntinfo.setUserid(null);
			bussEntinfo.setReseauid(reseauid);
		}else{
			bussEntinfo.setUserid(userid);
		}

		new Thread() {
			@Override
			public void run() {
				String retu = "";
				if (flage == RC_FLAGE) {
					//retu = Service.queryPatrolENTINFO(reseauid, pageSize + "");
					retu=Service.queryEntInfos(bussEntinfo,pageSize);
					try {
						JSONArray arr = new JSONArray(retu);
						for (int i = 0; i < arr.length(); i++) {
							MwpmBussEntinfo entinfo = new MwpmBussEntinfo();
							JSONObject temp = (JSONObject) arr.get(i);
							String id = temp.getString("ID");
							entinfo.setId(id);
							String enrol = temp.getString("ENROL");// 显示项
							entinfo.setEnrol(enrol);
							String name = temp.getString("NAME");// 显示项
							entinfo.setName(name);
							String member = temp.getString("MEMBER");// 显示项
							entinfo.setMember(member);
							String address = temp.getString("ADDRESS");// 显示项
							entinfo.setAddress(address);
							EntinfoData.add(entinfo);
						}
						handler.sendEmptyMessage(1);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else if (flage == ZX_FLAGE) {
					retu = Service.queryZxPatrolTask(bussEntinfo,pageSize);
					try {
						JSONArray arr = new JSONArray(retu);
						for (int i = 0; i < arr.length(); i++) {
							MwpmBussEntinfo entinfo = new MwpmBussEntinfo();
							JSONObject temp = (JSONObject) arr.get(i);
							String id = temp.getString("ID");
							entinfo.setId(id);
							String enrol = temp.getString("ENROL");// 显示项
							entinfo.setEnrol(enrol);
							String name = temp.getString("NAME");// 显示项
							entinfo.setName(name);
							String pid=temp.getString("PID");//任务ID
							String status=temp.getString("STATUS");
							entinfo.setPid(pid);
							entinfo.setPstatus(status);
							EntinfoData.add(entinfo);
						}
						handler.sendEmptyMessage(1);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

		}.start();
	}

	private void initView() {
		text1 = (TextView) findViewById(R.id.text1);
		text1.setText("企业注册号");
		text2 = (TextView) findViewById(R.id.text2);
		text2.setText("企业名称");
		textTop = (TextView) findViewById(R.id.top_text);
		textTop.setText("认领企业列表");
		findViewById(R.id.top_button_left).setVisibility(View.GONE);
		findViewById(R.id.top_button_right).setVisibility(View.GONE);
		list = (ListView) findViewById(R.id.list);
		View view = LayoutInflater.from(EntinfoMainActivity.this).inflate(
				R.layout.common_listview_bottom, null);
		more = (TextView) view.findViewById(R.id.morebtn);
		list.addFooterView(view);
		more.setOnClickListener(EntinfoMainActivity.this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if(!NAME.contains("检查记录管理员")){
			Toast.makeText(this, "此用户没有权限检查！", Toast.LENGTH_SHORT).show();
			return;
		}
		MwpmBussEntinfo Entinfo = (MwpmBussEntinfo) arg0.getAdapter().getItem(
				arg2);
		Intent intent = null;
		if (flage == JC_FLAGE) {
			intent = new Intent(this, InforRecorActivity.class);
		} else if(flage == YX_FLAGE){
			intent = new Intent(this, YxblActivity.class);
		}else if(flage == RC_FLAGE){
			intent = new Intent(this, PatrolEntinfohisActivity.class);
			intent.putExtra("type", "日常检查");
			TablesActivity.PID=null;
		}else if(flage == ZX_FLAGE){
			intent = new Intent(this, PatrolEntinfohisActivity.class);
			intent.putExtra("type", "专项检查");
			intent.putExtra("pid", Entinfo.getPid());
			intent.putExtra("status", Entinfo.getPstatus());
		}
		intent.putExtra("eid", Entinfo.getId() != null ? Entinfo.getId() : "");
		intent.putExtra("ename", Entinfo.getName() != null ? Entinfo.getName()
				: "");
		intent.putExtra("zcnumber",
				Entinfo.getEnrol() != null ? Entinfo.getEnrol() : "");
		intent.putExtra("address", Entinfo.getAddress() != null ? Entinfo.getAddress() : "");
		intent.putExtra("member", Entinfo.getMember() != null ? Entinfo.getMember() : "");
		startActivity(intent);
	}

	/*
	 * String wordname = temp.getString("wordname");
	 * entinfo.setWordname(wordname); String member = temp.getString("member1");
	 * entinfo.setMember(member); String address = temp.getString("address");
	 * entinfo.setAddress(address); String post = temp.getString("post");
	 * entinfo.setPost(post); String enttype = temp.getString("enttype");
	 * entinfo.setEnttype(enttype); String entpro = temp.getString("entpro");
	 * entinfo.setEntpro(entpro); String domunit = temp.getString("domunit");
	 * entinfo.setDomunit(domunit); String djjg = temp.getString("djjg");
	 * entinfo.setDjjg(djjg); String tel = temp.getString("tel");
	 * entinfo.setTel(tel); String entstatus = temp.getString("entstatus");
	 * entinfo.setEntstatus(entstatus); String appdate =
	 * temp.getString("appdate"); entinfo.setAppdate(appdate); String earcap =
	 * temp.getString("earcap"); entinfo.setEarcap(earcap); String workarea =
	 * temp.getString("workarea"); entinfo.setWorkarea(workarea); String
	 * createdate = temp.getString("createdate");
	 * entinfo.setCreatedate(createdate); String workstopdate =
	 * temp.getString("workstopdate"); entinfo.setWorkstopdate(workstopdate);
	 * String hynum = temp.getString("hynum"); entinfo.setHynum(hynum); String
	 * hytype = temp.getString("hytype"); entinfo.setHytype(hytype); String
	 * hycode = temp.getString("hycode"); entinfo.setHycode(hycode); String dah
	 * = temp.getString("dah"); entinfo.setDah(dah); String sddjjg =
	 * temp.getString("sddjjg"); entinfo.setSddjjg(sddjjg); String tz =
	 * temp.getString("tz"); entinfo.setTz(tz); // String createtime =
	 * temp.getString("createtime"); // entinfo.setCreatetime(createtime); //
	 * String lastdate = temp.getString("lastdate"); String optionid =
	 * temp.getString("optionid"); entinfo.setOptionid(optionid); String
	 * reseauid = temp.getString("reseauid"); entinfo.setReseauid(reseauid);
	 * String claimstatus = temp.getString("claimstatus");
	 * entinfo.setClaimstatus(claimstatus); String userid =
	 * temp.getString("userid"); entinfo.setUserid(userid); String lat =
	 * temp.getString("lat"); entinfo.setLat(lat); String long1 =
	 * temp.getString("long1"); entinfo.setLong1(long1);
	 */

	@Override
	public void onClick(View v) {
		if (v == more) {
			 pageSize++;
			switch (flage) {
			case RC_FLAGE:
				addMore(RC_FLAGE);
				break;
			case ZX_FLAGE:
				addMore(ZX_FLAGE);
				break;
			case JC_FLAGE:
				addMore(JC_FLAGE);
				break;
			case YX_FLAGE:
				addMore(RC_FLAGE);
				break;
			default:
				break;
			}
		}else if(v == leftBut){
			Intent intent=new Intent(this, BqycxActivity.class);
			intent.putExtra("xcbs", xcbs);
			startActivity(intent);
			this.finish();
		}
	}
	private void addMore(final int flage){
		final List<MwpmBussEntinfo> data=new ArrayList<MwpmBussEntinfo>();
		new Thread(){
			@Override
			public void run() {
				String retu="";
			if(flage==ZX_FLAGE){
				 retu = Service.queryZxPatrolTask(bussEntinfo,pageSize);
			}else {
				 retu=Service.queryEntInfos(bussEntinfo,pageSize);
			}
			
			if(retu.equals("{'errorcode':'4'}")) {
				handler.sendEmptyMessage(3);
				return;
			}
			try {
				JSONArray arr = new JSONArray(retu);
				for (int i = 0; i < arr.length(); i++) {
					MwpmBussEntinfo entinfo = new MwpmBussEntinfo();
					JSONObject temp = (JSONObject) arr.get(i);
					String id="";
					String enrol="";
					String name="";
					if(flage==ZX_FLAGE){
						id = temp.getString("ID");
						enrol = temp.getString("ENROL");// 显示项
						name = temp.getString("NAME");// 显示项
					}else{
						id = temp.getString("ID");
						enrol = temp.getString("ENROL");// 显示项
						name = temp.getString("NAME");// 显示项
					}
					entinfo.setId(id);
					entinfo.setEnrol(enrol);
					entinfo.setName(name);
					data.add(entinfo);
				}
				EntinfoData.addAll(data);
				Message msg=new Message();
				msg.what=2;
				msg.arg1=data.size();
				handler.sendMessage(msg);
			} catch (Exception e) {
				e.printStackTrace();
			}
			}
			
		}.start();
	}
}
