package com.hyrt.foshanLaw.G;

import java.util.List;
import java.util.zip.Inflater;

import com.hyrt.foshanLaw.R;
import com.hyrt.mwpm.vo.MwpmBussEntinfo;
import com.hyrt.mwpm.vo.MwpmBussPatrolItem;
import com.hyrt.mwpm.vo.MwpmBussPatrolLog;
import com.hyrt.mwpm.vo.MwpmBussReturnlog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class PatrolItemAdapter extends BaseAdapter {
	private List<MwpmBussPatrolItem> returnlogData;
	private Context context;
	private LayoutInflater  inflater;
	public PatrolItemAdapter(Context context,List<MwpmBussPatrolItem> returnlogData){
		this.context=context;
		this.returnlogData=returnlogData;
		inflater=LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return returnlogData.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return returnlogData.get(arg0);
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
		name.setText(returnlogData.get(position).getQid());
		number.setText(returnlogData.get(position).getContentid().toString());
		return convertView;
	}

	
}
