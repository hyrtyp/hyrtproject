package com.hyrt.foshanLaw.test;

import java.util.HashMap;

import com.hyrt.cei.util.ColumnsUtil;
import com.hyrt.cei.util.FormFile;
import com.hyrt.cei.util.MyTools;
import com.hyrt.cei.util.PostFile;
import com.hyrt.cei.util.StringUtil;
import com.hyrt.cei.vo.Column;
import com.hyrt.cei.webservice.service.Service;
import com.hyrt.foshanLaw.FoshanLawActivity;
import com.hyrt.foshanLaw.R;
import com.hyrt.foshanLaw.G.SavePatrolProofActivity;
import com.hyrt.foshanLaw.G.TablesActivity;
import com.hyrt.foshanLaw.R.id;
import com.hyrt.mwpm.vo.MwpmBussPatrolProof;

import android.test.InstrumentationTestCase;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class FoshanLawTest extends InstrumentationTestCase {
	private FoshanLawActivity sample = null;
	private Button button = null;
	private TextView text = null;

	/*
	 * 初始设置
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() {
		try {
			super.setUp();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Intent intent = new Intent();
		intent.setClassName("com.hyrt.foshanLaw",
				FoshanLawActivity.class.getName());
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		sample = (FoshanLawActivity) getInstrumentation().startActivitySync(
				intent);
		try {
			testAdd();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * 垃圾清理与资源回收
	 * 
	 * @see android.test.InstrumentationTestCase#tearDown()
	 */
	@Override
	protected void tearDown() {
		sample.finish();
		try {
			super.tearDown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 活动功能测试
	 
	public void testActivity() throws Exception {
		Log.v("testActivity", "test the Activity");
		SystemClock.sleep(1500);
		getInstrumentation().runOnMainSync(new PerformClick(button));
		SystemClock.sleep(3000);
		assertEquals("Hello Android", text.getText().toString());
	}

	
	 * 模拟按钮点击的接口
	 
	private class PerformClick implements Runnable {
		Button btn;

		public PerformClick(Button button) {
			btn = button;
		}

		public void run() {
			btn.performClick();
		}
	}*/

	/*
	 * 测试类中的方法
	 */
	public void testAdd() throws Exception {
		String rs = "<?xml version=\"1.0\" encoding=\"utf-8\"?> <ROOT><proofs>";
			rs += "<item>" + "<lid>" +"4028818b3df7db34013df812b99b003c" + "</lid>" 
					+ "<path>" + "/storage/sdcard0/DCIM/Camera/20130411_155529.jpg" + "</path>" 
					+ "<userid>" + "4028818b3df7db1d013df807199e0027" + "</userid>"
					+ "<clock>" +"2013-04-11 16:08:34"+ "</clock>"
					+ "<remark>" + "</remark>" 
					+ "</item>";
		rs += "</proofs></ROOT>";
		FormFile[] sssss = new FormFile[1];
		for (int i = 0; i < 1; i++) {
			sssss[i] = new FormFile("path" + i, "/storage/sdcard0/DCIM/Camera/20130411_155529.jpg");
		}
		HashMap<String, String> param = new HashMap<String, String>();
		param.put("xmlStr", rs);
		String rs1 = PostFile.post(MyTools.UPLOAD_PATH, param, sssss);
		if(rs1.equals("1")){
			System.out.println(rs1);
		}
	}
}
