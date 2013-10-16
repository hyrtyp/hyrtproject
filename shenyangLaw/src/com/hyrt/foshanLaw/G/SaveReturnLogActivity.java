package com.hyrt.foshanLaw.G;

import com.hyrt.cei.util.MyTools;
import com.hyrt.cei.util.StringUtil;
import com.hyrt.cei.webservice.service.Service;
import com.hyrt.foshanLaw.R;
import com.hyrt.mwpm.vo.MwpmBussPatrolLog;
import com.hyrt.mwpm.vo.MwpmBussReturnlog;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SaveReturnLogActivity extends Activity {
	private String lid;
	private EditText checkthing,disposetype;//回查情况，回查处理方式
	private Button next;
	private TextView xcsxid;
	private Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if(msg.what==1){
				Toast.makeText(getApplicationContext(), "录入成功！", Toast.LENGTH_SHORT).show();
				SaveReturnLogActivity.this.finish();
			}else{
				Toast.makeText(getApplicationContext(), "录入失败！", Toast.LENGTH_SHORT).show();
			}
		}};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.g_hcjllr);
		lid=getIntent().getStringExtra("lid");
		initView();
	}
    private void initView(){
    	xcsxid=(TextView) findViewById(R.id.g_xcsxid);
    	xcsxid.setText(lid);
    	checkthing=(EditText) findViewById(R.id.g_hcqk);
    	disposetype=(EditText) findViewById(R.id.g_hcclfs);
    	next=(Button) findViewById(R.id.a_kscx);
    	next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				new Thread(runnable).start();
			}
		});
    }
    Runnable runnable=new Runnable() {
		
		@Override
		public void run() {
			MwpmBussReturnlog returnLog=new MwpmBussReturnlog();
			returnLog.setLid(lid);
			returnLog.setCheckthing(checkthing.getText().toString());
			returnLog.setDisposetype(disposetype.getText().toString());
			returnLog.setChecktime(StringUtil.getSysTime());
            String ret=Service.saveReturnLog(returnLog);
            if(MyTools.RETCOAD.equals(ret)){
				handler.sendEmptyMessage(1);
				SaveReturnLogActivity.this.finish();
			}else{
				handler.sendEmptyMessage(2);
			}
		}
	};
}
