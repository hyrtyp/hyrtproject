package com.hyrt.NFC_ReTAG_PRO;


import com.hyrt.foshanLaw.R;
import com.hyrt.foshanLaw.b.BqycxActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class IFRD_ReTAG extends Activity {

	TextView promt;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main3);
		promt = (TextView) findViewById(R.id.textView1);
		findViewById(R.id.saomiao).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				/*RfidTagReaderAPI.Set14443ATagModel();
				System.out.println(RfidTagReaderAPI.Read14443BBlockData("00") + " : " + RfidTagReaderAPI.Read14443BBlockData("0A")
						 + " : " + RfidTagReaderAPI.Read14443BBlockData("01"));
				promt.setText(RfidTagReaderAPI.Read14443BBlockData("0A"));
				Intent intent = new Intent(IFRD_ReTAG.this,BqycxActivity.class);
				intent.putExtra("zch", promt.getText());
				startActivity(intent);*/
			}
		});
		//RfidTagReaderAPI.InitRFIDReader();
	}
	
	@Override
	protected void onDestroy() {
		//RfidTagReaderAPI.FinalRFIDReader();
		super.onDestroy();
	}

}