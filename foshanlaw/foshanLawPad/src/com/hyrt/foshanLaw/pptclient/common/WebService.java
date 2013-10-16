/**
 * 
 */
package com.hyrt.foshanLaw.pptclient.common;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;

import com.hyrt.cei.util.MyTools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

 
/**
 * Description:WebService调用
 * @author 郑伟
 * @Date 2013-1-15
 * @Copyright:2013-1-15
 * @Company:华源润通（北京）科技有限公司重庆分公司
 */
@SuppressWarnings("deprecation")
public class WebService {
	boolean err = false; // 執行時是否出錯
	boolean DEBUG = true; // 调试用，是否输出xml信息

	public boolean isErr() {
		return err;
	}

	// 判断网络状态
	boolean CheckNetWork(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();

		if (activeNetInfo != null && activeNetInfo.isConnected()) {
			return true;
		}
		return false;
	}

	public synchronized String Call(Context context) {
		err = false;
		String in0 = "<?xml version='1.0' encoding='utf-8'?><ROOT><id>1</id></ROOT>";
		String result = ""; // 返回值或者錯誤信息
		if (!this.CheckNetWork(context)) {
			err = true;
			return "网络无法连接";
		}
		try {
			String serviceNameSpace = "http://wsdl.webservice.lcsis.hyrt.com";
			String methodName = "queryUserGroup";
			String serviceURL = MyTools.url;
			
			String SOAP_ACTION = serviceURL + methodName;
			
			SoapObject request = new SoapObject(serviceNameSpace, methodName);

			request.addProperty("in0", in0);
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.bodyOut = request;
			(new MarshalBase64()).register(envelope);
			AndroidHttpTransport ht = new AndroidHttpTransport(serviceURL);
			ht.debug = true;
			ht.call(SOAP_ACTION, envelope);
			if (envelope.getResponse() != null) {
				SoapObject response = (SoapObject) envelope.bodyIn;
				//result = response.getProperty("out").toString();
				result = response.getProperty(0).toString();
			} else {
				result = "服务器处理失败";
				err = true;
			}
		} catch (Exception x) {
			result = "服务器连接失败";
			err = true;
		}
		if (DEBUG) {
			Log.i("result", result);
		}
		return result;
	}
}
