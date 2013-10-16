package com.hyrt.cei.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hyrt.foshanLaw.R;
import com.hyrt.foshanLaw.e.Law;

public class LawAdapter extends BaseAdapter {
	private Context mContext;
	private List<Law> data;
	LayoutInflater inflater;

	public LawAdapter(Context mContext, List<Law> data) {
		super();
		this.mContext = mContext;
		this.data = data;
		this.inflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {

		return data.size();
	}

	@Override
	public Object getItem(int arg0) {

		return data.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {

		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ViewHolder holder;
		if (arg1 == null) {
			arg1 = inflater.inflate(R.layout.entinfo_item, null);
			holder = new ViewHolder();
			holder.tv_num = (TextView) arg1.findViewById(R.id.number);
			holder.tv_name = (TextView) arg1.findViewById(R.id.name);
			arg1.setTag(holder);
		} else {
			holder = (ViewHolder) arg1.getTag();
		}
		holder.tv_num.setText(data.get(arg0).getDm());
		holder.tv_name.setText(data.get(arg0).getBz());

		return arg1;
	}

	class ViewHolder {
		TextView tv_num;
		TextView tv_name;

	}

}
