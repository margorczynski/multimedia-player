package org.projekt.multimediaplayer.gui;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import org.projekt.multimediaplayer.main.RssReader;
import org.projekt.multimediaplayer.main.Statistics;
import org.projekt.multimediaplayer.model.MultimediaFile;

import uk.co.caprica.vlcj.binding.internal.libvlc_media_t;
import uk.co.caprica.vlcj.medialist.MediaList;
import uk.co.caprica.vlcj.player.list.MediaListPlayerEventAdapter;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.DefaultFullScreenStrategy;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.list.MediaListPlayer;
import uk.co.caprica.vlcj.player.list.MediaListPlayerMode;

public class MultimediaPlayerBareJPanel extends JPanel
{
	public MultimediaPlayerBareJPanel(MultimediaPlayerBareJFrame ownerFrame)
	{
		this.ownerFrame = ownerFrame;

		mediaPlayerFactory = new MediaPlayerFactory();

		mediaListPlayer = mediaPlayerFactory.newMediaListPlayer();
		activeMediaList = mediaPlayerFactory.newMediaList();
		mediaListPlayer.setMediaList(activeMediaList);
		mediaPlayer = mediaPlayerFactory.newEmbeddedMediaPlayer(new DefaultFullScreenStrategy(ownerFrame));
		mediaListPlayer.setMediaPlayer(mediaPlayer);
		
		mediaListPlayer.addMediaListPlayerEventListener(new FileChangedListener());
		
		mediaPlayer.setFullScreen(false);
		
		ownerFrame.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyPressed(KeyEvent e) 
			{
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE) System.exit(0);
				
				if(e.getKeyCode() == KeyEvent.VK_RIGHT) mediaListPlayer.playNext();
			}
			
		});
		
		mediaListPlayer.stop();
		
		createUI();
	}

	public MediaPlayerFactory getMediaFactory()
	{
		return mediaPlayerFactory;

	}

	private void createUI()
	{
		layoutControls();
	}


	private void layoutControls()
	{
		RssReader rr = new RssReader();
		
		String headers = "";
		
		for(String s : rr.getHeaders())
		{
			headers += s + "     ";
		}
		
		
		textComponent  = new TextComponent(headers);
		
		setBorder(new EmptyBorder(4, 4, 4, 4));
		setLayout(new BorderLayout());
		
		
		canvasMovie = new Canvas();
		canvasMovie.setBackground(Color.DARK_GRAY);
		mediaPlayer.setVideoSurface(mediaPlayerFactory.newVideoSurface(canvasMovie));
		
		JPanel panel = new JPanel();
		
		panel.setBackground(Color.DARK_GRAY);
		
		panel.setLayout(new BorderLayout());
		
		panel.add(textComponent, BorderLayout.CENTER);
		
		add(canvasMovie, BorderLayout.CENTER);
		add(panel, BorderLayout.SOUTH);
		
		
		canvasMovie.doLayout();
		
	}
	

	public void changeMediaList(LinkedList<MultimediaFile> lista, MediaListPlayerMode mode)
	{
		currentListRef = lista;
		
	//	mediaPlayer.enableOverlay(false);
		if (mediaListPlayer.isPlaying()) mediaListPlayer.stop();
		if (mediaPlayer.isPlaying()) mediaPlayer.stop();

	
		
		activeMediaList = null;
		activeMediaList = mediaPlayerFactory.newMediaList();
		
		for(MultimediaFile item: lista)
			activeMediaList.addMedia(item.getPath());

		mediaListPlayer.setMediaList(activeMediaList);
		mediaListPlayer.setMode(mode);

		mediaListPlayer.play();
	}

	

	public void releaseMultimediaVlcj()
	{
		if (mediaListPlayer.isPlaying()) mediaListPlayer.stop();
		if (mediaPlayer.isPlaying()) mediaPlayer.stop();

		activeMediaList.release();
		mediaListPlayer.release();
		mediaPlayer.release();
		mediaPlayerFactory.release();
	}
	
	public boolean isPlaying()
	{
		return mediaListPlayer.isPlaying();
	}
	
	public List<MultimediaFile> getCurrentList()
	{
		return currentListRef;
	}
	
	private class FileChangedListener extends MediaListPlayerEventAdapter
	{
		Statistics statistics = new Statistics();
		
		@Override
		public void nextItem(MediaListPlayer mediaListPlayer,
	            libvlc_media_t item,
	            java.lang.String itemMrl)
		{
			String[] s = itemMrl.split("/");
			String replaced = s[s.length-1].replaceAll("01%20", "");
			replaced = replaced.replaceAll("%20", " ");
			replaced = replaced.replaceAll("%27", "'");
			
			statistics.addPlay(replaced);
		}
	}
	
	
	private static final long serialVersionUID = 1L;

	private static final int SKIP_TIME_MS = 10 * 1000; // 10 sekund

	private  ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

	private final EmbeddedMediaPlayer mediaPlayer;

	private TextComponent textComponent;

	// *****
	private MediaList activeMediaList;
	private MediaListPlayer mediaListPlayer;
	private JPanel windowPanel;
	private Canvas canvasMovie;
	private MediaPlayerFactory mediaPlayerFactory;
	
	private List<MultimediaFile> currentListRef;

	MultimediaPlayerBareJFrame ownerFrame;
	//private JButton fullScreenButton;
	private boolean mousePressedPlaying = false;
}
