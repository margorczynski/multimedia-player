package org.projekt.multimediaplayer.gui;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.acl.Owner;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.*;

import java.text.*;

import org.projekt.multimediaplayer.dao.ScheduleDao;
import org.projekt.multimediaplayer.dao.UserDao;
import org.projekt.multimediaplayer.model.Schedule;
import org.projekt.multimediaplayer.model.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JDialogCreateNewHarm extends JDialog
{

	
	public JDialogCreateNewHarm(MultimediaPlayerJFrame owner)//, User user, Schedule activeSchedule)
	{
		super(owner, windowTitle, ModalityType.APPLICATION_MODAL);
		//this.user = user;
		this.ownerFrame = owner;
		//this.activeSchedule = activeSchedule;
		initComponents();
		setSize(480, 525);
		setResizable(false);
	}

	public void clearEdit()
	{
		editHarmDescript.setText("");
		editHarmonogramName.setText("");
	}

	public void initComponents()
	{
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout());

		labelHeadline = new JLabel("<html><h2><i><b> Utwórz harmonogram </b></i></h2></html>");
		labelHeadline.setAlignmentX(CENTER_ALIGNMENT);

		// init var
		labelHarmonogramName = new JLabel();
		labelHarmonogramDesript = new JLabel();
		checkBoxActiv = new JCheckBox();
		buttonCreateHarm = new JButton();
		buttonCancel = new JButton();
		editHarmonogramName = new JTextField();
		editHarmDescript = new JTextArea();
		radioPlayInLoop = new JRadioButton();
		radioOneTimePlay = new JRadioButton();
		labelStartDate = new JLabel();
		labelStartTime = new JLabel();

		labelPlayFreq = new JLabel();
		spinerPlayFreq = new JSpinner();

		labelHarmonogramName.setText("Nazwa harmonogramu:");
		editHarmonogramName.setColumns(30);
		editHarmonogramName.setText("");

		labelHarmonogramDesript.setText("Opis harmonogramu:");

		checkBoxActiv.setText("Ustawiæ aktywnym");

		buttonCreateHarm.setText("Utwórz harmonogram");
		buttonCancel.setText("Anuluj");

		radioPlayInLoop.setSelected(true);
		radioPlayInLoop.setText("Odtwarzanie cykliczne");
		radioOneTimePlay.setText("Odtwarzanie jednorazowo");

		ButtonGroup radioButtonGroup = new ButtonGroup();
		radioButtonGroup.add(radioOneTimePlay);
		radioButtonGroup.add(radioPlayInLoop);

		labelStartDate.setText("Czas i data rozpoczêcia wyœwietlania");

		labelStartTime.setText("Godzina rozpoczêcia");

		spinerDateModel = new SpinnerDateModel();
		JSpinner spinerStartDate = new JSpinner(spinerDateModel);

		labelPlayFreq.setText("Czêstotliwoœæ odtwarzania");

		editHarmDescript.setColumns(30);
		editHarmDescript.setRows(5);

		JScrollPane editHarmDescriptScrollPane = new JScrollPane(editHarmDescript);

		mainPanel.add(labelHeadline, new GBC(0, 0, 2, 1).setAnchor(GBC.CENTER).setInsets(5, 5, 0, 0));
		mainPanel.add(labelHarmonogramName, new GBC(0, 1).setAnchor(GBC.WEST).setInsets(5, 5, 0, 0));
		mainPanel.add(editHarmonogramName, new GBC(0, 2, 2, 1).setAnchor(GBC.WEST).setInsets(5, 5, 0, 0));
		mainPanel.add(labelHarmonogramDesript, new GBC(0, 3).setAnchor(GBC.WEST).setInsets(20, 5, 0, 0));
		mainPanel.add(editHarmDescriptScrollPane, new GBC(0, 4, 2, 1).setAnchor(GBC.WEST).setInsets(5, 5, 0, 0));
		mainPanel.add(checkBoxActiv, new GBC(0, 5).setAnchor(GBC.WEST).setInsets(10, 5, 0, 0));
		mainPanel.add(radioPlayInLoop, new GBC(0, 6).setAnchor(GBC.WEST).setInsets(10, 5, 0, 0));
		mainPanel.add(radioOneTimePlay, new GBC(1, 6).setAnchor(GBC.WEST).setInsets(10, 5, 0, 0));

		mainPanel.add(labelStartDate, new GBC(0, 7, 2, 1).setAnchor(GBC.WEST).setInsets(5, 5, 0, 0));

		mainPanel.add(spinerStartDate, new GBC(0, 8).setAnchor(GBC.WEST).setInsets(5, 5, 0, 0));


		mainPanel.add(buttonCreateHarm, new GBC(0, 11).setAnchor(GBC.CENTER).setInsets(20, 5, 25, 0));
		mainPanel.add(buttonCancel, new GBC(1, 11).setAnchor(GBC.CENTER).setInsets(20, 5, 25, 0));

		this.setContentPane(mainPanel);
		pack();

		buttonCancel.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent arg0)
			{
				setVisible(false);
			}
		});

		this.getRootPane().setDefaultButton(buttonCreateHarm);
		buttonCreateHarm.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				ownerFrame.refreshActiveSchedule();
				isNewActivSched = false;
				// czu jakis uzytkownik jest zalogowany
				if (ownerFrame.getLogInUser() != null)
				{
					// nazwa harmonogramu konieczna
					if (editHarmonogramName.getText() != "")
					{
						Schedule harm = new Schedule();
						harm.setName(editHarmonogramName.getText());
						harm.setActive(checkBoxActiv.isSelected());
						harm.setDescription(editHarmDescript.getText());
						harm.setPeriodically(radioPlayInLoop.isSelected());
						harm.setUser(ownerFrame.getLogInUser());
						harm.setStartTime(spinerDateModel.getDate());

						List<Schedule> listSchedule = scheduleDao.findShedule(harm.getName());

						if (listSchedule.size() == 0)
						{

							// Dodany nowy harmonogram jest harmonogramem
							// aktywnym
							// usun status aktywny w pozostalych harmonogramach

							if (harm.isActive())
							{
								//czy mamy aktywny harmonogram ?
								if (ownerFrame.getActiveShedule() != null)
								{
									System.out.println("Aktywny stal sie nie aktywny");
									Schedule schedule = ownerFrame.getActiveShedule();
									schedule.setActive(false);
									scheduleDao.updateSchedule(schedule);	
								}
														
								scheduleDao.saveSchedule(harm);
								isNewActivSched = true;
							}

							//nie jest to harmonogram aktywny poprostu go sobie dodamy
							else 
							{
								scheduleDao.saveSchedule(harm);						
							}
							
				
							JOptionPane.showMessageDialog(JDialogCreateNewHarm.this, "Harmonogram zosta³ dodany prawid³owo", "Dodanie nowego harmonogramu", JOptionPane.INFORMATION_MESSAGE);
							setVisible(false);
							clearEdit();
						}//if czy jest taki harmonogram
					
						else
						{
							JOptionPane.showMessageDialog(JDialogCreateNewHarm.this, "Harmonogram o takiej nazwie ju¿ istnieje !!", "Dodanie nowego harmonogramu", JOptionPane.WARNING_MESSAGE);
						}
					}//name of harm
				}// user != null
				ownerFrame.refreshActiveSchedule();
			}
		});
	}
	
	public boolean isNewActivSched()
	{
		return isNewActivSched;
	}

	
	private static String windowTitle = "Utworz nowy harmonogram";
	private  boolean isNewActivSched = false;

	private JLabel labelHeadline;
	private MultimediaPlayerJFrame ownerFrame;
	private JButton buttonCancel;
	private JButton buttonCreateHarm;
	private JCheckBox checkBoxActiv;
	private JScrollPane descriptScrollPane;
	private JTextArea editHarmDescript;
	private JTextField editHarmonogramName;
	private JLabel labelHarmonogramDesript;
	private JLabel labelHarmonogramName;
	private JLabel labelPlayFreq;
	private JLabel labelStartDate;
	private JLabel labelStartTime;
	private JRadioButton radioOneTimePlay;
	private JRadioButton radioPlayInLoop;
	private JSpinner spinerPlayFreq;
	private JSpinner spinerStartDate;
	private JSpinner spinerStartTime;

	private SpinnerDateModel spinerDateModel;

	//private User user;
	//private Schedule activeSchedule;

	private final ApplicationContext appContext = new ClassPathXmlApplicationContext("application-context.xml");
	private final ScheduleDao scheduleDao = (ScheduleDao) appContext.getBean("scheduleDao");
	private final UserDao userDao = (UserDao) appContext.getBean("userDao");
}
