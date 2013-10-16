package com.hyrt.cei.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hyrt.foshanLaw.R;
import com.hyrt.foshanLaw.b.EntInfoListActivity;
import com.hyrt.foshanLaw.remoteService.StockQuoteService;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CommonListAdapter extends BaseAdapter {
	private JSONArray entinfoDatas;
	private LayoutInflater inflater;
	public int CURRENT_FLAG;
	private Context context;
	private String format = "yyyy-MM-dd";
	private StringBuilder reseaName;

	public CommonListAdapter(Context context, JSONArray EntinfoDatas,
			int CURRENT_FLAG) {
		this.entinfoDatas = EntinfoDatas;
		inflater = LayoutInflater.from(context);
		this.context = context;
		this.CURRENT_FLAG = CURRENT_FLAG;
	}
	
	public CommonListAdapter(Context context, JSONArray EntinfoDatas,
			int CURRENT_FLAG,StringBuilder reseaName) {
		this.entinfoDatas = EntinfoDatas;
		inflater = LayoutInflater.from(context);
		this.context = context;
		this.CURRENT_FLAG = CURRENT_FLAG;
		this.reseaName = reseaName;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (CURRENT_FLAG == -1)
			return entinfoDatas.length() - 1;
		else
			return entinfoDatas.length();
	}

	@Override
	public Object getItem(int arg0) {
		try {
			return entinfoDatas.get(arg0);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = inflater.inflate(R.layout.entinfo_item, null);
		TextView number = (TextView) convertView.findViewById(R.id.number);
		TextView name = (TextView) convertView.findViewById(R.id.name);
		try {
			switch (CURRENT_FLAG) {
			case EntInfoListActivity.ENTINFOLIST_FLAG:
				number.setText(entinfoDatas.getJSONObject(position)
						.getString("ENROL").equals("null") ? "" : entinfoDatas
						.getJSONObject(position).getString("ENROL"));
				name.setText(entinfoDatas.getJSONObject(position)
						.getString("NAME").equals("null") ? "" : entinfoDatas
						.getJSONObject(position).getString("NAME"));
				break;
			case EntInfoListActivity.ENTERPRISERECORDLIST_FLAG:
				number.setText(entinfoDatas.getJSONObject(position)
						.getString("ENROL").equals("null") ? "" : entinfoDatas
						.getJSONObject(position).getString("ENROL"));
				name.setText(entinfoDatas.getJSONObject(position)
						.getString("NAME").equals("null") ? "" : entinfoDatas
						.getJSONObject(position).getString("NAME"));
				break;
			case EntInfoListActivity.ENTINFOLINELIST_FLAG:
				number.setText(entinfoDatas.getJSONObject(position)
						.getString("ENROL").equals("null") ? "" : entinfoDatas
						.getJSONObject(position).getString("ENROL"));
				name.setText(entinfoDatas.getJSONObject(position)
						.getString("NAME").equals("null") ? "" : entinfoDatas
						.getJSONObject(position).getString("NAME"));
				break;
			case EntInfoListActivity.TASKLIST_ENTINFO_FLAG:
				number.setText(entinfoDatas.getJSONObject(position)
						.getString("ENROL").equals("null") ? "" : entinfoDatas
						.getJSONObject(position).getString("ENROL"));
				name.setText(entinfoDatas.getJSONObject(position)
						.getString("NAME").equals("null") ? "" : entinfoDatas
						.getJSONObject(position).getString("NAME"));
				break;
			case EntInfoListActivity.TASKLIST_FLAG:
				number.setText(entinfoDatas.getJSONObject(position)
						.getString("NAME").equals("null") ? "" : entinfoDatas
						.getJSONObject(position).getString("NAME"));
				name.setText(entinfoDatas.getJSONObject(position)
						.getString("CRASH").equals("null") ? "" : entinfoDatas
						.getJSONObject(position).getString("CRASH"));
				if (StockQuoteService.PIDS != null
						&& StockQuoteService.PIDS.contains(entinfoDatas
								.getJSONObject(position).getString("ID")))
					number.setTextColor(Color.YELLOW);
				break;
			case EntInfoListActivity.NOTICELIST_FLAG:
				number.setText(entinfoDatas.getJSONObject(position)
						.getString("title").equals("null") ? "" : entinfoDatas
						.getJSONObject(position).getString("title"));
				name.setText(entinfoDatas.getJSONObject(position)
						.getString("createtime").equals("null") ? ""
						: entinfoDatas.getJSONObject(position).getString(
								"createtime"));
				break;
			case EntInfoListActivity.NOCARDLIST_FLAG:
				number.setText(entinfoDatas.getJSONObject(position)
						.getString("title").equals("null") ? "" : entinfoDatas
						.getJSONObject(position).getString("title"));
				name.setText(entinfoDatas.getJSONObject(position)
						.getString("result").equals("null") ? "" : entinfoDatas
						.getJSONObject(position).getString("result"));
				break;
			case EntInfoListActivity.ERRORENT_FLAG:
				number.setText(entinfoDatas.getJSONObject(position)
						.getString("ENROL").equals("null") ? "" : entinfoDatas
						.getJSONObject(position).getString("ENROL"));
				name.setText(entinfoDatas.getJSONObject(position)
						.getString("NAME").equals("null") ? "" : entinfoDatas
						.getJSONObject(position).getString("NAME"));
				break;
			case EntInfoListActivity.COMMUSERLIST_FLAG:
				try{
				number.setText(entinfoDatas.getJSONObject(position)
						.getString("NAME").equals("null") ? "" : entinfoDatas
						.getJSONObject(position).getString("NAME"));
				name.setText(entinfoDatas.getJSONObject(position)
						.getString("PHONE").equals("null") ? "" : entinfoDatas
						.getJSONObject(position).getString("PHONE"));
				}catch (JSONException e) {
					e.printStackTrace();
					number.setText(entinfoDatas.getJSONObject(position)
							.getString("name").equals("null") ? "" : entinfoDatas
							.getJSONObject(position).getString("name"));
					name.setText(entinfoDatas.getJSONObject(position)
							.getString("phone").equals("null") ? "" : entinfoDatas
							.getJSONObject(position).getString("phone"));
				}
				break;
			default:
				number.setText(entinfoDatas.getJSONObject(position)
						.getString("title").equals("null") ? "" : entinfoDatas
						.getJSONObject(position).getString("title"));
				if (entinfoDatas.getJSONObject(position).getString("content") != null
						&& entinfoDatas.getJSONObject(position)
								.getString("content").contains("time")){
					name.setText(new SimpleDateFormat(format).format(new Date(Long.parseLong(new JSONObject(entinfoDatas
							.getJSONObject(position).getString("content"))
							.getString("time")))));
				}else{
					if(number.getText().toString().contains("网格") && reseaName != null)
						name.setText(reseaName.toString());
					else
						name.setText(entinfoDatas.getJSONObject(position)
							.getString("content").equals("null") ? ""
							: entinfoDatas.getJSONObject(position).getString(
									"content"));
				}
				// 如果内容过长的话则用一个textview显示
				if (entinfoDatas.getJSONObject(position).getString("content") != null
						&& entinfoDatas.getJSONObject(position)
								.getString("content").length() > 300) {
					convertView = new TextView(context);
					((TextView) convertView).setTextSize(20);
					((TextView) convertView).setBackgroundColor(Color.WHITE);
					((TextView) convertView).setTextColor(Color.BLACK);
					((TextView) convertView)
							.setText(number.getText().toString() + "\n     "
									+ name.getText().toString());
				}
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return convertView;
	}

}
