package com.hyrt.foshanLaw.b;

import com.hyrt.foshanLaw.R;
import com.hyrt.foshanLaw.G.EntinfoMainActivity;
import com.hyrt.mwpm.vo.MwpmBussEntinfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewDebug.IntToString;
import android.widget.Button;
import android.widget.EditText;

/**
 * 企业查询
 * 
 * @author user1
 * 
 */
public class BqycxActivity extends Activity {
	//跳转巡查企业标石，
	private String xcbs;
	// 注册号
	private String zch;
	// 名称
	private String mc;
	// 法定代表人
	private String fddbr;
	// 地址
	private String dz;
	// 注册资本
	private String zczb;
	// 经营范围
	private String jyfw;
	// 提交按钮
	private Button submit;
	// 清空按钮
	private Button clear;
	// 注册号输入框
	private EditText edit_zch;
	// 名称输入框
	private EditText edit_mc;
	// 法定代表人输入框
	private EditText edit_fddbr;
	// 地址输入框
	private EditText edit_dz;
	// 注册资本
	private EditText editZczb;
	// 经营范围
	private EditText editJyfw;

	public static final String HIDE_RIGHT_FLAG = "HIDE_RIGHT_FLAG";
	private String zch1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_qybzcx);
		xcbs=getIntent().getStringExtra("xcbs");
		findViewById(R.id.top_button_right).setVisibility(View.INVISIBLE);
		findViewById(R.id.top_button_left).setVisibility(View.INVISIBLE);
		findViewById(R.id.top_button_right).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(BqycxActivity.this,
								WzwzcxActivity.class);
						startActivity(intent);
					}
				});
		findViewById(R.id.top_button_left).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(BqycxActivity.this,
								EntInfoListActivity.class);
						intent.putExtra("listFlag",
								EntInfoListActivity.ERRORENT_FLAG);
						startActivity(intent);
					}
				});
		edit_zch = ((EditText) findViewById(R.id.a_zch));
		zch1 = getIntent().getStringExtra("zch");
		if(zch1 != null && zch1.length() >3){
			zch1 = zch1.substring(3);
			edit_zch.setText(zch1.trim());
		}
		edit_mc = (EditText) findViewById(R.id.a_mc);
		edit_fddbr = (EditText) findViewById(R.id.a_fddbr);
		edit_dz = (EditText) findViewById(R.id.a_dz);
		submit = (Button) findViewById(R.id.a_kscx);
		clear = (Button) findViewById(R.id.a_qksr);
		editZczb = (EditText) findViewById(R.id.zczb);
		editJyfw = (EditText) findViewById(R.id.jyfw);
		clear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				edit_zch.setText("");
				edit_mc.setText("");
				edit_fddbr.setText("");
				edit_dz.setText("");
				editZczb.setText("");
				editJyfw.setText("");
			}
		});
		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				zch = edit_zch.getText().toString();
				mc = edit_mc.getText().toString();
				fddbr = edit_fddbr.getText().toString();
				dz = edit_dz.getText().toString();
				zczb = editZczb.getText().toString();
				jyfw = editJyfw.getText().toString();
				MwpmBussEntinfo mwpmBussEntinfo = new MwpmBussEntinfo();
				mwpmBussEntinfo.setEnrol(zch);
				mwpmBussEntinfo.setName(mc);
				mwpmBussEntinfo.setMember(fddbr);
				mwpmBussEntinfo.setAddress(dz);
				mwpmBussEntinfo.setEarcap(zczb);
				mwpmBussEntinfo.setWorkarea(jyfw);
				String reseauid = getSharedPreferences(
							"userinfo", Activity.MODE_PRIVATE).getString(
							"userid", "");
				mwpmBussEntinfo.setUserid(reseauid);
				Intent intent=null;
				if("qyjc".equals(xcbs)){
					intent = new Intent(BqycxActivity.this,
							EntinfoMainActivity.class);
					intent.putExtra("flage", EntinfoMainActivity.JC_FLAGE);
				}else if("yxbl".equals(xcbs)){
					intent = new Intent(BqycxActivity.this,
							EntinfoMainActivity.class);
					intent.putExtra("flage", EntinfoMainActivity.YX_FLAGE);
				}else if("rccx".equals(xcbs)){
					intent = new Intent(BqycxActivity.this,
							EntinfoMainActivity.class);
					intent.putExtra("flage", EntinfoMainActivity.RC_FLAGE);
				}else if("zxcx".equals(xcbs)){
					intent = new Intent(BqycxActivity.this,
							EntinfoMainActivity.class);
					intent.putExtra("flage", EntinfoMainActivity.ZX_FLAGE);
				}else{
					intent = new Intent(BqycxActivity.this,
							EntInfoListActivity.class);
					intent.putExtra(
							"listFlag",
							getIntent().getIntExtra("listFlag",
									EntInfoListActivity.ENTINFOLIST_FLAG));
				}
				
				if(zch1 != null){
					intent.putExtra("listFlag",EntInfoListActivity.ENTERPRISERECORDLIST_FLAG);
				}else{intent.putExtra(
						"listFlag",
						getIntent().getIntExtra("listFlag",
								EntInfoListActivity.ENTINFOLIST_FLAG));
				}
				intent.putExtra("MwpmBussEntinfo", mwpmBussEntinfo);
				startActivity(intent);
			}
		});
	}
}
