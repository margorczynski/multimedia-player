package org.projekt.multimediaplayer.gui;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;
import org.projekt.multimediaplayer.model.MultimediaFile;
import org.projekt.multimediaplayer.model.Schedule;
import org.projekt.multimediaplayer.model.User;


import uk.co.caprica.vlcj.player.list.MediaListPlayerMode;

public class PlayMultimediaFromSchedulRunnable implements Runnable
{
	public PlayMultimediaFromSchedulRunnable(MultimediaPlayerJFrame ownerFrame, MultimediaPlayerJPanel moviePanel, Schedule activeSchedule, User logInUser)
	{
		System.out.println("Powolano watek !");
		this.moviePanel = moviePanel;
		this.activeSchedule = activeSchedule;
		this.logInUser = logInUser;
		this.ownerFrame = ownerFrame;
	}

	public void run()
	{
		Date currentDate = new Date();
		startTimeDate = activeSchedule.getStartTime();
		long milisStart = startTimeDate.getTime();
		long milisCurrent = currentDate.getTime();

		boolean play = false;
		close = false;

		if (startTimeDate.compareTo(currentDate) <= 0)
		{
			System.out.println("Czas mina³");

			if (JOptionPane.showConfirmDialog(ownerFrame, "Czas do odtworzenia harmonogramu min¹³ !\nCzy chesz odtworzyæ harmonogram mimo to ?", "Odtwarzanie", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
			{
				play = true;
			}
		}
		else
		{
			System.out.println("Czekam !!");
			long toPlayDate = Math.abs(milisCurrent - milisStart);

			if (JOptionPane.showConfirmDialog(ownerFrame, "Odtwarzanie zostanie uruchomione za (hh:mm:ss) : \"" + getTimeFromMilis(toPlayDate) + "\".\nCzy chesz zaczekaæ ?", "Odtwarzanie", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
			{
				try
				{
					Thread.sleep(toPlayDate);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
				System.out.println("Czas uplyn¹l");
				play = true;
			}
			
			// Odtwarzanie
		}

		// Odtwarzanie prawdziwe
		if (play == true && close!=true)
		{
			
			LinkedList<MultimediaFile> multimediaList = new LinkedList<MultimediaFile>();
			Set<MultimediaFile> multimediaFiles = activeSchedule.getScheduleMultimediaFiles();
			for(MultimediaFile mf:multimediaFiles)
			{
				multimediaList.add(mf);
			}
			
			
			//Posortuj pliki wzgledem ID
			Collections.sort(multimediaList,new Comparator<MultimediaFile>()
			{
				public int compare(MultimediaFile o1, MultimediaFile o2)
				{
					if ( o1.getId() < o2.getId())
						return -1;
					else if (o1.getId() > o2.getId()) 
						return 1;
					else return 0;
				}
			});
			
			moviePanel.changeMediaList(multimediaList, activeSchedule.isPeriodically()?MediaListPlayerMode.LOOP:MediaListPlayerMode.DEFAULT);
		}
		
	}

	public void setActiveSchedule(Schedule sched)
	{
		activeSchedule = sched;
		System.out.println(activeSchedule.getStartTime());
	}


	private String getTimeFromMilis(long millis)
	{
		String s = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis), TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
		return s;
	}

	public void closeThreadIfEnd()
	{
		this.close = false;
	}

	private boolean close = false;
	private MultimediaPlayerJFrame ownerFrame = null;
	private User logInUser = null;
	private Date startTimeDate = null;
	private MultimediaPlayerJPanel moviePanel;
	private Schedule activeSchedule = null;
	private List<MultimediaFile> sortMultimediaFiles = null;
}
