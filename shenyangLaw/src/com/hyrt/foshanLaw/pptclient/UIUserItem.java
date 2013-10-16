/**
 * 
 */
package com.hyrt.foshanLaw.pptclient;

import java.util.ArrayList;
import java.util.List;

import com.hyrt.foshanLaw.R;
import com.hyrt.foshanLaw.pptclient.common.GlobalFunction;
import com.hyrt.foshanLaw.pptclient.dao.SessionUserItem;
import com.hyrt.foshanLaw.pptclient.dao.UserItem;
import com.hyrt.foshanLaw.pptclient.db.business.CacheGroupFunction;
import com.hyrt.foshanLaw.pptclient.db.business.SessionGroupFunction;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Description:用户界面生成
 * 
 * @author 郑伟
 * @Date 2013-1-10
 * @Copyright:2013-1-10
 * @Company:华源润通（北京）科技有限公司重庆分公司
 */
public class UIUserItem {
	UserItem obj;
	CheckBox cbox;

	/**
	 * @param _userid
	 * @param _username
	 * @param _phone
	 * @param _gid
	 */
	public UIUserItem(UserItem _obj) {
		obj = _obj;
	}

	/**
	 * @param context
	 * @param uid
	 *            当前用户的id
	 * @param username
	 *            当前用户的名字
	 * @param level
	 * @return
	 */
	public View getView(final Context context, final String uid,
			final String username, int level) {
		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = mInflater.inflate(R.layout.useritem, null);
		int paddingleft = 20 * level;
		v.setPadding(paddingleft, 0, 0, 0);
		boolean isme = false;
		if (obj.getUserid().equals(uid)) {
			isme = true;
		}
		cbox = (CheckBox) v.findViewById(R.id.cbox);
		cbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				obj.setSelect(isChecked);
			}
		});

		TextView tvname = (TextView) v.findViewById(R.id.tvname);
		tvname.setText(obj.getUsername());

		ImageView imgwav = (ImageView) v.findViewById(R.id.imgwav);
		imgwav.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 保存
				// 无论多人还是单人，共用一个sessionid
				// sessionname,本地保存对方的名称，发送自己的名称
				String sid = GlobalFunction.GetUID();
				String sname = obj.getUsername() + "、" + username + "的对讲";

				SessionGroupFunction sgfun = new SessionGroupFunction(context);

				SessionUserItem ob = new SessionUserItem();
				ob.setUserid(obj.getUserid());
				ob.setUsername(obj.getUsername());
				List<SessionUserItem> l = new ArrayList<SessionUserItem>();
				l.add(ob);

				String tp = sgfun.checkSessionid(uid, l);
				if (tp.equals("") == true) {
					sgfun.add(uid, sid, sname, GlobalFunction.GetDateTime(),
							false);
					CacheGroupFunction cgfun = new CacheGroupFunction(context);
					cgfun.del(uid, sid);
					cgfun.add(uid, sid, obj.getUserid());
				} else {
					sid = tp;
				}

				// 弹出会话窗体
				Intent showDialog = new Intent(context, DialogActivity.class);
				showDialog.putExtra("sessionid", sid);
				showDialog.putExtra("sessionname", sname);
				showDialog.putExtra("isgroup", false);
				showDialog.putExtra("iscreate", true);
				ArrayList<String> receobj = new ArrayList<String>();
				receobj.add(obj.getUserid()); // 添加选择项的userid
				showDialog.putStringArrayListExtra("receobj", receobj);
				context.startActivity(showDialog);
			}
		});

		ImageView imgcall = (ImageView) v.findViewById(R.id.imgcall);
		imgcall.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 播打电话
				if ("".equals(obj.getPhone()) == false) {
					String data = "tel:" + obj.getPhone();
					Intent ie = new Intent();
					ie.setAction(Intent.ACTION_DIAL);
					ie.setData(Uri.parse(data));
					context.startActivity(ie);
				}
			}

		});

		if (isme) {
			// 自己所在的项不参与任何操作
			imgwav.setVisibility(View.INVISIBLE);
			imgcall.setVisibility(View.INVISIBLE);
			cbox.setVisibility(View.INVISIBLE);
		}

		return v;
	}

}
