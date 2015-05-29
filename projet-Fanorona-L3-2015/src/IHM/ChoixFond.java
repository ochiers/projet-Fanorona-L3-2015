package IHM;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ChoixFond extends JPanel {
	
	public ChoixFond() {

		super(new GridLayout(3,3));	//super constructeur par defaut
		
		int width = 233;
		int height = 173;
		
			// creation bouton image
		ImageIcon image1 = new ImageIcon("src/images/iconimage1.jpg");
		ImageIcon image2 = new ImageIcon("src/images/iconimage2.jpg");
		ImageIcon image3 = new ImageIcon("src/images/iconimage3.jpg");
		ImageIcon image4 = new ImageIcon("src/images/iconimage4.jpg");
		ImageIcon image5 = new ImageIcon("src/images/iconimage5.jpg");
		ImageIcon image6 = new ImageIcon("src/images/iconimage6.jpg");
		ImageIcon image7 = new ImageIcon("src/images/iconimage7.jpg");
		ImageIcon image8 = new ImageIcon("src/images/iconimage8.jpg");
		ImageIcon imageDefaut = new ImageIcon("src/images/iconimageDefault.jpg");
			// ajout boutons
		JButton img1 = new JButton((Icon)image1);
		JButton img2 = new JButton((Icon)image2);
		JButton img3 = new JButton((Icon)image3);
		JButton img4 = new JButton((Icon)image4);
		JButton img5 = new JButton((Icon)image5);
		JButton img6 = new JButton((Icon)image6);
		JButton img7 = new JButton((Icon)image7);
		JButton img8 = new JButton((Icon)image8);
		JButton imgDef = new JButton((Icon)imageDefaut);

		/*	// ajout listener
		img1.addActionListener(new Listener1());
		img2.addActionListener(new Listener2());
		img3.addActionListener(new Listener3());
		img4.addActionListener(new Listener4());
		img5.addActionListener(new Listener5());
		img6.addActionListener(new Listener6());
		img7.addActionListener(new Listener7());
		imgDef.addActionListener(new ListenerDef());*/
		
		add(img1);		
		add(img2);
		add(img3);
		add(img4);
		add(img5);
		add(img6);
		add(img7);
		add(img8);
		add(imgDef);
	}
	
    private static void createAndShow() {
		int pw = 699;
		int ph = 549;
		
    		//fenetre
	    JFrame frame = new JFrame("Choix image fond");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    	//ajout boutons-images
	    JPanel tmpPanel = new ChoixFond();
	    tmpPanel.setPreferredSize(new Dimension(pw,ph));
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
