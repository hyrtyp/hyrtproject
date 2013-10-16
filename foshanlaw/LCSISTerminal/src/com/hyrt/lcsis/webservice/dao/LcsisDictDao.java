package com.hyrt.lcsis.webservice.dao;

import java.util.List;

import com.hyrt.mwpm.util.PageBean;
import com.hyrt.mwpm.vo.MwpmBussEntinfo;

public interface LcsisDictDao {

	/**
	 * 根据hql条件，进行分页查询，显示数据，返回List
	 * 
	 * @param hql
	 * @param pageBean
	 * @param hqlcount
	 * @return
	 */
	List getResultData(String hql, PageBean pageBean, String hqlcount);

	public List getALLData(String hql);

	public List getInfo(String hql);

	/***
	 * 保存信息
	 * 
	 * @param
	 */
	public void saveObject(Object o);

	/**
	 * 根据ＨＱＬ，执行更新操作
	 * 
	 * @param hql
	 */
	public void updateXYInfo(String hql);

	void delObject(Object o);
	
	public Object saveObjectRePK(Object o);

	void delObjectAll(List o);
	public List getPageBeanList(PageBean pageBean, String hql) throws Exception;
	public int getRowcount(String hql_count);

	public List getListByNativeSql(String sql);

	public List getPhoneIpadByUserId(String userid);
	
	public List getPhoneIpadByDeviceId(String deviceid,String type);

	public List getRoleTypeByUserId(String userid);
	
}
