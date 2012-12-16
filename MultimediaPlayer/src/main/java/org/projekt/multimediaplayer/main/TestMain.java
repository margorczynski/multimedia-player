package org.projekt.multimediaplayer.main;

import org.springframework.context.ApplicationContext;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import org.projekt.multimediaplayer.model.*;
import org.projekt.multimediaplayer.dao.*;

import java.util.List;

public final class TestMain
{
	public static void main(String[] args)
	{
		List<User> userList;
		
		ApplicationContext appContext = new ClassPathXmlApplicationContext("application-context.xml");
		
		User user = new User();
		
		user.setId(1);
		user.setUsername("TestImie");
		user.setPassword("TestHaslo");
		
		UserDao userDao = (UserDao) appContext.getBean("userDao");
		
		userDao.saveUser(user);
		
		userList = userDao.findUsers("TestImie");
		
		for(User u : userList)
		{
			System.out.println(u.getUsername());
		}
	

		System.out.println( );
	}
}
