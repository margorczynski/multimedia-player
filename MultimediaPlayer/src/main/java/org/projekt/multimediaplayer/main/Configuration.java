package org.projekt.multimediaplayer.main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public final class Configuration
{
	private Configuration()
	{
		
	}
	
	/*
	 * Creates config.xml file with default configuration (checking vlc library path is rather mandatory before running)
	 */
	
	public void createDefaultConfigurationFile()
	{
		try 
		{
			Element settings = new Element("settings");
			Document doc = new Document(settings);
			doc.setRootElement(settings);
	 
			//settings.addContent(new Element("vlcLibPath32bit").setText("D:/VLC32"));
			settings.addContent(new Element("vlcLibPath32bit").setText("C:/Program Files/VideoLAN/VLC"));
			settings.addContent(new Element("vlcLibPath64bit").setText("D:/VLC64"));
			settings.addContent(new Element("url").setText("http://wiadomosci.wp.pl/ver,rss,rss.xml"));
	 
			
			XMLOutputter xmlOutput = new XMLOutputter();
	 
			xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(doc, new FileWriter("config.xml"));
		  } 
		  catch (IOException io) 
		  {
			System.out.println(io.getMessage());
		  }
	}
	
	/*
	 * Loads runtime configuration from the config.xml file
	 */
	
	public void loadConfigurationFromFile()
	{
		SAXBuilder builder = new SAXBuilder();
		
		File configFile = new File("config.xml");
		
		if(!configFile.exists()) createDefaultConfigurationFile();
		
		try 
		{ 
			Document document = (Document) builder.build(configFile);
			Element rootNode = document.getRootElement();

			//Checks the arch of the OS for the proper library path
			if(System.getProperty("sun.arch.data.model").equals("64"))
			{
				libLocation = rootNode.getChildText("vlcLibPath64bit");
			}
			else
			{
				libLocation = rootNode.getChildText("vlcLibPath32bit");
			}
			
			url = rootNode.getChildText("url");

	 
		  } 
		  catch (IOException io) 
		  {
			System.out.println(io.getMessage());
		  } 
		  catch (JDOMException jdomex) 
		  {
			System.out.println(jdomex.getMessage());
		  }
	}
	
	public String getLibLocation()
	{
		return libLocation;
	}
	
	public String getUrl()
	{
		return url;
	}

	private String libLocation;
	
	private String url;
}
