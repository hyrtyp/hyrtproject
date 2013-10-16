package com.hyrt.foshanLaw.G;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hyrt.cei.util.FormFile;
import com.hyrt.cei.util.MyTools;
import com.hyrt.cei.util.PostFile;
import com.hyrt.cei.util.StringUtil;
import com.hyrt.cei.webservice.service.Service;
import com.hyrt.foshanLaw.R;
import com.hyrt.mwpm.vo.MwpmBussPatrolItem;
import com.hyrt.mwpm.vo.MwpmBussPatrolProof;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class SavePatrolProofActivity extends Activity implements
		OnClickListener {
	
	private Button leftBtn;
	private Button paiz1, paiz2, paiz3, paiz4, paiz5, anjian, cmit;
	private EditText path1, path2, path3, path4, path5, bz1, bz2, bz3, bz4,
			bz5;
	private String fileName, userid, lid;
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
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.g_xcjl3);
		SharedPreferences settings = getSharedPreferences("userinfo",
				Activity.MODE_PRIVATE);
		userid = settings.getString("userid", "");
		SharedPreferences patrolLogId = getSharedPreferences("patrolLog",
				Activity.MODE_PRIVATE);
		lid = patrolLogId.getString("lid", "");
		initView();
	}

	private void initView() {
		paiz1 = (Button) findViewById(R.id.paizhao1);
		paiz1.setOnClickListener(this);
		paiz2 = (Button) findViewById(R.id.paizhao2);
		paiz2.setOnClickListener(this);
		paiz3 = (Button) findViewById(R.id.paizhao3);
		paiz3.setOnClickListener(this);
		paiz4 = (Button) findViewById(R.id.paizhao4);
		paiz4.setOnClickListener(this);
		paiz5 = (Button) findViewById(R.id.paizhao5);
		paiz5.setOnClickListener(this);
		cmit = (Button) findViewById(R.id.g_tj);
		cmit.setOnClickListener(this);
		anjian = (Button) findViewById(R.id.g_zaj);
		anjian.setOnClickListener(this);
		path1 = (EditText) findViewById(R.id.g_tplj1);
		path2 = (EditText) findViewById(R.id.g_tplj2);
		path3 = (EditText) findViewById(R.id.g_tplj3);
		path4 = (EditText) findViewById(R.id.g_tplj4);
		path5 = (EditText) findViewById(R.id.g_tplj5);
		bz1 = (EditText) findViewById(R.id.g_bz1);
		bz2 = (EditText) findViewById(R.id.g_bz2);
		bz3 = (EditText) findViewById(R.id.g_bz3);
		bz4 = (EditText) findViewById(R.id.g_bz4);
		bz5 = (EditText) findViewById(R.id.g_bz5);
		leftBtn = (Button)findViewById(R.id.top_button_left);
		leftBtn.setText("表单录入");
		leftBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(SavePatrolProofActivity.this,TablesActivity.class));
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		switch (v.getId()) {
		case R.id.paizhao1:
			startActivityForResult(intent, 1);
			break;
		case R.id.paizhao2:
			startActivityForResult(intent, 2);
			break;
		case R.id.paizhao3:
			startActivityForResult(intent, 3);
			break;
		case R.id.paizhao4:
			startActivityForResult(intent, 4);
			break;
		case R.id.paizhao5:
			startActivityForResult(intent, 5);
			break;
		case R.id.g_tj:
			new Thread(runnable).start();
			break;
		case R.id.g_zaj:
			Intent intent2=new Intent(this, CaseActivity.class);
			startActivity(intent2);
			this.finish();
			break;
		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(data == null)
			return;
		Uri uriVideo = data.getData();
		Cursor cursor = this.getContentResolver().query(uriVideo, null, null,
				null, null);
		if (cursor.moveToNext()) {
			fileName = cursor.getString(cursor.getColumnIndex("_data"));
		}
		if (fileName != null && !fileName.equals("")) {
			if (requestCode == 1) {
				path1.setText(fileName);
			} else if (requestCode == 2) {
				path2.setText(fileName);
			} else if (requestCode == 3) {
				path3.setText(fileName);
			} else if (requestCode == 4) {
				path4.setText(fileName);
			} else if (requestCode == 5) {
				path5.setText(fileName);
			}
		}
	}

	Runnable runnable = new Runnable() {

		@Override
		public void run() {
			List<MwpmBussPatrolProof> proofs = new ArrayList<MwpmBussPatrolProof>();
			if (!path1.getText().toString().equals("")) {
				MwpmBussPatrolProof proof = new MwpmBussPatrolProof();
				proof.setPath(path1.getText().toString());
				proof.setRemark(bz1.getText().toString());
				proof.setUserid(userid);
				proof.setLid(lid);
				proof.setClock(StringUtil.getSysTime());
				proofs.add(proof);
			} if (!path2.getText().toString().equals("")) {
				MwpmBussPatrolProof proof = new MwpmBussPatrolProof();
				proof.setPath(path2.getText().toString());
				proof.setRemark(bz2.getText().toString());
				proof.setUserid(userid);
				proof.setLid(lid);
				proof.setClock(StringUtil.getSysTime());
				proofs.add(proof);
			} if (!path3.getText().toString().equals("")) {
				MwpmBussPatrolProof proof = new MwpmBussPatrolProof();
				proof.setPath(path3.getText().toString());
				proof.setRemark(bz3.getText().toString());
				proof.setUserid(userid);
				proof.setLid(lid);
				proof.setClock(StringUtil.getSysTime());
				proofs.add(proof);
			} if (!path4.getText().toString().equals("")) {
				MwpmBussPatrolProof proof = new MwpmBussPatrolProof();
				proof.setPath(path4.getText().toString());
				proof.setRemark(bz4.getText().toString());
				proof.setUserid(userid);
				proof.setLid(lid);
				proof.setClock(StringUtil.getSysTime());
				proofs.add(proof);
			} if (!path5.getText().toString().equals("")) {
				MwpmBussPatrolProof proof = new MwpmBussPatrolProof();
				proof.setPath(path5.getText().toString());
				proof.setRemark(bz5.getText().toString());
				proof.setUserid(userid);
				proof.setLid(lid);
				proof.setClock(StringUtil.getSysTime());
				proofs.add(proof);
			}
			String rs = forrrrr(proofs);

			int size = proofs.size();
			FormFile[] sssss = new FormFile[size];
			for (int i = 0; i < size; i++) {
				sssss[i] = new FormFile("path" + i, proofs.get(i).getPath()
						.toString());
			}
			HashMap<String, String> param = new HashMap<String, String>();
			param.put("xmlStr", rs);
			try {
				String rs1 = PostFile.post(MyTools.UPLOAD_PATH, param, sssss);
				if(rs1.equals("1")){
					handler.sendEmptyMessage(1);
					//跳转表单
				/*	Intent intent=new Intent(getApplicationContext(), TablesActivity.class);
					startActivity(intent);*/
					SavePatrolProofActivity.this.finish();
				}else{
					handler.sendEmptyMessage(2);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	};

	private String forrrrr(List<MwpmBussPatrolProof> proofs) {
		String rs = "<?xml version=\"1.0\" encoding=\"utf-8\"?> <ROOT><proofs>";
		for (MwpmBussPatrolProof proof : proofs) {
			rs += "<item>" + "<lid>" + proof.getLid() + "</lid>" + "<path>"
					+ proof.getPath() + "</path>" + "<userid>"
					+ proof.getUserid() + "</userid>" + "<clock>"
					+ StringUtil.DateToStr(proof.getClock()) + "</clock>"
					+ "<remark>" + proof.getRemark() + "</remark>" + "</item>";
		}
		rs += "</proofs></ROOT>";
		return rs;
	}
}
