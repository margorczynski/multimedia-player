package org.projekt.multimediaplayer.main;

import java.net.URL;
import java.util.Iterator;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
 
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

/*
 * Klasa uzywana do pobierania naglowka z kanalu rss
 */

public final class RssReader
{
	/*
	 * Konstruktor pobiera naglowek z kanalu rss zdefiniowanego w config.xml i zapisuje go w zmiennej header
	 */
	public RssReader()
	{
		Configuration config = (Configuration) appContext.getBean("config");
		
		XmlReader reader = null;
		
		try
		{
			URL url  = new URL(config.getUrl());
		
			 
	      reader = new XmlReader(url);
	      SyndFeed feed = new SyndFeedInput().build(reader);
	      
	        SyndEntry entry = (SyndEntry) feed.getEntries().get(0);
	        header = entry.getTitle();
	     }
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
	     finally 
	     {
	    	 try
	    	 {
	            if (reader != null)
	                reader.close();
	    	 }
	    	 catch(Exception e)
	    	 {
	    		 e.printStackTrace();
	    	 }
	     }
	    
	}
	
	public String getHeader()
	{
		return header;
	}

	private String header;
	
	private final ApplicationContext appContext = new ClassPathXmlApplicationContext("application-context.xml");
}
