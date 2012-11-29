package org.projekt.multimediaplayer.gui;

import java.awt.Dimension;

import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.medialist.MediaList;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.FullScreenStrategy;
import uk.co.caprica.vlcj.player.list.MediaListPlayer;
import uk.co.caprica.vlcj.player.list.MediaListPlayerMode;

import org.projekt.multimediaplayer.model.*;

import com.sun.jna.NativeLibrary;

public class MultimediaPlayerJFrame extends JFrame
{
	public MultimediaPlayerJFrame()
	{
		super();

		initComponents();
		initMenus();
		//disableButtonsWhenLogOut();
		initMenuActionListener();
		thisFrame = this;
	}

	public void initComponents()
	{

		// dowi¹zanie bibliotek z programu VLC

		// Marcin Twoje chyba xd ?
		// NativeLibrary.addSearchPath("libvlc", "D:/VLC64/");

		// Krzysiek
		NativeLibrary.addSearchPath("libvlc", "C:/Program Files/VideoLAN/VLC");

		// Init JFrame elements
		this.setMinimumSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		this.setTitle("Odtwarzacz multimedialny");
		this.setLocation(100, 100);
		this.setSize(1050, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

		// *** Wersja z canvasem ***
		/*
		 * // Create a full-screen strategy fullScreenStrategy = new
		 * DefaultFullScreenStrategy(this);
		 * 
		 * // Create a media player instance (in this example an embedded media
		 * // player) EmbeddedMediaPlayer mediaPlayer =
		 * mediaPlayerFactory.newEmbeddedMediaPlayer(fullScreenStrategy);
		 * 
		 * // Create and set a new component to display the rendered video (not
		 * // shown: add the Canvas to a Frame) Canvas canvas = new Canvas();
		 * CanvasVideoSurface videoSurface =
		 * mediaPlayerFactory.newVideoSurface(canvas);
		 * mediaPlayer.setVideoSurface(videoSurface);
		 * 
		 * add(canvas);
		 * 
		 * mediaPlayer2.playMedia("C:\\b.avi");
		 * mediaPlayer2.setFullScreen(true);
		 */

		// Tworze fabryke obiektow
		mediaPlayerFactory = new MediaPlayerFactory();

		// Utworzenie listy odtwarzania
		MediaListPlayer mediaListPlayer = mediaPlayerFactory.newMediaListPlayer();

		// Utworzenie listy(MediaList) dla ListMediaPlayera
		// MediaList dodaje sie do ListMediaPlayer do ktorego poprzednio trzeba
		// dowiazac EmbeddedMediaPlayer
		MediaList mediaList = mediaPlayerFactory.newMediaList();
		// Zdefiniowanie przyk³adowej listy odtwarzania
		mediaList.addMedia("multimedia\\sample\\a.mp3");
		// mediaList.addMedia("C:\\b.avi");
		mediaList.addMedia("multimedia\\sample\\x.wmv");

		mediaListPlayer.setMediaList(mediaList);
		// mediaListPlayer.setMode(MediaListPlayerMode.LOOP);

		// **** tu zatrzymaj cofanie
		mediaPlayerComponent = new EmbeddedMediaPlayerComponent();

		// Utworzenie EmbeddedMediaPlayer
		mediaPlayer = mediaPlayerFactory.newEmbeddedMediaPlayer();

		// mediaPlayer = mediaPlayerComponent.getMediaPlayer();

		// Dowiazanie listy odtwarzania PLAYERA (listMediaPlayer) do konkretnego
		// odtwarzacza
		mediaListPlayer.setMediaPlayer(mediaPlayer);

		// Ustawienie strategi odtwarzania
		mediaListPlayer.setMode(MediaListPlayerMode.LOOP);

		MultimediaPlayerJPanel pcp = new MultimediaPlayerJPanel(mediaPlayer, mediaListPlayer, mediaPlayerFactory, mediaPlayerComponent);
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
		jMenuUzytkownik = new JMenu("U¿ytkownik");
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
		jMenuItemCreateAccount = new JMenuItem("Za³ó¿ konto");
		jMenuItemDeleteAccount = new JMenuItem("Usuñ konto");

		jMenuUzytkownik.add(jMenuItemLogIn);
		jMenuUzytkownik.add(jMenuItemLogOut);
		jMenuUzytkownik.add(new JSeparator());
		jMenuUzytkownik.add(jMenuItemCreateAccount);
		jMenuUzytkownik.add(jMenuItemDeleteAccount);

		// jMenuHarmonogram
		jMenuItemShowHarmo = new JMenuItem("Poka¿ harmonogramy");
		jMenuItemCreateHarmo = new JMenuItem("Utworz harmonogram");
		jMenuItemDeleteCurrentHarm = new JMenuItem("Usuñ bierz¹cy harmonogram");
		jMenuItemAddNewMediaToHarm = new JMenuItem("Dodaj nowy element do harmonogramu");
		jMenuItemChooseActivHarm = new JMenuItem("Wybierz aktywny harmonogram");

		jMenuHarmonogram.add(jMenuItemShowHarmo);
		jMenuHarmonogram.add(jMenuItemCreateHarmo);
		jMenuHarmonogram.add(jMenuItemDeleteCurrentHarm);
		jMenuHarmonogram.add(jMenuItemAddNewMediaToHarm);
		jMenuHarmonogram.add(jMenuItemChooseActivHarm);
		
		// jMenuPomoc
		jMenuItemAboutProgram = new JMenuItem("O programie");

		jMenuHelp.add(jMenuItemAboutProgram);

	}

	public void initMenuActionListener()
	{

		// TODO Rozwin¹c action listnery
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
				jDialogLogIn.setVisible(true);

				// TODO logowanie
			}
		});

		jMenuItemLogOut.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				// TODO Wyloguj wynuluj zmienna

				disableButtonsWhenLogOut();

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
				if (showHarmonogramDialog == null) showHarmonogramDialog = new JDialogShowHarmonograms(thisFrame);
				showHarmonogramDialog.setVisible(true);

			}
		});

		jMenuItemCreateHarmo.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{

				if (jDialogCreateNewHarm == null) jDialogCreateNewHarm = new JDialogCreateNewHarm(thisFrame);
				jDialogCreateNewHarm.setVisible(true);
			}
		});

		jMenuItemDeleteCurrentHarm.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				// TODO usuñ bierz¹cy harmonogram, pobierz uzytkownika z okna g³ownego, usun z niego harmonogram i powiazane z nim pliki, aktualizuj BD
				// wyswietl okienko z dostepnymi harmonogramami
				
			}
		});

		// Dodaj plik multimedialny
		jMenuItemAddNewMediaToHarm.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{

				Schedule test = new Schedule();
				if (addMultimediaFileJDialog == null) addMultimediaFileJDialog = new JDialogAddMultimediaFile(thisFrame, test);
				addMultimediaFileJDialog.setVisible(true);

			}
		});

		jMenuItemChooseActivHarm.addActionListener( new ActionListener()
		{
			
			public void actionPerformed(ActionEvent arg0)
			{
				// TODO Auto-generated method stub
				
			}
		});
		
	}

	public void disableButtonsWhenLogIn()
	{
		jMenuItemLogIn.setEnabled(false);
		jMenuItemLogOut.setEnabled(true);

		jMenuItemShowHarmo.setEnabled(true);
		jMenuItemCreateHarmo.setEnabled(true);
		jMenuItemDeleteCurrentHarm.setEnabled(true);
		jMenuItemAddNewMediaToHarm.setEnabled(true);
		jMenuItemChooseActivHarm.setEnabled(true);
	}

	public void disableButtonsWhenLogOut()
	{

		jMenuItemLogIn.setEnabled(true);
		jMenuItemLogOut.setEnabled(false);

		jMenuItemShowHarmo.setEnabled(false);
		jMenuItemCreateHarmo.setEnabled(false);
		jMenuItemDeleteCurrentHarm.setEnabled(false);
		jMenuItemAddNewMediaToHarm.setEnabled(false);
		jMenuItemChooseActivHarm.setEnabled(false);
	}

	// Menu var
	private JMenuBar menuBar;

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

	private JFrame thisFrame;

	private MediaPlayerFactory mediaPlayerFactory;
	private EmbeddedMediaPlayerComponent mediaPlayerComponent;
	private EmbeddedMediaPlayer mediaPlayer;
	private FullScreenStrategy fullScreenStrategy;

	private static int WINDOW_WIDTH = 700;
	private static int WINDOW_HEIGHT = 700;

	// Po zakonczeinu odtwarzania pamietaj aby zwolniæ komponent metod¹ :
	// mediaPlayerComponent.release();
}
