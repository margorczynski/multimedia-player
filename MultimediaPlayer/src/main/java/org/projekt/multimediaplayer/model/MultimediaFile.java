package org.projekt.multimediaplayer.model;

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

	public String getPath()
	{
		return path;
	}

	public void setPath(String description)
	{
		this.path = description;
	}

	public Schedule getSchedule()
	{
		return schedule;
	}

	public void setSchedule(Schedule schedule)
	{
		this.schedule = schedule;
	}

	public float getSize()
	{
		return size;
	}

	public long getLength()
	{
		return length;
	}

	public int getPlayDelay()
	{
		return playDelay;
	}

	public void setSize(float size)
	{
		this.size = size;
	}

	public void setLength(long length)
	{
		this.length = length;
	}

	public void setPlayDelay(int playDelay)
	{
		this.playDelay = playDelay;
	}

	@Override
	public String toString()
	{
		return getFilename();
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	private int id;
	private String filename;
	private String path;
	private Schedule schedule;
	private float size;
	private long length;
	private int playDelay;
	private String type;
}
