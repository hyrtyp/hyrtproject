package com.hyrt.cei.adapter;

import java.util.List;
import java.util.Map;

import com.hyrt.cei.adapter.LawAdapter.ViewHolder;
import com.hyrt.foshanLaw.R;
import com.hyrt.foshanLaw.e.Law;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LawDetailAdapter extends BaseAdapter {
	private Context mContext;
	private List<Map<String, String>> data;
	LayoutInflater inflater;
	Law law;
	TextView tv_contetn;

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	public LawDetailAdapter(Context mContext, List<Map<String, String>> data) {
		this.mContext = mContext;
		this.data = data;
		this.inflater = LayoutInflater.from(mContext);
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return data.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ViewHolder holder;
		//View view=inflater.inflate(R.layout.feng_list_hastitle, null);
		//tv_contetn=(TextView)view.findViewById(R.id.flfgnr);
		if (arg1 == null) {
			arg1 = inflater.inflate(R.layout.entinfo_item, null);
			holder = new ViewHolder();
			holder.name = (TextView) arg1.findViewById(R.id.number);
			holder.content = (TextView) arg1.findViewById(R.id.name);
			arg1.setTag(holder);
		} else {
			holder = (ViewHolder) arg1.getTag();
		}
		holder.name.setText(data.get(arg0).get("somekey"));
		holder.content.setText(data.get(arg0).get("value"));
		return arg1;
	}

	class ViewHolder {
		TextView name, content;

	}

}
