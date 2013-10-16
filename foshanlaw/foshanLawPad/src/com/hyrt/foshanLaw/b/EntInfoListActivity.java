package com.hyrt.foshanLaw.b;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hyrt.cei.adapter.CommonListAdapter;
import com.hyrt.cei.util.TimeOutHelper;
import com.hyrt.cei.webservice.service.Service;
import com.hyrt.foshanLaw.R;
import com.hyrt.foshanLaw.G.PatrolEntinfohisActivity;
import com.hyrt.foshanLaw.G.TablesActivity;
import com.hyrt.foshanLaw.G.WzwzInActivity;
import com.hyrt.foshanLaw.c.BsearchActivity;
import com.hyrt.mwpm.vo.MwpmBussEntinfo;
import com.hyrt.mwpm.vo.MwpmBussEntupdate;
import com.hyrt.mwpm.vo.MwpmBussNocard;
import com.hyrt.mwpm.vo.MwpmBussPatrol;
import com.hyrt.mwpm.vo.MwpmSysMes;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class EntInfoListActivity extends Activity implements OnClickListener,
		OnItemClickListener {

	// 企业列表标注标志位
	public static final int ENTINFOLIST_FLAG = 1;
	// 任务列表标志位
	public static final int TASKLIST_FLAG = 2;
	// 任务点击后企业列表
	public static final int TASKLIST_ENTINFO_FLAG = 3;
	// 公告通知标志位
	public static final int NOTICELIST_FLAG = 4;
	// 无证无照列表查询
	public static final int NOCARDLIST_FLAG = 5;
	// 经济户口查询列表
	public static final int ENTERPRISERECORDLIST_FLAG = 6;
	// 线路查询
	public static final int ENTINFOLINELIST_FLAG = 7;
	// 纠错信息列表查询
	public static final int ERRORENT_FLAG = 8;
	// 用户组查询
	public static final int COMMUSERLIST_FLAG = 9;

	private TimeOutHelper timeOutHelper;
	// 获取的数据
	private JSONArray dataEntInfos;
	// 适配器的数据
	private JSONArray entInfos = new JSONArray();
	// 适配器
	private CommonListAdapter commonListAdapter;
	// 列表控件
	private ListView listView;
	// 更多按钮
	private TextView footer;
	// 当前页码
	private int pageNo = 0;
	// 企业查询条件
	private MwpmBussEntinfo mwpmBussEntinfo;
	// 公告查询条件
	private MwpmSysMes mwpmSysMes;
	// 无证无照查询条件
	private MwpmBussNocard mwpmBussNocard;
	// 任务查询条件
	private MwpmBussPatrol mwpmBussPatrol;
	// 是否可以录入标示
	private String inFlage, level, name;
	private Handler dataHandler = new Handler() {
		@Override
		public void dispatchMessage(Message msg) {
				for (int i = 0; i < dataEntInfos.length(); i++) {
					try {
						entInfos.put(dataEntInfos.getJSONObject(i));
					} catch (JSONException e) {
						e.printStackTrace();
					}
					if (commonListAdapter == null) {
						commonListAdapter = new CommonListAdapter(
								EntInfoListActivity.this, entInfos, msg.what);
						listView.setAdapter(commonListAdapter);
						footer.setVisibility(View.VISIBLE);
					} else {
						commonListAdapter.notifyDataSetChanged();
					}
			}
				if (commonListAdapter.CURRENT_FLAG == COMMUSERLIST_FLAG
						&& getIntent().getBooleanExtra("point", false)) {
					Intent intent = new Intent(EntInfoListActivity.this,ArgGisActivity.class);
					intent.putExtra(ArgGisActivity.GIS_DATA_KEY, dataEntInfos.toString());
					intent.putExtra(ArgGisActivity.GIS_KEY, ArgGisActivity.DRAW_CLICKPOINTS_FLAG);
					startActivity(intent);
					EntInfoListActivity.this.finish();
				}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_hastitle);
		SharedPreferences settings = getSharedPreferences("userinfo",
				Activity.MODE_PRIVATE);
		level = settings.getString("level", "");
		name = settings.getString("roletype", "");
		mwpmBussEntinfo = (MwpmBussEntinfo) getIntent().getSerializableExtra(
				"MwpmBussEntinfo");
		mwpmSysMes = (MwpmSysMes) getIntent()
				.getSerializableExtra("MwpmSysMes");
		mwpmBussNocard = (MwpmBussNocard) getIntent().getSerializableExtra(
				"MwpmBussNocard");
		mwpmBussPatrol = (MwpmBussPatrol) getIntent().getSerializableExtra(
				"MwpmBussPatrol");
		inFlage = getIntent().getStringExtra("inFlag");
		if ("yes".equals(inFlage)) {
			Button right = (Button) findViewById(R.id.top_button_right);
			right.setVisibility(View.VISIBLE);
			right.setText("录入");
			right.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (name.contains("无证无照管理员")) {
						Intent intent = new Intent(EntInfoListActivity.this,
								WzwzInActivity.class);
						startActivity(intent);
					} else {
						Toast.makeText(EntInfoListActivity.this, "此用户没有权限!",
								Toast.LENGTH_SHORT).show();
					}
				}
			});
			findViewById(R.id.top_button_left).setVisibility(View.INVISIBLE);
		} else {
			findViewById(R.id.top_button_left).setVisibility(View.INVISIBLE);
			findViewById(R.id.top_button_right).setVisibility(View.INVISIBLE);
		}
		timeOutHelper = new TimeOutHelper(this);
		switch (getIntent().getIntExtra("listFlag", ENTINFOLIST_FLAG)) {
		case ENTINFOLIST_FLAG:
			initEntListTitle();
			new Handler().post(new Runnable() {

				@Override
				public void run() {
					loadEntInfos();
				}
			});
			break;
		case ENTERPRISERECORDLIST_FLAG:
			initEntListTitle();
			new Handler().post(new Runnable() {

				@Override
				public void run() {
					loadEntInfos();
				}
			});
			break;
		case ENTINFOLINELIST_FLAG:
			initEntListTitle();
			new Handler().post(new Runnable() {

				@Override
				public void run() {
					loadEntInfos();
				}
			});
			break;
		case TASKLIST_FLAG:
			initTaskListTitle();
			new Handler().post(new Runnable() {

				@Override
				public void run() {
					loadEntInfoTasks();
				}
			});
			break;
		case TASKLIST_ENTINFO_FLAG:
			initEntListTitle();
			new Handler().post(new Runnable() {

				@Override
				public void run() {
					loadCompanyByTaskId();
				}
			});
			break;
		case NOTICELIST_FLAG:
			initNoticeListTitle();
			new Handler().post(new Runnable() {

				@Override
				public void run() {
					loadNotice();
				}
			});
			break;
		case NOCARDLIST_FLAG:
			initNoCardListTitle();
			new Handler().post(new Runnable() {

				@Override
				public void run() {
					loadNoCard();
				}
			});
			break;
		case ERRORENT_FLAG:
			initErrorEntTitle();
			new Handler().post(new Runnable() {

				@Override
				public void run() {
					queryEntErrorList();
				}
			});
			break;
		case COMMUSERLIST_FLAG:
			initCommonUserTitle();
			new Handler().post(new Runnable() {

				@Override
				public void run() {
					getCommonUserList();
				}
			});
			break;
		}

		listView = (ListView) findViewById(R.id.list);
		View view = LayoutInflater.from(EntInfoListActivity.this).inflate(
				R.layout.common_listview_bottom, null);
		footer = (TextView) view.findViewById(R.id.morebtn);
		listView.addFooterView(view);
		footer.setOnClickListener(EntInfoListActivity.this);
		listView.setOnItemClickListener(EntInfoListActivity.this);
	}

	private void getCommonUserList() {
		new Thread() {
			public void doStart() {
				timeOutHelper.installProcessingDialog();
				this.start();
			}

			public void run() {
				// 检查登录名和密码是否正确
				try {
					pageNo++;
					timeOutHelper.installTimerTask();
					SharedPreferences settings = getSharedPreferences(
							"userinfo", Activity.MODE_PRIVATE);
					String level = settings.getString("level", "");
					String jsonResult = "";
					//if (level.equals("jz") || level.equals("zdz")) {
						//jsonResult = Service.getCommonUserList(pageNo);
					//} else {
						jsonResult = Service.getCommonUserList(pageNo,
								settings.getString("userid", ""));
					//}
					String errorCode = TimeOutHelper.ALDATA_FLAG;
					try {
						dataEntInfos = new JSONArray(jsonResult);
					} catch (Exception e) {
						errorCode = new JSONObject(jsonResult)
								.getString("errorcode");
						e.printStackTrace();
					}
					timeOutHelper.uninstallTimerTask(errorCode);
					timeOutHelper.uninstallDialog();
					if (TimeOutHelper.ALDATA_FLAG.equals(errorCode)) {
						dataHandler.sendEmptyMessage(COMMUSERLIST_FLAG);
					} else {
						pageNo--;
					}
				} catch (Exception e) {
					timeOutHelper
							.uninstallTimerTask(TimeOutHelper.EXPCLIENT_FLAG);
					timeOutHelper.uninstallDialog();
					pageNo--;
					e.printStackTrace();
				}
			}
		}.doStart();
	}

	private void initCommonUserTitle() {
		((TextView) findViewById(R.id.top_text)).setText("人员列表");
		((TextView) findViewById(R.id.text1)).setText("姓名");
		((TextView) findViewById(R.id.text2)).setText("手机号");
	}

	/**
	 * 信息纠错列表标题
	 */
	private void initErrorEntTitle() {
		((TextView) findViewById(R.id.top_text)).setText("企业信息纠错列表");
		((TextView) findViewById(R.id.text2)).setText("市场主体");
		((TextView) findViewById(R.id.text1)).setText("注册号");
	}

	/**
	 * 加载纠错列表列表
	 */
	private void queryEntErrorList() {
		new Thread() {
			public void doStart() {
				timeOutHelper.installProcessingDialog();
				this.start();
			}

			public void run() {
				// 检查登录名和密码是否正确
				try {
					SharedPreferences settings = getSharedPreferences(
							"userinfo", Activity.MODE_PRIVATE);
					pageNo++;
					timeOutHelper.installTimerTask();
					String level = settings.getString("level", "");
					MwpmBussEntinfo mwpmBussEntinfo = new MwpmBussEntinfo();
					if (level.equals("jz")) {
						mwpmBussEntinfo.setUserid(null);
						mwpmBussEntinfo.setReseauid(null);
					} else if (level.equals("wgzrr")) {
						mwpmBussEntinfo.setUserid(null);
						mwpmBussEntinfo.setReseauid(settings.getString(
								"reseauid", ""));
					} else {
						mwpmBussEntinfo.setUserid(settings.getString("userid",
								""));
						mwpmBussEntinfo.setReseauid(null);
					}
					String jsonResult = Service.queryEntErrorList(
							mwpmBussEntinfo, pageNo);
					String errorCode = TimeOutHelper.ALDATA_FLAG;
					try {
						dataEntInfos = new JSONArray(jsonResult);
					} catch (Exception e) {
						errorCode = new JSONObject(jsonResult)
								.getString("errorcode");
						e.printStackTrace();
					}
					timeOutHelper.uninstallTimerTask(errorCode);
					timeOutHelper.uninstallDialog();
					if (TimeOutHelper.ALDATA_FLAG.equals(errorCode)) {
						dataHandler.sendEmptyMessage(ERRORENT_FLAG);
					} else {
						pageNo--;
					}
				} catch (Exception e) {
					timeOutHelper
							.uninstallTimerTask(TimeOutHelper.EXPCLIENT_FLAG);
					timeOutHelper.uninstallDialog();
					pageNo--;
					e.printStackTrace();
				}
			}
		}.doStart();
	}

	/**
	 * 加载无照查询列表
	 */
	protected void loadNoCard() {
		new Thread() {
			public void doStart() {
				timeOutHelper.installProcessingDialog();
				this.start();
			}

			public void run() {
				// 检查登录名和密码是否正确
				try {
					pageNo++;
					timeOutHelper.installTimerTask();
					if ("wgzrr".equals(getSharedPreferences("userinfo",
							Activity.MODE_PRIVATE).getString("reseauid", "")))
						mwpmBussNocard.setReseauid(getSharedPreferences(
								"userinfo", Activity.MODE_PRIVATE).getString(
								"reseauid", ""));
					String jsonResult = Service.queryNoCards(mwpmBussNocard,
							pageNo);
					String errorCode = TimeOutHelper.ALDATA_FLAG;
					try {
						dataEntInfos = new JSONArray(jsonResult);
					} catch (Exception e) {
						errorCode = new JSONObject(jsonResult)
								.getString("errorcode");
						e.printStackTrace();
					}
					timeOutHelper.uninstallTimerTask(errorCode);
					timeOutHelper.uninstallDialog();
					if (TimeOutHelper.ALDATA_FLAG.equals(errorCode)) {
						dataHandler.sendEmptyMessage(NOCARDLIST_FLAG);
					} else {
						pageNo--;
					}
				} catch (Exception e) {
					timeOutHelper
							.uninstallTimerTask(TimeOutHelper.EXPCLIENT_FLAG);
					timeOutHelper.uninstallDialog();
					pageNo--;
					e.printStackTrace();
				}
			}
		}.doStart();
	}

	private void initEntListTitle() {
		((TextView) findViewById(R.id.top_text)).setText("主体列表");
		((TextView) findViewById(R.id.text1)).setText("注册号");
		((TextView) findViewById(R.id.text2)).setText("主体名称");
	}

	private void initTaskListTitle() {
		findViewById(R.id.top_button_right).setVisibility(View.VISIBLE);
		((Button) findViewById(R.id.top_button_right)).setText("任务查询");
		findViewById(R.id.top_button_right).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						startActivity(new Intent(EntInfoListActivity.this,
								BsearchActivity.class));
					}
				});
		((TextView) findViewById(R.id.top_text)).setText("任务列表");
		((TextView) findViewById(R.id.text1)).setText("任务名称");
		((TextView) findViewById(R.id.text2)).setText("重要程度");
	}

	private void initNoticeListTitle() {
		((TextView) findViewById(R.id.top_text)).setText("公告信息列表");
		((TextView) findViewById(R.id.text1)).setText("标题");
		((TextView) findViewById(R.id.text2)).setText("发布时间");
	}

	private void initNoCardListTitle() {
		((TextView) findViewById(R.id.top_text)).setText("无证无照列表");
		((TextView) findViewById(R.id.text1)).setText("记录标题");
		((TextView) findViewById(R.id.text2)).setText("处理结果");
	}

	/**
	 * 标注企业坐标
	 */
	private void gisEntInfos(final int position, final String[] latLon) {
		new Thread() {
			public void doStart() {
				timeOutHelper.installProcessingDialog();
				this.start();
			}

			public void run() {
				// 检查登录名和密码是否正确
				try {
					timeOutHelper.installTimerTask();
					if (latLon == null) {
						timeOutHelper
								.uninstallTimerTask(TimeOutHelper.NOGIS_FLAG);
						timeOutHelper.uninstallDialog();
						return;
					}
					String jsonResult = Service.updateEntInfo(entInfos
							.getJSONObject(position).getString("ID"),
							latLon[0], latLon[1]);
					String errorCode = TimeOutHelper.SUCCGIS_FLAG;
					try {
						errorCode = new JSONObject(jsonResult)
								.getString("errorcode");
					} catch (Exception e) {
						e.printStackTrace();
					}
					timeOutHelper.uninstallTimerTask(errorCode);
					timeOutHelper.uninstallDialog();
				} catch (Exception e) {
					timeOutHelper
							.uninstallTimerTask(TimeOutHelper.EXPCLIENT_FLAG);
					timeOutHelper.uninstallDialog();
					e.printStackTrace();
				}
			}
		}.doStart();
	}

	/**
	 * 获取企业列表
	 */
	private void loadEntInfos() {
		new Thread() {
			public void doStart() {
				timeOutHelper.installProcessingDialog();
				this.start();
			}

			public void run() {
				// 检查登录名和密码是否正确
				try {
					pageNo++;
					timeOutHelper.installTimerTask();
					SharedPreferences settings = getSharedPreferences(
							"userinfo", Activity.MODE_PRIVATE);
					String level = settings.getString("level", "");
					if (level.equals("jz")) {
						mwpmBussEntinfo.setUserid(null);
						mwpmBussEntinfo.setReseauid(null);
					} else if (level.equals("wgzrr")) {
						mwpmBussEntinfo.setUserid(null);
						mwpmBussEntinfo.setReseauid(settings.getString(
								"reseauid", ""));
					} else {
						mwpmBussEntinfo.setReseauid(null);
					}
					String jsonResult = Service.queryEntInfos(mwpmBussEntinfo,
							pageNo);
					String errorCode = TimeOutHelper.ALDATA_FLAG;
					try {
						dataEntInfos = new JSONArray(jsonResult);
					} catch (Exception e) {
						errorCode = new JSONObject(jsonResult)
								.getString("errorcode");
						e.printStackTrace();
					}
					timeOutHelper.uninstallTimerTask(errorCode);
					timeOutHelper.uninstallDialog();
					if (TimeOutHelper.ALDATA_FLAG.equals(errorCode)) {
						dataHandler.sendEmptyMessage(getIntent().getIntExtra(
								"listFlag", ENTINFOLIST_FLAG));
					} else {
						pageNo--;
					}
				} catch (Exception e) {
					timeOutHelper
							.uninstallTimerTask(TimeOutHelper.EXPCLIENT_FLAG);
					timeOutHelper.uninstallDialog();
					pageNo--;
					e.printStackTrace();
				}
			}
		}.doStart();
	}

	/**
	 * 获取检查任务列表
	 */
	private void loadEntInfoTasks() {
		new Thread() {
			public void doStart() {
				timeOutHelper.installProcessingDialog();
				this.start();
			}

			public void run() {
				// 检查登录名和密码是否正确
				try {
					pageNo++;
					timeOutHelper.installTimerTask();
					String jsonResult = Service.queryPatrolTask(mwpmBussPatrol,
							pageNo);
					String errorCode = TimeOutHelper.ALDATA_FLAG;
					try {
						dataEntInfos = new JSONArray(jsonResult);
					} catch (Exception e) {
						errorCode = new JSONObject(jsonResult)
								.getString("errorcode");
						e.printStackTrace();
					}
					timeOutHelper.uninstallTimerTask(errorCode);
					timeOutHelper.uninstallDialog();
					if (TimeOutHelper.ALDATA_FLAG.equals(errorCode)) {
						dataHandler.sendEmptyMessage(TASKLIST_FLAG);
					} else {
						pageNo--;
					}
				} catch (Exception e) {
					timeOutHelper
							.uninstallTimerTask(TimeOutHelper.EXPCLIENT_FLAG);
					timeOutHelper.uninstallDialog();
					pageNo--;
					e.printStackTrace();
				}
			}
		}.doStart();
	}

	/**
	 * 获取信息列表
	 */
	private void loadNotice() {
		new Thread() {
			public void doStart() {
				timeOutHelper.installProcessingDialog();
				this.start();
			}

			public void run() {
				// 检查登录名和密码是否正确
				try {
					pageNo++;
					timeOutHelper.installTimerTask();
					String jsonResult = Service.queryNotice(mwpmSysMes, pageNo);
					String errorCode = TimeOutHelper.ALDATA_FLAG;
					try {
						dataEntInfos = new JSONArray(jsonResult);
					} catch (Exception e) {
						errorCode = new JSONObject(jsonResult)
								.getString("errorcode");
						e.printStackTrace();
					}
					timeOutHelper.uninstallTimerTask(errorCode);
					timeOutHelper.uninstallDialog();
					if (TimeOutHelper.ALDATA_FLAG.equals(errorCode)) {
						dataHandler.sendEmptyMessage(NOTICELIST_FLAG);
					} else {
						pageNo--;
					}
				} catch (Exception e) {
					timeOutHelper
							.uninstallTimerTask(TimeOutHelper.EXPCLIENT_FLAG);
					timeOutHelper.uninstallDialog();
					pageNo--;
					e.printStackTrace();
				}
			}
		}.doStart();
	}

	/**
	 * 获取路线
	 */
	private void getEntInfoLine(final int position) {
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle("提示");
		builder.setMessage("是否获取此企业路线！");
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				try {
					String lat = entInfos.getJSONObject(position).getString(
							"LAT");
					String long1 = entInfos.getJSONObject(position).getString(
							"LONG1");
					String[] myLatLon = MyMapActivity.getLatLon();
					if (lat == null || long1 == null || lat.equals("")
							|| long1.equals("")) {
						AlertDialog.Builder builder = new Builder(
								EntInfoListActivity.this);
						builder.setTitle("提示");
						builder.setMessage("获取不到此企业位置！");
						builder.create().show();
					} else if (myLatLon == null) {
						AlertDialog.Builder builder = new Builder(
								EntInfoListActivity.this);
						builder.setTitle("提示");
						builder.setMessage("获取不到当前位置！");
						builder.create().show();
					} else {
						Intent intent = new Intent(EntInfoListActivity.this,
								ArgGisActivity.class);
						intent.putExtra(ArgGisActivity.GIS_KEY,
								ArgGisActivity.DRAW_LINE_FLAG);
						intent.putExtra(ArgGisActivity.GIS_DATA_KEY, lat + "!@"
								+ long1);
						startActivity(intent);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}

				dialog.dismiss();
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		try {
			builder.create().show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, final int position,
			long arg3) {
		switch (commonListAdapter.CURRENT_FLAG) {
		case TASKLIST_FLAG:
			clickTaskItem(position);
			break;
		case ENTINFOLIST_FLAG:
			clickEntInfoItem(position);
			break;
		case ENTINFOLINELIST_FLAG:
			getEntInfoLine(position);
			break;
		case NOTICELIST_FLAG:
			clickNoticeItem(position);
			break;
		case ENTERPRISERECORDLIST_FLAG:
			clickEnterpriseRecordItem(position);
			break;
		case NOCARDLIST_FLAG: // 五证无照
			if ("yes".equals(inFlage)) {
				JSONObject object = (JSONObject) arg0.getAdapter().getItem(
						position);
				if (object != null) {
					try {
						String nid = object.getString("id");
						String title = object.getString("title");
						String operator = object.getString("operator");
						// 进入检查录入页面
						Intent intent = new Intent(this,
								PatrolEntinfohisActivity.class);
						intent.putExtra("flage", "wzwz");
						intent.putExtra("type", "无证无照");
						intent.putExtra("eid", nid);
						intent.putExtra("ename", title);
						intent.putExtra("zcnumber", operator);
						startActivity(intent);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} else {
				clickNoCardItem(position);
			}
			break;
		case TASKLIST_ENTINFO_FLAG:
			clickTaskEntItem(position);
			break;
		case ERRORENT_FLAG:
			clickErrorEntItem(position);
			break;
		case COMMUSERLIST_FLAG:
			clickCommonUserListItem(position);
			break;
		}
	}

	/**
	 * 点击用户列表
	 */
	private void clickCommonUserListItem(final int position) {
		try {
			// 跳转到检查轨迹查询页面
			if (!getIntent().getBooleanExtra("point", false)) {
				String userid = entInfos.getJSONObject(position).getString(
						"USERID");
				Intent intent = new Intent(this, BxcgjcxActivity.class);
				intent.putExtra("userid", userid);
				startActivity(intent);
			} else {
				Intent intent = new Intent(this, ArgGisActivity.class);
				intent.putExtra(ArgGisActivity.GIS_KEY,
						ArgGisActivity.DRAW_POINT_FLAG);
				if ("null".equals(entInfos.getJSONObject(position).getString(
						"LAT"))) {
					AlertDialog.Builder builder = new Builder(
							EntInfoListActivity.this);
					builder.setTitle("提示");
					builder.setMessage("此人员位置不存在！");
					builder.create().show();
				} else {
					intent.putExtra(ArgGisActivity.GIS_DATA_KEY,
							entInfos.getJSONObject(position).getString("LAT")
									+ "!@"
									+ entInfos.getJSONObject(position)
											.getString("LONG1"));
					startActivity(intent);
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 点击纠错条目的操作
	 * 
	 * @param position
	 */
	private void clickErrorEntItem(int position) {
		Intent intent = new Intent(EntInfoListActivity.this,
				EntInfoDetailActivity.class);
		intent.putExtra("listFlag", ERRORENT_FLAG);
		MwpmBussEntupdate mwpmBussEntupdate = new MwpmBussEntupdate();
		try {
			Class class1 = Class.forName("com.hyrt.mwpm.vo.MwpmBussEntupdate");
			Field[] fields = class1.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				if (fields[i].getName().equals("createtime"))
					continue;
				fields[i].setAccessible(true);
				try{
					fields[i].set(
							mwpmBussEntupdate,
							entInfos.getJSONObject(position).getString(
									fields[i].getName().toUpperCase()));
					}catch(Exception e1){
						e1.printStackTrace();
					}
				
			}
			intent.putExtra("detailObject", mwpmBussEntupdate);
			startActivity(intent);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 点击任务对应企业条目操作
	 */
	private void clickTaskEntItem(final int position) {
		new Thread() {
			public void doStart() {
				timeOutHelper.installProcessingDialog();
				this.start();
			}

			public void run() {
				// 检查登录名和密码是否正确
				try {
					timeOutHelper.installTimerTask();
					SharedPreferences settings = getSharedPreferences(
							"userinfo", Activity.MODE_PRIVATE);
					String level = settings.getString("level", "");
					MwpmBussEntinfo mwpmBussEntinfo = new MwpmBussEntinfo();
					if (level.equals("jz")) {
						mwpmBussEntinfo.setUserid(null);
						mwpmBussEntinfo.setReseauid(null);
					} else if (level.equals("wgzrr")) {
						mwpmBussEntinfo.setUserid(null);
						mwpmBussEntinfo.setReseauid(settings.getString(
								"reseauid", ""));
					} else {
						mwpmBussEntinfo.setUserid(settings.getString("userid",
								""));
						mwpmBussEntinfo.setReseauid(null);
					}
					String jsonResult = Service.getEntByReaId(mwpmBussEntinfo,
							entInfos.getJSONObject(position).getString("ID"));
					String errorCode = TimeOutHelper.ALDATA_FLAG;
					try {
						dataEntInfos = new JSONArray(jsonResult);
					} catch (Exception e) {
						errorCode = new JSONObject(jsonResult)
								.getString("errorcode");
						e.printStackTrace();
					}
					timeOutHelper.uninstallTimerTask(errorCode);
					timeOutHelper.uninstallDialog();
					if (TimeOutHelper.ALDATA_FLAG.equals(errorCode)) {
						Intent intent = new Intent(EntInfoListActivity.this,
								PatrolEntinfohisActivity.class);
						intent.putExtra("eid", entInfos.getJSONObject(position)
								.getString("ID"));
						intent.putExtra(
								"ename",
								entInfos.getJSONObject(position).getString(
										"NAME"));
						intent.putExtra(
								"zcnumber",
								entInfos.getJSONObject(position).getString(
										"ENROL"));
						intent.putExtra(
								"member",
								entInfos.getJSONObject(position).getString(
										"MEMBER"));
						intent.putExtra(
								"address",
								entInfos.getJSONObject(position).getString(
										"ADDRESS"));
						intent.putExtra("status",
								getIntent().getStringExtra("status"));
						intent.putExtra("pid", getIntent().getStringExtra("id"));
						intent.putExtra("type", "专项检查");
						startActivity(intent);
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

	/**
	 * 点击经济户口查询列表条目操作
	 * 
	 * @param position
	 */
	private void clickEnterpriseRecordItem(int position) {
		Intent intent = new Intent(EntInfoListActivity.this,
				EntInfoDetailActivity.class);
		intent.putExtra("listFlag", ENTERPRISERECORDLIST_FLAG);
		MwpmBussEntinfo mwpmBussEntinfo = new MwpmBussEntinfo();
		try {
			Class class1 = Class.forName("com.hyrt.mwpm.vo.MwpmBussEntinfo");
			Field[] fields = class1.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				fields[i].setAccessible(true);
				if (fields[i].getName().toUpperCase().equals("PID")
						|| fields[i].getName().toUpperCase().equals("PSTATUS"))
					continue;
				try{
				fields[i].set(mwpmBussEntinfo, entInfos.getJSONObject(position)
						.getString(fields[i].getName().toUpperCase()));
				}catch(Exception e1){
					e1.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		intent.putExtra("detailObject", mwpmBussEntinfo);
		startActivity(intent);
	}

	/**
	 * 点击无证无照列表条目操作
	 */
	private void clickNoCardItem(int position) {
		Intent intent = new Intent(EntInfoListActivity.this,
				EntInfoDetailActivity.class);
		intent.putExtra("listFlag", NOCARDLIST_FLAG);
		MwpmBussNocard mwpmBussNocard = new MwpmBussNocard();
		try {
			Class class1 = Class.forName("com.hyrt.mwpm.vo.MwpmBussNocard");
			Field[] fields = class1.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				if (fields[i].getName().equals("submittime")
						|| fields[i].getName().equals("submittimee")
						|| fields[i].getName().equals("submittimes"))
					continue;
				fields[i].setAccessible(true);
				try{
					fields[i].set(mwpmBussNocard, entInfos.getJSONObject(position)
							.getString(fields[i].getName()));
					}catch(Exception e1){
						e1.printStackTrace();
					}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		intent.putExtra("detailObject", mwpmBussNocard);
		startActivity(intent);
	}

	/**
	 * 点击通知列表条目操作
	 * 
	 * @param position
	 */
	private void clickNoticeItem(int position) {
		MwpmSysMes mwpmSysMes = new MwpmSysMes();
		try {
			Class class1 = Class.forName("com.hyrt.mwpm.vo.MwpmSysMes");
			Field[] fields = class1.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				if (fields[i].getName().equals("createtimee")
						|| fields[i].getName().equals("createtimes"))
					continue;
				fields[i].setAccessible(true);
				try{
					fields[i].set(mwpmSysMes, entInfos.getJSONObject(position)
							.getString(fields[i].getName()));
					}catch(Exception e1){
						e1.printStackTrace();
					}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Intent intent = new Intent(this, EntInfoDetailActivity.class);
		intent.putExtra("detailObject", mwpmSysMes);
		intent.putExtra("listFlag", commonListAdapter.CURRENT_FLAG);
		startActivity(intent);
	}

	/**
	 * 点击任务列表条目操作
	 * 
	 * @param position
	 */
	private void clickTaskItem(int position) {
		Intent intent = new Intent(EntInfoListActivity.this,
				EntInfoListActivity.class);
		intent.putExtra("listFlag", TASKLIST_ENTINFO_FLAG);
		try {
			intent.putExtra("id",
					entInfos.getJSONObject(position).getString("ID"));
			TablesActivity.PID = entInfos.getJSONObject(position).getString(
					"ID");
			intent.putExtra("status", entInfos.getJSONObject(position)
					.getString("STATUS"));
			startActivity(intent);
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 根据任务列表条目获取企业信息
	 */
	private void loadCompanyByTaskId() {
		new Thread() {
			public void doStart() {
				timeOutHelper.installProcessingDialog();
				this.start();
			}

			public void run() {
				// 检查登录名和密码是否正确
				try {
					pageNo++;
					timeOutHelper.installTimerTask();
					String jsonResult = Service.queryCompanyByPatrolId(
							getIntent().getStringExtra("id"), pageNo);
					String errorCode = TimeOutHelper.ALDATA_FLAG;
					try {
						dataEntInfos = new JSONArray(jsonResult);
					} catch (Exception e) {
						errorCode = new JSONObject(jsonResult)
								.getString("errorcode");
						e.printStackTrace();
					}
					timeOutHelper.uninstallTimerTask(errorCode);
					timeOutHelper.uninstallDialog();
					if (TimeOutHelper.ALDATA_FLAG.equals(errorCode)) {
						dataHandler.sendEmptyMessage(TASKLIST_ENTINFO_FLAG);
					} else {
						pageNo--;
					}
				} catch (Exception e) {
					timeOutHelper
							.uninstallTimerTask(TimeOutHelper.EXPCLIENT_FLAG);
					timeOutHelper.uninstallDialog();
					pageNo--;
					e.printStackTrace();
				}
			}
		}.doStart();
	}

	/**
	 * 企业列表点击条目标注
	 */
	private void clickEntInfoItem(final int position) {
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle("提示");
		builder.setMessage("是否标注此企业！");
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				gisEntInfos(position, MyMapActivity.getLatLon());
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		try {
			builder.create().show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.morebtn:
			switch (getIntent().getIntExtra("listFlag", ENTINFOLIST_FLAG)) {
			case ENTINFOLIST_FLAG:
				loadEntInfos();
				break;
			case ENTERPRISERECORDLIST_FLAG:
				loadEntInfos();
				break;
			case ENTINFOLINELIST_FLAG:
				loadEntInfos();
				break;
			case TASKLIST_FLAG:
				loadEntInfoTasks();
				break;
			case TASKLIST_ENTINFO_FLAG:
				loadCompanyByTaskId();
				break;
			case NOTICELIST_FLAG:
				loadNotice();
				break;
			case NOCARDLIST_FLAG:
				loadNoCard();
				break;
			/*
			 * case COMMUSERLIST_FLAG: getCommonUserList(); break;
			 */
			case ERRORENT_FLAG:
				queryEntErrorList();
				break;
			}
			break;
		}
	}
}
