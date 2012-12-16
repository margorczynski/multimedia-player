package org.projekt.multimediaplayer.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.text.NumberFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeNode;

import org.projekt.multimediaplayer.dao.MultimediaFileDao;
import org.projekt.multimediaplayer.dao.ScheduleDao;
import org.projekt.multimediaplayer.dao.UserDao;
import org.projekt.multimediaplayer.model.MultimediaFile;
import org.projekt.multimediaplayer.model.Schedule;
import org.projekt.multimediaplayer.model.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JDialogShowHarmonograms extends JDialog
{

	public JDialogShowHarmonograms(MultimediaPlayerJFrame owner)
	{
		super(owner, "Podgl¹d harmonogramu", ModalityType.APPLICATION_MODAL);
		ownerFrame = owner;
		initComponents();
		initActionLisnerButtons();
		setSize(770, 610);
		setResizable(false);
	}


	public void setDefault()
	{
		// DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("BRAK");
		// treeModel = new DefaultTreeModel(rootNode);
		// treeHarmonogram.setModel(treeModel);

		editHarmonogramName.setText(" ");
		editHarmonogramDescript.setText(" ");
		editHarmonogramActive.setText(" ");
		editFileName.setText(" ");
		editFileDescript.setText(" ");
		editSize.setText(" ");
		// editSequence.setText(" ");
		editFileLength.setText(" ");
		editFileFormat.setText(" ");
	}

	public void initActionLisnerButtons()
	{

		closeButton.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				setVisible(false);
			}
		});

		addNewMediaButton.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				TreePath path = treeHarmonogram.getSelectionPath();
				if (path == null) return;
				DefaultMutableTreeNode selectNode = (DefaultMutableTreeNode) path.getLastPathComponent();
				Object node = selectNode.getUserObject();

				if (node instanceof Schedule)
				{
					System.out.println("Dodaje do harm: " + ((Schedule) node).getName());
					if (addMultimediaFileJDialog == null)
						addMultimediaFileJDialog = new JDialogAddMultimediaFile(ownerFrame, (Schedule) node);
					else
					{
						addMultimediaFileJDialog.setSchedule(((Schedule) node));
					}
					addMultimediaFileJDialog.setVisible(true);
					ownerFrame.refreshLogInUser();
					buildContentOfTree();
				}
			}
		});

		addNewHarmonogramButton.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				if (jDialogCreateNewHarm == null) jDialogCreateNewHarm = new JDialogCreateNewHarm(ownerFrame);
				jDialogCreateNewHarm.setVisible(true);
				ownerFrame.refreshActiveSchedule();
				thisFrame.buildContentOfTree();
			}
		});

		deleteHarmMediaButton.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				TreePath path = treeHarmonogram.getSelectionPath();
				if (path == null) return;
				DefaultMutableTreeNode selectNode = (DefaultMutableTreeNode) path.getLastPathComponent();
				Object node = selectNode.getUserObject();

				if (node instanceof Schedule)
				{
					Schedule scheduleNode = (Schedule) node;
					if (JOptionPane.showConfirmDialog(thisFrame, (Object) "Czy jesteœ pewien ze chcesz usun¹æ harmonogram: \"" + scheduleNode.getName() + "\" i wszystkie pliki multimedialne z nim zwi¹zane ?", "Usuwñ harmonogram", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
					{
						// czy harmonogram ktory chcemy usunac jest aktywny ??
						if (!scheduleNode.isActive())
						{

							Set<MultimediaFile> multimediaFile = scheduleNode.getScheduleMultimediaFiles();
							for (MultimediaFile mf : multimediaFile)
							{
								multimediaDao.deleteMultimediaFile(mf);
							}
							scheduleDao.deleteSchedule(scheduleNode);

						}

						// harmonogram aktywny wyrzuc jakis blad czy cos
						else
						{
							if (JOptionPane.showConfirmDialog(thisFrame, (Object) "Chcesz usun¹c aktywny harmonogram ! Aktywnym harmonogramem stanie siê kolejny z listy ( je¿eli nie ma wiecej harmonogramów zostanie Ci mo¿liwoœæ tylko odtwarzania wybranych plików )", "Usuwñ harmonogram", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
							{
								Set<MultimediaFile> multimediaFile = scheduleNode.getScheduleMultimediaFiles();
								for (MultimediaFile mf : multimediaFile)
								{
									multimediaDao.deleteMultimediaFile(mf);
								}
								scheduleDao.deleteSchedule(scheduleNode);

								ownerFrame.refreshLogInUser();

								Set<Schedule> schedulesSet = ownerFrame.getLogInUser().getUserSchedules();
								if (schedulesSet.size() != 0)
								{
									for (Schedule s : schedulesSet)
									{
										s.setActive(true);
										scheduleDao.updateSchedule(s);
										break;
									}
									ownerFrame.refreshActiveSchedule();
								}
							}
						}// else usuwasz aktywny harmonogram
					}// czy jestes pewien usuniecia
				}// czy to harmonogram

				else if (node instanceof MultimediaFile)
				{
					MultimediaFile multiNode = (MultimediaFile) node;
					if (JOptionPane.showConfirmDialog(thisFrame, (Object) "Czy jesteœ pewien ze chcesz usun¹æ harmonogram: \"" + multiNode.getFilename() + "\"  ?", "Usuñ plik", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
					{
						multimediaDao.deleteMultimediaFile((MultimediaFile) node);
					}
				}
				ownerFrame.refreshLogInUser();
				buildContentOfTree();
			}
		});

	}

	public void initComponents()
	{

		// * * M a i n P a n e l
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout());

		labelHeadline = new JLabel("<html><h2><i><b> Informacje o harmonogramie </b></i></h2></html>");
		labelHeadline.setAlignmentX(CENTER_ALIGNMENT);

		// * * H a r m o n o g r a m
		harmonogramPanel = new JPanel(new GridBagLayout());

		labelHarmonogramName = new JLabel("Nazwa harmonogramu: ");
		labelHarmonogramDescript = new JLabel("Opis harmonogramu: ");
		labelHarmonogramActive = new JLabel("Aktywny: ");

		editHarmonogramName = new JTextField(" ");
		editHarmonogramName.setColumns(15);
		editHarmonogramName.setEditable(false);

		editHarmonogramDescript = new JTextField(" ");
		editHarmonogramDescript.setColumns(15);
		editHarmonogramDescript.setEditable(false);

		editHarmonogramActive = new JTextField(" ");
		editHarmonogramActive.setColumns(15);
		editHarmonogramActive.setEditable(false);

		// GBC(kolumna, wiersz, ile kolumn, ile wierszy)
		harmonogramPanel.add(labelHarmonogramName, new GBC(0, 0).setAnchor(GBC.EAST).setInsets(10, 15, 0, 0));
		harmonogramPanel.add(labelHarmonogramDescript, new GBC(0, 1).setAnchor(GBC.EAST).setInsets(10, 15, 0, 0));
		harmonogramPanel.add(labelHarmonogramActive, new GBC(0, 2).setAnchor(GBC.EAST).setInsets(10, 15, 0, 0));

		harmonogramPanel.add(editHarmonogramName, new GBC(1, 0).setAnchor(GBC.EAST).setInsets(10, 15, 0, 0));
		harmonogramPanel.add(editHarmonogramDescript, new GBC(1, 1).setAnchor(GBC.EAST).setInsets(10, 15, 0, 0));
		harmonogramPanel.add(editHarmonogramActive, new GBC(1, 2).setAnchor(GBC.EAST).setInsets(10, 15, 0, 0));

		// * * M u l t i m e d i a
		filePanel = new JPanel(new GridBagLayout());
		labelFileName = new JLabel("Nazwa pliku: ");
		labelFileDescript = new JLabel("Opis: ");
		labelSize = new JLabel("Rozmiar: ");
		// labelSequence = new JLabel("Kolejnoœæ: ");
		labelFileLength = new JLabel("D³ugoœæ: ");
		labelFileFormat = new JLabel("Format: ");

		editFileName = new JTextField(" ");
		editFileName.setColumns(15);
		editFileName.setEditable(false);

		editFileDescript = new JTextField(" ");
		editFileDescript.setColumns(15);
		editFileDescript.setEditable(false);
		editSize = new JTextField(" ");
		editSize.setColumns(15);
		editSize.setEditable(false);

		editFileLength = new JTextField(" ");
		editFileLength.setColumns(15);
		editFileLength.setEditable(false);

		editFileFormat = new JTextField();
		editFileFormat.setColumns(15);
		editFileFormat.setEditable(false);

		filePanel.add(labelFileName, new GBC(0, 0).setAnchor(GBC.EAST).setInsets(10, 15, 0, 0));
		filePanel.add(labelFileDescript, new GBC(0, 1).setAnchor(GBC.EAST).setInsets(10, 15, 0, 0));
		filePanel.add(labelSize, new GBC(0, 2).setAnchor(GBC.EAST).setInsets(10, 15, 0, 0));
		// filePanel.add(labelSequence, new GBC(0,
		// 3).setAnchor(GBC.EAST).setInsets(10, 15, 0, 0));
		filePanel.add(labelFileLength, new GBC(0, 3).setAnchor(GBC.EAST).setInsets(10, 15, 0, 0));
		filePanel.add(labelFileFormat, new GBC(0, 4).setAnchor(GBC.EAST).setInsets(10, 15, 0, 0));

		filePanel.add(editFileName, new GBC(1, 0).setAnchor(GBC.WEST).setInsets(10, 15, 0, 0));
		filePanel.add(editFileDescript, new GBC(1, 1).setAnchor(GBC.WEST).setInsets(10, 15, 0, 0));
		filePanel.add(editSize, new GBC(1, 2).setAnchor(GBC.WEST).setInsets(10, 15, 0, 0));
		// filePanel.add(editSequence, new GBC(1,
		// 3).setAnchor(GBC.WEST).setInsets(10, 15, 0, 0));
		filePanel.add(editFileLength, new GBC(1, 3).setAnchor(GBC.WEST).setInsets(10, 15, 0, 0));
		filePanel.add(editFileFormat, new GBC(1, 4).setAnchor(GBC.WEST).setInsets(10, 15, 0, 0));

		// * * D r z e w o

		createTree();

		JPanel buttonsPanel = new JPanel(new GridBagLayout());

		closeButton = new JButton(" Zamknij ");
		addNewMediaButton = new JButton(" Dodaj plik ");
		addNewHarmonogramButton = new JButton(" Dodaj harmonogram ");
		deleteHarmMediaButton = new JButton(" Usun ");

		buttonsPanel.add(addNewMediaButton, new GBC(0, 0).setAnchor(GBC.EAST).setInsets(20, 15, 0, 0));
		buttonsPanel.add(addNewHarmonogramButton, new GBC(1, 0).setAnchor(GBC.EAST).setInsets(20, 15, 0, 0));
		buttonsPanel.add(deleteHarmMediaButton, new GBC(2, 0).setAnchor(GBC.EAST).setInsets(20, 15, 0, 0));
		buttonsPanel.add(closeButton, new GBC(3, 0).setAnchor(GBC.EAST).setInsets(20, 15, 0, 0));

		mainPanel.add(labelHeadline, new GBC(0, 0, 2, 1).setAnchor(GBC.CENTER).setInsets(20, 15, 0, 0));
		mainPanel.add(scrollPaneTree, new GBC(0, 1, 1, 2).setAnchor(GBC.EAST).setInsets(20, 15, 0, 0));
		mainPanel.add(harmonogramPanel, new GBC(1, 1).setAnchor(GBC.EAST).setInsets(20, 15, 0, 0));
		mainPanel.add(filePanel, new GBC(1, 2).setAnchor(GBC.EAST).setInsets(20, 15, 0, 0));
		mainPanel.add(buttonsPanel, new GBC(0, 3, 2, 1).setAnchor(GBC.CENTER).setInsets(20, 15, 0, 0));

		this.setContentPane(mainPanel);
		this.pack();

	}

	public void createTree()
	{
		treeHarmonogram = new JTree();
		scrollPaneTree = new JScrollPane(treeHarmonogram);
		scrollPaneTree.setPreferredSize(new Dimension(350, 400));
		treeHarmonogram.setCellRenderer(new MyNodeRenderer());

		treeHarmonogram.addTreeSelectionListener(new TreeSelectionListener()
		{
			public void valueChanged(TreeSelectionEvent e)
			{
				TreePath path = treeHarmonogram.getSelectionPath();
				if (path == null) return;
				DefaultMutableTreeNode selectNode = (DefaultMutableTreeNode) path.getLastPathComponent();
				Object node = selectNode.getUserObject();

				if (node instanceof Schedule)
				{
					Schedule schedule = (Schedule) node;
					setHarmInfo(schedule);
					clearFileInfo();
				}
				else if (node instanceof MultimediaFile)
				{
					MultimediaFile mf = (MultimediaFile) node;
					setMultimediaFileInfo(mf);

					DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) selectNode.getParent();
					Object parentObject = parentNode.getUserObject();

					if (parentObject instanceof Schedule) setHarmInfo((Schedule) parentObject);
				}
				else
				{

				}
			}
		});

		buildContentOfTree();

	}

	public void setMultimediaFileInfo(MultimediaFile mf)
	{
		editFileName.setText(mf.getFilename());
		editFileDescript.setText(mf.getPath());
		editFileLength.setText(getTimeFromMilis(mf.getLength()));

		Locale locale = new Locale("pl", "PL");
		NumberFormat currFormat = NumberFormat.getNumberInstance(locale);
		Double fileSizeMb = mf.getSize() / (1024.0 * 1024.0);
		String formatSize = currFormat.format(fileSizeMb);
		editSize.setText(formatSize + "Mb");

		editFileFormat.setText(mf.getType());
	}

	public void clearFileInfo()
	{
		editFileName.setText("");
		editFileDescript.setText("");
		editFileLength.setText("");
		editSize.setText("");
	}

	public void setHarmInfo(Schedule schedule)
	{
		editHarmonogramName.setText(schedule.getName());
		editHarmonogramDescript.setText(schedule.getDescription());
		editHarmonogramActive.setText(schedule.isActive() ? "Aktywny" : "Nie aktywny");
	}

	private String getTimeFromMilis(long millis)
	{
		String s = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis), TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
		return s;
	}

	public void buildContentOfTree()
	{
		System.out.println("Build content of tree");
		newUser = ownerFrame.getLogInUser();
		Set<Schedule> schedulesSet = newUser.getUserSchedules();
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Harmonogramy");

		for (Schedule sched : schedulesSet)
		{
			DefaultMutableTreeNode scheduleNode = new DefaultMutableTreeNode(sched);
			rootNode.add(scheduleNode);

			Set<MultimediaFile> multimediaFileSet = sched.getScheduleMultimediaFiles();
			for (MultimediaFile multimediaFile : multimediaFileSet)
			{
				DefaultMutableTreeNode multimediaNode = new DefaultMutableTreeNode(multimediaFile);
				scheduleNode.add(multimediaNode);
			}
		}

		treeModel = new DefaultTreeModel(rootNode);
		treeHarmonogram.setModel(treeModel);
	}

	private JDialogAddMultimediaFile addMultimediaFileJDialog;
	//
	MultimediaPlayerJFrame ownerFrame;

	// Buttony
	JButton closeButton;
	JButton addNewMediaButton;
	JButton addNewHarmonogramButton;
	JButton deleteHarmMediaButton;
	private JDialogShowHarmonograms thisFrame = this;
	private JDialogCreateNewHarm jDialogCreateNewHarm;
	// Drzewo
	JTree treeHarmonogram = null;
	JScrollPane scrollPaneTree;
	DefaultTreeModel treeModel;

	// harmonogram
	JPanel harmonogramPanel;
	JLabel labelHeadline;
	JLabel labelHarmonogramName;
	JLabel labelHarmonogramDescript;
	JLabel labelHarmonogramActive;

	JTextField editHeadline;
	JTextField editHarmonogramName;
	JTextField editHarmonogramDescript;
	JTextField editHarmonogramActive;

	// opis pliku

	JPanel filePanel;
	JLabel labelFileName;
	JLabel labelFileDescript;
	JLabel labelSize;
	JLabel labelFileLength;
	JLabel labelFileFormat;

	JTextField editFileName;
	JTextField editFileDescript;
	JTextField editSize;
	JTextField editFileLength;
	JTextField editFileFormat;

	User newUser;

	private final ApplicationContext appContext = new ClassPathXmlApplicationContext("application-context.xml");
	private final UserDao userDao = (UserDao) appContext.getBean("userDao");
	private final ScheduleDao scheduleDao = (ScheduleDao) appContext.getBean("scheduleDao");
	private final MultimediaFileDao multimediaDao = (MultimediaFileDao) appContext.getBean("multimediaFileDao");
}
