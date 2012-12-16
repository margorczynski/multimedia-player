package org.projekt.multimediaplayer.gui;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;

import org.projekt.multimediaplayer.dao.MultimediaFileDao;
import org.projekt.multimediaplayer.model.MultimediaFile;
import org.projekt.multimediaplayer.model.Schedule;
import org.projekt.multimediaplayer.model.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import uk.co.caprica.vlcj.filter.swing.SwingFileFilterFactory;
import uk.co.caprica.vlcj.medialist.MediaList;
import uk.co.caprica.vlcj.medialist.MediaListItem;
import uk.co.caprica.vlcj.player.MediaDetails;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.list.MediaListPlayer;

public class JDialogAddMultimediaFile extends JDialog
{

	public JDialogAddMultimediaFile(JFrame owner, Schedule schedule)
	{
		super(owner, windowTitle, ModalityType.APPLICATION_MODAL);

		this.activeSchedule = schedule;
		this.owner = owner;
		Point point = owner.getLocationOnScreen();
		this.setLocation((int)point.getX()+275, (int)point.getY()+155);
		

		initComponents();

		setSize(530, 380);
		setResizable(false);

	}

	public void setSchedule(Schedule s)
	{
		activeSchedule = s;
	}

	public void setDefault()
	{
		newMultimediaFile = null;
		fileTimeLengthMilis = -10;

		labelFileName.setText("Nazwa pliku");
		labelFileSize.setText("Rozmiar");
		labelFileType.setText("Typ");
		labelFileLength.setText("D³ugoœæ");

		textFileName.setText("Wybierz plik ... ");
		textFileSize.setText("Wybierz plik ... ");
		textFileType.setText("Wybierz plik ... ");
		textFileLength.setText("Wybierz plik ... ");

		textFilePatch.setText("Wybierz plik");
		labelFileInfo.setText("Informacje o pliku");

		newMultimediaFile = null;
	}

	public void initComponents()
	{

		JPanel mainPanel = new JPanel(new BorderLayout());

		labelHeadline = new JLabel("<html><h2><i><b> Dodaj plik </b></i></h2></html>");
		labelHeadline.setAlignmentX(CENTER_ALIGNMENT);

		labelFilePatch = new JLabel();
		textFilePatch = new JTextField();
		buttonOpen = new JButton("Otwórz");
		labelFileName = new JLabel();
		labelFileSize = new JLabel();
		labelFileType = new JLabel();
		labelFileLength = new JLabel();
		textFileName = new JTextField();
		textFileSize = new JTextField();
		textFileType = new JTextField();
		textFileLength = new JTextField();
		buttonSave = new JButton();
		buttonCancel = new JButton();
		labelFileInfo = new JLabel();

		textFileSeqNo = new JTextField();


		//textFileDelay.setColumns(15);
		textFileLength.setColumns(15);
		textFileName.setColumns(15);
		textFilePatch.setColumns(15);
		textFileSeqNo.setColumns(15);
		textFileSize.setColumns(15);
		textFileType.setColumns(15);

		// Panel otwierania pliku
		JPanel fileChooserPanel = new JPanel(new GridBagLayout());

		labelFilePatch.setText("Scie¿ka:");
		textFilePatch.setText("Kliknij aby wybraæ plik ....");
		textFilePatch.setColumns(35);

		// GBC(kolumna, wiersz, ile kolumn, ile wierszy)

		fileChooserPanel.add(labelHeadline, new GBC(0, 0, 2, 1).setAnchor(GBC.CENTER).setInsets(5, 0, 0, 0));
		fileChooserPanel.add(labelFilePatch, new GBC(0, 1).setAnchor(GBC.WEST).setInsets(2, 0, 0, 0));
		fileChooserPanel.add(textFilePatch, new GBC(0, 2).setAnchor(GBC.WEST).setInsets(5, 0, 0, 0));
		fileChooserPanel.add(buttonOpen, new GBC(1, 2).setAnchor(GBC.WEST).setInsets(5, 10, 0, 0));

		mainPanel.add(fileChooserPanel, BorderLayout.NORTH);

		// Panel srodkowy ( inforamcje o pliku i odtwarzaniu )

		JPanel middlePanel = new JPanel(new GridBagLayout());

		labelFileName.setText("Nazwa pliku");
		labelFileSize.setText("Rozmiar");
		labelFileType.setText("Typ");
		labelFileLength.setText("D³ugoœæ");

		textFileName.setText("Wybierz plik ... ");
		textFileSize.setText("Wybierz plik ... ");
		textFileType.setText("Wybierz plik ... ");
		textFileLength.setText("Wybierz plik ... ");

		labelFileInfo.setText("Informacje o pliku");
		middlePanel.add(labelFileInfo, new GBC(0, 0, 2, 1).setAnchor(GBC.CENTER).setInsets(5, 5, 10, 0));

		middlePanel.add(labelFileName, new GBC(0, 1).setAnchor(GBC.WEST).setInsets(5, 5, 0, 0));
		middlePanel.add(labelFileSize, new GBC(0, 2).setAnchor(GBC.WEST).setInsets(5, 5, 0, 0));
		middlePanel.add(labelFileType, new GBC(0, 3).setAnchor(GBC.WEST).setInsets(5, 5, 0, 0));
		middlePanel.add(labelFileLength, new GBC(0, 4).setAnchor(GBC.WEST).setInsets(5, 5, 0, 0));


		middlePanel.add(textFileName, new GBC(1, 1).setAnchor(GBC.WEST).setInsets(5, 5, 0, 0));
		middlePanel.add(textFileSize, new GBC(1, 2).setAnchor(GBC.WEST).setInsets(5, 5, 0, 0));
		middlePanel.add(textFileType, new GBC(1, 3).setAnchor(GBC.WEST).setInsets(5, 5, 0, 0));
		middlePanel.add(textFileLength, new GBC(1, 4).setAnchor(GBC.WEST).setInsets(5, 5, 0, 0));


		mainPanel.add(middlePanel, BorderLayout.CENTER);

		// FileChooser

		fileChooser = new JFileChooser("C:\\");
		fileChooser.setApproveButtonText("Dodaj");
		fileChooser.addChoosableFileFilter(SwingFileFilterFactory.newVideoFileFilter());
		fileChooser.addChoosableFileFilter(SwingFileFilterFactory.newAudioFileFilter());
		fileChooser.addChoosableFileFilter(SwingFileFilterFactory.newPlayListFileFilter());

		FileFilter defaultFilter = SwingFileFilterFactory.newMediaFileFilter();
		fileChooser.addChoosableFileFilter(defaultFilter);
		fileChooser.setFileFilter(defaultFilter);

		textFilePatch.addInputMethodListener(new InputMethodListener()
		{

			public void inputMethodTextChanged(InputMethodEvent arg0)
			{
				openFile();
			}

			public void caretPositionChanged(InputMethodEvent arg0)
			{
			}
		});

		// Button Panel
		buttonOpen.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent arg0)
			{

				openFile();
			}
		});

		buttonSave.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent arg0)
			{

				if (newMultimediaFile != null && fileTimeLengthMilis > 0)
				{

					if (activeSchedule != null)
					{
						MultimediaFile multimediaFile = new MultimediaFile();
						multimediaFile.setFilename(newMultimediaFile.getName());
						multimediaFile.setSchedule(activeSchedule);
						multimediaFile.setPath(newMultimediaFile.getAbsolutePath());
						multimediaFile.setSize(newMultimediaFile.length());
						multimediaFile.setLength(fileTimeLengthMilis);
						multimediaFile.setType(textFileType.getText());

						multimediaFileDao.saveMultimediaFile(multimediaFile);
						System.out.println("Plik zosta³ dodany do hamonogramu !");
						JOptionPane.showMessageDialog(JDialogAddMultimediaFile.this, "Plik zosta³ dodany prawidlowo", "Dodawanie pliku", JOptionPane.INFORMATION_MESSAGE);
						((MultimediaPlayerJFrame)owner).getMultimediaPanel().addNewMediaToPlayInHarm(multimediaFile.getPath());
					}
					else 
					{
						JOptionPane.showMessageDialog(JDialogAddMultimediaFile.this, "Plik nie zosta³ dodany poniewa¿ nie ma aktywnego ¿adnego harmonogramu !!!", "Dodawanie pliku", JOptionPane.INFORMATION_MESSAGE);

					}
					setDefault();
					setVisible(false);
				}
			}
		});

		buttonCancel.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent arg0)
			{
				setDefault();
				setVisible(false);
			}
		});

		buttonSave.setText("Zapisz");
		buttonCancel.setText("Anuluj");

		JPanel buttonPanel = new JPanel();

		buttonPanel.add(buttonSave);
		buttonPanel.add(buttonCancel);

		mainPanel.add(buttonPanel, BorderLayout.SOUTH);

		this.setContentPane(mainPanel);
		pack();
	}

	public void openFile()
	{
		if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(this))
		{

			setDefault();
			newMultimediaFile = fileChooser.getSelectedFile();

			try
			{
				textFilePatch.setText(newMultimediaFile.getCanonicalPath());
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}

			textFileName.setText(newMultimediaFile.getName());

			Locale locale = new Locale("pl", "PL");
			NumberFormat currFormat = NumberFormat.getNumberInstance(locale);
			Double fileSizeMb = newMultimediaFile.length() / (1024.0 * 1024.0);
			String formatSize = currFormat.format(fileSizeMb);

			textFileSize.setText(formatSize + "Mb");

			MediaPlayerFactory mediaPlayerFactory2 = new MediaPlayerFactory();
			EmbeddedMediaPlayer mediaPlayer = mediaPlayerFactory2.newEmbeddedMediaPlayer();
			mediaPlayer.playMedia(newMultimediaFile.getAbsolutePath());

			fileTimeLengthMilis = mediaPlayer.getLength();
			while (fileTimeLengthMilis <= 0)
			{
				fileTimeLengthMilis = mediaPlayer.getLength();
			}
			mediaPlayer.stop();
			mediaPlayer.release();

			textFileLength.setText(getTimeFromMilis(fileTimeLengthMilis));

			StringTokenizer stringTokenizer = new StringTokenizer(newMultimediaFile.getName(), ".");
			stringTokenizer.nextToken();
			String rozszerzenie = stringTokenizer.nextToken();

			boolean thisIsIt = false;

			for (String string : videoExtent)
			{
				if (rozszerzenie.trim().toLowerCase().equals(string.trim().toLowerCase()))
				{
					thisIsIt = true;
					textFileType.setText("Video");
					break;
				}
			}
			if (!thisIsIt)
			{
				for (String string : audioExtent)
				{
					if (rozszerzenie.trim().toLowerCase().equals(string.trim().toLowerCase()))
					{
						thisIsIt = true;
						textFileType.setText("Audio");
						break;
					}
				}
			}
			if (!thisIsIt)
			{
				textFileType.setText("Unidentified");
			}
		}
	}

	private String getTimeFromMilis(long millis)
	{
		String s = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis), TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
		return s;
	}

	String[] videoExtent = { "AVI", "BDMV", "BMK", "BSF", "flv", "HDMOV", "m2ts", "m4v", "MJP", "MJPG", "mkv", "MOOV", "mov", "mp2v", "MP4", "mpg", "mqv", "MTS", "qt", "rmp", "rmbv", "wmv" };
	String[] audioExtent = { "AIF", "AIFF", "AIFC", "AIFR", "MIDI", "MID", "RMI", "MP2", "MPG", "MPE", "MPEG", "MPEG2", "MP3", "MPEG3", "OGG", "RA", "WAVE", "WAV", "WMA" };

	private static String windowTitle = "Dodaj nowy plik";

	private JLabel labelHeadline;
	private JButton buttonCancel;
	private JButton buttonOpen;
	private JButton buttonSave;

	private JLabel labelFileInfo;
	private JLabel labelFileLength;
	private JLabel labelFileName;
	private JLabel labelFilePatch;
	private JLabel labelFileSize;
	private JLabel labelFileType;

	private JTextField textFileLength;
	private JTextField textFileName;
	private JTextField textFilePatch;
	private JTextField textFileSeqNo;
	private JTextField textFileSize;
	private JTextField textFileType;

	private File newMultimediaFile = null;

	long fileTimeLengthMilis = -10;

	private Schedule activeSchedule;
	private JFrame owner;

	private User newUser;

	private JFileChooser fileChooser;

	private final ApplicationContext appContext = new ClassPathXmlApplicationContext("application-context.xml");

	private final MultimediaFileDao multimediaFileDao = (MultimediaFileDao) appContext.getBean("multimediaFileDao");
}
