package org.projekt.multimediaplayer.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import sun.security.util.Cache.EqualByteArray;

public final class Schedule
{
	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public boolean isActive()
	{
		return active;
	}

	public void setActive(boolean active)
	{
		this.active = active;
	}

	public User getUser()
	{
		return user;
	}

	public void setUser(User user)
	{
		this.user = user;
	}

	public Set<MultimediaFile> getScheduleMultimediaFiles()
	{
		return scheduleMultimediaFiles;
	}

	public void setScheduleMultimediaFiles(Set<MultimediaFile> scheduleMultimediaFiles)
	{
		this.scheduleMultimediaFiles = scheduleMultimediaFiles;
	}

	public boolean isPeriodically()
	{
		return periodically;
	}

	public Date getStartTime()
	{
		return startTime;
	}

	public void setPeriodically(boolean periodically)
	{
		this.periodically = periodically;
	}

	public void setStartTime(Date startTime)
	{
		this.startTime = startTime;
	}
	
	
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String toString()
	{
		return getName();
	}
	
	public boolean equals(Schedule sched)
	{
		return (  ( name.equals(sched.getName()) ) &&  ( user.getId() == sched.user.getId())  );
		
	}
	
	
	
	
	private int id;
	private String name;
	private String description;
	private boolean active;
	private boolean periodically;
	private Date startTime;
	private User user;
	private Set<MultimediaFile> scheduleMultimediaFiles = new HashSet<MultimediaFile>();



	
}
