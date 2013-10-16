package com.hyrt.foshanLaw.G;

import java.util.List;
import java.util.zip.Inflater;

import com.hyrt.foshanLaw.R;
import com.hyrt.mwpm.vo.MwpmBussEntinfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class EntinfoAdapter extends BaseAdapter {
	private List<MwpmBussEntinfo> entinfoData;
	private Context context;
	private LayoutInflater  inflater;
	public EntinfoAdapter(Context context,List<MwpmBussEntinfo> EntinfoData){
		this.context=context;
		this.entinfoData=EntinfoData;
		inflater=LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return entinfoData.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return entinfoData.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView=inflater.inflate(R.layout.entinfo_item, null);
		}
		TextView number=(TextView) convertView.findViewById(R.id.number);
		TextView name=(TextView) convertView.findViewById(R.id.name);
		number.setText(entinfoData.get(position).getEnrol());
		name.setText(entinfoData.get(position).getName());
		return convertView;
	}

	
}
