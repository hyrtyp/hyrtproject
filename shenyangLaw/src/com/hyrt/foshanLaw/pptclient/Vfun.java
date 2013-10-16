/**
 * 
 */
package com.hyrt.foshanLaw.pptclient;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.hyrt.foshanLaw.pptclient.common.WebService;
import com.hyrt.foshanLaw.pptclient.dao.GroupItem;
import com.hyrt.foshanLaw.pptclient.dao.SessionUserItem;
import com.hyrt.foshanLaw.pptclient.dao.UserItem;
import com.hyrt.foshanLaw.pptclient.db.business.CacheGroupFunction;
import com.hyrt.foshanLaw.pptclient.db.dao.CacheGroup;

import android.content.Context;

/**
 * Description:用户数据初始化、获取用户选择
 * 
 * @author 郑伟
 * @Date 2013-1-10
 * @Copyright:2013-1-10
 * @Company:华源润通（北京）科技有限公司重庆分公司
 */
public class Vfun {
	List<GroupItem> glist;
	List<UserItem> ulist;

	public List<GroupItem> getGlist(String gid) {
		List<GroupItem> lst = new ArrayList<GroupItem>();
		for (int i = 0; i < glist.size();) {
			if (glist.get(i).getPid().equals(gid)) {
				lst.add(glist.get(i).Clone());
				glist.remove(i);
			} else {
				i++;
			}
		}
		return lst;
	}

	/**
	 * 取得下级分组数量
	 * 
	 * @param gid
	 * @return
	 */
	public int getSubGroupCount(String gid) {
		int cnt = 0;
		for (GroupItem obj : glist) {
			if (obj.getPid().equals(gid)) {
				cnt++;
			}
		}
		return cnt;
	}

	/**
	 * 取得某分组下的所有用户
	 * @param gid  分组id
	 * @return
	 */
	public List<UserItem> getUlist(String gid) {
		List<UserItem> lst = new ArrayList<UserItem>();
		for (int i = 0; i < ulist.size(); i++) {
			if (ulist.get(i).getGid().equals(gid)) {
				lst.add(ulist.get(i));
				// ulist.remove(i);
			}
		}
		return lst;
	}

	/**
	 * 用户选择列表，即使同时存在不同分组的同一个用户，也进行了唯一选择
	 * @return
	 */
	public List<SessionUserItem> getSelectUseridList() {
		Map<String,String > map=new HashMap<String,String>();
		List<SessionUserItem> re = new ArrayList<SessionUserItem>();
		for (int i = 0; i < ulist.size(); i++) {
			if (ulist.get(i).isSelect() == true) {
				//re.add(ulist.get(i).Clone());
				map.put(ulist.get(i).getUserid(), ulist.get(i).getUsername());
			}
		}
		//从map中取
		Set<Entry<String, String>> entries =map.entrySet();
		if(entries != null) {
			Iterator<Entry<String,String>> iterator =entries.iterator() ;
			while(iterator.hasNext()) {
				Map.Entry<String,String> entry = iterator.next();
				SessionUserItem sui=new SessionUserItem();
				sui.setUserid(entry.getKey());
				sui.setUsername(entry.getValue());
				re.add(sui);
			}
		}
		return re;
	}
	
	
	/**
	 * 所有用户，过滤 
	 * @return
	 */
	public Map<String,String > getAllUseridList() {
		Map<String,String > map=new HashMap<String,String>();
		for (int i = 0; i < ulist.size(); i++) {
				map.put(ulist.get(i).getUserid(), ulist.get(i).getUsername());
		}
		return map;
	}

	public Vfun() {
		glist = new ArrayList<GroupItem>();
		ulist = new ArrayList<UserItem>();
	}

	public void run(Context context, String userid) throws Exception {
		glist.clear();
		ulist.clear();

		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(context.getResources().getAssets()
				.open("test.xml"));
		Element root = doc.getRootElement();
		List<?> gtmp = root.getChild("GROUP").getChildren();
		for (Iterator<?> iter = gtmp.iterator(); iter.hasNext();) {
			Element el = (Element) iter.next();
			String gid = el.getChild("GID").getText();
			String gname = el.getChild("GNAME").getText();
			String pid = el.getChild("PID").getText();
			GroupItem g = new GroupItem(gid, gname, pid);
			glist.add(g);
		}

		List<?> utmp = root.getChild("USER").getChildren();
		for (Iterator<?> iter = utmp.iterator(); iter.hasNext();) {
			Element el = (Element) iter.next();
			String uid = el.getChild("UID").getText();
			String uname = el.getChild("UNAME").getText();
			String gid = el.getChild("GID").getText();
			String phone = el.getChild("PHONE").getText();
			// if(userid.equals(uid)==false){
			// 排除自身
			UserItem u = new UserItem(uid, uname, phone, gid);
			ulist.add(u);
			// }
		}
	}

	/**
	 * 加载数据
	 * @param context
	 * @param userid
	 * @throws Exception
	 */
	public void runFromWS(Context context, String userid) throws Exception {
		glist.clear();
		ulist.clear();
		List<String> ugid=new ArrayList<String>();
		
		WebService ws = new WebService();
		String res = ws.Call(context);
		if (ws.isErr() == false) {

			StringReader in = new StringReader(res);
			SAXBuilder builder = new SAXBuilder();

			Document doc = builder.build(in);
			Element root = doc.getRootElement();
			List<?> gtmp = root.getChild("GROUP").getChildren();
			for (Iterator<?> iter = gtmp.iterator(); iter.hasNext();) {
				Element el = (Element) iter.next();
				String gid = el.getChild("GID").getText();
				String gname = el.getChild("GNAME").getText();
				String pid = el.getChild("PID").getText();
				GroupItem g = new GroupItem(gid, gname, pid);
				glist.add(g);
			}

			List<?> utmp = root.getChild("USER").getChildren();
			for (Iterator<?> iter = utmp.iterator(); iter.hasNext();) {
				Element el = (Element) iter.next();
				String uid = el.getChild("UID").getText();
				String uname = el.getChild("UNAME").getText();
				String gid = el.getChild("GID").getText();
				String phone = el.getChild("PHONE").getText();
				UserItem u = new UserItem(uid, uname, phone, gid);
				ulist.add(u);
				if(uid.equals(userid)){
					//记录当前用户所在的分组gid
					ugid.add(gid);
				}
			}
		}
		
		//当前用户访问组的设定
		for(int i=0;i<ugid.size();i++){
			String gid=ugid.get(i);
			while(gid.equals("-1")==false&&gid.equals("")==false){
				gid=setUserOwn(gid);
			}
		}
	}

	String setUserOwn(String gid){
		String re="-1";
		for(int i=0;i<glist.size();i++){
			if(glist.get(i).getGid().equals(gid)){
				glist.get(i).setIsown(true);
				re=glist.get(i).getPid();
				break;
			}
		}
		return re;
	}
	
	/**
	 * 取得某会话的用户列表
	 * @param context
	 * @param userid
	 * @param sessionid
	 * @return
	 */
	public List<SessionUserItem> getUserList(Context context, String userid, String sessionid) {
		CacheGroupFunction fun = new CacheGroupFunction(context);
		List<CacheGroup> lst = fun.getList(userid, sessionid);
		List<SessionUserItem> re = new ArrayList<SessionUserItem>();
		for(CacheGroup cg:lst){
			SessionUserItem tmp=new SessionUserItem();
			tmp.setUserid(cg.getUserid());
			tmp.setUsername(getName(cg.getUserid()));
			re.add(tmp);
		}
		return re;
	}
	
	/**
	 * 通过用户id取得用户名
	 * @param fuid
	 * @return
	 */
	String getName(String fuid){
		String s="未知";
		for(int i=0;i<ulist.size();i++){
			if(ulist.get(i).getUserid().equals(fuid)){
				s=ulist.get(i).getUsername();
				break;
			}
		}
		return s;
	}
}
