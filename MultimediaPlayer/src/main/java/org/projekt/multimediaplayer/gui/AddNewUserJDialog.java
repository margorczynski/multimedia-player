package org.projekt.multimediaplayer.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import org.projekt.multimediaplayer.model.User;

public class AddNewUserJDialog extends JDialog
{

	public AddNewUserJDialog(JFrame owner)
	{
		super(owner, "Dodaj nowego u¿ytkownika");

		initComponents();

		arrangeDialog();

		setSize(300, 250);
		setResizable(false);
	}

	public void arrangeDialog()
	{
		clearEditText();

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout());

		// Naglowek
		mainPanel.add(labelHeadline, new GBC(0, 0, 2, 1).setAnchor(GBC.CENTER));

		mainPanel.add(labelName, new GBC(0, 2).setAnchor(GBC.EAST).setInsets(20, 15, 0, 0));
		mainPanel.add(userName, new GBC(1, 2).setAnchor(GBC.WEST).setInsets(20, 15, 0, 0));

		mainPanel.add(labelPassword, new GBC(0, 4).setAnchor(GBC.EAST).setInsets(10, 15, 0, 0));
		mainPanel.add(userPassword, new GBC(1, 4).setAnchor(GBC.WEST).setInsets(10, 15, 0, 0));

		// Przyciski
		mainPanel.add(buttonAddUser, new GBC(0, 6).setAnchor(GBC.CENTER).setInsets(30, 20, 0, 0));
		mainPanel.add(buttonCancel, new GBC(1, 6).setAnchor(GBC.CENTER).setInsets(30, 20, 0, 0));

		this.setContentPane(mainPanel);
	}

	public void clearEditText()
	{
		userName.setText("");
		userPassword.setText("");
	}

	public void initComponents()
	{

		labelHeadline = new JLabel("<html><h2><i><b> Dodaj nowego u¿ytkownika </b></i></h2></html>");
		labelHeadline.setAlignmentX(CENTER_ALIGNMENT);

		userName = new JTextField();
		userName.setColumns(10);

		userPassword = new JPasswordField();
		userPassword.setColumns(10);

		labelName = new JLabel("U¿ytkownik:");
		labelPassword = new JLabel("Has³o:");

		buttonAddUser = new JButton("Dodaj");
		buttonCancel = new JButton("Anuluj");

		buttonAddUser.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				// TODO Pobierz nazwe, haslo i dodaj do BD

			}
		});

		buttonCancel.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				clearEditText();
				setVisible(false);
			}
		});

	}

	JLabel labelHeadline;

	JTextField userName;
	JPasswordField userPassword;

	JLabel labelName;
	JLabel labelPassword;

	JButton buttonAddUser;
	JButton buttonCancel;

	User newUser;
}
