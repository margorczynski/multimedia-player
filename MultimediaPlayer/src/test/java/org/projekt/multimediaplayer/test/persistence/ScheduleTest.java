package org.projekt.multimediaplayer.test.persistence;

import static org.junit.Assert.assertFalse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.projekt.multimediaplayer.dao.ScheduleDao;
import org.projekt.multimediaplayer.model.Schedule;
import org.projekt.multimediaplayer.dao.UserDao;
import org.projekt.multimediaplayer.model.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public final class ScheduleTest
{
	@Before
	public void setUp()
	{
		System.out.println("Starting Schedule model class unit test...");
		
		System.out.println("Setting up example Schedule object for test named testSchedule");
		
		testUser.setUsername("JUnit Test Name");
		
		testUser.setPassword("JUnit Test Password");
		
		userDao.saveUser(testUser);
		
		
		testSchedule.setUser(testUser);
		
		testSchedule.setDescription("JUnit Test Description");
		
		testSchedule.setActive(true);
	}
	
	@Test
	public void runTests()
	{
		System.out.println("Saving testSchedule to database");
		
		scheduleDao.saveSchedule(testSchedule);
		
		
		System.out.println("Checking update method");
		
		scheduleDao.updateSchedule(testSchedule);
	}
	
	@After
	public void tearDown()
	{
		System.out.println("Removing testSchedule from database");
		
		scheduleDao.deleteSchedule(testSchedule);
		
		userDao.deleteUser(testUser);
		
		System.out.println("Ending Schedule model class unit test...");
	}
	
	private Schedule testSchedule = new Schedule();
	
	private User testUser = new User();
	
	private final ApplicationContext appContext = new ClassPathXmlApplicationContext("application-context.xml");
	
	private final ScheduleDao scheduleDao = (ScheduleDao) appContext.getBean("scheduleDao");
	
	private final UserDao userDao = (UserDao) appContext.getBean("userDao");
}
