/**
 * 
 */
package com.hyrt.foshanLaw.pptclient;

import java.util.ArrayList;
import java.util.List;

import com.hyrt.foshanLaw.R;
import com.hyrt.foshanLaw.pptclient.common.GlobalFunction;
import com.hyrt.foshanLaw.pptclient.dao.GroupItem;
import com.hyrt.foshanLaw.pptclient.dao.UserItem;
import com.hyrt.foshanLaw.pptclient.db.business.SessionGroupFunction;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

/**
 * Description:分组界面生成
 * 
 * @author 郑伟
 * @Date 2013-1-10
 * @Copyright:2013-1-10
 * @Company:华源润通（北京）科技有限公司重庆分公司
 */
public class UIGroupItem {
	GroupItem obj;
	List<UserItem> ulst;
	LinearLayout llmain;
	LinearLayout llsub;
	String uid;
	String uname;

	public UIGroupItem(GroupItem _obj, List<UserItem> _ulst, String _uid,
			String _uname,int _level) {
		obj = _obj;
		ulst = _ulst;
		uid = _uid;
		uname = _uname;
	}

	public View getView(final Context context,int level) {
		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = mInflater.inflate(R.layout.groupitem, null);
		llmain = (LinearLayout) v.findViewById(R.id.llmain);
		llsub = (LinearLayout) v.findViewById(R.id.llsub);
		llsub.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		// llsub.setVisibility(View.GONE);
		TextView tvname = (TextView) v.findViewById(R.id.tvname);
		int paddingleft=(level-1)*20;
		tvname.setPadding(paddingleft, 0, 0, 0);
		tvname.setText(obj.getGname());
		tvname.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (llsub.getVisibility() == View.VISIBLE) {
					llsub.setVisibility(View.GONE);
				} else {
					llsub.setVisibility(View.VISIBLE);
				}
			}
		});
		
		Button btncall = (Button) v.findViewById(R.id.btncall);
		btncall.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 保存
				SessionGroupFunction sgfun = new SessionGroupFunction(context);
				// CacheGroupFunction cgfun=new CacheGroupFunction(context);
				if (sgfun.isExist(uid, obj.getGid()) == false) {
					//本地不存在就添加
					sgfun.add(uid, obj.getGid(), obj.getGname(),
							GlobalFunction.GetDateTime(),   true);
				}
				// 弹出会话窗体
				Intent showDialog = new Intent(context, DialogActivity.class);
				showDialog.putExtra("sessionid", obj.getGid());
				showDialog.putExtra("sessionname", obj.getGname());
				showDialog.putExtra("isgroup", true);
				showDialog.putExtra("iscreate", true);
				ArrayList<String> receobj = new ArrayList<String>();
				receobj.add("");//防异常
				showDialog.putStringArrayListExtra("receobj", receobj);
				context.startActivity(showDialog);
			}
		});
		if(obj.isOwn()==false){
			btncall.setVisibility(View.INVISIBLE);
		} 
		

		for (int i = 0; i < ulst.size(); i++) {
			UIUserItem uui = new UIUserItem(ulst.get(i));
			View vsub = uui.getView(context, uid,uname, level);
			llsub.addView(vsub);
		}
		return v;
	}

	public void addView(View v) {
		// llsub.setVisibility(View.VISIBLE);
		llsub.addView(v);

	}
}
