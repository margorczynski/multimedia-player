package org.projekt.multimediaplayer.gui;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;

import org.projekt.multimediaplayer.main.RssReader;
import org.projekt.multimediaplayer.model.MultimediaFile;

import uk.co.caprica.vlcj.binding.LibVlcConst;
import uk.co.caprica.vlcj.filter.swing.SwingFileFilterFactory;
import uk.co.caprica.vlcj.medialist.MediaList;
import uk.co.caprica.vlcj.medialist.MediaListItem;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
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
		mediaPlayer = mediaPlayerFactory.newEmbeddedMediaPlayer();
		mediaListPlayer.setMediaPlayer(mediaPlayer);
		
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

		add(canvasMovie, BorderLayout.CENTER);
	}
	

	public void changeMediaList(LinkedList<MultimediaFile> lista, MediaListPlayerMode mode)
	{
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

	MultimediaPlayerBareJFrame ownerFrame;
	//private JButton fullScreenButton;
	private boolean mousePressedPlaying = false;
}