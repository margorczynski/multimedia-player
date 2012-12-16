package org.projekt.multimediaplayer.dao;

import java.util.List;

import org.projekt.multimediaplayer.model.Schedule;
import org.projekt.multimediaplayer.model.User;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public final class ScheduleDaoImpl extends HibernateDaoSupport implements
		ScheduleDao
{

	public void saveSchedule(Schedule schedule)
	{
		getHibernateTemplate().save(schedule);

	}

	public void updateSchedule(Schedule schedule)
	{
		getHibernateTemplate().update(schedule);

	}

	public void deleteSchedule(Schedule schedule)
	{
		getHibernateTemplate().delete(schedule);

	}

	public List<Schedule> findShedule(String sheduleName)
	{
		return getHibernateTemplate().find("from Schedule s where s.name = ?", sheduleName);
	}
	
	public List<Schedule> findActiveSchedule(User user)
	{
		return getHibernateTemplate().find("from Schedule s wher s.user_id = ? and s.active = 1", user.getId());
	}

}
