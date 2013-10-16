/**
 * 
 */
package com.hyrt.foshanLaw.pptclient.business;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.hyrt.foshanLaw.pptclient.common.CommKey;
import com.hyrt.foshanLaw.pptclient.common.GlobalFunction;
import com.hyrt.foshanLaw.pptclient.db.business.CacheDataFunction;
import com.hyrt.foshanLaw.pptclient.db.business.CacheGroupFunction;
import com.hyrt.foshanLaw.pptclient.db.business.SessionGroupFunction;
import com.hyrt.foshanLaw.pptclient.db.business.SessionItemFunction;
import com.hyrt.foshanLaw.pptclient.db.dao.SessionItem;

import android.content.Context;
import android.content.Intent;

/**
 * Description:json串解析执行类
 * 
 * @author 郑伟
 * @Date 2013-1-8
 * @Copyright:2013-1-8
 * @Company:华源润通（北京）科技有限公司重庆分公司
 */
public class CmdExcute {

	public static void run(Context context, String userid, String json)
			throws JSONException {
		JSONObject object;
		String cmd;

		object = (JSONObject) new JSONTokener(json).nextValue();
		cmd = object.getString("cmd");
		if (cmd.equals("info")) {
			// 会话列表信息
			// 写入cachegroup
			String sessionid = object.getString("sessionid");
			CacheGroupFunction cfun = new CacheGroupFunction(context);
			List<String> re = new ArrayList<String>();
			JSONArray infoarr = object.getJSONArray("receobj");
			// 防止异常
			if (infoarr == null || infoarr.length() == 0)
				return;
			for (int infoi = 0; infoi < infoarr.length(); infoi++) {
				String tmp = infoarr.getString(infoi);
				if (tmp.equals(userid) == false)
					// 不添加自己
					re.add(tmp);
			}

			cfun.add(userid, sessionid, re);
			// 通知界面加载明细表
			Intent ie = new Intent();
			ie.setAction(CommKey.ui_receiver);
			ie.putExtra("type", CommKey.type_sessioninfo);
			ie.putExtra("sessionid", sessionid);
			GlobalFunction.sendPendingBroadcast(context, ie, 2);

			// 发送通知

			SessionGroupFunction sgfun = new SessionGroupFunction(context);
			String sessionname = sgfun.getSessionName(userid, sessionid);
			GlobalFunction.showNotifa(context, sessionid, sessionname, false,
					(ArrayList<String>) re);

		} else if (cmd.equals("receive")) {
			// 会话来了,先反馈
			SessionItemFunction sfun = new SessionItemFunction(context);
			String tp = object.getString("stamp");
			String sessionid = object.getString("sessionid");
			String sessionname = object.getString("sessionname");
			long rid;
			try{
				rid=Long.parseLong(tp);
			}
			catch(NumberFormatException	ex){
				rid=System.currentTimeMillis();
			}
			String rtmsg = CmdStr.getReturnMsg("receive", userid, tp);
			Intent bkintent = new Intent();
			bkintent.setAction(CommKey.svr_receiver);
			bkintent.putExtra("type", CommKey.type_send);
			bkintent.putExtra("sessionid", rtmsg);
			GlobalFunction.sendPendingBroadcast(context, bkintent, 1);
			
			if(sfun.isExsit(rid, sessionid)){
				//存在相同的，不做处理
				return;
			}
			
			// 先分解，存放到本地数据库中
			
			SessionItem item = new SessionItem();
			// item.setFilepath(object.getString("path"));
			item.setFilepath(GlobalFunction.GetTmpPath(object
					.getString("filename")));
			item.setFtppath(object.getString("path"));
			item.setFuid(object.getString("fuid"));
			item.setSessionid(sessionid);
			item.setTm(object.getString("time"));
			item.setSecond(object.getString("second"));
			sfun.add(rid,userid, item);
			// 向界面发送会话信息
			Intent iesession = new Intent();
			iesession.setAction(CommKey.ui_receiver);
			iesession.putExtra("type", CommKey.type_new);
			iesession.putExtra("sessionid", sessionid);
			GlobalFunction.sendPendingBroadcast(context, iesession, 1);
			// 向界面发送单次会话信息 SessionItem
			Intent ieitem = new Intent();
			ieitem.setAction(CommKey.ui_receiver);
			ieitem.putExtra("type", CommKey.type_newitem);
			ieitem.putExtra("sessionid", sessionid);
			ieitem.putExtra("object", item);// 串行化object
			GlobalFunction.sendPendingBroadcast(context, ieitem, 1);
			// 判断会话成员是否存在
			SessionGroupFunction sgfun = new SessionGroupFunction(context);
		 
			// 如果没有会话人员缓存信息，发送广播，请求info串
			if (sgfun.isExist(userid, sessionid) == false) {
				// 生成会话主表
				if ("0".equals(object.getString("type"))) {
					// 不是分组
				 
					//取得成员信息，通知由info去处理
					sgfun.add(userid, sessionid, sessionname,
							object.getString("time"), false);
					Intent ie = new Intent();
					ie.setAction(CommKey.svr_receiver);
					ie.putExtra("type", CommKey.type_info);
					ie.putExtra("sessionid", sessionid);
					GlobalFunction.sendPendingBroadcast(context, ie, 1);
				} else {
					sgfun.add(userid, sessionid, sessionname,
							object.getString("time"), true);
					GlobalFunction.showNotifa(context, sessionid, sessionname,
							true, new ArrayList<String>());
				}

			} else {
				// 存在 这个组，更新
				sgfun.update(userid, sessionid, object.getString("time"));
				// 构造通知
				CacheGroupFunction cgfun = new CacheGroupFunction(context);
				ArrayList<String> receobj = cgfun.getUseridColl(userid,
						sessionid);
				GlobalFunction.showNotifa(context, sessionid, sessionname,
						sgfun.isGroup(userid, sessionid), receobj);
			}
		}else if(cmd.equals("file")){
			//发送成功，取得时间戳，回发给界面
			//20130521  交由Servic中处理
//			String id=object.getString("stamp");
//			Intent iesession = new Intent();
//			iesession.setAction(CommKey.ui_receiver);
//			iesession.putExtra("type", CommKey.type_returnmsg);
//			iesession.putExtra("id", id);
//			GlobalFunction.sendPendingBroadcast(context, iesession, 1);
		}
	}

	/**
	 * 删除历史记录
	 * 
	 * @param context
	 * @param id
	 */
	public static void DelCacheData(Context context, String id) {
		CacheDataFunction fun = new CacheDataFunction(context);
		fun.del(id);
	}

}
