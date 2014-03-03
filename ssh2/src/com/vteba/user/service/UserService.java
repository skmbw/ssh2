package com.vteba.user.service;

import java.util.List;
import java.util.Map;

import com.vteba.tm.generic.Page;
import com.vteba.user.model.User;

/**
 * 用户Service接口。
 * @author yinlei
 * 2014-1-8 下午1:12:52
 */
public interface UserService {
	/**
	 * @param entity
	 * @return
	 * @see com.vteba.tm.generic.IGenericDao#save(java.lang.Object)
	 */
	public String save(User entity);

	/**
	 * @param hql
	 * @param values
	 * @return
	 * @see com.vteba.tm.hibernate.IHibernateGenericDao#getEntityListByHql(java.lang.String, java.lang.Object[])
	 */
	public List<User> getEntityListByHql(String hql, Object... values);

	/**
	 * @param entity
	 * @see com.vteba.tm.generic.IGenericDao#saveOrUpdate(java.lang.Object)
	 */
	public void saveOrUpdate(User entity);

	/**
	 * @param entity
	 * @see com.vteba.tm.generic.IGenericDao#update(java.lang.Object)
	 */
	public void update(User entity);

	/**
	 * @param id
	 * @return
	 * @see com.vteba.tm.generic.IGenericDao#load(java.io.Serializable)
	 */
	public User load(String id);

	/**
	 * @param id
	 * @return
	 * @see com.vteba.tm.generic.IGenericDao#get(java.io.Serializable)
	 */
	public User get(String id);

	/**
	 * @param id
	 * @see com.vteba.tm.generic.IGenericDao#delete(java.io.Serializable)
	 */
	public void delete(String id);

	/**
	 * @param entity
	 * @see com.vteba.tm.generic.IGenericDao#delete(java.lang.Object)
	 */
	public void delete(User entity);

	/**
	 * @param entityClass
	 * @return
	 * @see com.vteba.tm.hibernate.IHibernateGenericDao#getAll(java.lang.Class)
	 */
	public <X> List<X> getAll(Class<X> entityClass);

	/**
	 * @param entityClass
	 * @param model
	 * @param objects
	 * @return
	 * @see com.vteba.tm.hibernate.IHibernateGenericDao#getListByPropertyEqual(java.lang.Class, java.lang.Object, java.lang.Object[])
	 */
	public <X> List<X> getListByPropertyEqual(Class<X> entityClass, X model, Object... objects);

	/**
	 * @param sql
	 * @param values
	 * @return
	 * @see com.vteba.tm.hibernate.IHibernateGenericDao#sqlQueryForObject(java.lang.String, java.lang.Object[])
	 */
	public List<Object[]> sqlQueryForObject(String sql, Object... values);


	/**
	 * @param hql
	 * @param namedQuery
	 * @param values
	 * @return
	 * @see com.vteba.tm.hibernate.IHibernateGenericDao#hqlQueryForObject(java.lang.String, boolean, java.lang.Object[])
	 */
	public List<Object[]> hqlQueryForObject(String hql, boolean namedQuery, Object... values);

	/**
	 * @param entityClass
	 * @param propertyName
	 * @param value
	 * @return
	 * @see com.vteba.tm.hibernate.IHibernateGenericDao#getUniqueResultByProperty(java.lang.Class, java.lang.String, java.lang.Object)
	 */
	public <X> X getUniqueResultByProperty(Class<X> entityClass, String propertyName, Object value);

	/**
	 * @param entityClass
	 * @param params
	 * @return
	 * @see com.vteba.tm.hibernate.IHibernateGenericDao#getUniqueResultByProperty(java.lang.Class, java.util.Map)
	 */
	public <X> X getUniqueResultByProperty(Class<X> entityClass, Map<String, Object> params);

	/**
	 * @param hql
	 * @param namedQuery
	 * @param values
	 * @return
	 * @see com.vteba.tm.hibernate.IHibernateGenericDao#uniqueResultByHql(java.lang.String, boolean, java.lang.Object[])
	 */
	public User uniqueResultByHql(String hql, boolean namedQuery, Object... values);

	/**
	 * @param hql
	 * @param namedQuery
	 * @param values
	 * @return
	 * @see com.vteba.tm.hibernate.IHibernateGenericDao#executeUpdateByHql(java.lang.String, boolean, java.lang.Object[])
	 */
	public int executeHqlUpdate(String hql, boolean namedQuery, Object... values);

	/**
	 * @param sql
	 * @param values
	 * @return
	 * @see com.vteba.tm.hibernate.IHibernateGenericDao#executeUpdateBySql(java.lang.String, java.lang.Object[])
	 */
	public int executeSqlUpdate(String sql, Object... values);

	/**
	 * @param page
	 * @param entity
	 * @return
	 * @see com.vteba.tm.hibernate.IHibernateGenericDao#queryForPageByModel(com.vteba.tm.generic.Page, java.lang.Object)
	 */
	public Page<User> queryForPageByModel(Page<User> page, User entity);

	/**
	 * @param page
	 * @param hql
	 * @param values
	 * @return
	 * @see com.vteba.tm.hibernate.IHibernateGenericDao#queryForPageByHql(com.vteba.tm.generic.Page, java.lang.String, java.lang.Object[])
	 */
	public Page<User> queryForPageByHql(Page<User> page, String hql, Object... values);

}
