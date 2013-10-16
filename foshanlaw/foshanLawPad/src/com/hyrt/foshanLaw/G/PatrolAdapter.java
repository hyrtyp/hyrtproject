package com.hyrt.foshanLaw.G;

import java.util.List;
import java.util.zip.Inflater;

import com.hyrt.cei.util.StringUtil;
import com.hyrt.foshanLaw.R;
import com.hyrt.mwpm.vo.MwpmBussEntinfo;
import com.hyrt.mwpm.vo.MwpmBussPatrolLog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class PatrolAdapter extends BaseAdapter {
	private List<MwpmBussPatrolLog> patrolLogData;
	private Context context;
	private LayoutInflater  inflater;
	public PatrolAdapter(Context context,List<MwpmBussPatrolLog> patrolLogData){
		this.context=context;
		this.patrolLogData=patrolLogData;
		inflater=LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return patrolLogData.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return patrolLogData.get(arg0);
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
		number.setText(StringUtil.DateToStr(patrolLogData.get(position).getClock()));
		name.setText(StringUtil.DateToStr(patrolLogData.get(position).getTerm()));
		return convertView;
	}

	
}
