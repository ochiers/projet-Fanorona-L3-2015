package IHM;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;

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
	JRadioButton bouton[][] = new JRadioButton[6][2]; // 6 couleurs et 2 palettes
/*	JRadioButton rb10;
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
	JRadioButton rb71;*/
	
	JTextField name1;
	JTextField name2;
	String nameJ1;
	String nameJ2;
	
	JButton accepter;
	JButton annuler;
	
	//variables de sauvegarde
	Image saveFond;
	String saveJ1;
//	String savelvl1;
	String saveJ2;
//	String savelvl2;
	Color save1;
	Color save2;
	
	public Fenetre fenetre;
	
	public Preferences(Fenetre f){
		fenetre = f;
	}
			
	public void majPref(){
		
		//Fenetre.frame3.setSize(500, 500);
		JPanel panelAccueil3 = new JPanel(new GridLayout(3,2));
		JPanel panelFond = new JPanel(new GridLayout(0,1));
		JPanel panelJoueurs = new JPanel(new GridLayout(0,1));
		JPanel panelPions1 = new JPanel(new GridLayout(0,1));
		JPanel panelPions2 = new JPanel(new GridLayout(0,1));
		JPanel panelBoutons = new JPanel(new GridLayout(0,1));
		
			// PANEL FOND
		saveFond = fenetre.panelAccueil.getImage();

		JLabel fond = new JLabel(" Choix fond d'ecran ");
		panelFond.add(fond);
		ButtonGroup groupe1 = new ButtonGroup();
		rb1 = new JRadioButtonMenuItem(" Fond multicolore ", new ImageIcon("src/images/image1"), false);
			rb1.addActionListener(new EcouteurPreferences(this));
		rb2 = new JRadioButtonMenuItem(" Fond bleu ", new ImageIcon("src/images/image2"), false);
			rb2.addActionListener(new EcouteurPreferences(this));
		rb3 = new JRadioButtonMenuItem(" Fond vert ", new ImageIcon("src/images/image3"), false);
			rb3.addActionListener(new EcouteurPreferences(this));
		rb4 = new JRadioButtonMenuItem(" Fond rose ", new ImageIcon("src/images/image4"), false);
			rb4.addActionListener(new EcouteurPreferences(this));
		rb5 = new JRadioButtonMenuItem(" Fond bleu clair ", new ImageIcon("src/images/image5"), false);
			rb5.addActionListener(new EcouteurPreferences(this));
		rb6 = new JRadioButtonMenuItem(" Fond gris blanc ", new ImageIcon("src/images/image6"), false);
			rb6.addActionListener(new EcouteurPreferences(this));
		rb7 = new JRadioButtonMenuItem(" Fond noir ", new ImageIcon("src/images/image7"), false);
			rb7.addActionListener(new EcouteurPreferences(this));
		rb8 = new JRadioButtonMenuItem(" Fond par defaut ", new ImageIcon("src/images/imageDefault"), true);
			rb8.addActionListener(new EcouteurPreferences(this));
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
	/*	if(fenetre.engine.partieCourante.joueurBlanc.aiPlayer || fenetre.engine.partieCourante.joueurNoir.aiPlayer){
			if(fenetre.engine.partieCourante.joueurBlanc.aiPlayer && fenetre.engine.partieCourante.joueurNoir.aiPlayer){
				saveJ1 = (" Ordinateur ");
				String tmplevel = fenetre.engine.partieCourante.joueurBlanc.getNiveau();
				if (tmplevel.equals("IA Facile")) savelvl1 = (" Niveau Facile ");
				else if (tmplevel.equals("IA Moyenne")) savelvl1 = (" Niveau Moyen ");
				else if (tmplevel.equals("IA Difficile")) savelvl1 = (" Niveau Difficile ");
				else System.out.println(" Erreur ");
				saveJ2 = (" Ordinateur ");
				tmplevel = fenetre.engine.partieCourante.joueurNoir.getNiveau();
				if (tmplevel.equals("IA Facile")) savelvl2 = (" Niveau Facile ");
				else if (tmplevel.equals("IA Moyenne")) savelvl2 = (" Niveau Moyen ");
				else if (tmplevel.equals("IA Difficile")) savelvl2 = (" Niveau Difficile ");
				else System.out.println(" Erreur ");
			}
			else{
				saveJ1 = (" Humain ");
				savelvl1 = (" Bonne Chance ! ");
				saveJ2 = (" Ordinateur ");
				String tmplevel = fenetre.engine.partieCourante.joueurNoir.getNiveau();
				if (tmplevel.equals("IA Facile")) savelvl2 = (" Niveau Facile ");
				else if (tmplevel.equals("IA Moyenne")) savelvl2 = (" Niveau Moyen ");
				else if (tmplevel.equals("IA Difficile")) savelvl2 = (" Niveau Difficile ");
				else System.out.println(" Erreur ");
			}
		}
		else{
			saveJ1 = (" Humain 1 ");
			savelvl1 = (" Bonne Chance ! ");
			saveJ2 = (" Humain 2 ");
			savelvl2 = (" Bonne Chance ! ");
		}
		
		
		nameJ1 = fenetre.engine.partieCourante.joueurBlanc.getNiveau();
		name1 = new JTextField(" Humain 1 ");
			name1.addActionListener(new EcouteurPreferences(this));
		nameJ2 = fenetre.engine.partieCourante.joueurNoir.getNiveau();
		name2 = new JTextField(" Humain 2 ");
			name2.addActionListener(new EcouteurPreferences(this));
		// ajout des boites de dialogue
		panelJoueurs.add(name1);
		panelJoueurs.add(name2);*/
		JLabel nom = new JLabel(" Choix nom joueurs humains ");
		panelJoueurs.add(nom);
		name1 = new JTextField(" Joueur 1 ");
			name1.addActionListener(new EcouteurPreferences(this));
		name2 = new JTextField(" Joueur 2 ");
			name2.addActionListener(new EcouteurPreferences(this));
		panelJoueurs.add(name1);
		panelJoueurs.add(name2);
			// PANEL COULEUR PIONS
		//save1 = ;
		//save2 = ;
		JLabel pion1 = new JLabel(" Choix couleur pions J1 ");
		panelPions1.add(pion1);
		ButtonGroup groupe2 = new ButtonGroup();
		bouton[0][0] = new JRadioButton(" Pion noir ");
			bouton[0][0].addActionListener(new EcouteurPreferences(this));
		bouton[1][0] = new JRadioButton(" Pion blanc ");
			bouton[1][0].addActionListener(new EcouteurPreferences(this));
		bouton[2][0] = new JRadioButton(" Pion bleu ");
			bouton[2][0].addActionListener(new EcouteurPreferences(this));
		bouton[3][0] = new JRadioButton(" Pion rouge ");
			bouton[3][0].addActionListener(new EcouteurPreferences(this));
		bouton[4][0] = new JRadioButton(" Pion vert ");
			bouton[4][0].addActionListener(new EcouteurPreferences(this));
		bouton[5][0] = new JRadioButton(" Pion jaune ");
			bouton[5][0].addActionListener(new EcouteurPreferences(this));
		/*rb70 = new JRadioButton(" Pion multicolore ");
			rb70.addActionListener(new EcouteurPreferences(this));*/
		// ajout des boutons radio dans le groupe bg
		for(int i=0;i<bouton.length;i++)
			groupe2.add(bouton[i][0]);
		
		//groupe2.add(rb70);
		//ajout de bg au panel
		for(int i=0;i<bouton.length;i++)
			panelPions1.add(bouton[i][0]);
		
		//panelPions1.add(rb70);
		
		JLabel pion2 = new JLabel(" Choix couleur pions J2 ");
		panelPions2.add(pion2);
		ButtonGroup groupe3 = new ButtonGroup();
		bouton[0][1] = new JRadioButton(" Pion noir ");
			bouton[0][1].addActionListener(new EcouteurPreferences(this));
		bouton[1][1] = new JRadioButton(" Pion blanc ");
			bouton[1][1].addActionListener(new EcouteurPreferences(this));
		bouton[2][1] = new JRadioButton(" Pion bleu ");
			bouton[2][1].addActionListener(new EcouteurPreferences(this));
		bouton[3][1] = new JRadioButton(" Pion rouge ");
			bouton[3][1].addActionListener(new EcouteurPreferences(this));
		bouton[4][1] = new JRadioButton(" Pion vert ");
			bouton[4][1].addActionListener(new EcouteurPreferences(this));
		bouton[5][1] = new JRadioButton(" Pion jaune ");
			bouton[5][1].addActionListener(new EcouteurPreferences(this));
		/*rb71 = new JRadioButton(" Pion multicolore ");
			rb71.addActionListener(new EcouteurPreferences(this));*/
		// ajout des boutons radio dans le groupe bg
		for(int i=0;i<bouton.length;i++)
			groupe3.add(bouton[i][1]);
		
		//groupe3.add(rb71);
		//ajout de bg au panel
		for(int i=0;i<bouton.length;i++)
			panelPions2.add(bouton[i][1]);

		int numCouleur=-1;
		if(fenetre.pion1==Color.black){
			numCouleur=0;
		}else if(fenetre.pion1==Color.white){
			numCouleur=1;
		}else if(fenetre.pion1==Color.blue){
			numCouleur=2;
		}else if(fenetre.pion1==Color.red){
			numCouleur=3;
		}else if(fenetre.pion1==Color.green){
			numCouleur=4;
		}else if(fenetre.pion1==Color.yellow){
			numCouleur=5;
		}
		bouton[numCouleur][0].setSelected(true);
		bouton[numCouleur][1].setEnabled(false);
		
		if(fenetre.pion2==Color.black){
			numCouleur=0;
		}else if(fenetre.pion2==Color.white){
			numCouleur=1;
		}else if(fenetre.pion2==Color.blue){
			numCouleur=2;
		}else if(fenetre.pion2==Color.red){
			numCouleur=3;
		}else if(fenetre.pion2==Color.green){
			numCouleur=4;
		}else if(fenetre.pion2==Color.yellow){
			numCouleur=5;
		}
		bouton[numCouleur][1].setSelected(true);
		bouton[numCouleur][0].setEnabled(false);
		
		save1=fenetre.pion1;
		save2=fenetre.pion2;
		
			// PANEL BOUTONS
		accepter = new JButton("Accepter");
			accepter.addActionListener(new EcouteurPreferences(this));
		annuler = new JButton("Annuler");
			annuler.addActionListener(new EcouteurPreferences(this));
		panelBoutons.add(accepter);
		panelBoutons.add(annuler);
				
		panelAccueil3.add(panelFond);
		panelAccueil3.add(panelJoueurs);
		panelAccueil3.add(panelPions1);
		panelAccueil3.add(panelPions2);
		panelAccueil3.add(panelBoutons);
		
		/*Fenetre.frame3.add(panelAccueil3);
		Fenetre.frame3.setResizable(false);
		Fenetre.frame3.setVisible(false);*/
	}
	
	public void desactiveLautrePion(){
		for(int i=0;i<bouton.length;i++){
			if(bouton[i][0].isSelected())bouton[i][1].setEnabled(false);
			else if(bouton[i][1].isSelected())bouton[i][0].setEnabled(false);
			else{
				bouton[i][0].setEnabled(true);
				bouton[i][1].setEnabled(true);
			}
			
		}
	}
	public void resetBouton(){
		for(int i=0;i<bouton.length;i++){
			bouton[i][0].setSelected(false);
			bouton[i][1].setSelected(false);
			bouton[i][0].setEnabled(true);
			bouton[i][1].setEnabled(true);
		}
		int i=0;
		if(fenetre.pion1==Color.black){}
		else{
			i++;
			if(fenetre.pion1==Color.white){}
			else{ 
				i++;
				if(fenetre.pion1==Color.blue){}
				else{ 
					i++;
					if(fenetre.pion1==Color.red){}
					else{ 
						i++;
						if(fenetre.pion1==Color.green){}
						else{ 
							i++;
							if(fenetre.pion1==Color.yellow){}
		}}}}}
		bouton[i][0].setSelected(true);
		bouton[i][1].setEnabled(false);
		i=0;
		if(fenetre.pion2==Color.black){}
		else{
			i++;
			if(fenetre.pion2==Color.white){}
			else{ 
				i++;
				if(fenetre.pion2==Color.blue){}
				else{ 
					i++;
					if(fenetre.pion2==Color.red){}
					else{ 
						i++;
						if(fenetre.pion2==Color.green){}
						else{ 
							i++;
							if(fenetre.pion2==Color.yellow){}
		}}}}}
		bouton[i][1].setSelected(true);
		bouton[i][0].setEnabled(false);
	}
	
}
