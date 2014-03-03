package com.vteba.tm.hibernate.impl;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.transform.ResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vteba.common.exception.BasicException;
import com.vteba.tm.generic.Page;
import com.vteba.tm.generic.impl.GenericDaoImpl;
import com.vteba.tm.hibernate.IHibernateGenericDao;

/**
 * 泛型DAO Hibernate实现，简化Entity DAO实现。
 * @author yinlei 
 * date 2012-5-6 下午10:39:42
 * @param <T> 实体类型
 * @param <ID> 主键类型，一般是String或者Long
 */
@SuppressWarnings("unchecked")
public abstract class HibernateGenericDaoImpl<T, ID extends Serializable>
		extends GenericDaoImpl<T, ID> implements IHibernateGenericDao<T, ID> {

	private static final Logger logger = LoggerFactory.getLogger(HibernateGenericDaoImpl.class);
	
	public HibernateGenericDaoImpl() {
		super();
	}
	
	public HibernateGenericDaoImpl(Class<T> entityClass) {
		super(entityClass);
	}
	
	public List<T> getEntityListByHql(String hql, Object... values) {
		if (logger.isInfoEnabled()) {
			logger.info("HQL query, hql = [{}], parameter = {}.", hql, Arrays.toString(values));
		}
		Query query = createQuery(hql, values);
		List<T> list = query.list();
		if (list == null) {
			list = Collections.emptyList();
		}
		return list;
	}
	
	public void flush() {
		getSession().flush();
	}

	public void clear() {
		getSession().clear();
	}
	
	/**
	 * 创建Query并绑定参数。<br>
	 * 1、使用JPA位置参数(如：?1，?2)。可使用List传参或者单个分别传参；<br>
	 * 2、命名参数，即指定参数名字，使用Map传递参数。Map的key为命名参数名，value为值，value中可以放List；<br>
	 * 3、命名参数，使用JavaBean传参，JavaBean实现AstModel接口，JavaBean中的属性名和命名参数名一致；<br>
	 * 4、HQL位置参数（如：?，?），不建议使用（deprecated）。<br>
	 * 5、in语法的list绑定，如：foo.bar in (:value_list)。Query.setParameterList("value_list", Collection vals)。
	 * @param hql 要执行的hql
	 * @param values hql要绑定的参数值
	 * @author yinlei
	 * date 2012-7-15 上午12:16:29
	 */
	protected Query createQuery(String hql, Object... values) {
		Query query = getSession().createQuery(hql);
		for (int i = 0; i < values.length; i++) {
			if (hql.indexOf("?" + (i + 1)) > 0) {
				logger.info("Use JPA style's position parameter binding.");
				if (values[i] instanceof List) {
					query.setParameterList((i + 1) + "", (List<?>)values[i]);
				} else {
					query.setParameter((i + 1) + "", values[i]);
				}
			} else if (values[i] instanceof Map){
				logger.info("Use named parameter binding.");
				Map<String, Object> map = (Map<String, Object>)values[i];
				for (Entry<String, Object> entry : map.entrySet()) {
					if (entry.getValue() instanceof List) {
						query.setParameterList(entry.getKey(), (List<?>)entry.getValue());
					} else {
						query.setParameter(entry.getKey(), entry.getValue());
					}
				}
			} else {
				logger.warn("HQL position parameter binding is deprecated, please use JPA style's.");
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}

	/**
	 * 创建SQLQuery，并绑定参数。
	 * @param sql sql语句，sql中使用？为占位符，或者命名参数
	 * @param resultClazz 结果类型
	 * @param values sql中的参数，单个传值，或者使用Map传值
	 */
	protected SQLQuery createSqlQuery(String sql, Class<?> resultClass, Object... values){
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		for (int i = 0; i < values.length; i++) {
			if (values[i] instanceof Map){
				logger.info("SQL Query, use named parameter binding.");
				Map<String, Object> map = (Map<String, Object>)values[i];
				for (Entry<String, Object> entry : map.entrySet()) {
					if (entry.getValue() instanceof List) {// in clause
						sqlQuery.setParameterList(entry.getKey(), (List<?>)entry.getValue());
					} else {
						sqlQuery.setParameter(entry.getKey(), entry.getValue());
					}
				}
			} else {
				logger.info("SQL Query, use position parameter binding.");
				sqlQuery.setParameter(i, values[i]);
			}
		}
		return sqlQuery;
	}
	
	/**
	 * 创建命名查询，hibernate session中不区分hql和sql，内部区分。<br>
	 * 使用JPA位置参数(如：?1，?2)。可使用List传参或者单个分别传参；<br>
	 * 命名参数，即指定参数名字，使用Map传递参数。Map的key为命名参数名，value为值，value中可以放List；<br>
	 * 命名参数，使用JavaBean传参，JavaBean实现AstModel接口，JavaBean中的属性名和命名参数名一致；<br>
	 * @param namedQuery 命名语句的名字
	 * @param values QL语句要绑定的参数
	 * @return Query实例
	 * @author yinlei
	 * date 2012-8-15 上午12:04:01
	 */
	protected Query createNamedQuery(String namedQuery, Object... values) {
		Query query = getSession().getNamedQuery(namedQuery);
		for (int i = 0; i < values.length; i++) {
			if (values[i] instanceof Map){
				logger.info("Use named parameter binding.");
				Map<String, Object> map = (Map<String, Object>)values[i];
				//如果是纯Map其实可以用query.setProperties
				for (Entry<String, Object> entry : map.entrySet()) {
					if (entry.getValue() instanceof List) {//in 语法
						query.setParameterList(entry.getKey(), (List<?>)entry.getValue());
					} else {
						query.setParameter(entry.getKey(), entry.getValue());
					}
				}
			} else {
				logger.info("Use JPA style's position parameter binding.");
				if (values[i] instanceof List) {//in 语法
					query.setParameterList((i + 1) + "", (List<?>)values[i]);
				} else {
					query.setParameter((i + 1) + "", values[i]);
				}
			}
		}	
		return query;
	}
	
	/**
	 * 创建命名SQL查询，使用{@link #createNamedQuery(String, Object...)}实现 <br>
	 * 使用JPA位置参数(如：?1，?2)。可使用List传参或者单个分别传参；<br>
	 * 命名参数，即指定参数名字，使用Map传递参数。Map的key为命名参数名，value为值，value中可以放List；<br>
	 * 命名参数，使用JavaBean传参，JavaBean实现AstModel接口，JavaBean中的属性名和命名参数名一致；<br>
	 * @param namedQuery 命名语句的名字
	 * @param values QL语句要绑定的参数
	 * @return Query实例
	 * @author yinlei
	 * date 2012-8-15 上午12:09:55
	 */
	protected SQLQuery createNamedSQLQuery(String namedQuery, Object... values) {
		return (SQLQuery)createNamedQuery(namedQuery, values);
	}
	
	/**
	 * 创建指定实体的Criteria
	 * @param entityClass 指定的实体
	 * @param criterions Criterion条件对象
	 * @author yinlei
	 * date 2012-12-17 下午10:58:06
	 */
	protected <X> Criteria createCriteria(Class<X> entityClass, Criterion... criterions) {
		if (logger.isInfoEnabled()) {
			logger.info("Create Criteria query by QBC. Entity = [{}].", entityClass.getName());
		}
		Criteria criteria = getSession().createCriteria(entityClass);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}
	
	/**
	 * 使用QBE创建Criteria
	 * @param entity 携带条件的实体
	 * @author yinlei
	 * date 2012-6-17 下午10:53:21
	 */
	protected <X> Criteria createCriteriaByModel(X entity){
		if (logger.isInfoEnabled()) {
			logger.info("Create Criteria query by QBE. Entity = [{}].", entityClass.getName());
		}
		Example example = Example.create(entity);
		example.ignoreCase().enableLike(MatchMode.START);
		Criteria criteria = getSession().createCriteria(entity.getClass()).add(example);
		return criteria;
	}
	
	public <X> List<X> getAll(Class<X> entityClass){
		return createCriteria(entityClass).list();
	}
	
	public <X> List<X> getListByPropertyEqual(Class<X> entityClass, X model, Object... objects){
		if (logger.isInfoEnabled()) {
			logger.info("Create Criteria query by QBE. Entity = [{}].", entityClass.getName());
		}
		Example example = Example.create(model);
		Criteria criteria = getSession().createCriteria(entityClass).add(example);
		for (int i = 0; i < objects.length; i++) {
			if (objects[i] instanceof Map) {
				Map<String, String> map = (Map<String, String>)objects[i];
				for (Entry<String, String> entry : map.entrySet()) {
					if (entry.getValue().equals("desc")) {
						criteria.addOrder(Order.desc(entry.getKey()));
					} else {
						criteria.addOrder(Order.asc(entry.getKey()));
					}
				}
			}
		}
		List<X> list = criteria.list();
		if (list == null) {
			list = Collections.emptyList();
		}
		
		return list;
	}
	
	public <X> X getUniqueResultByProperty(Class<X> entityClass, String proName, Object value) {
		Criterion criterion = Restrictions.eq(proName, value);
		return (X) createCriteria(entityClass, criterion).uniqueResult();
	}

	public <X> X getUniqueResultByProperty(Class<X> entityClass, Map<String, Object> params) {
		Criteria criteria = createCriteria(entityClass);
		for (Entry<String, Object> entry : params.entrySet()) {
			criteria.add(Restrictions.eq(entry.getKey(), entry.getValue()));
		}
		return (X) criteria.uniqueResult();
	}
	
	public T uniqueResultByHql(String hql, boolean namedQuery, Object... values){
		Query query = null;
		if (namedQuery) {
			query = createNamedQuery(hql, values);
		} else {
			query = createQuery(hql, values);
		}
		if (logger.isInfoEnabled()) {
			logger.info("uniqueResultByHql, hql = [{}], parameter = {}, resultClass = [{}].", 
					(namedQuery ? query.getQueryString() : hql), Arrays.toString(values), entityClass.getName());
		}
		return (T) query.uniqueResult();
	}

	protected Query distinct(Query query) {
		query.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return query;
	}
	
	public List<Object[]> sqlQueryForObject(String sql, Object... values){
		if (logger.isInfoEnabled()) {
			logger.info("sqlQueryForObject, sql = [{}], parameter = {}.", sql, Arrays.toString(values));
		}
		SQLQuery query = createSqlQuery(sql, null, values);
		List<Object[]> list = query.list();
		if (list == null) {
			list = Collections.emptyList();
		}
		return list;
	}

	public List<Object[]> hqlQueryForObject(String hql, boolean namedQuery, Object... values){
		Query query = null;
		if (namedQuery) {
			query = createNamedQuery(hql, values);
		} else {
			query = createQuery(hql, values);
		}
		if (logger.isInfoEnabled()) {
			logger.info("hqlQueryForObject, hql = [{}], parameter = {}.", (namedQuery ? query.getQueryString() : hql), Arrays.toString(values));
		}
		List<Object[]> list = query.list();
		if (list == null) {
			list = Collections.emptyList();
		}
		return list;
	}
	
	protected Criteria distinct(Criteria criteria) {
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return criteria;
	}

	public int executeHqlUpdate(String hql, boolean namedQuery, Object... values) {
		if (logger.isInfoEnabled()) {
			logger.info("Execute HQL, named = {}, hql = [{}], parameter = {}.", namedQuery, hql, Arrays.toString(values));
		}
		if (namedQuery) {
			return createNamedQuery(hql, values).executeUpdate();
		} else {
			return createQuery(hql, values).executeUpdate();
		}
	}
	
	public int executeSqlUpdate(String sql, Object... values){
		if (logger.isInfoEnabled()) {
			logger.info("Execute SQL, sql = [{}], parameter = {}.", sql, Arrays.toString(values));
		}
		return this.createSqlQuery(sql, null, values).executeUpdate();
	}
	
	public void initProxyObject(Object proxy) {
		Hibernate.initialize(proxy);
	}
    
	public Page<T> queryForPageByModel(Page<T> page, T entity) {
		if (logger.isInfoEnabled()) {
			logger.info("Criteria Paged Query, entity = [{}], page from [{}] to [{}].", 
					entity.getClass().getName(), page.getStartIndex(), page.getPageSize());
		}
		Criteria criteria = createCriteriaByModel(entity);
		long totalRecordCount = countCriteriaResult(criteria);
		page.setTotalRecordCount(totalRecordCount);
		setParameterToCriteria(page, criteria);
		List<T> result = criteria.list();
		page.setResult(result);
		return page;
	}
	
	public Page<T> queryForPageByHql(Page<T> page, String hql, Object... values) {
		if (logger.isInfoEnabled()) {
			logger.info("HQL Paged Query, hql = [{}], parameter = {}, page from [{}] to [{}].", 
					hql, Arrays.toString(values), page.getStartIndex(), page.getPageSize());
		}
		Query query = createQuery(hql, values);
		long totalRecordCount = countHqlResult(hql, values);
		page.setTotalRecordCount(totalRecordCount);
		setParameterToQuery(page, query);
		List<T> result = query.list();
		page.setResult(result);
		return page;
	}
		
	/**
	 * 给Query设置分页参数
	 * @author yinlei
	 * date 2012-5-14 下午11:43:13
	 */
	protected Query setParameterToQuery(Page<T> page, Query query){
		if (page.getPageSize() < 0) {
			throw new BasicException("Pagesize must be lager than 0.");
		}
		query.setFirstResult(page.getStartIndex());
		query.setMaxResults(page.getPageSize());
		return query;
	}
	
	/**
	 * 给Criteria设置分页和排序参数
	 * @author yinlei
	 * date 2012-5-14 下午11:42:29
	 */
	protected Criteria setParameterToCriteria(Page<T> page, Criteria criteria){
		if (page.getPageSize() < 0) {
			throw new BasicException("Pagesize must be lager than 0.");
		}
		if (page.getAscDesc() != null && page.getAscDesc().equals("desc")) {
			criteria.addOrder(Order.desc(page.getOrderBy()));
		} else if (page.getAscDesc() != null && page.getAscDesc().equals("asc")) {
			criteria.addOrder(Order.asc(page.getOrderBy()));
		}
		criteria.setFirstResult(page.getStartIndex());
		criteria.setMaxResults(page.getPageSize());
		return criteria;
	}
	
	public long countHqlResult(String hql, Object... values) {
		String countHql = prepareCountHql(hql);
		try {
			List<Object[]> list = hqlQueryForObject(countHql, false, values);
			Object object = list.get(0);
			Long count = Long.valueOf(object.toString());
			return count.longValue();
		} catch (Exception e) {
			throw new RuntimeException("HQL can't be auto count, hql is:" + countHql, e);
		}
	}

	public long countSqlResult(String sql, Object... values) {
		String countSql = prepareCountSql(sql);
		try {
			List<Object[]> list = hqlQueryForObject(countSql, false, values);
			Object object = list.get(0);
			Long count = Long.valueOf(object.toString());
			return count.longValue();
		} catch (Exception e) {
			throw new RuntimeException("SQL can't be auto count, sql is:" + countSql, e);
		}
	}
	
	/**
	 * 统计Criteria查询有多少记录，分页查询用
	 * @param c 要执行的Criteria
	 * @return 记录数
	 * @author yinlei
	 * date 2012-5-14 下午11:36:27
	 */
	protected long countCriteriaResult(Criteria criteria) {
		CriteriaImpl impl = (CriteriaImpl) criteria;
		
		//先把Projection、ResultTransformer、OrderBy取出来,清空三者后再执行Count操作
		Projection projection = impl.getProjection();
		ResultTransformer transformer = impl.getResultTransformer();
		
		//执行Count查询
		Long totalCountObject = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
		long totalCount = (totalCountObject != null) ? totalCountObject.longValue() : 0L;
		
		//将之前的Projection,ResultTransformer和OrderBy条件重新设回去
		criteria.setProjection(projection);
		if (projection == null) {
			criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		}
		if (transformer != null) {
			criteria.setResultTransformer(transformer);
		}
		return totalCount;
	}
	
	/**
	 * select显示的栏位与order by排序会影响count查询效率，进行简单的排除，未考虑union
	 * @param hql 原始hql
	 * @return 排除order by和显示栏位后的hql
	 * @author yinlei
	 * date 2012-5-14 下午11:30:14
	 */
	protected String prepareCountHql(String hql) {
		String fromHql = hql;
		fromHql = "from " + StringUtils.substringAfter(fromHql, "from");
		fromHql = StringUtils.substringBefore(fromHql, "order by");
		String countHql = "select count(*) " + fromHql;
		return countHql;
	}
	
	/**
	 * select显示的栏位与order by排序会影响count查询效率，进行简单的排除，未考虑union
	 * @param sql 原始sql
	 * @return 排除order by和显示栏位后的sql
	 * @author yinlei
	 * date 2012-7-14 下午11:31:21
	 */
	protected String prepareCountSql(String sql) {
		String fromSql = sql;
		fromSql = "from " + StringUtils.substringAfter(fromSql, "from");
		fromSql = StringUtils.substringBefore(fromSql, "order by");
		String countSql = "select count(*) count " + fromSql;
		return countSql;
	}
}
