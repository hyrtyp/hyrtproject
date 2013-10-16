package com.hyrt.NFC_ReTAG_PRO;

import java.util.Formatter;

import com.hyrt.foshanLaw.R;
import com.hyrt.foshanLaw.b.BqycxActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.widget.TextView;

public class NFC_ReTAG extends Activity {

	private NfcAdapter nfcAdapter;
	TextView promt;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main1);
		promt = (TextView) findViewById(R.id.textView1);
		SharedPreferences settings = getSharedPreferences(
				"userinfo",
				Activity.MODE_PRIVATE);
		if(settings.getString("userid","").length() ==0){
			this.finish();
			return;
		}
		// 获取默认的NFC控制器
		nfcAdapter = NfcAdapter.getDefaultAdapter(this);
		if (nfcAdapter == null) {
			promt.setText("设备不支持NFC！");
			finish();
			return;
		}
		if (!nfcAdapter.isEnabled()) {
			promt.setText("请在系统设置中先启用NFC功能！");
			startActivity(new Intent(Settings.ACTION_NFCSHARING_SETTINGS));
			finish();
			return;
		}

		String actionStr = getIntent().getAction();
		if (actionStr != null
				&& actionStr.equals("android.nfc.action.NDEF_DISCOVERED")) {
			Tag tag = getIntent().getParcelableExtra(NfcAdapter.EXTRA_TAG);
			byte[] strs = tag.getId();
			for(int i=0;i<strs.length;i++){
				System.out.println(strs[i]);
			}
			Parcelable[] rawMsgs = getIntent().getParcelableArrayExtra(
	                NfcAdapter.EXTRA_NDEF_MESSAGES);
	        // only one message sent during the beam
	        NdefMessage msg = (NdefMessage) rawMsgs[0];
	        // record 0 contains the MIME type, record 1 is the AAR, if present
			byte[] bytes = getIntent().getByteArrayExtra(NfcAdapter.EXTRA_ID);
			final StringBuilder sb = new StringBuilder();
			try {
				for (int i = bytes.length - 1; i >= 0; i--) {
					StringBuilder sbitem = new StringBuilder();
					new Formatter(sbitem)
							.format("%02X", Byte.valueOf(bytes[i]));
					sb.append(sbitem.toString());
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			promt.setText(new String(msg.getRecords()[0].getPayload()));
			Intent intent = new Intent(NFC_ReTAG.this,BqycxActivity.class);
			intent.putExtra("zch", promt.getText());
			startActivity(intent);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		this.finish();
		super.onPause();
	}
	
}