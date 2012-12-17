package org.projekt.multimediaplayer.gui;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.swing.SwingWorker;

import java.awt.*;

public final class TextComponent extends JComponent
{
	public TextComponent(String text)
	{
		this.text = text;
		
		setSize(100, 100);

		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() 
		{
		    public Void doInBackground() 
		    {
		        scrollText();
		        System.out.println("EXIT");
		        return null;
		    }
		};
		
		worker.execute();
	}
	
	private void scrollText()
	{
		
		boolean run = true;
		
		try
		{
			while(run) 
			{
				Thread.sleep(100);
			
				offset = (++offset) % 100;

				repaint();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void paint(Graphics g)
	{
		g.drawString(text, offset, 0);

	}
	
	private volatile int offset = 0;
	
	private String text;
}
