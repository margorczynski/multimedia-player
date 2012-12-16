package org.projekt.multimediaplayer.gui;

import java.awt.Dimension;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Set;

import javax.swing.*;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.medialist.MediaList;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.FullScreenStrategy;
import uk.co.caprica.vlcj.player.list.MediaListPlayer;
import uk.co.caprica.vlcj.player.list.MediaListPlayerMode;

import org.projekt.multimediaplayer.dao.UserDao;
import org.projekt.multimediaplayer.main.Configuration;
import org.projekt.multimediaplayer.model.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sun.jna.NativeLibrary;

public class MultimediaPlayerJFrame extends JFrame
{
	public MultimediaPlayerJFrame()
	{
		super();
		initComponents();
		initMenus();
		disableButtonsWhenLogOut();
		initMenuActionListener();
		thisFrame = this;

	}

	public void initMultimediaVlcj()
	{
		// Tworze fabryke obiektow vlcj
		mediaPlayerFactory = new MediaPlayerFactory();

		// Utworzenie listy odtwarzacza
		mediaListPlayer = mediaPlayerFactory.newMediaListPlayer();

		
		// Utworzenie listy(MediaList) dla ListMediaPlayera
		// MediaList dodaje sie do ListMediaPlayer do ktorego poprzednio trzeba
		// dowiazac EmbeddedMediaPlayer
		MediaList mediaList = mediaPlayerFactory.newMediaList();
		// Zdefiniowanie przyk�adowej listy odtwarzania
		
		//Sample
		mediaList.addMedia("multimedia\\sample\\a.mp3");
		mediaList.addMedia("multimedia\\sample\\x.wmv");
		
		

		mediaListPlayer.setMediaList(mediaList);
		// mediaListPlayer.setMode(MediaListPlayerMode.LOOP);

		// Utworzenie odtwarzacza
		mediaPlayerComponent = new EmbeddedMediaPlayerComponent();

		// Utworzenie EmbeddedMediaPlayer
		mediaPlayer = mediaPlayerFactory.newEmbeddedMediaPlayer();

		// mediaPlayer = mediaPlayerComponent.getMediaPlayer();

		// Dowiazanie listy odtwarzania PLAYERA (listMediaPlayer) do konkretnego
		// odtwarzacza
		mediaListPlayer.setMediaPlayer(mediaPlayer);

		// Ustawienie strategi odtwarzania
		mediaListPlayer.setMode(MediaListPlayerMode.LOOP);
	}

	public void initComponents()
	{

		Configuration config = (Configuration) appContext.getBean("config");
		config.createDefaultConfigurationFile();
		config.loadConfigurationFromFile();

		// dowi�zanie bibliotek z programu VLC

		// Marcin Twoje chyba xd ?
		// NativeLibrary.addSearchPath("libvlc", "D:/VLC64/");

		// Krzysiek
		System.out.println(config.getLibLocation());
		NativeLibrary.addSearchPath("libvlc", config.getLibLocation());

		// Init JFrame elements
		this.setMinimumSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		this.setTitle("Odtwarzacz multimedialny");
		this.setLocation(100, 100);
		this.setSize(1050, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

		
		initMultimediaVlcj();
		
		MultimediaPlayerJPanel pcp = new MultimediaPlayerJPanel(mediaPlayer, mediaListPlayer, mediaPlayerFactory);//, mediaPlayerComponent);
		this.add(pcp);
		// GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(this);
	}

	public void initMenus()
	{
		// Init menuBar
		menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);

		// Init sub menus
		jMenuFile = new JMenu("Plik");
		jMenuUzytkownik = new JMenu("U�ytkownik");
		jMenuHarmonogram = new JMenu("Harmonogram");
		jMenuHelp = new JMenu("Pomoc");

		menuBar.add(jMenuFile);
		menuBar.add(jMenuUzytkownik);
		menuBar.add(jMenuHarmonogram);
		menuBar.add(jMenuHelp);

		// Init submenus items
		// jMenuFile
		jMenuItemZamknij = new JMenuItem("Zamknij");
		jMenuFile.add(jMenuItemZamknij);

		// jMenuBD
		jMenuItemLogIn = new JMenuItem("Zaloguj");
		jMenuItemLogOut = new JMenuItem("Wyloguj");
		jMenuItemCreateAccount = new JMenuItem("Za�� konto");
		jMenuItemDeleteAccount = new JMenuItem("Usu� konto");

		jMenuUzytkownik.add(jMenuItemLogIn);
		jMenuUzytkownik.add(jMenuItemLogOut);
		jMenuUzytkownik.add(new JSeparator());
		jMenuUzytkownik.add(jMenuItemCreateAccount);
		jMenuUzytkownik.add(jMenuItemDeleteAccount);

		// jMenuHarmonogram
		jMenuItemShowHarmo = new JMenuItem("Poka� harmonogramy");
		jMenuItemCreateHarmo = new JMenuItem("Utworz harmonogram");
		jMenuItemAddNewMediaToHarm = new JMenuItem("Dodaj nowy element do harmonogramu");
		jMenuItemChooseActivHarm = new JMenuItem("Wybierz aktywny harmonogram");

		jMenuHarmonogram.add(jMenuItemShowHarmo);
		jMenuHarmonogram.add(jMenuItemCreateHarmo);
		jMenuHarmonogram.add(jMenuItemAddNewMediaToHarm);
		jMenuHarmonogram.add(jMenuItemChooseActivHarm);

		// jMenuPomoc
		jMenuItemAboutProgram = new JMenuItem("O programie");

		jMenuHelp.add(jMenuItemAboutProgram);

	}

	public void initMenuActionListener()
	{

		jMenuItemZamknij.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				mediaPlayerComponent.release();
				System.exit(0);
			}
		});

		// * * * * * * U S E R

		jMenuItemLogIn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				if (jDialogLogIn == null) jDialogLogIn = new JDialogLogIn(thisFrame);
				jDialogLogIn.setIsLogInFalse();
				jDialogLogIn.setVisible(true);

				if (jDialogLogIn.isLogInOk())
				{
					List<User> listaUzytkownikow = userDao.findUsers(jDialogLogIn.getLogInUser().getUsername());
					if (listaUzytkownikow.get(0) != null) logInUser = listaUzytkownikow.get(0);

					Set<Schedule> harmonogramy = logInUser.getUserSchedules();

					activeSchedule = null;
					if (harmonogramy != null)
					{
						for (Schedule harm : harmonogramy)
						{
							if (harm.isActive())
							{
								activeSchedule = harm;
								break;
							}
						}
					}

					System.out.println("zalogowany uzytkownik:" + "ID:" + logInUser.getId() + "NAME:" + logInUser.getUsername());
					System.out.println("\nLiczba harmoogramow:" + harmonogramy.size());
					System.out.println("Nazwa aktywnego harmonogramu:" + ((activeSchedule == null) ? "Brak aktywnego harmonogramu" : activeSchedule.getName()));

					// TODO Powolaj nowy watek SwingWorker kt�ry rozpocznie
					// nas�uchuch
				}
			}
		});

		jMenuItemLogOut.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				// TODO Wyloguj wynuluj zmienna
				logInUser = null;
				activeSchedule = null;
				disableButtonsWhenLogOut();
				jDialogLogIn.clearEditText();
			}
		});

		jMenuItemCreateAccount.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{

				if (newUserDialog == null) newUserDialog = new JDialogCreateNewUser(thisFrame);
				newUserDialog.setVisible(true);
			}
		});

		jMenuItemDeleteAccount.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				if (deleteUserDialog == null) deleteUserDialog = new JDialogDeleteUser(thisFrame);
				deleteUserDialog.setVisible(true);

			}
		});

		// * * * * * * H A R M O N O G R A M

		jMenuItemShowHarmo.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{

				if (showHarmonogramDialog == null)
				{
					showHarmonogramDialog = new JDialogShowHarmonograms(thisFrame);
					showHarmonogramDialog.setVisible(true);
				}
				else
				{
					showHarmonogramDialog.buildContentOfTree();
					showHarmonogramDialog.setVisible(true);
				}
				showHarmonogramDialog.setDefault();
			}
		});

		jMenuItemCreateHarmo.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				if (jDialogCreateNewHarm == null) jDialogCreateNewHarm = new JDialogCreateNewHarm(thisFrame);
				jDialogCreateNewHarm.setVisible(true);

				// refreshLogInUser();
				if (jDialogCreateNewHarm.isNewActivSched())
				{
					// logInUser = refreshUser(logInUser.getUsername());
					Set<Schedule> schedules = logInUser.getUserSchedules();
					for (Schedule schedule : schedules)
					{
						if (schedule.isActive())
						{
							activeSchedule = schedule;
							System.out.println("Aktywny harmonogram !" + activeSchedule);

							// TODO powiadom watek ze zmienil sie aktywny
							// harmonogram
						}
					}
				}
			}
		});

		// Dodaj plik multimedialny
		jMenuItemAddNewMediaToHarm.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{

				if (addMultimediaFileJDialog == null)
					addMultimediaFileJDialog = new JDialogAddMultimediaFile(thisFrame, activeSchedule);
				else addMultimediaFileJDialog.setSchedule(activeSchedule);

				addMultimediaFileJDialog.setVisible(true);
				refreshLogInUser();
			}
		});

		jMenuItemChooseActivHarm.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent arg0)
			{
				// TODO Auto-generated method stub
			}
		});
	}

	public User refreshUser(String userName)
	{
		List<User> users = userDao.findUsers(userName);
		if (users != null && users.size() != 0) return users.get(0);

		return null;
	}

	public void refreshLogInUser()
	{
		System.out.println("Odswiezony uzytkownik");
		if (logInUser != null)
		{
			List<User> users = userDao.findUsers(logInUser.getUsername());
			logInUser = users.get(0);
		}
		else
		{
			System.out.println("Nie ma zalogowanego uzytkownika !!! ");
		}
	}

	public void refreshActiveSchedule()
	{
		System.out.println("Odswiezony aktywny harmonogram w mainFrame");

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

				System.out.println("Brak harmonogram�w !!");
			}
		}
		else
		{
			activeSchedule = null;
			System.out.println("Nie ma zalogowanego uzytkownika !!! ");
		}

	}

	public void disableButtonsWhenLogIn()
	{
		jMenuItemLogIn.setEnabled(false);
		jMenuItemLogOut.setEnabled(true);

		jMenuItemShowHarmo.setEnabled(true);
		jMenuItemCreateHarmo.setEnabled(true);
		jMenuItemAddNewMediaToHarm.setEnabled(true);
		jMenuItemChooseActivHarm.setEnabled(true);
		jMenuItemCreateAccount.setEnabled(false);
		jMenuItemDeleteAccount.setEnabled(false);
	}

	public void disableButtonsWhenLogOut()
	{

		jMenuItemLogIn.setEnabled(true);
		jMenuItemLogOut.setEnabled(false);

		jMenuItemShowHarmo.setEnabled(false);
		jMenuItemCreateHarmo.setEnabled(false);
		jMenuItemAddNewMediaToHarm.setEnabled(false);
		jMenuItemChooseActivHarm.setEnabled(false);
		jMenuItemCreateAccount.setEnabled(true);
		jMenuItemDeleteAccount.setEnabled(true);
	}

	public User getLogInUser()
	{
		return logInUser;
	}

	public Schedule getActiveShedule()
	{
		System.out.println(activeSchedule);
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

	// Menu var
	private JMenuBar menuBar;

	private User logInUser = null;
	private Schedule activeSchedule = null;

	private JMenu jMenuFile;
	private JMenu jMenuUzytkownik;
	private JMenu jMenuHarmonogram;
	private JMenu jMenuHelp;

	private JMenuItem jMenuItemZamknij;

	private JMenuItem jMenuItemLogIn;
	private JMenuItem jMenuItemLogOut;
	private JMenuItem jMenuItemCreateAccount;
	private JMenuItem jMenuItemDeleteAccount;

	private JMenuItem jMenuItemShowHarmo;
	private JMenuItem jMenuItemCreateHarmo;
	private JMenuItem jMenuItemDeleteCurrentHarm;
	private JMenuItem jMenuItemAddNewMediaToHarm;
	private JMenuItem jMenuItemChooseActivHarm;

	private JMenuItem jMenuItemAboutProgram;
	// End Menu var

	// JDialog
	private JDialogCreateNewUser newUserDialog;
	private JDialogDeleteUser deleteUserDialog;
	private JDialogShowHarmonograms showHarmonogramDialog;
	private JDialogAddMultimediaFile addMultimediaFileJDialog;
	private JDialogLogIn jDialogLogIn;
	private JDialogCreateNewHarm jDialogCreateNewHarm;

	
	private MultimediaPlayerJFrame thisFrame;
	
	
	
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

	// Po zakonczeinu odtwarzania pamietaj aby zwolni� komponent metod� :
	// mediaPlayerComponent.release();
}

// / N O T A T K I !!!! :)

// *** Wersja z canvasem ***
/*
 * // Create a full-screen strategy fullScreenStrategy = new
 * DefaultFullScreenStrategy(this);
 * 
 * // Create a media player instance (in this example an embedded media //
 * player) EmbeddedMediaPlayer mediaPlayer =
 * mediaPlayerFactory.newEmbeddedMediaPlayer(fullScreenStrategy);
 * 
 * // Create and set a new component to display the rendered video (not //
 * shown: add the Canvas to a Frame) Canvas canvas = new Canvas();
 * CanvasVideoSurface videoSurface = mediaPlayerFactory.newVideoSurface(canvas);
 * mediaPlayer.setVideoSurface(videoSurface);
 * 
 * add(canvas);
 * 
 * mediaPlayer2.playMedia("C:\\b.avi"); mediaPlayer2.setFullScreen(true);
 */