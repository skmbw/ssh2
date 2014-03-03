package com.vteba.user.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.vteba.tm.hibernate.impl.HibernateGenericDaoImpl;
import com.vteba.user.dao.UserDao;
import com.vteba.user.model.User;

/**
 * 用户Dao实现。
 * @author yinlei
 * 2014-1-8 下午1:10:55
 */
@Repository
public class UserDaoImpl extends HibernateGenericDaoImpl<User, String> implements UserDao {

	public UserDaoImpl() {
		super();
	}

	public UserDaoImpl(Class<User> entityClass) {
		super(entityClass);
	}

	@Autowired
	@Override
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
