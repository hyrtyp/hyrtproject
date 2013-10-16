package com.hyrt.foshanLaw.b;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hyrt.cei.webservice.service.Service;
import com.hyrt.foshanLaw.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.Toast;

public class BxcgjcxActivity extends Activity {
	private DatePicker dt_a_jzrq, dt_a_qsrq;
	// 起始日期 截止日期
	private String qsrq, jzrq;
	Handler handler = new Handler();

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
				qsrq = dt_a_qsrq.getYear() + "-" + (dt_a_qsrq.getMonth() + 1)
						+ "-" + dt_a_qsrq.getDayOfMonth();
				jzrq = dt_a_jzrq.getYear() + "-" + (dt_a_jzrq.getMonth() + 1)
						+ "-" + dt_a_jzrq.getDayOfMonth();
				if (dateDiff(qsrq,jzrq,"yyyy-MM-dd")) {
					Toast.makeText(BxcgjcxActivity.this, "请缩小时间范围到5天以内！",
							Toast.LENGTH_LONG).show();
					return;
				}
				new Thread(new Runnable() {

					@Override
					public void run() {
						String userid = getIntent().getStringExtra("userid");
						if (userid == null) {
							userid = getSharedPreferences("userinfo",
									Activity.MODE_PRIVATE).getString("userid",
									"");
						}
						String locals = Service.queryPatrolLoca(userid, qsrq,
								jzrq);
						try {
							JSONArray jsonArray = new JSONArray(locals);
							Intent intent = new Intent(BxcgjcxActivity.this,
									ArgGisActivity.class);
							intent.putExtra(ArgGisActivity.GIS_KEY,
									ArgGisActivity.DRAW_POINTS_FLAG);
							intent.putExtra(ArgGisActivity.GIS_DATA_KEY,
									jsonArray.toString());
							startActivity(intent);
						} catch (JSONException e) {
							e.printStackTrace();
							handler.post(new Runnable() {

								@Override
								public void run() {
									Toast.makeText(BxcgjcxActivity.this,
											"无轨迹记录！", Toast.LENGTH_SHORT)
											.show();
								}
							});
						}
					}
				}).start();
			}
		});
	}

	public boolean dateDiff(String startTime, String endTime, String format) {
		// 按照传入的格式生成一个simpledateformate对象
		SimpleDateFormat sd = new SimpleDateFormat(format);
		long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
		long nh = 1000 * 60 * 60;// 一小时的毫秒数
		long nm = 1000 * 60;// 一分钟的毫秒数
		long ns = 1000;// 一秒钟的毫秒数
		long diff;
		try {
			// 获得两个时间的毫秒时间差异
			diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
			long day = diff / nd;// 计算差多少天
			long hour = diff % nd / nh;// 计算差多少小时
			long min = diff % nd % nh / nm;// 计算差多少分钟
			long sec = diff % nd % nh % nm / ns;// 计算差多少秒
			// 输出结果
			System.out.println("时间相差：" + day + "天" + hour + "小时" + min + "分钟"
					+ sec + "秒。");
			if(day > 5)
				return true;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}
}
