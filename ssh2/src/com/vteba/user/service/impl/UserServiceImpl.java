package com.vteba.user.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vteba.tm.generic.Page;
import com.vteba.user.dao.UserDao;
import com.vteba.user.model.User;
import com.vteba.user.service.UserService;

/**
 * 用户Service实现。
 * @author yinlei
 * 2014-1-8 下午1:12:30
 */
@Service
public class UserServiceImpl implements UserService {
	private UserDao userDaoImpl;

	public UserServiceImpl() {
		super();
	}

	public UserDao getUserDaoImpl() {
		return userDaoImpl;
	}

	@Autowired
	public void setUserDaoImpl(UserDao userDaoImpl) {
		this.userDaoImpl = userDaoImpl;
	}

	@Override
	public String save(User entity) {
		return userDaoImpl.save(entity);
	}

	@Override
	public List<User> getEntityListByHql(String hql, Object... values) {
		return userDaoImpl.getEntityListByHql(hql, values);
	}

	@Override
	public void saveOrUpdate(User entity) {
		userDaoImpl.saveOrUpdate(entity);
	}

	@Override
	public void update(User entity) {
		userDaoImpl.update(entity);
	}

	@Override
	public User load(String id) {
		return userDaoImpl.load(id);
	}

	@Override
	public User get(String id) {
		return userDaoImpl.get(id);
	}

	@Override
	public void delete(String id) {
		userDaoImpl.delete(id);
	}

	@Override
	public void delete(User entity) {
		userDaoImpl.delete(entity);
	}

	@Override
	public <X> List<X> getAll(Class<X> entityClass) {
		return userDaoImpl.getAll(entityClass);
	}

	@Override
	public <X> List<X> getListByPropertyEqual(Class<X> entityClass, X model,
			Object... objects) {
		return userDaoImpl.getListByPropertyEqual(entityClass, model, objects);
	}

	@Override
	public List<Object[]> sqlQueryForObject(String sql, Object... values) {
		return userDaoImpl.sqlQueryForObject(sql, values);
	}

	@Override
	public List<Object[]> hqlQueryForObject(String hql, boolean namedQuery,
			Object... values) {
		return userDaoImpl.hqlQueryForObject(hql, namedQuery, values);
	}

	@Override
	public <X> X getUniqueResultByProperty(Class<X> entityClass,
			String propertyName, Object value) {
		return userDaoImpl.getUniqueResultByProperty(entityClass, propertyName, value);
	}

	@Override
	public <X> X getUniqueResultByProperty(Class<X> entityClass,
			Map<String, Object> params) {
		return userDaoImpl.getUniqueResultByProperty(entityClass, params);
	}

	@Override
	public User uniqueResultByHql(String hql, boolean namedQuery,
			Object... values) {
		return userDaoImpl.uniqueResultByHql(hql, namedQuery, values);
	}

	@Override
	public int executeHqlUpdate(String hql, boolean namedQuery,
			Object... values) {
		return userDaoImpl.executeHqlUpdate(hql, namedQuery, values);
	}

	@Override
	public int executeSqlUpdate(String sql, Object... values) {
		return userDaoImpl.executeSqlUpdate(sql, values);
	}

	@Override
	public Page<User> queryForPageByModel(Page<User> page, User entity) {
		return userDaoImpl.queryForPageByModel(page, entity);
	}

	@Override
	public Page<User> queryForPageByHql(Page<User> page, String hql,
			Object... values) {
		return userDaoImpl.queryForPageByHql(page, hql, values);
	}
	
}
