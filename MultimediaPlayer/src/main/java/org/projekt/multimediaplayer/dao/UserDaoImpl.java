package org.projekt.multimediaplayer.dao;

import java.util.List;

import org.projekt.multimediaplayer.model.User;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public final class UserDaoImpl extends HibernateDaoSupport implements UserDao
{

	public void saveUser(User user)
	{
		getHibernateTemplate().save(user);

	}

	public void updateUser(User user)
	{
		getHibernateTemplate().update(user);

	}

	public void deleteUser(User user)
	{
		getHibernateTemplate().delete(user);

	}
	
	public List<User> findUsers(String username)
	{
		return getHibernateTemplate().find("from User u where u.username = ?", username);
	}

}
