package org.projekt.multimediaplayer.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.text.NumberFormat;
import java.util.Date;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JDialogShowHarmonograms extends JDialog
{
	public JDialogShowHarmonograms(MultimediaPlayerJFrame owner)
	{
		super(owner, "Podgl¹d harmonogramu", ModalityType.APPLICATION_MODAL);
		ownerFrame = owner;
		Point point = owner.getLocationOnScreen();
		this.setLocation((int)point.getX()+150, (int)point.getY()+55);
		
		initComponents();
		initActionLisnerButtons();
		setSize(770, 610);
		setResizable(false);
	}

	public void setDefault()
	{
		editHarmonogramName.setText(" ");
		editHarmonogramDescript.setText(" ");
		editHarmonogramActive.setText(" ");
		// editHarmonogramStartDate.setText(" ");
		editFileName.setText(" ");
		editFilePath.setText(" ");
		editSize.setText(" ");
		// editSequence.setText(" ");
		editFileLength.setText(" ");
		editFileFormat.setText(" ");
		spinerDateModel.setValue(new Date());
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

							ownerFrame.getMultimediaPanel().deleteActiveSchedule();
							ownerFrame.suspendThread();

							scheduleDao.deleteSchedule(scheduleNode);
						}

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
								ownerFrame.getMultimediaPanel().deleteActiveSchedule();

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
					if (JOptionPane.showConfirmDialog(thisFrame, (Object) "Czy jesteœ pewien ze chcesz usun¹æ ten plik: \"" + multiNode.getFilename() + "\"  ?", "Usuñ plik", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
					{

						DefaultMutableTreeNode parent = (DefaultMutableTreeNode) selectNode.getParent();
						Schedule parentSchedule = (Schedule) parent.getUserObject();

						if (parentSchedule.isActive())
						{
							ownerFrame.getMultimediaPanel().deleteFileFromActiveList(((MultimediaFile) node).getFilename());
							multimediaDao.deleteMultimediaFile((MultimediaFile) node);
						}
						else
						{
							multimediaDao.deleteMultimediaFile((MultimediaFile) node);
						}
					}
				}
				ownerFrame.refreshLogInUser();
				buildContentOfTree();
			}
		});

		setActivThisButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				TreePath path = treeHarmonogram.getSelectionPath();
				if (path == null) return;
				DefaultMutableTreeNode selectNode = (DefaultMutableTreeNode) path.getLastPathComponent();
				Object node = selectNode.getUserObject();

				if (node instanceof Schedule)
				{
					Schedule scheduleNode = (Schedule) node;
					if (JOptionPane.showConfirmDialog(thisFrame, (Object) "Czy jesteœ pewien ze chcesz uruchomiæ : \"" + scheduleNode.getName() + "\" ?", "Uruchom harmonogram", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
					{
						if (scheduleNode.isActive())
						{
							boolean a = ownerFrame.playerThread.isAlive();
							System.out.println(a);
							ownerFrame.refreshActiveSchedule();
							ownerFrame.runRunnableSched();
						}
						else
						{
							if (ownerFrame.getActiveShedule() != null)
							{
								Schedule activeSchedule = ownerFrame.getActiveShedule();
								activeSchedule.setActive(false);
								scheduleDao.updateSchedule(activeSchedule);

								scheduleNode.setActive(true);
								scheduleDao.updateSchedule(scheduleNode);

								ownerFrame.refreshActiveSchedule();
								buildContentOfTree();
							}
							else 
							{
								scheduleNode.setActive(true);
								scheduleDao.updateSchedule(scheduleNode);

								ownerFrame.refreshActiveSchedule();
								buildContentOfTree();
							}
						}
					}
				}
			}
		});

		saveChangeButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				TreePath path = treeHarmonogram.getSelectionPath();
				if (path == null) return;
				DefaultMutableTreeNode selectNode = (DefaultMutableTreeNode) path.getLastPathComponent();
				Object nodeUser = selectNode.getUserObject();

				if (nodeUser instanceof Schedule)
				{
					Schedule treeSchedule = (Schedule) nodeUser;
					// czy cos sie zmienilo
					if (!treeSchedule.getName().equals(editHarmonogramName.getText()))
					{
						List<Schedule> findSched = scheduleDao.findShedule(editHarmonogramName.getText());

						if (findSched.size() == 0 || findSched == null)
						{
							treeSchedule.setName(editHarmonogramName.getText());
							treeSchedule.setDescription(editHarmonogramDescript.getText());
							treeSchedule.setStartTime(spinerDateModel.getDate());
							scheduleDao.updateSchedule(treeSchedule);

							if (treeSchedule.isActive())
							{
								ownerFrame.refreshActiveSchedule();
							}
							buildContentOfTree();
						}
						else
						{
							JOptionPane.showMessageDialog(thisFrame, "Harmonogram o takie nazwie ju¿ istnieje !", "B³¹d", JOptionPane.ERROR_MESSAGE);
						}
					}
					else if (!treeSchedule.getDescription().equals(editHarmonogramDescript.getText()) || !treeSchedule.getStartTime().equals(spinerDateModel.getDate()))
					{
						treeSchedule.setDescription(editHarmonogramDescript.getText());
						treeSchedule.setStartTime(spinerDateModel.getDate());

						scheduleDao.updateSchedule(treeSchedule);

						if (treeSchedule.isActive())
						{
							ownerFrame.refreshActiveSchedule();
						}
						buildContentOfTree();
					}
				}

				else if (nodeUser instanceof MultimediaFile)
				{
					DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) selectNode.getParent();
					Object parentUser = parentNode.getUserObject();

					// rodzic pliku
					if (parentUser instanceof Schedule)
					{

						Schedule treeSchedule = (Schedule) parentUser;
						// czy cos sie zmienilo
						if (!treeSchedule.getName().equals(editHarmonogramName.getText()))
						{
							List<Schedule> findSched = scheduleDao.findShedule(editHarmonogramName.getText());

							if (findSched.size() == 0 || findSched == null)
							{
								treeSchedule.setName(editHarmonogramName.getText());
								treeSchedule.setDescription(editHarmonogramDescript.getText());
								treeSchedule.setStartTime(spinerDateModel.getDate());
								scheduleDao.updateSchedule(treeSchedule);

								if (treeSchedule.isActive())
								{
									ownerFrame.refreshActiveSchedule();
								}
								buildContentOfTree();
							}

							else
							{
								JOptionPane.showMessageDialog(thisFrame, "Harmonogram o takie nazwie ju¿ istnieje !", "B³¹d", JOptionPane.ERROR_MESSAGE);
							}

						}
						else if (!treeSchedule.getDescription().equals(editHarmonogramDescript.getText()) || !treeSchedule.getStartTime().equals(spinerDateModel.getDate()))
						{
							treeSchedule.setDescription(editHarmonogramDescript.getText());
							treeSchedule.setStartTime(spinerDateModel.getDate());

							scheduleDao.updateSchedule(treeSchedule);

							if (treeSchedule.isActive())
							{
								ownerFrame.refreshActiveSchedule();
							}
							buildContentOfTree();
						}
					}
				}
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
		labelHarmonogramStartDate = new JLabel("Data startu: ");

		editHarmonogramName = new JTextField(" ");
		editHarmonogramName.setColumns(15);
		// editHarmonogramName.setEditable(false);

		editHarmonogramDescript = new JTextField(" ");
		editHarmonogramDescript.setColumns(15);
		// editHarmonogramDescript.setEditable(false);

		editHarmonogramActive = new JTextField(" ");
		editHarmonogramActive.setColumns(15);
		editHarmonogramActive.setEditable(false);

		spinerDateModel = new SpinnerDateModel();
		spinnerHarmonogramStartDate = new JSpinner(spinerDateModel);

		// GBC(kolumna, wiersz, ile kolumn, ile wierszy)
		harmonogramPanel.add(labelHarmonogramName, new GBC(0, 0).setAnchor(GBC.EAST).setInsets(10, 15, 0, 0));
		harmonogramPanel.add(labelHarmonogramDescript, new GBC(0, 1).setAnchor(GBC.EAST).setInsets(10, 15, 0, 0));
		harmonogramPanel.add(labelHarmonogramActive, new GBC(0, 2).setAnchor(GBC.EAST).setInsets(10, 15, 0, 0));
		harmonogramPanel.add(labelHarmonogramStartDate, new GBC(0, 3).setAnchor(GBC.EAST).setInsets(10, 15, 0, 0));

		harmonogramPanel.add(editHarmonogramName, new GBC(1, 0).setAnchor(GBC.EAST).setInsets(10, 15, 0, 0));
		harmonogramPanel.add(editHarmonogramDescript, new GBC(1, 1).setAnchor(GBC.EAST).setInsets(10, 15, 0, 0));
		harmonogramPanel.add(editHarmonogramActive, new GBC(1, 2).setAnchor(GBC.EAST).setInsets(10, 15, 0, 0));
		harmonogramPanel.add(spinnerHarmonogramStartDate, new GBC(1, 3).setAnchor(GBC.WEST).setInsets(10, 15, 0, 0));

		// * * M u l t i m e d i a
		filePanel = new JPanel(new GridBagLayout());
		labelFileName = new JLabel("Nazwa pliku: ");
		labelFilePath = new JLabel("Œcie¿ka: ");
		labelSize = new JLabel("Rozmiar: ");
		// labelSequence = new JLabel("Kolejnoœæ: ");
		labelFileLength = new JLabel("D³ugoœæ: ");
		labelFileFormat = new JLabel("Format: ");

		editFileName = new JTextField(" ");
		editFileName.setColumns(15);
		editFileName.setEditable(false);

		editFilePath = new JTextField(" ");
		editFilePath.setColumns(15);
		editFilePath.setEditable(false);

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
		filePanel.add(labelFilePath, new GBC(0, 1).setAnchor(GBC.EAST).setInsets(10, 15, 0, 0));
		filePanel.add(labelSize, new GBC(0, 2).setAnchor(GBC.EAST).setInsets(10, 15, 0, 0));
		filePanel.add(labelFileLength, new GBC(0, 3).setAnchor(GBC.EAST).setInsets(10, 15, 0, 0));
		filePanel.add(labelFileFormat, new GBC(0, 4).setAnchor(GBC.EAST).setInsets(10, 15, 0, 0));

		filePanel.add(editFileName, new GBC(1, 0).setAnchor(GBC.WEST).setInsets(10, 15, 0, 0));
		filePanel.add(editFilePath, new GBC(1, 1).setAnchor(GBC.WEST).setInsets(10, 15, 0, 0));
		filePanel.add(editSize, new GBC(1, 2).setAnchor(GBC.WEST).setInsets(10, 15, 0, 0));
		filePanel.add(editFileLength, new GBC(1, 3).setAnchor(GBC.WEST).setInsets(10, 15, 0, 0));
		filePanel.add(editFileFormat, new GBC(1, 4).setAnchor(GBC.WEST).setInsets(10, 15, 0, 0));

		// * * D r z e w o

		createTree();

		JPanel buttonsPanel = new JPanel(new GridBagLayout());

		closeButton = new JButton(" Zamknij ");
		addNewMediaButton = new JButton(" Dodaj plik ");
		addNewHarmonogramButton = new JButton(" Dodaj harmonogram ");
		deleteHarmMediaButton = new JButton(" Usun ");
		setActivThisButton = new JButton("Uruchom harmonogram");
		saveChangeButton = new JButton("Zapisz");

		buttonsPanel.add(addNewMediaButton, new GBC(0, 0).setAnchor(GBC.EAST).setInsets(20, 15, 0, 0));
		buttonsPanel.add(addNewHarmonogramButton, new GBC(1, 0).setAnchor(GBC.EAST).setInsets(20, 15, 0, 0));
		buttonsPanel.add(deleteHarmMediaButton, new GBC(2, 0).setAnchor(GBC.EAST).setInsets(20, 15, 0, 0));
		buttonsPanel.add(setActivThisButton, new GBC(3, 0).setAnchor(GBC.EAST).setInsets(20, 15, 0, 0));
		buttonsPanel.add(saveChangeButton, new GBC(4, 0).setAnchor(GBC.EAST).setInsets(20, 15, 0, 0));
		buttonsPanel.add(closeButton, new GBC(5, 0).setAnchor(GBC.EAST).setInsets(20, 15, 0, 0));

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
		editFilePath.setText(mf.getPath());
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
		editFilePath.setText("");
		editFileLength.setText("");
		editSize.setText("");
	}

	public void setHarmInfo(Schedule schedule)
	{
		editHarmonogramName.setText(schedule.getName());
		editHarmonogramDescript.setText(schedule.getDescription());
		editHarmonogramActive.setText(schedule.isActive() ? "Aktywny" : "Nie aktywny");

		spinerDateModel.setValue(schedule.getStartTime());
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
	private JButton closeButton;
	private 	JButton addNewMediaButton;
	private JButton addNewHarmonogramButton;
	private JButton deleteHarmMediaButton;
	private JButton setActivThisButton;
	private JButton saveChangeButton;

	private JDialogShowHarmonograms thisFrame = this;
	private JDialogCreateNewHarm jDialogCreateNewHarm;
	// Drzewo
	private JTree treeHarmonogram = null;
	private JScrollPane scrollPaneTree;
	private DefaultTreeModel treeModel;

	// harmonogram
	private JPanel harmonogramPanel;
	private JLabel labelHeadline;
	private JLabel labelHarmonogramName;
	private JLabel labelHarmonogramDescript;
	private JLabel labelHarmonogramActive;
	private JLabel labelHarmonogramStartDate;

	private JTextField editHeadline;
	private JTextField editHarmonogramName;
	private JTextField editHarmonogramDescript;
	private JTextField editHarmonogramActive;
	private JSpinner spinnerHarmonogramStartDate;
	private SpinnerDateModel spinerDateModel;

	// opis pliku

	private JPanel filePanel;
	private JLabel labelFileName;
	private JLabel labelFilePath;
	private JLabel labelSize;
	private JLabel labelFileLength;
	private JLabel labelFileFormat;

	private JTextField editFileName;
	private JTextField editFilePath;
	private JTextField editSize;
	private JTextField editFileLength;
	private JTextField editFileFormat;

	User newUser;

	private final ApplicationContext appContext = new ClassPathXmlApplicationContext("application-context.xml");
	private final UserDao userDao = (UserDao) appContext.getBean("userDao");
	private final ScheduleDao scheduleDao = (ScheduleDao) appContext.getBean("scheduleDao");
	private final MultimediaFileDao multimediaDao = (MultimediaFileDao) appContext.getBean("multimediaFileDao");
}
