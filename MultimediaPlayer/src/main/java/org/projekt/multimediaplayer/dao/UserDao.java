package org.projekt.multimediaplayer.dao;

import java.util.List;

import org.projekt.multimediaplayer.model.User;

public interface UserDao
{
	void saveUser(User user);
	
	void updateUser(User user);
	
	void deleteUser(User user);
	
	List<User> findUsers(String username);
}
