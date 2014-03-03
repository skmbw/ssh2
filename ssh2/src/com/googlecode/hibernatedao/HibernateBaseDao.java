package com.googlecode.hibernatedao;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Filter;
import org.hibernate.LockMode;
import org.hibernate.ReplicationMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;

import com.googlecode.hibernatedao.support.Pagination;
import com.googlecode.hibernatedao.support.PaginationRequest;

/**
 * 提供了常用增删改查(CRUD)功能的DAO基础接口
 * 
 * @author libinsong1204@gmail.com
 */
@SuppressWarnings("rawtypes")
public interface HibernateBaseDao<T, ID extends Serializable> {
	// -------------------------------------------------------------------------
	// Convenience methods for loading individual objects
	// -------------------------------------------------------------------------
	/**
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#get(Class, Serializable)
	 * @see org.springframework.orm.hibernate3.HibernateOperations#get(Class, Serializable)
	 */
	public T get(ID id);

	/**
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#get(Class, Serializable, LockMode)
	 * @see org.springframework.orm.hibernate3.HibernateOperations#get(Class, Serializable, LockMode)
	 */
	public T get(final ID id, final LockMode lockMode);

	/**
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#load(Class, Serializable)
	 * @see org.springframework.orm.hibernate3.HibernateOperations#load(Class, Serializable)
	 */
	public T load(ID id);

	/**
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#load(Class, Serializable, LockMode)
	 * @see org.springframework.orm.hibernate3.HibernateOperations#load(Class, Serializable, LockMode)
	 */
	public T load(final ID id, final LockMode lockMode);

	/**
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#loadAll(Class);
	 * @see org.springframework.orm.hibernate3.HibernateOperations#loadAll(Class)
	 */
	public List<T> loadAll();

	/**
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#load(Object, Serializable)
	 * @see org.springframework.orm.hibernate3.HibernateOperations#load(Object, Serializable)
	 */
	public void load(final T entity, final ID id);

	/**
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#refresh(Object)
	 * @see org.springframework.orm.hibernate3.HibernateOperations#refresh(Object)
	 */
	public void refresh(final T entity);

	/**
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#refresh(Object, LockMode)
	 * @see org.springframework.orm.hibernate3.HibernateOperations#refresh(Object, LockMode)
	 */
	public void refresh(final T entity, final LockMode lockMode);

	/**
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#contains(Object)
	 * @see org.springframework.orm.hibernate3.HibernateOperations#contains(Object)
	 */
	public boolean contains(final T entity);

	/**
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#evict(Object)
	 * @see org.springframework.orm.hibernate3.HibernateOperations#evict(Object)
	 */
	public void evict(final T entity);

	/**
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#initialize(Object)
	 * @see org.springframework.orm.hibernate3.HibernateOperations#initialize(Object)
	 */
	public void initialize(T proxy);

	/**
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#enableFilter(String)
	 * @see org.springframework.orm.hibernate3.HibernateOperations#enableFilter(String)
	 */
	public Filter enableFilter(String filterName);
	
	/**
	 * Get the identifier of entity
	 */
	public Serializable getIdentifierObject(T entity);

	// -------------------------------------------------------------------------
	// Convenience methods for storing individual objects
	// -------------------------------------------------------------------------

	/**
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#lock(Object, LockMode)
	 * @see org.springframework.orm.hibernate3.HibernateOperations#load(Object, Serializable)
	 */
	public void lock(final T entity, final LockMode lockMode);

	/**
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#save(Object)
	 * @see org.springframework.orm.hibernate3.HibernateOperations#save(Object)
	 */
	public ID save(final T entity);

	/**
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#update(Object)
	 * @see org.springframework.orm.hibernate3.HibernateOperations#update(Object)
	 */
	public void update(T entity);

	/**
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#update(Object, LockMode)
	 * @see org.springframework.orm.hibernate3.HibernateOperations#update(Object, LockMode)
	 */
	public void update(final T entity, final LockMode lockMode);

	/**
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#saveOrUpdate(Object)
	 * @see org.springframework.orm.hibernate3.HibernateOperations#saveOrUpdate(Object)
	 */
	public void saveOrUpdate(final T entity);

	/**
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#saveOrUpdateAll(Collection)
	 * @see org.springframework.orm.hibernate3.HibernateOperations#saveOrUpdateAll(Collection)
	 */
	public void saveOrUpdateAll(final Collection<T> entities);

	/**
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#replicate(Object, ReplicationMode)
	 * @see org.springframework.orm.hibernate3.HibernateOperations#replicate(Object, ReplicationMode)
	 */
	public void replicate(final T entity, final ReplicationMode replicationMode);

	/**
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#persist(Object)
	 * @see org.springframework.orm.hibernate3.HibernateOperations#persist(Object)
	 */
	public void persist(final T entity);

	/**
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#merge(Object)
	 * @see org.springframework.orm.hibernate3.HibernateOperations#merge(Object)
	 */
	public T merge(final T entity);

	/**
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#delete(Object)
	 * @see org.springframework.orm.hibernate3.HibernateOperations#delete(Object)
	 */
	public void delete(T entity);
	
	/**
	 * Delete the given identifier.
	 * @param entity the identifier to delete
	 * @throws org.springframework.dao.DataAccessException in case of Hibernate errors
	 * @see org.hibernate.Session#delete(Object)
	 */
	public T delete(ID id);

	/**
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#delete(Object, LockMode)
	 * @see org.springframework.orm.hibernate3.HibernateOperations#delete(Object, LockMode)
	 */
	public void delete(final T entity, final LockMode lockMode);

	/**
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#deleteAll(Collection)
	 * @see org.springframework.orm.hibernate3.HibernateOperations#deleteAll(Collection)
	 */
	public void deleteAll(final Collection<T> entities);

	/**
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#flush()
	 * @see org.springframework.orm.hibernate3.HibernateOperations#flush()
	 */
	public void flush();

	/**
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#clear()
	 * @see org.springframework.orm.hibernate3.HibernateOperations#clear()
	 */
	public void clear();

	// -------------------------------------------------------------------------
	// Convenience finder methods for HQL strings
	// -------------------------------------------------------------------------
	/**
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#find(String)
	 * @see org.springframework.orm.hibernate3.HibernateOperations#find(String)
	 */
	public List findByHQL(String queryString);

	/**
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#find(String, Object)
	 * @see org.springframework.orm.hibernate3.HibernateOperations#find(String, Object)
	 */
	public List findByHQL(String queryString, Object value);

	/**
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#find(String, Object...)
	 * @see org.springframework.orm.hibernate3.HibernateOperations#find(String, Object...)
	 */
	public List findByHQL(final String queryString, final Object... values);

	/**
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#findByNamedParam(String, String, Object)
	 * @see org.springframework.orm.hibernate3.HibernateOperations#findByNamedParam(String, String, Object)
	 */
	public List findByHQLNamedParam(String queryString, String paramName, Object value);

	/**
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#findByNamedParam(String, String[], Object[])
	 * @see org.springframework.orm.hibernate3.HibernateOperations#findByNamedParam(String, String[], Object[])
	 */
	public List findByHQLNamedParam(final String queryString, final String[] paramNames, final Object[] values);

	/**
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#findByValueBean(String, Object)
	 * @see org.springframework.orm.hibernate3.HibernateOperations#findByValueBean(String, Object)
	 */
	public List findByHQLValueBean(final String queryString, final Object valueBean);
	
	// -------------------------------------------------------------------------
	// Convenience finder methods for dynamic detached criteria
	// -------------------------------------------------------------------------
	
	/**
	 * Execute a query based on a dynamically created Hibernate criteria object. use parameters to build DetachedCriteria
	 * 
	 * @param propertyName the name of the parameter
	 * @param value the value of the parameter, value can be Criterion instance
	 * @return a {@link List} containing the results of the query execution
	 */
	public List<T> findByNamedParam(String propertyName, Object value);
	
	/**
	 * Execute a query based on a dynamically created Hibernate criteria object. use parameters to build DetachedCriteria
	 * 
	 * @param joinEntity the name of the join entity
	 * @param propertyName the name of the parameter
	 * @param value the value of the parameter, value can be Criterion instance
	 * @return a {@link List} containing the results of the query execution
	 */
	public List<T> findByNamedParam(String joinEntity, String propertyName, Object value);
	
	/**
	 * Execute a query based on a dynamically created Hibernate criteria object. use parameters to build DetachedCriteria
	 * 
	 * @param propertyName the name of the parameter
	 * @param value the value of the parameter, value can be Criterion instance
	 * @param ooder {@link org.hibernate.criterion.Order} order instance
	 * @return a {@link List} containing the results of the query execution
	 */
	public List<T> findByNamedParamAndOrder(String propertyName, Object value, Order order);
	
	/**
	 * Execute a query based on a dynamically created Hibernate criteria object. use parameters to build DetachedCriteria
	 * 
	 * @param joinEntity the name of the join entity
	 * @param propertyName the name of the parameter
	 * @param value the value of the parameter, value can be Criterion instance
	 * @param ooder {@link org.hibernate.criterion.Order} order instance
	 * @return a {@link List} containing the results of the query execution
	 */
	public List<T> findByNamedParamAndOrder(String joinEntity, String propertyName, Object value, Order order);
	
	/**
	 * Execute a query based on a dynamically created Hibernate criteria object. use parameters to build DetachedCriteria
	 * 
	 * @param propertyNames the names of the parameters
	 * @param values the values of the parameters, values can be Criterion instance
	 * @return a {@link List} containing the results of the query execution
	 */
	public List<T> findByNamedParam(String[] propertyNames, Object[] values);
	
	/**
	 * Execute a query based on a dynamically created Hibernate criteria object. use parameters to build DetachedCriteria
	 * 
	 * @param propertyNames the names of the parameters
	 * @param values the values of the parameters, values can be Criterion instance
	 * @param orders {@link org.hibernate.criterion.Order} order instances
	 * @return a {@link List} containing the results of the query execution
	 */
	public List<T> findByNamedParamAndOrder(String[] propertyNames, Object[] values, Order[] orders);

	/**
	 * Execute a query based on a dynamically created Hibernate criteria object. use parameters to build DetachedCriteria
	 * 
	 * @param joinEntitys the names of the join entities
	 * @param propertyNames the names of the parameters
	 * @param values the values of the parameters, values can be Criterion instance
	 * @param orders {@link org.hibernate.criterion.Order} order instances
	 * @return a {@link List} containing the results of the query execution
	 */
	public List<T> findByNamedParamAndOrder(String[] joinEntitys, String[] propertyNames, Object[] values, Order[] orders);
	
	/**
	 * Execute a query based on a dynamically created Hibernate criteria object. use parameters to build DetachedCriteria
	 * 
	 * @param joinEntity the name of the join entity
	 * @param propertyName the name of the parameter
	 * @param value the value of the parameter, value can be Criterion instance
	 * @param offset the first result to retrieve, numbered from <tt>0</tt>
	 * @param limit the maximum number of results
	 * @return a {@link Pagination} contain query records and the total number of records
	 */
	public Pagination<T> findPageByNamedParam(String joinEntity, String propertyName, Object value, final int offset, final int limit);
	
	/**
	 * Execute a query based on a dynamically created Hibernate criteria object. use parameters to build DetachedCriteria
	 * 
	 * @param propertyName the name of the parameter
	 * @param value the value of the parameter, value can be Criterion instance
	 * @param offset the first result to retrieve, numbered from <tt>0</tt>
	 * @param limit the maximum number of results
	 * @return a {@link Pagination} contain query records and the total number of records
	 */
	public Pagination<T> findPageByNamedParam(String propertyName, Object value, final int offset, final int limit);
	
	/**
	 * Execute a query based on a dynamically created Hibernate criteria object. use parameters to build DetachedCriteria
	 * 
	 * @param propertyName the name of the parameter
	 * @param value the value of the parameter, value can be Criterion instance
	 * @param ooder {@link org.hibernate.criterion.Order} order instance
	 * @param offset the first result to retrieve, numbered from <tt>0</tt>
	 * @param limit the maximum number of results
	 * @return a {@link Pagination} contain query records and the total number of records
	 */
	public Pagination<T> findPageByNamedParamAndOrder(String propertyName, Object value, Order order, final int offset, final int limit);
	
	/**
	 * Execute a query based on a dynamically created Hibernate criteria object. use parameters to build DetachedCriteria
	 * 
	 * @param propertyNames the names of the parameters
	 * @param values the values of the parameters, values can be Criterion instance
	 * @param offset the first result to retrieve, numbered from <tt>0</tt>
	 * @param limit the maximum number of results
	 * @return a {@link Pagination} contain query records and the total number of records
	 */
	public Pagination<T> findPageByNamedParam(String[] propertyNames, Object[] values, final int offset, final int limit);
	
	/**
	 * Execute a query based on a dynamically created Hibernate criteria object. use parameters to build DetachedCriteria
	 * 
	 * @param propertyNames the names of the parameters
	 * @param values the values of the parameters, values can be Criterion instance
	 * @param orders {@link org.hibernate.criterion.Order} order instances
	 * @param offset the first result to retrieve, numbered from <tt>0</tt>
	 * @param limit the maximum number of results
	 * @return a {@link Pagination} contain query records and the total number of records
	 */
	public Pagination<T> findPageByNamedParamAndOrder(String[] propertyNames, Object[] values, Order[] orders, final int offset, final int limit);

	/**
	 * Execute a query based on a dynamically created Hibernate criteria object. use parameters to build DetachedCriteria
	 * 
	 * @param joinEntitys the names of the join entities
	 * @param propertyNames the names of the parameters
	 * @param values the values of the parameters, values can be Criterion instance
	 * @param orders {@link org.hibernate.criterion.Order} order instances
	 * @param offset the first result to retrieve, numbered from <tt>0</tt>
	 * @param limit the maximum number of results
	 * @return a {@link Pagination} contain query records and the total number of records
	 */
	public Pagination<T> findPageByNamedParamAndOrder(String[] joinEntitys, String[] propertyNames, Object[] values, 
			final Order[] orders, final int offset, final int limit);
	
	public Pagination<T> findPage(PaginationRequest<T> paginationRequest);

	// -------------------------------------------------------------------------
	// Convenience finder methods for named queries
	// -------------------------------------------------------------------------

	/**
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#findByNamedQuery(String)
	 * @see org.springframework.orm.hibernate3.HibernateOperations#findByNamedQuery(String)
	 */
	public List findByNamedQuery(String queryName);

	/**
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#findByNamedQuery(String, Object)
	 * @see org.springframework.orm.hibernate3.HibernateOperations#findByNamedQuery(String, Object)
	 */
	public List findByNamedQuery(String queryName, Object value);

	/**
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#findByNamedQuery(String, Object...)
	 * @see org.springframework.orm.hibernate3.HibernateOperations#findByNamedQuery(String, Object...)
	 */
	public List findByNamedQuery(final String queryName, final Object... values);

	/**
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#findByNamedParam(String, String, Object)
	 * @see org.springframework.orm.hibernate3.HibernateOperations#findByNamedParam(String, String, Object)
	 */
	public List findByNamedQueryAndNamedParam(String queryName, String paramName, Object value);

	/**
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#findByNamedParam(String, String[], Object[])
	 * @see org.springframework.orm.hibernate3.HibernateOperations#findByNamedParam(String, String[], Object[])
	 */
	public List findByNamedQueryAndNamedParam(final String queryName, final String[] paramNames, final Object[] values);

	/**
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#findByNamedQueryAndValueBean(String, Object)
	 * @see org.springframework.orm.hibernate3.HibernateOperations#findByNamedQueryAndValueBean(String, Object)
	 */
	public List findByNamedQueryAndValueBean(final String queryName, final Object valueBean);

	// -------------------------------------------------------------------------
	// Convenience finder methods for detached criteria
	// -------------------------------------------------------------------------

	/**
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#findByCriteria(DetachedCriteria)
	 * @see org.springframework.orm.hibernate3.HibernateOperations#findByCriteria(DetachedCriteria)
	 */
	public List findByCriteria(DetachedCriteria criteria);
	
	/**
	 * Execute a query based on a given Hibernate criteria object.
	 * @param criteria the detached Hibernate criteria object.
	 * <b>Note: Do not reuse criteria objects! They need to recreated per execution,
	 * due to the suboptimal design of Hibernate's criteria facility.</b>
	 * @return a {@link Long} the total number of query results
	 */
	public Long findCountByCriteria(final DetachedCriteria criteria);

	/**
	 * Execute a page query based on a given Hibernate criteria object.
	 * @param criteria the detached Hibernate criteria object.
	 * <b>Note: Do not reuse criteria objects! They need to recreated per execution,
	 * due to the suboptimal design of Hibernate's criteria facility.</b>
	 * @param offset the first result to retrieve, numbered from <tt>0</tt>
	 * @param limit the maximum number of results
	 * @return a {@link Pagination} contain query records and the total number of records
	 */
	public Pagination<T> findPageByCriteria(final DetachedCriteria criteria, final int offset, final int limit);

	/**
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#findByExample(Object)
	 * @see org.springframework.orm.hibernate3.HibernateOperations#findByExample(Object)
	 */
	public List<T> findByExample();

	/**
	 * Execute a page query based on a given Hibernate criteria object.
	 * @param offset the first result to retrieve, numbered from <tt>0</tt>
	 * @param limit the maximum number of results
	 * @return a {@link Pagination} contain query records and the total number of records
	 */
	public Pagination<T> findPageByExample(final int offset, final int limit);

	// -------------------------------------------------------------------------
	// Convenience query methods for iteration and bulk updates/deletes
	// -------------------------------------------------------------------------

	/**
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#iterate(String)
	 * @see org.springframework.orm.hibernate3.HibernateOperations#iterate(String)
	 */
	public Iterator iterate(String queryString);

	/**
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#iterate(String, Object)
	 * @see org.springframework.orm.hibernate3.HibernateOperations#iterate(String, Object)
	 */
	public Iterator iterate(String queryString, Object value);

	/**
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#iterate(String, Object...)
	 * @see org.springframework.orm.hibernate3.HibernateOperations#iterate(String, Object...)
	 */
	public Iterator iterate(final String queryString, final Object... values);

	/**
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#closeIterator(Iterator)
	 * @see org.springframework.orm.hibernate3.HibernateOperations#closeIterator(Iterator)
	 */
	public void closeIterator(Iterator it);

	/**
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#bulkUpdate(String)
	 * @see org.springframework.orm.hibernate3.HibernateOperations#bulkUpdate(String)
	 */
	public int bulkUpdate(String queryString);

	/**
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#bulkUpdate(String, Object)
	 * @see org.springframework.orm.hibernate3.HibernateOperations#bulkUpdate(String, Object)
	 */
	public int bulkUpdate(String queryString, Object value);

	/**
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#bulkUpdate(String, Object...)
	 * @see org.springframework.orm.hibernate3.HibernateOperations#bulkUpdate(String, Object...)
	 */
	public int bulkUpdate(final String queryString, final Object... values);
}
