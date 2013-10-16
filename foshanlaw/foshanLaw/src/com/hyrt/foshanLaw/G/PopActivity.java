package com.hyrt.foshanLaw.G;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hyrt.cei.webservice.service.Service;
import com.hyrt.foshanLaw.R;
import com.hyrt.mwpm.vo.MwpmBussPType;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

public class PopActivity extends Activity {
	private ListView list;
	private Button next;
	private List<MwpmBussPType> lists;
	private List<String> typesname=new ArrayList<String>();
	private List<String> typesid=new ArrayList<String>();
    private Handler handler=new Handler(){
    	@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			PTypeAdapter adapter=new PTypeAdapter(PopActivity.this, lists);
			list.setAdapter(adapter);
			list.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					MwpmBussPType type=(MwpmBussPType) arg0.getAdapter().getItem(arg2);
					CheckBox box=(CheckBox) arg1.findViewById(R.id.checkbox);
					if(box.isChecked()){
						box.toggle();
						typesname.remove(type.getPtypename());
						typesid.remove(type.getId());
					}else{
						box.toggle();
						typesname.add(type.getPtypename());
						typesid.add(type.getId());
					}
				}
			});
    	}
    };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.g_xcjl_pop);
		initData();
		initView();
	}

	private void initView() {
        list=(ListView) findViewById(R.id.xclx_list);
        next= (Button) findViewById(R.id.next);
        next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String ids="";
				String names="";
				for (String str : typesid) {
					ids+=str+",";
				}
				for (String str : typesname) {
					names+=str+",";
				}
				Intent data=new Intent();
				data.putExtra("ids", ids!=null&&!ids.equals("")?ids.substring(0, ids.length()-1):"");
				data.putExtra("names", names!=null&&!names.equals("")?names.substring(0, names.length()-1):"");
				PopActivity.this.setResult(RESULT_OK, data);
				PopActivity.this.finish();
			}
		});
	}
	private void initData(){
		new Thread(){

			@Override
			public void run() {
				String str=Service.queryPType();
				lists=new ArrayList<MwpmBussPType>();
				if(!str.equals("")){
					try {
						JSONArray array=new JSONArray(str);
						for (int i = 0; i < array.length(); i++) {
							MwpmBussPType pType=new MwpmBussPType();
							JSONObject temp = (JSONObject) array.get(i);
							String id = temp.getString("id");
							pType.setId(id);
							String ptypename = temp.getString("ptypename");
							pType.setPtypename(ptypename);
							lists.add(pType);
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					handler.sendEmptyMessage(1);
				}
			}
			
		}.start();
	}
}
