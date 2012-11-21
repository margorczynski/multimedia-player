package org.projekt.multimediaplayer.dao;

import org.projekt.multimediaplayer.model.Schedule;
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

}
