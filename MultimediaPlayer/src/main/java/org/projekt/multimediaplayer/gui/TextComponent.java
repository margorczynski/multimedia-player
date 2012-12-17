package org.projekt.multimediaplayer.gui;

import javax.swing.JComponent;

import javax.swing.SwingWorker;

import java.awt.*;

public final class TextComponent extends JComponent
{
	public TextComponent(String text)
	{
		this.text = text;
		
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() 
		{
		    public Void doInBackground() 
		    {
		        scrollText();
		        return null;
		    }
		};
	}
	
	private void scrollText()
	{
		try
		{
			Thread.sleep(100);
			
			offset++;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void paint(Graphics g)
	{
		g.drawString(text, offset % 100, 0);
	}
	
	
	private volatile int offset = 0;
	
	private String text;
}
