package org.projekt.multimediaplayer.dao;

import org.projekt.multimediaplayer.model.Schedule;

public interface ScheduleDao
{
	void saveSchedule(Schedule schedule);
	
	void updateSchedule(Schedule schedule);
	
	void deleteSchedule(Schedule schedule);
}
