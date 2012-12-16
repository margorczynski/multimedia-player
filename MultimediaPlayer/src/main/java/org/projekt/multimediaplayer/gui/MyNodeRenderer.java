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
		super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
		Font font = new Font("SansSerif", Font.PLAIN, 13);
		Font active = new Font("SansSerif", Font.BOLD + Font.ITALIC, 14);

		Object o = ((DefaultMutableTreeNode) value).getUserObject();

		if (o instanceof Schedule)
		{
			Schedule sched = (Schedule) o;
			setFont(font);
			if (sched.isActive()) setFont(active);
			setIcon(new ImageIcon("multimedia/buttons_icons/schedule-icon.png"));
		}
		else if (o instanceof MultimediaFile)
		{
			setFont(font);
			MultimediaFile mf = (MultimediaFile) o;

			if (mf.getType().trim().toLowerCase().equals("Audio".trim().toLowerCase()))
				setIcon(new ImageIcon("multimedia/buttons_icons/audioIco.png"));
			else if (mf.getType().trim().toLowerCase().equals("Video".trim().toLowerCase()))
				setIcon(new ImageIcon("multimedia/buttons_icons/videoIco.png"));
			else if (mf.getType().trim().toLowerCase().equals("Unidentified".toLowerCase().trim()))
				setIcon(new ImageIcon("multimedia/buttons_icons/videoIco.png"));
			else
			{
				setIcon(null);
			}

		}
		else
		{
			setFont(font);
			setIcon(null);

		}

		return this;
	}
}