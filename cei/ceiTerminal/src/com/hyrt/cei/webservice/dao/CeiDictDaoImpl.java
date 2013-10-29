package com.hyrt.cei.webservice.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import com.hyrt.mwpm.base.BaseDao;
import com.hyrt.mwpm.util.PageBean;

public class CeiDictDaoImpl extends BaseDao implements CeiDictDao {

	private static Logger log = Logger.getLogger(CeiDictDaoImpl.class);

	/**
	 * 根据hql条件，进行分页查询，显示数据，返回List
	 * 
	 * @param hql
	 * @param pageBean
	 * @param hqlcount
	 * @return
	 */
	@Override
	public List getResultData(String hql, PageBean pageBean, String hqlcount) {
		int allPage = 0;
		List dataList = new ArrayList();
		try {
			int pageNo = pageBean.getPageNo();
			// 当执行的hqlcount有值，且当前页数为第一页时，查询总页数
			if (hqlcount.length() > 0 && pageNo == 1) {
				allPage = getRowcountByNativeSql(hqlcount);
				// allPage = getRowcount(hqlcount);
				pageBean.setRowCount(allPage);
			}
			dataList = this.getPageFindByNative(hql, pageBean);
			// dataList = this.getPageFind(hql, pageBean);
			this.closeSession();
		} catch (Exception e) {
			log.error("EntinfoDaoImpl类中：getResultData", e);
			e.printStackTrace();
		}

		return dataList;
	}

	// 通过登陆名和密码取得用户相关信息
	public List getALLData(String hql) {
		List dataList = new ArrayList();
		dataList = executeHQL(hql);
		return dataList;
	}

	@Override
	public List getInfo(String sql) {
		return this.getListByNativeSql(sql);
	}

	public List getListByNativeSql(final String sql) {
		List lis = new ArrayList();
		Session session = this.getSession();
		List list = null;
		try {
			Query query = session.createSQLQuery(sql).setResultTransformer(
					Transformers.ALIAS_TO_ENTITY_MAP);
			list = query.list();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			session.flush();
			session.clear();
			session.close();
			if (session != null) {
				System.out.println("!!!session is not null!!!!!!!!!111");
				session = null;
			}
		}

		// session.close();
		return list;

	}

	@Override
	public void saveObject(Object o) {
		this.getHibernateTemplate().save(o);

	}

	@Override
	public void updateXYInfo(String hql) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().bulkUpdate(hql);

	}

	@Override
	public void delObject(Object o) {
		this.getHibernateTemplate().delete(o);

	}

	@Override
	public void delObjectAll(List o) {
		this.getHibernateTemplate().deleteAll(o);
	}
	
	public List getPageBeanList(PageBean pageBean, String hql) {
		List ls = new ArrayList();
		try {
			ls = this.getPageFind(hql, pageBean);
		} catch (Exception e) {
			log.error("类名:" + this.getClass().getName() + ",方法名：getAddressBookList出现异常 ", e);
			e.printStackTrace();
		}

		return ls;
	}
	public int getRowcount(String hql_count){
		 return this.getRowcount(hql_count);
	}
}
