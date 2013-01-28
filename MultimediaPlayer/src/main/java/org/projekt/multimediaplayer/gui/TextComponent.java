package org.projekt.multimediaplayer.gui;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingWorker;

import java.awt.*;

/*
 * JComponent odpowiedzialny za wyswietlanie przesuwajacego sie tekstu
 */


public final class TextComponent extends JComponent
{
	public TextComponent(String text)
	{
		this.text = text + " ";
		
		visibleTextLabel.setFont(new Font("Serif", Font.ITALIC, 25));
		visibleTextLabel.setForeground(Color.white);
		visibleTextLabel.setBackground(Color.DARK_GRAY);

		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() 
		{
		    @Override
			public Void doInBackground() 
		    {
		        scrollText();
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
				if(scrollSpeed != 0)
				{
					Thread.sleep(300/scrollSpeed);
				}
				else
				{
					while(scrollSpeed == 0) Thread.sleep(100);
				}
				
				char c = text.charAt(0);
				
		        String rest = text.substring(1);
		        
		        text = rest + c;
		        
		        visibleTextLabel.setText(text.substring(0, 75));
			
				offset = (++offset) % (text.length()+1);
				
				repaint();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		g.setColor(Color.white);
		
		visibleTextLabel.setSize(visibleTextLabel.getPreferredSize());
		
		visibleTextLabel.paint(g);
		
	}
	
	public Dimension getPreferredSize()
	{
		return new Dimension(this.getParent().getWidth(), 30);
	}

	
	public void setScrollSpeed(int scrollSpeed)
	{
		this.scrollSpeed = scrollSpeed;
	}


	private volatile int scrollSpeed = 2;
	
	private JLabel visibleTextLabel = new JLabel("");
	
	private volatile int offset = 0;
	
	private String text;
}
