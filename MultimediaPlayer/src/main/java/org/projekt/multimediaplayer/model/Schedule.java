package org.projekt.multimediaplayer.model;

import java.util.HashSet;
import java.util.Set;

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

	public Set<MultimediaFile> getScheduleMultimediaFiles()
	{
		return scheduleMultimediaFiles;
	}

	public void setScheduleMultimediaFiles(Set<MultimediaFile> scheduleMultimediaFiles)
	{
		this.scheduleMultimediaFiles = scheduleMultimediaFiles;
	}

	private int id;
	
	private String description;
	
	private boolean active;
	
	private User user;

	private Set<MultimediaFile> scheduleMultimediaFiles = new HashSet<MultimediaFile>();
}
