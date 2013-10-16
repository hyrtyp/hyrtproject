package com.hyrt.mwpm.base;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.hyrt.mwpm.util.PageBean;

public class BaseDao extends HibernateDaoSupport {

	public List executeHQLParam(String str, List sqlParams) {
		// List list=new ArrayList();
		List listcount = new ArrayList();
		try {
			if (sqlParams != null && sqlParams.size() > 0) {
				listcount = getHibernateTemplate().find(str,
						sqlParams.toArray());
			} else {
				listcount = getHibernateTemplate().find(str);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listcount;
	}

	public List executeHQL(String str) {
		List listcount = executeHQLParam(str, null);

		return listcount;
	}

	/**
	 * ����ID��ȡ����. ʵ�ʵ���Hibernate��session.load()��������ʵ�����proxy����. ������󲻴��ڣ��׳��쳣.
	 */
	public Object findByPK(Object entityClass, String id) {
		return getHibernateTemplate().get(entityClass.getClass(), id);
	}

	/**
	 * ��ȡȫ������.
	 */
	public List getAll(Object entityClass) {
		return getHibernateTemplate().loadAll(entityClass.getClass());
	}

	/**
	 * �������.
	 */
	public void saveObject(Object o) {
		getHibernateTemplate().saveOrUpdate(o);
	}
	
	public Object saveObjectRePK(Object o){
		return getHibernateTemplate().save(o);
	}

	/**
	 * �������.
	 */
	public void insert(Object o) {
		getHibernateTemplate().save(o);
	}

	public void update(String id, Object o) {
		getHibernateTemplate().update(id, o);
	}

	/**
	 * ɾ������.
	 */
	public void remove(Object o) {
		getHibernateTemplate().delete(o);
	}

	/**
	 * ɾ����������.
	 */
	public void removeAllList(List listObject) {
		getHibernateTemplate().deleteAll(listObject);
	}

	/**
	 * ����IDɾ������.
	 */
	public void removeById(Object entityClass, String id) {
		remove(findByPK(entityClass, id));
	}

	public void flush() {
		getHibernateTemplate().flush();
	}

	public void clear() {
		getHibernateTemplate().clear();
	}

	/**
	 * 
	 * 
	 * @param values
	 *            �ɱ����,��{@link #createQuery(String,Object...)}
	 */
	public List findByVO(Object values) {

		return getHibernateTemplate().findByExample(values);
	}

	/**
	 * @param hql
	 *            HSQL ��ѯ���
	 * @param ��ҳ��Ϣ
	 * @return List �����
	 */
	public List getPageFind(final String hql, final PageBean pageBean) {
		List list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				query.setFirstResult((pageBean.getPageNo() - 1)
						* pageBean.getPageSize());
				query.setMaxResults(pageBean.getPageSize());
				List list = query.list();
				return list;
			}
		});
		return list;
	}

	/**
	 * @param hql
	 *            HSQL ��ѯ���---����native sql
	 * @param ��ҳ��Ϣ
	 * @return List �����
	 */
	public List getPageFindByNative(final String hql, final PageBean pageBean) {
		List list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				// String hql =
				// "SELECT  Id, WorkId, TaskId, ValueTitle FROM         OA_View_ReleaseList WHERE  UserName = 'admin'";
				Query query = session.createSQLQuery(hql).setResultTransformer(
						Transformers.ALIAS_TO_ENTITY_MAP);
				;
				query.setFirstResult((pageBean.getPageNo() - 1)
						* pageBean.getPageSize());
				query.setMaxResults(pageBean.getPageSize());
				List list = query.list();
				return list;
			}
		});
		return list;
	}

	/**
	 * ʹ��hql �����в���
	 * 
	 * @param hql_count
	 *            HSQL ��ѯ���
	 * @return int ����ͳ������
	 */
	public int getRowcount(String hql_count) {
		int rowcount = 0;
		List lis = new ArrayList();
		lis = getHibernateTemplate().find(hql_count);
		Long count = new Long(0);
		if (lis != null && lis.size() > 0) {
			count = (Long) lis.get(0);
		}
		rowcount = count.intValue();

		return rowcount;
	}

	/**
	 * ʹ��native sql �����в���
	 * 
	 * @param hql_count
	 *            HSQL ��ѯ���
	 * @return int ����ͳ������
	 */
	public int getRowcountByNativeSql(final String hql_count) {
		List lis = new ArrayList();
		int rowcount = 0;
		Session session = this.getSession();
		try {
			Query query = session.createSQLQuery(hql_count)
					.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List list = query.list();

			String count = ((HashMap) list.get(0)).get("id").toString();
			rowcount = Integer.parseInt(count);
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
		return rowcount;

	}

	public void bulkUpdateBase(String hql) {
		this.getHibernateTemplate().bulkUpdate(hql);
	}

	public List executeHQLParam(String str) {
		// List list=new ArrayList();
		List listcount = new ArrayList();
		listcount = getHibernateTemplate().find(str);
		return listcount;
	}

	public void saveOrupdate(Object obj) {
		this.getHibernateTemplate().saveOrUpdate(obj);
	}

	public void closeSession() {
		Session session = this.getSession();
		if (session != null) {
			session.close();
		}
	}
}
