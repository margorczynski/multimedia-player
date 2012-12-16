package org.projekt.multimediaplayer.gui;

import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;

import org.projekt.multimediaplayer.dao.UserDao;
import org.projekt.multimediaplayer.model.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JDialogCreateNewUser extends JDialog
{

	public JDialogCreateNewUser(JFrame owner)
	{
		super(owner, "Dodaj nowego u¿ytkownika");

		initComponents();
		
		Point point = owner.getLocationOnScreen();
		this.setLocation((int)point.getX()+400, (int)point.getY()+200);
		
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

				if ((userName.getText() != "") && (userPassword.getPassword() != null))
				{

					newUser = new User();

					newUser.setUsername(userName.getText());
					newUser.setPassword(userPassword.getPassword());
					List<User> listaUzytkownikow = userDao.findUsers(newUser.getUsername());

					if (listaUzytkownikow.size() == 0)
					{
						userDao.saveUser(newUser);
						JOptionPane.showMessageDialog(JDialogCreateNewUser.this, "U¿ytkownik zosta³ dodany !", "Dodano u¿ytkownika", JOptionPane.INFORMATION_MESSAGE);
						setVisible(false);
						clearEditText();
					}
					else
					{
						JOptionPane.showMessageDialog(JDialogCreateNewUser.this, "Taki u¿ytkownik ju¿ istnieje !", "Taki u¿ytkownik ju¿ istnieje", JOptionPane.WARNING_MESSAGE);
					}

				}

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

	private String passToString(char[] a)
	{
		StringBuilder pas = new StringBuilder();
		pas.append(a);
		return pas.toString();
	}

	private final ApplicationContext appContext = new ClassPathXmlApplicationContext("application-context.xml");
	private final UserDao userDao = (UserDao) appContext.getBean("userDao");

	private JLabel labelHeadline;

	private JTextField userName;
	private JPasswordField userPassword;

	private JLabel labelName;
	private JLabel labelPassword;

	private JButton buttonAddUser;
	private JButton buttonCancel;

	private User newUser;
}
