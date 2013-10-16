/**
 * 
 */
package com.hyrt.foshanLaw.pptclient.receive;
 
import com.hyrt.foshanLaw.pptclient.common.CommKey;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Description:网络判断
 * @author 郑伟
 * @Date 2013-1-13
 * @Copyright:2013-1-13
 * @Company:华源润通（北京）科技有限公司重庆分公司
 */
public class NetWorkReceiver extends BroadcastReceiver{

	/* (non-Javadoc)
	 * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		String act = intent.getAction();
		if (act.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
			ConnectivityManager connectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo info = connectivityManager.getActiveNetworkInfo();
			Intent ie = new Intent();
			if (info != null && info.isAvailable() && info.isConnected()) {
				// 网络可用
				ie.setAction(CommKey.netstart_receiver);
			} else {
				//Log.i("xxx", "网络关闭，停止服务");
				ie.setAction(CommKey.netstop_receiver);
			}
			context.sendBroadcast(ie);
		}
	}

}
