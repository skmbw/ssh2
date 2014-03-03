package com.googlecode.hibernatedao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Filter;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.ReplicationMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.googlecode.hibernatedao.support.Pagination;
import com.googlecode.hibernatedao.support.PaginationRequest;

/**
 * 基于HibernateTemplate扩展的泛型Dao基类，部分方法直接调用HibernateTemplate中方法，
 * 增加一些查询方法，直接传入参数，动态创建Criteria对象，构建查询。最主要的增强提供分页查询，
 * 分页查询结果包含查询结果、记录总数和分页相关参数.
 * 
 * @author libinsong1204@gmail.com
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class HibernateBaseDaoImpl<T, ID extends Serializable> extends HibernateDaoSupport implements HibernateBaseDao<T, ID> {
	@Autowired
	protected ApplicationContext applicationContext;

	protected Class<T> entityClass;

	protected String entityName;

	@PostConstruct
	public void postConstruct() {
		Type type = getClass().getGenericSuperclass();
		if (type instanceof ParameterizedType) {
			entityClass = (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[0];
		}
		
		SessionFactory sessionFactory = applicationContext.getBean(SessionFactory.class);
		this.setSessionFactory(sessionFactory);

		ClassMetadata classMetadata = sessionFactory.getClassMetadata(entityClass);
		entityName = classMetadata.getEntityName();
	}

	// -------------------------------------------------------------------------
	// Convenience methods for loading individual objects
	// -------------------------------------------------------------------------

	public T get(ID id) throws DataAccessException {
		return this.getHibernateTemplate().get(entityClass, id);
	}

	public T get(final ID id, final LockMode lockMode) throws DataAccessException {
		return this.getHibernateTemplate().get(entityClass, id, lockMode);
	}

	public T load(ID id) throws DataAccessException {
		return this.getHibernateTemplate().load(entityClass, id);
	}

	public T load(final ID id, final LockMode lockMode) throws DataAccessException {
		return this.getHibernateTemplate().load(entityClass, id, lockMode);
	}

	public List<T> loadAll() throws DataAccessException {
		return this.getHibernateTemplate().loadAll(entityClass);
	}

	public void load(final T entity, final ID id) throws DataAccessException {
		this.getHibernateTemplate().load(entity, id);
	}

	public void refresh(final T entity) throws DataAccessException {
		refresh(entity, null);
	}

	public void refresh(final T entity, final LockMode lockMode) throws DataAccessException {
		this.getHibernateTemplate().refresh(entity, lockMode);
	}

	public boolean contains(final T entity) throws DataAccessException {
		return this.getHibernateTemplate().contains(entity);
	}

	public void evict(final T entity) throws DataAccessException {
		this.getHibernateTemplate().evict(entity);
	}

	public void initialize(T proxy) throws DataAccessException {
		this.getHibernateTemplate().initialize(proxy);
	}

	public Filter enableFilter(String filterName) throws IllegalStateException {
		Session session = SessionFactoryUtils.getSession(getSessionFactory(), false);
		Filter filter = session.getEnabledFilter(filterName);
		if (filter == null) {
			filter = session.enableFilter(filterName);
		}
		return filter;
	}
	
	public Serializable getIdentifierObject(T entity) {
		if (entity==null) {
			logger.warn("Unable to determine the identifier for an empty object");
			return null;
		}
		SessionFactory sf = this.getHibernateTemplate().getSessionFactory(); // Hibernate factory
		ClassMetadata cm = sf.getClassMetadata(entityClass);
		if ( cm==null ) 
			throw new RuntimeException("gIO(): Unable to get class metadata for " + entityClass.getSimpleName());
		return cm.getIdentifier(entity, (SessionImplementor) getSession());	
	}

	// -------------------------------------------------------------------------
	// Convenience methods for storing individual objects
	// -------------------------------------------------------------------------

	public void lock(final T entity, final LockMode lockMode) {
		this.getHibernateTemplate().lock(entity, lockMode);
	}

	public ID save(final T entity) {
		return (ID) this.getHibernateTemplate().save(entity);
	}

	public void update(T entity) {
		this.getHibernateTemplate().update(entity);
	}

	public void update(final T entity, final LockMode lockMode) {
		this.getHibernateTemplate().update(entity, lockMode);
	}

	public void saveOrUpdate(final T entity) {
		this.getHibernateTemplate().saveOrUpdate(entity);
	}

	public void saveOrUpdateAll(final Collection<T> entities) {
		//this.getHibernateTemplate().saveOrUpdateAll(entities);
	}

	public void replicate(final T entity, final ReplicationMode replicationMode) {
		this.getHibernateTemplate().replicate(entity, replicationMode);
	}

	public void persist(final T entity) {
		this.getHibernateTemplate().persist(entity);
	}

	public T merge(final T entity) {
		return this.getHibernateTemplate().merge(entity);
	}

	public void delete(T entity) {
		this.getHibernateTemplate().delete(entity);
	}
	
	public T delete(ID id) {
		T entity = this.get(id);
		
		if (entity != null) {
			this.delete(entity);
		}
		return entity;
	}

	public void delete(final T entity, final LockMode lockMode) {
		this.getHibernateTemplate().delete(entity, lockMode);
	}

	public void deleteAll(final Collection<T> entities) {
		this.getHibernateTemplate().deleteAll(entities);
	}

	public void flush() {
		this.getHibernateTemplate().flush();
	}

	public void clear() {
		this.getHibernateTemplate().clear();
	}

	// -------------------------------------------------------------------------
	// Convenience finder methods for HQL strings
	// -------------------------------------------------------------------------

	public List findByHQL(String queryString) {
		return this.getHibernateTemplate().find(queryString);
	}

	public List findByHQL(String queryString, Object value) {
		return this.getHibernateTemplate().find(queryString, value);
	}

	public List findByHQL(final String queryString, final Object... values) {
		return this.getHibernateTemplate().find(queryString, values);
	}

	public List findByHQLNamedParam(String queryString, String paramName, Object value) {
		return this.getHibernateTemplate().findByNamedParam(queryString, paramName, value);
	}

	public List findByHQLNamedParam(final String queryString, final String[] paramNames, final Object[] values) {
		return this.getHibernateTemplate().findByNamedParam(queryString, paramNames, values);
	}

	public List findByHQLValueBean(final String queryString, final Object valueBean) {
		return this.getHibernateTemplate().findByValueBean(queryString, valueBean);
	}
	
	// -------------------------------------------------------------------------
	// Convenience finder methods for dynamic detached criteria
	// -------------------------------------------------------------------------
	
	public List<T> findByNamedParam(String propertyName, Object value) {
		return this.findByNamedParamAndOrder(null, new String[]{propertyName}, new Object[]{value}, null);
	}
	
	public List<T> findByNamedParam(String joinEntity, String propertyName, Object value) {
		return this.findByNamedParamAndOrder(new String[]{joinEntity}, new String[]{propertyName}, new Object[]{value}, null);
	}
	
	public List<T> findByNamedParamAndOrder(String propertyName, Object value, Order order) {
		return this.findByNamedParamAndOrder(null, new String[]{propertyName}, new Object[]{value}, new Order[]{order});
	}
	
	public List<T> findByNamedParamAndOrder(String joinEntity, String propertyName, Object value, Order order) {
		return this.findByNamedParamAndOrder(new String[]{joinEntity}, new String[]{propertyName}, new Object[]{value}, new Order[]{order});
	}
	
	public List<T> findByNamedParam(String[] propertyNames, Object[] values) {
		return this.findByNamedParamAndOrder(null, propertyNames, values, null);
	}
	
	public List<T> findByNamedParamAndOrder(String[] propertyNames, Object[] values, Order[] orders) {
		return this.findByNamedParamAndOrder(null, propertyNames, values, orders);
	}

	public List<T> findByNamedParamAndOrder(String[] joinEntitys, String[] propertyNames, Object[] values, Order[] orders) {
		DetachedCriteria criteria = createDetachedCriteria(joinEntitys, propertyNames, values);

		if(orders != null) {
			for (Order order : orders) {
				criteria.addOrder(order);
			}
		}

		return this.findByCriteria(criteria);
	}
	
	public Pagination<T> findPageByNamedParam(String joinEntity, String propertyName, Object value, final int offset, final int limit) {
		return this.findPageByNamedParamAndOrder(new String[]{joinEntity}, new String[]{propertyName}, new Object[]{value}, null, offset, limit);
	}
	
	public Pagination<T> findPageByNamedParam(String propertyName, Object value, final int offset, final int limit) {
		return this.findPageByNamedParamAndOrder(null, new String[]{propertyName}, new Object[]{value}, null, offset, limit);
	}
	
	public Pagination<T> findPageByNamedParamAndOrder(String propertyName, Object value, Order order, final int offset, final int limit) {
		return this.findPageByNamedParamAndOrder(null, new String[]{propertyName}, new Object[]{value}, new Order[]{order}, offset, limit);
	}
	
	public Pagination<T> findPageByNamedParam(String[] propertyNames, Object[] values, final int offset, final int limit) {
		return this.findPageByNamedParamAndOrder(null, propertyNames, values, null, offset, limit);
	}
	
	public Pagination<T> findPageByNamedParamAndOrder(String[] propertyNames, Object[] values, Order[] orders, final int offset, final int limit) {
		return this.findPageByNamedParamAndOrder(null, propertyNames, values, orders, offset, limit);
	}

	public Pagination<T> findPageByNamedParamAndOrder(String[] joinEntitys, String[] propertyNames, Object[] values, 
			final Order[] orders, final int offset, final int limit) {
		final DetachedCriteria criteria = createDetachedCriteria(joinEntitys, propertyNames, values);
		
		return this.getHibernateTemplate().execute(new HibernateCallback<Pagination<T>>() {
			public Pagination<T> doInHibernate(Session session) throws HibernateException {
				Criteria executableCriteria = criteria.getExecutableCriteria(session);
				prepareCriteria(executableCriteria);
				
				long totalCount = ((Long) executableCriteria.setProjection(Projections.rowCount()).uniqueResult()).longValue();
				
				executableCriteria.setProjection(null);
				if(orders != null) {
					for (Order order : orders) {
						criteria.addOrder(order);
					}
				}
				List items = executableCriteria.setFirstResult(offset).setMaxResults(limit).list();

				double pageNumber = Math.ceil(totalCount * 1d / limit);
				Pagination<T> page = new Pagination<T>((long)pageNumber, limit, totalCount, items);
				return page;
			}
		});
	} 
	
	public Pagination<T> findPage(final PaginationRequest<T> paginationRequest) {
		final DetachedCriteria criteria = createDetachedCriteria(paginationRequest.getJoinEntitys(), paginationRequest.getPropertyNames(), paginationRequest.getValues());
		return this.getHibernateTemplate().execute(new HibernateCallback<Pagination<T>>() {
			public Pagination<T> doInHibernate(Session session) throws HibernateException {
				Criteria executableCriteria = criteria.getExecutableCriteria(session);
				prepareCriteria(executableCriteria);
				
				long totalCount = ((Long) executableCriteria.setProjection(Projections.rowCount()).uniqueResult()).longValue();
				
				executableCriteria.setProjection(null);
				if(paginationRequest.getOrders() != null) {
					for (Order order : paginationRequest.getOrders()) {
						criteria.addOrder(order);
					}
				}
				
				int offset = paginationRequest.getOffset();
				int limit = paginationRequest.getLimit();
				List items = executableCriteria.setFirstResult(offset).setMaxResults(limit).list();

				double pageNumber = Math.ceil(totalCount * 1d / limit);
				Pagination<T> page = new Pagination<T>((long)pageNumber, limit, totalCount, items);
				return page;
			}
		});
		
	}

	// -------------------------------------------------------------------------
	// Convenience finder methods for named queries
	// -------------------------------------------------------------------------

	public List findByNamedQuery(String queryName) {
		return this.getHibernateTemplate().findByNamedQuery(queryName);
	}

	public List findByNamedQuery(String queryName, Object value) {
		return this.getHibernateTemplate().findByNamedQuery(queryName, value);
	}

	public List findByNamedQuery(final String queryName, final Object... values) {
		return this.getHibernateTemplate().findByNamedQuery(queryName, values);
	}

	public List findByNamedQueryAndNamedParam(String queryName, String paramName, Object value) {
		return this.getHibernateTemplate().findByNamedQueryAndNamedParam(queryName, paramName, value);
	}

	public List findByNamedQueryAndNamedParam(final String queryName, final String[] paramNames, final Object[] values) {
		return this.getHibernateTemplate().findByNamedQueryAndNamedParam(queryName, paramNames, values);
	}

	public List findByNamedQueryAndValueBean(final String queryName, final Object valueBean) {
		return this.getHibernateTemplate().findByNamedQueryAndValueBean(queryName, valueBean);
	}

	// -------------------------------------------------------------------------
	// Convenience finder methods for detached criteria
	// -------------------------------------------------------------------------

	public List findByCriteria(DetachedCriteria criteria) {
		return this.getHibernateTemplate().findByCriteria(criteria);
	}
	
	public Long findCountByCriteria(final DetachedCriteria criteria) {
		return this.getHibernateTemplate().execute(new HibernateCallback<Long>() {
			public Long doInHibernate(Session session) throws HibernateException {
				Criteria executableCriteria = criteria.getExecutableCriteria(session);
				prepareCriteria(executableCriteria);
				
				long totalCount = ((Long) executableCriteria.setProjection(Projections.rowCount()).uniqueResult()).longValue();
				return totalCount;
			}
		});
	}

	public Pagination<T> findPageByCriteria(final DetachedCriteria criteria, final int offset, final int limit) {
		return this.getHibernateTemplate().execute(new HibernateCallback<Pagination<T>>() {
			public Pagination<T> doInHibernate(Session session) throws HibernateException {
				Criteria executableCriteria = criteria.getExecutableCriteria(session);
				prepareCriteria(executableCriteria);
				
				long totalCount = ((Long) executableCriteria.setProjection(Projections.rowCount()).uniqueResult()).longValue();
				executableCriteria.setProjection(null);
				List items = executableCriteria.setFirstResult(offset).setMaxResults(limit).list();

				double pageNumber = Math.ceil(totalCount * 1d / limit);
				Pagination<T> page = new Pagination<T>((long)pageNumber, limit, totalCount, items);
				return page;
			}
		});
	}
	
	public List<T> findByExample() throws DataAccessException {
		return (List<T>) this.getHibernateTemplate().findByExample(entityClass);
	}
	
	public Pagination<T> findPageByExample(final int offset, final int limit) {
		return this.getHibernateTemplate().execute(new HibernateCallback<Pagination<T>>() {
			public Pagination<T> doInHibernate(Session session) throws HibernateException {
				Criteria executableCriteria = session.createCriteria(entityClass);
				prepareCriteria(executableCriteria);
				
				long totalCount = ((Long) executableCriteria.setProjection(Projections.rowCount()).uniqueResult()).longValue();
				executableCriteria.setProjection(null);
				List items = executableCriteria.setFirstResult(offset).setMaxResults(limit).list();

				double pageNumber = Math.ceil(totalCount * 1d / limit);
				Pagination<T> page = new Pagination<T>((int)pageNumber, limit, totalCount, items);
				return page;
			}
		});
	}

	// -------------------------------------------------------------------------
	// Convenience query methods for iteration and bulk updates/deletes
	// -------------------------------------------------------------------------

	public Iterator iterate(String queryString) {
		return this.getHibernateTemplate().iterate(queryString);
	}

	public Iterator iterate(String queryString, Object value) {
		return this.getHibernateTemplate().iterate(queryString, value);
	}

	public Iterator iterate(final String queryString, final Object... values) {
		return this.getHibernateTemplate().iterate(queryString, values);
	}

	public void closeIterator(Iterator it) {
		this.getHibernateTemplate().closeIterator(it);
	}

	public int bulkUpdate(String queryString) {
		return this.getHibernateTemplate().bulkUpdate(queryString);
	}

	public int bulkUpdate(String queryString, Object value) {
		return this.getHibernateTemplate().bulkUpdate(queryString, value);
	}

	public int bulkUpdate(final String queryString, final Object... values) {
		return this.getHibernateTemplate().bulkUpdate(queryString, values);
	}

	// -------------------------------------------------------------------------
	// Helper methods used by the operations above
	// -------------------------------------------------------------------------
	protected void prepareCriteria(Criteria criteria) {
		if (this.getHibernateTemplate().isCacheQueries()) {
			criteria.setCacheable(true);
			if (this.getHibernateTemplate().getQueryCacheRegion() != null) {
				criteria.setCacheRegion(this.getHibernateTemplate().getQueryCacheRegion());
			}
		}
		if (this.getHibernateTemplate().getFetchSize() > 0) {
			criteria.setFetchSize(this.getHibernateTemplate().getFetchSize());
		}
		if (this.getHibernateTemplate().getMaxResults() > 0) {
			criteria.setMaxResults(this.getHibernateTemplate().getMaxResults());
		}
		SessionFactoryUtils.applyTransactionTimeout(criteria, getSessionFactory());
	}
	
	protected DetachedCriteria createDetachedCriteria(String[] joinEntitys, String[] propertyNames, Object[] values) {
		if(joinEntitys != null)
			return createDetachedCriteria(Arrays.asList(joinEntitys), Arrays.asList(propertyNames), Arrays.asList(values));
		else
			return createDetachedCriteria(null, Arrays.asList(propertyNames), Arrays.asList(values));
	}
	
	protected DetachedCriteria createDetachedCriteria(List<String> joinEntitys, List<String> propertyNames, List<Object> values) {
		DetachedCriteria criteria = DetachedCriteria.forClass(entityClass);

		if(joinEntitys != null) {
			for (String joinEntity : joinEntitys) {
				criteria.setFetchMode(joinEntity, FetchMode.JOIN);
				criteria.createAlias(joinEntity, joinEntity);
			}
		}

		for (int i = 0, len = propertyNames.size(); i < len; i++) {
			String propertyName = propertyNames.get(i);
			Object value = values.get(i);
			
			if (value instanceof Criterion) {
				criteria.add((Criterion) value);
			} else if (value instanceof Collection) {
				criteria.add(Restrictions.in(propertyName, (Collection) value));
			} else if (value.getClass().isArray()) {
				criteria.add(Restrictions.in(propertyName, (Object[])value));
			} else {
				criteria.add(Restrictions.eq(propertyName, value));
			}
		}
		return criteria;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public void setEntityClass(Class<T> entityClass) {
		this.entityClass = entityClass;
	}
}