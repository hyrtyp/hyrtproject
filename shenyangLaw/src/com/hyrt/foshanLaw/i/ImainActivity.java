package com.hyrt.foshanLaw.i;

import com.hyrt.NFC_ReTAG_PRO.NFC_ReTAG;
import com.hyrt.TM_PRO.TM_ReTAG;
import com.hyrt.foshanLaw.R;
import com.hyrt.foshanLaw.b.GgcxActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class ImainActivity extends Activity implements OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.i_main);
		findViewById(R.id.icon_a).setOnClickListener(this);
		findViewById(R.id.icon_b).setOnClickListener(this);
		findViewById(R.id.icon_c).setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = null;
		switch (v.getId()) {
		case R.id.icon_a:
			intent = new Intent(this,TM_ReTAG.class);
			break;
		case R.id.icon_b:
			intent = new Intent(this, NFC_ReTAG.class);
			break;
		case R.id.icon_c:
			break;
		}
		if (intent != null)
			startActivity(intent);
	}
}
