package org.projekt.multimediaplayer.gui;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;
import javax.swing.JFrame;
import org.projekt.multimediaplayer.model.MultimediaFile;
import org.projekt.multimediaplayer.model.Schedule;
import org.projekt.multimediaplayer.model.User;


import uk.co.caprica.vlcj.player.list.MediaListPlayerMode;

public class PlayMultimediaFromSchedulRunnableBare implements Runnable
{
	public PlayMultimediaFromSchedulRunnableBare(JFrame ownerFrame, MultimediaPlayerBareJPanel moviePanel, Schedule activeSchedule, User logInUser)
	{
		this.movieBarePanel = moviePanel;
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
		
		long t1 = System.currentTimeMillis();

		boolean play = false;
		close = false;
		
		System.out.println("Run time: "+startTimeDate.compareTo(currentDate));
		
		System.out.println("Run entry");

		while(true)
		{
				if(Math.abs(t1 - System.currentTimeMillis()) > 1000) currentDate = new Date();
			
				if(!play && Math.abs(t1 - System.currentTimeMillis()) > 1000)
				{
					
					t1 = System.currentTimeMillis();
				}
			
				if (!play && currentDate.after(activeSchedule.getStartTime()))
				{
					play = true;
					System.out.println("Run if 1 k");
				}
				
				// Odtwarzanie prawdziwe
				if (play == true && close!=true && !movieBarePanel.isPlaying())
				{
					System.out.println("Run if 2 k");
					
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
					System.out.println("Run change");
					movieBarePanel.changeMediaList(multimediaList, activeSchedule.isPeriodically()?MediaListPlayerMode.LOOP:MediaListPlayerMode.DEFAULT);
				}
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
	private JFrame ownerFrame = null;
	private User logInUser = null;
	private Date startTimeDate = null;
	private MultimediaPlayerBareJPanel movieBarePanel;
	private Schedule activeSchedule = null;
	private List<MultimediaFile> sortMultimediaFiles = null;
}
