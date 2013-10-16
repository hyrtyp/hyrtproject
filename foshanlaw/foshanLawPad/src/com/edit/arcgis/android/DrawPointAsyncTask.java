package com.edit.arcgis.android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.MapView;

public class DrawPointAsyncTask extends AsyncTask<Integer, Integer, String> {
	private String strUrl = "";
	@SuppressWarnings("unused")
	private MapView thisMap = null;
	GraphicsLayer mGraphicsLayer;

	public DrawPointAsyncTask(String url, MapView map) {
		this.strUrl = url;
		this.thisMap = map;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected String doInBackground(Integer... params) {
		String result = "";
		try {
			URL url = new URL(strUrl);
			try {
				HttpURLConnection urlConn = (HttpURLConnection) url
						.openConnection();
				InputStreamReader in = new InputStreamReader(
						urlConn.getInputStream());
				BufferedReader bufferReader = new BufferedReader(in);

				String readLine = null;
				while ((readLine = bufferReader.readLine()) != null) {
					result += readLine;
				}
				in.close();
				urlConn.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		try {
			JSONObject jsonresult = new JSONObject(result);// ת��ΪJSONObject
//			int num = result.length();
			JSONArray nameList = jsonresult.getJSONArray("name");// ��ȡJSONArray
			int length = nameList.length();
			String name = "";
			for (int i = 0; i < length; i++) {// ����JSONArray
				Log.d("debugTest", Integer.toString(i));
				JSONObject oj = nameList.getJSONObject(i);
				name = name + oj.getString("name") + "|";
			}
			JSONArray xlist = jsonresult.getJSONArray("x");// ��ȡJSONArray
			int lengthx = xlist.length();
			String x = "";
			for (int i = 0; i < lengthx; i++) {// ����JSONArray
				Log.d("debugTest", Integer.toString(i));
				JSONObject oj = xlist.getJSONObject(i);
				x = x + oj.getString("x") + "|";
			}
			JSONArray ylist = jsonresult.getJSONArray("y");// ��ȡJSONArray
			int lengthy = ylist.length();
			String y = "";
			for (int i = 0; i < lengthy; i++) {// ����JSONArray
				Log.d("debugTest", Integer.toString(i));
				JSONObject oj = nameList.getJSONObject(i);
				y = y + oj.getString("y") + "|";
			}
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
		
		return "ִ";
	}

	@Override
	protected void onProgressUpdate(Integer... progress) {
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
	}
}
