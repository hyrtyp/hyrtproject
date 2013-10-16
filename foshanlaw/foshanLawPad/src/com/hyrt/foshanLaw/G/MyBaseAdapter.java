package com.hyrt.foshanLaw.G;

import java.util.List;
import java.util.zip.Inflater;

import com.hyrt.foshanLaw.R;
import com.hyrt.mwpm.vo.MwpmBussEntinfo;
import com.hyrt.mwpm.vo.MwpmSysDictionary;
import com.hyrt.mwpm.vo.MwpmSysUserinfo;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;


public class MyBaseAdapter extends BaseAdapter {

	private List<?> data;//MwpmSysUserinfo MwpmSysDictionary
	private Context context;
	private LayoutInflater  inflater;
	public MyBaseAdapter(Context context,List<?> data){
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
		//TextView number=new TextView(context);
		CheckedTextView number=new CheckedTextView(context);
		number.setTextColor(Color.BLACK);
		number.setPadding(10, 10,10, 10);
		if(data.get(position) instanceof MwpmSysUserinfo){
		    number.setText(((MwpmSysUserinfo)(data.get(position))).getName());
		}else if(data.get(position) instanceof MwpmSysDictionary){
			number.setText(((MwpmSysDictionary)(data.get(position))).getDataname());
		}
		return number;
	}

	
}
