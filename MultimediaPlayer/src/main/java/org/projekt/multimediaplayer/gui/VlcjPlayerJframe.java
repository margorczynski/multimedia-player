package org.projekt.multimediaplayer.gui;
import java.awt.Dimension;
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
		 * // player) 
		 * EmbeddedMediaPlayer mediaPlayer = mediaPlayerFactory.newEmbeddedMediaPlayer(fullScreenStrategy);
		 * 
		 * // Create and set a new component to display the rendered video (not
		 * // shown: add the Canvas to a Frame) 
		 * Canvas canvas = new Canvas();
		 * CanvasVideoSurface videoSurface = mediaPlayerFactory.newVideoSurface(canvas);
		 * mediaPlayer.setVideoSurface(videoSurface);
		 * 
		 * add(canvas);
		 * 
		 * mediaPlayer2.playMedia("C:\\b.avi");
		 * mediaPlayer2.setFullScreen(true);
		 */

		//Tworze fabryke obiektow
		mediaPlayerFactory = new MediaPlayerFactory();

		// Utworzenie listy odtwarzania
		MediaListPlayer mediaListPlayer = mediaPlayerFactory.newMediaListPlayer();



		// Utworzenie listy(MediaList) dla ListMediaPlayera
		// MediaList dodaje sie do ListMediaPlayer do ktorego poprzednio trzeba
		// dowiazac EmbeddedMediaPlayer
		MediaList mediaList = mediaPlayerFactory.newMediaList();
		//Zdefiniowanie przyk³adowej listy odtwarzania
		mediaList.addMedia("\\a.mp3");
		//mediaList.addMedia("C:\\b.avi");
		mediaList.addMedia("\\x.wmv");
		
		mediaListPlayer.setMediaList(mediaList);
		// mediaListPlayer.setMode(MediaListPlayerMode.LOOP);

		
		//**** tu zatrzymaj cofanie 
		mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
		
		// Utworzenie EmbeddedMediaPlayer
		mediaPlayer = mediaPlayerFactory.newEmbeddedMediaPlayer();
		
		//mediaPlayer = mediaPlayerComponent.getMediaPlayer();
		
		
		// Dowiazanie listy odtwarzania PLAYERA (listMediaPlayer) do konkretnego
		// odtwarzacza
		mediaListPlayer.setMediaPlayer(mediaPlayer);
		
		//Ustawienie strategi odtwarzania 
		mediaListPlayer.setMode(MediaListPlayerMode.LOOP);


		PlayerControlsPanel pcp = new PlayerControlsPanel(mediaPlayer, mediaListPlayer, mediaList, mediaPlayerFactory, mediaPlayerComponent);
		this.add(pcp);
		
		
		
		// mediaPlayer.playMedia("C:\\a.mp3","C:\\b.avi","C:\\d.avi");
		// mediaPlayer.setFullScreen(true);

		// mediaPlayer2.playMedia("C:\\b.avi");

		// JPanel panel = new JPanel();
		// panel.add(mediaPlayerComponent);
		// this.setContentPane(panel);
		// this.add(panel);
		// this.setContentPane(mediaPlayerComponent);
		// mediaPlayer = mediaPlayerComponent.getMediaPlayer();

		// mediaPlayer.playMedia("C:\\b.avi");
		// mediaPlayer.setFullScreen(true);

	}

	public void initMenus()
	{
		// Init menuBar
		menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);

		// Init sub menus
		JMenu jmenu = new JMenu("Menu 1");

		menuBar.add(jmenu);
		// Init submenus items
		JMenuItem jMenuItem = new JMenuItem("Pozycja pierwsza w menu");

		jmenu.add(jMenuItem);

	}

	JMenuBar menuBar;
	MediaPlayerFactory mediaPlayerFactory;
  EmbeddedMediaPlayerComponent mediaPlayerComponent;
	EmbeddedMediaPlayer mediaPlayer;
	FullScreenStrategy fullScreenStrategy;

	private static int WINDOW_WIDTH = 700;
	private static int WINDOW_HEIGHT = 700;

	// Po zakonczeinu odtwarzania pamietaj aby zwolniæ komponent metod¹ :
	// mediaPlayerComponent.release();
}
