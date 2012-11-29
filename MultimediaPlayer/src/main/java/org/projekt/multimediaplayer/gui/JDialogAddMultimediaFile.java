package org.projekt.multimediaplayer.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.io.File;
import java.io.IOException;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.List;
import java.util.Locale;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;

import org.projekt.multimediaplayer.dao.UserDao;
import org.projekt.multimediaplayer.model.Schedule;
import org.projekt.multimediaplayer.model.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import uk.co.caprica.vlcj.filter.swing.SwingFileFilterFactory;

public class JDialogAddMultimediaFile extends JDialog
{

	public JDialogAddMultimediaFile(JFrame owner, Schedule schedule)
	{
		super(owner, windowTitle);

		this.schedule = schedule;
		this.owner = owner;

		initComponents();

		setSize(530, 420);
		setResizable(false);

	}

	public void setDefault()
	{
		labelFileName.setText("Nazwa pliku");
		labelFileSize.setText("Rozmiar");
		labelFileType.setText("Typ");
		labelFileLength.setText("D³ugoœæ");

		textFileName.setText("Wybierz plik ... ");
		textFileSize.setText("Wybierz plik ... ");
		textFileType.setText("Wybierz plik ... ");
		textFileLength.setText("Wybierz plik ... ");

		labelFileInfo.setText("Informacje o pliku");
		labelDisplayInfo.setText("Informacje dotycz¹ce wyœwietlenia");
		labelFileSeqNo.setText("Pozycja na liscie");
		labelFileDelay.setText("OpoŸnienie odtwarzania");
		textFileDelay.setText("0");
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
		labelDisplayInfo = new JLabel();
		labelFileSeqNo = new JLabel();
		labelFileDelay = new JLabel();
		textFileSeqNo = new JTextField();
		textFileDelay = new JTextField();

		textFileDelay.setColumns(15);
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
		labelDisplayInfo.setText("Informacje dotycz¹ce wyœwietlenia");
		labelFileSeqNo.setText("Pozycja na liscie");
		labelFileDelay.setText("OpoŸnienie odtwarzania");
		textFileDelay.setText("0");

		middlePanel.add(labelFileInfo, new GBC(0, 0, 2, 1).setAnchor(GBC.CENTER).setInsets(5, 5, 10, 0));

		middlePanel.add(labelFileName, new GBC(0, 1).setAnchor(GBC.WEST).setInsets(5, 5, 0, 0));
		middlePanel.add(labelFileSize, new GBC(0, 2).setAnchor(GBC.WEST).setInsets(5, 5, 0, 0));
		middlePanel.add(labelFileType, new GBC(0, 3).setAnchor(GBC.WEST).setInsets(5, 5, 0, 0));
		middlePanel.add(labelFileLength, new GBC(0, 4).setAnchor(GBC.WEST).setInsets(5, 5, 0, 0));
		middlePanel.add(labelDisplayInfo, new GBC(0, 5, 2, 1).setAnchor(GBC.CENTER).setInsets(20, 5, 10, 0));
		middlePanel.add(labelFileSeqNo, new GBC(0, 6).setAnchor(GBC.WEST).setInsets(5, 5, 0, 0));
		middlePanel.add(labelFileDelay, new GBC(0, 7).setAnchor(GBC.WEST).setInsets(5, 5, 0, 0));

		middlePanel.add(textFileName, new GBC(1, 1).setAnchor(GBC.WEST).setInsets(5, 5, 0, 0));
		middlePanel.add(textFileSize, new GBC(1, 2).setAnchor(GBC.WEST).setInsets(5, 5, 0, 0));
		middlePanel.add(textFileType, new GBC(1, 3).setAnchor(GBC.WEST).setInsets(5, 5, 0, 0));
		middlePanel.add(textFileLength, new GBC(1, 4).setAnchor(GBC.WEST).setInsets(5, 5, 0, 0));

		middlePanel.add(textFileSeqNo, new GBC(1, 6).setAnchor(GBC.WEST).setInsets(5, 5, 0, 0));
		middlePanel.add(textFileDelay, new GBC(1, 7).setAnchor(GBC.WEST).setInsets(5, 5, 0, 0));

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
				// TODO Auto-generated method stub

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
				// TODO Auto-generated method stub

			}
		});

		buttonCancel.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent arg0)
			{
				setVisible(false);
				setDefault();
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

			// TODO 'Dodaj plik' / Dla pliku utworzyc mediaPlayerList czy jakos tak i
			// zczytaæ ile trwa i wypisaæ

			// TODO AddFile / Z Schedule przeliczyc sobie ile jest rekordow i
			// wpisaæ ostatni numer + 1;

		}
	}

	private static String windowTitle = "Dodaj nowy plik";

	private JLabel labelHeadline;
	private JButton buttonCancel;
	private JButton buttonOpen;
	private JButton buttonSave;

	private JLabel labelDisplayInfo;
	private JLabel labelFileDelay;
	private JLabel labelFileInfo;
	private JLabel labelFileLength;
	private JLabel labelFileName;
	private JLabel labelFilePatch;
	private JLabel labelFileSeqNo;
	private JLabel labelFileSize;
	private JLabel labelFileType;

	private JTextField textFileDelay;
	private JTextField textFileLength;
	private JTextField textFileName;
	private JTextField textFilePatch;
	private JTextField textFileSeqNo;
	private JTextField textFileSize;
	private JTextField textFileType;

	private File newMultimediaFile;
	private Schedule schedule;
	private JFrame owner;

	private User newUser;

	private JFileChooser fileChooser;

}
