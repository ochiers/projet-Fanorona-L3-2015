package IHM;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ChoixFond extends JPanel {
	static JPanel tmpPanel = new JPanel();
	
	public ChoixFond() {

		int width = 100;
		int height = 30;
		int pw = 700;
		int ph = 500;

		tmpPanel.setLayout(null);
		tmpPanel.setPreferredSize(new Dimension(pw, ph));
		
			// creation bouton image
		ImageIcon image1 = new ImageIcon("src/images/image1");
		ImageIcon image2 = new ImageIcon("src/images/image2");
		ImageIcon image3 = new ImageIcon("src/images/image3");
		ImageIcon image4 = new ImageIcon("src/images/image4");
		ImageIcon image5 = new ImageIcon("src/images/image5");
		ImageIcon image6 = new ImageIcon("images/image6");
		ImageIcon image7 = new ImageIcon("images/image7");
		ImageIcon imageDefaut = new ImageIcon("images/imageDefaut");
		JButton img1 = new JButton(image1);
		JButton img2 = new JButton(image2);
		JButton img3 = new JButton(image3);
		JButton img4 = new JButton(image4);
		JButton img5 = new JButton(image5);
		JButton img6 = new JButton(image6);
		JButton img7 = new JButton(image7);
		JButton imgDef = new JButton();
			// ancrage des boutons
		imgDef.setIcon(imageDefaut);
		img1.setBounds(50, 50, width, height);
		img2.setBounds(200, 100, width, height);
		img3.setBounds(400, 150, width, height);
		img4.setBounds(50, 200, width, height);
		img5.setBounds(200, 250, width, height);
		img6.setBounds(400, 300, width, height);
		img7.setBounds(50, 350, width, height);
		imgDef.setBounds(200, 400, width, height);
		
		
		/*	// ajout listener
		img1.addActionListener(new Listener1());
		img2.addActionListener(new Listener2());
		img3.addActionListener(new Listener3());
		img4.addActionListener(new Listener4());
		img5.addActionListener(new Listener5());
		img6.addActionListener(new Listener6());
		img7.addActionListener(new Listener7());
		imgDef.addActionListener(new ListenerDef());*/
		
		tmpPanel.add(img1);		
		tmpPanel.add(img2);
		tmpPanel.add(img3);
		tmpPanel.add(img4);
		tmpPanel.add(img5);
		tmpPanel.add(img6);
		tmpPanel.add(img7);
		tmpPanel.add(imgDef);
	}
	
    private static void createAndShow() {
	    	//fenetre
	    JFrame frame = new JFrame("Choix image fond");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    	//ajout colorchoosen
	    new ChoixFond();
	    tmpPanel.setOpaque(true); 
	    frame.setContentPane(tmpPanel);
	
	    frame.pack();
	    frame.setVisible(true);
	}
	
	/*public static void main(String[] args) {
	    javax.swing.SwingUtilities.invokeLater(new Runnable() {
	        public void run() { createAndShow(); }
	    });
	}*/
	
}
