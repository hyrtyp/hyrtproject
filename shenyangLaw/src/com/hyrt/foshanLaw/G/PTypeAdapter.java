package com.hyrt.foshanLaw.G;

import java.util.List;
import java.util.zip.Inflater;

import com.hyrt.foshanLaw.R;
import com.hyrt.mwpm.vo.MwpmBussEntinfo;
import com.hyrt.mwpm.vo.MwpmBussPType;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class PTypeAdapter extends BaseAdapter {
	private List<MwpmBussPType> data;
	private Context context;
	private LayoutInflater  inflater;
	public PTypeAdapter(Context context,List<MwpmBussPType> data){
		this.context=context;
		this.data=data;
		inflater=LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
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
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView=inflater.inflate(R.layout.g_xclx_item, null);
		}
		TextView view=(TextView) convertView.findViewById(R.id.xclx_text);
		view.setText(data.get(position).getPtypename());
		return convertView;  
	}

	
}
