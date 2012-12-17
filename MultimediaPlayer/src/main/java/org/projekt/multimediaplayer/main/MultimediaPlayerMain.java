package org.projekt.multimediaplayer.main;

import org.projekt.multimediaplayer.gui.MultimediaPlayerJFrame;

import javax.swing.JFrame;

public class MultimediaPlayerMain
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		JFrame frame = new MultimediaPlayerJFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}
