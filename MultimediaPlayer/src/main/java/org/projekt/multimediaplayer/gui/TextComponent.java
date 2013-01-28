package org.projekt.multimediaplayer.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingWorker;

/*
 * JComponent odpowiedzialny za wyswietlanie przesuwajacego sie tekstu
 */

public final class TextComponent extends JComponent
{
	public TextComponent(final String text)
	{
		this.text = text + " ";

		visibleTextLabel.setFont(new Font("Serif", Font.ITALIC, 25));
		visibleTextLabel.setForeground(Color.white);
		visibleTextLabel.setBackground(Color.DARK_GRAY);

		final SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>()
		{
			@Override
			public Void doInBackground()
			{
				scrollText();
				return null;
			}
		};

		worker.execute();
	}

	private void scrollText()
	{

		final boolean run = true;

		try
		{
			while (run)
			{
				if (scrollSpeed != 0)
				{
					Thread.sleep(300 / scrollSpeed);
				}
				else
				{
					while (scrollSpeed == 0)
						Thread.sleep(100);
				}

				final char c = text.charAt(0);

				final String rest = text.substring(1);

				text = rest + c;

				visibleTextLabel.setText(text);

				offset = (++offset) % (text.length() + 1);

				repaint();
			}
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void paintComponent(final Graphics g)
	{
		super.paintComponent(g);

		g.setColor(Color.white);

		visibleTextLabel.setSize(visibleTextLabel.getPreferredSize());

		visibleTextLabel.paint(g);

	}

	@Override
	public Dimension getPreferredSize()
	{
		return new Dimension(this.getParent().getWidth(), 30);
	}

	public void setScrollSpeed(final int scrollSpeed)
	{
		this.scrollSpeed = scrollSpeed;
	}

	private volatile int	scrollSpeed			= 2;

	private final JLabel	visibleTextLabel	= new JLabel("");

	private volatile int	offset				= 0;

	private String			text;
}
