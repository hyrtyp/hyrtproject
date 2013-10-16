package com.hyrt.foshanLaw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hyrt.cei.util.MyTools;
import com.hyrt.cei.util.TimeOutHelper;
import com.hyrt.cei.webservice.service.Service;
import com.hyrt.foshanLaw.b.ArgGisActivity;
import com.hyrt.foshanLaw.b.NavitApplication;
import com.hyrt.foshanLaw.i.ImainActivity;
import com.hyrt.foshanLaw.pptclient.PPTService;
import com.hyrt.foshanLaw.pptclient.common.GlobalFunction;
import com.hyrt.foshanLaw.remoteService.StockQuoteService;
import com.zijunlin.Zxing.Demo.CaptureActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager.NameNotFoundException;
import android.location.LocationManager;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

public class FoshanLawActivity extends Activity {

	private TimeOutHelper timeOutHelper;
	public static final String DEVICE_TYPE = "pad";
	public static final boolean IS_DEBUG = true;
	public String deviceUniqueIdentifier;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		timeOutHelper = new TimeOutHelper(this);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		SharedPreferences settings = getSharedPreferences("userinfo",
				Activity.MODE_PRIVATE);
		((EditText) findViewById(R.id.main_loginName)).setText(settings
				.getString("name", ""));
		((EditText) findViewById(R.id.main_loginPass)).setText(settings
				.getString("password", ""));
		findViewById(R.id.main_loginSubmit).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						new Thread() {
							public void doStart() {
								timeOutHelper.installProcessingDialog();
								this.start();
							}

							public void run() {
								// 检查登录名和密码是否正确
								try {
									timeOutHelper.installTimerTask();
									final String passwordStr = ((EditText) findViewById(R.id.main_loginPass))
											.getText().toString();
									String jsonResult = Service
											.getUser(
													((EditText) findViewById(R.id.main_loginName))
															.getText()
															.toString(),
													passwordStr);
									final JSONObject user = new JSONObject(
											jsonResult);
									String errorCode = TimeOutHelper.ALDATA_FLAG;
									try {
										errorCode = user.getString("errorcode");
									} catch (Exception e) {
										e.printStackTrace();
									}

									if (!IS_DEBUG
											|| errorCode
													.equals(TimeOutHelper.ALDATA_FLAG)) {
										String result = Service
												.getPhoneIpadByUserId(user
														.getString("userid"));
										if (result.contains(DEVICE_TYPE)
												&& result.contains(getDeviceUniqueIdentifier())) {
											// 设备号匹配
										} else if (result.contains(DEVICE_TYPE)
												&& !result.contains(getDeviceUniqueIdentifier())) {
											errorCode = TimeOutHelper.ERROR_DEVICE_FLAG;
										} else if (!result
												.contains(DEVICE_TYPE)) {
											String errorCodeStr = Service.savePhoneIpadByUserId(
													user.getString("userid"),
													DEVICE_TYPE,getDeviceUniqueIdentifier());
											try {
												errorCode = new JSONObject(
														errorCodeStr)
														.getString("errorcode");
											} catch (Exception e) {
												e.printStackTrace();
											}
										}
									}
									timeOutHelper.uninstallTimerTask(errorCode);
									timeOutHelper.uninstallDialog();
									if (TimeOutHelper.ALDATA_FLAG
											.equals(errorCode)) {
										GlobalFunction.setUserId(
												FoshanLawActivity.this,
												user.getString("userid"));
										((NavitApplication) FoshanLawActivity.this
												.getApplication()).setUid(user
												.getString("userid"));
										((NavitApplication) FoshanLawActivity.this
												.getApplication())
												.setUname(user
														.getString("name"));
										SharedPreferences settings = getSharedPreferences(
												"userinfo",
												Activity.MODE_PRIVATE);
										Editor editor = settings.edit();
										editor.putString("userid",
												user.getString("userid"));
										editor.putString("name",
												user.getString("loginname"));
										editor.putString("reseauid",
												user.getString("reseauid"));
										editor.putString("level",
												user.getString("level"));
										editor.putString("password",
												passwordStr);
										editor.commit();
										File file1 = new File(
												MyTools.LOCAL_PATH
														+ "reseauid.txt");
										if (file1.exists())
											file1.delete();
										try {
											FileWriter writer = new FileWriter(
													file1);
											writer.write(user
													.getString("reseauid"));
											writer.close();
										} catch (IOException e) {
											e.printStackTrace();
										}
										File file = new File(MyTools.LOCAL_PATH
												+ "userid.txt");
										if (file.exists())
											file.delete();
										try {
											FileWriter writer = new FileWriter(
													file);
											writer.write(user
													.getString("userid"));
											writer.close();
										} catch (IOException e) {
											e.printStackTrace();
										}
										new Thread(new Runnable() {

											@Override
											public void run() {
												try {
													String role = Service
															.getRoleTypeByUserId(user
																	.getString("userid"));
													String type = new JSONArray(
															role).toString();
													SharedPreferences settings = getSharedPreferences(
															"userinfo",
															Activity.MODE_PRIVATE);
													Editor editor = settings
															.edit();
													editor.putString(
															"roletype", type);
													editor.commit();
												} catch (JSONException e) {
													// TODO Auto-generated catch
													// block
													e.printStackTrace();
												}
											}
										}).start();
										//初始化消息通知设置 by zhengwei
										GlobalFunction.init_NotiSetting(FoshanLawActivity.this);
										// 启动服务  by zhengwei
										PPTService.Start(FoshanLawActivity.this);
										startActivity(new Intent(
												FoshanLawActivity.this,
												MainChoiceActivity.class));
										FoshanLawActivity.this.finish();
									}
								} catch (Exception e) {
									timeOutHelper
											.uninstallTimerTask(TimeOutHelper.EXPCLIENT_FLAG);
									timeOutHelper.uninstallDialog();
									e.printStackTrace();
								}
							}
						}.doStart();
					}
				});
		LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		// 判断GPS是否正常启动
		if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			Toast.makeText(FoshanLawActivity.this, "请开启GPS导航...",
					Toast.LENGTH_SHORT).show();
			// 返回开启GPS导航设置界面
			Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			startActivity(intent);
			this.finish();
		}else{
			updateThread.start();
		}
		Intent i = new Intent(this, StockQuoteService.class);
		startService(i);
	}
	
    protected String getDeviceUniqueIdentifier(){
        if(deviceUniqueIdentifier == null)
        	deviceUniqueIdentifier = ((TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        if(deviceUniqueIdentifier == null || deviceUniqueIdentifier.length() == 0)
        	deviceUniqueIdentifier = android.provider.Settings.Secure.getString(this.getContentResolver(), 
        			android.provider.Settings.Secure.ANDROID_ID);
        return deviceUniqueIdentifier;
    }
    
    private Thread updateThread = new Thread() {
		public void run() {
			try {
				URL url = new URL("http://192.168.1.11:8090/LCSISTerminal/version1");
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.connect();
				// 获取文件大小
				int length = conn.getContentLength();
				// 创建输入流
				InputStream is = conn.getInputStream();
				if(Integer.parseInt(getStreamString(is))>getVersionCode(FoshanLawActivity.this)){
					Intent intent = new Intent();
					String apkUrl = "http://192.168.1.11:8090/LCSISTerminal/foshan1.apk";
					Uri u = Uri.parse(apkUrl);
					intent.setData(u);
					intent.setAction(Intent.ACTION_VIEW);
					intent.setClassName("com.android.browser",
							"com.android.browser.BrowserActivity");
					FoshanLawActivity.this.startActivity(intent);
				}  
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		/**
		 * 获取软件版本号
		 * 
		 * @param context
		 * @return
		 */
		private int getVersionCode(Context context) {
			int versionCode = 0;
			try {
				// 获取软件版本号，对应AndroidManifest.xml下android:versionCode
				versionCode = context.getPackageManager().getPackageInfo(
						"com.hyrt.foshanLaw", 0).versionCode;
			} catch (NameNotFoundException e) {
				e.printStackTrace();
			}
			return versionCode;
		}

		public String getStreamString(InputStream tInputStream) {
			if (tInputStream != null) {
				try {
					BufferedReader tBufferedReader = new BufferedReader(
							new InputStreamReader(tInputStream));
					StringBuffer tStringBuffer = new StringBuffer();
					String sTempOneLine = new String("");
					while ((sTempOneLine = tBufferedReader.readLine()) != null) {
						tStringBuffer.append(sTempOneLine);
					}
					return tStringBuffer.toString();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			return null;
		}
	};
	

}