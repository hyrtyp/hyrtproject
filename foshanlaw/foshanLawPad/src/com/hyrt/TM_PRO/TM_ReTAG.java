package com.hyrt.TM_PRO;

import com.hyrt.foshanLaw.R;
import com.zijunlin.Zxing.Demo.CaptureActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class TM_ReTAG extends Activity {

	TextView promt;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main2);
		promt = (TextView) findViewById(R.id.textView1);
		startActivityForResult(new Intent(this,CaptureActivity.class),111);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == 111){
			promt.setText(data.getStringExtra("SCAN_RESULT"));
		}
		super.onActivityResult(requestCode, resultCode, data);
	}


}