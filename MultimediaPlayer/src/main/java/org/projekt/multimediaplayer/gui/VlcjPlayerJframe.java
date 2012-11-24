package org.projekt.multimediaplayer.gui;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.medialist.MediaList;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.FullScreenStrategy;
import uk.co.caprica.vlcj.player.list.MediaListPlayer;
import uk.co.caprica.vlcj.player.list.MediaListPlayerMode;

import com.sun.jna.NativeLibrary;

public class VlcjPlayerJframe extends JFrame
{
	public VlcjPlayerJframe()
	{
		super();
		this.setMinimumSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		this.setTitle("test vlcj");

		initComponents();
		initMenus();
	}

	public void initComponents()
	{

		// dowi¹zanie bibliotek z programu VLC
		NativeLibrary.addSearchPath("libvlc", "C:/Program Files/VideoLAN/VLC");

		// Init JFrame elements
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
		mediaList.addMedia("\\a.mp3");
		// mediaList.addMedia("C:\\b.avi");
		mediaList.addMedia("\\x.wmv");

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

		PlayerControlsPanel pcp = new PlayerControlsPanel(mediaPlayer, mediaListPlayer, mediaPlayerFactory, mediaPlayerComponent);
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
		jMenuBD = new JMenu("Baza Danych");
		jMenuHarmonogram = new JMenu("Harmonogram");
		jMenuHelp = new JMenu("Pomoc");

		menuBar.add(jMenuFile);
		menuBar.add(jMenuBD);
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

		jMenuBD.add(jMenuItemLogIn);
		jMenuBD.add(jMenuItemLogIn);
		jMenuBD.add(jMenuItemLogOut);
		jMenuBD.add(jMenuItemDeleteAccount);

		// jMenuHarmonogram
		jMenuItemShowHarmo = new JMenuItem("Poka¿ harmonogramy");
		jMenuItemCreateHarmo = new JMenuItem("Utworz harmonogram");
		jMenuItemDeleteCurrentHarm = new JMenuItem("Usuñ bierz¹cy harmonogram");
		jMenuItemAddNewMediaToHarm = new JMenuItem("Dodaj nowy element do harmonogramu");

		jMenuHarmonogram.add(jMenuItemShowHarmo);
		jMenuHarmonogram.add(jMenuItemCreateHarmo);
		jMenuHarmonogram.add(jMenuItemDeleteCurrentHarm);
		jMenuHarmonogram.add(jMenuItemAddNewMediaToHarm);

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

			}
		});

		jMenuItemLogIn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{

			}
		});

		jMenuItemLogOut.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{

			}
		});

		jMenuItemCreateAccount.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{

			}
		});

		jMenuItemDeleteAccount.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{

			}
		});

		jMenuItemShowHarmo.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{

			}
		});

		jMenuItemCreateHarmo.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{

			}
		});

		jMenuItemDeleteCurrentHarm.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{

			}
		});

		jMenuItemAddNewMediaToHarm.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{

			}
		});

	}

	// Menu var
	JMenuBar menuBar;

	JMenu jMenuFile;
	JMenu jMenuBD;
	JMenu jMenuHarmonogram;
	JMenu jMenuHelp;

	JMenuItem jMenuItemZamknij;

	JMenuItem jMenuItemLogIn;
	JMenuItem jMenuItemLogOut;
	JMenuItem jMenuItemCreateAccount;
	JMenuItem jMenuItemDeleteAccount;

	JMenuItem jMenuItemShowHarmo;
	JMenuItem jMenuItemCreateHarmo;
	JMenuItem jMenuItemDeleteCurrentHarm;
	JMenuItem jMenuItemAddNewMediaToHarm;

	JMenuItem jMenuItemAboutProgram;
	// End Menu var

	MediaPlayerFactory mediaPlayerFactory;
	EmbeddedMediaPlayerComponent mediaPlayerComponent;
	EmbeddedMediaPlayer mediaPlayer;
	FullScreenStrategy fullScreenStrategy;

	private static int WINDOW_WIDTH = 700;
	private static int WINDOW_HEIGHT = 700;

	// Po zakonczeinu odtwarzania pamietaj aby zwolniæ komponent metod¹ :
	// mediaPlayerComponent.release();
}
