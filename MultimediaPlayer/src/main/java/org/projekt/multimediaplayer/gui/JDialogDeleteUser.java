package org.projekt.multimediaplayer.gui;

import java.awt.GridBagConstraints;
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

public class JDialogDeleteUser extends JDialog
{

	public JDialogDeleteUser(JFrame owner)
	{
		super(owner, windowTitle);

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
		mainPanel.add(labelHeadline, new GBC(0, 0, 2, 1).setAnchor(GridBagConstraints.CENTER));

		mainPanel.add(labelName, new GBC(0, 2).setAnchor(GridBagConstraints.EAST).setInsets(20, 15, 0, 0));
		mainPanel.add(userName, new GBC(1, 2).setAnchor(GridBagConstraints.WEST).setInsets(20, 15, 0, 0));

		mainPanel.add(labelPassword, new GBC(0, 4).setAnchor(GridBagConstraints.EAST).setInsets(10, 15, 0, 0));
		mainPanel.add(userPassword, new GBC(1, 4).setAnchor(GridBagConstraints.WEST).setInsets(10, 15, 0, 0));

		// Przyciski
		mainPanel.add(buttonAddUser, new GBC(0, 6).setAnchor(GridBagConstraints.CENTER).setInsets(30, 20, 0, 0));
		mainPanel.add(buttonCancel, new GBC(1, 6).setAnchor(GridBagConstraints.CENTER).setInsets(30, 20, 0, 0));

		this.setContentPane(mainPanel);
	}

	public void clearEditText()
	{
		userName.setText("");
		userPassword.setText("");
	}

	public void initComponents()
	{

		labelHeadline = new JLabel("<html><h2><i><b>" + windowTitle + "</b></i></h2></html>");
		labelHeadline.setAlignmentX(CENTER_ALIGNMENT);

		userName = new JTextField();
		userName.setColumns(10);

		userPassword = new JPasswordField();
		userPassword.setColumns(10);

		labelName = new JLabel("U¿ytkownik:");
		labelPassword = new JLabel("Has³o:");

		buttonAddUser = new JButton("Usuñ");
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

					// czy jest uzytkownik o taki loginie
					if (listaUzytkownikow.size() != 0)
					{
						// czy hasla sie zgadzaja
						if (listaUzytkownikow.get(0).getPassword().equals(newUser.getPassword()))
						{
							if (JOptionPane.showConfirmDialog(thisFrame, (Object) "Czy jesteœ pewien ze chcesz usun¹æ konto: \" " + userName.getText() + "\" ?", "Usuwanie konta", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
							{
								// TODO nieche sie usuwaæ ? luknij na to ( cos z sesja czy cos ? nie wiem) 
								userDao.deleteUser(newUser);
								JOptionPane.showMessageDialog(thisFrame, "U¿ytkownik zosta³ usuniêty!", "Usuniêto u¿ytkownika", JOptionPane.INFORMATION_MESSAGE);
								thisFrame.setVisible(false);
								clearEditText();
							}
						}
						else
						{
							JOptionPane.showMessageDialog(thisFrame, "Podane has³o nie pasuje do u¿ytkownika !!", "B³¹d has³a !", JOptionPane.WARNING_MESSAGE);
						}
					}
					else
					{
						JOptionPane.showMessageDialog(JDialogDeleteUser.this, "Nie znaleziono podanego u¿ytkownika !", "Nie ma takiego u¿ytkownika", JOptionPane.WARNING_MESSAGE);
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

	private JDialog thisFrame = this;
	private JLabel labelHeadline;

	private JTextField userName;
	private JPasswordField userPassword;

	private JLabel labelName;
	private 	JLabel labelPassword;

	private JButton buttonAddUser;
	private JButton buttonCancel;

	private 	User newUser;

	private static String windowTitle = "Usuñ  konto u¿ytkownika";
}
