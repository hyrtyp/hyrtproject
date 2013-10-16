package com.hyrt.foshanLaw.b;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hyrt.cei.webservice.service.Service;
import com.hyrt.foshanLaw.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;

public class BxcgjcxActivity extends Activity {
	private DatePicker dt_a_jzrq, dt_a_qsrq;
	//起始日期 截止日期
	private String qsrq, jzrq;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_xcgjcx);
		dt_a_qsrq = (DatePicker) findViewById(R.id.a_qsrq);
		dt_a_jzrq = (DatePicker) findViewById(R.id.a_jzrq);
		findViewById(R.id.a_kscx).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				qsrq = dt_a_qsrq.getYear()+"-"+(dt_a_qsrq.getMonth() + 1)+"-"+dt_a_qsrq.getDayOfMonth();
				jzrq = dt_a_jzrq.getYear()+"-"+(dt_a_jzrq.getMonth() + 1)+"-"+dt_a_jzrq.getDayOfMonth();
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						String userid = getIntent().getStringExtra("userid");
						if(userid == null){
							userid = getSharedPreferences("userinfo", Activity.MODE_PRIVATE).getString("userid", "");
						}
						String locals = Service.queryPatrolLoca(userid, qsrq, jzrq);
						System.out.println(locals);
						try {
							JSONArray jsonArray = new JSONArray(locals);
							StringBuilder sb = new StringBuilder();
							for(int i=0;i<jsonArray.length();i++){
								JSONObject jsonObject = jsonArray.getJSONObject(i);
								sb.append(jsonObject.getString("LONG1")+","+jsonObject.getString("LAT")+";");
							}
							Intent intent = new Intent(BxcgjcxActivity.this,GDMapActivity.class);
							intent.putExtra(GDMapActivity.GIS_KEY, GDMapActivity.DRAW_POINTS_FLAG);
							intent.putExtra(GDMapActivity.GIS_DATA_KEY, sb.toString());
							startActivity(intent);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}).start();
			}
		});
	
	}
}
