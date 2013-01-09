package org.projekt.multimediaplayer.gui;

import org.projekt.multimediaplayer.main.RssReader;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.GridLayout;
import javax.swing.JSlider;

public final class RssFrame extends JFrame
{
	public RssFrame()
	{
		RssReader rr = new RssReader();
		
		String headers = "";
		
		System.out.println("WE");
		
		this.setLayout(new GridLayout(0,1));
		
		for(String s : rr.getHeaders())
		{
			System.out.println(s);
			
			headers += s + "     ";
			
			add(new JLabel(s));
		}
		
		this.setVisible(true);
		
		System.out.println("WY");
		
		JPanel panel = new JPanel();
		
		panel.setLayout(new GridLayout(1,0));
		
		panel.add(new TextComponent(headers));
		
		JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 10, 3);
		
		slider.setMajorTickSpacing(10);
		slider.setMinorTickSpacing(1);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		
		panel.add(slider);
		
		add(panel);
	}
}
