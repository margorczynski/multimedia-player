package org.projekt.multimediaplayer.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class LeftMenuPanel_v2 extends JPanel 
{
	public LeftMenuPanel_v2()
	{
		super();
		initComponents();
		
	}
	
	
	public void initHarmonogramTree()
	{
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Lista harmonogramów");
		
		//Pobierz z BD harmonogramy i wstaw je do modelu drzewa
		DefaultMutableTreeNode harm1 = new DefaultMutableTreeNode("Super harmonogram");
			root.add(harm1);
			harm1.add( new DefaultMutableTreeNode("Pozycja 1"));
			harm1.add( new DefaultMutableTreeNode("Pozycja 2"));
			harm1.add( new DefaultMutableTreeNode("Pozycja 3"));
			harm1.add( new DefaultMutableTreeNode("Pozycja 4"));
		
		DefaultMutableTreeNode harm2 = new DefaultMutableTreeNode("Inny harmonogram");
			root.add(harm2);
			harm2.add( new DefaultMutableTreeNode("Pozycja 1"));
			harm2.add( new DefaultMutableTreeNode("Pozycja 2"));
			harm2.add( new DefaultMutableTreeNode("Pozycja 3"));
			harm2.add( new DefaultMutableTreeNode("Pozycja 4"));
			harm2.add( new DefaultMutableTreeNode("Pozycja 5"));
			harm2.add( new DefaultMutableTreeNode("Pozycja 6"));
			harm2.add( new DefaultMutableTreeNode("Pozycja 7"));
			harm2.add( new DefaultMutableTreeNode("Pozycja 8"));
			harm2.add( new DefaultMutableTreeNode("Pozycja 9"));
			harm2.add( new DefaultMutableTreeNode("Pozycja 10"));
			harm2.add( new DefaultMutableTreeNode("Pozycja 11"));
		
		DefaultTreeModel treeModel = new DefaultTreeModel(root);
		treeHarmonogram = new JTree(treeModel);
		treeHarmonogram.setBackground(Color.lightGray);
	}
	
	
	public void initPanelHarmonogram()
	{
		//modelowanie drzewa
		initHarmonogramTree();
		
		jPanelHarmonogram.setLayout(new GridLayout(6,1));
		JScrollPane scrollPaneTree = new JScrollPane(treeHarmonogram);
		scrollPaneTree.setPreferredSize(new Dimension(LEFT_MENU_PANEL_WIDTH,400));
		scrollPaneTree.setMinimumSize(new Dimension(LEFT_MENU_PANEL_WIDTH,400));
		jPanelHarmonogram.add(scrollPaneTree);
		
		//************** Informacje o harmonogramie *******
		jPanelHarmonogram.add(new JLabel("Informacje o wybranym harmonogramie"));
		JPanel panelInfAboutHarm = new JPanel();
			JLabel textHarmName = new JLabel("Nazwa harmonogramu :");
			JLabel textDateStart = new JLabel("Data rozpoczêcia :");
			JLabel textDateAdd = new JLabel ("Data dodania harmongramu:");
		JLabel editHarmName = new JLabel("testowa_nazwa");
		JLabel editDateStart = new JLabel("12:00:00");
		JLabel editDateAdd = new JLabel("2012.01.01");
		
		panelInfAboutHarm.setPreferredSize(new Dimension(LEFT_MENU_PANEL_WIDTH, 200));
		panelInfAboutHarm.setLayout(new GridLayout(3,2));
		
		panelInfAboutHarm.add(textHarmName);
			panelInfAboutHarm.add(editHarmName);
		
		panelInfAboutHarm.add(textDateStart);
			panelInfAboutHarm.add(editDateStart);
	
		panelInfAboutHarm.add(textDateAdd);
			panelInfAboutHarm.add(editDateAdd);
		
		jPanelHarmonogram.add(panelInfAboutHarm);
		//*********************
		
		
		//************** Informacje o pozycji harmonogramu *******
				jPanelHarmonogram.add(new JLabel("Informacje o wybranym pliku"));
				JPanel panelInfAboutFile = new JPanel();
					JLabel textFileAdr = new JLabel("Sciezka :");
					JLabel textFileLengthTime = new JLabel("Czas trwania pliku :");
					JLabel textFileSize = new JLabel ("Rozmiar :");
					JLabel textTyp = new JLabel ("Typ :");
					
					JLabel editFileAdr = new JLabel("C:/a.mp3");
					JLabel editFileLengthTime = new JLabel("1:23:23");
					JLabel editFileSize = new JLabel ("3123kb");
					JLabel editTyp = new JLabel ("Audio");
				
					panelInfAboutFile.setPreferredSize(new Dimension(LEFT_MENU_PANEL_WIDTH, 200));
					panelInfAboutFile.setLayout(new GridLayout(4,2));
				
					panelInfAboutFile.add(textFileAdr);
					panelInfAboutFile.add(editFileAdr);
				
					panelInfAboutFile.add(textFileLengthTime);
					panelInfAboutFile.add(editFileLengthTime);
			
					panelInfAboutFile.add(textFileSize);
					panelInfAboutFile.add(editFileSize);
					
				
					panelInfAboutFile.add(textTyp);
					panelInfAboutFile.add(editTyp);
					
				jPanelHarmonogram.add(panelInfAboutFile);
		//*********************
		
		//*** Edycja harmonogramu
				JPanel buttonPanel = new JPanel();
				//buttonPanel.setLayout(new BorderLayout());
				
				JButton buttonEditHarm  = new JButton("Edytuj Harmonogram");
				JButton buttonEditPozHarm  = new JButton("Edytuj pozycje");
				
				buttonPanel.add(buttonEditPozHarm);
				buttonPanel.add(buttonEditHarm);
			
				jPanelHarmonogram.add(buttonPanel);
		
		
		JPanel panelInfOPozycji;
		
	}
	
	
	public void initComponents()
	{
		
		this.setLayout(new BorderLayout());
		
		tabbedPane = new JTabbedPane();
		//tabbedPane.setPreferredSize(new Dimension(300,300));
		jPanelBD = new JPanel();
		jPanelHarmonogram = new JPanel();
		jPanelOdtwarzanie = new JPanel();
		
		//Polacz JTabbedPane
		tabbedPane.addTab("Harmonogram", jPanelHarmonogram);
		tabbedPane.addTab("BD", jPanelBD);
		tabbedPane.addTab("Odtwarzanie",jPanelOdtwarzanie);
		
		
		
		//Harmonogram dodaj rzeczy
		initPanelHarmonogram();
		
	
		
		this.add(tabbedPane);
		this.setPreferredSize(new Dimension(LEFT_MENU_PANEL_WIDTH, MainWindow.WINDOW_HEIGHT));
		this.setVisible(true); 

	}
	
	
	private JTree treeHarmonogram;
	private JTabbedPane tabbedPane;
	private JPanel jPanelHarmonogram;
	private JPanel jPanelBD;
	private JPanel jPanelOdtwarzanie;
	
	static final int LEFT_MENU_PANEL_WIDTH = 350;
}
