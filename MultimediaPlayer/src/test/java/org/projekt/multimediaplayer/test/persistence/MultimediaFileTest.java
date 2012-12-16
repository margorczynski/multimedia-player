package org.projekt.multimediaplayer.test.persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.projekt.multimediaplayer.dao.MultimediaFileDao;
import org.projekt.multimediaplayer.dao.ScheduleDao;
import org.projekt.multimediaplayer.dao.UserDao;
import org.projekt.multimediaplayer.model.MultimediaFile;
import org.projekt.multimediaplayer.model.Schedule;
import org.projekt.multimediaplayer.model.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.util.Date;

public final class MultimediaFileTest
{
	@Before
	public void setUp()
	{
		System.out.println("Starting MultimediaFile model class unit test...");
		
		System.out.println("Setting up example MultimediaFile object for test named testMultimediaFile");
		
		testUser.setUsername("JUnit Test Name");
		
		testUser.setPassword("JUnit Test Password");
		
		userDao.saveUser(testUser);
		
		
		testSchedule.setUser(testUser);
		
		testSchedule.setDescription("JUnit Test Description");
		
		testSchedule.setActive(true);
		
		scheduleDao.saveSchedule(testSchedule);
		
		
		testMultimediaFile.setSchedule(testSchedule);
		
		testMultimediaFile.setFilename("JUnit Test Filename");
		
		//testMultimediaFile.setPlaybackDate(new Date());
	}
	
	@Test
	public void runTests()
	{
		System.out.println("Saving testMultimediaFile to database");
		
		multimediaFileDao.saveMultimediaFile(testMultimediaFile);
		
		testMultimediaFile.setFilename("JUnit Test Updated Filename");
		
		
		System.out.println("Checking update method");
		
		multimediaFileDao.updateMultimediaFile(testMultimediaFile);
	}
	
	@After
	public void tearDown()
	{
		System.out.println("Removing testMultimediaFile from database");
		
		multimediaFileDao.deleteMultimediaFile(testMultimediaFile);
		
		System.out.println("Ending Schedule model class unit test...");
	}
	
	private MultimediaFile testMultimediaFile = new MultimediaFile();
	
	private Schedule testSchedule = new Schedule();
	
	private User testUser = new User();
	
	private final ApplicationContext appContext = new ClassPathXmlApplicationContext("application-context.xml");
	
	private final MultimediaFileDao multimediaFileDao = (MultimediaFileDao) appContext.getBean("multimediaFileDao");
	
	private final ScheduleDao scheduleDao = (ScheduleDao) appContext.getBean("scheduleDao");
	
	private final UserDao userDao = (UserDao) appContext.getBean("userDao");
}
