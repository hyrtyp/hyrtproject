package com.hyrt.foshanLaw.e;

import java.util.Date;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hyrt.cei.util.MyTools;
import com.hyrt.cei.util.StringUtil;
import com.hyrt.cei.webservice.service.Service;
import com.hyrt.foshanLaw.R;
import com.hyrt.mwpm.vo.MwpmBussEntupdate;

public class InforRecorActivity extends Activity implements OnClickListener {
	EditText  b_jcnr;
	TextView b_sczt, b_zch;
	Button btn_tj;
	String str_submitid,eid,ename,zcnumber;
    Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if(msg.what==200){
				Toast.makeText(getApplicationContext(), "插入成功！", Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(getApplicationContext(), "插入失败！", Toast.LENGTH_SHORT).show();
			}
		}
    	
    };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.b_scjtxxjclr);
		SharedPreferences settings = getSharedPreferences("userinfo",
				Activity.MODE_PRIVATE);
		str_submitid = settings.getString("userid", "admin");
		eid=getIntent().getStringExtra("eid");
		ename=getIntent().getStringExtra("ename");
		zcnumber=getIntent().getStringExtra("zcnumber");
		b_sczt = (TextView) findViewById(R.id.b_sczt);
		b_sczt.setText(ename);
		b_zch = (TextView) findViewById(R.id.b_zch);
		b_zch.setText(zcnumber);
		b_jcnr = (EditText) findViewById(R.id.b_jcnr);
		btn_tj = (Button) findViewById(R.id.a_kscx);
		btn_tj.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.a_kscx:
			sendData();
			break;

		default:
			break;
		}

	}

	private void sendData() {
		new Thread() {
			@Override
			public void run() {
				MwpmBussEntupdate entupdate=new MwpmBussEntupdate();
				entupdate.setEnrol(b_zch.getText().toString());
				entupdate.setName(b_sczt.getText().toString());
				entupdate.setSubmitid(str_submitid);
				entupdate.setUpdatecontent(b_jcnr.getText().toString());
				entupdate.setCreatetime(StringUtil.getSysTime());
				String rs = Service.saveEntErrorinfo(entupdate);
				if(rs.equals(MyTools.RETCOAD)){
					handler.sendEmptyMessage(200);
				}else{
					handler.sendEmptyMessage(404);
				}
			}

		}.start();

	}
}
