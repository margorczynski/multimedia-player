package org.projekt.multimediaplayer.main;

import java.util.Map;
import java.util.HashMap;

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
		
		System.out.println("ADD1");
		
		int numberOfTimesPlayed = 0;
		
		Map<String, String> timesPlayedMap = readTimesPlayedMap(filename);
		
		System.out.println("ADD2");
			
		if(timesPlayedMap.get(filename) != null)
		{
			numberOfTimesPlayed = Integer.parseInt(timesPlayedMap.get(filename));
		}
		
		numberOfTimesPlayed++;
		
		System.out.println("ADD3");
		
		timesPlayedMap.put(filename, String.valueOf(numberOfTimesPlayed));
		
		saveTimesPlayedMap(timesPlayedMap);
		
		System.out.println("ADD4");
		
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
		
		System.out.println("READ1");
		
		Map<String, String> timesPlayedMap = new HashMap<String, String>();
		
		System.out.println("READ2");
		
		try
		{
			System.out.println("WHILE1");
			line = reader.readLine();
			System.out.println(line);
			while(line != null)
			{
				System.out.println("WGHILE");
				String[] pair = line.split(":");
				
				timesPlayedMap.put(pair[0], pair[1]);
				
				line = reader.readLine();
			}
			System.out.println("WHILE AFTER");
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
				writer.append(entry.getKey() + ":" + entry.getValue());
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
