package IHM;

import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextField;

public class Preferences {

	JRadioButtonMenuItem rb1;
	JRadioButtonMenuItem rb2;
	JRadioButtonMenuItem rb3;
	JRadioButtonMenuItem rb4;
	JRadioButtonMenuItem rb5;
	JRadioButtonMenuItem rb6;
	JRadioButtonMenuItem rb7;
	JRadioButtonMenuItem rb8;
	
	JRadioButton rb10;
	JRadioButton rb20;
	JRadioButton rb30;
	JRadioButton rb40;
	JRadioButton rb50;
	JRadioButton rb60;
	JRadioButton rb70;

	JRadioButton rb11;
	JRadioButton rb21;
	JRadioButton rb31;
	JRadioButton rb41;
	JRadioButton rb51;
	JRadioButton rb61;
	JRadioButton rb71;
	
	JTextField name1;
	JTextField name2;
	String nameJ1;
	String nameJ2;
	
	public Fenetre fenetre;
	
	public Preferences(Fenetre f){
		fenetre = f;
	}
			
	public void majPref(){
		
		Fenetre.frame3.setSize(500, 500);
		JPanel panelAccueil3 = new JPanel(new GridLayout(3,2));
		JPanel panelFond = new JPanel(new GridLayout(0,1));
		JPanel panelJoueurs = new JPanel(new GridLayout(0,1));
		JPanel panelPions1 = new JPanel(new GridLayout(0,1));
		JPanel panelPions2 = new JPanel(new GridLayout(0,1));
		JPanel panelBoutons = new JPanel(new GridLayout(0,1));
		panelAccueil3.add(panelFond);
		panelAccueil3.add(panelJoueurs);
		panelAccueil3.add(panelPions1);
		panelAccueil3.add(panelPions2);
		panelAccueil3.add(panelBoutons);
		
			// PANEL FOND
		JLabel fond = new JLabel(" Choix fond d'ecran ");
		panelFond.add(fond);
		ButtonGroup groupe1 = new ButtonGroup();
		rb1 = new JRadioButtonMenuItem(" Fond multicolore ", new ImageIcon("src/images/image1"), false);
			//rb1.addActionListener(new EcouteurPreferences(this));
		rb2 = new JRadioButtonMenuItem(" Fond bleu ", new ImageIcon("src/images/image2"), false);
			//rb2.addActionListener(new EcouteurPreferences(this));
		rb3 = new JRadioButtonMenuItem(" Fond vert ", new ImageIcon("src/images/image3"), false);
			//rb3.addActionListener(new EcouteurPreferences(this));
		rb4 = new JRadioButtonMenuItem(" Fond rose ", new ImageIcon("src/images/image4"), false);
			//rb4.addActionListener(new EcouteurPreferences(this));
		rb5 = new JRadioButtonMenuItem(" Fond bleu clair ", new ImageIcon("src/images/image5"), false);
			//rb5.addActionListener(new EcouteurPreferences(this));
		rb6 = new JRadioButtonMenuItem(" Fond gris blanc ", new ImageIcon("src/images/image6"), false);
			//rb6.addActionListener(new EcouteurPreferences(this));
		rb7 = new JRadioButtonMenuItem(" Fond noir ", new ImageIcon("src/images/image7"), false);
			//rb7.addActionListener(new EcouteurPreferences(this));
		rb8 = new JRadioButtonMenuItem(" Fond par defaut ", new ImageIcon("src/images/imageDefault"), true);
			//rb8.addActionListener(new EcouteurPreferences(this));
		// ajout des boutons radio dans le groupe bg
		groupe1.add(rb1);
		groupe1.add(rb2);
		groupe1.add(rb3);
		groupe1.add(rb4);
		groupe1.add(rb5);
		groupe1.add(rb6);
		groupe1.add(rb7);
		groupe1.add(rb8);
		//ajout de bg au panel
		panelFond.add(rb1);
		panelFond.add(rb2);
		panelFond.add(rb3);
		panelFond.add(rb4);
		panelFond.add(rb5);
		panelFond.add(rb6);
		panelFond.add(rb7);
		panelFond.add(rb8);
		
			// PANEL NOMS
		JLabel nom = new JLabel(" Choix nom joueurs humains ");
		panelJoueurs.add(nom);
		nameJ1 = fenetre.engine.partieCourante.joueurBlanc.getNiveau();
		name1 = new JTextField(" Humain 1 ");
			//name1.addActionListener(new EcouteurPreferences(this, nameJ1));
		nameJ2 = fenetre.engine.partieCourante.joueurNoir.getNiveau();
		name2 = new JTextField(" Humain 2 ");
			//name2.addActionListener(new EcouteurPreferences(this, nameJ2));
		// ajout des boites de dialogue
		panelJoueurs.add(name1);
		panelJoueurs.add(name2);
		
			// PANEL COULEUR PIONS
		JLabel pion1 = new JLabel(" Choix couleur pions J1 ");
		panelPions1.add(pion1);
		ButtonGroup groupe2 = new ButtonGroup();
		rb10 = new JRadioButton(" Pion noir ");
			//rb10.addActionListener(new EcouteurPreferences(this));
		rb20 = new JRadioButton(" Pion blanc ");
			//rb20.addActionListener(new EcouteurPreferences(this));
		rb30 = new JRadioButton(" Pion bleu ");
			//rb30.addActionListener(new EcouteurPreferences(this));
		rb40 = new JRadioButton(" Pion rouge ");
			//rb40.addActionListener(new EcouteurPreferences(this));
		rb50 = new JRadioButton(" Pion vert ");
			//rb50.addActionListener(new EcouteurPreferences(this));
		rb60 = new JRadioButton(" Pion jaune ");
			//rb60.addActionListener(new EcouteurPreferences(this));
		rb70 = new JRadioButton(" Pion multicolore ");
			//rb70.addActionListener(new EcouteurPreferences(this));
		// ajout des boutons radio dans le groupe bg
		groupe2.add(rb10);
		groupe2.add(rb20);
		groupe2.add(rb30);
		groupe2.add(rb40);
		groupe2.add(rb50);
		groupe2.add(rb60);
		groupe2.add(rb70);
		//ajout de bg au panel
		panelPions1.add(rb10);
		panelPions1.add(rb20);
		panelPions1.add(rb30);
		panelPions1.add(rb40);
		panelPions1.add(rb50);
		panelPions1.add(rb60);
		panelPions1.add(rb70);
		
		JLabel pion2 = new JLabel(" Choix couleur pions J2 ");
		panelPions2.add(pion2);
		ButtonGroup groupe3 = new ButtonGroup();
		rb11 = new JRadioButton(" Pion noir ");
			//rb11.addActionListener(new EcouteurPreferences(this));
		rb21 = new JRadioButton(" Pion blanc ");
			//rb21.addActionListener(new EcouteurPreferences(this));
		rb31 = new JRadioButton(" Pion bleu ");
			//rb31.addActionListener(new EcouteurPreferences(this));
		rb41 = new JRadioButton(" Pion rouge ");
			//rb41.addActionListener(new EcouteurPreferences(this));
		rb51 = new JRadioButton(" Pion vert ");
			//rb51.addActionListener(new EcouteurPreferences(this));
		rb61 = new JRadioButton(" Pion jaune ");
			//rb61.addActionListener(new EcouteurPreferences(this));
		rb71 = new JRadioButton(" Pion multicolore ");
			//rb71.addActionListener(new EcouteurPreferences(this));
		// ajout des boutons radio dans le groupe bg
		groupe3.add(rb11);
		groupe3.add(rb21);
		groupe3.add(rb31);
		groupe3.add(rb41);
		groupe3.add(rb51);
		groupe3.add(rb61);
		groupe3.add(rb71);
		//ajout de bg au panel
		panelPions2.add(rb11);
		panelPions2.add(rb21);
		panelPions2.add(rb31);
		panelPions2.add(rb41);
		panelPions2.add(rb51);
		panelPions2.add(rb61);
		panelPions2.add(rb71);
		
			// PANEL BOUTONS
		JButton accept= new JButton("Accepter");
			//accept.addActionListener(new EcouteurPreferences(this, accept.getText()));
		JButton annuler= new JButton("Annuler");
			//annuler.addActionListener(new EcouteurPreferences(this, annuler.getText()));
		panelBoutons.add(accept);
		panelBoutons.add(annuler);
		
		Fenetre.frame3.add(panelAccueil3);
		Fenetre.frame3.setResizable(false);
		Fenetre.frame3.setVisible(false);
	}
	
}
