/**
 * 
 */
package com.hyrt.foshanLaw.pptclient;

import java.net.InetSocketAddress;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.json.JSONException;

import com.hyrt.foshanLaw.pptclient.business.CmdExcute;
import com.hyrt.foshanLaw.pptclient.business.CmdStr;
import com.hyrt.foshanLaw.pptclient.common.CommKey;
import com.hyrt.foshanLaw.pptclient.common.FileOperator;
import com.hyrt.foshanLaw.pptclient.common.GlobalFunction;
import com.hyrt.foshanLaw.pptclient.db.business.CacheDataFunction;
import com.hyrt.foshanLaw.pptclient.db.dao.CacheData;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

/**
 * Description:核心服务，用于创建连接和后台数据交互
 * 
 * @author 郑伟
 * @Date 2013-1-7
 * @Copyright:2013-1-7
 * @Company:华源润通（北京）科技有限公司重庆分公司
 */
public class PPTService extends Service {
	CmdReceiver cmdr;
	Context context;
	String deviceid;
	NioSocketConnector connector;
	IoSession session;
	ConnectFuture cf;
 
	boolean isUpdating; // 上传标识
	// Logger logger;
	StartConnect th;
	boolean isConnect;
	String userid;
	Handler ServiceHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			// 重启服务完成 处理临时表中的数据，提交给后台
			String m = msg.getData().getString("type");

			String stamp = "" + System.currentTimeMillis();
			String str = null;
			if ("login".equals(m)) {
				// 发送登陆信息
				str = CmdStr.getLoginstr(stamp, userid);
				send(str);
			} else if (CommKey.type_info.equals(m)) {
				// 查询会话信息
				String sessionid = msg.getData().getString("sessionid");
				str = CmdStr.getSessionInfo(stamp, userid, sessionid);
				send(str);
			} else if (CommKey.type_send.equals(m)) {
				String sendmsg = msg.getData().getString("sessionid");
				send(sendmsg);
			}
			super.handleMessage(msg);
		}
	};
	Runnable UpdateCacheData = new Runnable() {

		@Override
		public void run() {
			new Thread(new Runnable() {

				@Override
				public void run() {
					updateData();
				}
			}).start();
		}

	};

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		  Log.i("session", "------------service初始化---------");
		System.setProperty("java.net.preferIPv6Addresses", "false");
		context = PPTService.this;
		super.onCreate();

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		isUpdating = false;
		  Log.i("session", "------------service  销毁---------");

		// 断开连接
		destory_connect();

		synchronized (th) {
			th.interrupt();
			th = null;
		}

		// 取消广播
		context.unregisterReceiver(cmdr);

		super.onDestroy();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		  Log.i("session", "------------service   启动---------");
		isConnect = false;
 
		isUpdating = false;
		userid = GlobalFunction.getUserId(context);
		// 注册广播
		cmdr = new CmdReceiver();
		IntentFilter recevierFilter = new IntentFilter();
		recevierFilter.addAction(CommKey.svr_receiver);
		recevierFilter.addAction(CommKey.netstop_receiver);
		recevierFilter.addAction(CommKey.netstart_receiver);
		recevierFilter.addAction("login");
		context.registerReceiver(cmdr, recevierFilter);

		// mina启动
		if (GlobalFunction.checkNetWork(context)) {
			// Log.i("service", "网络可用，服务启动");
			runConnect();
		} else {
			// Log.i("service", "网络不可用，服务不启动");
		}
		super.onStart(intent, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generated method stub
		return super.onUnbind(intent);
	}

	// **********************私有函数

	/**
	 * 内部接收广播，主要用于启动mina连接
	 */
	class CmdReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context Ccontext, Intent intent) {
			// TODO Auto-generated method stub
			String act = intent.getAction();
			if (act.equals(CommKey.svr_receiver)) {
				String type = intent.getExtras().getString("type");
				if (type == null || type.equals(""))
					return;
				String sessionid = intent.getExtras().getString("sessionid");
				if (CommKey.type_update.equals(type)) {
					// 提交数据
					if(isConnect)
						ServiceHandler.post(UpdateCacheData);
					else{
						//重连
						runConnect();
					}
				} else if (CommKey.type_info.equals(type)
						|| CommKey.type_send.equals(type)) {
					// 交给ServiceHandler来处理
					Message msg = ServiceHandler.obtainMessage();
					Bundle b = new Bundle();
					b.putString("type", type);
					b.putString("sessionid", sessionid);
					msg.setData(b);
					ServiceHandler.sendMessage(msg);
				}
			} else if (act.equals(CommKey.netstop_receiver)) {
				// 网络关闭
				destory_connect();
				isConnect = false;
			} else if (act.equals(CommKey.netstart_receiver)) {
				// 重启服务
				if (isConnect == false)
					runConnect();
			}else if(act.equals("login")){
				Message msg = ServiceHandler.obtainMessage();
				Bundle b = new Bundle();
				b.putString("type", "login");
				msg.setData(b);
				ServiceHandler.sendMessage(msg);
			}  
		}
	}

	// ***********************静态函数，启动、停止服务

	public static void Start(Context _context) {
		if (GlobalFunction.isRunning(_context) == false) {
			 
			Intent intnet = new Intent(CommKey.pkgname);
			_context.startService(intnet);
		}else{
			//重新登陆
			Intent ie = new Intent();
			ie.setAction("login");
			_context.sendBroadcast(ie);
		}
	}

	public static void Stop(Context _context) {
		Intent intnet = new Intent(CommKey.pkgname);
		_context.stopService(intnet);
	}

	// **********************IoHandler
	class CIoHandler extends IoHandlerAdapter {

		@Override
		public void exceptionCaught(IoSession session, Throwable cause)
				throws Exception {
			// TODO Auto-generated method stub
			//重连
			isConnect = false;
			runConnect();
			super.exceptionCaught(session, cause);
			throw new CharacterCodingException();
		}

		@Override
		public void messageSent(IoSession session, Object message)
				throws Exception {
			// TODO Auto-generated method stub
			super.messageSent(session, message);
		}

		@Override
		public void sessionOpened(IoSession session) {
			// Set reader idle time to 10 seconds.
			// sessionIdle(...) method will be invoked when no data is read
			// for 10 seconds.
			session.getConfig().setIdleTime(IdleStatus.READER_IDLE, 10);
			isConnect = true;
			Message msg = ServiceHandler.obtainMessage();
			Bundle b = new Bundle();
			b.putString("type", "login");
			msg.setData(b);
			ServiceHandler.sendMessage(msg);

		}
		
		

		@Override
		public void sessionIdle(IoSession session, IdleStatus status)
				throws Exception {
			// TODO Auto-generated method stub
			
			String str=String.format("{\"cmd\":\"step\",\"uid\":\"%s\"}", userid);
			send(str);
			super.sessionIdle(session, status);
		}

		@Override
		public void sessionClosed(IoSession session) {
			// Print out total number of bytes read from the remote peer.
			// System.err.println("Total " + session.getReadBytes() +
			// " byte(s)");
 
			isConnect = false;

			if (GlobalFunction.checkNetWork(context)) {
				runConnect();
				// Intent ie = new Intent();
				// ie.setAction("RESTART_SERVICE");
				// GlobalFunction.sendPendingBroadcast(context, ie, 5);
			}
		}

		@Override
		public void messageReceived(IoSession session, Object message) {

			String theMessage = message.toString();
			if ("".equals(theMessage) == false
					&& "1".equals(theMessage) == false) {
				Log.i("session", "收到    " + theMessage);
				// 事件处理
				try {
					CmdExcute.run(context, userid, theMessage);

					/*
					 * //来消息了，给予消息栏提示 author : yepeng NotificationManager nm =
					 * (NotificationManager)
					 * getSystemService(NOTIFICATION_SERVICE); Notification
					 * notification = new Notification(R.drawable.icon, "对讲提醒",
					 * System.currentTimeMillis()); Intent intent = new
					 * Intent(PPTService.this,DialogActivity.class);
					 * PendingIntent contentIntent =
					 * PendingIntent.getActivity(PPTService.this, 3, intent, 3);
					 * notification.setLatestEventInfo(PPTService.this,"对讲提醒",
					 * "有新的对讲信息，请点击查看！", contentIntent); if (nm != null &&
					 * notification != null) { nm.notify(R.string.time_task,
					 * notification); }
					 */
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * mina发送
	 * 
	 * @param str
	 */
	boolean send(String str) {
		boolean flag = false;
		if (isConnect) {
			if (str.equals("") == false) {
				try {
					session.write(str);
					flag = true;
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}else{
			runConnect();
		}
		return flag;
	}

	/**
	 * 上传事件，先上传语音文件，再提交数据
	 */
	synchronized void updateData() {
		if (isConnect) {
			// 处理一般的数据
			if (isUpdating) {
				return; // 正在提交，不变化
			}
			isUpdating = true;

			CacheDataFunction cfun = new CacheDataFunction(context);
			List<CacheData> lst = cfun.getList(userid);
			if (lst.size() > 0) {

				for (int i = 0; i < lst.size(); i++) {
					// 上传文件
					FileOperator fop = new FileOperator(context);
					String result = fop.upload(lst.get(i).getSessionid(),
							userid, lst.get(i).getFilename(), lst.get(i)
									.getFilepath(), lst.get(i).getId());
					fop.close();
					if (result.equals("ok") == false) {
						// 提示错误信息，文件没有上传成功
						// ui显示发送失败
					} else {
						lst.get(i).setSend(true);
						// 上传信息
						if (send(lst.get(i).getResult())) {
							// 如果发送成功后，设置删除，并通知界面
							lst.get(i).setDel(true);
							// 通知ui更新界面
							Intent iesession = new Intent();
							iesession.setAction(CommKey.ui_receiver);
							iesession.putExtra("type", CommKey.type_returnmsg);
							iesession.putExtra("id", lst.get(i).getId());

							GlobalFunction.sendPendingBroadcast(context,
									iesession, 1);
						}
					}
				}
				// 清空数据
				cfun.del(lst);
			}

			isUpdating = false;
		}
	}

	void runConnect() {

		if (!isConnect) {

			if (th == null) {
				// 未初始化
				th = new StartConnect();
				th.start();
			} else {
				synchronized (th) {
					if (th.isRunning() == false) {
						destory_connect();

						th = null;
						th = new StartConnect();
						th.start();
					}
				}

			}
			// Toast.makeText(context, "启动服务", Toast.LENGTH_SHORT).show();
		}
	}

	class StartConnect extends Thread {
		boolean isrunning;

		public StartConnect() {
			isrunning = false;
		}

		public boolean isRunning() {
			return isrunning;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub

			destory_connect();
			isrunning = true;
			try {

				// Create TCP/IP connector.
				connector = new NioSocketConnector();
			 
				// 设置编码
				connector.getFilterChain().addLast("logger",
						new LoggingFilter());

				TextLineCodecFactory tlc = new TextLineCodecFactory(
						Charset.forName("gb2312"),
						// LineDelimiter.WINDOWS.getValue(),
						// LineDelimiter.WINDOWS.getValue()
						"\r\n", "\r\n");
				tlc.setDecoderMaxLineLength(20480);
				connector.getFilterChain().addLast("codec",
						new ProtocolCodecFilter(tlc));

				// 设置连接超时30秒
				connector.setConnectTimeoutMillis(30000L);
				connector.setConnectTimeoutCheckInterval(10000);
				// Start communication.
				connector.setHandler(new CIoHandler());
				 
				String ip = CommKey.server_default_ip;
				int port = CommKey.server_default_port;
				cf = connector.connect(new InetSocketAddress(ip, port));
			 
				cf.awaitUninterruptibly(30000L);// 等待连接并创建完成
				try {
					session = cf.getSession();
					 
					if (session != null) {
						session.getCloseFuture().awaitUninterruptibly();
					}
				} catch (RuntimeIoException e) {
					e.printStackTrace();
				}
				// 如果session没创建成功，则销毁mina的部分
				// *********注意，服务端断开之后才会执行下面的部分
				if (isConnect == false) {
					destory_connect();
				}

			} catch (Exception x) {
				destory_connect();
			}
			isrunning = false;
			isConnect = false;
		}

	}

	/**
	 * 重置连接
	 */
	void destory_connect() {
		if (cf != null)
			cf.cancel();
		if (connector != null) {
			connector.dispose();
			connector = null;
		}
		if (session != null) {
			session.close(true);
		}
		session = null;
	}
}
