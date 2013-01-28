package org.projekt.multimediaplayer.gui;

import static java.awt.SystemTray.getSystemTray;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import org.projekt.multimediaplayer.dao.UserDao;
import org.projekt.multimediaplayer.main.Configuration;
import org.projekt.multimediaplayer.model.Schedule;
import org.projekt.multimediaplayer.model.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.medialist.MediaList;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.FullScreenStrategy;
import uk.co.caprica.vlcj.player.list.MediaListPlayer;

import com.sun.jna.NativeLibrary;

public class MultimediaPlayerBareJFrame extends JFrame
{
	public MultimediaPlayerBareJFrame()
	{
		super();
		initComponents();
		thisFrame = this;

	}

	public void initIconsAndTry()
	{
		ImageIcon icon = new ImageIcon("multimedia/greenBig.png");
		this.setIconImage(icon.getImage());

		if (SystemTray.isSupported())
		{

			ImageIcon iconT = new ImageIcon("multimedia/green16.png");
			final TrayIcon iconTray = new TrayIcon(iconT.getImage());
			iconTray.setToolTip("Odtwrzacz multimedialny");
			iconTray.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					MultimediaPlayerBareJFrame.this.setVisible(true);
					MultimediaPlayerBareJFrame.this.setExtendedState(Frame.NORMAL);
					getSystemTray().remove(iconTray);
				}

			});

			addWindowListener(new WindowAdapter()
			{
				@Override
				public void windowIconified(WindowEvent e)
				{
					MultimediaPlayerBareJFrame.this.setVisible(false);
					try
					{
						getSystemTray().add(iconTray);
					}
					catch (AWTException e1)
					{
						e1.printStackTrace();
					}
				}

			});
		}
	}
	public void initComponents()
	{
		initIconsAndTry();
		
		Configuration config = (Configuration) appContext.getBean("config");
		config.loadConfigurationFromFile();

		// dowi¹zanie bibliotek z programu VLC

		// Marcin Twoje chyba xd ?
		// NativeLibrary.addSearchPath("libvlc", "D:/VLC64/");

		// Krzysiek

		NativeLibrary.addSearchPath("libvlc", config.getLibLocation());

		// Init JFrame elements
		this.setMinimumSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		this.setTitle("Odtwarzacz multimedialny");
		this.setLocation(100, 100);
		this.setSize(1050, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		logInUser = refreshUser(config.getDefaultUsername());

		multimediaPlayerBareJPanel = new MultimediaPlayerBareJPanel(this);// ,
																	// mediaPlayerComponent);
		
		this.setLayout(new BorderLayout());
		this.add(multimediaPlayerBareJPanel, BorderLayout.CENTER);
		refreshActiveSchedule();
		runRunnableSched();

	}

	public User refreshUser(String userName)
	{
		System.out.println("Bare frame refresh user");
		List<User> users = userDao.findUsers(userName);
		if (users != null && users.size() != 0) return users.get(0);

		return null;
	}

	public void refreshLogInUser()
	{
		if (logInUser != null)
		{
			List<User> users = userDao.findUsers(logInUser.getUsername());
			logInUser = users.get(0);
		}
		else
		{
			System.out.println("Nie ma zalogowanego uzytkownika bare!!! ");
		}
	}

	public void refreshActiveSchedule()
	{
		String currentActiveHarmName = "empty";
		if (activeSchedule != null) currentActiveHarmName = activeSchedule.getName();

		refreshLogInUser();
		boolean changeActiveSchedule = false;

		if (logInUser != null)
		{
			Set<Schedule> harmonogramy = logInUser.getUserSchedules();
			if (harmonogramy != null)
			{
				for (Schedule harm : harmonogramy)
				{
					if (harm.isActive())
					{
						changeActiveSchedule = true;
						activeSchedule = harm;
						break;
					}
				}
			}
			if (!changeActiveSchedule)
				activeSchedule = null;
			else
			{

				System.out.println("Brak harmonogramów !!");
			}
		}
		else
		{
			activeSchedule = null;
			System.out.println("Nie ma zalogowanego uzytkownika !!! ");
		}

		if (activeSchedule != null)
		{
			if (!currentActiveHarmName.equals(activeSchedule.getName()))
			{
				if (playerThread != null)
				{
					playerThread = null;
					runnableSchedu = null;

					System.gc();

					runnableSchedu = new PlayMultimediaFromSchedulRunnableBare(this, multimediaPlayerBareJPanel, activeSchedule, logInUser);
					playerThread = new Thread(runnableSchedu);
					playerThread.start();
				}
				else
				{
					runnableSchedu = new PlayMultimediaFromSchedulRunnableBare(this, multimediaPlayerBareJPanel, activeSchedule, logInUser);
					playerThread = new Thread(runnableSchedu);
					playerThread.start();
				}
			}
		}
	}

	public User getLogInUser()
	{
		return logInUser;
	}

	public Schedule getActiveShedule()
	{
		return activeSchedule;
	}

	public void setActiveSchedule(Schedule schedule)
	{
		this.activeSchedule = schedule;
	}

	public void activeScheduleSetNull()
	{
		activeSchedule = null;
	}

	public void suspendThread()
	{
		if (playerThread.isAlive()) runnableSchedu.closeThreadIfEnd();
		System.gc();
	}

	public void runRunnableSched()
	{
		System.out.println("Bare frame run runnable");
		playerThread = null;
		playerThread = new Thread(runnableSchedu);
		playerThread.start();
		System.out.println("Bare frame run runnable end");
	}

	public MultimediaPlayerBareJPanel getMultimediaPanel()
	{
		return multimediaPlayerBareJPanel;
	}

	public Thread playerThread = null;
	private PlayMultimediaFromSchedulRunnableBare runnableSchedu = null;

	private MultimediaPlayerBareJPanel multimediaPlayerBareJPanel = null;
	// Menu var
	private JMenuBar menuBar;

	private User logInUser = null;
	private Schedule activeSchedule = null;

	private MultimediaPlayerBareJFrame thisFrame;

	private MediaPlayerFactory mediaPlayerFactory;
	private EmbeddedMediaPlayerComponent mediaPlayerComponent;
	private EmbeddedMediaPlayer mediaPlayer;
	private MediaListPlayer mediaListPlayer;
	private MediaList mediaList;

	private FullScreenStrategy fullScreenStrategy;

	private static int WINDOW_WIDTH = 700;
	private static int WINDOW_HEIGHT = 700;

	private final ApplicationContext appContext = new ClassPathXmlApplicationContext("application-context.xml");
	private final UserDao userDao = (UserDao) appContext.getBean("userDao");
}
