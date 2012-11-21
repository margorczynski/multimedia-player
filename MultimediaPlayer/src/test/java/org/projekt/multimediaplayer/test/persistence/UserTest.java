package org.projekt.multimediaplayer.test.persistence;

import org.junit.*;
import static org.junit.Assert.*;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import org.projekt.multimediaplayer.model.User;
import org.projekt.multimediaplayer.dao.UserDao;

public final class UserTest
{
	@Before
	public void setUp()
	{
		System.out.println("Starting User model class unit test...");
		
		System.out.println("Setting up example User object for test named testUser");
		
		testUser.setUsername("JUnit TestUsername");
		
		testUser.setPassword("JUnit TestPassword");
	}
	
	@Test
	public void runTests()
	{
		System.out.println("Saving testUser to database");
		
		userDao.saveUser(testUser);
		
		
		System.out.println("Checking findUsers method");
		
		assertFalse(userDao.findUsers(testUser.getUsername()).isEmpty());
		
		
		System.out.println("Checking update method");
		
		testUser = userDao.findUsers(testUser.getUsername()).get(0);
		
		testUser.setUsername("JUnit UpdatedTestUsername");
		
		testUser.setPassword("JUnit UpdatedTestPassword");
		
		userDao.updateUser(testUser);
		
		assertFalse(userDao.findUsers(testUser.getUsername()).isEmpty());

	}
	
	@After
	public void tearDown()
	{
		System.out.println("Removing testUser from database");
		
		userDao.deleteUser(testUser);
		
		System.out.println("Ending User model class unit test...");
	}
	
	private User testUser = new User();
	
	private final ApplicationContext appContext = new ClassPathXmlApplicationContext("application-context.xml");
	
	private final UserDao userDao = (UserDao) appContext.getBean("userDao");
}
