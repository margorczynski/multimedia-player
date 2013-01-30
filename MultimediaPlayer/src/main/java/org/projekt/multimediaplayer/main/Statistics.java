package org.projekt.multimediaplayer.main;

import java.util.Map;
import java.util.HashMap;
import java.util.Date;

import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Statistics
{
	public Statistics()
	{
		
		File statisticsFile = new File("statistics.txt");
		
		statisticsFile.delete();
		
		try
		{
			statisticsFile.createNewFile();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void addPlay(String filename)
	{
		openAll();
		
		int numberOfTimesPlayed = 0;
		
		Map<String, String> timesPlayedMap = readTimesPlayedMap(filename);
			
		if(timesPlayedMap.get(filename) != null)
		{
			numberOfTimesPlayed = Integer.parseInt(timesPlayedMap.get(filename));
		}
		
		numberOfTimesPlayed++;
		
		timesPlayedMap.put(filename, String.valueOf(numberOfTimesPlayed));
		
		saveTimesPlayedMap(timesPlayedMap);

		
		closeAll();
	}
	
	private void closeAll()
	{
		try
		{
			writer.close();
			reader.close();
			
			fileWriter.close();
			fileReader.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private void openAll()
	{
		try
		{
			fileWriter = new FileWriter("statistics.txt", true);
			fileReader = new FileReader("statistics.txt");
			
			writer = new BufferedWriter(fileWriter);
			reader = new BufferedReader(fileReader);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private Map<String, String> readTimesPlayedMap(String filename)
	{
		String line;
		
		Map<String, String> timesPlayedMap = new HashMap<String, String>();
		
		try
		{
			line = reader.readLine();
			System.out.println(line);
			while(line != null)
			{
				String[] pair = line.split(":");
				
				timesPlayedMap.put(pair[0], pair[1]);
				
				line = reader.readLine();
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		return timesPlayedMap;
	}
	
	private void saveTimesPlayedMap(Map<String, String> timesPlayedMap)
	{
		try
		{
			fileWriter = new FileWriter("statistics.txt", false);
			
			for(Map.Entry<String, String> entry : timesPlayedMap.entrySet())
			{
				writer.append(entry.getKey() + ":" + entry.getValue() + ":" + (new Date()).toString());
				writer.newLine();
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	
	private FileWriter fileWriter;
	private FileReader fileReader;
	
	private BufferedReader reader;
	private BufferedWriter writer;
}
