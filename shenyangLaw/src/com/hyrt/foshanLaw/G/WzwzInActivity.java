package com.hyrt.foshanLaw.G;

import com.hyrt.cei.util.MyTools;
import com.hyrt.cei.util.StringUtil;
import com.hyrt.cei.webservice.service.Service;
import com.hyrt.foshanLaw.R;
import com.hyrt.mwpm.vo.MwpmBussNocard;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class WzwzInActivity extends Activity {
	private EditText jlbt, jyz, sfzh, dh, xxdz;
	private Spinner wzwzlx, jyxm,cljg;
	private Button in;
	private String userid;
    private Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if(msg.what==1){
				Toast.makeText(getApplicationContext(), "录入成功！", Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(getApplicationContext(), "录入失败！", Toast.LENGTH_SHORT).show();
			}
		}};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.d_wzwzlr);
		SharedPreferences settings = getSharedPreferences("userinfo",
				Activity.MODE_PRIVATE);
		userid = settings.getString("userid", "");
		initView();
	}

	private void initView() {
		jlbt = (EditText) findViewById(R.id.d_jlbt);
		jyz = (EditText) findViewById(R.id.d_jyz);
		sfzh = (EditText) findViewById(R.id.d_sfzh);
		dh = (EditText) findViewById(R.id.d_dh);
		xxdz = (EditText) findViewById(R.id.d_xxdz);
		wzwzlx = (Spinner) findViewById(R.id.d_wzwzlx);
		jyxm = (Spinner) findViewById(R.id.d_jyxm);
		cljg=(Spinner) findViewById(R.id.d_cljg);
		in = (Button) findViewById(R.id.d_in);
		in.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				new Thread(runnable).start();
				WzwzInActivity.this.finish();
			}
		});
	}

	Runnable runnable = new Runnable() {

		@Override
		public void run() {
			MwpmBussNocard nocard=new MwpmBussNocard();
			nocard.setTitle(jlbt.getText().toString());
			nocard.setOperator(jyz.getText().toString());
			nocard.setIdcard(sfzh.getText().toString());
			nocard.setPhone(dh.getText().toString());
			nocard.setAddress(xxdz.getText().toString());
			nocard.setType(wzwzlx.getSelectedItem().toString());
			nocard.setItem(jyxm.getSelectedItem().toString());
			nocard.setResult(cljg.getSelectedItem().toString());
			nocard.setSubmitid(userid);
			nocard.setSubmittime(StringUtil.getSysTime());
			String res=Service.saveNocard(nocard);
			if(MyTools.RETCOAD.equals(res)){
				handler.sendEmptyMessage(1);
			}else{
				handler.sendEmptyMessage(2);
			}
		}
	};
}
