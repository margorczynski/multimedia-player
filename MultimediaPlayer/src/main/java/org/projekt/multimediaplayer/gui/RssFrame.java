package org.projekt.multimediaplayer.gui;

import org.projekt.multimediaplayer.main.RssReader;

import javax.swing.JFrame;

import javax.swing.JLabel;

import java.awt.GridLayout;

public final class RssFrame extends JFrame
{
	public RssFrame()
	{
		RssReader rr = new RssReader();
		
		this.setLayout(new GridLayout(0,1));
		
		for(String s : rr.getHeaders())
		{
			add(new JLabel(s));
		}
		
		add(new TextComponent("Test"));
	}
}
