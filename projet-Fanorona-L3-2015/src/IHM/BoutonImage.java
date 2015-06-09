package IHM;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.*;

@SuppressWarnings("serial")
public class BoutonImage extends JComponent {

	JLabel fonction;
	ImageIcon fond;
	int large, haut;
	
	public BoutonImage(String label, String img, int width, int height){
		fonction = new JLabel(label);
		fond = new ImageIcon(img);
		large = width;
		haut = height;
	}
	
	public void PaintComponent(Graphics g){
		Graphics2D drawable = (Graphics2D) g;
		drawable.drawImage(fond.getImage(), 0, 0, large, haut, null);
		
		JPanel transparent = new JPanel(new BorderLayout());
		transparent.setOpaque(false);
		transparent.setForeground(new Color(0,0,0,0));
		transparent.add(fonction, BorderLayout.PAGE_END);
	}
}
