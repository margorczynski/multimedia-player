package org.projekt.multimediaplayer.main;

import java.awt.BorderLayout;
import java.awt.GraphicsEnvironment;
import java.awt.GraphicsDevice;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import org.projekt.multimediaplayer.gui.MultimediaPlayerBareJFrame;

public class MultimediaPlayerBareMain
{

	public static void main(final String[] args)
	{
		

		final JFrame frame = new MultimediaPlayerBareJFrame();
		
		//gs.setFullScreenWindow(frame);

		//frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setDefaultLookAndFeelDecorated(false);
		
		
	}

}
