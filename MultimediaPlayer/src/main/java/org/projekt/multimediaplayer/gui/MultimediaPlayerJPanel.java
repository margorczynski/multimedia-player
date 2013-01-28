package org.projekt.multimediaplayer.gui;

/*
 * This file is part of VLCJ.
 *
 * VLCJ is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * VLCJ is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with VLCJ.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Copyright 2009, 2010, 2011 Caprica Software Limited.
 */

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

public class MultimediaPlayerJPanel extends JPanel
{

	public MultimediaPlayerJPanel(final MultimediaPlayerJFrame ownerFrame)
	{
		this.ownerFrame = ownerFrame;

		mediaPlayerFactory = new MediaPlayerFactory();

		mediaListPlayer = mediaPlayerFactory.newMediaListPlayer();
		activeMediaList = mediaPlayerFactory.newMediaList();
		mediaListPlayer.setMediaList(activeMediaList);
		mediaPlayer = mediaPlayerFactory.newEmbeddedMediaPlayer();
		mediaListPlayer.setMediaPlayer(mediaPlayer);

		createUI();

		executorService.scheduleAtFixedRate(new UpdateRunnable(mediaPlayer), 0L, 1L, TimeUnit.SECONDS);
	}

	public MediaPlayerFactory getMediaFactory()
	{
		return mediaPlayerFactory;

	}

	private void createUI()
	{
		createControls();
		layoutControls();
		registerListeners();
	}

	private void createControls()
	{
		timeLabel = new JLabel("hh:mm:ss");

		positionSlider = new JSlider();
		positionSlider.setMinimum(0);
		positionSlider.setMaximum(1000);
		positionSlider.setValue(0);
		positionSlider.setToolTipText("Position");

		previousChapterButton = new JButton();
		previousChapterButton.setIcon(new ImageIcon("multimedia/buttons_icons/control_start_blue.png"));
		previousChapterButton.setToolTipText("Go to previous chapter");

		rewindButton = new JButton();
		rewindButton.setIcon(new ImageIcon("multimedia/buttons_icons/control_rewind_blue.png"));
		rewindButton.setToolTipText("Skip back");

		stopButton = new JButton();
		stopButton.setIcon(new ImageIcon("multimedia/buttons_icons/control_stop_blue.png"));
		stopButton.setToolTipText("Stop");

		pauseButton = new JButton();
		pauseButton.setIcon(new ImageIcon("multimedia/buttons_icons/control_pause_blue.png"));
		pauseButton.setToolTipText("Play/pause");

		playButton = new JButton();
		playButton.setIcon(new ImageIcon("multimedia/buttons_icons/control_play_blue.png"));
		playButton.setToolTipText("Play");

		fastForwardButton = new JButton();
		fastForwardButton.setIcon(new ImageIcon("multimedia/buttons_icons/control_fastforward_blue.png"));
		fastForwardButton.setToolTipText("Skip forward");

		nextChapterButton = new JButton();
		nextChapterButton.setIcon(new ImageIcon("multimedia/buttons_icons/control_end_blue.png"));
		nextChapterButton.setToolTipText("Go to next chapter");

		toggleMuteButton = new JButton();
		toggleMuteButton.setIcon(new ImageIcon("multimedia/buttons_icons/sound_mute.png"));
		toggleMuteButton.setToolTipText("Toggle Mute");

		volumeSlider = new JSlider();
		volumeSlider.setOrientation(SwingConstants.HORIZONTAL);
		volumeSlider.setMinimum(LibVlcConst.MIN_VOLUME);
		volumeSlider.setMaximum(LibVlcConst.MAX_VOLUME);
		volumeSlider.setPreferredSize(new Dimension(100, 40));
		volumeSlider.setToolTipText("Change volume");

		ejectButton = new JButton();
		ejectButton.setIcon(new ImageIcon("multimedia/buttons_icons/control_eject_blue.png"));
		ejectButton.setToolTipText("Load/eject media");

		// fullScreenButton = new JButton();
		// fullScreenButton.setIcon(new
		// ImageIcon("multimedia/buttons_icons/image.png"));
		// fullScreenButton.setToolTipText("Toggle full-screen");

		fileChooser = new JFileChooser();
		fileChooser.setApproveButtonText("Play");
		fileChooser.addChoosableFileFilter(SwingFileFilterFactory.newVideoFileFilter());
		fileChooser.addChoosableFileFilter(SwingFileFilterFactory.newAudioFileFilter());
		fileChooser.addChoosableFileFilter(SwingFileFilterFactory.newPlayListFileFilter());
		final FileFilter defaultFilter = SwingFileFilterFactory.newMediaFileFilter();
		fileChooser.addChoosableFileFilter(defaultFilter);
		fileChooser.setFileFilter(defaultFilter);
	}

	private void layoutControls()
	{
		setBorder(new EmptyBorder(4, 4, 4, 4));
		setLayout(new BorderLayout());

		final JPanel positionPanel = new JPanel();
		positionPanel.setLayout(new GridLayout(1, 1));
		positionPanel.add(positionSlider);

		final JPanel topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout(8, 0));
		topPanel.add(timeLabel, BorderLayout.WEST);
		topPanel.add(positionPanel, BorderLayout.CENTER);

		add(topPanel, BorderLayout.NORTH);

		final JPanel buttonControlPanel = new JPanel();
		final JPanel buttonsPanel = new JPanel();

		buttonControlPanel.setLayout(new GridLayout(0, 1));

		buttonsPanel.setLayout(new FlowLayout());
		buttonsPanel.add(previousChapterButton);
		buttonsPanel.add(rewindButton);
		buttonsPanel.add(stopButton);
		buttonsPanel.add(pauseButton);
		buttonsPanel.add(playButton);
		buttonsPanel.add(fastForwardButton);
		buttonsPanel.add(nextChapterButton);
		buttonsPanel.add(volumeSlider);
		buttonsPanel.add(toggleMuteButton);
		// buttonControlPanel.add(fullScreenButton);
		buttonsPanel.add(ejectButton);

		buttonControlPanel.add(buttonsPanel);

		add(buttonControlPanel, BorderLayout.SOUTH);

		canvasMovie = new Canvas();
		canvasMovie.setBackground(Color.DARK_GRAY);
		mediaPlayer.setVideoSurface(mediaPlayerFactory.newVideoSurface(canvasMovie));

		add(canvasMovie, BorderLayout.CENTER);
	}

	/**
	 * Broken out position setting, handles updating mediaPlayer
	 */
	private void setSliderBasedPosition()
	{
		if (!mediaPlayer.isSeekable()) { return; }
		float positionValue = positionSlider.getValue() / 1000.0f;
		// Avoid end of file freeze-up
		if (positionValue > 0.99f)
		{
			positionValue = 0.99f;
		}
		mediaPlayer.setPosition(positionValue);
	}

	private void updateUIState()
	{
		if (!mediaPlayer.isPlaying())
		{
			// Resume play or play a few frames then pause to show current
			// position in video
			mediaPlayer.play();
			if (!mousePressedPlaying)
			{
				try
				{
					// Half a second probably gets an iframe
					Thread.sleep(500);
				}
				catch (final InterruptedException e)
				{
					// Don't care if unblocked early
				}
				mediaPlayer.pause();
			}
		}
		final long time = mediaPlayer.getTime();
		final int position = (int) (mediaPlayer.getPosition() * 1000.0f);
		updateTime(time);
		updatePosition(position);
	}

	private void skip(final int skipTime)
	{
		// Only skip time if can handle time setting
		if (mediaPlayer.getLength() > 0)
		{
			mediaPlayer.skip(skipTime);
			updateUIState();
		}
	}

	private void registerListeners()
	{
		// Ustawienie poziomu g�o�cno�ci
		mediaPlayer.addMediaPlayerEventListener(new MediaPlayerEventAdapter()
		{
			@Override
			public void playing(final MediaPlayer mediaPlayer)
			{
				updateVolume(mediaPlayer.getVolume());
			}
		});

		// Ustawienie pozycji slidera odpowiedzialnego za aktualn� pozycj�
		// odtwarzania
		positionSlider.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(final MouseEvent e)
			{
				if (mediaPlayer.isPlaying())
				{
					mousePressedPlaying = true;
					mediaPlayer.pause();
				}
				else
				{
					mousePressedPlaying = false;
				}
				setSliderBasedPosition();
			}

			@Override
			public void mouseReleased(final MouseEvent e)
			{
				setSliderBasedPosition();
				updateUIState();
			}
		});

		// Poprzedni chapter
		previousChapterButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent e)
			{
				// mediaPlayer.previousChapter();
				mediaListPlayer.playPrevious();
			}
		});

		// Przewi�
		rewindButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent e)
			{
				skip(-SKIP_TIME_MS);
			}
		});

		// Stop
		stopButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent e)
			{
				// mediaPlayer.stop();
				mediaListPlayer.stop();
			}
		});

		// Pauza
		pauseButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent e)
			{
				// mediaPlayer.pause();
				mediaListPlayer.pause();
			}
		});

		// Play
		playButton.addActionListener(new ActionListener()
		{

			public void actionPerformed(final ActionEvent e)
			{
				// mediaPlayer.play();
				mediaListPlayer.play();
				// JFrame a = new JFrame();
				// a.add(mediaPlayer.)
				// mediaListPlayer.play();

			}
		});

		// Przewi� do przodu
		fastForwardButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent e)
			{
				skip(SKIP_TIME_MS);
			}
		});

		// Nast�pny utw�r
		nextChapterButton.addActionListener(new ActionListener()
		{

			public void actionPerformed(final ActionEvent e)
			{
				// mediaPlayer.nextChapter();
				mediaListPlayer.playNext();

			}
		});

		// Wycisz
		toggleMuteButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent e)
			{
				mediaPlayer.mute();
			}
		});

		//
		volumeSlider.addChangeListener(new ChangeListener()
		{

			public void stateChanged(final ChangeEvent e)
			{
				final JSlider source = (JSlider) e.getSource();
				// if(!source.getValueIsAdjusting()) {
				mediaPlayer.setVolume(source.getValue());
				// }
			}
		});

		ejectButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent e)
			{
				mediaPlayer.enableOverlay(false);
				if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(MultimediaPlayerJPanel.this))
				{
					if (mediaListPlayer.isPlaying()) mediaListPlayer.stop();
					if (mediaPlayer.isPlaying()) mediaPlayer.stop();

					activeMediaList = null;
					activeMediaList = mediaPlayerFactory.newMediaList();
					activeMediaList.addMedia(fileChooser.getSelectedFile().getAbsolutePath());
					mediaListPlayer.setMediaList(activeMediaList);
					mediaListPlayer.play();

				}
				mediaPlayer.enableOverlay(true);
			}
		});

	}

	public void disableButton()
	{
		ejectButton.setEnabled(false);
	}

	public void enableButton()
	{
		ejectButton.setEnabled(true);
	}

	private final class UpdateRunnable implements Runnable
	{

		private final MediaPlayer	mediaPlayer;

		private UpdateRunnable(final MediaPlayer mediaPlayer)
		{
			this.mediaPlayer = mediaPlayer;
		}

		public void run()
		{
			final long time = mediaPlayer.getTime();
			final int position = (int) (mediaPlayer.getPosition() * 1000.0f);

			SwingUtilities.invokeLater(new Runnable()
			{

				public void run()
				{
					if (mediaPlayer.isPlaying())
					{
						// asd
						updateTime(time);
						updatePosition(position);
					}
				}
			});
		}
	}

	private void updateTime(final long millis)
	{
		final String s = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis), TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
		timeLabel.setText(s);
	}

	private void updatePosition(final int value)
	{
		positionSlider.setValue(value);
	}

	private void updateVolume(final int value)
	{
		volumeSlider.setValue(value);
	}

	public void addNewMediaToPlayInHarm(final String path)
	{
		activeMediaList.addMedia(path);
	}

	public void changeMediaList(final LinkedList<MultimediaFile> lista, final MediaListPlayerMode mode)
	{
		// mediaPlayer.enableOverlay(false);
		if (mediaListPlayer.isPlaying()) mediaListPlayer.stop();
		if (mediaPlayer.isPlaying()) mediaPlayer.stop();

		activeMediaList = null;
		activeMediaList = mediaPlayerFactory.newMediaList();

		for (final MultimediaFile item : lista)
			activeMediaList.addMedia(item.getPath());

		mediaListPlayer.setMediaList(activeMediaList);
		mediaListPlayer.setMode(mode);

		mediaListPlayer.play();

		ejectButton.setEnabled(false);
		// mediaPlayer.enableOverlay(true);
		// zablokuj otwieranie plik�w strza�eczka
	}

	public void deleteActiveSchedule()
	{
		if (mediaListPlayer.isPlaying()) mediaListPlayer.stop();
		if (mediaPlayer.isPlaying()) mediaPlayer.stop();

		activeMediaList = null;
		activeMediaList = mediaPlayerFactory.newMediaList();
	}

	public void deleteFileFromActiveList(final String name)
	{
		List<MediaListItem> lista = new LinkedList<MediaListItem>();
		lista = activeMediaList.items();

		int i = 0;
		for (final MediaListItem mListItem : lista)
		{
			if (mListItem.name().equals(name))
			{
				if (mediaListPlayer.isPlaying()) mediaListPlayer.stop();
				if (mediaPlayer.isPlaying()) mediaPlayer.stop();

				activeMediaList.removeMedia(i);

				mediaListPlayer.playNext();
				break;
			}
			i++;
		}

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

	class SliderListener implements ChangeListener
	{
		public void stateChanged(final ChangeEvent e)
		{
			final JSlider source = (JSlider) e.getSource();

			if (!source.getValueIsAdjusting())
			{
				textComponent.setScrollSpeed(source.getValue());
			}
		}
	}

	private static final long				serialVersionUID	= 1L;

	private static final int				SKIP_TIME_MS		= 10 * 1000;									// 10
																												// sekund

	private final ScheduledExecutorService	executorService		= Executors.newSingleThreadScheduledExecutor();

	private final EmbeddedMediaPlayer		mediaPlayer;

	private JLabel							timeLabel;
	// private JProgressBar positionProgressBar;
	private JSlider							positionSlider;

	private TextComponent					textComponent;

	private JButton							previousChapterButton;
	private JButton							rewindButton;
	private JButton							stopButton;
	private JButton							pauseButton;
	private JButton							playButton;
	private JButton							fastForwardButton;
	private JButton							nextChapterButton;
	private JButton							ejectButton;

	private JButton							toggleMuteButton;
	private JSlider							volumeSlider;

	private JFileChooser					fileChooser;
	// *****
	private MediaList						activeMediaList;
	private final MediaListPlayer			mediaListPlayer;
	private JPanel							windowPanel;
	private Canvas							canvasMovie;
	private final MediaPlayerFactory		mediaPlayerFactory;

	MultimediaPlayerJFrame					ownerFrame;
	// private JButton fullScreenButton;
	private boolean							mousePressedPlaying	= false;

}