package org.projekt.multimediaplayer.model;

import java.util.Set;
import java.util.HashSet;

public final class User
{
	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}
	
	public Set<Schedule> getUserSchedules()
	{
		return userSchedules;
	}

	public void setUserSchedules(Set<Schedule> userSchedules)
	{
		this.userSchedules = userSchedules;
	}

	private int id;
	
	private String username;
	
	private String password;
	
	private Set<Schedule> userSchedules = new HashSet<Schedule>();
}
