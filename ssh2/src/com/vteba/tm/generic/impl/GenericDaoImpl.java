package com.vteba.tm.generic.impl;

import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vteba.tm.generic.IGenericDao;
import com.vteba.util.reflection.ReflectUtils;

/**
 * 泛型DAO抽象类。用于继承，简化子类实现。
 * @author yinlei
 * date 2012-4-2 下午10:47:34
 * @param <T> 实体类型
 * @param <ID> 主键类型，一般是String或者Long
 */
@SuppressWarnings("unchecked")
public abstract class GenericDaoImpl<T, ID extends Serializable> implements IGenericDao<T, ID> {
	private static final Logger logger = LoggerFactory.getLogger(GenericDaoImpl.class);
	protected Class<T> entityClass;
	protected SessionFactory sessionFactory;
	
	public GenericDaoImpl(){
		entityClass = ReflectUtils.getClassGenericType(this.getClass());
	}
	
	public GenericDaoImpl(Class<T> entityClass){
		this.entityClass = entityClass;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * 延迟到子类中注入具体的sessionFactory实例
	 * @param sessionFactory 具体的sessionFactory
	 * @author yinlei
	 * date 2012-6-22 下午4:06:15
	 */
	public abstract void setSessionFactory(SessionFactory sessionFactory);
	
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public ID save(T entity) {
		ID id = (ID) getSession().save(entity);
		if (logger.isInfoEnabled()) {
			logger.info("save entity = [{}] , id=[{}]", entity.getClass().getName(), id);
		}
		return id;
	}

	public void persist(T entity) {
		if (logger.isInfoEnabled()) {
			logger.info("persist entity = [{}]", entity.getClass().getName());
		}
		getSession().persist(entity);
	}

	public void saveOrUpdate(T entity) {
		if (logger.isInfoEnabled()) {
			logger.info("saveOrUpdate entity = [{}]", entity.getClass().getName());
		}
		getSession().saveOrUpdate(entity);
	}
	
	public void update(T entity) {
		if (logger.isInfoEnabled()) {
			logger.info("update entity = [{}]", entity.getClass().getName());
		}
		getSession().update(entity);
	}

	public T merge(T entity) {
		if (logger.isInfoEnabled()) {
			logger.info("meger entity = [{}]", entity.getClass().getName());
		}
		T result = (T) getSession().merge(entity);
		return result;
	}

	public T load(Class<T> entity, ID id) {
		if (logger.isInfoEnabled()) {
			logger.info("load entity = [{}], id =[{}].", entity.getName(), id);
		}
		return (T) getSession().load(entity, id);
	}

	public T load(ID id) {
		if (logger.isInfoEnabled()) {
			logger.info("load entity = [{}], id = [{}]", entityClass.getName(), id);
		}
		return (T) getSession().load(entityClass, id);
	}

	public <X> X get(Class<X> entity, ID id) {
		if (logger.isInfoEnabled()) {
			logger.info("get entity = [{}], id = [{}]", entity.getName(), id);
		}
		return (X) getSession().get(entity, id);
	}

	public T get(ID id) {
		if (logger.isInfoEnabled()) {
			logger.info("get entity = [{}], id = [{}]", entityClass.getName(), id);
		}
		return (T) getSession().get(entityClass, id);
	}
	
	public void delete(ID id) {
		if (logger.isInfoEnabled()) {
			logger.info("delete entity = [{}], id = [{}]", entityClass.getName(), id);
		}
		getSession().delete(load(id));
	}

	public void delete(T entity) {
		if (logger.isInfoEnabled()) {
			logger.info("delete entity = [{}]", entity.getClass().getName());
		}
		getSession().delete(entity);
	}
}
