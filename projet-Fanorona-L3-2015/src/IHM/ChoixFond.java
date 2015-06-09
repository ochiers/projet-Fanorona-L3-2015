package IHM;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.io.File;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ChoixFond extends JPanel {
	Fenetre	fenetre;
	JButton	img1;
	JButton	img2;
	JButton	img3;
	JButton	img4;
	JButton	img5;
	JButton	img6;
	JButton	img7;
	JButton	img8;
	JButton	imgDef;

	String	str_icon_img1	= "." + File.separator + "Ressources" + File.separator + "images" + File.separator + "iconimage1.jpg";
	String	str_icon_img2	= "." + File.separator + "Ressources" + File.separator + "images" + File.separator + "iconimage2.jpg";
	String	str_icon_img3	= "." + File.separator + "Ressources" + File.separator + "images" + File.separator + "iconimage3.jpg";
	String	str_icon_img4	= "." + File.separator + "Ressources" + File.separator + "images" + File.separator + "iconimage4.jpg";
	String	str_icon_img5	= "." + File.separator + "Ressources" + File.separator + "images" + File.separator + "iconimage5.jpg";
	String	str_icon_img6	= "." + File.separator + "Ressources" + File.separator + "images" + File.separator + "iconimage6.jpg";
	String	str_icon_img7	= "." + File.separator + "Ressources" + File.separator + "images" + File.separator + "iconimage7.jpg";
	String	str_icon_img8	= "." + File.separator + "Ressources" + File.separator + "images" + File.separator + "iconimage8.jpg";
	String	str_icon_imgDef	= "." + File.separator + "Ressources" + File.separator + "images" + File.separator + "iconimageDefault.jpg";
	
	String	str_img1	= "." + File.separator + "Ressources" + File.separator + "images" + File.separator + "image1.jpg";
	String	str_img2	= "." + File.separator + "Ressources" + File.separator + "images" + File.separator + "image2.jpg";
	String	str_img3	= "." + File.separator + "Ressources" + File.separator + "images" + File.separator + "image3.jpg";
	String	str_img4	= "." + File.separator + "Ressources" + File.separator + "images" + File.separator + "image4.jpg";
	String	str_img5	= "." + File.separator + "Ressources" + File.separator + "images" + File.separator + "image5.jpg";
	String	str_img6	= "." + File.separator + "Ressources" + File.separator + "images" + File.separator + "image6.jpg";
	String	str_img7	= "." + File.separator + "Ressources" + File.separator + "images" + File.separator + "image7.jpg";
	String	str_img8	= "." + File.separator + "Ressources" + File.separator + "images" + File.separator + "image8.jpg";
	String	str_imgDef	= "." + File.separator + "Ressources" + File.separator + "images" + File.separator + "imageDefault.jpg";

	public ChoixFond(Fenetre f) {

		super(new GridLayout(3, 3)); // super constructeur par defaut
		fenetre = f;

		// creation bouton image
		ImageIcon image1 = new ImageIcon(str_icon_img1);
		ImageIcon image2 = new ImageIcon(str_icon_img2);
		ImageIcon image3 = new ImageIcon(str_icon_img3);
		ImageIcon image4 = new ImageIcon(str_icon_img4);
		ImageIcon image5 = new ImageIcon(str_icon_img5);
		ImageIcon image6 = new ImageIcon(str_icon_img6);
		ImageIcon image7 = new ImageIcon(str_icon_img7);
		ImageIcon image8 = new ImageIcon(str_icon_img8);
		ImageIcon imageDefaut = new ImageIcon(str_icon_imgDef);
		// ajout boutons
		img1 = new JButton((Icon) image1);
		img2 = new JButton((Icon) image2);
		img3 = new JButton((Icon) image3);
		img4 = new JButton((Icon) image4);
		img5 = new JButton((Icon) image5);
		img6 = new JButton((Icon) image6);
		img7 = new JButton((Icon) image7);
		img8 = new JButton((Icon) image8);
		imgDef = new JButton((Icon) imageDefaut);

		// ajout listener
		img1.addActionListener(new ItemAction_img1());
		img2.addActionListener(new ItemAction_img2());
		img3.addActionListener(new ItemAction_img3());
		img4.addActionListener(new ItemAction_img4());
		img5.addActionListener(new ItemAction_img5());
		img6.addActionListener(new ItemAction_img6());
		img7.addActionListener(new ItemAction_img7());
		img8.addActionListener(new ItemAction_img8());
		imgDef.addActionListener(new ItemAction_imgDef());

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

	class ItemAction_img1 implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			Fenetre.setPanelAccueil(new ImagePanel(fenetre.frame, new ImageIcon(str_img1).getImage(), ImageObserver.WIDTH, ImageObserver.HEIGHT));
			fenetre.imageActuelle = new ImageIcon(str_img1);
			fenetre.frame.repaint();
		}

	}

	class ItemAction_img2 implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			Fenetre.setPanelAccueil(new ImagePanel(fenetre.frame, new ImageIcon(str_img2).getImage(), fenetre.fw, fenetre.fh));
			fenetre.imageActuelle = new ImageIcon(str_img2);
			fenetre.frame.repaint();
		}

	}

	class ItemAction_img3 implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			Fenetre.setPanelAccueil(new ImagePanel(fenetre.frame, new ImageIcon(str_img3).getImage(), fenetre.fw, fenetre.fh));
			fenetre.imageActuelle = new ImageIcon(str_img3);
			fenetre.frame.repaint();
		}

	}

	class ItemAction_img4 implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			Fenetre.setPanelAccueil(new ImagePanel(fenetre.frame, new ImageIcon(str_img4).getImage(), fenetre.fw, fenetre.fh));
			fenetre.imageActuelle = new ImageIcon(str_img4);
			fenetre.frame.repaint();
		}

	}

	class ItemAction_img5 implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			Fenetre.setPanelAccueil(new ImagePanel(fenetre.frame, new ImageIcon(str_img5).getImage(), fenetre.fw, fenetre.fh));
			fenetre.imageActuelle = new ImageIcon(str_img5);
			fenetre.frame.repaint();
		}

	}

	class ItemAction_img6 implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			Fenetre.setPanelAccueil(new ImagePanel(fenetre.frame, new ImageIcon(str_img6).getImage(), fenetre.fw, fenetre.fh));
			fenetre.imageActuelle = new ImageIcon(str_img6);
			fenetre.frame.repaint();
		}

	}

	class ItemAction_img7 implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			Fenetre.setPanelAccueil(new ImagePanel(fenetre.frame, new ImageIcon(str_img7).getImage(), fenetre.fw, fenetre.fh));
			fenetre.imageActuelle = new ImageIcon(str_img7);
			fenetre.frame.repaint();
		}

	}

	class ItemAction_img8 implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			Fenetre.setPanelAccueil(new ImagePanel(fenetre.frame, new ImageIcon(str_img8).getImage(), fenetre.fw, fenetre.fh));
			fenetre.imageActuelle = new ImageIcon(str_img8);
			fenetre.frame.repaint();
		}

	}

	class ItemAction_imgDef implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			Fenetre.setPanelAccueil(new ImagePanel(fenetre.frame, new ImageIcon(str_imgDef).getImage(), fenetre.fw, fenetre.fh));
			fenetre.imageActuelle = new ImageIcon(str_imgDef);
			fenetre.frame.repaint();
		}

	}
}
