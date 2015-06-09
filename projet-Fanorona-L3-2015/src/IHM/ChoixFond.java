package IHM;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

	public ChoixFond(Fenetre f)
	{

		super(new GridLayout(3, 3)); // super constructeur par defaut
		fenetre = f;

		// creation bouton image
		ImageIcon image1 = new ImageIcon("./Ressources/images/iconimage1.jpg".replace("/", File.separator));
		ImageIcon image2 = new ImageIcon("./Ressources/images/iconimage2.jpg".replace("/", File.separator));
		ImageIcon image3 = new ImageIcon("./Ressources/images/iconimage3.jpg".replace("/", File.separator));
		ImageIcon image4 = new ImageIcon("./Ressources/images/iconimage4.jpg".replace("/", File.separator));
		ImageIcon image5 = new ImageIcon("./Ressources/images/iconimage5.jpg".replace("/", File.separator));
		ImageIcon image6 = new ImageIcon("./Ressources/images/iconimage6.jpg".replace("/", File.separator));
		ImageIcon image7 = new ImageIcon("./Ressources/images/iconimage7.jpg".replace("/", File.separator));
		ImageIcon image8 = new ImageIcon("./Ressources/images/iconimage8.jpg".replace("/", File.separator));
		ImageIcon imageDefaut = new ImageIcon("./Ressources/images/iconimageDefault.jpg".replace("/", File.separator));
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

		public void actionPerformed(ActionEvent e)
		{
			fenetre.setPanelAccueil(new ImagePanel(fenetre.frame, new ImageIcon("./Ressources/images/image1.jpg".replace("/", File.separator)).getImage(), fenetre.frame.WIDTH, fenetre.frame.HEIGHT));
			fenetre.imageActuelle = new ImageIcon("./Ressources/images/image1.jpg".replace("/", File.separator));
			fenetre.frame.repaint();
		}

	}

	class ItemAction_img2 implements ActionListener {

		public void actionPerformed(ActionEvent e)
		{
			fenetre.setPanelAccueil(new ImagePanel(fenetre.frame, new ImageIcon("./Ressources/images/image2.jpg".replace("/", File.separator)).getImage(), fenetre.fw, fenetre.fh));
			fenetre.imageActuelle = new ImageIcon("./Ressources/images/image2.jpg".replace("/", File.separator));
			fenetre.frame.repaint();
		}

	}

	class ItemAction_img3 implements ActionListener {

		public void actionPerformed(ActionEvent e)
		{
			fenetre.setPanelAccueil(new ImagePanel(fenetre.frame, new ImageIcon("./Ressources/images/image3.jpg".replace("/", File.separator)).getImage(), fenetre.fw, fenetre.fh));
			fenetre.imageActuelle = new ImageIcon("./Ressources/images/image3.jpg".replace("/", File.separator));
			fenetre.frame.repaint();
		}

	}

	class ItemAction_img4 implements ActionListener {

		public void actionPerformed(ActionEvent e)
		{
			fenetre.setPanelAccueil(new ImagePanel(fenetre.frame, new ImageIcon("./Ressources/images/image4.jpg".replace("/", File.separator)).getImage(), fenetre.fw, fenetre.fh));
			fenetre.imageActuelle = new ImageIcon("./Ressources/images/image4.jpg".replace("/", File.separator));
			fenetre.frame.repaint();
		}

	}

	class ItemAction_img5 implements ActionListener {

		public void actionPerformed(ActionEvent e)
		{
			fenetre.setPanelAccueil(new ImagePanel(fenetre.frame, new ImageIcon("./Ressources/images/image5.jpg".replace("/", File.separator)).getImage(), fenetre.fw, fenetre.fh));
			fenetre.imageActuelle = new ImageIcon("./Ressources/images/image5.jpg".replace("/", File.separator));
			fenetre.frame.repaint();
		}

	}

	class ItemAction_img6 implements ActionListener {

		public void actionPerformed(ActionEvent e)
		{
			fenetre.setPanelAccueil(new ImagePanel(fenetre.frame, new ImageIcon("./Ressources/images/image6.jpg".replace("/", File.separator)).getImage(), fenetre.fw, fenetre.fh));
			fenetre.imageActuelle = new ImageIcon("./Ressources/images/image6.jpg".replace("/", File.separator));
			fenetre.frame.repaint();
		}

	}

	class ItemAction_img7 implements ActionListener {

		public void actionPerformed(ActionEvent e)
		{
			fenetre.setPanelAccueil(new ImagePanel(fenetre.frame, new ImageIcon("./Ressources/images/image7.jpg".replace("/", File.separator)).getImage(), fenetre.fw, fenetre.fh));
			fenetre.imageActuelle = new ImageIcon("./Ressources/images/image7.jpg".replace("/", File.separator));
			fenetre.frame.repaint();
		}

	}

	class ItemAction_img8 implements ActionListener {

		public void actionPerformed(ActionEvent e)
		{
			fenetre.setPanelAccueil(new ImagePanel(fenetre.frame, new ImageIcon("./Ressources/images/image8.jpg".replace("/", File.separator)).getImage(), fenetre.fw, fenetre.fh));
			fenetre.imageActuelle = new ImageIcon("./Ressources/images/image8.jpg".replace("/", File.separator));
			fenetre.frame.repaint();
		}

	}

	class ItemAction_imgDef implements ActionListener {

		public void actionPerformed(ActionEvent e)
		{
			fenetre.setPanelAccueil(new ImagePanel(fenetre.frame, new ImageIcon("./Ressources/images/imageDefault.jpg".replace("/", File.separator)).getImage(), fenetre.fw, fenetre.fh));
			fenetre.imageActuelle = new ImageIcon("./Ressources/images/imageDefault.jpg".replace("/", File.separator));
			fenetre.frame.repaint();
		}

	}
}
