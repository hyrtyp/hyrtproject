package com.hyrt.foshanLaw.G;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hyrt.cei.util.MyTools;
import com.hyrt.cei.util.StringUtil;
import com.hyrt.cei.webservice.service.Service;
import com.hyrt.foshanLaw.R;
import com.hyrt.foshanLaw.b.MyMapActivity;
import com.hyrt.mwpm.vo.MwpmBussPType;
import com.hyrt.mwpm.vo.MwpmBussPatrolLog;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SavePatrolLogActivity extends Activity {
	private String reseauid, userid, eid,type;
	private EditText xcnr, zdjkdq, bz,xcry;
	private TextView typename;
	private DatePicker zgqx;
	private Button next,xuanze;
	private String typesid,typesname,pid;
	
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 1) {
				/*Toast.makeText(getApplicationContext(), "录入成功！",
						Toast.LENGTH_SHORT).show();*/
			} else if(msg.what == 3){
				Toast.makeText(getApplicationContext(), "获取坐标失败！",
						Toast.LENGTH_SHORT).show();
			}else {
				Toast.makeText(getApplicationContext(), "录入失败！",
						Toast.LENGTH_SHORT).show();
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.g_xcjl1);
		SharedPreferences settings = getSharedPreferences("userinfo",
				Activity.MODE_PRIVATE);
		userid = settings.getString("userid", "");
		reseauid = settings.getString("reseauid", "");
		pid=getIntent().getStringExtra("pid");
		eid = getIntent().getStringExtra("eid");
		type=getIntent().getStringExtra("type");
		initView();
	}

	private void initView() {
		xcnr = (EditText) findViewById(R.id.g_xcnr);
		zdjkdq = (EditText) findViewById(R.id.g_zdjkdq);
		xcry=(EditText) findViewById(R.id.g_xcry);
		zgqx = (DatePicker) findViewById(R.id.g_zgqx);
		bz = (EditText) findViewById(R.id.g_bz);
		next = (Button) findViewById(R.id.next);
		next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				new Thread(runnable).start();
			}
		});
		xuanze=(Button) findViewById(R.id.xuanze);
		xuanze.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent(SavePatrolLogActivity.this, PopActivity.class);
				startActivityForResult(intent, 100);
			}
		});
		typename=(TextView)findViewById(R.id.g_xclx);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode==RESULT_OK){
			typesid=data.getStringExtra("ids");
			typesname=data.getStringExtra("names");
			typename.setText(typesname);
		}
	}

	Runnable runnable = new Runnable() {

		@Override
		public void run() {
			MwpmBussPatrolLog patrolLog = new MwpmBussPatrolLog();
			patrolLog.setEntid(eid);
			patrolLog.setContent(xcnr.getText().toString());
			patrolLog.setHandman(xcry.getText().toString());
			patrolLog.setUserid(userid);
			patrolLog.setClock(StringUtil.getSysTime());
			patrolLog.setType(type!=null&&!"".equals(type)?type:"日常巡查");
			patrolLog.setArea(zdjkdq.getText().toString());
			patrolLog.setRemark(bz.getText().toString());
			patrolLog.setTerm(StringUtil.getDateType(zgqx.getYear() + "-"
					+ (zgqx.getMonth() +1)+ "-" + zgqx.getDayOfMonth()));
			patrolLog.setIsrecheck("n");
			patrolLog.setRid(reseauid);
			//插入记录表
			String pk = Service.savePatrolLOG(patrolLog,pid);
			//插入巡查记录对应类型表
			String typeRet="";
			if(pk!=null&&!pk.equals("")&&typesid!=null&&!typesid.equals("")){
				typeRet = Service.savePType(pk,typesid);
			}
			//上传企业坐标、返回 9  成功
			/*String jsonResult = Service.updateEntInfo(eid,MyMapActivity.getLatLon()[1], MyMapActivity.getLatLon()[0]);
            if(!"9".equals(jsonResult)){
            	handler.sendEmptyMessage(3);
            }*/
			// 巡查记录保存成功，跳转保存巡查事项表
			if (pk != null && !pk.equals("")) {//&&MyTools.RETCOAD.equals(typeRet)
				handler.sendEmptyMessage(1);
				SharedPreferences patrolLogId = getSharedPreferences(
						"patrolLog", Activity.MODE_PRIVATE);
				patrolLogId.edit().putString("lid", pk).commit();
				patrolLogId.edit().putString("eid", eid).commit();

				Intent intent = new Intent(SavePatrolLogActivity.this,
						SavePatrolItemActivity.class);
				intent.putExtra("pk", pk);
				startActivity(intent);
				SavePatrolLogActivity.this.finish();
			} else {
				handler.sendEmptyMessage(2);
			}
		}

		// Service.savePatrolLOG(patrolLog);

	};
}
