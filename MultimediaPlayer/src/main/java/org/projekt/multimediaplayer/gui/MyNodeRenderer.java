package org.projekt.multimediaplayer.gui;

import java.awt.Component;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import org.projekt.multimediaplayer.model.MultimediaFile;
import org.projekt.multimediaplayer.model.Schedule;

class MyNodeRenderer extends DefaultTreeCellRenderer
{

	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus)
	{
		// TODO poprawic rozmiar czcionki i kroj
		super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
		// int style = Font.BOLD;
		Font font = getFont();
		Font normalFont = getFont();
		Font active = font.deriveFont(Font.BOLD | Font.ITALIC,12);


		Object o = ((DefaultMutableTreeNode) value).getUserObject();

		
		if (o instanceof Schedule)
		{
			Schedule sched = (Schedule) o;

			if (sched.isActive()) 
				setFont(active);
			setIcon(new ImageIcon("multimedia/buttons_icons/schedule-icon.png"));
		}
		else if (o instanceof MultimediaFile)
		{
			MultimediaFile mf = (MultimediaFile) o;

			if(mf.getType().trim().toLowerCase().equals("Audio".trim().toLowerCase()))
				setIcon(new ImageIcon("multimedia/buttons_icons/audioIco.png"));
			else if(mf.getType().trim().toLowerCase().equals("Video".trim().toLowerCase()))
				setIcon(new ImageIcon("multimedia/buttons_icons/videoIco.png"));
			else if(mf.getType().trim().toLowerCase().equals("Unidentified".toLowerCase().trim()))
				setIcon(new ImageIcon("multimedia/buttons_icons/videoIco.png"));
			else {
				setIcon(null);
			}
			
		}
		else
		{
			setIcon(null);

		}

		return this;
	}
}