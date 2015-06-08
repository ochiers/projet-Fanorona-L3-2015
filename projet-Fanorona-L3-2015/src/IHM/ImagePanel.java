package IHM;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {

	private static final long	serialVersionUID	= 1L;
	public JFrame				frame;
	static Image				img;
	private int					width;
	private int					height;

	public ImagePanel(JFrame frame, String img, int x, int y)
	{
		// this(new ImageIcon(img).getImage(), x, y);
		ImagePanel.img = new ImageIcon(img).getImage();
		this.width = x;
		this.height = y;
		this.frame = frame;
	}

	public ImagePanel(JFrame frame, Image img, int x, int y)
	{
		ImagePanel.img = img;
		this.width = x;
		this.height = y;
		this.frame = frame;
	}

	public ImagePanel(JFrame frame, Icon icon, int x, int y)
	{
		this.width = x;
		this.height = y;
		ImagePanel.img = iconToImage(icon);
		this.frame = frame;
	}

	public Image getImage()
	{
		return ImagePanel.img;
	}

	// fonction recuperee sur Internet
	public static Image iconToImage(Icon icon)
	{
		if (icon instanceof ImageIcon)
		{
			return ((ImageIcon) icon).getImage();
		} else
		{
			int w = icon.getIconWidth();
			int h = icon.getIconHeight();
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			GraphicsDevice gd = ge.getDefaultScreenDevice();
			GraphicsConfiguration gc = gd.getDefaultConfiguration();
			BufferedImage image = gc.createCompatibleImage(w, h);
			Graphics2D g = image.createGraphics();
			icon.paintIcon(null, g, 0, 0);
			g.dispose();
			return image;
		}
	}

	public void paintComponent(Graphics g)
	{
		g.drawImage(img, 0, 0, this.frame.getWidth(), this.frame.getHeight(), this);
	}

}