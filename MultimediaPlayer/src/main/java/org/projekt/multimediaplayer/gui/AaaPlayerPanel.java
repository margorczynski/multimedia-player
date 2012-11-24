package org.projekt.multimediaplayer.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.media.CannotRealizeException;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.NoPlayerException;
import javax.media.Player;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class AaaPlayerPanel extends JPanel 
{
	public AaaPlayerPanel()
	{
		super();
		init();
	}
	
	
	public void init()
	{
		
		this.setLayout(new BorderLayout());
		
		// MP3 dzia³a
		setMediaFileURL("file","C:/a.mp3");
		
		try{
			Player player = Manager.createRealizedPlayer(new MediaLocator(mediaFileURL));
			Component video = player.getVisualComponent();
			Component control = player.getControlPanelComponent();

			if((video !=null)  )
			{
				add(video, BorderLayout.CENTER);
			
			}
			
			add(control,BorderLayout.SOUTH);
			player.start();
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
			
	}
	
	public void setMediaFileURL(String protocol, String file) 
	{
		try {
			mediaFileURL = new URL(protocol,null,file);
		} 
		catch (MalformedURLException e) {
		System.out.println("B³¹d wytwarzania URL");
			e.printStackTrace();
		}
	}
	
	
	private Component mediaControlComponent = null;
	private Player player = null;
	private URL mediaFileURL = null;
	
}
