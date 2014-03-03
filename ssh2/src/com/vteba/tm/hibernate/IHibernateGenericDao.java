package com.vteba.tm.hibernate;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.vteba.tm.generic.IGenericDao;
import com.vteba.tm.generic.Page;

/**
 * Hibernate 泛型 DAO接口，简化Entity DAO实现。
 * @author yinlei 
 * date 2012-5-6 下午10:42:35
 * @param <T> 实体类型
 * @param <ID> 主键类型，一般是String或者Long
 */
public interface IHibernateGenericDao<T, ID extends Serializable> extends IGenericDao<T, ID> {
	/** 
     * 查询当前PO Bean List，一般查询单实体。<br>
     * 1、查询全部栏位，select u from User u where...<br>
     * 2、使用select new查询部分栏位，select new User(u.id,u.name) from User u where...，实体类中要有相应的构造函数<br>
     * 3、直接查询部分栏位，则返回List&lt;Object[]&gt;，不建议这么使用。如果是单实体，建议使用2中的select new，如果是多实体关联，<br>
     *    建议使用{@link #getListByHql(String, Class, Object...)}可以直接返回JavaBean<br>
	 * @param hql 可用Jpa风格参数： ?1、?2。命名参数： :subjectName。Hibernate参数： ? (deprecated)。
	 * @param values hql参数，可以使用单个参数，Map，List，AstModel实例，传参。
	 */
	public List<T> getEntityListByHql(String hql, Object... values);
	
	/**
	 * 获得指定entity的实体list，<em>慎用</em>，确保不会返回很多对象。
	 * @param entityClass 实体class
	 */
	public <X> List<X> getAll(Class<X> entityClass);
	
	/**
	 * 强制hibernate将对象与数据库同步。
	 */
	public void flush();
	
	/**
	 * 清空hibernate的session缓存，慎用。
	 */
	public void clear();
	
	/**
	 * 根据属性equal查询，使用QBE实现
	 * @param entityClass 要查询的实体类
	 * @param model 携带查询条件model
	 * @param objects 使用Map传参，key是排序字段，value是asc或desc。
	 * @return list 查询结果List&lt;X&gt;
	 */
	public <X> List<X> getListByPropertyEqual(Class<X> entityClass, X model, Object... objects);
	
	/**
	 * sql查询标量值，返回List&lt;Object[]&gt;
	 * @param sql sql语句
	 * @param values sql参数值
	 * @return List&lt;Object[]&gt;
	 * @author yinlei
	 * date 2013-6-11 下午5:10:53
	 */
	public List<Object[]> sqlQueryForObject(String sql, Object... values);
	
	/**
	 * hql查询标量值，返回List&lt;Object[]&gt;
	 * @param hql hql语句
	 * @param namedQuery 是否命名hql
	 * @param values hql参数
	 * @return List&lt;Object[]&gt;
	 * @author yinlei
	 * date 2013-6-11 下午5:12:18
	 */
	public List<Object[]> hqlQueryForObject(String hql, boolean namedQuery, Object... values);
	
	/**
	 * 获得唯一实体，请确保属性具有唯一性
	 * @param entityClass 要查询的实体类
	 * @param propertyName 属性名
	 * @param value 属性值
	 * @return 实体&lt;X&gt;
	 */
	public <X> X getUniqueResultByProperty(Class<X> entityClass, String propertyName, Object value);
	
	/**
	 * 获得唯一实体，请确保属性具有唯一性
	 * @param entityClass 要查询的实体类
	 * @param params 携带查询参数，key为属性名，value为值
	 * @return 实体&lt;X&gt;
	 * @author yinlei
	 * date 2013-6-11 下午5:19:04
	 */
	public <X> X getUniqueResultByProperty(Class<X> entityClass, Map<String, Object> params);
	
	/**
	 * 使用hql获得唯一实体。<br>
	 * 1、hql应查询Class&lt;T&gt;实例所有的属性，如：select s from Subject s where .... 。<br>
	 * 2、使用new T()构造函数指定属性，如：select new Subject(id, subjectCode, subjectName, level) 
	 *    from Subject s where .... 同时Subject实体中要有对应的构造函数才行。<br>
	 * 3、查询任意栏位，hql中的栏位名或别名要和Class&lt;T&gt;实例中的属性名一致。使用AliasedResultTransformer转换任意列。<br>
	 * @param hql 查询语句
	 * @param namedQuery 是否命名查询
	 * @param values hql中绑定的参数值
	 * @return 当前实体&lt;T&gt;
	 */
	public T uniqueResultByHql(String hql, boolean namedQuery, Object... values);
	
	/**
	 * 执行任意hql，常用于update，delete，insert
	 * @param hql 要执行的hql
	 * @param values hql中绑定的参数值
	 * @param namedQuery 是否命名查询
	 * @return 影响的实体数
	 */
	public int executeHqlUpdate(String hql, boolean namedQuery, Object... values);
	
	/**
	 * 执行任意sql，常用于update，delete，insert
	 * @param sql 要执行的sql
	 * @param values sql中绑定的参数值
	 * @return 影响的记录数
	 */
	public int executeSqlUpdate(String sql, Object... values);
	
	/**
	 * 初始化延迟加载的对象，load默认延迟加载
	 */
	public void initProxyObject(Object proxy);
	
	/**
	 * 分页查询，使用criteria实现
	 * @param page 分页数据
	 * @param entity 携带查询条件
	 * @return Page&lt;T&gt;分页，携带查询结果
	 * @author yinlei
	 * date 2012-7-8 下午10:34:23
	 */
	public Page<T> queryForPageByModel(Page<T> page, T entity);
	
	/**
	 * 使用hql进行分页查询
	 * @param page 分页条件
	 * @param hql hql语句
	 * @param values hql参数值
	 * @return Page&lt;T&gt;分页，携带查询结果
	 * @author yinlei
	 * date 2013-6-11 下午5:28:27
	 */
	public Page<T> queryForPageByHql(Page<T> page, String hql, Object... values);
	
	/**
	 * 统计hql查询返回多少条记录，分页查询使用
	 * @param hql 要执行的hql
	 * @param values hql绑定的参数值
	 * @return 记录数
	 * @author yinlei
	 * date 2012-5-14 下午11:39:33
	 */
	public long countHqlResult(String hql, Object... values);
	/**
	 * 统计sql查询返回多少条记录，分页查询使用
	 * @param sql 要执行的sql
	 * @param values sql绑定的参数值
	 * @return 记录数
	 * @author yinlei
	 * date 2012-5-14 下午11:40:33
	 */
	public long countSqlResult(String sql, Object... values);
	
}
