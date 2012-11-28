package org.projekt.multimediaplayer.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;

import org.projekt.multimediaplayer.dao.UserDao;
import org.projekt.multimediaplayer.model.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ShowHarmonogramsDialog extends JDialog
{

	public ShowHarmonogramsDialog(JFrame owner)
	{
		super(owner, "Podgl¹d harmonogramu");

		initComponents();
		initActionLisnerButtons();
		 setSize(770, 610);
		setResizable(false);
	}

	public void loadData()
	{
		// TODO Uzupe³niæ dane ;)

	}

	public void initActionLisnerButtons()
	{
		closeButton.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				setVisible(false);
			}
		});

		addNewMediaButton.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub

			}
		});

		addNewHarmonogramButton.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub

			}
		});

		deleteHarmMediaButton.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub

			}
		});

	}

	public void initComponents()
	{

		// * * M a i n P a n e l
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout());

		labelHeadline = new JLabel("<html><h2><i><b> Informacje o harmonogramie </b></i></h2></html>");
		labelHeadline.setAlignmentX(CENTER_ALIGNMENT);

		// * * H a r m o n o g r a m
		harmonogramPanel = new JPanel(new GridBagLayout());

		labelHarmonogramName = new JLabel("Nazwa harmonogramu: ");
		labelHarmonogramDescript = new JLabel("Opis harmonogramu: ");
		labelHarmonogramActive = new JLabel("Aktywny: ");

		editHarmonogramName = new JTextField(" ");
		editHarmonogramName.setColumns(15);

		editHarmonogramDescript = new JTextField(" ");
		editHarmonogramDescript.setColumns(15);

		editHarmonogramActive = new JTextField(" ");
		editHarmonogramActive.setColumns(15);
		// GBC(kolumna, wiersz, ile kolumn, ile wierszy)
		harmonogramPanel.add(labelHarmonogramName, new GBC(0, 0).setAnchor(GBC.EAST).setInsets(10, 15, 0, 0));
		harmonogramPanel.add(labelHarmonogramDescript, new GBC(0, 1).setAnchor(GBC.EAST).setInsets(10, 15, 0, 0));
		harmonogramPanel.add(labelHarmonogramActive, new GBC(0, 2).setAnchor(GBC.EAST).setInsets(10, 15, 0, 0));

		harmonogramPanel.add(editHarmonogramName, new GBC(1, 0).setAnchor(GBC.EAST).setInsets(10, 15, 0, 0));
		harmonogramPanel.add(editHarmonogramDescript, new GBC(1, 1).setAnchor(GBC.EAST).setInsets(10, 15, 0, 0));
		harmonogramPanel.add(editHarmonogramActive, new GBC(1, 2).setAnchor(GBC.EAST).setInsets(10, 15, 0, 0));

		// * * M u l t i m e d i a
		filePanel = new JPanel(new GridBagLayout());
		labelFileName = new JLabel("Nazwa pliku: ");
		labelFileDescript = new JLabel("Opis: ");
		labelPlayBackDate = new JLabel("Data dodania: ");
		labelSequence = new JLabel("Kolejnoœæ: ");
		labelFileLength = new JLabel("D³ugoœæ: ");
		labelFileFormat= new JLabel("Format: ");
		
		editFileName = new JTextField(" ");
		editFileName.setColumns(15);

		editFileDescript = new JTextField(" ");
		editFileDescript.setColumns(15);

		editPlayBackDate = new JTextField(" ");
		editPlayBackDate.setColumns(15);

		editSequence = new JTextField("");
		editSequence.setColumns(15);

		editFileLength = new JTextField(" ");
		editFileLength.setColumns(15);
		
		editFileFormat = new JTextField();
		editFileFormat.setColumns(15);
		
		
		
		filePanel.add(labelFileName, new GBC(0, 0).setAnchor(GBC.EAST).setInsets(10, 15, 0, 0));
		filePanel.add(labelFileDescript, new GBC(0, 1).setAnchor(GBC.EAST).setInsets(10, 15, 0, 0));
		filePanel.add(labelPlayBackDate, new GBC(0, 2).setAnchor(GBC.EAST).setInsets(10, 15, 0, 0));
		filePanel.add(labelSequence, new GBC(0, 3).setAnchor(GBC.EAST).setInsets(10, 15, 0, 0));
		filePanel.add(labelFileLength, new GBC(0, 4).setAnchor(GBC.EAST).setInsets(10, 15, 0, 0));
		filePanel.add(labelFileFormat, new GBC(0, 5).setAnchor(GBC.EAST).setInsets(10, 15, 0, 0));
		

		filePanel.add(editFileName, new GBC(1, 0).setAnchor(GBC.WEST).setInsets(10, 15, 0, 0));
		filePanel.add(editFileDescript, new GBC(1, 1).setAnchor(GBC.WEST).setInsets(10, 15, 0, 0));
		filePanel.add(editPlayBackDate, new GBC(1, 2).setAnchor(GBC.WEST).setInsets(10, 15, 0, 0));
		filePanel.add(editSequence, new GBC(1, 3).setAnchor(GBC.WEST).setInsets(10, 15, 0, 0));
		filePanel.add(editFileLength, new GBC(1, 4).setAnchor(GBC.WEST).setInsets(10, 15, 0, 0));
		filePanel.add(editFileFormat, new GBC(1, 5).setAnchor(GBC.WEST).setInsets(10, 15, 0, 0));

		// * * D r z e w o
		treeHarmonogram = new JTree();
		scrollPaneTree = new JScrollPane(treeHarmonogram);
		scrollPaneTree.setPreferredSize(new Dimension(350, 400));
		
		JPanel buttonsPanel = new JPanel(new GridBagLayout());

		closeButton = new JButton(" Zamknij ");
		addNewMediaButton = new JButton(" Dodaj plik ");
		addNewHarmonogramButton = new JButton(" Dodaj harmonogram ");
		deleteHarmMediaButton = new JButton(" Usun ");

		buttonsPanel.add(addNewMediaButton, new GBC(0, 0).setAnchor(GBC.EAST).setInsets(20, 15, 0, 0));
		buttonsPanel.add(addNewHarmonogramButton, new GBC(1, 0).setAnchor(GBC.EAST).setInsets(20, 15, 0, 0));
		buttonsPanel.add(deleteHarmMediaButton, new GBC(2, 0).setAnchor(GBC.EAST).setInsets(20, 15, 0, 0));
		buttonsPanel.add(closeButton, new GBC(3, 0).setAnchor(GBC.EAST).setInsets(20, 15, 0, 0));

		mainPanel.add(labelHeadline, new GBC(0, 0, 2, 1).setAnchor(GBC.CENTER).setInsets(20, 15, 0, 0));
		mainPanel.add(scrollPaneTree, new GBC(0, 1, 1, 2).setAnchor(GBC.EAST).setInsets(20, 15, 0, 0));
		mainPanel.add(harmonogramPanel, new GBC(1, 1).setAnchor(GBC.EAST).setInsets(20, 15, 0, 0));
		mainPanel.add(filePanel, new GBC(1, 2).setAnchor(GBC.EAST).setInsets(20, 15, 0, 0));
		mainPanel.add(buttonsPanel, new GBC(0, 3, 2, 1).setAnchor(GBC.CENTER).setInsets(20, 15, 0, 0));

		this.setContentPane(mainPanel);
		this.pack();

	}

	// Buttony
	JButton closeButton;
	JButton addNewMediaButton;
	JButton addNewHarmonogramButton;
	JButton deleteHarmMediaButton;

	// Drzewo
	JTree treeHarmonogram;
	JScrollPane scrollPaneTree;

	// harmonogram
	JPanel harmonogramPanel;
	JLabel labelHeadline;
	JLabel labelHarmonogramName;
	JLabel labelHarmonogramDescript;
	JLabel labelHarmonogramActive;

	JTextField editHeadline;
	JTextField editHarmonogramName;
	JTextField editHarmonogramDescript;
	JTextField editHarmonogramActive;

	// opis pliku

	JPanel filePanel;
	JLabel labelFileName;
	JLabel labelFileDescript;
	JLabel labelPlayBackDate;
	JLabel labelSequence;
	JLabel labelFileLength;
	JLabel labelFileFormat;
	
	JTextField editFileName;
	JTextField editFileDescript;
	JTextField editPlayBackDate;
	JTextField editSequence;
	JTextField editFileLength;
	JTextField editFileFormat;
	
	User newUser;
}
