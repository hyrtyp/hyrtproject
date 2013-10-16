package com.hyrt.foshanLaw.G;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hyrt.cei.util.MyTools;
import com.hyrt.cei.util.StringUtil;
import com.hyrt.cei.webservice.service.Service;
import com.hyrt.foshanLaw.R;
import com.hyrt.mwpm.vo.MwpmBussCase;
import com.hyrt.mwpm.vo.MwpmBussPatrolLog;
import com.hyrt.mwpm.vo.MwpmSysDictionary;
import com.hyrt.mwpm.vo.MwpmSysUserinfo;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class CaseActivity extends Activity{
	String userid, lid,eid,reseauid,property,ruserid;
	private EditText ajmc, ajly, ajjbqk, ajclyj;
	private Spinner xbrid, ajxz;
	private Button kscx;
	private int pageIndex=1;
	private List<MwpmSysUserinfo> userList;
	private List<MwpmSysDictionary> dictionaryList;
	
	private Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if(msg.what==1){
				xbrid.setAdapter(new MyBaseAdapter(getApplicationContext(), userList));
				ajxz.setAdapter(new MyBaseAdapter(getApplicationContext(), dictionaryList));
			}else if(msg.what==2){
				Toast.makeText(getApplicationContext(), "案件插入失败!", Toast.LENGTH_SHORT).show();
			}else if(msg.what==3){
				Toast.makeText(getApplicationContext(), "案件插入成功!", Toast.LENGTH_SHORT).show();
				CaseActivity.this.finish();
			}
		}
		
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.g_ajlr);
		SharedPreferences settings = getSharedPreferences("userinfo",
				Activity.MODE_PRIVATE);
		userid = settings.getString("userid", "");
		reseauid=settings.getString("reseauid", "");
		SharedPreferences patrolLogId = getSharedPreferences("patrolLog",
				Activity.MODE_PRIVATE);
		lid = patrolLogId.getString("lid", "");//巡查记录ID
		eid=patrolLogId.getString("eid", "");//企业ID
		initData();
		initView();
	}
    private void initData(){
    	new Thread(){

			@Override
			public void run() {
				//根据网格ID获取网格内的用户
				userList=new ArrayList<MwpmSysUserinfo>();
				dictionaryList=new ArrayList<MwpmSysDictionary>();
				String rtu=Service.getUserListByrId(reseauid,pageIndex+"");
				String rtuzd=Service.queryDictionaryList("ajxz",pageIndex+"");
				try {
					JSONArray array=new JSONArray(rtu);
					JSONArray arrayzd=new JSONArray(rtuzd);
					for (int i = 0; i < array.length(); i++) {
						MwpmSysUserinfo userinfo=new MwpmSysUserinfo();
						JSONObject temp = (JSONObject) array.get(i);
						userinfo.setUserid(temp.getString("userid"));
						userinfo.setName(temp.getString("name"));
						userList.add(userinfo);
					}
					for (int i = 0; i < arrayzd.length(); i++) {
						MwpmSysDictionary dictionary=new MwpmSysDictionary();
						JSONObject temp = (JSONObject) arrayzd.get(i);
						dictionary.setId(temp.getString("id"));
						dictionary.setDataname(temp.getString("dataname"));
						dictionaryList.add(dictionary);
					}
					handler.sendEmptyMessage(1);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//查询数据字典获取案件性质
				
			}
    		
    	}.start();
    }
	private void initView() {
		ajmc = (EditText) findViewById(R.id.g_ajmc);
		ajxz = (Spinner) findViewById(R.id.g_ajxz);
		ajly = (EditText) findViewById(R.id.g_ajly);
		ajjbqk = (EditText) findViewById(R.id.g_ajjbqk);
		ajclyj = (EditText) findViewById(R.id.g_ajclyj);
		xbrid = (Spinner) findViewById(R.id.g_xbrid);
		kscx = (Button) findViewById(R.id.g_kscx);
		kscx.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new Thread(runnable).start();
			}
		});
	}
	 Runnable runnable=new Runnable() {
			@Override
			public void run() {
				MwpmBussCase c=new MwpmBussCase();
				c.setEid(lid);
				c.setName(ajmc.getText().toString());
				c.setSubmituserid(userid);
				c.setSubmitunitid(reseauid);
				c.setCreatetime(StringUtil.getSysTime());
				c.setIdea(ajclyj.getText().toString());
				c.setStatus("ddsh");
				c.setMain(eid);
				try {
					c.setProperty(((MwpmSysDictionary) (ajxz.getSelectedItem()))
							.getId() == null ? "" : ((MwpmSysDictionary) (ajxz
							.getSelectedItem())).getId());//
					c.setJointuserid(((MwpmSysUserinfo) (xbrid
							.getSelectedItem())).getUserid() == null ? ""
							: ((MwpmSysUserinfo) (xbrid.getSelectedItem()))
									.getUserid());//
				} catch (Exception e) {
				}
				c.setCasesource(ajly.getText().toString());
				c.setBaseinfo(ajjbqk.getText().toString());
				String rstr=Service.saveCase(c);
				if(!MyTools.RETCOAD.equals(rstr)){
					handler.sendEmptyMessage(2);
				}else{
					handler.sendEmptyMessage(3);
				}
			}
		};

}
