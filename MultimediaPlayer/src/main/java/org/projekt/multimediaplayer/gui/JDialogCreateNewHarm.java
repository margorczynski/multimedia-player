package org.projekt.multimediaplayer.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.*;

import org.projekt.multimediaplayer.dao.UserDao;
import org.projekt.multimediaplayer.model.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;




public class JDialogCreateNewHarm extends JDialog
{

	public JDialogCreateNewHarm(JFrame owner)//, User user)
	{
		super(owner, windowTitle);

		//this.user = user;
		
		initComponents();


		setSize(480, 525);
		setResizable(false);
	}


	public void initComponents()
	{
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout());
		
		labelHeadline = new JLabel("<html><h2><i><b> Utwórz harmonogram </b></i></h2></html>");
		labelHeadline.setAlignmentX(CENTER_ALIGNMENT);
		
		//init var
        labelHarmonogramName = new JLabel();
        labelHarmonogramDesript = new JLabel();
        checkBoxActiv = new JCheckBox();
        buttonCreateHarm = new JButton();
        buttonCancel = new JButton();
        editHarmonogramName = new JTextField();
        editHarmDescript = new JTextArea();
        radioPlayInLoop = new JRadioButton();
        radioOneTimePlay = new JRadioButton();
        labelStartDate = new JLabel();
        labelStartTime = new JLabel();

        spinerStartDate = new JSpinner();
        labelPlayFreq = new JLabel();
        spinerPlayFreq = new JSpinner();

		
		

        labelHarmonogramName.setText("Nazwa harmonogramu:");
        editHarmonogramName.setColumns(30);
        editHarmonogramName.setText("Nazwa harmonogramu");
        
        labelHarmonogramDesript.setText("Opis harmonogramu:");
        
        checkBoxActiv.setText("Ustawiæ aktywnym");
        
        buttonCreateHarm.setText("Utwórz harmonogram");
        buttonCancel.setText("Anuluj");
        

         
        radioPlayInLoop.setSelected(true);
        radioPlayInLoop.setText("Odtwarzanie cykliczne");
        radioOneTimePlay.setText("Odtwarzanie jednorazowo");
        
        ButtonGroup radioButtonGroup = new ButtonGroup();
        radioButtonGroup.add(radioOneTimePlay);
        radioButtonGroup.add(radioPlayInLoop);
        
        labelStartDate.setText("Data rozpoczêcia odtwarzania");
        
        labelStartTime.setText("Godzina rozpoczêcia");
        
        
        //TODO dopracowaæ spinery
        Date date = new Date();
        SpinnerDateModel sm = 
        new SpinnerDateModel(date, null, null, Calendar.HOUR);
        spinerStartTime = new JSpinner(sm);
        JSpinner.DateEditor de = new JSpinner.DateEditor(spinerStartTime, "hh:mm");
        spinerStartTime.setEditor(de);
        
        
        //spinerStartTime.setModel(new SpinnerDateModel(new java.util.Date(1354217356593L), null, null, java.util.Calendar.HOUR));
        //spinerStartTime.setToolTipText("");
        
        labelPlayFreq.setText("Czêstotliwoœæ odtwarzania");
		
        editHarmDescript.setColumns(30);
        editHarmDescript.setRows(5);
        
		JScrollPane editHarmDescriptScrollPane = new JScrollPane(editHarmDescript);
		
        mainPanel.add( labelHeadline , new GBC(0, 0,2,1 ).setAnchor(GBC.CENTER).setInsets(5, 5, 0, 0));
        mainPanel.add( labelHarmonogramName, new GBC(0, 1).setAnchor(GBC.WEST).setInsets(5, 5, 0, 0));
        mainPanel.add( editHarmonogramName, new GBC(0, 2,2,1).setAnchor(GBC.WEST).setInsets(5, 5, 0, 0));
        mainPanel.add( labelHarmonogramDesript, new GBC(0, 3).setAnchor(GBC.WEST).setInsets(20, 5, 0, 0));
        mainPanel.add( editHarmDescriptScrollPane, new GBC(0, 4,2,1).setAnchor(GBC.WEST).setInsets(5, 5, 0, 0));
        mainPanel.add( checkBoxActiv, new GBC(0, 5).setAnchor(GBC.WEST).setInsets(10, 5, 0, 0));
        mainPanel.add( radioPlayInLoop, new GBC(0, 6).setAnchor(GBC.WEST).setInsets(10, 5, 0, 0));
        mainPanel.add( radioOneTimePlay, new GBC(1, 6).setAnchor(GBC.WEST).setInsets(10, 5, 0, 0));
        mainPanel.add( labelStartDate, new GBC(0, 7).setAnchor(GBC.WEST).setInsets(5, 5, 0, 0));
        mainPanel.add( labelStartTime, new GBC(1, 7).setAnchor(GBC.WEST).setInsets(5, 15, 0, 0));
        mainPanel.add( spinerStartDate, new GBC(0, 8).setAnchor(GBC.WEST).setInsets(5, 5, 0, 0));
        mainPanel.add( spinerStartTime, new GBC(1, 8).setAnchor(GBC.WEST).setInsets(5, 15, 0, 0));
        mainPanel.add( labelPlayFreq, new GBC(0, 9).setAnchor(GBC.WEST).setInsets(20, 5, 0, 0));
        mainPanel.add( spinerPlayFreq, new GBC(0, 10).setAnchor(GBC.WEST).setInsets(5, 5, 0, 0));
        mainPanel.add( buttonCreateHarm, new GBC(0, 11).setAnchor(GBC.CENTER).setInsets(20, 5, 25, 0));
        mainPanel.add( buttonCancel, new GBC(1, 11).setAnchor(GBC.CENTER).setInsets(20, 5, 25, 0));
		
		
	
		this.setContentPane(mainPanel);
		pack();
		
		
		buttonCancel.addActionListener( new ActionListener()
		{
			
			public void actionPerformed(ActionEvent arg0)
			{
				setVisible(false);	
			}
		});
		
		
		buttonCreateHarm.addActionListener(new ActionListener()
		{
			
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				
			}
		});
	}
	

	private static String windowTitle = "Utworz nowy harmonogram";
	
	JLabel labelHeadline;

    private JButton buttonCancel;
    private JButton buttonCreateHarm;
    private JCheckBox checkBoxActiv;
    private JScrollPane descriptScrollPane;
    private JTextArea editHarmDescript;
    private JTextField editHarmonogramName;
    private JLabel labelHarmonogramDesript;
    private JLabel labelHarmonogramName;
    private JLabel labelPlayFreq;
    private JLabel labelStartDate;
    private JLabel labelStartTime;
    private JRadioButton radioOneTimePlay;
    private JRadioButton radioPlayInLoop;
    private JSpinner spinerPlayFreq;
    private JSpinner spinerStartDate;
    private JSpinner spinerStartTime;

	User user;
}
