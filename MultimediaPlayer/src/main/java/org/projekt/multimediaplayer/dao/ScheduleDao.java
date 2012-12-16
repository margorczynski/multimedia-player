package org.projekt.multimediaplayer.dao;

import java.util.List;

import org.projekt.multimediaplayer.model.Schedule;
import org.projekt.multimediaplayer.model.User;

public interface ScheduleDao
{
	void saveSchedule(Schedule schedule);
	
	void updateSchedule(Schedule schedule);
	
	void deleteSchedule(Schedule schedule);
	
	List<Schedule> findShedule(String sheduleName);
	
	List<Schedule> findActiveSchedule(User user);
}
