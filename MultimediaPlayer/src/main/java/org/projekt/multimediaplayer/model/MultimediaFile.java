package org.projekt.multimediaplayer.model;

import java.util.Date;

public final class MultimediaFile
{
	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getFilename()
	{
		return filename;
	}

	public void setFilename(String filename)
	{
		this.filename = filename;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public Date getPlaybackDate()
	{
		return playbackDate;
	}

	public void setPlaybackDate(Date playbackDate)
	{
		this.playbackDate = playbackDate;
	}

	private int id;
	
	private String filename;
	
	private String description;
	
	private Date playbackDate;
	
	private Schedule schedule;
}
