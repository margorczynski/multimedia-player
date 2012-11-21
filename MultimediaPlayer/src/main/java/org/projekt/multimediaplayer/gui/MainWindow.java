package org.projekt.multimediaplayer.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JToolBar;

public class MainWindow extends JFrame 
{

	
	public MainWindow()
	{
	
		super();
		this.setMinimumSize(new Dimension(WINDOW_WIDTH,WINDOW_HEIGHT));
		this.setTitle( WINDOW_TITLE);
		
		initComponents();
	}
	
	public void initComponents()
	{
		
		
		this.setLayout( new BorderLayout());
		
		// Menu Bar
		menuBar = new JMenuBar();
		JMenu jMenuEdit = new JMenu("Opcje");
		JMenu jMenuOprogramie = new JMenu("O Programie");
		
		menuBar.add(jMenuEdit);
		menuBar.add(jMenuOprogramie);
		this.add(menuBar,BorderLayout.NORTH);
		
		
		
		
		// Lewy  panel z menu
		leftMenuPanel = new LeftMenuPanel_v2();
		this.add(leftMenuPanel, BorderLayout.WEST);
		
		
		
		
		
		
		// Prawy panel z odtwarzaczem
		rightPlayerPanel = new PlayerPanel();
		this.add(rightPlayerPanel, BorderLayout.CENTER);
		
		
		//tool bar
		toolBar = new JToolBar();
		toolBar.setRollover(false);
		this.add(toolBar,BorderLayout.SOUTH);
		pack();
		
		
	}
	
	PlayerPanel rightPlayerPanel;
	LeftMenuPanel_v2 leftMenuPanel;
	JMenuBar menuBar ;
	JToolBar toolBar;
	static final int WINDOW_WIDTH  = 1000;
	static final int WINDOW_HEIGHT = 700;
	static final String WINDOW_TITLE = "Odtwarzacz multimedialny z harmonogramem";

}
