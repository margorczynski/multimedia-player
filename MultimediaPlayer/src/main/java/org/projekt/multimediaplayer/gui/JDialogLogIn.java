package org.projekt.multimediaplayer.gui;

import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;

import org.projekt.multimediaplayer.dao.UserDao;
import org.projekt.multimediaplayer.model.Schedule;
import org.projekt.multimediaplayer.model.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JDialogLogIn extends JDialog
{

	public JDialogLogIn(JFrame owner)
	{
		super(owner, windowTitle, ModalityType.APPLICATION_MODAL);

		windowOwner = (MultimediaPlayerJFrame) owner;
		initComponents();
		Point point = owner.getLocationOnScreen();
		this.setLocation((int) point.getX() + 400, (int) point.getY() + 200);
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
		mainPanel.add(buttonLogIn, new GBC(0, 6).setAnchor(GBC.CENTER).setInsets(30, 20, 0, 0));
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

		labelHeadline = new JLabel("<html><h2><i><b> Podaj swoje dane logowania </b></i></h2></html>");
		labelHeadline.setAlignmentX(CENTER_ALIGNMENT);

		userName = new JTextField();
		userName.setColumns(10);

		userPassword = new JPasswordField();
		userPassword.setColumns(10);

		labelName = new JLabel("U¿ytkownik:");
		labelPassword = new JLabel("Has³o:");

		buttonLogIn = new JButton("Zaloguj");
		buttonCancel = new JButton("Anuluj");

		buttonLogIn.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				isLogIn = false;
				char[] pass = userPassword.getPassword();

				if ((!(userName.getText().isEmpty()) && !(pass.length == 0)))
				{
					loginUser = new User();
					loginUser.setUsername(userName.getText());
					loginUser.setPassword(pass);

					List<User> listaUzytkownikow = userDao.findUsers(loginUser.getUsername());

					// czy jest uzytkownik za ktorego chcemy sie zalogowac
					if (listaUzytkownikow.size() != 0)
					{

						// czy haslo sie zgadza
						if (listaUzytkownikow.get(0).getPassword().equals(loginUser.getPassword()))
						{
							JOptionPane.showMessageDialog(JDialogLogIn.this, "Zalogowano !!", "Logowanie !", JOptionPane.INFORMATION_MESSAGE);
							isLogIn = true;
							windowOwner.disableButtonsWhenLogIn();
							setVisible(false);
						}

						else
						{
							JOptionPane.showMessageDialog(JDialogLogIn.this, "Podane has³o nie pasuje do u¿ytkownika !!", "B³¹d has³a !", JOptionPane.WARNING_MESSAGE);
							loginUser = null;
						}
					}
					else
					{
						JOptionPane.showMessageDialog(JDialogLogIn.this, "Nie znaleziono podanego u¿ytkownika !", "Nie ma takiego u¿ytkownika", JOptionPane.WARNING_MESSAGE);
						loginUser = null;
					}
				}
				else
				{
					JOptionPane.showMessageDialog(JDialogLogIn.this, "Musisz podaæ login i has³o !", "Uzupe³nij dane", JOptionPane.WARNING_MESSAGE);
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
		mainPanel.add(buttonLogIn, new GBC(0, 6).setAnchor(GBC.CENTER).setInsets(30, 20, 0, 0));
		mainPanel.add(buttonCancel, new GBC(1, 6).setAnchor(GBC.CENTER).setInsets(30, 20, 0, 0));

		this.setContentPane(mainPanel);

	}

	public User getLogInUser()
	{
		return loginUser;
	}

	public boolean isLogInOk()
	{
		return isLogIn;
	}

	public void setIsLogInFalse()
	{
		isLogIn = false;
	}

	private final ApplicationContext appContext = new ClassPathXmlApplicationContext("application-context.xml");
	private final UserDao userDao = (UserDao) appContext.getBean("userDao");

	private boolean isLogIn = false;
	private MultimediaPlayerJFrame windowOwner;
	private JLabel labelHeadline;

	private JTextField userName;
	private JPasswordField userPassword;

	private JLabel labelName;
	private JLabel labelPassword;

	private JButton buttonLogIn;
	private JButton buttonCancel;

	private static String windowTitle = "Logowaine";
	private 	User loginUser = null;
}
